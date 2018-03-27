package sunwou.websocket;




import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


/**
 * @author onepieces
 * socket链接类
 */
@ServerEndpoint("/shop/socket")
public class ShopWebSocket {

	
	private static Map<String,Session>  shops=new HashMap<>();
	
	
	public static void send(String key,String text){
		     if(shops.containsKey(key)){
		    	 Session session=shops.get(key);
		    	 synchronized (key) {
					session.getAsyncRemote().sendText(text);
				 }
		     }
	}
	
	
    /**
     * 连接成功
     * @throws IOException */
    @OnOpen
    public void onOpen(Session session) throws IOException {
    	String id=session.getRequestParameterMap().get("id").get(0);
    	//shops.put(id, session);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
    	String id=session.getRequestParameterMap().get("id").get(0);
        if(shops.containsKey(id)){
        	shops.remove(id);
        }
    }

    /**
     * 收到消息
     * @param message 
    */
    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
    }


	/**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        String id=session.getRequestParameterMap().get("id").get(0);
        if(shops.containsKey(id)){
        	shops.remove(id);
        }
    }

}
