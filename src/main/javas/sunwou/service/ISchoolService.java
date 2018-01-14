package sunwou.service;

import java.util.List;

import sunwou.entity.School;
import sunwou.mongo.util.QueryObject;

public interface ISchoolService {

	String add(School school);

	List<School> find(QueryObject qo);

	School findById(String sunwouId);

}
