package sunwou.valueobject;

import java.util.HashMap;
import java.util.Map;


import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 响应数据包
 * @author ops
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseObject {

	private boolean code;

	private String msg;

	private Map<String, Object> params;
	
   
	

	public ResponseObject(boolean code, String msg, Map<String, Object> params) {
		super();
		this.code = code;
		this.msg = msg;
		this.params = params;
	}
	
	

	public ResponseObject(boolean code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}



	public boolean isCode() {
		return code;
	}

	public void setCode(boolean code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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
