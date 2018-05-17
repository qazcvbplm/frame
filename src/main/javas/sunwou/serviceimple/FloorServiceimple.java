package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sunwou.entity.Floor;
import sunwou.mongo.daoimple.FloorDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IFloorService;

@Component
public class FloorServiceimple implements IFloorService {

	@Autowired
	private FloorDaoImple iFloorDao;

	@Override
	public String add(Floor floor) {
		// TODO Auto-generated method stub
		return iFloorDao.add(floor);
	}

	@Override
	public List<Floor> find(QueryObject qo) {
		return iFloorDao.find(qo);
	}

	@Override
	public int update(Floor floor) {
		// TODO Auto-generated method stub
		return iFloorDao.updateById(floor);
	}

	@Override
	public int count(QueryObject qo) {
		// TODO Auto-generated method stub
		return iFloorDao.count(qo);
	}

	@Override
	public Floor findById(String fid) {
		// TODO Auto-generated method stub
		return iFloorDao.findById(fid);
	}
}
