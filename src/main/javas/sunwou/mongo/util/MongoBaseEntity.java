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
	
	private String createTime;
	
	private String createDate;
	
	private Boolean isDelete;

    
	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public String getSunwouId() {
		return sunwouId;
	}


	public void setSunwouId(String sunwouId) {
		this.sunwouId = sunwouId;
	}

	
	public void  beforAdd(){
	}
	
	public void beforUpdate(){
		
	}
	
}
