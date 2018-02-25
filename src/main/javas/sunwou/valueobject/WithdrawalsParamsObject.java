package sunwou.valueobject;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotEmpty;

public class WithdrawalsParamsObject {

	@NotEmpty(message="参数非空")
	private String schoolId;
	private BigDecimal amount;
	@NotEmpty(message="参数非空")
	private String secert;
	@NotEmpty(message="参数非空")
	private String type;
	
	private String openid;

	
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getSecert() {
		return secert;
	}

	public void setSecert(String secert) {
		this.secert = secert;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
