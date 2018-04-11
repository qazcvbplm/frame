package sunwou.entity;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import sunwou.exception.MyException;
import sunwou.mongo.util.MongoBaseEntity;


public class School extends MongoBaseEntity{

	@NotEmpty(message="学校名字不能为空")
	private String schoolName;//学校名称
	@NotEmpty(message="代理账号不能为空")
	private String schoolUserName;//学校代理账号
	@NotEmpty(message="代理密码不能为空")
	private String schoolPassWord;//学校代理密码
    
    private String indexTopTitle;//首页头部文字
    
    private Integer indexTopDay;//首页头部天数
    
    private BigDecimal money;//学校余额

    private String phone;//代理手机
    
    private BigDecimal senderMoney;//配送员总金额
    @NotNull(message="抽成不能为空")
    private BigDecimal appRate;//App对此学校的抽成
    
    private BigDecimal senderFloorMoney;//配送员未送到楼上时应扣除费用
    
    private Integer senderMax;//配送路程超出此距离需要额外配送费
    
    private Integer senderOutRange;//每超出这个距离就增收配送费
    
    private BigDecimal senderMaxOutMOney;//每超出senderOutRange米的价格
    
    private Integer runMinMOney;//跑腿最低价格
    
    private String takeOutEndRemind;//外卖订单送达床上温馨提醒
    
    private String takeOutNoEndRemind;//外卖订单送达楼下温馨提醒
    
    private String orderTimeOutReming;//订单长时间无人接手温馨提示
    
    

	public String getOrderTimeOutReming() {
		return orderTimeOutReming;
	}

	public void setOrderTimeOutReming(String orderTimeOutReming) {
		this.orderTimeOutReming = orderTimeOutReming;
	}

	public String getTakeOutEndRemind() {
		return takeOutEndRemind;
	}

	public void setTakeOutEndRemind(String takeOutEndRemind) {
		this.takeOutEndRemind = takeOutEndRemind;
	}

	public String getTakeOutNoEndRemind() {
		return takeOutNoEndRemind;
	}

	public void setTakeOutNoEndRemind(String takeOutNoEndRemind) {
		this.takeOutNoEndRemind = takeOutNoEndRemind;
	}

	public Integer getRunMinMOney() {
		return runMinMOney;
	}

	public void setRunMinMOney(Integer runMinMOney) {
		this.runMinMOney = runMinMOney;
	}


	public Integer getSenderOutRange() {
		return senderOutRange;
	}

	public void setSenderOutRange(Integer senderOutRange) {
		this.senderOutRange = senderOutRange;
	}

	public BigDecimal getSenderFloorMoney() {
		return senderFloorMoney;
	}

	public void setSenderFloorMoney(BigDecimal senderFloorMoney) {
		this.senderFloorMoney = senderFloorMoney;
	}

	public Integer getSenderMax() {
		return senderMax;
	}

	public void setSenderMax(Integer senderMax) {
		this.senderMax = senderMax;
	}

	public BigDecimal getSenderMaxOutMOney() {
		return senderMaxOutMOney;
	}

	public void setSenderMaxOutMOney(BigDecimal senderMaxOutMOney) {
		this.senderMaxOutMOney = senderMaxOutMOney;
	}

	public BigDecimal getAppRate() {
		return appRate;
	}

	public void setAppRate(BigDecimal appRate) {
		this.appRate = appRate;
	}

	public BigDecimal getSenderMoney() {
		return senderMoney;
	}

	public void setSenderMoney(BigDecimal senderMoney) {
		this.senderMoney = senderMoney;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

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

	public void add() {
		this.money=new BigDecimal(0);
		this.indexTopTitle = "";
		this.indexTopDay = 0;
		this.senderMoney=new BigDecimal(0);
		this.senderMax=3000;
		this.senderMaxOutMOney=new BigDecimal(1);
		this.senderFloorMoney=new BigDecimal(0.5);
		this.takeOutEndRemind="配送员已穿梭于走道间，将您的餐送至您的宿舍。祝您用餐愉快！";
		this.takeOutNoEndRemind=" 您的餐已放在您的寝室楼下啦，并已返还您{x}配送费到您的零钱，请注意查收！";
		this.orderTimeOutReming="您的外卖已过5分钟还未被商家接手，请联系一下商家或进入小程序进行全额退款。";
	}

	public void update() {
		this.appRate=null;
		this.money=null;
		this.senderMoney=null;
		if(this.schoolPassWord.length()<6){
			throw new MyException("密码不能少于6位");
		}
	}
    
    
}
