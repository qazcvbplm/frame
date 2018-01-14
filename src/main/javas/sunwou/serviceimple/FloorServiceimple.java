package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sunwou.entity.Floor;
import sunwou.mongo.dao.IFloorDao;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IFloorService;

@Component
public class FloorServiceimple implements IFloorService {

	@Autowired
	private IFloorDao iFloorDao;

	@Override
	public String add(Floor floor) {
		// TODO Auto-generated method stub
		return iFloorDao.add(floor);
	}

	@Override
	public List<Floor> find(QueryObject qo) {
		return iFloorDao.find(qo);
	}
}
