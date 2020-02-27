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
import org.springframework.transaction.annotation.Transactional;

import com.framework.entity.ModulEntity;
import com.framework.entity.ModulMusicEntity;
import com.framework.service.ModulMusicService;
import com.framework.service.ModulService;
import com.framework.utils.R;
import com.framework.utils.StringUtil;


/**
 * 模板信息表
 * 
 * @author R & D
 * @email 
 * @date 2019-04-28 02:12:10
 */
@Controller
@RequestMapping("modul")
public class ModulController {
	@Autowired
	private ModulService modulService;
	@Autowired
	private ModulMusicService modulMusicService;
	
	@RequestMapping("/modul.html")
	public String list(){
		return "modul/modul.html";
	}
	
	@RequestMapping("/modul_add.html")
	public String add(){
		return "modul/modul_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("modul:list")
	public R listModul(){
		ModulEntity modulEntity = new ModulEntity();
		modulEntity.setUserId(0L);
		modulEntity.setModulType("system");
		
		//查询列表数据
		List<ModulEntity> modulList = modulService.queryObjectByKey(modulEntity);
		
		return R.ok().put("page", modulList);
	}
	/*@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("modul:list")
	public R list(Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("modulType", "system");
		
		//查询列表数据
		List<ModulEntity> modulList = modulService.queryList(map);
		int total = modulService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(modulList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}*/
	
	@ResponseBody
	@RequestMapping("/list_music")
	@RequiresPermissions("modul:list")
	public R listMusic(){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (1 - 1) * 100000);
		map.put("limit", 100000);
		
		//查询列表数据
		List<ModulMusicEntity> modulMusicList = modulMusicService.queryList(map);
		
		return R.ok().put("modulMusicList", modulMusicList);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("modul:info")
	public R info(@PathVariable("id") Long id){
		return R.ok().put("modul", modulService.queryObject(id));
	}
	/*@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("modul:info")
	public R info(@PathVariable("id") Long id){
		Boolean seetingBtn = true;//可编辑
		ModulEntity modul = modulService.queryObject(id,seetingBtn);
		
		return R.ok().put("modul", modul);
	}*/
	
	/**
	 * 获取模板信息
	 */
	@ResponseBody
	@RequestMapping("/info_modul")
//	@RequiresPermissions("modul:info")
	public R modulInfoBySystem(Long id,String modulCode){
		return modulService.queryModulInfo(id,modulCode);
	}
	
	/**
	 * 保存
	 */
	/*@Transactional
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("modul:save")
	public R save(@RequestBody ModulEntity modul){
		return R.ok().put("modulId", modulService.save(modul));
	}*/
	
	/**
	 * 添加模板
	 * @param id
	 * @param modulCode
	 * @return
	 */
	@Transactional
	@ResponseBody
	@RequestMapping("/save_modul")
	@RequiresPermissions("modul:save")
	public R saveModul(Long id,String modulCode){
		ModulEntity modulEntity = modulService.queryObject(id);
		if(StringUtil.isEmpty(modulEntity)){
			return R.error("获取不到模板信息,请刷新页面后重新操作!");
		}
		
		Long modulId = modulService.saveModul(modulCode);
		if(StringUtil.isEmpty(modulId)){
			return R.error("获取不到模板信息,请刷新页面后重新操作!");
		}
		if(StringUtil.isEmpty(modulEntity.getModulNameAndId())){
			modulEntity.setModulNameAndId(modulCode+"="+modulId+";");
		}else{
			modulEntity.setModulNameAndId(modulEntity.getModulNameAndId()+modulCode+"="+modulId+";");	
		}
		modulService.update(modulEntity);
		
		return R.ok().put("modulId", modulId);
	}
	
	/**
	 * 删除模板信息
	 * @param id
	 * @param modulCode
	 * @return
	 */
	@Transactional
	@ResponseBody
	@RequestMapping("/delete_modul")
	@RequiresPermissions("modul:save")
	public R deleteModul(Long mainModulId,Long id,String modulCode){
		if(StringUtil.isEmpty(mainModulId) || StringUtil.isEmpty(id) || StringUtil.isEmpty(modulCode)){
			return R.error("获取不到模板信息,请刷新页面后重新操作!");
		}
		
		return modulService.deleteModul(mainModulId,id,modulCode);
	}
	
	/**
	 * 保存模板信息
	 */
	@Transactional
	@ResponseBody
	@RequestMapping("/save_information_modul")
	@RequiresPermissions("modul:save")
	public R saveInformationModul(HttpServletRequest request){
		return modulService.saveInformationModul(request);
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("modul:update")
	public R update(@RequestBody ModulEntity modul){
		return modulService.update(modul);
	}
	
	/**
	 * 删除
	 */
	@Transactional
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("modul:delete")
	public R delete(@RequestBody Long[] ids){
		ModulEntity modulEntity = modulService.queryObject(ids[0]);
		if(StringUtil.isEmpty(modulEntity)){
			return R.error("没有该模板信息,请刷新后重新选择要删除的模板!");
		}
		
		if(!StringUtil.isEmpty(modulEntity.getModulNameAndId())){
			//删除子模板
			if(StringUtil.isIndexOfStr(modulEntity.getModulNameAndId(), ";")){
				String[] modulNmaeAndIds = modulEntity.getModulNameAndId().split(";");
				for(String modulNameAndId : modulNmaeAndIds){
					if(StringUtil.isIndexOfStr(modulNameAndId, "=")){
						modulService.deleteModul(ids[0],Long.valueOf(modulNameAndId.split("=")[1]),modulNameAndId.split("=")[0]);
					}
				}
			}
		}
		modulService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
