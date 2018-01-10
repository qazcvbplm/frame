package sunwou.entity;


import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotEmpty;

import sunwou.mongo.util.MongoBaseEntity;
import sunwou.util.StringUtil;

public class App extends MongoBaseEntity{

	private String appidWX;//微信appid
	
	private String appidMY;//支付宝appid
	
	private String appid;//appid
	
	private String secertWX;//微信秘钥
	
	private String mch_id;//微信商户号
	
	private String payKeyWX;//微信支付秘钥
	
    private String privatekey;//支付宝私钥
    
    private String publickey;//支付宝公钥
    
	@NotEmpty(message="描述不能为农")
    private String appDescribe;//应用程序的描述
	
	private BigDecimal vip;
	
	
	public BigDecimal getVip() {
		return vip;
	}

	public void setVip(BigDecimal vip) {
		this.vip = vip;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public App(String appid2) {
		this.setSunwouId(appid2);
	}
	
	public App() {
		super();
	}

	public boolean check(){
		boolean result=true;
		if(!StringUtil.isEmpty(appidWX))
		{
			if(StringUtil.isEmpty(secertWX))
				result=false;
		}
		if(!StringUtil.isEmpty(appidMY))
		{
			if(StringUtil.isEmpty(privatekey)||StringUtil.isEmpty(publickey))
				result=false;
		}
		if(StringUtil.isEmpty(appidWX)&&StringUtil.isEmpty(appidMY)&&StringUtil.isEmpty(appid))
		{
			result=false;
		}
		return result;
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
	public String getPrivatekey() {
		return privatekey;
	}
	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}
	public String getPublickey() {
		return publickey;
	}
	public void setPublickey(String publickey) {
		this.publickey = publickey;
	}
	public String getAppDescribe() {
		return appDescribe;
	}
	public void setAppDescribe(String appDescribe) {
		this.appDescribe = appDescribe;
	}

	public String getAppidWX() {
		return appidWX;
	}

	public void setAppidWX(String appidWX) {
		this.appidWX = appidWX;
	}

	public String getAppidMY() {
		return appidMY;
	}

	public void setAppidMY(String appidMY) {
		this.appidMY = appidMY;
	}
    
    
	
}
