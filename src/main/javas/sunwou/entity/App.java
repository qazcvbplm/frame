package sunwou.entity;




import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import sunwou.mongo.util.MongoBaseEntity;

@ApiModel
public class App extends MongoBaseEntity{

	@ApiModelProperty(value="微信提供appid")
	@NotEmpty(message="appid不能为空")
	private String appid;//微信appid
	@ApiModelProperty(value="微信提供秘钥")
	@NotEmpty(message="秘钥不能为空")
	private String secertWX;//微信秘钥
	@ApiModelProperty(value="微信提供商户号")
	@NotEmpty(message="商户号不能为空")
	private String mch_id;//微信商户号
	@ApiModelProperty(value="微信提供支付秘钥")
	@NotEmpty(message="支付秘钥不能为空")
	private String payKeyWX;//微信支付秘钥
	@ApiModelProperty(value="超级管理员账号")
	@NotEmpty(message="管理员账号不能为空")
    private String userName;//用户名
	@ApiModelProperty(value="超级管理员密码")
	@NotEmpty(message="管理员密码不能为空")
    private String passWord;//密码

	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecertWX() {
		return secertWX;
	}

	public void setSecertWX(String secertWX) {
		this.secertWX = secertWX;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getPayKeyWX() {
		return payKeyWX;
	}

	public void setPayKeyWX(String payKeyWX) {
		this.payKeyWX = payKeyWX;
	}

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
