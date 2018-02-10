package sunwou.valueobject;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class AddTakeOutParamsObject {

	@NotEmpty(message="店铺id不能为空")
	private String shopId;
	@NotNull(message="参数错误")
	private String[] productId;
	@NotNull(message="参数错误")
	private Integer[] number;
	@NotNull(message="参数错误")
	private Integer[] attr;
	@NotEmpty(message="用户id不能为空")
	private String userId;
	private String addressId;
	private boolean takeout=true;
	
	private String remark;
	
	private String reserveTime;

	
	

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

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public boolean isTakeout() {
		return takeout;
	}

	public void setTakeout(boolean takeout) {
		this.takeout = takeout;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String[] getProductId() {
		return productId;
	}

	public void setProductId(String[] productId) {
		this.productId = productId;
	}

	public Integer[] getNumber() {
		return number;
	}

	public void setNumber(Integer[] number) {
		this.number = number;
	}

	public Integer[] getAttr() {
		return attr;
	}

	public void setAttr(Integer[] attr) {
		this.attr = attr;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	
	
}
