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

import com.framework.entity.TyStatisticsEntity;
import com.framework.service.TyStatisticsService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;


/**
 * 平台信息统计
 * 
 * @author R & D
 * @email 
 * @date 2019-05-14 18:02:32
 */
@Controller
@RequestMapping("tystatistics")
public class TyStatisticsController {
	@Autowired
	private TyStatisticsService tyStatisticsService;
	
	@RequestMapping("/tystatistics.html")
	public String list(){
		return "tystatistics/tystatistics.html";
	}
	
	@RequestMapping("/tystatistics_add.html")
	public String add(){
		return "tystatistics/tystatistics_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("tystatistics:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TyStatisticsEntity> tyStatisticsList = tyStatisticsService.queryList(map);
		int total = tyStatisticsService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tyStatisticsList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 获取当前平台信息
	 */
	@ResponseBody
	@RequestMapping("/info_now")
	@RequiresPermissions("tystatistics:info")
	public R infoByNow(){
		TyStatisticsEntity tyStatistics = tyStatisticsService.queryByNow();
		return R.ok().put("tyStatistics", tyStatistics);
	}
	
	
	/**
	 * 信息
	 *//*
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tystatistics:info")
	public R info(@PathVariable("id") Long id){
		TyStatisticsEntity tyStatistics = tyStatisticsService.queryObject(id);
		
		return R.ok().put("tyStatistics", tyStatistics);
	}*/
	
	/**
	 * 保存
	 *//*
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tystatistics:save")
	public R save(@RequestBody TyStatisticsEntity tyStatistics){
		tyStatisticsService.save(tyStatistics);
		
		return R.ok();
	}
	
	*//**
	 * 修改
	 *//*
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tystatistics:update")
	public R update(@RequestBody TyStatisticsEntity tyStatistics){
		tyStatisticsService.update(tyStatistics);
		
		return R.ok();
	}
	
	*//**
	 * 删除
	 *//*
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tystatistics:delete")
	public R delete(@RequestBody Long[] ids){
		tyStatisticsService.deleteBatch(ids);
		
		return R.ok();
	}*/
	
}
