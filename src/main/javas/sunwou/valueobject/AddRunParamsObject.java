package sunwou.valueobject;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotEmpty;

public class AddRunParamsObject {

	@NotEmpty(message="参数不能为空")
	private String name;
	@NotEmpty(message="参数不能为空")
	private String phone;
	@NotEmpty(message="参数不能为空")
	private String address;
	@NotEmpty(message="参数不能为空")
	private String subType;
	
	private BigDecimal amount;
	@NotEmpty(message="参数不能为空")
	private String schoolId;
	@NotEmpty(message="参数不能为空")
	private String userId;
	@NotEmpty(message="参数不能为空")
	private String reserveTime;
	
	private String remark;
	
	private String secret;

	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getReserveTime() {
		return reserveTime;
	}

	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
	
}
