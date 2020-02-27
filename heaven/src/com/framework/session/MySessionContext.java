package com.framework.session;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

public class MySessionContext {

	private static MySessionContext instance;  
    private HashMap<String,HttpSession> sessionMap;  
  
    private MySessionContext() {  
        sessionMap = new HashMap<String,HttpSession>();  
    }  
  
    public static MySessionContext getInstance() {  
        if (instance == null) {  
            instance = new MySessionContext();  
        }  
        return instance;  
    }  
  
    public synchronized void addSession(HttpSession session) {  
        if (session != null) {  
            sessionMap.put(session.getId(), session);  
        }  
    }  
  
    public synchronized boolean delSession(String sessionID) {  
    	HttpSession session = getSession(sessionID);
        if (session != null) {  
            sessionMap.remove(session.getId());  
            return true;
        }  else {
        	return false;
        }
    }
    
    public synchronized HttpSession getSession(String sessionID) {  
        if (sessionID == null) {  
            return null;  
        }  
        return sessionMap.get(sessionID);  
    }
    
}
