package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sunwou.entity.WithdrawalsLog;
import sunwou.mongo.daoimple.WithdrawalsLogDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IWithdrawalsLogService;

@Component
public class WithdrawalsLogServiceimple implements IWithdrawalsLogService{

	@Autowired
	private WithdrawalsLogDaoImple iWithdrawalsLogDao;

	@Override
	public List<WithdrawalsLog> find(QueryObject qo) {
		// TODO Auto-generated method stub
		return iWithdrawalsLogDao.find(qo);
	}

	@Override
	public String add(WithdrawalsLog log) {
		// TODO Auto-generated method stub
		return iWithdrawalsLogDao.add(log);
	}

	@Override
	public int count(QueryObject qo) {
		// TODO Auto-generated method stub
		return iWithdrawalsLogDao.count(qo);
	}
}
