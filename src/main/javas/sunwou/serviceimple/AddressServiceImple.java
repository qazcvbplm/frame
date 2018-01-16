package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import sunwou.entity.Address;
import sunwou.mongo.dao.IAddressDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.service.IAddressService;
import sunwou.valueobject.AddressParamObejct;

@Component
public class AddressServiceImple implements IAddressService{

	@Autowired
	private IAddressDao iAddressDao;

	@Override
	public String add(Address address) {
		// TODO Auto-generated method stub
		return iAddressDao.add(address);
	}

	@Override
	public List<Address> find(AddressParamObejct apo) {
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("userId").is(apo.getUserId())
				,Criteria.where("schoolId").
				is(apo.getSchoolId()),Criteria.where("isDelete").is(false));
		return iAddressDao.getMongoTemplate().find(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.ADDRESS));
	}

	@Override
	public int update(Address address) {
		// TODO Auto-generated method stub
		return iAddressDao.updateById(address, MongoBaseDaoImple.ADDRESS);
	}
}
