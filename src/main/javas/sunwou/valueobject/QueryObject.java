package sunwou.valueobject;

public class QueryObject {

	private String fields[];
	
	private SortObject  sorts[];
	
	private WhereObject wheres[];

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public SortObject[] getSorts() {
		return sorts;
	}

	public void setSorts(SortObject[] sorts) {
		this.sorts = sorts;
	}

	public WhereObject[] getWheres() {
		return wheres;
	}

	public void setWheres(WhereObject[] wheres) {
		this.wheres = wheres;
	}
	
	
}
