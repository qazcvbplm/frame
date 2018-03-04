package sunwou.serviceimple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sunwou.entity.Evaluate;
import sunwou.entity.Order;
import sunwou.mongo.dao.IEvaluateDao;
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
}
