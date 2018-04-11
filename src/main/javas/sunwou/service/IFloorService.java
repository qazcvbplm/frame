package sunwou.service;

import java.util.List;

import sunwou.entity.Category;
import sunwou.entity.Floor;
import sunwou.mongo.util.QueryObject;

public interface IFloorService {

	String add(Floor floor);

	List<Floor> find(QueryObject qo);

	int update(Floor floor);

	int count(QueryObject qo);

	Floor findById(String fid);

}
