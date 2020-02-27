package com.framework.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.framework.entity.TsUserEntity;
import com.framework.service.TsUserService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;
import com.framework.utils.StringUtil;


/**
 * 业务员账号
 * 
 * @author R & D
 * @email 
 * @date 2019-03-20 13:52:39
 */
@Controller
@RequestMapping("tsuser")
public class TsUserController {
	@Autowired
	private TsUserService tsUserService;
	
	@RequestMapping("/tsuser.html")
	public String list(){
		return "tsuser/tsuser.html";
	}
	
	@RequestMapping("/tsuser_add.html")
	public String add(){
		return "tsuser/tsuser_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("tsuser:list")
	public R list(HttpServletRequest request,Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "keyword"))){
			map.put("keywod", StringUtil.toUTF8One(request, "keyword"));
		}
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "company_id")) && !StringUtil.toUTF8One(request, "company_id").equals("-1")){
			map.put("company_id", Integer.valueOf(StringUtil.toUTF8One(request, "company_id")));
		    System.out.println("tsUser:"+map.get("company_id"));
		}
		
		//查询列表数据
		List<TsUserEntity> tsUserList = tsUserService.queryList(map);
		int total = tsUserService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(tsUserList, total, limit, page);
		
//		try {
//			String ss = ObjectUtils.toString(pageUtil);
//			System.out.println(pageUtil);
//			JSONObject jsonStr= JSONObject.parseObject(ss);
//			
//		} catch (Exception e) {
//			System.out.println("数据不是json格式");
//			return R.error("数据不是json格式");
//
//		}
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tsuser:info")
	public R info(@PathVariable("id") Long id){
		TsUserEntity tsUser = tsUserService.queryObject(id);
		
		return R.ok().put("tsUser", tsUser);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tsuser:save")
	public R save(HttpServletRequest request){
		
		return tsUserService.save(request);
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("tsuser:update")
	public R update(HttpServletRequest request){
		return tsUserService.update(request);
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tsuser:delete")
	public R delete(@RequestBody Long[] ids){
		tsUserService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
