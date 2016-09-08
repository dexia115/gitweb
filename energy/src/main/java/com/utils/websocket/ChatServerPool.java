package com.utils.websocket;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.java_websocket.WebSocket;

public class ChatServerPool {
	
	private static final Map<WebSocket,String> userconnections = new HashMap<WebSocket,String>();
	
	/**
	 * 向特定的用户发送数据
	 * @param user
	 * @param message
	 */
	public static void sendMessageToUser(WebSocket conn,String message){
		if(null != conn && null != userconnections.get(conn)){
			conn.send(message);
		}
	}
	
	/**
	 * 移除连接池中的连接
	 * @param inbound
	 */
	public static boolean removeUser(WebSocket conn){
		if(userconnections.containsKey(conn)){
			userconnections.remove(conn);	//移除连接
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取用户名
	 * @param session
	 */
	public static String getUserByKey(WebSocket conn){
		return userconnections.get(conn);
	}
	
	/**
	 * 获取WebSocket
	 * @param user
	 */
	public static WebSocket getWebSocketByUser(String user){
		Set<WebSocket> keySet = userconnections.keySet();
		synchronized (keySet) {
			for (WebSocket conn : keySet) {
				String cuser = userconnections.get(conn);
				if(cuser.equals(user)){
					return conn;
				}
			}
		}
		return null;
	}
	
	/**
	 * 向连接池中添加连接
	 * @param inbound
	 */
	public static void addUser(String user, WebSocket conn){
		WebSocket webSocket = getWebSocketByUser(user);
		if(webSocket != null){
			removeUser(webSocket);
		}
		userconnections.put(conn,user);	//添加连接
//		if(webSocket == null){
//			userconnections.put(conn,user);	//添加连接
//		}
	}

}
