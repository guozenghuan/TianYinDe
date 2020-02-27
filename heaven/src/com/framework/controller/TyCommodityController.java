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

import com.framework.entity.TcUserEntity;
import com.framework.entity.TyCommodityEntity;
import com.framework.service.TyCommodityService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;
import com.framework.utils.StringUtil;


/**
 * 
 * 
 * @author R & D
 * @email 
 * @date 2019-03-23 14:20:58
 */
@Controller
@RequestMapping("tycommodity")
public class TyCommodityController {
	@Autowired
	private TyCommodityService tyCommodityService;
	
	@RequestMapping("/tycommodity.html")
	public String list(){
		return "tycommodity/tycommodity.html";
	}
	
	@RequestMapping("/tycommodity_add.html")
	public String add(){
		return "tycommodity/tycommodity_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("tycommodity:list")
	public R list(HttpServletRequest request,Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("titlekey", StringUtil.toUTF8One(request, "title"));
		map.put("categotyId", StringUtil.toUTF8One(request, "categotyId"));
		
		//查询列表数据
		List<TyCommodityEntity> tyCommodityList = tyCommodityService.queryList(map);
		int total = tyCommodityService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tyCommodityList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tycommodity:info")
	public R info(@PathVariable("id") Long id){
		TyCommodityEntity tyCommodity = tyCommodityService.queryObject(id);
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("id", tyCommodity.getId());
		map.put("categotyId", tyCommodity.getCategotyId());
		map.put("mainImage", tyCommodity.getMainImage());
		map.put("price", tyCommodity.getPrice());
		map.put("secondImages", tyCommodity.getSecondImages());
		map.put("status", tyCommodity.getStatus());
		map.put("text", tyCommodity.getText());
		map.put("title", tyCommodity.getTitle());
		return R.ok().put("tyCommodity", map);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tycommodity:save")
	public R save(HttpServletRequest request){
		return tyCommodityService.save(request);
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tycommodity:update")
	public R update(HttpServletRequest request){
		return tyCommodityService.update(request);
	}
	
	/**
	 * 修改商品状态
	 */
	@ResponseBody
	@RequestMapping("/update_status")
	@RequiresPermissions("tycommodity:update")
	public R updateStatus(@RequestBody TyCommodityEntity tyCommodity){
		TyCommodityEntity update = new TyCommodityEntity();
		update.setId(tyCommodity.getId());
		update.setStatus(tyCommodity.getStatus());
		tyCommodityService.updateStatus(update);
		return R.ok(); 
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tycommodity:delete")
	public R delete(@RequestBody Long[] ids){
		return tyCommodityService.deleteBatch(ids);
	}
	
}
