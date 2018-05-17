package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sunwou.entity.DayLog;
import sunwou.mongo.daoimple.DayLogDaoImple;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IDayLogService;

@Component
public class DayLogServiceImple implements IDayLogService{

	
	@Autowired
	private DayLogDaoImple iDayLogDao;

	@Override
	public String add(DayLog dayLogshop) {
		// TODO Auto-generated method stub
		if(dayLogshop.getType().equals("配送员跑腿日志")&&dayLogshop.getTotalOrderNumber()==0){
			return null;
		}
		else{
			return iDayLogDao.add(dayLogshop);
		}
	}

	@Override
	public List<DayLog> find(QueryObject qo) {
		// TODO Auto-generated method stub
		return iDayLogDao.find(qo);
	}

	@Override
	public DayLog findById(String id) {
		// TODO Auto-generated method stub
		return iDayLogDao.findById(id);
	}

	@Override
	public int update(DayLog dayLog) {
		// TODO Auto-generated method stub
		return iDayLogDao.updateById(dayLog);
	}

	@Override
	public int count(QueryObject qo) {
		// TODO Auto-generated method stub
		return iDayLogDao.count(qo);
	}


}
