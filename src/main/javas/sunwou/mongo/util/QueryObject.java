package sunwou.mongo.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查询对象
 * @author ops
 */
@ApiModel
public class QueryObject {
	
	@ApiModelProperty(notes="不填")
	private String tableName;
	@ApiModelProperty(notes="{<br/>fields:['你所需要的属性1','你所需要的属性2']<br/>}<br/>(不填则为所有属性)")
	private String fields[];
	@ApiModelProperty(notes="{<br/>sorts:[<br/>{value:'排序属性',asc:true}<br/>]<br/>}")
	private SortObject  sorts[];
	@ApiModelProperty(notes="{<br/>wheres:[<br/>{value:'比较属性',opertionType:'比较类型(equal,lt,lte,gt,gte,like)',opertionValue:'目标值'}<br/>]<br/>}")
	private WhereObject wheres[];
	@ApiModelProperty(notes="{<br/>pages:{currentPage:'当前页码',size:'每页数量'}<br/>}")
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

