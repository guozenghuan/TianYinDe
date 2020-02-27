package com.framework.controller.customer;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.entity.ModulEntity;
import com.framework.entity.TcUserEntity;
import com.framework.entity.TyCommodityEntity;
import com.framework.entity.TyHelpEntity;
import com.framework.entity.TyInvitationCashmoneyEntity;
import com.framework.entity.TyInvitationCommentEntity;
import com.framework.entity.TyInvitationGiftEntity;
import com.framework.entity.TyServiceEntity;
import com.framework.entity.TyVideoEntity;
import com.framework.service.ModulService;
import com.framework.service.TcUserService;
import com.framework.service.TyCommodityService;
import com.framework.service.TyHelpService;
import com.framework.service.TyInvitationCashmoneyService;
import com.framework.service.TyInvitationCommentService;
import com.framework.service.TyInvitationGiftService;
import com.framework.service.TyServiceService;
import com.framework.service.TyVideoService;
import com.framework.session.MySessionContext;
import com.framework.session.UserSession;
import com.framework.utils.ConfigUtil;
import com.framework.utils.PageUtils;
import com.framework.utils.R;
import com.framework.utils.StringUtil;
import com.framework.utils.WechatMenu;
import com.framework.utils.wechat.AuthUtil;
import com.framework.utils.wechat.WxUtil;

/**
 * 公开接口不需要登入
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/customer")
@CrossOrigin
public class COpentController {
	
	@Autowired
	private TyCommodityService tyCommodityService;
	
	@Autowired
	private TyServiceService tyServiceService;
	
	@Autowired
	private TyHelpService tyHelpService;
	
	@Autowired
	private TyVideoService tyVideoService;
	
	@Autowired
	private ModulService modulService;
	
	@Autowired
	private TyInvitationCommentService tyInvitationCommentService;
	
	@Autowired
	private TyInvitationCashmoneyService tyInvitationCashmoneyService;
	
	@Autowired
	private TyInvitationGiftService tyInvitationGiftService;
	
	@Autowired
	private TcUserService tcUserService;
	
	@ResponseBody
    @RequestMapping("/test_login")
    public R wechatCode(HttpSession session,HttpServletRequest request,Long id) {
		//添加用户信息到session中
		session.setAttribute("customerUser", tcUserService.queryObject(id));
		MySessionContext mc = MySessionContext.getInstance();
		mc.addSession(session);
		return R.ok("登入成功!");
	}
	
	@ResponseBody
    @RequestMapping("/createMenu")
    public R createMenu(HttpSession session,HttpServletRequest request) {
		WechatMenu.createMenu();
		return R.ok("成功!");
	}
	
	/**
	 * 获取商品信息
	 */
	@ResponseBody 
	@RequestMapping(value = "/commodity", method = RequestMethod.GET)
	public R commodity(HttpServletRequest request,Integer page) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * 10);
		map.put("limit", 10);
		map.put("status", 0);
		
		//查询列表数据
		List<TyCommodityEntity> tyCommodityList = tyCommodityService.queryList(map);
		int total = tyCommodityService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tyCommodityList, total, 10, page);
		
		return R.ok().put("data", pageUtil);
	}
	
	@ResponseBody
    @RequestMapping("/share_callback")
	public void callBack(HttpServletRequest request,HttpServletResponse response,String url){
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 微信分享接口
	 * @param request
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value = "/wxshare", method = RequestMethod.POST)
	public R wxShare(HttpServletRequest request,String url){
		try {
			return R.ok().put("data", WxUtil.getWechatSharePareme(url));
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return R.error("获取分享信息错误,请重新分享!");
	}
	
	/**
	 * 服务信息
	 */
	@ResponseBody 
	@RequestMapping(value = "/service", method = RequestMethod.GET)
	public R service(HttpServletRequest request) {
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
		
		Map<String, Object> map = new HashMap<>();
		map.put("liucheng", jsonObjectLC);
		map.put("fuwu", fuwu);
		map.put("jiage", jiage.get(0));
		
		return R.ok().put("data", map);
	}
	
	/**
	 * 获取平台帮助信息
	 */
	@ResponseBody 
	@RequestMapping(value = "/help_msg", method = RequestMethod.GET)
	public R helpMsg(HttpServletRequest request,Integer page) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * 10);
		map.put("limit", 10);
		
		//查询列表数据
		List<TyHelpEntity> tyHelpList = tyHelpService.queryList(map);
		int total = tyHelpService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tyHelpList, total, 10, page);
		
		return R.ok().put("data", pageUtil);
	}
	
	/**
	 * 获取视频MV信息
	 */
	@ResponseBody 
	@RequestMapping(value = "/video_mv", method = RequestMethod.GET)
	public R videoMv(HttpServletRequest request,Integer page) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * 10);
		map.put("limit", 10);
		map.put("status", 0);
		
		//查询列表数据
		List<TyVideoEntity> tyVideoList = tyVideoService.queryList(map);
		int total = tyVideoService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(tyVideoList, total, 10, page);
		
		return R.ok().put("data", pageUtil);
	}
	
	/**
	 * 获取系统模板
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/system_moduls", method = RequestMethod.GET)
	public R moduls(HttpServletRequest request,Integer page){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * 10);
		map.put("limit", 10);
		map.put("modulType", "system");
		map.put("userId", 0);
		map.put("status", "show");
		map.put("moduylNameAndIdFlag", "moduylNameAndIdFlag");
		
		//查询列表数据
		List<ModulEntity> modulList = modulService.queryListByNow(map);
		int total = modulService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(modulList, total, 10, page);
		
		return R.ok().put("data", pageUtil);
	}
	
	/**
	 * 获取主模板下所有模板信息,不可编辑
	 *//*
	@ResponseBody
	@RequestMapping(value = "/system_modul_all_info", method = RequestMethod.GET)
	public R modulsAllInfo(Long id){
		if(StringUtil.isEmpty(id)){
			return R.error("查找不到该模板信息,请重新选择模板!");
		}
		Boolean seetingBtn = false;//不可编辑
		ModulEntity modul = modulService.queryObject(id,seetingBtn);
		return R.ok().put("data", modul);
	}
	
	*//**
	 * 获取模板信息
	 *//*
	@ResponseBody
	@RequestMapping(value = "/info_modul", method = RequestMethod.GET)
	public R modulInfo(Long id,String modulCode){
		return modulService.queryModulInfo(id,modulCode);
	}
	
	
	/**
	 * 获取主模板下所有模板信息,不可编辑
	 */
	@ResponseBody
	@RequestMapping(value = "/share_modul_all_info", method = RequestMethod.GET)
	public R shareModulsAllInfo(Long id){
		if(StringUtil.isEmpty(id)){
			return R.error("查找不到该模板信息,可能该模板已经被删除!");
		}
		ModulEntity modul = modulService.queryObject(id);
		if(StringUtil.isEmpty(modul)){
			return R.error("查找不到该请帖信息,可能请帖已经被删除!");
		}
		return R.ok().put("data", modul);
	}
	
	/**
	 * 获取模板评论
	 */
	@ResponseBody
	@RequestMapping(value = "/share_modul_comment", method = RequestMethod.GET)
	public R commentShare(Long modulId){
		if(StringUtil.isEmpty(modulId)){
			return R.error("查找不到该模板评论信息,可能该模板已经被删除!");
		}
		
		List<TyInvitationCommentEntity> list = tyInvitationCommentService.queryListRand(modulId);
		List<String> data = new ArrayList<>();
		for(TyInvitationCommentEntity entity : list){
			if(!StringUtil.isEmpty(entity.getName())){
				data.add(entity.getName().substring(0, 1)+"** : "+entity.getNote());
			}
		}
		
		//随机获取50条模板评论信息
		return R.ok().put("data", data);
	}
	
	/**
	 * 获取模板奠金、赠送的平台礼物
	 */
	@ResponseBody
	@RequestMapping(value = "/share_modul_moneyandgift", method = RequestMethod.GET)
	public R moneyGiftShare(Long modulId){
		if(StringUtil.isEmpty(modulId)){
			return R.error("查找不到该模板信息,可能该模板已经被删除!");
		}

		List<String> data = new ArrayList<>();
		
		//随机获取25条奠金信息
		List<TyInvitationCashmoneyEntity> listDianJin = tyInvitationCashmoneyService.queryListRand(modulId);
		for(TyInvitationCashmoneyEntity cashmoneyEntity : listDianJin){
			data.add(cashmoneyEntity.getName().substring(0, 1)+"** : 奠礼 ￥ "+cashmoneyEntity.getMoney());
		}
		
		//随机获取25条礼物信息
		List<TyInvitationGiftEntity> listLiWu = tyInvitationGiftService.queryListRand(modulId);
		for(TyInvitationGiftEntity giftEntity : listLiWu){
			data.add(giftEntity.getName().substring(0, 1)+"** : 奠礼 "+giftEntity.getGiftName());
		}
		
		//打乱list集合
		Collections.shuffle(data);
		
		return R.ok().put("data", data);
	}
	
	
	/**
	 * 获取礼物码
	 */
	@ResponseBody
	@RequestMapping(value = "/share_modul_giftcode", method = RequestMethod.GET)
	public R giftShare(Long modulId){
		if(StringUtil.isEmpty(modulId)){
			return R.error("查找不到该模板礼物信息,可能该模板已经被删除!");
		}

		SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long currentTime = System.currentTimeMillis();
		
		Map<String, Object> map = new HashMap<>();
		map.put("modulId", modulId);
		map.put("etime", sDateFormat.format(new Date(currentTime)));
		currentTime -= 10*1000;//获取10秒区间
		map.put("stime", sDateFormat.format(new Date(currentTime)));
		
		//获取礼物码
		String code = "";
		List<TyInvitationGiftEntity> listLiWu = tyInvitationGiftService.queryByCode(map);
		if(!listLiWu.isEmpty() && listLiWu.size()>0){
			code = listLiWu.get(0).getGiftCode();
		}
		
		return R.ok().put("data", code);
	}
	
	
}
