package sunwou.valueobject;

import org.hibernate.validator.constraints.NotEmpty;

public class ApplyParamsObject {

	@NotEmpty(message="申请Id不能为空")
	private String applyId;
	
	private Boolean success;
	
	private String result;

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
}
