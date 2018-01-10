package sunwou.mongo.util;

public  class WhereObject {

	private String value;
	
	private String opertionType;
	
	private String opertionValue;
	
	private boolean and=true;
	
	

	public boolean isAnd() {
		return and;
	}

	public void setAnd(boolean and) {
		this.and = and;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOpertionType() {
		return opertionType;
	}

	public void setOpertionType(String opertionType) {
		this.opertionType = opertionType;
	}

	public String getOpertionValue() {
		return opertionValue;
	}

	public void setOpertionValue(String opertionValue) {
		this.opertionValue = opertionValue;
	}
	
	
}