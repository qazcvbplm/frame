package sunwou.service;

import java.math.BigDecimal;
import java.util.List;

import sunwou.entity.School;
import sunwou.mongo.util.QueryObject;
import sunwou.valueobject.SchoolLoginParamObject;

public interface ISchoolService {

	String add(School school);

	List<School> find(QueryObject qo);

	School findById(String sunwouId);

	School login(SchoolLoginParamObject spo);

	int update(School school);

	List<School> findAll();

	int money(String schoolId, BigDecimal amount, boolean add);


}
