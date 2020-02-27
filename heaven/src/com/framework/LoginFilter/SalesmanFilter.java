package com.framework.LoginFilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * 业务员拦截器
 * @author Administrator
 */
@CrossOrigin
public class SalesmanFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	   HttpServletRequest hsq = (HttpServletRequest) request;
		 
       Object o= hsq.getSession().getAttribute("salesmanUser");
       if (o==null){
           ((HttpServletResponse)response).sendRedirect("/login_salesman.html");
       }else{//未过期,放行
    	   chain.doFilter(request, response);
       }
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
