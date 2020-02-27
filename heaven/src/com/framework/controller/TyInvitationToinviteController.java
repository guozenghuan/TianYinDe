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

import com.framework.entity.TyInvitationToinviteEntity;
import com.framework.service.TyInvitationToinviteService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;
import com.framework.utils.StringUtil;


/**
 * 赴邀
 * 
 * @author R & D
 * @email 
 * @date 2019-05-07 00:31:33
 */
@Controller
@RequestMapping("tyinvitationtoinvite")
public class TyInvitationToinviteController {
	@Autowired
	private TyInvitationToinviteService tyInvitationToinviteService;
	
	@RequestMapping("/tyinvitationtoinvite.html")
	public String list(){
		return "tyinvitationtoinvite/tyinvitationtoinvite.html";
	}
	
	@RequestMapping("/tyinvitationtoinvite_add.html")
	public String add(){
		return "tyinvitationtoinvite/tyinvitationtoinvite_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("tyinvitationtoinvite:list")
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
		
		//查询列表数据
		List<TyInvitationToinviteEntity> tyInvitationToinviteList = tyInvitationToinviteService.queryList(map);
		int total = tyInvitationToinviteService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tyInvitationToinviteList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tyinvitationtoinvite:info")
	public R info(@PathVariable("id") Long id){
		TyInvitationToinviteEntity tyInvitationToinvite = tyInvitationToinviteService.queryObject(id);
		
		return R.ok().put("tyInvitationToinvite", tyInvitationToinvite);
	}
	
	/**
	 * 保存
	 *//*
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tyinvitationtoinvite:save")
	public R save(@RequestBody TyInvitationToinviteEntity tyInvitationToinvite){
		tyInvitationToinviteService.save(tyInvitationToinvite);
		
		return R.ok();
	}*/
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tyinvitationtoinvite:update")
	public R update(@RequestBody TyInvitationToinviteEntity tyInvitationToinvite){
		tyInvitationToinviteService.update(tyInvitationToinvite);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tyinvitationtoinvite:delete")
	public R delete(@RequestBody Long[] ids){
		tyInvitationToinviteService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
