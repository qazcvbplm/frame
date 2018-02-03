package sunwou.service;

import java.util.List;

import sunwou.entity.FullCut;

public interface IFullCutService {

	String add(FullCut fullCut);

	List<FullCut> findByShopId(String shopId);

	int update(FullCut fullCut);

}
