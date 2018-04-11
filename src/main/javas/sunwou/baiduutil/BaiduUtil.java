package sunwou.baiduutil;

import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import sunwou.util.Util;

public class BaiduUtil {

	private final static String zbzh = "http://api.map.baidu.com/geoconv/v1/?coords=";    //百度坐标转化api
    private final static String ak = "1P106dOK4twNEkPprKzU4OmbHVNBsBOX";                  //app key
    private final static String distance = "http://api.map.baidu.com/routematrix/v2/driving?output=json"; // 百度测距离api
    private final static String locationurl="http://api.map.baidu.com/geocoder/v2/?ak=qFbGsVhASUXTGeLaz4H243dpqjlDyg6W";
    /**
   	 * 百度定位
   	 * latitude x,longitude y  经纬度
   	 */
   	@RequestMapping("wx/getlocation")
   	public static JsonObject getlocation(String latitude,String longitude)
   	{
   		String baidu=wgs84tobaidu(latitude+","+longitude);
   		JsonObject rt = null;
   		String url=locationurl+"&location=" + baidu.split(",")[1] + "," + baidu.split(",")[0] + "&output=json&pois=0";
   		String rs=Util.httpRequest(url, "GET", null);
   		rt=Util.gson.fromJson(rs, JsonObject.class);
   		return rt;
   	}
   	
   	
    /**
     * wgs84坐标转成百度坐标
     * @param wgs x,y
     * @return
     */
    public static String wgs84tobaidu(String wgs) {
	        String zb = zbzh +  wgs.split(",")[1] +","+wgs.split(",")[0] +"&from=1&to=5&ak=" + ak;
	        Gson gson = new Gson();
            String r =Util.httpRequest(zb, "GET", null);
            JsonObject json = gson.fromJson(r, JsonObject.class);
            JsonArray arr = (JsonArray) json.get("result");
            JsonObject nzb = (JsonObject) arr.get(0);
            String nlatitude = nzb.get("x").toString();
            String nlongitude = nzb.get("y").toString();
            return nlatitude + "," + nlongitude;
    }
    
    
    /**
     * 计算多个点对多个点距离
     * @param origins   x,y  起点
     * @param destinations x1,y1|x2,y2.......
     * @return
     */
    public static int Distance(String origins, String destinations) {
    		String url = distance + "&origins=" + origins + "&destinations=" + destinations + "&ak=" + ak;
        	String r =Util.httpRequest(url, "GET", null);
            JsonObject json = Util.gson.fromJson(r, JsonObject.class);
            JsonObject rs = json.get("result").getAsJsonArray().get(0).getAsJsonObject();
            return rs.get("distance").getAsJsonObject().get("value").getAsInt();
    }
}
