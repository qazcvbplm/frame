package sunwou.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 响应数据包
 * @author ops
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SunwouResponse {

	private String code;

	private String msg;

	private String subCode;

	private String subMsg;

	private String body;

	private Map<String, Object> params;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public String getSubMsg() {
		return subMsg;
	}

	public void setSubMsg(String subMsg) {
		this.subMsg = subMsg;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void push(String key, Object o) {
		if (params == null)
			params = new HashMap<>();
		params.put(key, o);
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	
	    
}
