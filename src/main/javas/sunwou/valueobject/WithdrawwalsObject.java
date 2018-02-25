package sunwou.valueobject;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;


public class WithdrawwalsObject {
	 //String rs=WeChatPayUtil.transfers(request,payId, sender.getMoney(), sender.getPhone(), user.getOpenid());
	//public static String pay(String name,String bankNumber,String bank_code,String amount,String desc,String partner_trade_no) 
	
	private HttpServletRequest request;
	
	private String schoolId;
	
	private String payId;
	
	private BigDecimal amount;
	
	private String phone;
	
	private String openid;
	
	private String name;
	
	private String bankNumber;
	
	private String bamk_code;
	
	private String desc;
	
	private String type;
	
	private String bz;
	
	

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public String getBamk_code() {
		return bamk_code;
	}

	public void setBamk_code(String bamk_code) {
		this.bamk_code = bamk_code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	
	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public WithdrawwalsObject(HttpServletRequest request,String schoolId, String payId, BigDecimal amount, String phone, String openid,String bz,String type) {
		super();
		this.request = request;
		this.payId = payId;
		this.amount = amount;
		this.phone = phone;
		this.openid = openid;
		this.bz=bz;
		this.type=type;
		this.schoolId=schoolId;
	}

	public WithdrawwalsObject(HttpServletRequest request,String schoolId, String payId, BigDecimal amount, String name, String bankNumber,
			String bamk_code, String desc, String type,String bz) {
		super();
		this.request = request;
		this.schoolId=schoolId;
		this.payId = payId;
		this.amount = amount;
		this.name = name;
		this.bankNumber = bankNumber;
		this.bamk_code = bamk_code;
		this.desc = desc;
		this.type = type;
		this.bz=bz;
		
	}
	
	
}
