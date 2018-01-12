package sunwou.valueobject;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="code换取用户信息")
public class CodeToUserParamObejct{

	@ApiModelProperty(value="微信code")
	@NotEmpty(message="code不能为空")
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
