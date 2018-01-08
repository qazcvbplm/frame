package sunwou.wx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import sunwou.util.StringUtil;
import sunwou.util.Util;

public class WXUtil {
	public final static String appid = "wx018b1c6c1d1ad2b9";// wx750e248ada94beee,wxa75115dccbe8ecec
	private final static String secert = "0d307849ea1ff32bbb464ff6de15babb";// 20c81253f1895ef460edea93e864e50c,193c497589ddb70f1953f685cc1199c9
	private static String openidurl = "https://api.weixin.qq.com/sns/jscode2session?appid=";
    private static String tokenurl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";  //获取token的api
    private static String codeurl = "https://api.weixin.qq.com/wxa/getwxacode?access_token=";                      //获取小程序二维码api
    
 
   
	/**
	 * 获取openid
	 * @param code 客户端获取code
	 */
	public static String wxlogin(String code) {
			if(StringUtil.isEmpty(code))
				return null;
		    String nurl = openidurl + appid + "&secret=" + secert + "&js_code=" + code + "&grant_type=authorization_code";
			String RequestOpenidResult=Util.httpRequest(nurl, "GET", null);
			JsonObject RequestOpenidjson = Util.gson.fromJson(RequestOpenidResult, JsonObject.class);
			if (RequestOpenidjson.get("errcode") == null) 
			{
				// 正确获取openid
				return RequestOpenidjson.get("openid").getAsString();
			} 
		return null;
	}
	
	/**
	 * 获取access_token
	 * @param appid
	 * @param secret
	 * @return
	 */
	public String getAccessToken(String appid,String secret){
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
    public static int getCode(String page, String path) {
            Gson gson = new Gson();
            String rs =Util.httpRequest(tokenurl + "&appid=" + appid + "&secret=" + secert, "GET", null);
            JsonObject json = gson.fromJson(rs, JsonObject.class);
            String token = json.get("access_token").toString();
            JsonObject params = new JsonObject();
            params.addProperty("path", page);
            params.addProperty("width", 430);
            token = token.substring(1, token.length() - 1);
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
