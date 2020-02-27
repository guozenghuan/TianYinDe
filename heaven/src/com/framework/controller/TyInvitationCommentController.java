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

import com.framework.entity.TyInvitationCommentEntity;
import com.framework.service.TyInvitationCommentService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;
import com.framework.utils.StringUtil;


/**
 * 模板评论
 * 
 * @author R & D
 * @email 
 * @date 2019-05-07 00:31:33
 */
@Controller
@RequestMapping("tyinvitationcomment")
public class TyInvitationCommentController {
	@Autowired
	private TyInvitationCommentService tyInvitationCommentService;
	
	@RequestMapping("/tyinvitationcomment.html")
	public String list(){
		return "tyinvitationcomment/tyinvitationcomment.html";
	}
	
	@RequestMapping("/tyinvitationcomment_add.html")
	public String add(){
		return "tyinvitationcomment/tyinvitationcomment_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("tyinvitationcomment:list")
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
		List<TyInvitationCommentEntity> tyInvitationCommentList = tyInvitationCommentService.queryList(map);
		int total = tyInvitationCommentService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tyInvitationCommentList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tyinvitationcomment:info")
	public R info(@PathVariable("id") Long id){
		TyInvitationCommentEntity tyInvitationComment = tyInvitationCommentService.queryObject(id);
		
		return R.ok().put("tyInvitationComment", tyInvitationComment);
	}
	
	/**
	 * 保存
	 *//*
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tyinvitationcomment:save")
	public R save(@RequestBody TyInvitationCommentEntity tyInvitationComment){
		tyInvitationCommentService.save(tyInvitationComment);
		
		return R.ok();
	}*/
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tyinvitationcomment:update")
	public R update(@RequestBody TyInvitationCommentEntity tyInvitationComment){
		tyInvitationCommentService.update(tyInvitationComment);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tyinvitationcomment:delete")
	public R delete(@RequestBody Long[] ids){
		tyInvitationCommentService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
