package sunwou.service;

import java.util.List;

import sunwou.entity.OpenTime;

public interface IOpenTimeService {

	String add(OpenTime openTime);

	List<OpenTime> findByShopId(String shopId);

	int update(OpenTime openTime);

}
