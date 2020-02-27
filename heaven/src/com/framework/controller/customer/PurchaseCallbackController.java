package com.framework.controller.customer;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.entity.TbFlowingRecordEntity;
import com.framework.entity.TcUserEntity;
import com.framework.entity.TsSchedulingEntity;
import com.framework.entity.TsSchedulingPaiDanEntity;
import com.framework.entity.TyInvitationCashmoneyEntity;
import com.framework.entity.TyInvitationGiftEntity;
import com.framework.entity.TyOrderCommodityEntity;
import com.framework.entity.TyOrderServiceEntity;
import com.framework.service.SysUserService;
import com.framework.service.TbFlowingRecordService;
import com.framework.service.TcUserService;
import com.framework.service.TsSchedulingPaiDanService;
import com.framework.service.TsSchedulingService;
import com.framework.service.TyInvitationCashmoneyService;
import com.framework.service.TyInvitationGiftService;
import com.framework.service.TyOrderCommodityService;
import com.framework.service.TyOrderServiceService;
import com.framework.utils.StringUtil;
import com.framework.utils.wechat.ModulMsgByOpenId;
import com.framework.utils.wechat.WechatUtils;
import com.github.wxpay.sdk.WXPayUtil;

/**
 * 支付后微信通知支付结果的路径
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/customer")
@CrossOrigin
public class PurchaseCallbackController {
	
	@Autowired
	private TsSchedulingService tsSchedulingService;
	
	@Autowired
	private TcUserService tcUserService;

	@Autowired
	private TyOrderCommodityService tyOrderCommodityService;
	
	@Autowired
	private TbFlowingRecordService tbFlowingRecordService;
	
	@Autowired
	private TyOrderServiceService tyOrderServiceService;
	
	@Autowired
	private TyInvitationCashmoneyService tyInvitationCashmoneyService;
	
	@Autowired
	private TyInvitationGiftService tyInvitationGiftService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private TsSchedulingPaiDanService tsSchedulingPaiDanService;
	
	/**
	 * @Description 商品，微信支付成功后回调次接口
	 */
	@ResponseBody
    @RequestMapping("/purchase_commodity_callback")
	public String callBack(HttpServletRequest request,HttpServletResponse response){
		InputStream is = null;
		try {
			is = request.getInputStream();//获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
			String xml = WechatUtils.inputStream2String(is, "UTF-8");
			Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);//将微信发的xml转map
						
			if(notifyMap.get("return_code").equals("SUCCESS")){
	                if(notifyMap.get("result_code").equals("SUCCESS")){  
	                //String amountpaid = notifyMap.get("total_fee");//实际支付的订单金额:单位 分
	                //BigDecimal amountPay = (new BigDecimal(amountpaid).divide(new BigDecimal("100"))).setScale(2);//将分转换成元-实际支付金额:元
	                //String openid = notifyMap.get("openid");//用户的openid
	                String ordersSn = notifyMap.get("out_trade_no");//商户订单号 
	                
	                //获取到支付成功的订单信息
	                String[] orders = ordersSn.split("-");
	                Long commodityId = Long.valueOf(orders[orders.length-1]);
	                TyOrderCommodityEntity commodity = tyOrderCommodityService.queryObject(commodityId);
	                
	                //更新支付状态
	                commodity.setPayStatus(1);
	                tyOrderCommodityService.update(commodity);
	                
	                //添加用户流水
	                TbFlowingRecordEntity tbFlowingRecordEntity = new TbFlowingRecordEntity();
	                tbFlowingRecordEntity.setUserId(commodity.getUserId());
	                tbFlowingRecordEntity.setMoney(commodity.getCommodityPrice());
	                tbFlowingRecordEntity.setSymbol("-");
	                tbFlowingRecordEntity.setType("shopping");
	                tbFlowingRecordEntity.setTypeName("购物");
	                tbFlowingRecordEntity.setCreatetime(new Date());
	                tbFlowingRecordEntity.setNote("购买商城商品-"+commodity.getCommodityTitle());
	                
	                tbFlowingRecordService.save(tbFlowingRecordEntity);
	            }  
	        }
			
	        //告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
	        response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");  
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
     * @Description 定金，微信支付成功后回调次接口
     */
    @ResponseBody
    @RequestMapping("/deposit_servicemeal_callback")
    public String callBackDep(HttpServletRequest request,HttpServletResponse response){
        InputStream is = null;
        try {
            is = request.getInputStream();//获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
            String xml = WechatUtils.inputStream2String(is, "UTF-8");
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);//将微信发的xml转map
                        
            if(notifyMap.get("return_code").equals("SUCCESS")){
                    if(notifyMap.get("result_code").equals("SUCCESS")){  
                    //String amountpaid = notifyMap.get("total_fee");//实际支付的订单金额:单位 分
                    //BigDecimal amountPay = (new BigDecimal(amountpaid).divide(new BigDecimal("100"))).setScale(2);//将分转换成元-实际支付金额:元
                    //String openid = notifyMap.get("openid");//用户的openid
                    String ordersSn = notifyMap.get("out_trade_no");//商户订单号 
                    
                    //获取到支付成功的订单信息
                    String[] orders = ordersSn.split("-");
                    Long commodityId = Long.valueOf(orders[orders.length-1]);
                    TyOrderServiceEntity serviceEntity = tyOrderServiceService.queryObject(commodityId);
                    
                    //更新支付状态 
                    serviceEntity.setDepStu(1); 
                    tyOrderServiceService.update(serviceEntity);
                    
                    //添加用户流水
                    TbFlowingRecordEntity tbFlowingRecordEntity = new TbFlowingRecordEntity();
                    tbFlowingRecordEntity.setUserId(serviceEntity.getUserid());
                    tbFlowingRecordEntity.setMoney(serviceEntity.getPrice());
                    tbFlowingRecordEntity.setSymbol("-");
                    tbFlowingRecordEntity.setType("service");
                    tbFlowingRecordEntity.setTypeName("服务套餐");
                    tbFlowingRecordEntity.setCreatetime(new Date());
                    tbFlowingRecordEntity.setNote("支付服务套餐费用");
                    
                    tbFlowingRecordService.save(tbFlowingRecordEntity);
                }
            }
            
            //告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
            response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");  
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	/**
	 * @Description 服务，  微信支付成功后回调次接口
	 */
	@ResponseBody
    @RequestMapping("/purchase_service_callback")
	public String callBackService(HttpServletRequest request,HttpServletResponse response){
		InputStream is = null;
		try {
			is = request.getInputStream();//获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
			String xml = WechatUtils.inputStream2String(is, "UTF-8");
			Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);//将微信发的xml转map
						
			if(notifyMap.get("return_code").equals("SUCCESS")){
	                if(notifyMap.get("result_code").equals("SUCCESS")){ 
	                //String amountpaid = notifyMap.get("total_fee");//实际支付的订单金额:单位 分
	                //BigDecimal amountPay = (new BigDecimal(amountpaid).divide(new BigDecimal("100"))).setScale(2);//将分转换成元-实际支付金额:元
	                String openid = notifyMap.get("openid");//用户的openid
	                String ordersSn = notifyMap.get("out_trade_no");//商户订单号 
	                
	                //获取到支付成功的订单信息
	                String[] orders = ordersSn.split("-");
	                Long commodityId = Long.valueOf(orders[orders.length-1]);
	                TyOrderServiceEntity serviceEntity = tyOrderServiceService.queryObject(commodityId);
	                
	                //更新支付状态
	                serviceEntity.setPayStatus(1);
	                tyOrderServiceService.update(serviceEntity);
	        
	                //添加用户流水
	                TbFlowingRecordEntity tbFlowingRecordEntity = new TbFlowingRecordEntity();
	                tbFlowingRecordEntity.setUserId(serviceEntity.getUserid());
	                tbFlowingRecordEntity.setMoney(serviceEntity.getServicePrice());
	                tbFlowingRecordEntity.setSymbol("-");
	                tbFlowingRecordEntity.setType("service");
	                tbFlowingRecordEntity.setTypeName("预约服务");
	                tbFlowingRecordEntity.setCreatetime(new Date());
	                tbFlowingRecordEntity.setNote("预约服务,支付服务定制费用");
	                
	                tbFlowingRecordService.save(tbFlowingRecordEntity);
	                
	                //用户购买服务成功通知(通知用户)
	                ModulMsgByOpenId.sercieBuySuccess(openid,ordersSn);
	        		//派单给业务员值班 gzh 2019-12-20
	        		//当前日期
	        		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	        		Calendar calendat1 = Calendar.getInstance();
	        		String dateStr1 = sdf1.format(calendat1.getTime());
	        		List<TsSchedulingEntity> tsUserIds = tsSchedulingService.queryListByDate(dateStr1);
	        		//一人一天
	        		//Long tsUserId = tsSchedulingService.querySchedulingByDate(dateStr);
	        		int dateS = tsSchedulingPaiDanService.queryDate(dateStr1);
	        		if(dateS == 0){
	        			tsSchedulingPaiDanService.deleteDate();
	        	
					};
	        		if(tsUserIds != null){
        				tsSchedulingPaiDanService.saveDate(tsUserIds);
	        			List<TsSchedulingPaiDanEntity> tsUser = tsSchedulingPaiDanService.queryDateMin();
	        			tsSchedulingPaiDanService.addNumber(tsUser.get(0));
	        			//派单  ?
	        			tyOrderServiceService.paiDan(serviceEntity.getId(),tsUser.get(0).getTsUserId());
	        		};
	                
	                
	                //获取用户信息
	                String nickName = "无";
	                String userId = "无";
	                TcUserEntity tcUserEntity = tcUserService.queryObject(serviceEntity.getUserid());
	                if(tcUserEntity != null && !StringUtil.isEmpty(tcUserEntity.getNickname()) && !StringUtil.isEmpty(tcUserEntity.getId())){
	                	nickName = tcUserEntity.getNickname();
	                	userId = tcUserEntity.getId().toString();
	                }
	                
	                //获取后台工作人员的id
	                final List<String> openidList = sysUserService.queryByUserIdForOpenidList(nickName,userId,ordersSn);
	                //该地方启用一个线程来跑,以防支付成功了还要一个一个通知导致时间过长用户支付体验感太差
	                new Thread(new Runnable() {
						@Override
						public void run() {
							//服务工单提醒(通知后台工作人员)
							int iFlag = 0;
			                for(String userOpenid : openidList){
			                	if(iFlag >= 3){
			                		ModulMsgByOpenId.serviceOrderNew(userOpenid,openidList.get(0),openidList.get(1),openidList.get(2));
			                	}
			                	iFlag += 1;
			                }
						}
					}).start();
	                
	            }else{
	            	String ordersSn = notifyMap.get("out_trade_no");//商户订单号 
	                String[] orders = ordersSn.split("-");
	                Long commodityId = Long.valueOf(orders[orders.length-1]);
		        	//删除该订单
		        	tbFlowingRecordService.delete(commodityId);
	            }
	        }else{
	        	String ordersSn = notifyMap.get("out_trade_no");//商户订单号 
                String[] orders = ordersSn.split("-");
                Long commodityId = Long.valueOf(orders[orders.length-1]);
	        	//删除该订单
	        	tbFlowingRecordService.delete(commodityId);
	        }
			
	        //告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
	        response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");  
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Description 服务套餐，微信支付成功后回调次接口
	 */
	@ResponseBody
    @RequestMapping("/purchase_servicemeal_callback")
	public String callBackServiceMeal(HttpServletRequest request,HttpServletResponse response){
		InputStream is = null;
		try {
			is = request.getInputStream();//获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
			String xml = WechatUtils.inputStream2String(is, "UTF-8");
			Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);//将微信发的xml转map
						
			if(notifyMap.get("return_code").equals("SUCCESS")){
	                if(notifyMap.get("result_code").equals("SUCCESS")){  
	                //String amountpaid = notifyMap.get("total_fee");//实际支付的订单金额:单位 分
	                //BigDecimal amountPay = (new BigDecimal(amountpaid).divide(new BigDecimal("100"))).setScale(2);//将分转换成元-实际支付金额:元
	                //String openid = notifyMap.get("openid");//用户的openid
	                String ordersSn = notifyMap.get("out_trade_no");//商户订单号 
	                
	                //获取到支付成功的订单信息
	                String[] orders = ordersSn.split("-");
	                Long commodityId = Long.valueOf(orders[orders.length-1]);
	                TyOrderServiceEntity serviceEntity = tyOrderServiceService.queryObject(commodityId);
	                
	                //更新支付状态
	                serviceEntity.setPriceStatus(1);
	                serviceEntity.setStatus(1);
	                tyOrderServiceService.update(serviceEntity);
	                
	                //添加用户流水
	                TbFlowingRecordEntity tbFlowingRecordEntity = new TbFlowingRecordEntity();
	                tbFlowingRecordEntity.setUserId(serviceEntity.getUserid());
	                tbFlowingRecordEntity.setMoney(serviceEntity.getPrice());
	                tbFlowingRecordEntity.setSymbol("-");
	                tbFlowingRecordEntity.setType("service");
	                tbFlowingRecordEntity.setTypeName("服务套餐");
	                tbFlowingRecordEntity.setCreatetime(new Date());
	                tbFlowingRecordEntity.setNote("支付服务套餐费用");
	                
	                tbFlowingRecordService.save(tbFlowingRecordEntity);
	            }
	        }
			
	        //告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
	        response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");  
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Description 奠金，微信支付成功后回调次接口
	 */
	@ResponseBody
    @RequestMapping("/purchase_cashmoney_callback")
	public String cashmoneyCallBack(HttpServletRequest request,HttpServletResponse response){
		InputStream is = null;
		try {
			is = request.getInputStream();//获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
			String xml = WechatUtils.inputStream2String(is, "UTF-8");
			Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);//将微信发的xml转map
						
			if(notifyMap.get("return_code").equals("SUCCESS")){
	                if(notifyMap.get("result_code").equals("SUCCESS")){  
	                //String amountpaid = notifyMap.get("total_fee");//实际支付的订单金额:单位 分
	                //BigDecimal amountPay = (new BigDecimal(amountpaid).divide(new BigDecimal("100"))).setScale(2);//将分转换成元-实际支付金额:元
	                //String openid = notifyMap.get("openid");//用户的openid
	                String ordersSn = notifyMap.get("out_trade_no");//商户订单号 
	                
	                //获取到支付成功的订单信息
	                String[] orders = ordersSn.split("-");
	                Long commodityId = Long.valueOf(orders[orders.length-1]);
	                
	                TyInvitationCashmoneyEntity cashmoneyEntity = tyInvitationCashmoneyService.queryObject(commodityId);
	                //更新支付状态
	                cashmoneyEntity.setStatus(0);
	                tyInvitationCashmoneyService.update(cashmoneyEntity);
	                
	                //添加用户流水
	                TbFlowingRecordEntity tbFlowingRecordEntity = new TbFlowingRecordEntity();
	                tbFlowingRecordEntity.setUserId(cashmoneyEntity.getUserId());
	                tbFlowingRecordEntity.setMoney(cashmoneyEntity.getMoney());
	                tbFlowingRecordEntity.setSymbol("-");
	                tbFlowingRecordEntity.setType("cash_gift");
	                tbFlowingRecordEntity.setTypeName("奠金");
	                tbFlowingRecordEntity.setCreatetime(new Date());
	                tbFlowingRecordEntity.setNote("支付奠金");
	                tbFlowingRecordService.save(tbFlowingRecordEntity);
	                
	                //添加收钱方钱包余额
	                Map<String, Object> map = new HashMap<>();
	                map.put("id", cashmoneyEntity.getModulUserId());
	                map.put("wallet", cashmoneyEntity.getMoney());
	                tcUserService.addWallet(map);
	                
	                //添加收钱方流水
	                TbFlowingRecordEntity tbFlowingRecordEntityadd = new TbFlowingRecordEntity();
	                tbFlowingRecordEntityadd.setUserId(cashmoneyEntity.getModulUserId());
	                tbFlowingRecordEntityadd.setMoney(cashmoneyEntity.getMoney());
	                tbFlowingRecordEntityadd.setSymbol("+");
	                tbFlowingRecordEntityadd.setType("cash_gift");
	                tbFlowingRecordEntityadd.setTypeName("奠金");
	                tbFlowingRecordEntityadd.setCreatetime(new Date());
	                tbFlowingRecordEntityadd.setNote("收到奠金");
	                tbFlowingRecordService.save(tbFlowingRecordEntityadd);
	            }  
	        }
			
	        //告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
	        response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");  
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Description 平台礼物，微信支付成功后回调次接口
	 */
	@ResponseBody
    @RequestMapping("/purchase_giftservice_callback")
	public String giftServiceCallBack(HttpServletRequest request,HttpServletResponse response){
		InputStream is = null;
		try {
			is = request.getInputStream();//获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
			String xml = WechatUtils.inputStream2String(is, "UTF-8");
			Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);//将微信发的xml转map
						
			if(notifyMap.get("return_code").equals("SUCCESS")){
	                if(notifyMap.get("result_code").equals("SUCCESS")){  
	                //String amountpaid = notifyMap.get("total_fee");//实际支付的订单金额:单位 分
	                //BigDecimal amountPay = (new BigDecimal(amountpaid).divide(new BigDecimal("100"))).setScale(2);//将分转换成元-实际支付金额:元
	                //String openid = notifyMap.get("openid");//用户的openid
	                String ordersSn = notifyMap.get("out_trade_no");//商户订单号 
	                
	                //获取到支付成功的订单信息
	                String[] orders = ordersSn.split("-");
	                Long commodityId = Long.valueOf(orders[orders.length-1]);
	                
	                TyInvitationGiftEntity giftEntity = tyInvitationGiftService.queryObject(commodityId);
	                //更新支付状态
	                giftEntity.setStatus(0);
	                tyInvitationGiftService.update(giftEntity);
	                
	                //添加用户流水
	                TbFlowingRecordEntity tbFlowingRecordEntity = new TbFlowingRecordEntity();
	                tbFlowingRecordEntity.setUserId(giftEntity.getUserId());
	                tbFlowingRecordEntity.setMoney(giftEntity.getPrice());
	                tbFlowingRecordEntity.setSymbol("-");
	                tbFlowingRecordEntity.setType("give_gift");
	                tbFlowingRecordEntity.setTypeName("赠送平台礼物");
	                tbFlowingRecordEntity.setCreatetime(new Date());
	                tbFlowingRecordEntity.setNote("支付平台礼物");
	                tbFlowingRecordService.save(tbFlowingRecordEntity);
	            }  
	        }
			
	        //告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
	        response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");  
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
