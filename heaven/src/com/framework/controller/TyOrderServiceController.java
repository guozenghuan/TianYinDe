package com.framework.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.framework.entity.TsUserEntity;
import com.framework.entity.TyOrderServiceEntity;
import com.framework.entity.TyServiceEntity;
import com.framework.entity.TyUserServiceEntity;
import com.framework.service.TyOrderServiceService;
import com.framework.service.TyServiceService;
import com.framework.service.TyUserServiceService;
import com.framework.session.UserSession;
import com.framework.utils.PageUtils;
import com.framework.utils.R;
import com.framework.utils.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 服务订单
 * 
 * @author R & D
 * @email 
 * @date 2019-04-08 16:38:46
 */
@Controller
@RequestMapping("tyorderservice")
public class TyOrderServiceController {
	@Autowired
	private TyOrderServiceService tyOrderServiceService;

	@Autowired
	private TyServiceService tyServiceService;

	@Autowired
	private TyUserServiceService tyUserServiceService;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping("/tyorderservice.html")
	public String list(){
		return "tyorderservice/tyorderservice.html";
	}
	@RequestMapping("/quotation.html")
	public String quotation(){
		return "quotation/quotation.html";
	}

	@RequestMapping("/tyorderservice_add.html")
	public String add(){
		return "tyorderservice/tyorderservice_add.html";
	}

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	//	@RequiresPermissions("tyorderservice:list")
	public R list(HttpServletRequest request,Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);

		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "keyword"))){
			map.put("keywod", StringUtil.toUTF8One(request, "keyword"));
		}
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "tsUserStatus")) && !StringUtil.toUTF8One(request, "tsUserStatus").trim().equals("-1")){
			map.put("tsUserStatus", StringUtil.toUTF8One(request, "tsUserStatus"));
		}

		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "status")) && StringUtil.toUTF8One(request, "status").trim().equals("error")){
			//error      已失败
			map.put("priceStatus", "0");
			map.put("status", "1");
		}else if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "status")) && StringUtil.toUTF8One(request, "status").trim().equals("ok")){
			//ok         已完成
			map.put("priceStatus", "1");
			map.put("status", "1");
			map.put("userStatus", "1");
		}else if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "status")) && StringUtil.toUTF8One(request, "status").trim().equals("ywok")){
			//ywok       已确认
			map.put("priceStatus", "1");
			map.put("status", "1");
			map.put("userStatus", "0");
		
		}else if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "status")) && StringUtil.toUTF8One(request, "status").trim().equals("fuwuzhong")){
			//fuwuzhong  服务中
			map.put("priceStatusTwo", "ok");
			map.put("status", "0");
		}else if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "status")) && StringUtil.toUTF8One(request, "status").trim().equals("quotation")){
			map.put("pid", "-100");
		}else{
			if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "priceStatus")) && !StringUtil.toUTF8One(request, "priceStatus").trim().equals("-1")){
				map.put("priceStatus", StringUtil.toUTF8One(request, "priceStatus"));
			}
			if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "status")) && !StringUtil.toUTF8One(request, "status").trim().equals("-1")){
				map.put("status", StringUtil.toUTF8One(request, "status"));
			}
		}
		String pre="【列表】";
		logger.info(pre+"参数:map"+ map.toString());		

		//查询列表数据

		List<TyOrderServiceEntity> tyOrderServiceList = tyOrderServiceService.queryList(map);
		int total = tyOrderServiceService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(tyOrderServiceList, total, limit, page);
		return R.ok().put("page", pageUtil);
	}



	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	//	@RequiresPermissions("tyorderservice:info")
	public R info(@PathVariable("id") Long id){
		String pre="【信息】";
		logger.info(pre+"参数:id"+ id);
		TyOrderServiceEntity tyOrderService = tyOrderServiceService.queryObject(id);

		TyUserServiceEntity tyUserService = new TyUserServiceEntity();
		tyUserService.setParentId(id);
		List<TyUserServiceEntity> fuwu = tyUserServiceService.queryObjectByKey(tyUserService);

		return R.ok().put("tyOrderService", tyOrderService).put("fuwu", fuwu);
	}

	/**
	 * 获取服务信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/service_info")
	//	@RequiresPermissions("tyorderservice:info")
	public R serviceFuWuMsg(){
		TyServiceEntity tyService = new TyServiceEntity();
		tyService.setParentId(0L);//只有最大的父亲id

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

		return R.ok().put("service", fuwu);
	}

	/**
	 * 保存
	 */
	/*@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("tyorderservice:save")
	public R save(@RequestBody TyOrderServiceEntity tyOrderService){
		tyOrderServiceService.save(tyOrderService);

		return R.ok();
	}*/


	/**
	 * 派单
	 */
	@ResponseBody
	@RequestMapping("/pandan")
	//	@RequiresPermissions("tyorderservice:update")
	public R paiDan(Long orderId,Long tsUserId){
		String pre="【派单】";
		logger.info(pre+"参数:orderId"+ orderId+",tsUserId:"+tsUserId);	
		return tyOrderServiceService.paiDan(orderId,tsUserId);
	}

	/**
	 * 修改服务详情
	 */
	@ResponseBody
	@RequestMapping("/updatefw")
	//	@RequiresPermissions("tyorderservice:update")
	public R updatefw(Long orderId,String price,String note,String fuwuIds,String deposit,int isCollect){
		String pre="【修改服务详情】";
		logger.info(pre+"参数:orderId"+ orderId+",price:"+price+",note:"+ note+",fuwuIds:"+fuwuIds);
		if(StringUtil.isEmpty(price) && !StringUtil.isEmpty(fuwuIds)){
			return R.error("请填写订单服务总价!");
		}
		if(StringUtil.isEmpty(price) && StringUtil.isEmpty(fuwuIds)){
			return R.ok();
		}
		return tyOrderServiceService.updatefw(orderId,new BigDecimal(price),note,fuwuIds, deposit,isCollect);
	}
	/**
	 * 保存或修改报价单
	 * luog
	 * 20191207
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public R saveOrUpdateQuotation(String id,@RequestParam("pid")String pid){
		Long idL=null;
		if(!StringUtil.isEmpty(id)){
			idL=Long.parseLong(id);
		}		
		Long pidL=null;
		if(!StringUtil.isEmpty(pid)){
			pidL=Long.parseLong(pid);
		}	

		TyOrderServiceEntity entityOld=tyOrderServiceService.queryObject(pidL);
		Integer status  =entityOld.getStatus();
		Integer priceStatus  =entityOld.getPriceStatus();
		Integer userStatus  =entityOld.getUserStatus();
		if(status!=1 || priceStatus!=1 || userStatus!=1){
			return R.error("创建报价单条件是已经支付，客户已确认，业务员也确认！");
		}

		TyOrderServiceEntity entityNew=new TyOrderServiceEntity();
		entityNew.setPid(pidL);
		entityNew.setStatus(1);
		entityNew.setPriceStatus(1);
		entityNew.setUserStatus(1);
		entityNew.setPayStatus(1);
		entityNew.setAuditState(3);

		if(id!=null){
			entityNew.setId(idL);
			tyOrderServiceService.update(entityNew);
		}else{
			tyOrderServiceService.save(entityNew);
		}


		return R.ok().put("entity", entityNew);
	} 

	/**
	 * 修改报价单审核状态
	 * luog
	 * 20191207
	 */
	@ResponseBody
	@RequestMapping("/updateAuditState")
	//	@RequiresPermissions("tyorderservice:update")
	public R updateAuditState(Long id,Integer auditState){
		String pre="【修改报价单审核状态】";
		logger.info(pre+"参数:id"+id+",auditState:"+auditState);
		if(id==null || id<1){
			return R.error("报价单id为空!");
		}
		if(auditState==null || auditState<1){
			return R.error("审核状态为空!");
		}

		return tyOrderServiceService.updateAuditState(id,auditState);
	}
	/**
	 * 删除报价单
	 * luog
	 * 20191207
	 */
	@ResponseBody
	@RequestMapping("/deleteQuotation")
	//	@RequiresPermissions("tyorderservice:update")
	public R deleteQuotation(Long id){
		String pre="【删除报价单】";
		logger.info(pre+"参数:id"+id);
		if(id==null || id<1){
			return R.error("报价单id为空!");
		}

		tyOrderServiceService.delete(id);
		return R.ok();
	}

	/**
	 * 订单服务已完成
	 */
	@ResponseBody
	@RequestMapping("/ok_service_order")
	public R okServiceOrder(Long orderId){

		String pre="【订单服务已完成】";
		logger.info(pre+"参数:orderId"+orderId);
		TyOrderServiceEntity tyOrderService = tyOrderServiceService.queryObject(orderId);
		if(StringUtil.isEmpty(tyOrderService)){
			return R.error("查找不到订单信息!");
		}
		if(tyOrderService.getStatus() == 1){
			return R.error("该订单服务已完成,不能继续操作!");
		}
		if(tyOrderService.getPriceStatus() == 0){
			return R.error("该订单用户未付款不可操作服务完成!");
		}
		return tyOrderServiceService.updateFuOk(orderId);
	}
	/**
	 * 订单服务线下打款
	 */
	@ResponseBody
	@RequestMapping("/xianxia_service_order")
	public R xianXiaServiceOrder(Long orderId){
		String pre="【订单服务线下打款】";
		logger.info(pre+"参数:orderId"+orderId);
		TyOrderServiceEntity tyOrderService = tyOrderServiceService.queryObject(orderId);
		if(StringUtil.isEmpty(tyOrderService)){
			return R.error("查找不到订单信息!");
		}
		if(tyOrderService.getStatus() == 1){
			return R.error("该订单服务已完成,不能继续操作!");
		}
		if(tyOrderService.getPriceStatus() != 0){
			return R.error("该订单用户已付款不可操作线下付款方式!");
		}
		tyOrderService.setPriceStatus(2);
		tyOrderServiceService.update(tyOrderService);
		return R.ok("操作成功!");
	}
	/**
	 * 订单服务交易失败
	 */
	@ResponseBody
	@RequestMapping("/false_service_order")
	public R falseServiceOrder(Long orderId){
		String pre="【订单服务交易失败】";
		logger.info(pre+"参数:orderId"+orderId);
		TyOrderServiceEntity tyOrderService = tyOrderServiceService.queryObject(orderId);
		if(StringUtil.isEmpty(tyOrderService)){
			return R.error("查找不到订单信息!");
		}
		if(tyOrderService.getStatus() == 1){
			return R.error("该订单服务已完成,不能继续操作!");
		}
		if(tyOrderService.getPriceStatus() == 1){
			return R.error("该订单用户已付款不可操作交易失败!");
		}
		return tyOrderServiceService.updateFuOk(orderId);
	}

	/**
	 * 删除
	 */
	/*@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("tyorderservice:delete")
	public R delete(@RequestBody Long[] ids){
		tyOrderServiceService.deleteBatch(ids);

		return R.ok();
	}*/

}
