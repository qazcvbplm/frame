package sunwou.entity;

import java.math.BigDecimal;
import java.util.Date;

import sunwou.mongo.util.MongoBaseEntity;
import sunwou.util.TimeUtil;

public class User extends MongoBaseEntity{

	//微信小程序唯一标识
	private String openid;
	//用户头像
	private String avatarUrl;
	//用户昵称
	private String nickName;
	//省份
	private String province;
	//性别
	private String gender;
	//城市
	private String city;
	//会员标识
	private Boolean vipFlag;
	//实名标志
	private Boolean authenticationFlag;
	//配送员标志
	private Boolean senderFlag;
	//商家标志
	private Boolean shoperFlag;
	//积分
	private Integer source;
	//余额
	private BigDecimal money;
	//用户手机号码
	private String phone;
	//上次登录时间
	private String lastLoginTime;
	
	
	public User() {
		super();
	}
	public User(String openid) {
		this.openid=openid;
		this.source=0;
		this.money=new BigDecimal(0.00);
		this.lastLoginTime=TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY);
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Boolean getVipFlag() {
		return vipFlag;
	}
	public void setVipFlag(Boolean vipFlag) {
		this.vipFlag = vipFlag;
	}
	public Boolean getAuthenticationFlag() {
		return authenticationFlag;
	}
	public void setAuthenticationFlag(Boolean authenticationFlag) {
		this.authenticationFlag = authenticationFlag;
	}
	public Boolean getSenderFlag() {
		return senderFlag;
	}
	public void setSenderFlag(Boolean senderFlag) {
		this.senderFlag = senderFlag;
	}
	public Boolean getShoperFlag() {
		return shoperFlag;
	}
	public void setShoperFlag(Boolean shoperFlag) {
		this.shoperFlag = shoperFlag;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	
	
}
