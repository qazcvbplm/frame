package sunwou.service;

import java.util.List;

import sunwou.entity.School;
import sunwou.mongo.util.QueryObject;
import sunwou.entity.DayLog;

public interface IDayLogService {

	String add(DayLog dayLogshop);

	List<DayLog> find(QueryObject qo);

	DayLog findById(String id);

	int update(DayLog dayLog);

	int count(QueryObject qo);

}
