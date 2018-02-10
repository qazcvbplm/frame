package sunwou.entity;

import java.math.BigDecimal;

public class OrderProduct {

	private Product product;
	
	private Integer number;
	
	private Attribute attribute;
	
	private BigDecimal total;
	
	private BigDecimal discountPrice;

	public OrderProduct() {
		super();
	}

	public OrderProduct(Product product2, int number2, int att) {
		this.product=product2;
		this.number=number2;
		this.attribute=product2.getAttribute().get(att);
		this.total=this.attribute.getPrice().multiply(new BigDecimal(number2)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		this.discountPrice=this.total.multiply(product2.getDiscount()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	
	
}
