package sunwou.entity;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import sunwou.mongo.util.MongoBaseEntity;

public class Apply extends MongoBaseEntity{

	@NotEmpty(message="用户id不能为空")
	private String userId;
	@NotEmpty(message="学校id不能为空")
	private String schoolId;
	private String type;
	@NotEmpty(message="申请内容不能为空")
	private String content;
	
	private String status;
	@NotEmpty(message="模板id不能为空")
	private String formid;
	
	

	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
