package com.longray.controller.common;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import com.utils.websocket.ChatServerPool;


public class ChatServer extends WebSocketServer{

	public ChatServer(int port) throws UnknownHostException {
		super(new InetSocketAddress(port));
	}

	public ChatServer(InetSocketAddress address) {
		super(address);
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 客户端发送消息到服务器时触发事件
	 */
	@Override
	public void onMessage(WebSocket conn, String message) {
		if(null != message && message.startsWith("FHadminqq313596790")){
			userjoin(message.replaceFirst("FHadminqq313596790", ""),conn);
		}
		if(null != message && message.startsWith("LeaveFHadminqq313596790")){
			String toUser = message.substring(message.indexOf("LeaveFHadminqq313596790")+23);
			userLeave(toUser);
		}
		if(null != message && message.contains("fhadmin886")){
			String toUser = message.substring(message.indexOf("fhadmin886")+10, message.indexOf("fhfhadmin888"));
			message = message.substring(0, message.indexOf("fhadmin886")) +message.substring(message.indexOf("fhfhadmin888")+12, message.length());
			WebSocket receive = ChatServerPool.getWebSocketByUser(toUser);
			if(receive != null){
				ChatServerPool.sendMessageToUser(receive,message);//向某用户发送消息
			}else{
				System.out.println("用户："+toUser+"已下线");
			}
			
//			ChatServerPool.sendMessageToUser(conn, message);//同时向本人发送消息
		}
		
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 用户加入处理
	 * @param user
	 */
	public void userjoin(String user, WebSocket conn){
		
		ChatServerPool.addUser(user,conn);//向连接池添加当前的连接对象
	}
	
	/**
	 * 用户下线处理
	 * @param user
	 */
	public void userLeave(String user){
		WebSocket conn = ChatServerPool.getWebSocketByUser(user);
		ChatServerPool.removeUser(conn);//在连接池中移除连接
	}

}
