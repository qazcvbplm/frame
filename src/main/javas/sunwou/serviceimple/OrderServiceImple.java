package sunwou.serviceimple;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import sunwou.entity.Address;
import sunwou.entity.App;
import sunwou.entity.FullCut;
import sunwou.entity.Order;
import sunwou.entity.OrderProduct;
import sunwou.entity.Product;
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
import sunwou.util.TimeUtil;
import sunwou.valueobject.AddTakeOutParamsObject;

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
			 order.setWaterNumber(waternumber()+1);
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
	
}
