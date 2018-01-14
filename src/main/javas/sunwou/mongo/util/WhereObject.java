package sunwou.mongo.util;

public  class WhereObject {

	private String value;
	
	private String opertionType;
	
	private Object opertionValue;
	
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

	public Object getOpertionValue() {
		return opertionValue;
	}

	public void setOpertionValue(Object opertionValue) {
		this.opertionValue = opertionValue;
	}

	
	
	
}