package sunwou.entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import sunwou.mongo.util.MongoBaseEntity;

public class Address extends MongoBaseEntity{

	@NotEmpty(message="用户id不能为空")
	private String userId;
	@NotEmpty(message="学校id不能为空")
	private String schoolId;
	@NotEmpty(message="学校名字不能为空")
	private String schoolName;
	@NotEmpty(message="楼栋id不能为空")
	private String floorId;
	@NotEmpty(message="楼栋名字不能为空")
	private String floorName;
	@NotEmpty(message="联系人名字不能为空")
	private String concatName;
	@NotEmpty(message="联系人电话不能为空")
	private String concatPhone;
	@NotEmpty(message="详细地址不能为空")
	private String detail;

	
	public Address() {
		super();
	}

	public Address(String name, String phone, String address) {
		this.concatName=name;
		this.concatPhone=phone;
		this.detail=address;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getConcatName() {
		return concatName;
	}

	public void setConcatName(String concatName) {
		this.concatName = concatName;
	}

	public String getConcatPhone() {
		return concatPhone;
	}

	public void setConcatPhone(String concatPhone) {
		this.concatPhone = concatPhone;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
	
	
}
