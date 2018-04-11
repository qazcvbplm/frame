package sunwou.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.annotation.Transient;

import com.wx.towallet.WeChatPayUtil;

import sunwou.baiduutil.BaiduUtil;
import sunwou.exception.MyException;
import sunwou.mongo.util.MongoBaseEntity;
import sunwou.service.IAppService;
import sunwou.util.StringUtil;
import sunwou.util.TimeUtil;
import sunwou.util.Util;
import sunwou.valueobject.AddRunParamsObject;
import sunwou.valueobject.AddTakeOutParamsObject;
import sunwou.wx.WXUtil;

public class Order extends MongoBaseEntity{
	
	private String schoolId;
	
	private String userId;
	
	private String shopId;
	
	private String shopPhone;
	
	private String shopImage;
	
	private String shopCategoryId;
	
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
	
	private String completeTime;
	
	private BigDecimal appRate;
	
	private BigDecimal senderRate;
	
	private BigDecimal shopRate;
	
	private Boolean get;
	
	private Boolean end;
	
	private BigDecimal senderFloorMoney;//未送达楼上扣除费用
	
	private BigDecimal distanceMoney;//超出配送距离额外费用

	private Boolean remind;
	
	

	public String getShopCategoryId() {
		return shopCategoryId;
	}

	public void setShopCategoryId(String shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}

	public Boolean getRemind() {
		return remind;
	}

	public void setRemind(Boolean remind) {
		this.remind = remind;
	}

	public BigDecimal getDistanceMoney() {
		return distanceMoney;
	}

	public void setDistanceMoney(BigDecimal distanceMoney) {
		this.distanceMoney = distanceMoney;
	}

	public BigDecimal getSenderFloorMoney() {
		return senderFloorMoney;
	}

	public void setSenderFloorMoney(BigDecimal senderFloorMoney) {
		this.senderFloorMoney = senderFloorMoney;
	}

	public Boolean getEnd() {
		return end;
	}

	public void setEnd(Boolean end) {
		this.end = end;
	}

	public Boolean getGet() {
		return get;
	}

	public void setGet(Boolean get) {
		this.get = get;
	}

	public BigDecimal getShopRate() {
		return shopRate;
	}

	public void setShopRate(BigDecimal shopRate) {
		this.shopRate = shopRate;
	}

	public BigDecimal getSenderRate() {
		return senderRate;
	}

	public void setSenderRate(BigDecimal senderRate) {
		this.senderRate = senderRate;
	}

	public BigDecimal getAppRate() {
		return appRate;
	}

	public void setAppRate(BigDecimal appRate) {
		this.appRate = appRate;
	}

	public static int getXiaoshu() {
		return xiaoshu;
	}

	@Transient
	private static final int xiaoshu=3;

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

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
		this.shopCategoryId=s.getCategoryId();
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
		this.distanceMoney=new BigDecimal(0);
		this.boxPrice = new BigDecimal(0);
		this.productPrice = new BigDecimal(0);
		this.shopdiscount = new BigDecimal(0);
		this.appdiscount = new BigDecimal(0);
		this.appGet = new BigDecimal(0);
		this.shopGet = new BigDecimal(0);
		this.agentGet = new BigDecimal(0);
		this.senderGet=new BigDecimal(0);
		this.discountType="无优惠";
		this.get=false;
	}
	


	
	public Order(AddRunParamsObject aop, String type,App app) {
		this.type=type;
		this.floorId=aop.getFloorId();
		this.subType=aop.getSubType();
		this.setSunwouId(Util.GenerateOrderNumber(aop.getUserId(), "run"));
		if(StringUtil.isEmpty(aop.getName())||StringUtil.isEmpty(aop.getPhone())||StringUtil.isEmpty(aop.getAddress())){
			throw new MyException("请完整填写地址");
		}
		this.setAddress(new Address(aop.getName(),aop.getPhone(),aop.getAddress()));
		this.total=aop.getAmount();
		this.schoolId=aop.getSchoolId();
		this.userId=aop.getUserId();
		this.secret=aop.getSecret();
		this.reserveTime=aop.getReserveTime();
		this.remark=aop.getRemark();
		this.status="待付款";
		this.setAppGet(this.getTotal().multiply(app.getOrderRate()));
		this.sendPrice=aop.getAmount();
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
		this.shopdiscount=this.shopdiscount.add(discount.multiply(shopRate)).setScale(xiaoshu, BigDecimal.ROUND_HALF_DOWN);
		this.appdiscount=this.appdiscount.add(discount.multiply(new BigDecimal(1.0).subtract(shopRate))).setScale(xiaoshu, BigDecimal.ROUND_HALF_DOWN);
		
	}

	public void completeDiscount2(Shop s, FullCut fullCut) {
		BigDecimal shopRate=s.getFullCutRate();
		BigDecimal discount=new BigDecimal(fullCut.getCut());
		this.shopdiscount=this.shopdiscount.add(discount.multiply(shopRate)).setScale(xiaoshu, BigDecimal.ROUND_HALF_DOWN);
		this.appdiscount=this.appdiscount.add(discount.multiply(new BigDecimal(1.0).subtract(shopRate))).setScale(xiaoshu, BigDecimal.ROUND_HALF_DOWN);
	}
	
	public void completeProductAndBox(Shop s, OrderProduct op) {
		this.productPrice=this.productPrice.add(op.getTotal()).setScale(xiaoshu, BigDecimal.ROUND_HALF_DOWN);
		if(op.getProduct().getBoxFlag()){
			this.boxPrice=this.boxPrice.add(s.getBoxPrice().multiply(new BigDecimal(op.getNumber()))).setScale(xiaoshu, BigDecimal.ROUND_HALF_DOWN);
		}
		
	}

	public void complete(Shop s, boolean send, IAppService iAppService) {

          	this.total=this.productPrice
          			.add(this.boxPrice)
          			.subtract(this.shopdiscount)
          			.subtract(this.appdiscount);
          	if(send){
          		//计算距离额外的配送费
          		//this.distanceMoney=iAppService.completeSenderMoney(this.getAddress().getFloorId(), s.getSunwouId());
          		this.sendPrice=s.getSendPrice().add(distanceMoney);
          		this.total=this.total.add(this.sendPrice).setScale(xiaoshu, BigDecimal.ROUND_HALF_DOWN);
          	}else{
          		this.total=this.total.setScale(xiaoshu, BigDecimal.ROUND_HALF_DOWN);
          	}
	}

	public void app(School school, Shop s) {
		this.appRate=school.getAppRate();
		this.shopRate=s.getRate();
		this.appGet=this.total.multiply(this.appRate).setScale(xiaoshu, BigDecimal.ROUND_HALF_DOWN);
		//this.shopGet=this.total.subtract(this.appGet).subtract(this.sendPrice).multiply(new BigDecimal(1).subtract(s.getRate())).add(this.appdiscount).setScale(xiaoshu, BigDecimal.ROUND_HALF_DOWN);
		//this.agentGet=this.total.subtract(this.appGet).subtract(this.shopGet).setScale(xiaoshu, BigDecimal.ROUND_HALF_DOWN);
	}

	/*
	 * 已经废弃
	 */
	@Deprecated
	public void completeSender(BigDecimal rate) {
		BigDecimal temp=this.sendPrice.multiply(rate);
		this.senderGet=this.sendPrice.subtract(temp).setScale(xiaoshu, BigDecimal.ROUND_HALF_DOWN);
		if(this.type.equals("外卖订单")||this.type.equals("堂食订单"))
		this.agentGet=this.agentGet.subtract(this.senderGet).setScale(xiaoshu, BigDecimal.ROUND_HALF_DOWN);
		if(this.type.equals("跑腿订单"))
		this.agentGet=temp.subtract(this.appGet);
	}

	public void setSenderMsg(Sender sender) {
		this.senderRate=sender.getRate();
		this.setSenderId(sender.getSunwouId());
		this.setStatus("配送员已接手");
		this.setSenderName(sender.getRealName());
		this.setSenderPhone(sender.getPhone());
		this.senderImage=sender.getCreateDate();
		//this.completeSender(sender.getRate());
	}

	public void complete() {
		// TODO Auto-generated method stub
		if(this.type.equals("跑腿订单")){
			this.agentGet=this.total.multiply(senderRate).subtract(appGet);
			this.senderGet=total.subtract(total.multiply(senderRate));
		}
		if(this.type.equals("外卖订单")){
				this.agentGet=productPrice.add(boxPrice).subtract(appdiscount.add(shopdiscount)).multiply(shopRate).add(sendPrice.multiply(senderRate)).subtract(appGet).subtract(appdiscount);
				this.senderGet=sendPrice.subtract(sendPrice.multiply(senderRate));
				this.shopGet=total.subtract(appGet).subtract(agentGet).subtract(senderGet);
		}
		if(this.type.equals("堂食订单")){
			this.agentGet=productPrice.add(boxPrice).subtract(appdiscount.add(shopdiscount)).multiply(shopRate).subtract(appGet).subtract(appdiscount);
			this.shopGet=total.subtract(appGet).subtract(agentGet);
		}
		this.setStatus("已完成");
    	this.setCompleteTime(TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY));

	}

	public boolean takeOutCheckEnd(BigDecimal senderFloorMoney) {
		if((this.type.equals("外卖订单")||this.type.equals("跑腿订单"))&&!end){
			//对总价进行减少
			this.total=this.total.subtract(senderFloorMoney);
			//app所得减少
			appGet=appGet.subtract(senderFloorMoney.multiply(appRate));
			//对配送费进行更改
			sendPrice=sendPrice.subtract(senderFloorMoney);
			this.senderFloorMoney=senderFloorMoney;
			return false;
		}
		return true;
	}

	public void EndMB(App app,School school,User user, HttpServletRequest request) throws Exception {
		if(this.type.equals("外卖订单")){
			Map<String, String> map = new HashMap<>();
			map.put("appid", app.getAppid());
			map.put("secert", app.getSecertWX());
			map.put("template_id", "5KL1MSdNJl0BVU_YKS2AZfEAXHmiRhaHUA_zNviQBII");
			map.put("touser", user.getOpenid());
			map.put("form_id", getPrepareId());
			map.put("keywordcount", "8");
			map.put("keyword1", this.getSunwouId());
			map.put("keyword2", TimeUtil.formatDate(new Date(), TimeUtil.TO_S));
			map.put("keyword3", this.getStatus());
			map.put("keyword4", getSenderName());
			map.put("keyword5", getSenderPhone());
			map.put("keyword6", school.getPhone());
			String wxts="";
			if(this.end){
				wxts=school.getTakeOutEndRemind();
			}else{
				wxts=school.getTakeOutNoEndRemind();
				wxts.replace("{x}", school.getSenderFloorMoney()+"元");
				//将配送费退回
				String payId=this.getSunwouId().replace("-", "");
					WeChatPayUtil.transfers(request, app, payId, school.getSenderFloorMoney(), user.getOpenid());
			}
			map.put("keyword7", wxts);
			WXUtil.snedM(map);
		}
	}

	public void remindUser(App app,School school,User user,Shop shop) {
		Map<String, String> map = new HashMap<>();
		map.put("appid", app.getAppid());
		map.put("secert", app.getSecertWX());
		map.put("template_id", "1rcOscspDMUASxHCTC9IqcGHkKN8oZJz4wN2L6YHZUs");
		map.put("touser", user.getOpenid());
		map.put("form_id", getPrepareId());
		map.put("keywordcount", "6");
		map.put("keyword1", this.getSunwouId());
		map.put("keyword2",getCreateTime());
		map.put("keyword3",shop.getShopName());
		map.put("keyword4", shop.getShopPhone());
		map.put("keyword5", school.getOrderTimeOutReming());
		WXUtil.snedM(map);
	}

}
