package com.framework.controller;

import java.math.BigDecimal;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.framework.entity.TsItemsAndTotalEntity;
import com.framework.service.TsItemsAndTotalService;
import com.framework.utils.R;

/**
 * 审核订单 03
 * @author 
 *
 */
@Controller
@RequestMapping("/reportTotal")
public class TsReportTotalController {
	@Autowired
	private TsItemsAndTotalService tsItemsAndTotalService;
	
	@ResponseBody
	@RequestMapping("/info")
	public TsItemsAndTotalEntity info(Long tsUserId ,String number){
		
		return tsItemsAndTotalService.queryObjectByUN(tsUserId,number);

	}
	
	/**
	 * 预审核，审核
	 */
	@ResponseBody
	@RequestMapping("/check")
    //@RequiresPermissions("")
	public R check(Long tsUserId,String number,Long check,
			String admin,BigDecimal b,String commission){
		
		R r = tsItemsAndTotalService.check(tsUserId,number,check,admin,b,commission);
		return r ;
		
	}
}





















