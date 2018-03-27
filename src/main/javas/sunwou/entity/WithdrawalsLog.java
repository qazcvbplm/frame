package sunwou.entity;

import java.math.BigDecimal;

import sunwou.mongo.util.MongoBaseEntity;

public class WithdrawalsLog extends MongoBaseEntity{

	private String schoolId;
	
	private String senderId;
	
	private String type;
	
	private BigDecimal amount;

	private String name;
	
	private String schoolName;
	
	private BigDecimal sxf;
	
	
	
	public BigDecimal getSxf() {
		return sxf;
	}

	public void setSxf(BigDecimal sxf) {
		this.sxf = sxf;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WithdrawalsLog() {
		super();
	}

	public WithdrawalsLog(String schoolId, String senderId, String type, BigDecimal amount,String schoolName,BigDecimal sxf) {
		super();
		this.schoolId = schoolId;
		this.senderId = senderId;
		this.type = type;
		this.amount = amount;
		this.schoolName=schoolName;
		this.sxf=sxf;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
}
