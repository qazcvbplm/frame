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
    private static final Double PI = Math.PI;  
    
    private static final Double PK = 180 / PI;  
      
    /** 
     * @Description: 第一种方法 
     * @param lat_a 
     * @param lng_a 
     * @param lat_b 
     * @param lng_b 
     * @param @return    
     * @return double 
     * @author 钟志铖 
     * @date 2014-9-7 上午10:11:35 
     */  
    public static double getDistanceFromTwoPoints(String lat, String lng, String lat2, String lng2) { 
    	double lat_a=Double.valueOf(lat);
    	double lng_a=Double.valueOf(lng);
    	double lat_b=Double.valueOf(lat2);
    	double lng_b=Double.valueOf(lng2);
        double t1 = Math.cos(lat_a / PK) * Math.cos(lng_a / PK) * Math.cos(lat_b / PK) * Math.cos(lng_b / PK);  
        double t2 = Math.cos(lat_a / PK) * Math.sin(lng_a / PK) * Math.cos(lat_b / PK) * Math.sin(lng_b / PK);  
        double t3 = Math.sin(lat_a / PK) * Math.sin(lat_b / PK);  
  
        double tt = Math.acos(t1 + t2 + t3);  
  
        System.out.println("两点间的距离：" + 6366000 * tt + " 米");  
        return 6366000 * tt;  
    }  
  
      
    /********************************************************************************************************/  
    // 地球半径  
    private static final double EARTH_RADIUS = 6370996.81;  
  
    // 弧度  
    private static double radian(double d) {  
        return d * Math.PI / 180.0;  
    }  
  
    /** 
     * @Description: 第二种方法 
     * @param lat1 
     * @param lng1 
     * @param lat2 
     * @param lng2    
     * @return void 
     * @author 钟志铖 
     * @date 2014-9-7 上午10:11:55 
     */  
    public static void distanceOfTwoPoints(double lat1, double lng1, double lat2, double lng2) {  
        double radLat1 = radian(lat1);  
        double radLat2 = radian(lat2);  
        double a = radLat1 - radLat2;  
        double b = radian(lng1) - radian(lng2);  
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)  
                + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));  
        s = s * EARTH_RADIUS;  
        s = Math.round(s * 10000) / 10000;  
        double ss = s * 1.0936132983377;  
        System.out.println("两点间的距离是：" + s + "米" + "," + (int) ss + "码");  
    }  
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
    
    
 /*   public static void main(String[] args) {
		System.out.println(Distance("30.32454,120.347406","30.32454,120.347406"));
		getDistanceFromTwoPoints("30.32454","120.347406", "30.32454", "120.347406");
	}*/
    
    
}
