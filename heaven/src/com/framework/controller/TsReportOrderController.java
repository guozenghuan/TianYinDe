package com.framework.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.entity.TsItemsAndTotalEntity;
import com.framework.entity.TsOrderEntity;
import com.framework.entity.TsReportOrderEntity;
import com.framework.service.TsReportOrderService;
import com.framework.service.TsUploadOrderService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;
import com.framework.utils.TransactionRollBack;

/**
 * 业务员提交订单
 * @author GZH
 *
 */
@Controller
@RequestMapping("/tsReportOrder")
public class TsReportOrderController {
	@Autowired
	private TsReportOrderService tsReportOrderService;
	@Autowired
	private TsUploadOrderService tsUploadOrderService;

	@RequestMapping("/tsReportOrder_audit.html")
	public String auditorhtml(){
		return "tsReportOrder/tsReportOrder_audit.html";
	}
	@RequestMapping("/tsReportOrder.html")
	public String html(){
		return "tsReportOrder/tsReportOrder.html";
	}

	/**
	 * 查询已提交订单列表
	 */
	@ResponseBody
	@RequestMapping("/orderList")
	public R orderList(Long sta, Long tsUserId, Integer limit, Integer page){
		
		//分页起点
		Integer page01 = (page-1)*limit;
		//orderList 
		List<TsReportOrderEntity> orderList = null;
		//int total
		int total = 0;
		//业务员查询审核订单 
		if(sta == 0){
			//查询列表数据       sql可以优化?
			orderList = tsReportOrderService.queryOrderList(tsUserId,limit,page01);
			//查询总页数
			total = tsReportOrderService.queryOrderTotalByUserId(tsUserId);
		
			//审核未通过  (sta=1)
		}else if (sta == 1){
			//查询列表数据
			orderList = tsReportOrderService.queryOrderLists(tsUserId,limit,page01);
			//查询总页数
			total = tsReportOrderService.queryOrderTotalByTsUser();
	
			//审核已通过  (sta=2)
		}else if (sta == 2) {
			orderList = tsReportOrderService.queryOrderListCheck(limit,page01);
			//查询总页数
			total = tsReportOrderService.queryOrderTotalByStatuOne();
			//未审核订单  (sta=3)
		}else if (sta == 3) {
			
			orderList = tsReportOrderService.queryOrderListNoCheck(limit,page01);
			//查询总页数
			total = tsReportOrderService.queryOrderTotalByStatuTwo();
		}
		
		PageUtils pageUtils = new PageUtils(orderList,total,limit,page);
		return R.ok().put("page", pageUtils);
	}

	/**
	 * 删除提交的订单
	 */
	@ResponseBody
	@RequestMapping("/orderDelete")
	//@RequiresPermissions("")
	public R delete(Long tsUserId,String number){	
		tsReportOrderService.deleteReportOrder(tsUserId, number);
		return R.ok("删除成功！");
	}

	/**
	 * 审核           
	 */
	@ResponseBody
	@RequestMapping("/check")
	public R check(Long tsUserId,String number,Long status){

		tsReportOrderService.checkReportOrder(tsUserId,number,status);
		return R.ok("审核成功！");	
	}

	/**
	 * 提交、更改订单     ???
	 */
	@ResponseBody
	@RequestMapping("/orderUpload")
	public R orderUpload(HttpServletRequest request){
		
		//tsUploadOrderService.uploadOrder(tsItems,tsOrder,itemsAndTotal);
		
		return R.ok("提交成功！");	
	}

	/**
	 * 查询订单
	 */
	@ResponseBody
	@RequestMapping("/orderInfo")
	public R orderInfo(Long tsUserId,String number){
		Map<String,Object>	map = 
		tsUploadOrderService.infoOrder(tsUserId,number);

		return R.ok(map);
	}


}


















