package sunwou.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.google.gson.JsonSyntaxException;

import sunwou.util.Util;
import sunwou.websocket.MyWebSocket;


public class ExceptionResolver extends SimpleMappingExceptionResolver {
	
	
	

	 public ModelAndView resolveException(HttpServletRequest request,
	            HttpServletResponse response, Object handler, Exception ex) {
	        try {
	            Map<String,Object> result = new HashMap<String,Object>();
	            result.put("code", false);
	            if(ex instanceof MyException){
	                result.put("msg", ex.getMessage());
	            }else if(ex instanceof JsonSyntaxException){
	            	result.put("msg", "json格式错误");
	            }
	            else{
	            
	            	for(StackTraceElement error: ex.getStackTrace()){
	            		//Util.outerror(ex+"-"+error.toString());
	            		MyWebSocket.console(ex+"-"+error.toString());
	            	}
	                result.put("msg", "系统运行错误");
	            }
	            //此行必加，否则返回的json在浏览器中看到是乱码，不易于识别
	            response.setHeader("Access-Control-Allow-Credentials", "true");
	    		response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
	            response.setHeader("Content-Type","text/html;charset=UTF-8");
	            response.setContentType("text/xml;charset=utf-8");
	            response.getWriter().write(Util.gson.toJson(result));
	            response.getWriter().close();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	        return null;
	    }

    
    
}