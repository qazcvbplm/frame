package sunwou.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import sunwou.util.Util;

@Aspect
@Component
public class ControllerAop {

	
	  private long beforeTime;
	  
	 //controller包的子包里面任何方法
	    @Pointcut("execution(public * sunwou.controller.*.*(..))")
	    public void checkToken(){
	    }
	
	    @Before("checkToken()")
	    public void beforeCheckToken(){
	    	beforeTime =System.currentTimeMillis();
	     
	    }

	    @After("checkToken()")
	    public void afterCheckToken(){
	    	 //两个方法在没有使用JSF的项目中是没有区别的
/*	        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
	        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
	        long time = System.currentTimeMillis()-beforeTime;
	        Util.outLog(getIp(request)+"-"+request.getRequestURI()+"-"+time+"毫秒");
*/	    }
	    
	    
	    /**
	     * 获取ip地址
	     * @param request
	     * @return
	     */
	    private String getIp(HttpServletRequest request){
	        if (request.getHeader("x-forwarded-for") == null) {
	            return request.getRemoteAddr();
	        }
	        return request.getHeader("x-forwarded-for");
	    }
	   
	    
}
