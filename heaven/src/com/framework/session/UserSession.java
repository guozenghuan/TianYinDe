package com.framework.session;

import javax.servlet.http.HttpServletRequest;

import com.framework.entity.TcUserEntity;
import com.framework.entity.TsUserEntity;

/**
 * 获取用户信息
 * @author Administrator
 *
 */
public class UserSession {

	/**
	 * 获取客户信息
	 * @param request
	 * @return
	 */
	public static TcUserEntity customer(HttpServletRequest request){
		TcUserEntity tcUser = (TcUserEntity) request.getSession().getAttribute("customerUser");
		return tcUser;
	}
	
	/**
	 * 获取业务员信息
	 * @param request
	 * @return
	 */
	public static TsUserEntity salesman(HttpServletRequest request){
		TsUserEntity tsUser = (TsUserEntity) request.getSession().getAttribute("salesmanUser");
		return tsUser;
	}
	
}
