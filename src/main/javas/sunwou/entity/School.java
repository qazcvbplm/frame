package sunwou.entity;

import org.hibernate.validator.constraints.NotEmpty;

import sunwou.mongo.util.MongoBaseEntity;


public class School extends MongoBaseEntity{

	@NotEmpty(message="学校名字不能为空")
	private String schoolName;//学校名称
	@NotEmpty(message="代理账号不能为空")
	private String schoolUserName;//学校代理账号
	@NotEmpty(message="代理密码不能为空")
	private String schoolPassWord;//学校代理密码
	
    private Boolean settledFlag;//入驻功能是否开启
    
    private String settledRemind;//入驻功能关闭时提醒内容
    
    private Boolean acceptFlag;//接单功能是否开启
    
    private String acceptRemind;//接单功能关闭时提醒内容

    private Boolean signFlag;//签到功能是否开启
    
    private String signRemind;//签到功能关闭时提醒内容
    
    private Boolean sourceShopFlag;//积分商城是否开启
    
    private String sourceRemind;//积分功能关闭时提醒内容
    
    private Boolean vipFlag;//会员卡购买是否开启
    
    private String vipRemind;//会员卡购买关闭时提醒文字
    
    private Boolean withdrawalsFlag;//提现功能是否开启
    
    private Boolean withdrawalsRemind;//提现功能是关闭时提醒
    
    private String indexTopTitle;//首页头部文字
    
    private Integer indexTopDay;//首页头部天数

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolUserName() {
		return schoolUserName;
	}

	public void setSchoolUserName(String schoolUserName) {
		this.schoolUserName = schoolUserName;
	}

	public String getSchoolPassWord() {
		return schoolPassWord;
	}

	public void setSchoolPassWord(String schoolPassWord) {
		this.schoolPassWord = schoolPassWord;
	}

	public Boolean getSettledFlag() {
		return settledFlag;
	}

	public void setSettledFlag(Boolean settledFlag) {
		this.settledFlag = settledFlag;
	}

	public String getSettledRemind() {
		return settledRemind;
	}

	public void setSettledRemind(String settledRemind) {
		this.settledRemind = settledRemind;
	}

	public Boolean getAcceptFlag() {
		return acceptFlag;
	}

	public void setAcceptFlag(Boolean acceptFlag) {
		this.acceptFlag = acceptFlag;
	}

	public String getAcceptRemind() {
		return acceptRemind;
	}

	public void setAcceptRemind(String acceptRemind) {
		this.acceptRemind = acceptRemind;
	}

	public Boolean getSignFlag() {
		return signFlag;
	}

	public void setSignFlag(Boolean signFlag) {
		this.signFlag = signFlag;
	}

	public String getSignRemind() {
		return signRemind;
	}

	public void setSignRemind(String signRemind) {
		this.signRemind = signRemind;
	}

	public Boolean getSourceShopFlag() {
		return sourceShopFlag;
	}

	public void setSourceShopFlag(Boolean sourceShopFlag) {
		this.sourceShopFlag = sourceShopFlag;
	}

	public String getSourceRemind() {
		return sourceRemind;
	}

	public void setSourceRemind(String sourceRemind) {
		this.sourceRemind = sourceRemind;
	}

	public Boolean getVipFlag() {
		return vipFlag;
	}

	public void setVipFlag(Boolean vipFlag) {
		this.vipFlag = vipFlag;
	}

	public String getVipRemind() {
		return vipRemind;
	}

	public void setVipRemind(String vipRemind) {
		this.vipRemind = vipRemind;
	}

	public Boolean getWithdrawalsFlag() {
		return withdrawalsFlag;
	}

	public void setWithdrawalsFlag(Boolean withdrawalsFlag) {
		this.withdrawalsFlag = withdrawalsFlag;
	}

	public Boolean getWithdrawalsRemind() {
		return withdrawalsRemind;
	}

	public void setWithdrawalsRemind(Boolean withdrawalsRemind) {
		this.withdrawalsRemind = withdrawalsRemind;
	}

	public String getIndexTopTitle() {
		return indexTopTitle;
	}

	public void setIndexTopTitle(String indexTopTitle) {
		this.indexTopTitle = indexTopTitle;
	}

	public Integer getIndexTopDay() {
		return indexTopDay;
	}

	public void setIndexTopDay(Integer indexTopDay) {
		this.indexTopDay = indexTopDay;
	}
    
    
}