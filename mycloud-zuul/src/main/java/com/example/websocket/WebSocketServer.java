package com.example.websocket;

/**
 * User: lanxinghua
 * Date: 2019/4/25 11:17
 * Desc: websocket服务
 */
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.example.common.utils.R;
import com.example.entity.SysUserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@ServerEndpoint("/websocket")
@Component
public class WebSocketServer {

    private static final Logger log= LoggerFactory.getLogger(WebSocketServer.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        log.info("有新窗口开始监听");
        try {
            R r = R.ok().put("data", "socket连接成功");
            sendMessage(JSON.toJSONString(r));
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        log.info("有一连接关闭");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口的信息:"+message);
        //群发消息
        for (WebSocketServer item : webSocketSet) {
            try {
                R r = R.ok().put("data", message);
                item.sendMessage(JSON.toJSONString(r));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 实现服务器主动推送
     */
    public void qqLogin(R r) throws Exception {
        this.session.getBasicRemote().sendText(JSON.toJSONString(r));
    }


    /**
     * 群发自定义消息
     * */
    public static void sendUserInfo(R r){
        log.info("推送消息到窗口推送内容:"+ JSON.toJSONString(r));
        for (WebSocketServer item : webSocketSet) {
            try {
                item.qqLogin(r);
            } catch (Exception e) {
                continue;
            }
        }
    }
}
