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

import com.framework.entity.TyInvitationGiftEntity;
import com.framework.service.TyInvitationGiftService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;
import com.framework.utils.StringUtil;


/**
 * 赠送平台礼物
 * 
 * @author R & D
 * @email 
 * @date 2019-05-07 00:31:33
 */
@Controller
@RequestMapping("tyinvitationgift")
public class TyInvitationGiftController {
	@Autowired
	private TyInvitationGiftService tyInvitationGiftService;
	
	@RequestMapping("/tyinvitationgift.html")
	public String list(){
		return "tyinvitationgift/tyinvitationgift.html";
	}
	
	@RequestMapping("/tyinvitationgift_add.html")
	public String add(){
		return "tyinvitationgift/tyinvitationgift_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("tyinvitationgift:list")
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
		List<TyInvitationGiftEntity> tyInvitationGiftList = tyInvitationGiftService.queryList(map);
		int total = tyInvitationGiftService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tyInvitationGiftList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tyinvitationgift:info")
	public R info(@PathVariable("id") Long id){
		TyInvitationGiftEntity tyInvitationGift = tyInvitationGiftService.queryObject(id);
		
		return R.ok().put("tyInvitationGift", tyInvitationGift);
	}
	
	/**
	 * 保存
	 *//*
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tyinvitationgift:save")
	public R save(@RequestBody TyInvitationGiftEntity tyInvitationGift){
		tyInvitationGiftService.save(tyInvitationGift);
		
		return R.ok();
	}*/
	
	/**
	 * 修改
	 *//*
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tyinvitationgift:update")
	public R update(@RequestBody TyInvitationGiftEntity tyInvitationGift){
		tyInvitationGiftService.update(tyInvitationGift);
		
		return R.ok();
	}*/
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tyinvitationgift:delete")
	public R delete(@RequestBody Long[] ids){
		tyInvitationGiftService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
