package com.framework.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.entity.TsSchedulingCommissionEntity;
import com.framework.service.TsSchedulingCommissionService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;

@Controller
@RequestMapping("/tscommission")
public class TsSchedulingCommissionController {
	@Autowired
	private TsSchedulingCommissionService tsSchedulingCommissionService;
	
	@RequestMapping("/tscommission_add.html")
	public String commissionAdd(){
		return "/tscommission/tscommission_add.html";
	}
	@RequestMapping("/tscommission.html")
	public String commission(){
		return "tscommission/tscommission.html";
	}
	
	@ResponseBody
	@RequestMapping("/info/name")
	public String[] tsname(){
		String[] tsName = tsSchedulingCommissionService.queryTsAccount();
		//String[] tsName = {"001","002","003"};
		return tsName;
	}
	
	@ResponseBody
	@RequestMapping("/check/commission")
	public R checkCommisssion(String account,String time,
			Integer limit, Integer page){	
		Map<String, Object> map = new HashMap<>();
		map.put("account", account);
		map.put("time", time);
		map.put("page", (page-1)*limit);
		map.put("limit",limit);
		int total = tsSchedulingCommissionService.queryTotal(map);
		System.out.println("total:"+total);
		List<TsSchedulingCommissionEntity> commissionList = 
		tsSchedulingCommissionService.queryListByMap(map);
		
		PageUtils pageUtil = new PageUtils(commissionList,total,limit,page);
		
		return R.ok().put("page", pageUtil);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(Long tsUserId,String number){
		
		tsSchedulingCommissionService.delete(tsUserId,number);
		
		return R.ok();
		
	}
	
	
	
}











