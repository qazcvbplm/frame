package sunwou.entity;

import org.hibernate.validator.constraints.NotEmpty;

import sunwou.mongo.util.MongoBaseEntity;

public class Floor extends MongoBaseEntity{

	@NotEmpty(message="楼栋名字不能为空")
	private String name;
	@NotEmpty(message="学校id不能为空")
	private String schoolId;
	@NotEmpty(message="经度不能为空")
	private String lng;
	@NotEmpty(message="纬度不能为空")
	private String lat;
	
	

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
	
}
