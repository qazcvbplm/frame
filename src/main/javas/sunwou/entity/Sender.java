package sunwou.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotEmpty;

import sunwou.mongo.util.MongoBaseEntity;

public class Sender extends MongoBaseEntity{
	
	@NotEmpty(message="用户id不能为空")
	private String userId;
	@NotEmpty(message="学校id不能为空")
	private String schoolId;
	private String type;
	
	private String status;
	@NotEmpty(message="模板id不能为空")
	private String formid;
	
	private String realName;
	
	private String sex;
	
	private String floors[];
	
	private BigDecimal rate;
	
	private BigDecimal total;
	
	
	

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String[] getFloors() {
		return floors;
	}

	public void setFloors(String[] floors) {
		this.floors = floors;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void update() {
		this.total=null;
	}
	
	
}
