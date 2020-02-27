package com.framework.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.framework.entity.TyInvitationCashmoneyEntity;
import com.framework.service.TyInvitationCashmoneyService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;
import com.framework.utils.StringUtil;


/**
 * 奠金
 * @author R & D
 * @email 
 * @date 2019-05-07 00:31:33
 */
@Controller
@RequestMapping("tyinvitationcashmoney")
public class TyInvitationCashmoneyController {
	@Autowired
	private TyInvitationCashmoneyService tyInvitationCashmoneyService;
	
	@RequestMapping("/tyinvitationcashmoney.html")
	public String list(){
		return "tyinvitationcashmoney/tyinvitationcashmoney.html";
	}
	
	@RequestMapping("/tyinvitationcashmoney_add.html")
	public String add(){
		return "tyinvitationcashmoney/tyinvitationcashmoney_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("tyinvitationcashmoney:list")
	public R list(HttpServletRequest request,Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "type")) && !StringUtil.isEmpty(StringUtil.toUTF8One(request, "userID"))){
			if(StringUtil.toUTF8One(request, "type").trim().equals("fatie")){
				map.put("modulUserId", StringUtil.toUTF8One(request, "userID"));
			}else{
				map.put("userId", StringUtil.toUTF8One(request, "userID"));
			}
		}
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "status"))){
			map.put("status", StringUtil.toUTF8One(request, "status"));
		}
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "orderNumber"))){
			map.put("orderNumber", StringUtil.toUTF8One(request, "orderNumber"));
		}
		
		//查询列表数据
		List<TyInvitationCashmoneyEntity> tyInvitationCashmoneyList = tyInvitationCashmoneyService.queryList(map);
		int total = tyInvitationCashmoneyService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tyInvitationCashmoneyList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tyinvitationcashmoney:info")
	public R info(@PathVariable("id") Long id){
		TyInvitationCashmoneyEntity tyInvitationCashmoney = tyInvitationCashmoneyService.queryObject(id);
		
		return R.ok().put("tyInvitationCashmoney", tyInvitationCashmoney);
	}
	
	/**
	 * 保存
	 *//*
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tyinvitationcashmoney:save")
	public R save(@RequestBody TyInvitationCashmoneyEntity tyInvitationCashmoney){
		tyInvitationCashmoneyService.save(tyInvitationCashmoney);
		
		return R.ok();
	}*/
	
	/**
	 * 修改
	 *//*
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tyinvitationcashmoney:update")
	public R update(@RequestBody TyInvitationCashmoneyEntity tyInvitationCashmoney){
		tyInvitationCashmoneyService.update(tyInvitationCashmoney);
		
		return R.ok();
	}*/
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tyinvitationcashmoney:delete")
	public R delete(@RequestBody Long[] ids){
		tyInvitationCashmoneyService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
