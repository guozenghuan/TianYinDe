package com.framework.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.entity.TyServiceEntity;
import com.framework.service.TyServiceService;
 import com.framework.utils.R;
import com.framework.utils.StringUtil;


/**
 * 服务
 * 
 * @author R & D
 * @email 
 * @date 2019-03-24 18:53:52
 */
@Controller
@RequestMapping("tyservice")
public class TyServiceController {
	@Autowired
	private TyServiceService tyServiceService;
	
	@RequestMapping("/tyservice.html")
	public String list(){
		return "tyservice/tyservice.html";
	}
	
	@RequestMapping("/tyservice_add.html")
	public String add(){
		return "tyservice/tyservice_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("tyservice:list")
	public R listService(){
		TyServiceEntity tyService = new TyServiceEntity();
		tyService.setParentId(0L);//只有最大的父亲id
		
		JSONArray relationship = new JSONArray();
		JSONObject jObject = new JSONObject();
		jObject.put("children_num", 0);
		jObject.put("parent_num", 0);
		jObject.put("sibling_num", 0);
		relationship.add(jObject);
		
		//流程数据
		tyService.setType("liucheng");
		List<TyServiceEntity> liucheng = tyServiceService.queryObjectByKey(tyService);
		JSONObject jsonObjectLC = new JSONObject();
		jsonObjectLC.put("id", liucheng.get(0).getId());
		jsonObjectLC.put("name", liucheng.get(0).getName());
		jsonObjectLC.put("text", liucheng.get(0).getText());
		
		jsonObjectLC.put("relationship", relationship);
		
		jsonObjectLC.put("children", tyServiceService.recursive(liucheng.get(0).getId()));
		
		List<TyServiceEntity> nullList = new ArrayList<>();
		TyServiceEntity neEntity = new TyServiceEntity();
		neEntity.setId(-1L);
		nullList.add(neEntity);
		
		//服务详情数据
		tyService.setType("fuwu");
		List<TyServiceEntity> fuwu = tyServiceService.queryObjectByKeySort(tyService);
		for(TyServiceEntity fEntity : fuwu){
			TyServiceEntity fSend = new TyServiceEntity();
			fSend.setParentId(fEntity.getId());
			List<TyServiceEntity> fuwuSecond = tyServiceService.queryObjectByKeySort(fSend);
			if(fuwuSecond.isEmpty() || fuwuSecond.size() <= 0){
				fEntity.setChildrenFw(nullList);
			}else{
				fEntity.setChildrenFw(fuwuSecond);
			}
		}
		
		//服务价格
		tyService.setType("jiage");
		List<TyServiceEntity> jiage = tyServiceService.queryObjectByKeySort(tyService);
		 
		return R.ok().put("liucheng", jsonObjectLC).put("fuwu", fuwu).put("jiage", jiage.get(0));
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tyservice:info")
	public R info(@PathVariable("id") Long id){
		TyServiceEntity tyService = tyServiceService.queryObject(id);
		
		return R.ok().put("tyService", tyService);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tyservice:save")
	public R save(@RequestBody TyServiceEntity tyService){
		tyServiceService.save(tyService);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tyservice:update")
	public R update(@RequestBody TyServiceEntity tyService){
	
		tyServiceService.update(tyService);
		
		return R.ok();
	}
	
	@ResponseBody
	@RequestMapping("/updateMainSors")
	@RequiresPermissions("tyservice:update")
	public R updateMainSors(@RequestBody TyServiceEntity tyService){
		tyServiceService.updateMainSors(tyService);
		
		return R.ok();
	}
	
	@ResponseBody
	@RequestMapping("/updateMainSorsScd")
	@RequiresPermissions("tyservice:update")
	public R updateMainSorsScd(@RequestBody TyServiceEntity tyService){
		tyServiceService.updateMainSorsScd(tyService);
		
		return R.ok();
	}
	
	@ResponseBody
	@RequestMapping("/updateJiaGe")
	@RequiresPermissions("tyservice:update")
	public R updateJiage(@RequestBody TyServiceEntity tyService){
		tyServiceService.updateJiaGe(tyService);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tyservice:delete")
	public R delete(@RequestBody Long[] ids){
		if(ids == null || ids.length <= 0){
			return R.error("删除失败,信息不存在!");
		}
		TyServiceEntity tyService = tyServiceService.queryObject(ids[0]);
		if(StringUtil.isEmpty(tyService)){
			return R.error("删除失败,信息不存在!");
		}
		
		//获取子节点所有id
		List<Long> listScond = tyServiceService.getRecursiveIds(tyService.getId());
		//如果是最顶级不能删除自身可以删除子节点
		if(!tyService.getParentId().equals(0L)){
			listScond.add(tyService.getId());
		}
		
		Long[] idsList = new Long[listScond.size()];
		listScond.toArray(idsList);
		
		tyServiceService.deleteBatch(idsList);
		
		return R.ok();
	}
	
	@ResponseBody
	@RequestMapping("/deleteFw")
	@RequiresPermissions("tyservice:delete")
	public R deleteFw(@RequestBody Long[] ids){
		if(ids == null || ids.length <= 0){
			return R.error("删除失败,信息不存在!");
		}
		TyServiceEntity tyService = tyServiceService.queryObject(ids[0]);
		if(StringUtil.isEmpty(tyService)){
			return R.error("删除失败,信息不存在!");
		}
		
		tyServiceService.deleteBatch(ids);
		
		return R.ok();
	}
	
	@ResponseBody
	@RequestMapping("/deleteFwAll")
	@RequiresPermissions("tyservice:delete")
	public R deleteFwAll(@RequestBody Long[] ids){
		if(ids == null || ids.length <= 0){
			return R.error("删除失败,信息不存在!");
		}
		TyServiceEntity tyService = tyServiceService.queryObject(ids[0]);
		if(StringUtil.isEmpty(tyService)){
			return R.error("删除失败,信息不存在!");
		}
		
		tyServiceService.deleteFwAll(ids);
		
		return R.ok();
	}
	
}
