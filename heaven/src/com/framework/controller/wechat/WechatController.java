package com.framework.controller.wechat;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.entity.TcUserEntity;
import com.framework.entity.TsUserEntity;
import com.framework.service.TcUserService;
import com.framework.service.TsUserService;
import com.framework.session.MySessionContext;
import com.framework.utils.ConfigUtil;
import com.framework.utils.OrderCodeFactory;
import com.framework.utils.R;
import com.framework.utils.StringUtil;
import com.framework.utils.wechat.AuthUtil;
import com.framework.utils.wechat.CheckoutUtil;
import com.framework.utils.wechat.HttpRequest;
import com.framework.utils.wechat.WechatUtils;
import com.github.wxpay.sdk.WXPayUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wechat")
public class WechatController {
	
	@Autowired
	private TcUserService tcUserService;
	@Autowired
	private TsUserService tsUserService;

	/**
	 * 微信消息接收和token验证
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/token")
	public void token(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean isGet = request.getMethod().toLowerCase().equals("get");
		PrintWriter print;
		if (isGet) {
			// 微信加密签名
			String signature = request.getParameter("signature");
			// 时间戳
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");
			// 随机字符串
			String echostr = request.getParameter("echostr");
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			if (signature != null && CheckoutUtil.checkSignature(signature, timestamp, nonce)) {
				try {
					print = response.getWriter();
					print.write(echostr);
					print.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@ResponseBody
    @RequestMapping("/wechat_login")
    public void wechatLogin(HttpServletResponse response,String callbackUrl) {
		try {
			callbackUrl = URLEncoder.encode(callbackUrl, "UTF-8");
			
			//这里是回调的url
			String redirect_uri = URLEncoder.encode(ConfigUtil.getWechat_CallBackUserMSG()+"?callbackUrl="+callbackUrl, "UTF-8");
			
			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
	                "appid=APPID" +
	                "&redirect_uri=REDIRECT_URI" +
	                "&response_type=code" + 
	                "&scope=SCOPE" +
	                "&state=123#wechat_redirect";
			
			url = url.replace("APPID",ConfigUtil.getAppID()).replace("REDIRECT_URI",redirect_uri).replace("SCOPE","snsapi_userinfo");
			response.sendRedirect(url);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@ResponseBody
    @RequestMapping("/code")
    public void wechatCode(HttpSession session,HttpServletRequest request,HttpServletResponse response,String callbackUrl) {
		String code = StringUtil.toUTF8One(request, "code");
		
		//获取code后，请求以下链接获取access_token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AuthUtil.APPID
                + "&secret=" + AuthUtil.APPSECRET
                + "&code=" + code
                + "&grant_type=authorization_code";
        
		try {
			//通过网络请求方法来请求上面这个接口
	        JSONObject jsonObject = AuthUtil.doGetJson(url);
	        //判断用户信息是否获取成功，不成功则重新让用户授权
			if(StringUtil.isEmpty(jsonObject)){
				response.sendRedirect(ConfigUtil.getWechat_Login());
				return;
			}
	        
	        //从返回的JSON数据中取出access_token和openid，拉取用户信息时用
	        String token =  jsonObject.getString("access_token");
	        String openid = jsonObject.getString("openid");
	        
	        // 第三步：刷新access_token（如果需要）

	        // 第四步：拉取用户信息(需scope为 snsapi_userinfo)
	        String infoUrl ="https://api.weixin.qq.com/sns/userinfo?access_token=" + token
	                + "&openid=" + openid
	                + "&lang=zh_CN";
	       
	        //通过网络请求方法来请求上面这个接口
			JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
			//判断用户信息是否获取成功，不成功则重新让用户授权
			if(StringUtil.isEmpty(userInfo)){
				response.sendRedirect(ConfigUtil.getWechat_Login());
				return;
			}
			
			//根据openid判断用户信息是否存在，不存在则增加，存在则更新
			TcUserEntity tcUser = new TcUserEntity();
			tcUser.setOpenid(userInfo.getString("openid"));
			List<TcUserEntity> tcuserList = tcUserService.queryObjectByKey(tcUser);
			
			tcUser.setNickname(userInfo.getString("nickname"));
			tcUser.setHeadimgurl(userInfo.getString("headimgurl"));
			if(tcuserList.isEmpty() || tcuserList.size() <= 0){
				//新增
				tcUser.setWallet(new BigDecimal(0.00));
				tcUser.setStatus(0);
				tcUser.setCreatetime(new Date());
				tcUserService.save(tcUser);
				//添加用户信息到session中
				session.setAttribute("customerUser", tcUserService.queryObject(tcUser.getId()));
				MySessionContext mc = MySessionContext.getInstance();
				mc.addSession(session);
			}else{
				//更新
				tcUser.setId(tcuserList.get(0).getId());
				tcUserService.update(tcUser);
				//添加用户信息到session中
				session.setAttribute("customerUser", tcUserService.queryObject(tcUser.getId()));
				MySessionContext mc = MySessionContext.getInstance();
				mc.addSession(session);
			}
			
		} catch (ClientProtocolException e1) {   
			e1.printStackTrace();
			try {
				response.sendRedirect(ConfigUtil.getWechat_Login());
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			try {
				response.sendRedirect(ConfigUtil.getWechat_Login());
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
		try {
			//授权成功跳转的页面
//			response.sendRedirect(ConfigUtil.getLogin_Href());
			response.sendRedirect(callbackUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@ResponseBody
    @RequestMapping("/wechat_salesman_sq")
    public void wechatSalesmanSq(HttpServletResponse response,Long id) {
		try {
			//这里是回调的url
			String redirect_uri = URLEncoder.encode("http://tianyinde.com/wechat/salesman_code_sq?id="+id, "UTF-8");
			
			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
	                "appid=APPID" +
	                "&redirect_uri=REDIRECT_URI" +
	                "&response_type=code" + 
	                "&scope=SCOPE" +
	                "&state=123#wechat_redirect";
			
			url = url.replace("APPID",ConfigUtil.getAppID()).replace("REDIRECT_URI",redirect_uri).replace("SCOPE","snsapi_userinfo");
			response.sendRedirect(url);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@ResponseBody
    @RequestMapping("/wechat_salesman_login")
    public void wechatSalesmanLogin(HttpServletResponse response) {
		try {
			//这里是回调的url
			String redirect_uri = URLEncoder.encode("http://tianyinde.com/wechat/salesman_code", "UTF-8");
			
			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
	                "appid=APPID" +
	                "&redirect_uri=REDIRECT_URI" +
	                "&response_type=code" + 
	                "&scope=SCOPE" +
	                "&state=123#wechat_redirect";
			
			url = url.replace("APPID",ConfigUtil.getAppID()).replace("REDIRECT_URI",redirect_uri).replace("SCOPE","snsapi_userinfo");
			response.sendRedirect(url);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@ResponseBody
    @RequestMapping("/salesman_code_sq")
    public void wechatSalesmanCodeSq(HttpSession session,HttpServletRequest request,HttpServletResponse response,Long id) {
		String code = StringUtil.toUTF8One(request, "code");
		
		//获取code后，请求以下链接获取access_token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AuthUtil.APPID
                + "&secret=" + AuthUtil.APPSECRET
                + "&code=" + code
                + "&grant_type=authorization_code";
        
		try {
			//通过网络请求方法来请求上面这个接口
	        JSONObject jsonObject = AuthUtil.doGetJson(url);
	        //判断用户信息是否获取成功，不成功则重新让用户授权
			if(StringUtil.isEmpty(jsonObject)){
				response.sendRedirect(ConfigUtil.getWechat_Salesman_Login());
				return;
			}
	        
	        //从返回的JSON数据中取出access_token和openid，拉取用户信息时用
	        String token =  jsonObject.getString("access_token");
	        String openid = jsonObject.getString("openid");
	        
	        // 第三步：刷新access_token（如果需要）

	        // 第四步：拉取用户信息(需scope为 snsapi_userinfo)
	        String infoUrl ="https://api.weixin.qq.com/sns/userinfo?access_token=" + token
	                + "&openid=" + openid
	                + "&lang=zh_CN";
	       
	        //通过网络请求方法来请求上面这个接口
			JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
			//判断用户信息是否获取成功，不成功则重新让用户授权
			if(StringUtil.isEmpty(userInfo)){
				response.sendRedirect(ConfigUtil.getWechat_Salesman_Login());
				return;
			}
			
			//根据openid判断用户信息是否存在，不存在则增加，存在则更新
			TsUserEntity tsUser = new TsUserEntity();
			TsUserEntity tcuserList = tsUserService.queryObject(id);
			
			tsUser.setWechatOpenid(userInfo.getString("openid"));
			tsUser.setWechatName(userInfo.getString("nickname"));
			tsUser.setWechatHead(userInfo.getString("headimgurl"));
			if(tcuserList==null){
				//授权失败跳转的页面
				response.sendRedirect(ConfigUtil.getWechat_Salesman_Login());
				return;
			}else{
				//更新
				tsUser.setId(tcuserList.getId());
				tsUserService.updateNow(tsUser);
				//添加用户信息到session中
				session.setAttribute("salesmanUser",tcuserList);
				MySessionContext mc = MySessionContext.getInstance();
				mc.addSession(session);
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
			try {
				response.sendRedirect("http://tianyinde.com/login_salesman.html");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			try {
				response.sendRedirect("http://tianyinde.com/login_salesman.html");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
		try {
			//授权成功跳转的页面
			response.sendRedirect("http://tianyinde.com/index_salesman.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@ResponseBody
    @RequestMapping("/salesman_code")
    public void wechatSalesmanCode(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
		String code = StringUtil.toUTF8One(request, "code");
		
		//获取code后，请求以下链接获取access_token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AuthUtil.APPID
                + "&secret=" + AuthUtil.APPSECRET
                + "&code=" + code
                + "&grant_type=authorization_code";
        
		try {
			//通过网络请求方法来请求上面这个接口
	        JSONObject jsonObject = AuthUtil.doGetJson(url);
	        //判断用户信息是否获取成功，不成功则重新让用户授权
			if(StringUtil.isEmpty(jsonObject)){
				response.sendRedirect(ConfigUtil.getWechat_Salesman_Login());
				return;
			}
	        
	        //从返回的JSON数据中取出access_token和openid，拉取用户信息时用
	        String token =  jsonObject.getString("access_token");
	        String openid = jsonObject.getString("openid");
	        
	        // 第三步：刷新access_token（如果需要）

	        // 第四步：拉取用户信息(需scope为 snsapi_userinfo)
	        String infoUrl ="https://api.weixin.qq.com/sns/userinfo?access_token=" + token
	                + "&openid=" + openid
	                + "&lang=zh_CN";
	       
	        //通过网络请求方法来请求上面这个接口
			JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
			//判断用户信息是否获取成功，不成功则重新让用户授权
			if(StringUtil.isEmpty(userInfo)){
				response.sendRedirect(ConfigUtil.getWechat_Salesman_Login());
				return;
			}
			
			//根据openid判断用户信息是否存在，不存在则增加，存在则更新
			TsUserEntity tsUser = new TsUserEntity();
			tsUser.setWechatOpenid(userInfo.getString("openid"));
			List<TsUserEntity> tcuserList = tsUserService.queryObjectByKey(tsUser);
			
			tsUser.setWechatName(userInfo.getString("nickname"));
			tsUser.setWechatHead(userInfo.getString("headimgurl"));
			if(tcuserList.isEmpty() || tcuserList.size() <= 0){
				//授权失败跳转的页面
				response.sendRedirect("http://tianyinde.com/login_salesman.html");
				return;
			}else{
				//更新
				tsUser.setId(tcuserList.get(0).getId());
				tsUserService.updateNow(tsUser);
				//添加用户信息到session中
				session.setAttribute("salesmanUser",tcuserList.get(0));
				MySessionContext mc = MySessionContext.getInstance();
				mc.addSession(session);
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
			try {
				response.sendRedirect("http://tianyinde.com/login_salesman.html");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			try {
				response.sendRedirect("http://tianyinde.com/login_salesman.html");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
		try {
			//授权成功跳转的页面
			response.sendRedirect("http://tianyinde.com/index_salesman.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @Description 微信浏览器内微信支付/公众号支付(JSAPI)
	 * @param request
	 * @param code
	 * @return Map
	 */
	@ResponseBody
    @RequestMapping("/orders")
	public R orders(HttpServletRequest request) {
		try {
			//拼接统一下单地址参数
			Map<String, String> paraMap = new HashMap<String, String>();
			
			paraMap.put("appid", ConfigUtil.getAppID());
			paraMap.put("body", "商城-测试订单结算");
			paraMap.put("mch_id", ConfigUtil.getMerchant_Number());//商户号
			paraMap.put("nonce_str", WXPayUtil.generateNonceStr());
			paraMap.put("openid", "oTHLa1UlKwSXB6erbv12GWAj3oI0");
			paraMap.put("out_trade_no", OrderCodeFactory.toCode(3L)+"-188736719");//订单号-要唯一
			paraMap.put("spbill_create_ip", "192.168.1.188");//用户的ip
			paraMap.put("total_fee","1");//付款金额-单位分  
			paraMap.put("trade_type", "JSAPI");
//			paraMap.put("commodity_id", "18");
			paraMap.put("notify_url",ConfigUtil.getWechat_Pay_Callback());// 此路径是微信服务器调用支付结果通知
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
		}
		return R.error("系统异常,稍后重试!");
	}
	
	/**
	 * @Description 微信支付成功后回调次接口
	 */
	@ResponseBody
    @RequestMapping("/pay_callback")
	public String callBack(HttpServletRequest request,HttpServletResponse response){
		System.out.println("微信支付成功,微信发送的callback信息,请注意修改订单信息");
		InputStream is = null;
		try {
			is = request.getInputStream();//获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
			String xml = WechatUtils.inputStream2String(is, "UTF-8");
			Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);//将微信发的xml转map
						
			if(notifyMap.get("return_code").equals("SUCCESS")){
	                if(notifyMap.get("result_code").equals("SUCCESS")){  
	                String ordersSn = notifyMap.get("out_trade_no");//商户订单号 
	                String amountpaid = notifyMap.get("total_fee");//实际支付的订单金额:单位 分
	                BigDecimal amountPay = (new BigDecimal(amountpaid).divide(new BigDecimal("100"))).setScale(2);//将分转换成元-实际支付金额:元
	                String openid = notifyMap.get("openid");//用户的openid
	                String body = notifyMap.get("body");
	                System.out.println(notifyMap);
	                System.out.println("body=="+notifyMap);
	                //String trade_type = notifyMap.get("trade_type");  
	                
	                /*以下是自己的业务处理------仅做参考
	                 * 更新order对应字段/已支付金额/状态码
	                 */
	                  
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





