package sunwou.service;

import java.util.List;

import sunwou.entity.Address;
import sunwou.valueobject.AddressParamObejct;

public interface IAddressService {

	String add(Address address);

	List<Address> find(AddressParamObejct apo);

	int update(Address address);

}
