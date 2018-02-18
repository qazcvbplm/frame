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

import com.mongodb.Mongo;

import sunwou.entity.Address;
import sunwou.entity.App;
import sunwou.entity.FullCut;
import sunwou.entity.Order;
import sunwou.entity.OrderProduct;
import sunwou.entity.Product;
import sunwou.entity.Sender;
import sunwou.entity.Shop;
import sunwou.entity.User;
import sunwou.mongo.dao.IAddressDao;
import sunwou.mongo.dao.IOrderDao;
import sunwou.mongo.dao.IProductDao;
import sunwou.mongo.dao.IShopDao;
import sunwou.mongo.dao.IUserDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IFullCutService;
import sunwou.service.IOrderService;
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
	private IShopDao iShopDao;
	@Autowired
	private IProductDao iProductDao;
	@Autowired
	private IFullCutService iFullCutService;
	@Autowired
	private IAddressDao iAddressDao;
	@Autowired
	private IUserDao iUserDao;
	@Override
	public String add(AddTakeOutParamsObject aop, App app) {
		Order order;
		Shop s=iShopDao.findById(aop.getShopId(), MongoBaseDaoImple.SHOP);
		Address address=iAddressDao.findById(aop.getAddressId(), MongoBaseDaoImple.ADDRESS);
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
			Product product=iProductDao.findById(productIds[i], MongoBaseDaoImple.PRODUCT);
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
	public int waternumber() {
		Criteria c=new Criteria();
		c.and("createDate").is(TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY)).and("status").ne("待付款");
		return (int) iOrderDao.getMongoTemplate().count(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.ORDER));
	}
	@Override
	public int paysuccess(Order order) {
		int rs=0;
		if(order.getStatus().equals("待付款")){
			 order.setStatus("待接手");
			 rs+=iOrderDao.updateById(order, MongoBaseDaoImple.ORDER);
			 List<OrderProduct> op=order.getOrderProduct();
			 Product p;
			 for(OrderProduct temp:op){
				 p=iProductDao.findById(temp.getProduct().getSunwouId(), MongoBaseDaoImple.PRODUCT);
				 p.setSales(p.getSales()+1);
				 iProductDao.updateById(p, MongoBaseDaoImple.PRODUCT);
			 }
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
		c.andOperator(Criteria.where("senderId").is(sender.getSunwouId()));
		List<Order> rs=iOrderDao.getMongoTemplate().find(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.ORDER));
		List<Order> rs2=findBySenderDJS(sender);
		rs.addAll(rs2);
		return rs;
	}
	@Override
	public void statisticsbytimeandshop(String shopId, String startTime, String endTime, ShopStatisticsByTime sbt) {
                Criteria c=new Criteria();
                if(StringUtil.isEmpty(startTime)||StringUtil.isEmpty(endTime)){
                	startTime=TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY);
                	endTime=startTime;
                }
                c.andOperator(Criteria.where("shopId").is(shopId),
                		Criteria.where("createDate").gte(startTime),
                		Criteria.where("createDate").lte(endTime),
                		Criteria.where("status").is("已完成"));
                List<Order> rs=iOrderDao.getMongoTemplate().find(new Query(), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.ORDER));
                for(Order temp:rs){
                	sbt.setTotal(sbt.getTotal().add(temp.getTotal()));
                	sbt.setShopGet(sbt.getShopGet().add(temp.getShopGet()));
                	sbt.setBoxPrice(sbt.getBoxPrice().add(temp.getBoxPirce()));
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
        List<Order> rs=iOrderDao.getMongoTemplate().find(new Query(), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.ORDER));
        for(Order temp:rs){
        	if(temp.getStatus().equals("已完成")){
        		sbt.setComplete(sbt.getComplete()+1);
        		sbt.setGet(sbt.getGet().add(temp.getSenderGet()));
        	}
        }
        sbt.setOrderNumber(rs.size());
		sbt.setUnComplete(sbt.getOrderNumber()-sbt.getComplete());
	}

	
}
