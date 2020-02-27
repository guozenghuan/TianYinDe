package com.framework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品与服务
 * @author GZH
 *
 */
@Controller
@RequestMapping("/tsItemAndService")
public class TsItemAndTotalServiceController {
	
	@RequestMapping("/tsItemAndService.html")
	public String tsItemAndService(){
		
		return "tsItemAndService/tsItemAndService.html";
	}
	@RequestMapping("/tsItemAndService_add.html")
	public String itemAndService(){
		
		return "tsItemAndService/tsItemAndService_add.html";
	}
	
	

}
