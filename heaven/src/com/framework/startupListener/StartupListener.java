package com.framework.startupListener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.framework.utils.WechatMenu;

/**
 * 启动监听器
 *
 * @author Storezhang
 */
@Service
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

	 @Override  
     public void onApplicationEvent(ContextRefreshedEvent event) {  
       if(event.getApplicationContext().getParent() == null){//root application context 没有parent，他就是老大.
    	   System.out.println("创建微信菜单");
            //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。  
    	   WechatMenu.createMenu();
       }  
     }  
}