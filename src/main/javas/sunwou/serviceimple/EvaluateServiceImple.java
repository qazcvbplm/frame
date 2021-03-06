package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.Mongo;

import sunwou.entity.Evaluate;
import sunwou.entity.Order;
import sunwou.mongo.daoimple.EvaluateDaoImple;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IEvaluateService;
import sunwou.service.IOrderService;

@Component
public class EvaluateServiceImple implements IEvaluateService{

	
	@Autowired
	private EvaluateDaoImple iEvaluateDao;
	
	@Autowired
	private IOrderService iOrderService;

	@Override
	public String add(Evaluate evaluate) {
		Order order=new Order();
		order.setSunwouId(evaluate.getOrderId());
		order.setPl(true);
		iOrderService.update(order);
		order=iOrderService.findById(evaluate.getOrderId());
		evaluate.setShopId(order.getShopId());
		evaluate.setWaterNumber(order.getWaterNumber()+"");
		// TODO Auto-generated method stub
		return iEvaluateDao.add(evaluate);
	}

	@Override
	public List<Evaluate> find(QueryObject qo) {
		// TODO Auto-generated method stub
		return iEvaluateDao.find(qo);
	}

	@Override
	public int count(QueryObject qo) {
		// TODO Auto-generated method stub
		return iEvaluateDao.count(qo);
	}

	@Override
	public List<Evaluate> findByShop(String shopId) {
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("shopId").is(shopId));
		Query q=new Query(c);
		q.with(new Sort(Direction.DESC, "createTime"));
		return iEvaluateDao.getMongoTemplate().find(q,iEvaluateDao.getCl());
	}
}
