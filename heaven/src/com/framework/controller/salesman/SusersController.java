package com.framework.controller.salesman;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.entity.TsItemEntity;
import com.framework.entity.TsOrderEntity;
import com.framework.entity.TsUserEntity;
import com.framework.entity.TyOrderServiceEntity;
import com.framework.entity.TyServiceEntity;
import com.framework.entity.TyUserServiceEntity;
import com.framework.service.TsItemService;
import com.framework.service.TsOrderService;
import com.framework.service.TsReportOrderService;
import com.framework.service.TsSchedulingService;
import com.framework.service.TsUserService;
import com.framework.service.TyOrderServiceService;
import com.framework.service.TyServiceService;
import com.framework.service.TyUserServiceService;
import com.framework.session.UserSession;
import com.framework.utils.PageUtils;
import com.framework.utils.R;
import com.framework.utils.StringUtil;

/**
 * 业务员登入
 * @author Administrator
 */
@Controller
@RequestMapping("/salesman/user")
@CrossOrigin
public class SusersController {

	@Autowired
	private TsUserService tsUserService;

	@Autowired
	private TyOrderServiceService tyOrderServiceService;

	@Autowired
	private TyServiceService tyServiceService;

	@Autowired
	private TyUserServiceService tyUserServiceService;

	@Autowired
	private TsSchedulingService tsSchedulingServicer;

	@Autowired
	private TsOrderService tsOrderService;

	@Autowired
	private TsReportOrderService tsReportOrderService;

	@Autowired
	private TsItemService tsItemService;
	/**
	 * 获取业务员信息
	 */
	@ResponseBody
	@RequestMapping(value = "/user_msg", method = RequestMethod.GET)
	public R login(HttpServletRequest request) throws IOException {
		TsUserEntity tsUserEntity = UserSession.salesman(request);

		TsUserEntity userData = tsUserService.queryObject(tsUserEntity.getId());
		userData.setPassword(null);
		userData.setWechatOpenid(null);

		return R.ok().put("data", userData);
	}

	/**
	 * 判断业务员是否微信授权
	 */
	@ResponseBody
	@RequestMapping(value = "/user_wechat", method = RequestMethod.GET)
	public R userWechat(HttpServletRequest request) throws IOException {
		TsUserEntity tsUserEntity = UserSession.salesman(request);

		TsUserEntity userData = tsUserService.queryObject(tsUserEntity.getId());
		if(userData == null){
			return R.error();
		}else{
			if(StringUtil.isEmpty(userData.getWechatOpenid())){
				return R.ok().put("id", userData.getId());
			}else{
				return R.error();
			}
		}
	}

	/**
	 * 清除业务员是否微信授权账号信息
	 */
	@ResponseBody
	@RequestMapping(value = "/clear_wechat", method = RequestMethod.POST)
	public R clearWechat(HttpServletRequest request) throws IOException {
		TsUserEntity tsUserEntity = UserSession.salesman(request);

		TsUserEntity userData = tsUserService.queryObject(tsUserEntity.getId());
		if(userData == null){
			return R.error("查找不到账号信息,请重新登入后操作!");
		}else{
			if(!StringUtil.isEmpty(userData.getWechatOpenid())){
				userData.setWechatHead("");
				userData.setWechatName("");
				userData.setWechatOpenid("");
				tsUserService.updateNow(userData);
				return R.ok();
			}else{
				return R.error("绑定的微信号已清除,无需重复操作!");
			}
		}
	}

	/**
	 * 获取业务员信息
	 */
	@ResponseBody
	@RequestMapping(value = "/update_password", method = RequestMethod.POST)
	public R updatePwd(HttpServletRequest request,String password,String newPassword) throws IOException {
		TsUserEntity tsUserEntity = UserSession.salesman(request);
		tsUserEntity = tsUserService.queryObject(tsUserEntity.getId());

		String passwordFlag = StringUtil.isPassword(password);
		if(!passwordFlag.equals("true")){
			return R.error("原密码:"+passwordFlag);
		}
		String newPasswordFlag = StringUtil.isPassword(newPassword);
		if(!newPasswordFlag.equals("true")){
			return R.error("新密码:"+newPasswordFlag);
		}

		//判断密码是否正确
		if(!new Sha256Hash(password).toHex().trim().equals(tsUserEntity.getPassword().trim())){
			return R.error("原密码错误,请重新填写!");
		}

		//加密密码  sha256加密
		tsUserEntity.setPassword(new Sha256Hash(newPassword).toHex());
		tsUserService.updateNow(tsUserEntity);

		return R.ok();
	}

	/**
	 * 修改业务信息
	 */
	@ResponseBody
	@RequestMapping(value = "/tsuser_update", method = RequestMethod.POST)
	public R update(HttpServletRequest request){
		return tsUserService.updateMy(request);
	}

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/tyorderservice_list")
	public R list(HttpServletRequest request,Integer page, Integer limit){
		TsUserEntity tsUserEntity = UserSession.salesman(request);
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("tsUserid", tsUserEntity.getId());

		List<TyOrderServiceEntity> tyOrderServiceList = null ;
		int total = 0 ;

		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "keyword"))){
			map.put("keywod", StringUtil.toUTF8One(request, "keyword"));
		}
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "tsUserStatus")) && !StringUtil.toUTF8One(request, "tsUserStatus").trim().equals("-1")){
			map.put("tsUserStatus", StringUtil.toUTF8One(request, "tsUserStatus"));
		}

		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "status")) && StringUtil.toUTF8One(request, "status").trim().equals("error")){
			map.put("priceStatus", "0");
			map.put("status", "1");
		}else if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "status")) && StringUtil.toUTF8One(request, "status").trim().equals("ok")){
			/*map.put("priceStatus", "1");*/
			// 已完成  ok  审核通过,客户确认,(业务员已确认)
			//map.put("priceStatusTwo", "ok");
			map.put("status", "1");
			map.put("userStatus", "1");
			map.put("auditState","1");
		}else if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "status")) && StringUtil.toUTF8One(request, "status").trim().equals("ywok")){
			//已确认    1-已付款   1-业务员已确认     客户确定订单服务完成：0-未确认
			//进入条件：业务员确认，客户未确认  
			//移除条件：付款完成，线下付款，审核通过，客户确认
			//map.put("priceStatusTwo", "ok");
			//map.put("priceStatus", "1");  //price_status 是否付款：0-待付款，1-已付款，2-线下打款
			//map.put("status", "1");//订单状态：0-服务中，1-业务员已确认
			//map.put("userStatus", "0");//客户确定订单服务完成：0-未确认，1-已确认     
			map.put("fwz","1");
			map.put("auditStatusOne", "error");//审核未通过，未审核, (业务员已确认)  /(客户确认/客户未确认)
			//map.put("auditStatusTwo", "error");//审核通过,客户未确认,(业务员已确认)

			tyOrderServiceList = tyOrderServiceService.queryListSure(map);
			total = tyOrderServiceService.queryTotalSure(map); 
		}else if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "status")) && StringUtil.toUTF8One(request, "status").trim().equals("fuwuzhong")){
			//服务中   ，1-已付款，2-线下打款     现在：0-待付款
			map.put("priceStatusTwo", "ok");
			map.put("status", "0");//0-服务中
			map.put("fwz", "3");

		}else if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "status")) && StringUtil.toUTF8One(request, "status").trim().equals("weifukuan")){
			//新订单  0-待付款 0-服务中
			map.put("fwz", "2");
			map.put("priceStatus", "0");
			map.put("status", "0");
		}else{
			if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "priceStatus")) && !StringUtil.toUTF8One(request, "priceStatus").trim().equals("-1")){
				map.put("priceStatus", StringUtil.toUTF8One(request, "priceStatus"));
			}
			if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "status")) && !StringUtil.toUTF8One(request, "status").trim().equals("-1")){
				map.put("status", StringUtil.toUTF8One(request, "status"));
			}
		}

		//查询列表数据
		if(!StringUtil.isEmpty(StringUtil.toUTF8One(request, "status")) && !StringUtil.toUTF8One(request, "status").trim().equals("ywok")){
			tyOrderServiceList = tyOrderServiceService.queryList(map);
			total = tyOrderServiceService.queryTotal(map);
		}
		PageUtils pageUtil = new PageUtils(tyOrderServiceList, total, limit, page);

		return R.ok().put("page", pageUtil);
	}


	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/tyorderservice_info/{id}")
	public R info(HttpServletRequest request,@PathVariable("id") Long id){
		TsUserEntity tsUserEntity = UserSession.salesman(request);
		TyOrderServiceEntity tyOrderService = tyOrderServiceService.queryObject(id);
		if(!tsUserEntity.getId().equals(tyOrderService.getTsUserid())){
			return R.error("查找不到订单信息,可能该订单未派给您!");
		}

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
	@RequestMapping("/tyorderservice_service_info")
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
	 * 修改服务详情
	 */
	@ResponseBody
	@RequestMapping("/tyorderservice_updatefw")
	public R updatefw(HttpServletRequest request,Long orderId,String price,String note,String fuwuIds,String deposit,int isCollect){
		TsUserEntity tsUserEntity = UserSession.salesman(request);
		TyOrderServiceEntity tyOrderService = tyOrderServiceService.queryObject(orderId);
		if(!tsUserEntity.getId().equals(tyOrderService.getTsUserid())){
			return R.error("查找不到订单信息,可能该订单为派给您!");
		}
		if(StringUtil.isEmpty(price) && !StringUtil.isEmpty(fuwuIds)){
			return R.error("请填写订单服务总价!");
		}
		if(StringUtil.isEmpty(price) && StringUtil.isEmpty(fuwuIds)){
			return R.ok();
		}
		if((isCollect == 1 && (deposit.compareTo("0.00") == 0 || deposit.compareTo("0") == 0)) ||
			(isCollect == 0 && (deposit.compareTo("0.00") == 1 ))) {
			return R.error("请确认是否收取订金!");
		}
		
		return tyOrderServiceService.updatefw(orderId,new BigDecimal(price),note,fuwuIds, deposit,isCollect);
	}

	/**
	 * 订单服务已完成
	 */
	@ResponseBody
	@RequestMapping("/ok_service_order")
	public R okServiceOrder(HttpServletRequest request,Long orderId){
		TsUserEntity tsUserEntity = UserSession.salesman(request);

		TyOrderServiceEntity tyOrderService = tyOrderServiceService.queryObject(orderId);
		if(StringUtil.isEmpty(tyOrderService)){
			return R.error("查找不到订单信息,可能该订单未派给您!");
		}
		if(!tsUserEntity.getId().equals(tyOrderService.getTsUserid())){
			return R.error("查找不到订单信息,可能该订单为派给您!");
		}
		if(tyOrderService.getStatus() == 1){
			return R.error("该订单服务已完成,不能继续操作!");
		}
		//		if(tyOrderService.getPriceStatus() == 0){
		//			return R.error("该订单用户未付款不可操作服务完成!");
		//		}
		
		
		return tyOrderServiceService.updateFuOk(orderId);
	}
	/**
	 * 订单服务线下打款
	 */
	@ResponseBody
	@RequestMapping("/xianxia_service_order")
	public R xianXiaServiceOrder(HttpServletRequest request,Long orderId){
		TsUserEntity tsUserEntity = UserSession.salesman(request);

		TyOrderServiceEntity tyOrderService = tyOrderServiceService.queryObject(orderId);
		if(StringUtil.isEmpty(tyOrderService)){
			return R.error("查找不到订单信息,可能该订单为派给您!");
		}
		if(!tsUserEntity.getId().equals(tyOrderService.getTsUserid())){
			return R.error("查找不到订单信息,可能该订单为派给您!");
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
	public R falseServiceOrder(HttpServletRequest request,Long orderId){
		TsUserEntity tsUserEntity = UserSession.salesman(request);

		TyOrderServiceEntity tyOrderService = tyOrderServiceService.queryObject(orderId);
		if(StringUtil.isEmpty(tyOrderService)){
			return R.error("查找不到订单信息,可能该订单为派给您!");
		}
		if(!tsUserEntity.getId().equals(tyOrderService.getTsUserid())){
			return R.error("查找不到订单信息,可能该订单为派给您!");
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
	 * 查询今日值班和同组人员
	 */
	@ResponseBody
	@RequestMapping("/query/scheduling")
	public R queryTsScheduling(Long tsUserId) {
		String[] account = tsSchedulingServicer.queryTsScheduling(tsUserId); 
		return R.ok().put("account", account);
	}

	/**
	 * 转单
	 */
	@ResponseBody
	@RequestMapping("/change/scheduling")
	public R changeTsScheduling(String account,String number) {

		tsSchedulingServicer.changeTsScheduling(account,number); 

		return R.ok("转班成功！");

	}

	/**
	 * 提交订单（头标签）
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reportTsOrder/save")
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

	@ResponseBody
	@RequestMapping("/reportTsItem/save")
	public R reportTsItem(@RequestBody TsItemEntity[] itemsEntity){
		TsItemEntity tsItem = itemsEntity[0];
		int tsIt = tsItemService.queryObjectByNumber(tsItem.getNumber());
		if(tsIt == 0){
			tsItemService.saveList(itemsEntity);
		} else {
			tsItemService.updateList(itemsEntity);
		}

		return R.ok("上报订单成功！");
	}
	
	@ResponseBody
	@RequestMapping("/reportTsItem/tsItems")
	public R reportTsItems() {
		
		
		return null;
	}

}




