package sunwou.entity;




import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import sunwou.mongo.util.MongoBaseEntity;
import sunwou.util.TimeUtil;
import sunwou.wx.WXUtil;

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
	@ApiModelProperty(value="每笔订单抽成")
	@NotNull(message="抽成不能为空")
    private BigDecimal orderRate;
	
	private BigDecimal money;
	
	private BigDecimal total;
	
	private String apkVersion;
	
	
	public String getApkVersion() {
		return apkVersion;
	}

	public void setApkVersion(String apkVersion) {
		this.apkVersion = apkVersion;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getOrderRate() {
		return orderRate;
	}

	public void setOrderRate(BigDecimal orderRate) {
		this.orderRate = orderRate;
	}

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

	public void sendMS(User user,Order order) {
		Map<String, String> map = new HashMap<>();
		map.put("appid", getAppid());
		map.put("secert", getSecertWX());
		map.put("template_id", "W_SD2f3TJDEMw5FYLgb9PrZh6cbLFrRWK4kJUg8w5UI");
		map.put("touser", user.getOpenid());
		map.put("form_id", order.getPrepareId());
		map.put("keywordcount", "7");
		map.put("keyword1", TimeUtil.formatDate(new Date(), TimeUtil.TO_S));
		map.put("keyword2", order.getOrderProduct().get(0).getProduct().getName()+"等商品");
		map.put("keyword3", order.getWaterNumber()+"");
		map.put("keyword4", order.getReserveTime());
		map.put("keyword5", order.getShopName());
		map.put("keyword6", order.getShopAddress());
		WXUtil.snedM(map);
		
	}
    
    
}
