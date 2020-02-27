package com.framework.LoginFilter;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.framework.entity.TcUserEntity;
import com.framework.service.TcUserService;
import com.framework.session.MySessionContext;


/**
 * 客户拦截器
 * @author Administrator
 */
@CrossOrigin
public class CustomerFilter implements Filter{
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	   HttpServletRequest hsq = (HttpServletRequest) request;
		 
       Object o= hsq.getSession().getAttribute("customerUser");
       if (o==null){//登入过期
    	   //重新获取用户信息
//    	   ((HttpServletResponse)response).sendError(444, "用户信息过期,请重新获取用户信息!");
    	   
    	  //添加用户信息到session中
    	TcUserEntity tcUserEntity = new TcUserEntity();
    	tcUserEntity.setId(8090L);
    	tcUserEntity.setOpenid("oTHLa1UlKwSXB6erbv12GWAj3oI0");
    	tcUserEntity.setNickname("大熊");
    	tcUserEntity.setHeadimgurl("http://thirdwx.qlogo.cn/mmopen/vi_32/ktLLGzrH88Cya4DwjibR7AcTwoDKNRm2hqnOicu9bV7HbUf0wWYMAFWcS6gyquADeXL9fcLWTibOuQmQeISuppiblA/132");
    	tcUserEntity.setWallet(new BigDecimal(0.15));
    	tcUserEntity.setStatus(0);
    	hsq.getSession().setAttribute("customerUser", tcUserEntity);
   		MySessionContext mc = MySessionContext.getInstance();
   		mc.addSession(hsq.getSession());
   		chain.doFilter(request, response); 
       }else{//未过期,放行
    	   chain.doFilter(request, response);
       }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

}
