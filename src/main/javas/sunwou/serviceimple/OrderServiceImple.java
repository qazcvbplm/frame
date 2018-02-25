package sunwou.serviceimple;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import sunwou.entity.Address;
import sunwou.entity.App;
import sunwou.entity.DayLog;
import sunwou.entity.FullCut;
import sunwou.entity.Order;
import sunwou.entity.OrderProduct;
import sunwou.entity.Product;
import sunwou.entity.Sender;
import sunwou.entity.Shop;
import sunwou.entity.User;
import sunwou.mongo.dao.IOrderDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IAddressService;
import sunwou.service.IAppService;
import sunwou.service.IFullCutService;
import sunwou.service.IOrderService;
import sunwou.service.IProductService;
import sunwou.service.ISchoolService;
import sunwou.service.ISenderService;
import sunwou.service.IShopService;
import sunwou.service.IUserService;
import sunwou.util.StringUtil;
import sunwou.util.TimeUtil;
import sunwou.valueobject.AddTakeOutParamsObject;
import sunwou.valueobject.SenderStatisticsByTime;
import sunwou.valueobject.ShopStatisticsByTime;
import sunwou.wx.WXUtil;

@Component
public class OrderServiceImple implements IOrderService{

	@Autowired
	private IOrderDao iOrderDao;
	@Autowired
	private IShopService iShopService;
	@Autowired
	private IFullCutService iFullCutService;
	@Autowired
	private IAddressService iAddressService;
	@Autowired
	private ISenderService iSenderService;
	@Autowired
	private IProductService iProductService;
	@Autowired 
	private IUserService iUserService;
	@Autowired
	private ISchoolService iSchoolService;
	@Autowired
	private IAppService iAppService;
	
	private final long TIMEOUT=2*60*60*1000;
	@Override
	public String add(AddTakeOutParamsObject aop, App app) {
		Order order;
		Shop s=iShopService.findById(aop.getShopId());
		Address address=iAddressService.findById(aop.getAddressId());
		if(aop.isTakeout()){
			order=new Order(s,aop,"外卖订单");
			order.setFloorId(address.getFloorId());
		}
		else{
			order=new Order(s,aop,"堂食订单");
		}
		//设置收货人信息
		order.setAddress(address);
		List<FullCut> fullCuts=iFullCutService.findByShopId(s.getSunwouId());
		//判断是否有折扣商品
		boolean discount=false;
		List<OrderProduct> ops=new ArrayList<>();
		String[] productIds=aop.getProductId();
		//计算商品
		for(int i=0;i<productIds.length;i++){
			Product product=iProductService.findbyId(productIds[i]);
			int number=aop.getNumber()[i];
			int att=aop.getAttr()[i];
			OrderProduct op=new OrderProduct(product,number,att);
			//核算优惠价格
			if(product.getDiscount().compareTo(new BigDecimal("1.0"))!=0){
				      discount=true;
				      order.setDiscountType("商品折扣");
				      order.completeDiscount(s,op);
			}
			//计算商品总价和餐盒费
			order.completeProductAndBox(s,op);
			//添加订单商品
			ops.add(op);
		}
		//计算满减
		if(!discount){
			FullCut fullCut=null;
			for(int i=0;i<fullCuts.size();i++){
				 if(order.getProductPrice().compareTo(new BigDecimal(fullCuts.get(i).getFull()))>-1){
					      fullCut=fullCuts.get(i);
				 }
			}
			if(fullCut!=null){
				order.setDiscountType("满减优惠");
				order.completeDiscount2(s,fullCut);
			}
		}
		
		//计算总价
		order.complete(s,aop.isTakeout());
		//价格核算
		order.app(app,s);
		//设置订单商品
		order.setOrderProduct(ops);
		return iOrderDao.add(order);
	}
	@Override
	public Order findById(String sunwouId) {
		// TODO Auto-generated method stub
		return iOrderDao.findById(sunwouId, MongoBaseDaoImple.ORDER);
	}
	@Override
	public int update(Order o) {
		// TODO Auto-generated method stub
		return iOrderDao.updateById(o, MongoBaseDaoImple.ORDER);
	}
	@Override
	public List<Order> find(QueryObject qo) {
		// TODO Auto-generated method stub
		return iOrderDao.find(qo);
	}
	@Override
	public int count(QueryObject qo) {
		// TODO Auto-generated method stub
		return iOrderDao.count(qo);
	}
	public int waternumber(String shopId) {
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("createDate").is(TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY)),
		Criteria.where("status").ne("待付款"),
		Criteria.where("status").ne("待接手"),
		Criteria.where("shopId").is(shopId));
		return (int) iOrderDao.getMongoTemplate().count(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.ORDER));
	}
	@Override
	public int paysuccess(Order order) {
		int rs=0;
		if(order.getStatus().equals("待付款")){
			 order.setStatus("待接手");
			 order.setPayTime(TimeUtil.formatDate(new Date(), TimeUtil.TO_S));
			 order.setTimeOut(System.currentTimeMillis()+TIMEOUT);
			 rs+=iOrderDao.updateById(order, MongoBaseDaoImple.ORDER);
		}
		return rs;
	}
	@Override
	public int clear() {
		Criteria c=new Criteria();
		 c.andOperator(Criteria.where("status").is("待付款"));
		 iOrderDao.getMongoTemplate().findAllAndRemove(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.ORDER));
		return 0;
	}
	@Override
	public List<Order> findBySenderDJS(Sender sender) {
		String[] floorsId=sender.getFloorsId();
		Criteria c=new Criteria();
		c.andOperator(
				Criteria.where("floorId").in(floorsId),
				Criteria.where("shopId").in(sender.getShopsId()),
				Criteria.where("status").is("商家已接手"),
				Criteria.where("type").is("外卖订单"),
				Criteria.where("senderId").exists(false));
		Query query=new Query(c);
		query.fields()
		.include("status")
		.include("shopName")
		.include("shopImage")
		.include("shopAddress")
		.include("type")
		.include("waterNumber")
		.include("address")
		.include("createTime")
		.include("remark");
		return iOrderDao.getMongoTemplate().find(query, MongoBaseDaoImple.classes.get(MongoBaseDaoImple.ORDER));
	}
	@Override
	public List<Order> findByShopDJS(Shop shop) {
		Criteria c=new Criteria();
		c.andOperator(
				Criteria.where("shopId").is(shop.getSunwouId()),
				Criteria.where("status").is("待接手"));
		return iOrderDao.getMongoTemplate().find(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.ORDER));
	}
	@Override
	public int toDaySchoolOrderCount(String schoolId) {
		Criteria c=new Criteria();
		c.andOperator(
				Criteria.where("schoolId").is(schoolId),
				Criteria.where("createDate").is(TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY)),
				Criteria.where("status").is("已完成"));
		return (int) iOrderDao.getMongoTemplate().count(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.ORDER));
	}
	@Override
	public BigDecimal schoolToDayTransactionMoney(String schoolId) {
		Criteria c=new Criteria();
		c.andOperator(
				Criteria.where("schoolId").is(schoolId),
				Criteria.where("createDate").is(TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY)),
				Criteria.where("status").is("已完成"));
		Query query=new Query(c);
		query.fields().include("total");
		List<Order> rs=iOrderDao.getMongoTemplate().find(query, MongoBaseDaoImple.classes.get(MongoBaseDaoImple.ORDER));
		BigDecimal total=new BigDecimal("0");
		for(Order o:rs){
			total=total.add(o.getTotal());
		}
		total=total.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		return total;
	}
	@Override
	public int senderAcceptOrder(Order order, Sender sender,App app,User user) {
		order.setSenderId(sender.getSunwouId());
		order.completeSender(sender.getRate());
		order.setStatus("配送员已接手");
		order.setSenderName(sender.getRealName());
		order.setSenderPhone(sender.getPhone());
		int rs=update(order);
		//模板消息通知
		Map<String, String> map = new HashMap<>();
		map.put("appid", app.getAppid());
		map.put("secert", app.getSecertWX());
		map.put("template_id", "ydpYVe0uEYYsuegZhAjeCjBlHHYvfH_ycLJR8qq1DUM");
		map.put("touser", user.getOpenid());
		map.put("form_id", order.getPrepareId());
		map.put("keywordcount", "7");
		map.put("keyword1", TimeUtil.formatDate(new Date(), TimeUtil.TO_S));
		map.put("keyword2", order.getOrderProduct().get(0).getProduct().getName()+"等商品");
		map.put("keyword3", sender.getRealName());
		map.put("keyword4", sender.getPhone());
		map.put("keyword5", "正在配送");
		map.put("keyword6", "恭喜您又获得了积分，可以去积分商城选购.");
		WXUtil.snedM(map);
		return rs;
	}
	@Override
	public List<Order> findorderToday(Shop shop) {
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("createDate").is(TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY)),
				Criteria.where("shopId").is(shop.getSunwouId()),
				Criteria.where("status").ne("待付款"));
		return iOrderDao.getMongoTemplate().find(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.ORDER));
	}
	@Override
	public List<Order> findorderToday(Sender sender) {
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("senderId").is(sender.getSunwouId()),Criteria.where("createDate").is(TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY)));
		List<Order> rs=iOrderDao.getMongoTemplate().find(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.ORDER));
		List<Order> rs2=findBySenderDJS(sender);
		rs.addAll(rs2);
		return rs;
	}
	@Override
	public void statisticsbytimeandshop(String shopId, String startTime, String endTime, ShopStatisticsByTime sbt) {
                if(StringUtil.isEmpty(startTime)||StringUtil.isEmpty(endTime)){
                	startTime=TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY);
                	endTime=startTime;
                }
                Criteria c=new Criteria();
                c.andOperator(Criteria.where("shopId").is(shopId),
                		Criteria.where("createDate").gte(startTime),
                		Criteria.where("createDate").lte(endTime),
                		Criteria.where("status").is("已完成"));
                List<Order> rs=iOrderDao.getMongoTemplate().find(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.ORDER));
                for(Order temp:rs){
                	sbt.setTotal(sbt.getTotal().add(temp.getTotal()));
                	sbt.setShopGet(sbt.getShopGet().add(temp.getShopGet()));
                	sbt.setBoxPrice(sbt.getBoxPrice().add(temp.getBoxPrice()));
                }
                sbt.setOrderNumber(rs.size());
	}
	@Override
	public void statisticsbytimeandsender(String senderId, String startTime, String endTime,
			SenderStatisticsByTime sbt) {
		// TODO Auto-generated method stub
		Criteria c=new Criteria();
        if(StringUtil.isEmpty(startTime)||StringUtil.isEmpty(endTime)){
        	startTime=TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY);
        	endTime=startTime;
        }
        c.andOperator(Criteria.where("senderId").is(senderId),
        		Criteria.where("createDate").gte(startTime),
        		Criteria.where("createDate").lte(endTime));
        List<Order> rs=iOrderDao.getMongoTemplate().find(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.ORDER));
        for(Order temp:rs){
        	if(temp.getStatus().equals("已完成")){
        		sbt.setComplete(sbt.getComplete()+1);
        		sbt.setGet(sbt.getGet().add(temp.getSenderGet()));
        	}
        }
        sbt.setOrderNumber(rs.size());
		sbt.setUnComplete(sbt.getOrderNumber()-sbt.getComplete());
	}
	@Override
	public List<Order> timeOutProcess() {
		int rscount=0;
		String today=TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY);
		Criteria c=new Criteria();
        c.andOperator(
        		Criteria.where("createDate").is(today),
        		Criteria.where("status").is("商家已接手"),
        		Criteria.where("type").is("堂食订单"),
        		Criteria.where("timeOut").lte(System.currentTimeMillis()));
        List<Order> rs=iOrderDao.getMongoTemplate().find(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.ORDER));
        for(Order temp:rs){
        	temp.setStatus("已完成");
        	rscount+=update(temp);
        	//完成逻辑
        	takeOutComplete(temp);
        }
		return rs;
	}
	@Override
	public void shopDayLog(String day, DayLog dayLogshop) {
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("shopId").is(dayLogshop.getShopId()),
				Criteria.where("createDate").is(day)
				);
		c.orOperator(Criteria.where("status").is("已完成"),
				Criteria.where("status").is("已取消"));
		List<Order> orders=iOrderDao.getMongoTemplate().find(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.ORDER));
		for(Order temp:orders){
			dayLogshop.addOrder(temp);
		}
	}
	@Override
	public int takeOutComplete(Order order) {
		//订单完成后的逻辑
		Sender sender=iSenderService.findById(order.getSenderId());
		//用户增加积分
		iUserService.addSource(order.getUserId(),order.getTotal().intValue(),"加");
		//销量增加
		iProductService.salesadd(order.getOrderProduct());
		//配送员增加配送费
		iSenderService.money(sender, order.getSenderGet(), true);
        //学校增加余额
		iSchoolService.money(sender.getSchoolId(), order.getTotal().subtract(order.getAppGet()), true);
		//平台余额增加
		iAppService.money(order.getAppGet(), true);
		return 0;
	}

}
