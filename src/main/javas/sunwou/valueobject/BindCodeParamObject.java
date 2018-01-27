package sunwou.valueobject;

import org.hibernate.validator.constraints.NotEmpty;

public class BindCodeParamObject {

	@NotEmpty(message="用户id不能为空")
	private String userId;
	@NotEmpty(message="手机号码不能为空")
	private String phone;
	@NotEmpty(message="验证码不能为空")
	private String code;
	
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	

}
