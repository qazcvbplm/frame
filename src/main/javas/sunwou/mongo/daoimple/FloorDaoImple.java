package sunwou.mongo.daoimple;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import sunwou.entity.Floor;
import sunwou.mongo.util.MongoBaseDaoImple;

@Component
public class FloorDaoImple extends MongoBaseDaoImple<Floor>{

	public Floor getByName(String floorName) {
		Criteria c= new Criteria();
		c.andOperator(Criteria.where("isDelete").is(false),Criteria.where("name").is(floorName));
		List<Floor> fs=this.getMongoTemplate().find(new Query(), this.getCl());
		
		return fs.size()>0?fs.get(0):null;
	}

}
