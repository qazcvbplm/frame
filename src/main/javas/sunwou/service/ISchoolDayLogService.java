package sunwou.service;

import java.util.List;

import sunwou.entity.School;
import sunwou.entity.SchoolDayLog;

public interface ISchoolDayLogService {

	void everyDay(List<School> schools);

	List<SchoolDayLog> getToDay(String schoolId);
}
