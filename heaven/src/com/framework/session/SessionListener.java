package com.framework.session;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 后台管理员和业务员和客户的session管理器
 * @author Administrator
 */
@WebListener
public class SessionListener implements HttpSessionListener {
    
    private MySessionContext myc = MySessionContext.getInstance();  
      
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        myc.addSession(session);
        //设置1个小时不刷新，则销毁session
        session.setMaxInactiveInterval(60*60);//单位秒
    }
  
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {  
        HttpSession session = httpSessionEvent.getSession();  
        myc.delSession(session.getId());  
    }
  
}
