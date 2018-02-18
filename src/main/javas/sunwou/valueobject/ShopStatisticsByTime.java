package sunwou.valueobject;

import java.math.BigDecimal;

public class ShopStatisticsByTime {

	
	private int orderNumber;
	
	private BigDecimal total;
	
	private BigDecimal shopGet;
	
	private BigDecimal boxPrice;

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getShopGet() {
		return shopGet;
	}

	public void setShopGet(BigDecimal shopGet) {
		this.shopGet = shopGet;
	}

	public BigDecimal getBoxPrice() {
		return boxPrice;
	}

	public void setBoxPrice(BigDecimal boxPrice) {
		this.boxPrice = boxPrice;
	}

	public ShopStatisticsByTime() {
		super();
		this.orderNumber = 0;
		this.total = new BigDecimal(0);
		this.shopGet = new BigDecimal(0);
		this.boxPrice = new BigDecimal(0);
	}

	
	
}
