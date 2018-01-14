package sunwou.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.google.gson.JsonSyntaxException;

import sunwou.util.Util;


public class ExceptionResolver extends SimpleMappingExceptionResolver {

	 public ModelAndView resolveException(HttpServletRequest request,
	            HttpServletResponse response, Object handler, Exception ex) {
	        try {
	            Map<String,Object> result = new HashMap<String,Object>();
	            result.put("success", false);
	            if(ex instanceof MyException){
	                result.put("result", ex.getMessage());
	            }else if(ex instanceof JsonSyntaxException){
	            	result.put("result", "json格式错误");
	            }
	            else{
	                result.put("result", "系统运行错错误");
	            }
	            //此行必加，否则返回的json在浏览器中看到是乱码，不易于识别
	            response.setHeader("Content-Type","text/html;charset=UTF-8");
	            response.getWriter().write(Util.gson.toJson(result));
	            response.getWriter().close();
	        } catch (Exception e) {
	            //e.printStackTrace();
	        }
	        return null;
	    }

    
    
}