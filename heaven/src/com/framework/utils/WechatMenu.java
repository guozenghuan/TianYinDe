package com.framework.utils;

import com.alibaba.fastjson.JSONObject;
import com.framework.entity.ViewButton;
import com.framework.utils.wechat.HttpRequest;
import com.framework.utils.wechat.WxUtil;

import net.sf.json.JSONArray;

public class WechatMenu {
	
	public static void createMenu(){
		ViewButton cbt=new ViewButton();
		cbt.setUrl("http://tianyinde.com/web/index.html#/mall");
        cbt.setName("平台用户");
        cbt.setType("view");
         
         
        ViewButton vbt=new ViewButton();
        vbt.setUrl("http://tianyinde.com/login_salesman.html");
        vbt.setName("业务员");
        vbt.setType("view");
         
        JSONArray button=new JSONArray();
        button.add(cbt);
        button.add(vbt);
         
        JSONObject menujson=new JSONObject();
        menujson.put("button", button);
        System.out.println(menujson.toJSONString());
        
        //获取access_token
		String resultAccessToken = WxUtil.sendGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ConfigUtil.getAppID()+"&secret="+ConfigUtil.getAppSecret());
		net.sf.json.JSONObject jsonAccessToken = net.sf.json.JSONObject.fromObject(resultAccessToken);
		//判断是否有access_token
		if(jsonAccessToken.containsKey("access_token")){
			String access_token = jsonAccessToken.getString("access_token");
			
			//这里为请求接口的url   +号后面的是token，这里就不做过多对token获取的方法解释
	        String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
	        
	        try{
	        	String rs=HttpRequest.sendPost(url, menujson.toJSONString());
//	            String rs=HttpUtils.sendPostBuffer(url, menujson.toJSONString());
	            System.out.println("创建微信菜单: "+rs);
	        }catch(Exception e){
	            System.out.println("请求错误！");
	        }
		}else{
			System.out.println("创建微信菜单失败,获取不到微信access_token");
		}
	}

	public static void main(String[] args) {
		createMenu();
    }
	
}
