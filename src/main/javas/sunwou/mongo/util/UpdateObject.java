package sunwou.mongo.util;

public class UpdateObject {

	private String tableName;
	
	
	private SetObject sets[];

	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public SetObject[] getSets() {
		return sets;
	}

	public void setSets(SetObject[] sets) {
		this.sets = sets;
	}
	
	
}
