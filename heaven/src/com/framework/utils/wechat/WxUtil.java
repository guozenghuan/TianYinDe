package com.framework.utils.wechat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
//import java.util.List;
import java.util.Map;

import com.framework.utils.ConfigUtil;
import com.framework.utils.StringUtil;
import com.github.wxpay.sdk.WXPayUtil;

import net.sf.json.JSONObject;

public class WxUtil {
	
	public static void setWechatSharePareme(){
		//获取access_token
		String resultAccessToken = sendGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ConfigUtil.getAppID()+"&secret="+ConfigUtil.getAppSecret());
		JSONObject jsonAccessToken = JSONObject.fromObject(resultAccessToken);
		//判断是否有access_token
		if(jsonAccessToken.containsKey("access_token")){
			String access_token = jsonAccessToken.getString("access_token");
			//保存
			ConfigUtil.update("ACCESS_TOKEN", access_token);
			
			//jsapi_ticket
			String resultJsapiTicket = sendGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi");
			JSONObject jsonJsapiTicket = JSONObject.fromObject(resultJsapiTicket);
			//判断是否有jsapi_ticket
			if(jsonJsapiTicket.containsKey("ticket")){
				String jsapi_ticket = jsonJsapiTicket.getString("ticket");
				//保存
				ConfigUtil.update("JSAPI_TICKET", jsapi_ticket);
				
				//设置过期时间
				long currentTime = System.currentTimeMillis();
				//设置1个半小时过期
				currentTime += 90*60*1000;
				//保存
				ConfigUtil.update("WECHAT_TIME", Long.toString(currentTime));
			}
		}
	}

	public static Map<String, Object> getWechatSharePareme(String url) {
		//判断时间是否过期
		String timeStr = ConfigUtil.getValue("WECHAT_TIME");
		if(StringUtil.isEmpty(timeStr)){
			//时间已经过期
			setWechatSharePareme();
		}else{
			Long nowTime = System.currentTimeMillis();
			if(nowTime.longValue()>Long.valueOf(timeStr).longValue()){
				//时间已经过期
				setWechatSharePareme();
			}
		}
		
		String noncestr = WXPayUtil.generateNonceStr();
		String timestamp = Long.toString(System.currentTimeMillis()/1000);
		String sh1String ="jsapi_ticket="+ConfigUtil.getValue("JSAPI_TICKET")+"&"+
				"noncestr="+noncestr+"&"+
				"timestamp="+timestamp+"&"+
				"url="+url;
		
		String signature = SHA1(sh1String);
		Map<String, Object> data = new HashMap<>();
		data.put("appid", ConfigUtil.getAppID());
		data.put("timestamp", timestamp);
		data.put("noncestr", noncestr);
		data.put("signature", signature);
		return data;
	}
	
	/**
	 * 
	 * @author：罗国辉
	 * 			@date： 2015年12月17日 上午9:24:43
	 * 			@description： SHA、SHA1加密
	 * 			@parameter： str：待加密字符串
	 * 			@return： 加密串
	 **/
	public static String SHA1(String str) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1"); // 如果是SHA加密只需要将"SHA-1"改成"SHA"即可
			digest.update(str.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexStr = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexStr.append(0);
				}
				hexStr.append(shaHex);
			}
			return hexStr.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String sendGet(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
//			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			/*for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}*/
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
}
