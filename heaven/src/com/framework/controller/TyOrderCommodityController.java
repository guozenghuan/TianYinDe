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

import com.framework.entity.TyOrderCommodityEntity;
import com.framework.service.TyOrderCommodityService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;
import com.framework.utils.StringUtil;


/**
 * 商品订单
 * 
 * @author R & D
 * @email 
 * @date 2019-03-27 13:52:37
 */
@Controller
@RequestMapping("tyordercommodity")
public class TyOrderCommodityController {
	@Autowired
	private TyOrderCommodityService tyOrderCommodityService;
	
	@RequestMapping("/tyordercommodity.html")
	public String list(){
		return "tyordercommodity/tyordercommodity.html";
	}
	
	@RequestMapping("/tyordercommodity_add.html")
	public String add(){
		return "tyordercommodity/tyordercommodity_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("tyordercommodity:list")
	public R list(HttpServletRequest request,Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		/*map.put("keyword", StringUtil.toUTF8One(request, "keyword"));
		map.put("pay_status", StringUtil.toUTF8One(request, "pay_status"));
		map.put("express_status", StringUtil.toUTF8One(request, "express_status"));
		map.put("status", StringUtil.toUTF8One(request, "status"));*/
		
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "keyword"))){
			map.put("keywod", StringUtil.toUTF8One(request, "keyword"));
		}
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "pay_status")) && !StringUtil.toUTF8One(request, "pay_status").equals("-1")){
			map.put("pay_status", Integer.valueOf(StringUtil.toUTF8One(request, "pay_status")));
		}
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "express_status")) && !StringUtil.toUTF8One(request, "express_status").equals("-1")){
			map.put("express_status", Integer.valueOf(StringUtil.toUTF8One(request, "express_status")));
		}
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "status")) && !StringUtil.toUTF8One(request, "status").equals("-1")){
			map.put("status", Integer.valueOf(StringUtil.toUTF8One(request, "status")));
		}
		
		//查询列表数据
		List<TyOrderCommodityEntity> tyOrderCommodityList = tyOrderCommodityService.queryList(map);
		int total = tyOrderCommodityService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tyOrderCommodityList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tyordercommodity:info")
	public R info(@PathVariable("id") Long id){
		TyOrderCommodityEntity tyOrderCommodity = tyOrderCommodityService.queryObject(id);
		
		return R.ok().put("tyOrderCommodity", tyOrderCommodity);
	}
	
	/**
	 * 保存
	 */
	/*@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tyordercommodity:save")
	public R save(@RequestBody TyOrderCommodityEntity tyOrderCommodity){
		tyOrderCommodityService.save(tyOrderCommodity);
		
		return R.ok();
	}*/
	
	/**
	 * 修改
	 */
	/*@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tyordercommodity:update")
	public R update(@RequestBody TyOrderCommodityEntity tyOrderCommodity){
		tyOrderCommodityService.update(tyOrderCommodity);
		
		return R.ok();
	}*/
	
	/**
	 * 添加快递信息
	 */
	@ResponseBody
	@RequestMapping("/sendGoods")
	@RequiresPermissions("tyordercommodity:update")
	public R sendGoods(@RequestBody TyOrderCommodityEntity tyOrderCommodity){
		return tyOrderCommodityService.sendGoods(tyOrderCommodity);
	}
	
	/**
	 * 修改快递信息
	 */
	@ResponseBody
	@RequestMapping("/updateSendGoods")
	@RequiresPermissions("tyordercommodity:update")
	public R updateSendGoods(@RequestBody TyOrderCommodityEntity tyOrderCommodity){
		if(StringUtil.isEmpty(tyOrderCommodity.getExpressName()) || StringUtil.isEmpty(tyOrderCommodity.getExpressNumber())){
			return R.error("快递名称和快递单号必须填写!");
		}
		
		TyOrderCommodityEntity update = new TyOrderCommodityEntity();
		update.setId(tyOrderCommodity.getId());
		update.setExpressName(tyOrderCommodity.getExpressName());
		update.setExpressNumber(tyOrderCommodity.getExpressNumber());
		tyOrderCommodityService.update(update);
		
		return R.ok();
	}
	
	/**
	 * 修改收货信息
	 */
	@ResponseBody
	@RequestMapping("/updateAddres")
	@RequiresPermissions("tyordercommodity:update")
	public R updateAddres(@RequestBody TyOrderCommodityEntity tyOrderCommodity){
		TyOrderCommodityEntity update = new TyOrderCommodityEntity();
		update.setId(tyOrderCommodity.getId());
		update.setName(tyOrderCommodity.getName());
		update.setPhone(tyOrderCommodity.getPhone());
		update.setAddres(tyOrderCommodity.getAddres());
		tyOrderCommodityService.update(update);
		
		return R.ok();
	}
	
	/**
	 * 退款
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/refund/{id}")
	@RequiresPermissions("tyordercommodity:info")
	public R refund(@PathVariable("id") Long id){
		return tyOrderCommodityService.refund(id);
	}
	
	/**
	 * 删除
	 */
	/*@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tyordercommodity:delete")
	public R delete(@RequestBody Long[] ids){
		tyOrderCommodityService.deleteBatch(ids);
		
		return R.ok();
	}*/
	
}
