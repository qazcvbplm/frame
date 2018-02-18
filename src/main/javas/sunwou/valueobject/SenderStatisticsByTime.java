package sunwou.valueobject;

import java.math.BigDecimal;

public class SenderStatisticsByTime {

	private Integer orderNumber;
	
	private Integer complete;
	
	private Integer unComplete;
	
	private BigDecimal get;

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getComplete() {
		return complete;
	}

	public void setComplete(Integer complete) {
		this.complete = complete;
	}

	public Integer getUnComplete() {
		return unComplete;
	}

	public void setUnComplete(Integer unComplete) {
		this.unComplete = unComplete;
	}

	public BigDecimal getGet() {
		return get;
	}

	public void setGet(BigDecimal get) {
		this.get = get;
	}

	public SenderStatisticsByTime() {
		super();
		this.orderNumber = 0;
		this.complete = 0;
		this.unComplete = 0;
		this.get = new BigDecimal(0);
	}
	
	
	
}
