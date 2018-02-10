package sunwou.valueobject;

import org.hibernate.validator.constraints.NotEmpty;

public class PayParamsObject {

	@NotEmpty(message="订单id不能为空")
	private String sunwouId;
	@NotEmpty(message="支付方式不能为空")
	private String payment;

	public String getSunwouId() {
		return sunwouId;
	}

	public void setSunwouId(String sunwouId) {
		this.sunwouId = sunwouId;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}
	
	
}
