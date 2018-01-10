package sunwou.mongo.util;

public class SortObject {

	private String value;
	
	private boolean asc=true;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}
	
	
}
