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

import com.framework.entity.TyVideoEntity;
import com.framework.service.TyVideoService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;


/**
 * 视频展示
 * 
 * @author R & D
 * @email 
 * @date 2019-04-09 18:41:09
 */
@Controller
@RequestMapping("tyvideo")
public class TyVideoController {
	@Autowired
	private TyVideoService tyVideoService;
	
	@RequestMapping("/tyvideo.html")
	public String list(){
		return "tyvideo/tyvideo.html";
	}
	
	@RequestMapping("/tyvideo_add.html")
	public String add(){
		return "tyvideo/tyvideo_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("tyvideo:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<TyVideoEntity> tyVideoList = tyVideoService.queryList(map);
		int total = tyVideoService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tyVideoList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tyvideo:info")
	public R info(@PathVariable("id") Long id){
		TyVideoEntity tyVideo = tyVideoService.queryObject(id);
		tyVideo.setCreatetime(null);
		return R.ok().put("tyVideo", tyVideo);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tyvideo:save")
	public R save(HttpServletRequest request){
		return tyVideoService.save(request);
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tyvideo:update")
	public R update(HttpServletRequest request){
		return tyVideoService.update(request);
	}
	
	/**
	 * 排序
	 * @param tyService
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateMainSors")
	@RequiresPermissions("tyservice:update")
	public R updateMainSors(@RequestBody TyVideoEntity tyVideo){
		return tyVideoService.updateSors(tyVideo);
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tyvideo:delete")
	public R delete(@RequestBody Long[] ids){
		return tyVideoService.deleteBatch(ids);
	}
	
}
