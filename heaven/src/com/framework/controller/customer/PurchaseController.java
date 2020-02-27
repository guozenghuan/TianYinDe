package com.framework.controller.customer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.entity.ModulEntity;
import com.framework.entity.TbAddressEntity;
import com.framework.entity.TcUserEntity;
import com.framework.entity.TyCommodityEntity;
import com.framework.entity.TyGiftEntity;
import com.framework.entity.TyInvitationCashmoneyEntity;
import com.framework.entity.TyInvitationGiftEntity;
import com.framework.entity.TyOrderCommodityEntity;
import com.framework.entity.TyOrderServiceEntity;
import com.framework.entity.TyServiceEntity;
import com.framework.service.ModulService;
import com.framework.service.TbAddressService;
import com.framework.service.TyCommodityService;
import com.framework.service.TyGiftService;
import com.framework.service.TyInvitationCashmoneyService;
import com.framework.service.TyInvitationGiftService;
import com.framework.service.TyInvitationToinviteService;
import com.framework.service.TyOrderCommodityService;
import com.framework.service.TyOrderServiceService;
import com.framework.service.TyServiceService;
import com.framework.session.UserSession;
import com.framework.utils.ConfigUtil;
import com.framework.utils.OrderCodeFactory;
import com.framework.utils.R;
import com.framework.utils.StringUtil;
import com.framework.utils.TransactionRollBack;
import com.framework.utils.wechat.HttpRequest;
import com.github.wxpay.sdk.WXPayUtil;

/**
 * 购买
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/customer/user")
@CrossOrigin
public class PurchaseController {
	
	@Autowired
	private TyCommodityService tyCommodityService;
	
	@Autowired
	private TyOrderCommodityService tyOrderCommodityService;
	
	@Autowired
	private TbAddressService tbAddressService;
	
	@Autowired
	private TyOrderServiceService tyOrderServiceService;
	
	@Autowired
	private TyServiceService tyServiceService;
	
	@Autowired
	private ModulService modulService;
	
	@Autowired
	private TyInvitationCashmoneyService tyInvitationCashmoneyService;
	
	@Autowired
	private TyInvitationGiftService tyInvitationGiftService;
	
	@Autowired
	private TyGiftService tyGiftService;
	
	@Autowired
	private TyInvitationToinviteService tyInvitationToinviteService;
	
	/**
	 * 购买商品
	 */
	@Transactional
	@ResponseBody 
	@RequestMapping(value = "/purchase_commodity", method = RequestMethod.POST)
	public R purchaseCommodity(HttpServletRequest request,Long commodityId,Long addressId,String userIp) {
		//获取用户信息
		TcUserEntity user = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(user.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		if(StringUtil.isEmpty(userIp)){
			return R.error("获取当前设备IP失败,请联系客服处理!");
		}
		
		//获取用户收获地址信息
		TbAddressEntity address = tbAddressService.queryObject(addressId);
		if(StringUtil.isEmpty(address)){
			return R.error("收货地址信息获取失败,请重新选择收货地址!");
		}
		
		//根据商品id获取商品信息
		TyCommodityEntity commodity = tyCommodityService.queryObject(commodityId);
		if(StringUtil.isEmpty(commodity)){
			return R.error("查询不到改商品信息,请重新刷新页面后再操作!");
		}
		
		
		/**生成商品订单**/
		TyOrderCommodityEntity order = new TyOrderCommodityEntity();
		order.setUserId(user.getId());
		order.setAddressId(addressId);
		order.setCommodityId(commodityId);
		order.setCommodityImage(commodity.getMainImage());
		order.setCommodityTitle(commodity.getTitle());
		order.setCommodityPrice(commodity.getPrice());
		order.setPayStatus(0);
		order.setExpressStatus(0);
		order.setName(address.getName());
		order.setPhone(address.getPhone());
		order.setAddres(address.getAddres());
		order.setStatus(0);
		order.setCreatetime(new Date());
		tyOrderCommodityService.save(order);
		
		//根据用户id生成商品订单号，让单号+订单id(唯一、回调时就懂是哪个订单支付了)
		String number = OrderCodeFactory.toCode(user.getId())+"-"+order.getId();
		order.setNumber(number);
		tyOrderCommodityService.update(order);
		
		try {
			//拼接统一下单地址参数
			Map<String, String> paraMap = new HashMap<String, String>();
			
			paraMap.put("appid", ConfigUtil.getAppID());
			paraMap.put("body", commodity.getTitle());
			paraMap.put("mch_id", ConfigUtil.getMerchant_Number());//商户号
			paraMap.put("nonce_str", WXPayUtil.generateNonceStr());
			paraMap.put("openid", user.getOpenid());//用户的Openid
			paraMap.put("out_trade_no", number);//订单号-要唯一
			paraMap.put("spbill_create_ip", userIp);//用户的ip
			String priceS = String.valueOf(commodity.getPrice().multiply(new BigDecimal(Double.toString(100))).doubleValue());
			if(StringUtil.isIndexOfStr(priceS, ".")){
				priceS = priceS.split("\\.")[0];
			}
			paraMap.put("total_fee",priceS);//付款金额-单位分(元换算为分)  
			paraMap.put("trade_type", "JSAPI");
			paraMap.put("notify_url",ConfigUtil.getWechat_Commodity_Callback());// 此路径是微信服务器调用支付结果通知
			String sign = WXPayUtil.generateSignature(paraMap, ConfigUtil.getMerchant_Api_Secret());
			paraMap.put("sign", sign);
			String xml = WXPayUtil.mapToXml(paraMap);//将所有参数(map)转xml格式
						
			// 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
			String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			
			String xmlStr = HttpRequest.sendPost(unifiedorder_url, xml);//发送post请求"统一下单接口"返回预支付id:prepay_id
			
			//以下内容是返回前端页面的json数据
			String prepay_id = "";//预支付id
			if (xmlStr.indexOf("SUCCESS") != -1) {  
				Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);  
				prepay_id = (String) map.get("prepay_id");  
			}
			Map<String, String> payMap = new HashMap<String, String>();
			payMap.put("appId", ConfigUtil.getAppID());  
			payMap.put("timeStamp", System.currentTimeMillis()/1000+"");  
			payMap.put("nonceStr", WXPayUtil.generateNonceStr());  
			payMap.put("signType", "MD5");
			payMap.put("package", "prepay_id=" + prepay_id);  
			String paySign = WXPayUtil.generateSignature(payMap, ConfigUtil.getMerchant_Api_Secret());  
			payMap.put("paySign", paySign);
			
			return R.ok().put("data", payMap);
		} catch (Exception e) {
			e.printStackTrace();
			//数据回滚
			TransactionRollBack.rollBackUpdate();
			return R.error("系统异常,稍后重试!");
		}
	}

	
	/**
	 * 支付待支付的商品订单
	 */
	@Transactional
	@ResponseBody 
	@RequestMapping(value = "/purchase_commodity_order", method = RequestMethod.POST)
	public R purchaseCommodityOrder(HttpServletRequest request,Long id,String userIp) {
		//获取用户信息
		TcUserEntity user = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(user.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		if(StringUtil.isEmpty(userIp)){
			return R.error("获取当前设备IP失败,请联系客服处理!");
		}
		
		//查询商品订单信息
		TyOrderCommodityEntity commodity = tyOrderCommodityService.queryObject(id);
		if(StringUtil.isEmpty(commodity)){
			return R.error("商品订单不存在,请刷新好重新操作!");
		}
		if(commodity.getPayStatus() != 0){
			return R.error("该订单已支付,无需重复操作!");
		}
		
		try {
			//拼接统一下单地址参数
			Map<String, String> paraMap = new HashMap<String, String>();
			
			paraMap.put("appid", ConfigUtil.getAppID());
			paraMap.put("body", commodity.getCommodityTitle());
			paraMap.put("mch_id", ConfigUtil.getMerchant_Number());//商户号
			paraMap.put("nonce_str", WXPayUtil.generateNonceStr());
			paraMap.put("openid", user.getOpenid());//用户的Openid
			paraMap.put("out_trade_no", commodity.getNumber());//订单号-要唯一
			paraMap.put("spbill_create_ip", userIp);//用户的ip
			String priceS = String.valueOf(commodity.getCommodityPrice().multiply(new BigDecimal(Double.toString(100))).doubleValue());
			if(StringUtil.isIndexOfStr(priceS, ".")){
				priceS = priceS.split("\\.")[0];
			}
			paraMap.put("total_fee",priceS);//付款金额-单位分(元换算为分)  
			paraMap.put("trade_type", "JSAPI");
			paraMap.put("notify_url",ConfigUtil.getWechat_Commodity_Callback());// 此路径是微信服务器调用支付结果通知
			String sign = WXPayUtil.generateSignature(paraMap, ConfigUtil.getMerchant_Api_Secret());
			paraMap.put("sign", sign);
			String xml = WXPayUtil.mapToXml(paraMap);//将所有参数(map)转xml格式
						
			// 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
			String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			
			String xmlStr = HttpRequest.sendPost(unifiedorder_url, xml);//发送post请求"统一下单接口"返回预支付id:prepay_id
			
			//以下内容是返回前端页面的json数据
			String prepay_id = "";//预支付id
			if (xmlStr.indexOf("SUCCESS") != -1) {  
				Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);  
				prepay_id = (String) map.get("prepay_id");  
			}
			Map<String, String> payMap = new HashMap<String, String>();
			payMap.put("appId", ConfigUtil.getAppID());  
			payMap.put("timeStamp", System.currentTimeMillis()/1000+"");  
			payMap.put("nonceStr", WXPayUtil.generateNonceStr());  
			payMap.put("signType", "MD5");
			payMap.put("package", "prepay_id=" + prepay_id);  
			String paySign = WXPayUtil.generateSignature(payMap, ConfigUtil.getMerchant_Api_Secret());  
			payMap.put("paySign", paySign);
			
			return R.ok().put("data", payMap);
		} catch (Exception e) {  
			e.printStackTrace();
			//数据回滚
			TransactionRollBack.rollBackUpdate();
			return R.error("系统异常,稍后重试!");
		}
	}
	
	
	/**
	 * 购买服务
	 */
	@Transactional
	@ResponseBody 
	@RequestMapping(value = "/purchase_service", method = RequestMethod.POST)
	public R purchaseService(HttpServletRequest request,String name,String phone,String userIp) {
		//获取用户信息
		TcUserEntity user = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(user.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		if(StringUtil.isEmpty(userIp)){
			return R.error("获取当前设备IP失败,请联系客服处理!");
		}
		
		if(StringUtil.isEmpty(name)){
			return R.error("请填写联系人!");
		}
		String phoneFlag = StringUtil.isMobile(phone);
		if(!phoneFlag.equals("true")){
			return R.error(phoneFlag);
		}
		
		//获取服务价格
		TyServiceEntity tyService = new TyServiceEntity();
		tyService.setParentId(0L);//只有最大的父亲id
		tyService.setType("jiage");
		List<TyServiceEntity> jiage = tyServiceService.queryObjectByKeySort(tyService);
		if(jiage.isEmpty() || jiage.size() <= 0){
			return R.error("查询不到服务信息,请重新刷新页面后再操作!");
		}
		
		BigDecimal jiageService = new BigDecimal(jiage.get(0).getName());
		
		/**生成服务订单**/
		TyOrderServiceEntity order = new TyOrderServiceEntity();
		order.setUserid(user.getId());
		order.setServicePrice(jiageService);
		order.setPayStatus(0);
		order.setName(name);
		order.setPhone(phone);
		order.setCreatetime(new Date());
		order.setTsUserStatus(0);
		order.setServiceStatus(0);
		order.setPriceStatus(0);
		order.setStatus(0);
		order.setUserStatus(0);
		order.setFwz(2);
//		if(pid!=null && pid>0){
//			order.setPid(pid);	
//		}
//		if(auditState!=null && auditState>0){
//			order.setAuditState(auditState);	
//		}
		
		
		tyOrderServiceService.save(order);
		
		//根据用户id生成商品订单号，让单号+订单id(唯一、回调时就懂是哪个订单支付了)
		String number = OrderCodeFactory.toCode(user.getId())+"-"+order.getId();
		order.setNumber(number);
		tyOrderServiceService.update(order);
		
		try {
			//拼接统一下单地址参数
			Map<String, String> paraMap = new HashMap<String, String>();
			
			paraMap.put("appid", ConfigUtil.getAppID());
			paraMap.put("body", "预定服务");
			paraMap.put("mch_id", ConfigUtil.getMerchant_Number());//商户号
			paraMap.put("nonce_str", WXPayUtil.generateNonceStr());
			paraMap.put("openid", user.getOpenid());//用户的Openid
			paraMap.put("out_trade_no", number);//订单号-要唯一
			paraMap.put("spbill_create_ip", userIp);//用户的ip
			String priceS = String.valueOf(jiageService.multiply(new BigDecimal(Double.toString(100))).doubleValue());
			if(StringUtil.isIndexOfStr(priceS, ".")){
				priceS = priceS.split("\\.")[0];
			}
			paraMap.put("total_fee",priceS);//付款金额-单位分(元换算为分)  
			paraMap.put("trade_type", "JSAPI");
			paraMap.put("notify_url",ConfigUtil.getWechat_Service_Callback());// 此路径是微信服务器调用支付结果通知
			String sign = WXPayUtil.generateSignature(paraMap, ConfigUtil.getMerchant_Api_Secret());
			paraMap.put("sign", sign);
			String xml = WXPayUtil.mapToXml(paraMap);//将所有参数(map)转xml格式
						
			// 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
			String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			
			String xmlStr = HttpRequest.sendPost(unifiedorder_url, xml);//发送post请求"统一下单接口"返回预支付id:prepay_id
			
			//以下内容是返回前端页面的json数据
			String prepay_id = "";//预支付id
			if (xmlStr.indexOf("SUCCESS") != -1) {  
				Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);  
				prepay_id = (String) map.get("prepay_id");  
			}
			Map<String, String> payMap = new HashMap<String, String>();
			payMap.put("appId", ConfigUtil.getAppID());  
			payMap.put("timeStamp", System.currentTimeMillis()/1000+"");  
			payMap.put("nonceStr", WXPayUtil.generateNonceStr());  
			payMap.put("signType", "MD5");
			payMap.put("package", "prepay_id=" + prepay_id);  
			String paySign = WXPayUtil.generateSignature(payMap, ConfigUtil.getMerchant_Api_Secret());  
			payMap.put("paySign", paySign);
			
			return R.ok().put("data", payMap);
		} catch (Exception e) {
			e.printStackTrace();
			//数据回滚
			TransactionRollBack.rollBackUpdate();
			return R.error("系统异常,稍后重试!");
		}
	}
	
	
	/**
	 * 支付服务套餐
	 */
	@Transactional
	@ResponseBody 
	@RequestMapping(value = "/purchase_service_meal", method = RequestMethod.POST)
	public R purchaseServiceMeal(HttpServletRequest request,Long serviceId,String userIp, String payType) {
		//获取用户信息
		TcUserEntity user = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(user.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		
		if(StringUtil.isEmpty(userIp)){
			return R.error("获取当前设备IP失败,请联系客服处理!");
		}
		
		//获取服务订单信息
		TyOrderServiceEntity serviceEntity = tyOrderServiceService.queryObject(serviceId);
		if(StringUtil.isEmpty(serviceEntity)){
			return R.error("查找不到该订单信息,请刷新页面后重新查询!");
		}
		if(serviceEntity.getServiceStatus() == 0){
			return R.error("服务套餐未确定,请联系业务员确认服务套餐!");
		}
		if(StringUtil.isEmpty(serviceEntity.getPrice())){
			return R.error("服务套餐价格业务员未确认,请联系业务员确认后方可付款!");
		}
		
		//支付套餐费用
		if(serviceEntity.getFwz() != 1 && serviceEntity.getIsCollect() == 0){
			return R.error("服务中,请联系业务员，服务完成后方可付款!");
		} 
		//支付套餐尾款
		if(serviceEntity.getIsCollect() == 1 && serviceEntity.getDepStu() == 1 
				&& serviceEntity.getFwz() != 1) {
			return R.error("服务中,请联系业务员，服务完成后方可付款!");
		}
	 
		try {
			//拼接统一下单地址参数
			Map<String, String> paraMap = new HashMap<String, String>();
			
			paraMap.put("appid", ConfigUtil.getAppID());
			paraMap.put("body", "支付服务套餐");
			paraMap.put("mch_id", ConfigUtil.getMerchant_Number());//商户号
			paraMap.put("nonce_str", WXPayUtil.generateNonceStr());
			paraMap.put("openid", user.getOpenid());//用户的Openid
			paraMap.put("spbill_create_ip", userIp);//用户的ip
			String priceS = String.valueOf(serviceEntity.getPrice().multiply(new BigDecimal(Double.toString(100))).doubleValue());
			String notify_url = ConfigUtil.getWechat_ServiceMeal_Callback();
			String tradeNo = serviceEntity.getId()+serviceEntity.getNumber();
			//0定金支付，1尾款支付，如果两者都不是则全款支付
            if("0".equals(payType)){
                priceS = String.valueOf(Float.parseFloat(serviceEntity.getDeposit())*100);
                notify_url = ConfigUtil.getWechat_ServiceDep_Callback();
                tradeNo = "DJ"+serviceEntity.getId()+serviceEntity.getNumber();
            }else if("1".equals(payType)){
                priceS = String.valueOf(Float.parseFloat(priceS) - (Float.parseFloat(serviceEntity.getDeposit())*100));
                tradeNo = "WK"+serviceEntity.getId()+serviceEntity.getNumber();
            }
		    if(StringUtil.isIndexOfStr(priceS, ".")){
			   priceS = priceS.split("\\.")[0];
		    }
            paraMap.put("out_trade_no", tradeNo);
			paraMap.put("total_fee",priceS);//付款金额-单位分(元换算为分)  
			paraMap.put("trade_type", "JSAPI");
			paraMap.put("notify_url", notify_url);// 此路径是微信服务器调用支付结果通知
			String sign = WXPayUtil.generateSignature(paraMap, ConfigUtil.getMerchant_Api_Secret());
			paraMap.put("sign", sign);
			String xml = WXPayUtil.mapToXml(paraMap);//将所有参数(map)转xml格式
						
			// 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
			String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			
			String xmlStr = HttpRequest.sendPost(unifiedorder_url, xml);//发送post请求"统一下单接口"返回预支付id:prepay_id
			
			//以下内容是返回前端页面的json数据
			String prepay_id = "";//预支付id
			if (xmlStr.indexOf("SUCCESS") != -1) {  
				Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);  
				prepay_id = (String) map.get("prepay_id");  
			}
			Map<String, String> payMap = new HashMap<String, String>();
			payMap.put("appId", ConfigUtil.getAppID());  
			payMap.put("timeStamp", System.currentTimeMillis()/1000+"");  
			payMap.put("nonceStr", WXPayUtil.generateNonceStr());  
			payMap.put("signType", "MD5");
			payMap.put("package", "prepay_id=" + prepay_id);  
			String paySign = WXPayUtil.generateSignature(payMap, ConfigUtil.getMerchant_Api_Secret());  
			payMap.put("paySign", paySign);
			
			return R.ok().put("data", payMap);
		} catch (Exception e) {
			e.printStackTrace();
			//数据回滚
			TransactionRollBack.rollBackUpdate();
			return R.error("系统异常,稍后重试!");
		}
	}
	
	
	
	/**
	 * 支付奠金
	 */
	@Transactional
	@ResponseBody 
	@RequestMapping(value = "/purchase_cashmoney", method = RequestMethod.POST)
	public R purchaseCashmoney(HttpServletRequest request,Long modulId,String name,BigDecimal money,String userIp) {
		//获取用户信息
		TcUserEntity user = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(user.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		if(StringUtil.isEmpty(userIp)){
			return R.error("获取当前设备IP失败,请联系客服处理!");
		}
		if(StringUtil.isEmpty(name) || StringUtil.isEmpty(modulId) || StringUtil.isEmpty(money)){
			return R.error("请完善所有信息!");
		}
		if(money.compareTo(new BigDecimal(0)) == -1 || money.compareTo(new BigDecimal(0)) == 0){
			return R.error("金额不能小于0!");
		}
		//判断该模板是否存在
		ModulEntity modulEntity = modulService.queryObject(modulId);
		if(StringUtil.isEmpty(modulEntity)){
			return R.error("没有该模板信息,可能模板已被删除!");
		}
		
		/**生成订单**/
		TyInvitationCashmoneyEntity cashmoneyEntity = new TyInvitationCashmoneyEntity();
		cashmoneyEntity.setUserId(user.getId());
		cashmoneyEntity.setName(name);
		cashmoneyEntity.setMoney(money);
		cashmoneyEntity.setStatus(1);
		cashmoneyEntity.setModulId(modulEntity.getId());
		cashmoneyEntity.setModulUserId(modulEntity.getUserId());
		cashmoneyEntity.setCreatetime(new Date());
		tyInvitationCashmoneyService.save(cashmoneyEntity);
		//根据用户id生成商品订单号，让单号+订单id(唯一、回调时就懂是哪个订单支付了)
		String number = OrderCodeFactory.toCode(user.getId())+"-"+cashmoneyEntity.getId();
		cashmoneyEntity.setOrderNumber(number);
		tyInvitationCashmoneyService.update(cashmoneyEntity);
		
		try {
			//拼接统一下单地址参数
			Map<String, String> paraMap = new HashMap<String, String>();
			
			paraMap.put("appid", ConfigUtil.getAppID());
			paraMap.put("body", "奠金");
			paraMap.put("mch_id", ConfigUtil.getMerchant_Number());//商户号
			paraMap.put("nonce_str", WXPayUtil.generateNonceStr());
			paraMap.put("openid", user.getOpenid());//用户的Openid
			paraMap.put("out_trade_no", number);//订单号-要唯一
			paraMap.put("spbill_create_ip", userIp);//用户的ip
			String priceS = String.valueOf(money.multiply(new BigDecimal(Double.toString(100))).doubleValue());
			if(StringUtil.isIndexOfStr(priceS, ".")){
				priceS = priceS.split("\\.")[0];
			}
			paraMap.put("total_fee",priceS);//付款金额-单位分(元换算为分)  
			paraMap.put("trade_type", "JSAPI");
			paraMap.put("notify_url",ConfigUtil.getWechat_Cashmoney_Callback());// 此路径是微信服务器调用支付结果通知
			String sign = WXPayUtil.generateSignature(paraMap, ConfigUtil.getMerchant_Api_Secret());
			paraMap.put("sign", sign);
			String xml = WXPayUtil.mapToXml(paraMap);//将所有参数(map)转xml格式
						
			// 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
			String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			
			String xmlStr = HttpRequest.sendPost(unifiedorder_url, xml);//发送post请求"统一下单接口"返回预支付id:prepay_id
			
			//以下内容是返回前端页面的json数据
			String prepay_id = "";//预支付id
			if (xmlStr.indexOf("SUCCESS") != -1) {  
				Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);  
				prepay_id = (String) map.get("prepay_id");  
			}
			Map<String, String> payMap = new HashMap<String, String>();
			payMap.put("appId", ConfigUtil.getAppID());  
			payMap.put("timeStamp", System.currentTimeMillis()/1000+"");  
			payMap.put("nonceStr", WXPayUtil.generateNonceStr());  
			payMap.put("signType", "MD5");
			payMap.put("package", "prepay_id=" + prepay_id);  
			String paySign = WXPayUtil.generateSignature(payMap, ConfigUtil.getMerchant_Api_Secret());  
			payMap.put("paySign", paySign);
			
			return R.ok().put("data", payMap);
		} catch (Exception e) {
			e.printStackTrace();
			//数据回滚
			TransactionRollBack.rollBackUpdate();
			return R.error("系统异常,稍后重试!");
		}
	}
	
	
	/**
	 * 支付平台礼物
	 */
	@Transactional
	@ResponseBody 
	@RequestMapping(value = "/purchase_giftservice", method = RequestMethod.POST)
	public R purchaseGiftService(HttpServletRequest request,Long modulId,String giftCode,String userIp) {
		//获取用户信息
		TcUserEntity user = UserSession.customer(request);
		/**判断用户是否被冻结**/
		if(user.getStatus() != 0){
			return R.error("操作失败!您的账户已被冻结,如有疑问请联系客服!");
		}
		if(StringUtil.isEmpty(userIp)){
			return R.error("获取当前设备IP失败,请联系客服处理!");
		}
		if(StringUtil.isEmpty(giftCode) || StringUtil.isEmpty(modulId)){
			return R.error("查找不到信息,请重新进入后再赠送礼物!");
		}
		
		//判断该模板是否存在
		ModulEntity modulEntity = modulService.queryObject(modulId);
		if(StringUtil.isEmpty(modulEntity)){
			return R.error("没有该模板信息,可能模板已被删除!");
		}
		
		//判断礼物是否存在
		TyGiftEntity tyGiftEneity = new TyGiftEntity();
		tyGiftEneity.setGiftCode(giftCode);
		List<TyGiftEntity> listGift = tyGiftService.queryObjectByKey(tyGiftEneity);
		if(listGift.isEmpty() || listGift.size() <= 0){
			return R.error("没有该礼物信息,请重新选择礼物!");
		}
		
		/**生成订单**/
		TyInvitationGiftEntity giftEntity = new TyInvitationGiftEntity();
		giftEntity.setUserId(user.getId());
		giftEntity.setName(tyInvitationToinviteService.nameModul(user));
		giftEntity.setGiftCode(listGift.get(0).getGiftCode());
		giftEntity.setGiftName(listGift.get(0).getGiftName());
		giftEntity.setPrice(listGift.get(0).getPrice());
		giftEntity.setModulId(modulId);
		giftEntity.setModulUserId(modulEntity.getUserId());
		giftEntity.setStatus(1);
		giftEntity.setCreatetime(new Date());
		tyInvitationGiftService.save(giftEntity);
		//根据用户id生成商品订单号，让单号+订单id(唯一、回调时就懂是哪个订单支付了)
		String number = OrderCodeFactory.toCode(user.getId())+"-"+giftEntity.getId();
		giftEntity.setOrderNumber(number);
		tyInvitationGiftService.update(giftEntity);
		
		try {
			//拼接统一下单地址参数
			Map<String, String> paraMap = new HashMap<String, String>();
			
			paraMap.put("appid", ConfigUtil.getAppID());
			paraMap.put("body", "赠送平台礼物");
			paraMap.put("mch_id", ConfigUtil.getMerchant_Number());//商户号
			paraMap.put("nonce_str", WXPayUtil.generateNonceStr());
			paraMap.put("openid", user.getOpenid());//用户的Openid
			paraMap.put("out_trade_no", number);//订单号-要唯一
			paraMap.put("spbill_create_ip", userIp);//用户的ip
			String priceS = String.valueOf(listGift.get(0).getPrice().multiply(new BigDecimal(Double.toString(100))).doubleValue());
			if(StringUtil.isIndexOfStr(priceS, ".")){
				priceS = priceS.split("\\.")[0];
			}
			paraMap.put("total_fee",priceS);//付款金额-单位分(元换算为分)  
			paraMap.put("trade_type", "JSAPI");
			paraMap.put("notify_url",ConfigUtil.getWechat_GiftService_Callback());// 此路径是微信服务器调用支付结果通知
			String sign = WXPayUtil.generateSignature(paraMap, ConfigUtil.getMerchant_Api_Secret());
			paraMap.put("sign", sign);
			String xml = WXPayUtil.mapToXml(paraMap);//将所有参数(map)转xml格式
						
			// 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
			String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			
			String xmlStr = HttpRequest.sendPost(unifiedorder_url, xml);//发送post请求"统一下单接口"返回预支付id:prepay_id
			
			//以下内容是返回前端页面的json数据
			String prepay_id = "";//预支付id
			if (xmlStr.indexOf("SUCCESS") != -1) {  
				Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);  
				prepay_id = (String) map.get("prepay_id");  
			}
			Map<String, String> payMap = new HashMap<String, String>();
			payMap.put("appId", ConfigUtil.getAppID());  
			payMap.put("timeStamp", System.currentTimeMillis()/1000+"");  
			payMap.put("nonceStr", WXPayUtil.generateNonceStr());  
			payMap.put("signType", "MD5");
			payMap.put("package", "prepay_id=" + prepay_id);  
			String paySign = WXPayUtil.generateSignature(payMap, ConfigUtil.getMerchant_Api_Secret());  
			payMap.put("paySign", paySign);
			
			return R.ok().put("data", payMap);
		} catch (Exception e) {
			e.printStackTrace();
			//数据回滚
			TransactionRollBack.rollBackUpdate();
			return R.error("系统异常,稍后重试!");
		}
	}
	
	
	public static void main(String[] args) {
		
	}
}






