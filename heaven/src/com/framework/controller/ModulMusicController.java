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

import com.framework.entity.ModulMusicEntity;
import com.framework.service.ModulMusicService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;


/**
 * 
 * 
 * @author R & D
 * @email 
 * @date 2019-04-28 12:58:09
 */
@Controller
@RequestMapping("modulmusic")
public class ModulMusicController {
	@Autowired
	private ModulMusicService modulMusicService;
	
	@RequestMapping("/modulmusic.html")
	public String list(){
		return "modulmusic/modulmusic.html";
	}
	
	@RequestMapping("/modulmusic_add.html")
	public String add(){
		return "modulmusic/modulmusic_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("modulmusic:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<ModulMusicEntity> modulMusicList = modulMusicService.queryList(map);
		int total = modulMusicService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(modulMusicList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("modulmusic:info")
	public R info(@PathVariable("id") Long id){
		ModulMusicEntity modulMusic = modulMusicService.queryObject(id);
		
		return R.ok().put("modulMusic", modulMusic);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("modulmusic:save")
	public R save(HttpServletRequest request){
		return modulMusicService.save(request);
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("modulmusic:update")
	public R update(HttpServletRequest request){
		return modulMusicService.update(request);
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("modulmusic:delete")
	public R delete(@RequestBody Long[] ids){
		modulMusicService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
