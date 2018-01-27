package sunwou.valueobject;

import org.hibernate.validator.constraints.NotEmpty;

public class ShopLoginParamObject {

	@NotEmpty(message="用户名不能为空")
	private String userName;
	@NotEmpty(message="密码不能为空")
	private String passWord;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	
}
