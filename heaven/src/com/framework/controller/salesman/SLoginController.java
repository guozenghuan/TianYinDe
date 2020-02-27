package com.framework.controller.salesman;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.entity.TsUserEntity;
import com.framework.service.TsUserService;
import com.framework.session.MySessionContext;
import com.framework.utils.R;
import com.framework.utils.StringUtil;

/**
 * 业务员登入
 * @author Administrator
 */
@Controller
@RequestMapping("/salesman")
public class SLoginController {
	
	@Autowired
	private TsUserService tsUserService;
	
	/**
	 * 退出
	 */
	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public R login(HttpSession session)  {
		MySessionContext mc = MySessionContext.getInstance();
		session.setAttribute("salesmanUser",null);
		mc.addSession(session);
		mc.delSession("salesmanUser");
		
		return R.ok("操作成功!");
	}
	
	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public R login(HttpSession session, String account,String password) throws IOException {
		if(StringUtil.isEmpty(account)){
			return R.error("请输入账号");
		}
		if(StringUtil.isEmpty(password)){
			return R.error("请输入登入密码");
		}
		
		TsUserEntity tsUserEntity = new TsUserEntity();
		tsUserEntity.setAccount(account);
		tsUserEntity.setPassword(new Sha256Hash(password).toHex());
		List<TsUserEntity> list = tsUserService.queryObjectByKey(tsUserEntity);
		if(list.isEmpty() || list.size() <= 0){
			return R.error("账号或者密码错误");
		}
		
		//添加用户信息到session中
		session.setAttribute("salesmanUser",list.get(0));
		MySessionContext mc = MySessionContext.getInstance();
		mc.addSession(session);
		
//		return R.ok("操作成功!");
		return R.ok().put("tsUserId",list.get(0).getId());
	}
	
	
}
