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
import com.framework.entity.TcUserWechatEntity;
import com.framework.entity.TcUserWithdrawalEntity;
import com.framework.service.TcUserService;
import com.framework.service.TcUserWechatService;
import com.framework.service.TcUserWithdrawalService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;
import com.framework.utils.StringUtil;


/**
 * 提现申请
 * 
 * @author R & D
 * @email 
 * @date 2019-05-12 10:16:52
 */
@Controller
@RequestMapping("tcuserwithdrawal")
public class TcUserWithdrawalController {
	@Autowired
	private TcUserWithdrawalService tcUserWithdrawalService;
	
	@Autowired
	private TcUserWechatService tcUserWechatService;
	
	@Autowired
	private TcUserService tcUserService;
	
	@RequestMapping("/tcuserwithdrawal.html")
	public String list(){
		return "tcuserwithdrawal/tcuserwithdrawal.html";
	}
	
	@RequestMapping("/tcuserwithdrawal_add.html")
	public String add(){
		return "tcuserwithdrawal/tcuserwithdrawal_add.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("tcuserwithdrawal:list")
	public R list(HttpServletRequest request,Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "keyword"))){
			map.put("keywod", StringUtil.toUTF8One(request, "keyword"));
		}
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "status")) && !StringUtil.toUTF8One(request, "status").trim().equals("-1")){
			map.put("status", StringUtil.toUTF8One(request, "status"));
		}
		
		//查询列表数据
		List<TcUserWithdrawalEntity> tcUserWithdrawalList = tcUserWithdrawalService.queryList(map);
		int total = tcUserWithdrawalService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tcUserWithdrawalList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tcuserwithdrawal:info")
	public R info(@PathVariable("id") Long id){
		TcUserWithdrawalEntity tcUserWithdrawal = tcUserWithdrawalService.queryObject(id);
		
		return R.ok().put("tcUserWithdrawal", tcUserWithdrawal);
	}
	
	@ResponseBody
	@RequestMapping("/info_user_wechat/{id}")
	@RequiresPermissions("tcuserwithdrawal:info")
	public R infoUserWechat(@PathVariable("id") Long id){
		TcUserWechatEntity tcUserWithdrawal = new TcUserWechatEntity();
		tcUserWithdrawal.setUserId(id);
		List<TcUserWechatEntity> list = tcUserWechatService.queryObjectByKey(tcUserWithdrawal);
		
		Map<String, Object> data = new HashMap<>();
		
		if(list.isEmpty() || list.size() <= 0){
			data.put("wechatStatus", 0);//没有上传
		}else{
			data.put("wechatStatus", 1);//上传
			data.put("wechat", list.get(0).getWechat());
		}
		TcUserEntity tcUserEntity = tcUserService.queryObject(id);
		tcUserEntity.setOpenid(null);
		tcUserEntity.setWallet(null);
		data.put("user", tcUserEntity);
		return R.ok().put("data", data);
	}
	
	/**
	 * 保存
	 *//*
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tcuserwithdrawal:save")
	public R save(@RequestBody TcUserWithdrawalEntity tcUserWithdrawal){
		tcUserWithdrawalService.save(tcUserWithdrawal);
		
		return R.ok();
	}*/
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/setting")
	@RequiresPermissions("tcuserwithdrawal:update")
	public R updateSetting(Long id,Integer type,String note){
		return tcUserWithdrawalService.updateSetting(id,type,note);
	}
	
	/**
	 * 删除
	 *//*
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tcuserwithdrawal:delete")
	public R delete(@RequestBody Long[] ids){
		tcUserWithdrawalService.deleteBatch(ids);
		
		return R.ok();
	}*/
	
}
