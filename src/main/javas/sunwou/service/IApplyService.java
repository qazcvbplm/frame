package sunwou.service;

import sunwou.entity.Apply;
import sunwou.valueobject.ApplyParamsObject;

public interface IApplyService {

	String add(Apply apply);

	int update(ApplyParamsObject apo);

}
