package com.framework.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.framework.entity.TcUserEntity;
import com.framework.entity.TcUserPayPasswordEntity;
import com.framework.service.TcUserPayPasswordService;
import com.framework.service.TcUserService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;
import com.framework.utils.StringUtil;


/**
 * 客户信息
 * 
 * @author R & D
 * @email 
 * @date 2019-04-07 10:56:14
 */
@Controller
@RequestMapping("tcuser")
public class TcUserController {
	@Autowired
	private TcUserService tcUserService;
	
	@Autowired
	private TcUserPayPasswordService tcUserPayPasswordService;
	
	@RequestMapping("/tcuser.html")
	public String list(){
		return "tcuser/tcuser.html";
	}
	
	@RequestMapping("/tcuser_add.html")
	public String add(){
		return "tcuser/tcuser_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("tcuser:list")
	public R list(HttpServletRequest request,Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "keyword"))){
			map.put("keywod", StringUtil.toUTF8One(request, "keyword"));
		}
		
		//查询列表数据
		List<TcUserEntity> tcUserList = tcUserService.queryList(map);
		int total = tcUserService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tcUserList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tcuser:info")
	public R info(@PathVariable("id") Long id){
		TcUserEntity tcUser = tcUserService.queryObject(id);
		
		return R.ok().put("tcUser", tcUser);
	}
	
	/**
	 * 保存
	 *//*
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tcuser:save")
	public R save(@RequestBody TcUserEntity tcUser){
		tcUserService.save(tcUser);
		
		return R.ok();
	}*/
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tcuser:update")
	public R update(@RequestBody TcUserEntity tcUser){
		TcUserEntity update = new TcUserEntity();
		update.setId(tcUser.getId());
		update.setStatus(tcUser.getStatus());
		tcUserService.update(update);
		return R.ok();
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping("/update_paypwd")
	@RequiresPermissions("tcuser:update")
	public R updatePaypwd(Long id){
		TcUserEntity tcUserEntity = tcUserService.queryObject(id);
		if(StringUtil.isEmpty(tcUserEntity)){
			return R.error("查找不到该用户信息,请稍后再试!");
		}
		
		TcUserPayPasswordEntity tcUserPayPasswordEntity = new TcUserPayPasswordEntity();
		tcUserPayPasswordEntity.setUserId(tcUserEntity.getId());
		List<TcUserPayPasswordEntity> list = tcUserPayPasswordService.queryObjectByKey(tcUserPayPasswordEntity);
		if(list.isEmpty() || list.size() <= 0){
			return R.error("该用户可能没有设置支付密码,不可为他初始化支付密码!");
		}
		
		// sha256加密
		String payPassword = new Sha256Hash("123456").toHex();
		tcUserPayPasswordEntity.setPayPassword(payPassword);
		tcUserPayPasswordEntity.setId(list.get(0).getId());
		tcUserPayPasswordEntity.setUpdatetime(new Date());
		tcUserPayPasswordService.update(tcUserPayPasswordEntity);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 *//*
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tcuser:delete")
	public R delete(@RequestBody Long[] ids){
		tcUserService.deleteBatch(ids);
		
		return R.ok();
	}*/
	
}
