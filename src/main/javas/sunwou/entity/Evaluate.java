package sunwou.entity;

import sunwou.mongo.util.MongoBaseEntity;

public class Evaluate extends MongoBaseEntity{

	private String orderId;
	
	private String schoolId;
	
	private String senderId;
	
	private Integer senderNumber;
	
	private Integer shopNumber;
	
	private String senderDes;
	
	private String shopDes;
	
	private String userId;
	
	private String waterNumber;
	
	private String shopId;
	
	

	public String getWaterNumber() {
		return waterNumber;
	}

	public void setWaterNumber(String waterNumber) {
		this.waterNumber = waterNumber;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public Integer getSenderNumber() {
		return senderNumber;
	}

	public void setSenderNumber(Integer senderNumber) {
		this.senderNumber = senderNumber;
	}

	public Integer getShopNumber() {
		return shopNumber;
	}

	public void setShopNumber(Integer shopNumber) {
		this.shopNumber = shopNumber;
	}

	public String getSenderDes() {
		return senderDes;
	}

	public void setSenderDes(String senderDes) {
		this.senderDes = senderDes;
	}

	public String getShopDes() {
		return shopDes;
	}

	public void setShopDes(String shopDes) {
		this.shopDes = shopDes;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
