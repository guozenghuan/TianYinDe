package com.framework.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.entity.TsItemEntity;
import com.framework.service.TsItemService;
import com.framework.utils.PageUtils;
import com.framework.utils.R;
/**
 * 审核/提交订单 02
 * @author GZH
 *
 */
@Controller
@RequestMapping("/reportTsItem")
public class TsItemController {
	@Autowired
	private TsItemService tsItemService;

	@ResponseBody
	@RequestMapping("/save")
	public R reportTsItem(@RequestBody TsItemEntity[] itemsEntity){
		TsItemEntity tsItem = itemsEntity[0];
		int tsIt = tsItemService.queryObjectByNumber(tsItem.getNumber());
		if(tsIt == 0){
			tsItemService.saveList(itemsEntity);
		} else {
			tsItemService.updateList(itemsEntity);
		}

		return R.ok();
	}

	@ResponseBody
	@RequestMapping("/list")
	public R tsitemsList(Long tsUserId,String number, Integer limit, Integer page){
		Map<String,Object> map = new HashMap<>();
		map.put("tsUserId", tsUserId);
		map.put("number", number);
		map.put("page", (page-1)*limit);
		map.put("limit", limit);
		int total = tsItemService.queryItemsTotal(tsUserId,number);
		List<TsItemEntity> tsItemsList = tsItemService.queryItemsList(map); 
		PageUtils pageUtil = new PageUtils(tsItemsList, total, limit, page);
		
		return R.ok().put("page", pageUtil);
	}

}




