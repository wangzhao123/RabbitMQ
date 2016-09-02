package com.taikang.session;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
	private static SessionManager instance = null;
	
	private static Map<String, ISession> sessions = new HashMap<>();
	
	private SessionManager() {
	}
	
	public static SessionManager getInstance() {
		if (instance == null) {
			instance = new SessionManager();
		}
		
		return instance;
	}
	
	
	public ISession createSession(String token) {
		ISession session = new Session();
		sessions.put(token, session);
		return session;
	}
	
	public ISession getSession(String token) {
		return sessions.get(token);
	}
	
	public boolean hasSession(String token) {
		if(sessions.get(token)!=null){
			return true;
		}
		return false;
	}
	
	public void removeSession(String token){
		sessions.remove(token);
	}
	
}
