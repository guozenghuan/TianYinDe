package com.framework.controller;

import java.util.Date;
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

import com.framework.entity.TsCompanyEntity;
import com.framework.service.TsCompanyService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;


/**
 * 公司信息
 * 
 * @author R & D
 * @email 
 * @date 2019-03-20 13:52:39
 */
@Controller
@RequestMapping("tscompany")
public class TsCompanyController {
	@Autowired
	private TsCompanyService tsCompanyService;
	
	@RequestMapping("/tscompany.html")
	public String list(){
		return "tscompany/tscompany.html";
	}
	
	@RequestMapping("/tscompany_add.html")
	public String add(){
		return "tscompany/tscompany_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("tscompany:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TsCompanyEntity> tsCompanyList = tsCompanyService.queryList(map);
		int total = tsCompanyService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tsCompanyList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 获取所有数据
	 */
	@ResponseBody
	@RequestMapping("/listopen")
	@RequiresPermissions("tscompany:list:open")
	public R listOpen(){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", 0);
		map.put("limit", 10000);
		
		//查询列表数据
		List<TsCompanyEntity> tsCompany = tsCompanyService.queryList(map);
		
		return R.ok().put("tsCompany", tsCompany);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tscompany:info")
	public R info(@PathVariable("id") Long id){
		TsCompanyEntity tsCompany = tsCompanyService.queryObject(id);
		
		return R.ok().put("tsCompany", tsCompany);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tscompany:save")
	public R save(@RequestBody TsCompanyEntity tsCompany){
		tsCompany.setCreatetime(new Date());
		tsCompanyService.save(tsCompany);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tscompany:update")
	public R update(@RequestBody TsCompanyEntity tsCompany){
		tsCompanyService.update(tsCompany);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tscompany:delete")
	public R delete(@RequestBody Long[] ids){
		tsCompanyService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
