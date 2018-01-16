package sunwou.valueobject;

import org.hibernate.validator.constraints.NotEmpty;

public class AddressParamObejct {

	@NotEmpty(message="用户id不能为空")
	private String userId;
	@NotEmpty(message="学校id不能为空")
	private String schoolId;

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
	
	
}
