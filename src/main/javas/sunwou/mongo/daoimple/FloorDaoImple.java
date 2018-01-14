package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.Floor;
import sunwou.mongo.dao.IFloorDao;
import sunwou.mongo.util.MongoBaseDaoImple;

@Component
public class FloorDaoImple extends MongoBaseDaoImple<Floor> implements IFloorDao{

}
