package com.framework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.entity.TsOrderEntity;
import com.framework.service.TsOrderService;
import com.framework.service.TsReportOrderService;
import com.framework.utils.R;

/**
 * 审核/提交订单 01
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/reportTsOrder")
public class TsOrderController {
	@Autowired
	private TsOrderService tsOrderService;
	@Autowired 
	private TsReportOrderService tsReportOrderService;

	/**
	 * 提交订单（头标签）
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R tsOrder(@RequestBody TsOrderEntity order){
		order.setNumber(order.getNumber().trim());
		if(order.getTsNumber() == 0 || order.getTsNumber() == null){
			return R.error("请填写业务员人数！");
		};
		if(tsOrderService.queryPageByUN(order.getTsUserId(),order.getNumber().trim()) == 0){
			tsOrderService.save(order);
		} else {
			if(tsReportOrderService.queryCheckPageByUN(order.getTsUserId(),order.getNumber().trim()) != 0){
				return R.error("订单已通过审核，不可再次提交！");
			};
			//未审核可再次提交
			order.setNumber(order.getNumber().trim());
			tsOrderService.update(order);
		};
		
		return R.ok("保存成功!");

	}

	/**
	 * 查询订单头信息
	 * @param tsUserId 业务员ID
	 * @param number 订单号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/info")
	public TsOrderEntity infoOrder(Long tsUserId,String number ){
		TsOrderEntity order = tsOrderService.info(tsUserId,number);   
		return order;
		
	}


}
