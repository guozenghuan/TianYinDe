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

import com.framework.entity.TbFlowingRecordEntity;
import com.framework.service.TbFlowingRecordService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;
import com.framework.utils.StringUtil;


/**
 * 用户流水
 * 
 * @author R & D
 * @email 
 * @date 2019-05-05 15:47:11
 */
@Controller
@RequestMapping("tbflowingrecord")
public class TbFlowingRecordController {
	@Autowired
	private TbFlowingRecordService tbFlowingRecordService;
	
	@RequestMapping("/tbflowingrecord.html")
	public String list(){
		return "tbflowingrecord/tbflowingrecord.html";
	}
	
	@RequestMapping("/tbflowingrecord_add.html")
	public String add(){
		return "tbflowingrecord/tbflowingrecord_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("tbflowingrecord:list")
	public R list(HttpServletRequest request,Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "keyword"))){
			map.put("keywod", StringUtil.toUTF8One(request, "keyword"));
		}
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "type")) && !StringUtil.toUTF8One(request, "type").equals("-1")){
			map.put("type", Integer.valueOf(StringUtil.toUTF8One(request, "type")));
		}
		
		//查询列表数据
		List<TbFlowingRecordEntity> tbFlowingRecordList = tbFlowingRecordService.queryList(map);
		int total = tbFlowingRecordService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tbFlowingRecordList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tbflowingrecord:info")
	public R info(@PathVariable("id") Long id){
		TbFlowingRecordEntity tbFlowingRecord = tbFlowingRecordService.queryObject(id);
		
		return R.ok().put("tbFlowingRecord", tbFlowingRecord);
	}
	
	/**
	 * 保存
	 *//*
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tbflowingrecord:save")
	public R save(@RequestBody TbFlowingRecordEntity tbFlowingRecord){
		tbFlowingRecordService.save(tbFlowingRecord);
		
		return R.ok();
	}
	
	*//**
	 * 修改
	 *//*
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tbflowingrecord:update")
	public R update(@RequestBody TbFlowingRecordEntity tbFlowingRecord){
		tbFlowingRecordService.update(tbFlowingRecord);
		
		return R.ok();
	}
	
	*//**
	 * 删除
	 *//*
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tbflowingrecord:delete")
	public R delete(@RequestBody Long[] ids){
		tbFlowingRecordService.deleteBatch(ids);
		
		return R.ok();
	}*/
	
}
