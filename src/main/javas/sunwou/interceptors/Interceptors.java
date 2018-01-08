package sunwou.interceptors;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class Interceptors implements HandlerInterceptor {

    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }

    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {

    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object o) throws Exception {
        request.setCharacterEncoding("UTF-8");//设置编码?
        response.setContentType("text/html;charset=UTF-8");//设置传输编码
        String url = request.getRequestURI();
        HashMap<String, String> map = new HashMap<String, String>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        request.getSession().setAttribute("params", map);
          /*  Set<Map.Entry<String, String>> set = map.entrySet();
	        for (Map.Entry entry : set) {
	            System.out.println(entry.getKey() + ":" + entry.getValue());
	        }  */
        if (request.getSession().getAttribute("admin") == null) {
            request.getSession().setAttribute("lasturl", url.substring(8));
            response.sendRedirect("login.jsp");
            return false;
        }
        if (request.getHeader("X-Requested-With") != null) {
            return true;
        } else {
            return true;
        }
    }

}
