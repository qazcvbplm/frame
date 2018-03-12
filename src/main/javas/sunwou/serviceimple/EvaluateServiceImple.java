package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sunwou.entity.Evaluate;
import sunwou.entity.Order;
import sunwou.mongo.dao.IEvaluateDao;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IEvaluateService;
import sunwou.service.IOrderService;

@Component
public class EvaluateServiceImple implements IEvaluateService{

	
	@Autowired
	private IEvaluateDao iEvaluateDao;
	
	@Autowired
	private IOrderService iOrderService;

	@Override
	public String add(Evaluate evaluate) {
		Order order=new Order();
		order.setSunwouId(evaluate.getOrderId());
		order.setPl(true);
		iOrderService.update(order);
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
}
