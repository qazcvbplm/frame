package sunwou.mongo.util;



import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;



/**
 * 实体类父类
 * @author onepieces
 */
@Document
public class MongoBaseEntity {

	@Id
	private String sunwouId;
	
	private boolean isDelete=false;
	
	private String createTime;
	
	private String lastUpdateTime;
	
	private String deleteTime;
	
    private String sort;
    
    private String createDate;

    @Transient
    private String where;
    
    
    
	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(String deleteTime) {
		this.deleteTime = deleteTime;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}


	public String getSunwouId() {
		return sunwouId;
	}


	public void setSunwouId(String sunwouId) {
		this.sunwouId = sunwouId;
	}

	
}
