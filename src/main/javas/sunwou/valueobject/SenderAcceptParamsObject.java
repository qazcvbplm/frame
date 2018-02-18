package sunwou.valueobject;

import org.hibernate.validator.constraints.NotEmpty;

public class SenderAcceptParamsObject {


	@NotEmpty(message="订单id不能为空")
	private String orderId;
	@NotEmpty(message="配送员id不能为空")
	private String senderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	
	
}
