package com.framework.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.framework.entity.TyGiftEntity;
import com.framework.service.TyGiftService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;


/**
 * 礼物
 * 
 * @author R & D
 * @email 
 * @date 2019-05-07 00:31:33
 */
@Controller
@RequestMapping("tygift")
public class TyGiftController {
	@Autowired
	private TyGiftService tyGiftService;
	
	@RequestMapping("/tygift.html")
	public String list(){
		return "tygift/tygift.html";
	}
	
	@RequestMapping("/tygift_add.html")
	public String add(){
		return "tygift/tygift_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("tygift:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TyGiftEntity> tyGiftList = tyGiftService.queryList(map);
		int total = tyGiftService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tyGiftList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tygift:info")
	public R info(@PathVariable("id") Long id){
		TyGiftEntity tyGift = tyGiftService.queryObject(id);
		
		return R.ok().put("tyGift", tyGift);
	}
	
	/**
	 * 保存
	 *//*
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tygift:save")
	public R save(@RequestBody TyGiftEntity tyGift){
		tyGiftService.save(tyGift);
		
		return R.ok();
	}*/
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tygift:update")
	public R update(@RequestBody TyGiftEntity tyGift){
		tyGiftService.update(tyGift);
		
		return R.ok();
	}
	/*
	*//**
	 * 删除
	 *//*
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tygift:delete")
	public R delete(@RequestBody Long[] ids){
		tyGiftService.deleteBatch(ids);
		
		return R.ok();
	}*/
	
}
