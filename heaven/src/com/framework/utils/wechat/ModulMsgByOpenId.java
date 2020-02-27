package com.framework.utils.wechat;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.framework.utils.ConfigUtil;
import com.framework.utils.StringUtil;

import net.sf.json.JSONObject;

public class ModulMsgByOpenId {
	
	public static void main(String[] args) {
		sercieBuySuccess("oTHLa1UlKwSXB6erbv12GWAj3oI0","DD945888101-111");//用户购买服务成功通知(通知用户)
		commoditySendGoods("oTHLa1UlKwSXB6erbv12GWAj3oI0","顺便快递","dt9888123666");//商品发货通知(通知用户)
		serviceOrderNotice("oTHLa1UlKwSXB6erbv12GWAj3oI0","王先生","190796661001");//服务预约派单通知(通知用户)
		
		serviceOrderNew("oTHLa1UlKwSXB6erbv12GWAj3oI0","大熊","8090","DD945888101-111");//服务工单提醒(通知后台工作人员)
		paiDanServiceNew("oTHLa1UlKwSXB6erbv12GWAj3oI0","DD945888101-111","范先生","18888665610");//新派单提醒(通知业务员)
		
	}
	
	/**
	 * 获取access_token
	 * @return
	 */
	public static String getAccessToken(){
		//获取access_token
		String resultAccessToken = WxUtil.sendGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ConfigUtil.getAppID()+"&secret="+ConfigUtil.getAppSecret());
		JSONObject jsonAccessToken = JSONObject.fromObject(resultAccessToken);
		//判断是否有access_token
		if(jsonAccessToken.containsKey("access_token")){
			String access_token = jsonAccessToken.getString("access_token");
			return access_token;
		}
		return null;
	}
	
	
	/**
	 * 用户购买服务成功通知(通知用户)
	 * @param openid
	 * @param orderid
	 */
	public static void sercieBuySuccess(String openid,String orderNumber){
		//获取access_token
		String access_token = getAccessToken();
		//判断是否有access_token
		if(!StringUtil.isEmpty(access_token)){
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("touser", openid);//OPENID
			jsonObject.put("template_id", "EVKwi9ZWTUuNIN-MLBEo0UpnP3oPesBSdFzj1rRaFbE");//模板id
			jsonObject.put("url", "http://tianyinde.com/web/index.html#/user");
			jsonObject.put("topcolor", "#FF0000");
			
			JSONObject productType = new JSONObject();
			productType.put("value", "服务订单");
			
			JSONObject name = new JSONObject();
			name.put("value", orderNumber);
			name.put("color", "#173177");
			
			JSONObject number = new JSONObject();
			number.put("value", "1份");
			number.put("color", "#173177");
			
			JSONObject expDate = new JSONObject();
			expDate.put("value", "一次性服务");
			expDate.put("color", "#173177");
			
			JSONObject remark = new JSONObject();
			remark.put("value", "稍后会派送业务员与您联系,请保持手机通讯正常!");
			remark.put("color", "#e11d1d");
			
			JSONObject jsonData = new JSONObject();
			jsonData.put("productType", productType);
			jsonData.put("name", name);
			jsonData.put("number", number);
			jsonData.put("expDate", expDate);
			jsonData.put("remark", remark);
			
			jsonObject.put("data", jsonData);
			
			String data = HttpRequest.sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token, jsonObject.toString());
			JSONObject result = JSONObject.fromObject(data);
			//判断是否有errcode
			if(result.containsKey("errcode")){
				if(!result.get("errcode").toString().equals("0")){
					System.out.println(data);
				}
			}else{
				System.out.println(data);
			}
		}else{
			System.out.println("获取access_token失败!");
		}
	}
	
	/**
	 * 服务预约派单通知(通知用户)
	 * @param openid
	 * @param name
	 * @param phone
	 */
	public static void serviceOrderNotice(String openid,String name,String phone){
		//获取access_token
		String access_token = getAccessToken();
		//判断是否有access_token
		if(!StringUtil.isEmpty(access_token)){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("touser", openid);//OPENID
			jsonObject.put("template_id", "VxQ61Gtx0QqpY49bTnmAOsNiDnnP9QZW2Cdu3cbXtG8");//模板id
			jsonObject.put("url", "http://tianyinde.com/web/index.html#/user");
			jsonObject.put("topcolor", "#FF0000");
			
			JSONObject first = new JSONObject();
			first.put("value", "您预约的服务已派送业务员，请保持手机畅通以便业务员与您取得联系。");
			first.put("color", "#e11d1d");
			
			JSONObject goods_name = new JSONObject();
			goods_name.put("value", "预约服务");
			goods_name.put("color", "#173177");
			
			JSONObject service_time = new JSONObject();
			service_time.put("value", "与业务员沟通确认的服务套餐为准");
			service_time.put("color", "#173177");
			
			JSONObject engineer_name = new JSONObject();
			engineer_name.put("value", name+"/"+phone);
			engineer_name.put("color", "#173177");
			
			JSONObject cost_standard = new JSONObject();
			cost_standard.put("value", "与业务员沟通确认的服务套餐为准");
			cost_standard.put("color", "#173177");
			
			JSONObject jsonData = new JSONObject();
			jsonData.put("first", first);
			jsonData.put("goods_name", goods_name);
			jsonData.put("service_time", service_time);
			jsonData.put("engineer_name", engineer_name);
			jsonData.put("cost_standard", cost_standard);
			
			jsonObject.put("data", jsonData);
			
			String data = HttpRequest.sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token, jsonObject.toString());
			JSONObject result = JSONObject.fromObject(data);
			//判断是否有errcode
			if(result.containsKey("errcode")){
				if(!result.get("errcode").toString().equals("0")){
					System.out.println(data);
				}
			}else{
				System.out.println(data);
			}
		}else{
			System.out.println("获取access_token失败!");
		}
	}
	
	/**
	 * 商品发货通知(通知用户)
	 * @param openid
	 * @param name
	 * @param number
	 */
	public static void commoditySendGoods(String openid,String name,String number){
		//获取access_token
		String access_token = getAccessToken();
		//判断是否有access_token
		if(!StringUtil.isEmpty(access_token)){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("touser", openid);//OPENID
			jsonObject.put("template_id", "biQIsFY6mWsK99Mwad6Y2GdmU0O-wZD6nEuE9u3_r48");//模板id
			jsonObject.put("url", "http://tianyinde.com/web/index.html#/user");
			jsonObject.put("topcolor", "#FF0000");
			
			JSONObject first = new JSONObject();
			first.put("value", "您好,您购买的商品已发货,请注意查收!");
			first.put("color", "#e11d1d");
			
			JSONObject keyword1 = new JSONObject();
			keyword1.put("value", name);
			keyword1.put("color", "#173177");
			
			JSONObject keyword2 = new JSONObject();
			keyword2.put("value", number);
			keyword2.put("color", "#173177");
			
			JSONObject keyword3 = new JSONObject();
			keyword3.put("value", "天胤德商品");
			keyword3.put("color", "#173177");
			
			JSONObject keyword4 = new JSONObject();
			keyword4.put("value", "1");
			keyword4.put("color", "#173177");
			
			JSONObject jsonData = new JSONObject();
			jsonData.put("first", first);
			jsonData.put("keyword1", keyword1);
			jsonData.put("keyword2", keyword2);
			jsonData.put("keyword3", keyword3);
			jsonData.put("keyword4", keyword4);
			
			jsonObject.put("data", jsonData);
			
			String data = HttpRequest.sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token, jsonObject.toString());
			JSONObject result = JSONObject.fromObject(data);
			//判断是否有errcode
			if(result.containsKey("errcode")){
				if(!result.get("errcode").toString().equals("0")){
					System.out.println(data);
				}
			}else{
				System.out.println(data);
			}
		}else{
			System.out.println("获取access_token失败!");
		}
	}
	
	
	/**
	 * 服务工单提醒(通知后台工作人员)
	 * @param openid
	 * @param name
	 * @param id
	 * @param orderNumber
	 */
	public static void serviceOrderNew(String openid,String name,String id,String orderNumber){
		//获取access_token
		String access_token = getAccessToken();
		//判断是否有access_token
		if(!StringUtil.isEmpty(access_token)){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("touser", openid);//OPENID
			jsonObject.put("template_id", "U6tTwfLGl3toGu989saZ6B3WpFqCSFUTFgKwc5EXiFg");//模板id
			jsonObject.put("url", "http://tianyinde.com/web/index.html#/user");
			jsonObject.put("topcolor", "#FF0000");
			
			JSONObject first = new JSONObject();
			first.put("value", "您有新的服务订单,请及时派送业务员进行处理!");
			first.put("color", "#e11d1d");
			
			JSONObject keyword1 = new JSONObject();
			keyword1.put("value", orderNumber);
			keyword1.put("color", "#173177");
			
			JSONObject keyword2 = new JSONObject();
			keyword2.put("value", name+",ID-"+id);
			keyword2.put("color", "#173177");
			
			JSONObject keyword3 = new JSONObject();
			keyword3.put("value", "天胤德服务订单");
			keyword3.put("color", "#173177");
			
			JSONObject jsonData = new JSONObject();
			jsonData.put("first", first);
			jsonData.put("keyword1", keyword1);
			jsonData.put("keyword2", keyword2);
			jsonData.put("keyword3", keyword3);
			
			jsonObject.put("data", jsonData);
			
			String data = HttpRequest.sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token, jsonObject.toString());
			JSONObject result = JSONObject.fromObject(data);
			//判断是否有errcode
			if(result.containsKey("errcode")){
				if(!result.get("errcode").toString().equals("0")){
					System.out.println(data);
				}
			}else{
				System.out.println(data);
			}
		}else{
			System.out.println("获取access_token失败!");
		}
	}
	
	
	/**
	 * 新派单提醒(通知业务员)
	 * @param openid
	 * @param orderNumber
	 * @param name
	 * @param phone
	 */
	public static void paiDanServiceNew(String openid,String orderNumber,String name,String phone){
		//获取access_token
		String access_token = getAccessToken();
		//判断是否有access_token
		if(!StringUtil.isEmpty(access_token)){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("touser", openid);//OPENID
			jsonObject.put("template_id", "smR0Oofmr5yqjnxGP4QzPLoaCMdNs5YNw1hC7Z7PhgQ");//模板id
			jsonObject.put("url", "http://tianyinde.com/web/index.html#/user");
			jsonObject.put("topcolor", "#FF0000");
			
			JSONObject first = new JSONObject();
			first.put("value", "您有新的派单消息,请及时联系用户!");
			first.put("color", "#e11d1d");
			
			JSONObject keyword1 = new JSONObject();
			keyword1.put("value", orderNumber);
			keyword1.put("color", "#173177");
			
			JSONObject keyword2 = new JSONObject();
			keyword2.put("value", "天胤德服务订单");
			keyword2.put("color", "#173177");
			
			JSONObject keyword3 = new JSONObject();
			keyword3.put("value", name+"/"+phone);
			keyword3.put("color", "#173177");
			
			JSONObject keyword4 = new JSONObject();
			keyword4.put("value", "预约服务");
			keyword4.put("color", "#173177");
			
			JSONObject keyword5 = new JSONObject();
			SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");//设置日期格式
			keyword5.put("value", df.format(new Date()));
			keyword5.put("color", "#173177");
			
			JSONObject jsonData = new JSONObject();
			jsonData.put("first", first);
			jsonData.put("keyword1", keyword1);
			jsonData.put("keyword2", keyword2);
			jsonData.put("keyword3", keyword3);
			jsonData.put("keyword4", keyword4);
			jsonData.put("keyword5", keyword5);
			
			jsonObject.put("data", jsonData);
			
			String data = HttpRequest.sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token, jsonObject.toString());
			JSONObject result = JSONObject.fromObject(data);
			//判断是否有errcode
			if(result.containsKey("errcode")){
				if(!result.get("errcode").toString().equals("0")){
					System.out.println(data);
				}
			}else{
				System.out.println(data);
			}
		}else{
			System.out.println("获取access_token失败!");
		}
	}
	
}


