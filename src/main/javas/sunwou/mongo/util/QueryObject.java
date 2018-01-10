package sunwou.mongo.util;

/**
 * 查询对象
 * @author ops
 */
public class QueryObject {
	
	private String tableName;

	private String fields[];
	
	private SortObject  sorts[];
	
	private WhereObject wheres[];
	
	private PageObejct pages;
	
	

	public PageObejct getPages() {
		return pages;
	}

	public void setPages(PageObejct pages) {
		this.pages = pages;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

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

