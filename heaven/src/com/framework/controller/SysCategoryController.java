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

import com.framework.entity.SysCategoryEntity;
import com.framework.service.SysCategoryService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;


/**
 * 类目表
 * 
 * @author R & D
 * @email 
 * @date 2019-11-18 09:52:48
 */
@Controller
@RequestMapping("syscategory")
public class SysCategoryController {
	@Autowired
	private SysCategoryService sysCategoryService;
	
	@RequestMapping("/syscategory.html")
	public String list(){
		return "syscategory/syscategory.html";
	}
	
	@RequestMapping("/syscategory_add.html")
	public String add(){
		return "syscategory/syscategory_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("syscategory:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<SysCategoryEntity> sysCategoryList = sysCategoryService.queryList(map);
		int total = sysCategoryService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(sysCategoryList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("syscategory:info")
	public R info(@PathVariable("id") Integer id){
		SysCategoryEntity sysCategory = sysCategoryService.queryObject(id);
		
		return R.ok().put("sysCategory", sysCategory);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("syscategory:save")
	public R save(@RequestBody SysCategoryEntity sysCategory){
		sysCategoryService.save(sysCategory);
		
		return R.ok();
	}
	/**
	 * 保存--增加图片
	 */
	@ResponseBody
	@RequestMapping("/saveAddImg")
	@RequiresPermissions("syscategory:save")
	public R saveAddImg(HttpServletRequest request){
		sysCategoryService.saveImg(request);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("syscategory:update")
	public R update(@RequestBody SysCategoryEntity sysCategory){
		sysCategoryService.update(sysCategory);
		
		return R.ok();
	}
	/**
	 * 修改--带图片
	 */
	@ResponseBody
	@RequestMapping("/updateImg")
	@RequiresPermissions("syscategory:update")
	public R updateImg(HttpServletRequest request){
		sysCategoryService.updateImg(request);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("syscategory:delete")
	public R delete(@RequestBody Integer[] ids){
		sysCategoryService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
