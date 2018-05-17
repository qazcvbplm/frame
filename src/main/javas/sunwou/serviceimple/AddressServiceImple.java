package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import sunwou.entity.Address;
import sunwou.entity.Floor;
import sunwou.exception.MyException;
import sunwou.mongo.daoimple.AddressDaoImple;
import sunwou.mongo.daoimple.FloorDaoImple;
import sunwou.service.IAddressService;
import sunwou.valueobject.AddressParamObejct;

@Component
public class AddressServiceImple implements IAddressService{

	@Autowired
	private AddressDaoImple iAddressDao;
	@Autowired
	private FloorDaoImple floorDao;

	@Override
	public String add(Address address) {
		Floor floor=floorDao.findById(address.getFloorId());
		if(!floor.getName().equals(address.getFloorName())){
			Floor temp=floorDao.getByName(address.getFloorName());
			if(temp!=null){
				address.setFloorId(temp.getName());
			}else{
				throw new MyException("错误请重试");
			}
		}
		return iAddressDao.add(address);
	}

	@Override
	public List<Address> find(AddressParamObejct apo) {
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("userId").is(apo.getUserId())
				,Criteria.where("schoolId").
				is(apo.getSchoolId()),Criteria.where("isDelete").is(false));
		return iAddressDao.getMongoTemplate().find(new Query(c),iAddressDao.getCl());
	}

	@Override
	public int update(Address address) {
		// TODO Auto-generated method stub
		return iAddressDao.updateById(address);
	}

	@Override
	public Address findById(String addressId) {
		// TODO Auto-generated method stub
		return iAddressDao.findById(addressId);
	}
}
