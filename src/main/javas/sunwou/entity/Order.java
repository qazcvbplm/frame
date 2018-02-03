package sunwou.entity;

import java.math.BigDecimal;
import java.util.List;

import sunwou.mongo.util.MongoBaseEntity;

public class Order extends MongoBaseEntity{
	
	private String schoolId;
	
	private String userId;
	
	private String shopId;
	
	private String status;
	
	private String type;
	
	private String addressId;
	
	private Address address;
	
	private List<OrderProduct> orderProduct;
	
	private String discountType;
	
	private BigDecimal total;
	
	private BigDecimal sendPrice;
	
	private BigDecimal boxPirce;
	
	private BigDecimal discount;
	
	private BigDecimal appGet;
	
	private BigDecimal shopGet;
	
	private BigDecimal agentGet;
	
	private String prepareId;
	
	private String senderId;
	
	private String floorId;

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

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
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

	public BigDecimal getBoxPirce() {
		return boxPirce;
	}

	public void setBoxPirce(BigDecimal boxPirce) {
		this.boxPirce = boxPirce;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
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
	
	
	

}
