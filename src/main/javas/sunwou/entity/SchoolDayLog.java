package sunwou.entity;

import java.math.BigDecimal;

import sunwou.mongo.util.MongoBaseEntity;

public class SchoolDayLog extends MongoBaseEntity{

	
	private String schoolId;
	
	private BigDecimal totalIn;
	
	private BigDecimal appGet;
	
	private BigDecimal agentGet;
	
	private Integer totalOrderNumber;
	
	private Integer okOrderNumber;
	
	private Integer canelOrderNumber;

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public BigDecimal getTotalIn() {
		return totalIn;
	}

	public void setTotalIn(BigDecimal totalIn) {
		this.totalIn = totalIn;
	}

	public BigDecimal getAppGet() {
		return appGet;
	}

	public void setAppGet(BigDecimal appGet) {
		this.appGet = appGet;
	}

	public BigDecimal getAgentGet() {
		return agentGet;
	}

	public void setAgentGet(BigDecimal agentGet) {
		this.agentGet = agentGet;
	}

	public Integer getTotalOrderNumber() {
		return totalOrderNumber;
	}

	public void setTotalOrderNumber(Integer totalOrderNumber) {
		this.totalOrderNumber = totalOrderNumber;
	}

	public Integer getOkOrderNumber() {
		return okOrderNumber;
	}

	public void setOkOrderNumber(Integer okOrderNumber) {
		this.okOrderNumber = okOrderNumber;
	}

	public Integer getCanelOrderNumber() {
		return canelOrderNumber;
	}

	public void setCanelOrderNumber(Integer canelOrderNumber) {
		this.canelOrderNumber = canelOrderNumber;
	}


	public void add() {
		this.totalIn = new BigDecimal(0);
		this.appGet = new BigDecimal(0);
		this.agentGet = new BigDecimal(0);
		this.totalOrderNumber = 0;
		this.okOrderNumber = 0;
		this.canelOrderNumber = 0;
	}
	
	
}
