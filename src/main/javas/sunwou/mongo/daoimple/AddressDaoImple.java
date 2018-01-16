package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.Address;
import sunwou.mongo.dao.IAddressDao;
import sunwou.mongo.util.MongoBaseDaoImple;

@Component
public class AddressDaoImple extends MongoBaseDaoImple<Address> implements IAddressDao{

}
