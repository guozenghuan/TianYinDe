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

import com.framework.entity.TyHelpEntity;
import com.framework.entity.TyVideoEntity;
import com.framework.service.TyHelpService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;


/**
 * 平台帮助
 * 
 * @author R & D
 * @email 
 * @date 2019-04-10 14:46:55
 */
@Controller
@RequestMapping("tyhelp")
public class TyHelpController {
	@Autowired
	private TyHelpService tyHelpService;
	
	@RequestMapping("/tyhelp.html")
	public String list(){
		return "tyhelp/tyhelp.html";
	}
	
	@RequestMapping("/tyhelp_add.html")
	public String add(){
		return "tyhelp/tyhelp_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("tyhelp:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TyHelpEntity> tyHelpList = tyHelpService.queryList(map);
		int total = tyHelpService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tyHelpList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tyhelp:info")
	public R info(@PathVariable("id") Long id){
		TyHelpEntity tyHelp = tyHelpService.queryObject(id);
		
		return R.ok().put("tyHelp", tyHelp);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tyhelp:save")
	public R save(@RequestBody TyHelpEntity tyHelp){
		tyHelpService.save(tyHelp);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tyhelp:update")
	public R update(@RequestBody TyHelpEntity tyHelp){
		tyHelpService.update(tyHelp);
		
		return R.ok();
	}
	
	/**
	 * 排序
	 * @param tyService
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateMainSors")
	@RequiresPermissions("tyhelp:update")
	public R updateMainSors(@RequestBody TyHelpEntity tyHelp){
		return tyHelpService.updateSors(tyHelp);
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tyhelp:delete")
	public R delete(@RequestBody Long[] ids){
		tyHelpService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
