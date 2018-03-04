package sunwou.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import sunwou.mongo.util.MongoBaseEntity;
import sunwou.util.Util;
import sunwou.valueobject.AddRunParamsObject;
import sunwou.valueobject.AddTakeOutParamsObject;

public class Order extends MongoBaseEntity{
	
	private String schoolId;
	
	private String userId;
	
	private String shopId;
	
	private String shopPhone;
	
	private String shopImage;
	
	private String secret;
	
	private String status;
	
	private String shopAddress;
	
	private String type;
	
	private String subType;
	
	private String shopName;

	private Address address;
	
	private List<OrderProduct> orderProduct;
	
	private String discountType;
	
	private BigDecimal total;
	
	private BigDecimal sendPrice;
	
	private BigDecimal boxPrice;
	
	private BigDecimal productPrice;
	
	private BigDecimal shopdiscount;
	
	private BigDecimal appdiscount;
	
	private BigDecimal appGet;
	
	private BigDecimal shopGet;
	
	private BigDecimal agentGet;
	
	private BigDecimal senderGet;
	
	private String prepareId;
	
	private String senderId;
	
	private String floorId;

	private String payment;
	
	private String remark;
	
	private String reserveTime;
	
	private Integer waterNumber;
	
	private String payTime;
	
	private long timeOut;

	private String senderName;
	
	private String senderPhone;
	
	private String senderImage;
	
	private Boolean pl;
	
	private String userImage;
	
	private String userName;
	
	private String gender;
	
	

	
	

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Boolean getPl() {
		return pl;
	}

	public void setPl(Boolean pl) {
		this.pl = pl;
	}

	public String getSenderImage() {
		return senderImage;
	}

	public void setSenderImage(String senderImage) {
		this.senderImage = senderImage;
	}

	public String getShopPhone() {
		return shopPhone;
	}

	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderPhone() {
		return senderPhone;
	}

	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}

	public long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public Integer getWaterNumber() {
		return waterNumber;
	}

	public void setWaterNumber(Integer waterNumber) {
		this.waterNumber = waterNumber;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReserveTime() {
		return reserveTime;
	}

	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public Order() {
		super();
	}


	public Order(Shop s, AddTakeOutParamsObject aop, String type) {
		this.shopPhone=s.getShopPhone();
		this.shopName=s.getShopName();
		this.shopId=s.getSunwouId();
		this.shopImage=s.getShopImage();
		this.schoolId=s.getSchoolId();
		this.userId=aop.getUserId();
		this.status="待付款";
		this.type=type;
		this.shopAddress=s.getAddress();
		this.remark=aop.getRemark();
		this.reserveTime=aop.getReserveTime();
		this.setSunwouId(Util.GenerateOrderNumber(aop.getUserId(), "takeout"));
		this.total = new BigDecimal(0);
		this.sendPrice = new BigDecimal(0);
		this.boxPrice = new BigDecimal(0);
		this.productPrice = new BigDecimal(0);
		this.shopdiscount = new BigDecimal(0);
		this.appdiscount = new BigDecimal(0);
		this.appGet = new BigDecimal(0);
		this.shopGet = new BigDecimal(0);
		this.agentGet = new BigDecimal(0);
		this.senderGet=new BigDecimal(0);
	}
	


	
	public Order(AddRunParamsObject aop, String type,App app) {
		this.type=type;
		this.subType=aop.getSubType();
		this.setSunwouId(Util.GenerateOrderNumber(aop.getUserId(), "run"));
		this.setAddress(new Address(aop.getName(),aop.getPhone(),aop.getAddress()));
		this.total=aop.getAmount();
		this.schoolId=aop.getSchoolId();
		this.userId=aop.getUserId();
		this.secret=aop.getSecret();
		this.reserveTime=aop.getReserveTime();
		this.remark=aop.getRemark();
		this.status="待付款";
		this.setAppGet(this.getTotal().multiply(app.getOrderRate()));
	}

	public BigDecimal getSenderGet() {
		return senderGet;
	}

	public void setSenderGet(BigDecimal senderGet) {
		this.senderGet = senderGet;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public String getShopImage() {
		return shopImage;
	}

	public void setShopImage(String shopImage) {
		this.shopImage = shopImage;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<OrderProduct> getOrderProduct() {
		return orderProduct;
	}

	public void setOrderProduct(List<OrderProduct> orderProduct) {
		this.orderProduct = orderProduct;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getSendPrice() {
		return sendPrice;
	}

	public void setSendPrice(BigDecimal sendPrice) {
		this.sendPrice = sendPrice;
	}


	public BigDecimal getAppGet() {
		return appGet;
	}

	public void setAppGet(BigDecimal appGet) {
		this.appGet = appGet;
	}

	public BigDecimal getShopGet() {
		return shopGet;
	}

	public void setShopGet(BigDecimal shopGet) {
		this.shopGet = shopGet;
	}

	public BigDecimal getAgentGet() {
		return agentGet;
	}

	public void setAgentGet(BigDecimal agentGet) {
		this.agentGet = agentGet;
	}

	public String getPrepareId() {
		return prepareId;
	}

	public void setPrepareId(String prepareId) {
		this.prepareId = prepareId;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}

	public BigDecimal getShopdiscount() {
		return shopdiscount;
	}

	public void setShopdiscount(BigDecimal shopdiscount) {
		this.shopdiscount = shopdiscount;
	}

	public BigDecimal getAppdiscount() {
		return appdiscount;
	}

	public void setAppdiscount(BigDecimal appdiscount) {
		this.appdiscount = appdiscount;
	}
	
	

	public BigDecimal getBoxPrice() {
		return boxPrice;
	}

	public void setBoxPrice(BigDecimal boxPrice) {
		this.boxPrice = boxPrice;
	}

	public void completeDiscount(Shop s, OrderProduct op) {
		BigDecimal shopRate=s.getProductDiscountRate();
		BigDecimal discount=op.getTotal().subtract(op.getDiscountPrice());
		this.shopdiscount=this.shopdiscount.add(discount.multiply(shopRate)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		this.appdiscount=this.appdiscount.add(discount.multiply(new BigDecimal(1.0).subtract(shopRate))).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		
	}

	public void completeDiscount2(Shop s, FullCut fullCut) {
		BigDecimal shopRate=s.getFullCutRate();
		BigDecimal discount=new BigDecimal(fullCut.getCut());
		this.shopdiscount=this.shopdiscount.add(discount.multiply(shopRate)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		this.appdiscount=this.appdiscount.add(discount.multiply(new BigDecimal(1.0).subtract(shopRate))).setScale(2, BigDecimal.ROUND_HALF_DOWN);
	}
	
	public void completeProductAndBox(Shop s, OrderProduct op) {
		this.productPrice=this.productPrice.add(op.getTotal()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		if(op.getProduct().getBoxFlag()){
			this.boxPrice=this.boxPrice.add(s.getBoxPrice()).multiply(new BigDecimal(op.getNumber())).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		}
		
	}

	public void complete(Shop s, boolean send) {
          	this.total=this.productPrice
          			.add(this.boxPrice)
          			.subtract(this.shopdiscount)
          			.subtract(this.appdiscount);
          	if(send){
          		this.total=this.total.add(s.getSendPrice()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
          		this.sendPrice=s.getSendPrice();
          	}else{
          		this.total=this.total.setScale(2, BigDecimal.ROUND_HALF_DOWN);
          	}
	}

	public void app(App app, Shop s) {
		this.appGet=this.total.multiply(app.getOrderRate()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		this.shopGet=this.total.subtract(this.appGet).subtract(this.sendPrice).multiply(new BigDecimal(1).subtract(s.getRate())).add(this.appdiscount).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		this.agentGet=this.total.subtract(this.appGet).subtract(this.shopGet).setScale(2, BigDecimal.ROUND_HALF_DOWN);
	}

	public void completeSender(BigDecimal rate) {
		BigDecimal temp=this.sendPrice.multiply(rate);
		this.senderGet=this.sendPrice.subtract(temp).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		this.agentGet=this.agentGet.subtract(this.senderGet).setScale(2, BigDecimal.ROUND_HALF_DOWN);
	}

	public void setSenderMsg(Sender sender) {
		this.setSenderId(sender.getSunwouId());
		this.completeSender(sender.getRate());
		this.setStatus("配送员已接手");
		this.setSenderName(sender.getRealName());
		this.setSenderPhone(sender.getPhone());
		this.senderImage=sender.getCreateDate();
	}

}
