package sunwou.websocket;




import java.io.IOException;

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
@ServerEndpoint("/ws/socket")
public class MyWebSocket {

	
	
    /**
     * 连接成功
     * @throws IOException */
    @OnOpen
    public void onOpen(Session session) throws IOException {
    	session.getBasicRemote().sendText("连接成功");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
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
        System.out.println("发生错误");
    }

}
