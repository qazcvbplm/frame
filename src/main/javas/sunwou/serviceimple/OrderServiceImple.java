package sunwou.serviceimple;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.github.qcloudsms.httpclient.HTTPException;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import sunwou.entity.Address;
import sunwou.entity.App;
import sunwou.entity.DayLog;
import sunwou.entity.FullCut;
import sunwou.entity.Order;
import sunwou.entity.OrderProduct;
import sunwou.entity.Product;
import sunwou.entity.School;
import sunwou.entity.Sender;
import sunwou.entity.Shop;
import sunwou.entity.User;
import sunwou.exception.MyException;
import sunwou.mongo.daoimple.OrderDaoImple;
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
import sunwou.util.Util;
import sunwou.valueobject.AddRunParamsObject;
import sunwou.valueobject.AddTakeOutParamsObject;
import sunwou.valueobject.SenderStatisticsByTime;
import sunwou.valueobject.ShopStatisticsByTime;
import sunwou.wx.WXUtil;

@Component
public class OrderServiceImple implements IOrderService{

	@Autowired
	private OrderDaoImple iOrderDao;
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
		Shop shop=iShopService.findById(aop.getShopId());
		Address address=iAddressService.findById(aop.getAddressId());
		School school=iSchoolService.findById(shop.getSchoolId());
		if(aop.isTakeout()){
			order=new Order(shop,aop,"外卖订单");
			order.setFloorId(address.getFloorId());
		}
		else{
			order=new Order(shop,aop,"堂食订单");
		}
		//设置收货人信息
		order.setAddress(address);
		List<FullCut> fullCuts=iFullCutService.findByShopId(shop.getSunwouId());
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
				      order.completeDiscount(shop,op);
			}
			//计算商品总价和餐盒费
			order.completeProductAndBox(shop,op);
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
				order.completeDiscount2(shop,fullCut);
			}
		}
		//计算总价
		order.complete(shop,aop.isTakeout(),iAppService);
		//价格核算
		order.app(school,shop);
		//设置订单商品
		order.setOrderProduct(ops);
		return iOrderDao.add(order);
	}
	@Override
	public String addRun(AddRunParamsObject aop, App app) {
		Order order=new Order(aop,"跑腿订单",app);
		User user=iUserService.findById(aop.getUserId());
		School school=iSchoolService.findById(aop.getSchoolId());
		if(order.getTotal().compareTo(new BigDecimal(school.getRunMinMOney()))==-1){
			throw new MyException("不少于"+school.getRunMinMOney()+"元");
		}
		order.setUserImage(user.getAvatarUrl());
		order.setUserName(user.getNickName());
		order.setGender(user.getGender());
		return iOrderDao.add(order);
	}

	@Override
	public Order findById(String sunwouId) {
		// TODO Auto-generated method stub
		return iOrderDao.findById(sunwouId);
	}
	@Override
	public int update(Order o) {
		// TODO Auto-generated method stub
		return iOrderDao.updateById(o);
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
		Criteria.where("status").ne("已取消"),
		Criteria.where("shopId").is(shopId));
		return (int) iOrderDao.getMongoTemplate().count(new Query(c),iOrderDao.getName());
	}
	@Override
	public int paysuccess(Order order) {
		int rs=0;
		if(order.getStatus().equals("待付款")){
			 order.setStatus("待接手");
			 order.setPayTime(TimeUtil.formatDate(new Date(), TimeUtil.TO_S));
			 order.setTimeOut(System.currentTimeMillis()+TIMEOUT);
			 rs+=iOrderDao.updateById(order);
			 //平台余额增加
			 iAppService.money(order.getAppGet(), true);
		}
		return rs;
	}
	@Override
	public int clear() {
		Criteria c=new Criteria();
		 c.andOperator(Criteria.where("status").is("待付款"));
		return  iOrderDao.getMongoTemplate().findAllAndRemove(new Query(c), iOrderDao.getCl()).size();
	}
	@Override
	public List<Order> findBySenderDJS(Sender sender) {
		if(sender.getTakeOutFlag()!=null&&sender.getTakeOutFlag()){
			String[] floorsId=sender.getFloorsId();
			Criteria c=new Criteria();
			c.andOperator(
					Criteria.where("floorId").in(floorsId),
					Criteria.where("shopId").in(sender.getShopsId()),
					Criteria.where("status").is("商家已接手"),
					Criteria.where("type").is("外卖订单"),
					Criteria.where("senderId").exists(false));
			Query query=new Query(c);
	/*		query.fields().include("status").include("shopName").include("shopImage").include("shopAddress").include("type")
			.include("waterNumber")
			.include("address")
			.include("createTime")
			.include("remark").include("sendPrice");*/
			return iOrderDao.getMongoTemplate().find(query, iOrderDao.getCl());
		}else{
			return null; 
		}
	}
	@Override
	public List<Order> findorderRunToday(Sender sender) {
		if(sender.getRunFlag()!=null&&sender.getRunFlag()){
			String[] floorsId=sender.getFloorsId();
			Criteria c=new Criteria();
			c.andOperator(
					Criteria.where("floorId").in(floorsId),
					Criteria.where("status").is("待接手"),
					Criteria.where("type").is("跑腿订单"),
					Criteria.where("senderId").exists(false));
			return iOrderDao.getMongoTemplate().find(new Query(c), iOrderDao.getCl());
		}else{
			return null;
		}
	}
	@Override
	public List<Order> findByShopDJS(Shop shop) {
		Criteria c=new Criteria();
		c.andOperator(
				Criteria.where("shopId").is(shop.getSunwouId()),
				Criteria.where("status").is("待接手"));
		return iOrderDao.getMongoTemplate().find(new Query(c), iOrderDao.getCl());
	}
	@Override
	public int toDaySchoolOrderCount(String schoolId) {
		Criteria c=new Criteria();
		c.andOperator(
				Criteria.where("schoolId").is(schoolId),
				Criteria.where("completeTime").is(TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY)),
				Criteria.where("status").is("已完成"));
		return (int) iOrderDao.getMongoTemplate().count(new Query(c), iOrderDao.getName());
	}
	@Override
	public BigDecimal schoolToDayTransactionMoney(String schoolId) {
		Criteria c=new Criteria();
		c.andOperator(
				Criteria.where("schoolId").is(schoolId),
				Criteria.where("completeTime").is(TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY)),
				Criteria.where("status").is("已完成"));
		Query query=new Query(c);
		query.fields().include("total");
		List<Order> rs=iOrderDao.getMongoTemplate().find(query, iOrderDao.getCl());
		BigDecimal total=new BigDecimal("0");
		for(Order o:rs){
			total=total.add(o.getTotal());
		}
		total=total.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		return total;
	}
	@Override
	public int senderAcceptOrder(Order order, Sender sender,App app,User user) {
		User SenderUser=iUserService.findById(sender.getUserId());
		sender.setCreateDate(SenderUser.getAvatarUrl());
		order.setSenderMsg(sender);
		int rs=update(order);
		Map<String, String> map = new HashMap<>();
		if(order.getType().equals("外卖订单")){
			map.put("keyword2", order.getOrderProduct().get(0).getProduct().getName()+"等商品");
		}
		if(order.getType().equals("跑腿订单")){
			map.put("keyword2", order.getRemark());
		}
		//模板消息通知
		map.put("appid", app.getAppid());
		map.put("secert", app.getSecertWX());
		map.put("template_id", "ydpYVe0uEYYsuegZhAjeCjBlHHYvfH_ycLJR8qq1DUM");
		map.put("touser", user.getOpenid());
		map.put("form_id", order.getPrepareId());
		map.put("keywordcount", "7");
		map.put("keyword1", TimeUtil.formatDate(new Date(), TimeUtil.TO_S));
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
		return iOrderDao.getMongoTemplate().find(new Query(c).with(new Sort(Direction.DESC, "createTime")), iOrderDao.getCl());
	}
	@Override
	public List<Order> findorderToday(Sender sender) {
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("senderId").is(sender.getSunwouId()),Criteria.where("createDate").is(TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY)));
		List<Order> rs=iOrderDao.getMongoTemplate().find(new Query(c),iOrderDao.getCl());
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
                List<Order> rs=iOrderDao.getMongoTemplate().find(new Query(c), iOrderDao.getCl());
                for(Order temp:rs){
                	sbt.setTotal(sbt.getTotal().add(temp.getTotal()));
                	sbt.setShopGet(sbt.getShopGet().add(temp.getShopGet()));
                	sbt.setBoxPrice(sbt.getBoxPrice().add(temp.getSendPrice()));
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
        List<Order> rs=iOrderDao.getMongoTemplate().find(new Query(c), iOrderDao.getCl());
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
        		Criteria.where("status").is("商家已接手"),
        		Criteria.where("type").is("堂食订单"),
        		Criteria.where("timeOut").lte(System.currentTimeMillis()));
        List<Order> rs=iOrderDao.getMongoTemplate().find(new Query(c), iOrderDao.getCl());
        for(Order temp:rs){
        	//完成逻辑
        	takeOutComplete(temp,null);
        }
		return rs;
	}
	@Override
	public void shopDayLog(String day, DayLog dayLogshop) {
		Criteria c=new Criteria();
		c.andOperator(
				Criteria.where("shopId").is(dayLogshop.getSelfId()),
				Criteria.where("completeTime").is(day)
				);
		c.orOperator(Criteria.where("status").is("已完成"),
				Criteria.where("status").is("已取消"));
		Query query=new Query(c);
		List<Order> orders=iOrderDao.getMongoTemplate().find(query, iOrderDao.getCl());
		for(Order temp:orders){
			dayLogshop.addOrder(temp);
		}
	}
	@Override
	public void senderDayLog(String day, DayLog dayLogsender) {
		Criteria c=new Criteria();
		c.andOperator(
				Criteria.where("senderId").is(dayLogsender.getSelfId()),
				Criteria.where("completeTime").is(day)
				);
		c.orOperator(Criteria.where("status").is("已完成"),
				Criteria.where("status").is("已取消"));
		Query query=new Query(c);
		List<Order> orders=iOrderDao.getMongoTemplate().find(query, iOrderDao.getCl());
		for(Order temp:orders){
			dayLogsender.addRunOrder(temp);
		}
		
	}
	@Override
	public int takeOutComplete(Order order,HttpServletRequest request) {
		int rs=0;
		App app=iAppService.find();
		School school =iSchoolService.findById(order.getSchoolId());
		User user=iUserService.findById(order.getUserId());
		//发送模板消息
		order.EndMB(app,school,user,iUserService);
		//订单完成后的计算所得
		order.complete();
		//更新订单
		if((rs=update(order))==1){
			//如果未上楼
			if(order.getEnd()!=null&&!order.getEnd()){
				iUserService.Money(user.getSunwouId(), school.getSenderFloorMoney(),true);
			}
			//配送员增加配送费
			if(order.getType().equals("外卖订单")||order.getType().equals("跑腿订单")){
				iSenderService.money(order.getSenderId(), order.getSenderGet(), true);
			}
			//学校增加余额
			iSchoolService.money(order.getSchoolId(), order.getTotal().subtract(order.getAppGet()), true);
			//学校增加配送员余额
			iSchoolService.SenderMoney(order.getSchoolId(), order.getSenderGet(), true);
			//用户增加积分
			iUserService.addSource(order.getUserId(),order.getTotal().intValue(),"加");
			//销量增加
			if(order.getType().equals("外卖订单")||order.getType().equals("堂食订单")){
				iProductService.salesadd(order.getOrderProduct());
			}
		}
		return rs;
	}
	@Override
	public int cancel(Order order) {
		order.setStatus("已取消");
		order.setCompleteTime(TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY));
		int rs=update(order);
		iSchoolService.money(order.getSchoolId(), order.getAppGet(), false);
		return rs;
	}
	@Override
	public void takeoutRemind() {
		App app=iAppService.find();
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("status").is("待接手"),Criteria.where("type").is("外卖订单"));
		Query query=new Query(c);
		List<Order> orders=iOrderDao.getMongoTemplate().find(query, iOrderDao.getCl());
		for(Order o:orders){
			School school=iSchoolService.findById(o.getSchoolId());
			User user=iUserService.findById(o.getUserId());
			Shop shop=iShopService.findById(o.getShopId());
			long time=TimeUtil.parse(o.getPayTime(), TimeUtil.TO_S).getTime();
			if(System.currentTimeMillis()-time>3*60*1000){
				if(o.getRemind()==null){
					o.remindUser(app,school,user,shop);
					o.setRemind(true);
					iOrderDao.updateById(o);
					try {
						Util.qqsms(1400069674, "7089ae8c9a950999c776783aa0d64d67", school.getPhone(), 111862,shop.getShopName()+","+shop.getShopPhone(),null);
					} catch (JSONException | HTTPException | IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	@Override
	public DayLog tj(QueryObject qo) {
		long start=System.currentTimeMillis();
		DayLog dayLog=new DayLog();
		/*	List<Order> rs=iOrderDao.find(qo);
		dayLog.setTotalOrderNumber(rs.size());
		dayLog.setTotalIn(new BigDecimal(0));
		dayLog.setSelfGet(new BigDecimal(0));
		dayLog.setBoxPrice(new BigDecimal(0));
		dayLog.setSendPrice(new BigDecimal(0));
		dayLog.setProductPrice(new BigDecimal(0));
		for(Order temp:rs){
		     dayLog.setTotalIn(dayLog.getTotalIn().add(temp.getTotal()));	
		     if(temp.getType().equals("外卖订单")||temp.getType().equals("堂食订单")){
		    	 dayLog.setSelfGet(dayLog.getSelfGet().add(temp.getShopGet()));
		    	 dayLog.setBoxPrice(dayLog.getBoxPrice().add(temp.getBoxPrice()));
		    	 dayLog.setProductPrice(dayLog.getProductPrice().add(temp.getProductPrice()));
		     }
		     dayLog.setSendPrice(dayLog.getSendPrice().add(temp.getSendPrice()));
		}*/
		String map="function(){emit('1',this)}";
		String reduce="function(key,values){"
				+ "var rs={total:0.0,boxPrice:0.0,sendPrice:0.0,productPrice:0.0,shopGet:0.0};"
				+ "for(var i=0;i<values.length;i++){"
				+ "rs.total+=values[i].total*1.00;"
				+ "rs.sendPrice+=values[i].sendPrice*1.00;"
				+ "if(values[i].type!=\"跑腿订单\"){"
				+ "rs.boxPrice+=values[i].boxPrice*1.00;"
				+ "rs.productPrice+=values[i].productPrice*1.00;"
				+ "rs.shopGet+=values[i].shopGet*1.00;"
				+ "}"
				+ "}"
				+ "return rs;}";
	    MapReduceResults<Map> rs = iOrderDao.getMongoTemplate().mapReduce(new Query(iOrderDao.getCriteria(qo)), iOrderDao.getName(), map, reduce, Map.class);
	    Map rss=(Map) rs.iterator().next().get("value");
	    dayLog.setTotalOrderNumber((int)rs.getCounts().getInputCount());
		dayLog.setTotalIn(new BigDecimal(rss.get("total")+""));
		dayLog.setBoxPrice(new BigDecimal(rss.get("boxPrice")+""));
		dayLog.setSendPrice(new BigDecimal(rss.get("sendPrice")+""));
		dayLog.setProductPrice(new BigDecimal(rss.get("productPrice")+""));
		dayLog.setSelfGet(new BigDecimal(rss.get("shopGet")+""));
		System.out.println(System.currentTimeMillis()-start);
		return dayLog;
	}
	
	public static final long TimeLong=20*60*60*1000;
/*	public int completeTimeLong() {
		App app=iAppService.find();
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("type").is("外卖订单"),Criteria.where("status").is("配送员已接手"));
		List<Order> orders=iOrderDao.getMongoTemplate().find(new Query(c).with(new Sort(Direction.DESC, "createTime")),iOrderDao.getCl());
		for(Order temp:orders){
			if(temp.getAppRate()==null)
				temp.setAppRate(app.getOrderRate());
			School school =iSchoolService.findById(temp.getSchoolId());
			Shop shop=iShopService.findById(temp.getShopId());
			if(temp.getShopRate()==null&&temp.getType().equals("外卖订单")){
				temp.setShopRate(shop.getRate());
			}
			temp.setSenderFloorMoney(temp.getSenderFloorMoney());
			temp.setEnd(false);
			long payTime=TimeUtil.parse(temp.getPayTime(), TimeUtil.TO_S).getTime();
			if(System.currentTimeMillis()-payTime>TimeLong){
				try {
					takeOutComplete(temp, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}*/


	
}
