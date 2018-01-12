package sunwou.wx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import sunwou.exception.MyException;
import sunwou.util.StringUtil;
import sunwou.util.Util;

public class WXUtil {
	private static String openidurl = "https://api.weixin.qq.com/sns/jscode2session?appid=";
    private static String tokenurl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";  //获取token的api
    private static String codeurl = "https://api.weixin.qq.com/wxa/getwxacode?access_token=";                      //获取小程序二维码api
    
 
   
	/**
	 * 获取openid
	 * @param code 客户端获取code
	 */
	public static String wxlogin(String appid,String secert,String code) {
			StringBuffer sb=new StringBuffer();
		    String nurl = sb.append(openidurl).append(appid).append("&secret=").append(secert).append("&js_code=").
		    		append(code).append("&grant_type=authorization_code").toString();
			String RequestOpenidResult=Util.httpRequest(nurl, "GET", null);
			JsonObject RequestOpenidjson = Util.gson.fromJson(RequestOpenidResult, JsonObject.class);
			if (RequestOpenidjson.get("errcode") == null) 
			{
				// 正确获取openid
				return RequestOpenidjson.get("openid").getAsString();
			} 
			else
			{
				throw new MyException(RequestOpenidjson.get("errormsg").getAsString());
			}
	}
	
	/**
	 * 获取access_token
	 * @param appid
	 * @param secret
	 * @return
	 */
	public String getAccessToken(String appid,String secert){
		 Gson gson = new Gson();
         String rs =Util.httpRequest(tokenurl + "&appid=" + appid + "&secret=" + secert, "GET", null);
         JsonObject json = gson.fromJson(rs, JsonObject.class);
         return json.get("access_token").toString();
	}
	
	 /**
     * 获取小程序二维码
     * @param page 页面路径
     * @param path 二维码路径
     * @return
     */
    public static int getCode(String appid,String secert,String page, String path) {
            Gson gson = new Gson();
            String rs =Util.httpRequest(tokenurl + "&appid=" + appid + "&secret=" + secert, "GET", null);
            JsonObject json = gson.fromJson(rs, JsonObject.class);
            String token = json.get("access_token").getAsString();
            JsonObject params = new JsonObject();
            params.addProperty("path", page);
            params.addProperty("width", 430);
            String u = codeurl + token;
            InputStream in = PayUtil.httpRequest2(u, "POST", params.toString());
            if (in == null) {
                return 0;
            }
       try {
            FileOutputStream fileout = new FileOutputStream(new File(path));
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = in.read(b)) != -1) {
                fileout.write(b, 0, len);
                fileout.flush();
            }
            fileout.close();
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
  
	
}
