package sunwou.aop;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import sunwou.util.TimeUtil;
import sunwou.util.Util;

@Aspect
@Component
public class ControllerAop {

	
	  
	 //controller包的子包里面任何方法
	    @Pointcut("execution(public * sunwou.controller.*.*(..))")
	    public void checkToken(){
	    }
	
	    @Before("checkToken()")
	    public void beforeCheckToken(){
	     
	    }

	    @After("checkToken()")
	    public void afterCheckToken(){
	    }
	    
	    
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
	    
	    @Around("checkToken()")
	       public Object doAround(ProceedingJoinPoint pjp) throws Throwable {  
	            long time = System.currentTimeMillis();  
	            Object retVal = pjp.proceed();  
	            time = System.currentTimeMillis() - time;  
	            if(time>1000){
	            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
	            String a=TimeUtil.formatDate(new Date(), TimeUtil.TO_S)+getIp(request)+"-"+request.getRequestURI()+"-执行时间为"+time+"ms";  
	            	Util.outLog(a);
	            }
	            return retVal;  
	        }   
	   
	    
}
