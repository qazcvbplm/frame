package sunwou.entity;

import java.math.BigDecimal;
import java.util.Date;

import sunwou.mongo.util.MongoBaseEntity;
import sunwou.util.TimeUtil;
import sunwou.util.Util;

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
	//实名标志
	private Boolean authenticationFlag;
	//配送员标志
	private Boolean senderFlag;
	//商家标志
	private Boolean shoperFlag;
	//兼职发布者标志
	private Boolean offerFlag;
	//积分
	private Integer source;
	//余额
	private BigDecimal money;
	//用户手机号码
	private String phone;
	//上次登录时间
	private String lastLoginTime;
	//学校id
	private String schoolId;
	//学校名字
	private String schoolName;
	//楼栋id
	private String floorId;
	//默认地址id
	private String addressId;
	//支付密码
	private String payPass;
	//指纹id
	private String fingerprintId;
	
	
	
	
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getPayPass() {
		return payPass;
	}
	public void setPayPass(String payPass) {
		this.payPass = payPass;
	}
	public String getFingerprintId() {
		return fingerprintId;
	}
	public void setFingerprintId(String fingerprintId) {
		this.fingerprintId = fingerprintId;
	}
	public void update(){
		if(this.source!=null||this.money!=null)
		{
			this.source=null;
			this.money=null;
            Util.outLog(this.getSunwouId()+"非法操作更新敏感信息");			
		}
	}
	public Boolean getOfferFlag() {
		return offerFlag;
	}
	public void setOfferFlag(Boolean offerFlag) {
		this.offerFlag = offerFlag;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getFloorId() {
		return floorId;
	}
	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}
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
