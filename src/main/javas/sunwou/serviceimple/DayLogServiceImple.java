package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sunwou.entity.DayLog;
import sunwou.mongo.dao.IDayLogDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IDayLogService;

@Component
public class DayLogServiceImple implements IDayLogService{

	
	@Autowired
	private IDayLogDao iDayLogDao;

	@Override
	public String add(DayLog dayLogshop) {
		// TODO Auto-generated method stub
		return iDayLogDao.add(dayLogshop);
	}

	@Override
	public List<DayLog> find(QueryObject qo) {
		// TODO Auto-generated method stub
		return iDayLogDao.find(qo);
	}

	@Override
	public DayLog findById(String id) {
		// TODO Auto-generated method stub
		return iDayLogDao.findById(id, MongoBaseDaoImple.DAYLOG);
	}

	@Override
	public int update(DayLog dayLog) {
		// TODO Auto-generated method stub
		return iDayLogDao.updateById(dayLog, MongoBaseDaoImple.DAYLOG);
	}

	@Override
	public int count(QueryObject qo) {
		// TODO Auto-generated method stub
		return iDayLogDao.count(qo);
	}


}
