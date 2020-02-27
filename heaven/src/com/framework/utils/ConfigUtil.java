package com.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import org.springframework.web.multipart.MultipartFile;

/**
 * 读取配置属性值 工具类
 * @author WTW
 */
public class ConfigUtil {
	
	private static final String PROPERTIES_NAME = "setting.properties";
	private static Properties p;
	static {
		try {
			p = new Properties();
			InputStream in = ConfigUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_NAME);
			p.load(new InputStreamReader(in,"UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取单个 spring.properties 配置文件的值
	 * @param key 键
	 * @return 值
	 */
	public static String getValue(String key){
		return p.getProperty(key);
	}
	
	/**
	 * 修改或者新增key
	 * @param key
	 * @param value
	 */
	public static void update(String key, String value) {
		p.setProperty(key, value);
		FileOutputStream oFile = null;
		try {
			oFile = new FileOutputStream(PROPERTIES_NAME);
			//将Properties中的属性列表（键和元素对）写入输出流
			p.store(oFile, "");
			oFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 判断tomcat的webapps下的文件夹是否存在，不存在则创建
	 * @param path
	 * @return
	 */
	public static void createWeappsFiles(String path){
		String filePath = getTomcatWebapps()+path;
		
		String paths[] = filePath.split("/");
		String dir = paths[0];
		for (int i = 0; i < paths.length - 1; i++) {
			try {
				dir = dir + "/" + paths[i + 1];
				File dirFile = new File(dir);
				if (!dirFile.exists()) {
					dirFile.mkdir();
				}
			} catch (Exception err) {
				System.err.println("ELS - Chart : 文件夹创建发生异常");
			}
		}
	}
	
	/**
	 * 删除文件
	 * @param path
	 */
	public static void deleteFile(String path){
		String filePath = getTomcatWebapps()+path;
		try {
        	File file=new File(filePath);
            if(file.exists()&&file.isFile()) {
                file.delete();
            }
		} catch (Exception e) {
		}
	}
	
	/**
	 * 删除文件集合
	 * @param path
	 */
	public static void deleteFile(List<String> pathList){
		for(String path : pathList){
			String filePath = getTomcatWebapps()+path;
			try {
	        	File file=new File(filePath);
	            if(file.exists()&&file.isFile()) {
	                file.delete();
	            }
			} catch (Exception e) {
			}
		}
	}
	
	
	
	/**
	 * 删除文件夹  
	 * @param path 带有文件路径的文件夹路径/folder01/folder02/file.jpg,删除文件夹folder02
	 */
    public static void delFolder(String path) {
         try {
        	String[] pathSplit = path.split("/");
        	path = path.replace(pathSplit[pathSplit.length-1], "");
        	String folderPath = getTomcatWebapps()+path;
        	 
            delAllFile(folderPath); //删除完里面所有内容  
            String filePath = folderPath;  
            filePath = filePath.toString();  
            java.io.File myFilePath = new java.io.File(filePath);  
            myFilePath.delete(); //删除空文件夹  
         } catch (Exception e) {  
            e.printStackTrace();   
         }  
    }
    
    /**
     * 删除指定文件夹下的所有文件
     * @param path
     * @return
     */
    public static boolean delAllFile(String path) {
       boolean flag = false;  
       File file = new File(path);  
       if (!file.exists()) {  
         return flag;  
       }  
       if (!file.isDirectory()) {  
         return flag;  
       }  
       String[] tempList = file.list();  
       File temp = null;  
       for (int i = 0; i < tempList.length; i++) {  
          if (path.endsWith(File.separator)) {  
             temp = new File(path + tempList[i]);  
          } else {  
              temp = new File(path + File.separator + tempList[i]);  
          }  
          if (temp.isFile()) {  
             temp.delete();  
          }  
          if (temp.isDirectory()) {  
             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件  
             delFolder(path + "/" + tempList[i]);//再删除空文件夹  
             flag = true;  
          }  
       }  
       return flag;  
     }  
	
	
	/**
	 * 获取tomcat的webapps路径
	 * @return
	 */
	public static String getTomcatWebapps(){
		return getValue("TOMCAT_WEBAPP");
	}
	
	/**
	 * 获取微信收款码的路径
	 * @return
	 */
	public static String getWechatPayImage(){
		String path = getValue("CUSTOMER_WECHAT_PAYIMAGE");
		createWeappsFiles(path);
		return path;
	}
	
	/**
	 * 保存微信收款码
	 */
	public static String saveWechatPayImage(MultipartFile multipartFile){
		//图片路径和地址
		String[] names = multipartFile.getOriginalFilename().split("\\.");
		String imagePath = getWechatPayImage() + StringUtil.randomUUID() + "." + names[names.length-1];
		try {
			multipartFile.transferTo(new File(ConfigUtil.getTomcatWebapps()+imagePath));
			
			return imagePath;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 后台创建模板的图片文件路径
	 * @return
	 */
	public static String getSystemModul(Long modulId,String modulCode){
		String path = getValue("SYSTEM_MODUL").replace("{modulid}", modulCode+"/"+String.valueOf(modulId));
		createWeappsFiles(path);
		return path;
	}
	
	/**
	 * 复制模板图片
	 * @param modulID
	 * @param imagePath
	 * @return
	 */
	public static String copyModulImagesByUser(Long modulId,String modulCode,String imagePath){
		String[] ordPathImage = imagePath.split("/");
		String newPath = getSystemModul(modulId,modulCode)+ordPathImage[ordPathImage.length-1];
		try {
			copyFile(imagePath,newPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return newPath;
	}
	/**
	 * 复制文件的方法
	 * @param oldPath
	 * @param newPath
	 * @throws IOException
	 */
	public static void copyFile(String oldPath, String newPath) throws IOException {
		oldPath = getTomcatWebapps()+oldPath;
		newPath = getTomcatWebapps()+newPath;
        File oldFile = new File(oldPath);
        File file = new File(newPath);
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);;

        byte[] buffer=new byte[2097152];
        int readByte = 0;
        while((readByte = in.read(buffer)) != -1){
            out.write(buffer, 0, readByte);
        }
    
        in.close();
        out.close();
    }
	
	/**
	 * 删除模板文件
	 */
	public static void delModulFolder(Long modulId,String modulCode){
		String path = getValue("SYSTEM_MODUL").replace("{modulid}", modulCode+"/"+String.valueOf(modulId));
		delFolder(path);
	}
	
	/**
	 * 后台创建商品的图片文件路径
	 * @return
	 */
	public static String getSystemCommodity(String commodityid){
		String path = getValue("SYSTEM_COMMODITY").replace("{commodityid}", commodityid);
		createWeappsFiles(path);
		return path;
	}
	
	/**
	 * 获取音乐存放地地址
	 * @return
	 */
	public static String getMusciSfilePath(){
		createWeappsFiles(getValue("SYSTEM_MUSICS"));
		return getValue("SYSTEM_MUSICS");
	}
	
	/**
	 * 获取视频存放的路径
	 * @return
	 */
	public static String getVideosPath(){
		String path = getValue("SYSTEM_VIDEOS");
		createWeappsFiles(path);
		return path;
	}
	
	/**
	 * 获取视频标图存放的路径
	 * @return
	 */
	public static String getVideoImagesPath(){
		String path = getValue("SYSTEM_VIDEO_IMAGES");
		createWeappsFiles(path);
		return path;
	}
	
	/**
	 * 获取ffmpeg的路径
	 * @return
	 */
	public static String getFfmpegPath(){
		return getValue("SYSTEM_FFMPEG");
	}
	
	/**
	 * 获取mencoder的路径
	 * @return
	 */
	public static String getMencoderPath(){
		return getValue("SySTEM_MENCODER");
	}
	
	/**
	 * 业务员头像存储文件路径
	 * @return
	 */
	public static String getSalesmanPortrait(String userid){
		String path = getValue("SALESMAN_PROTRAIT").replace("{userid}", userid);
		createWeappsFiles(path);
		return path;
	}
	
	/**
	 * 客户头像的文件路径
	 * @return
	 */
	public static String getCustomerPortrait(){
		return getValue("CUSTOMER_PROTRAIT");
	}
	
	/**
	 * 客户模板图片的文件路径
	 * @return
	 */
	public static String getCustomerModul(){
		return getValue("CUSTOMER_MODUL");
	}
	
	/**
	 * 获取微信AppID
	 * @return
	 */
	public static String getAppID(){
		return getValue("APPID");
	}
	
	/**
	 * 获取微信AppSecret
	 * @return
	 */
	public static String getAppSecret(){
		return getValue("APPSECRET");
	}
	
	/**
	 * 获取微信Token
	 * @return
	 */
	public static String getToken(){
		return getValue("TOKEN");
	}
	
	/**
	 * 获取微信EncodingAESKey
	 * @return
	 */
	public static String getEncodingAESKey(){
		return getValue("ENCODINGAESKEY");
	}	
	
	/**
	 * 获取微信商户号
	 * @return
	 */
	public static String getMerchant_Number(){
		return getValue("MERCHANT_NUMBER");
	}
	
	/**
	 * 获取微信商户名称
	 * @return
	 */
	public static String getMerchant_Name(){
		return getValue("MERCHANT_NAME");
	}
	
	/**
	 * api密匙
	 * @return
	 */
	public static String getMerchant_Api_Secret(){
		return getValue("MERCHANT_API_SECRET");
	}
	
	/**
	 * 支付成功微信回调通知的地址
	 * @return
	 */
	public static String getWechat_Pay_Callback(){
		return getValue("WECHAT_PAY_CALLBACK");
	}
	
	/**
	 * 商品支付成功微信回调通知的地址
	 * @return
	 */
	public static String getWechat_Commodity_Callback(){
		return getValue("WECHAT_COMMODITY_CALLBACK");
	}
	
	/**
	 * 奠金支付成功微信回调通知的地址
	 * @return
	 */
	public static String getWechat_Cashmoney_Callback(){
		return getValue("WECHAT_CASHMONEY_CALLBACK");
	}
	
	/**
	 * 平台礼物支付成功微信回调通知的地址
	 * @return
	 */
	public static String getWechat_GiftService_Callback(){
		return getValue("WECHAT_GIFTSRVICE_CALLBACK");
	}
	
	
	/**
	 * 服务支付成功微信回调通知的地址
	 * @return
	 */
	public static String getWechat_Service_Callback(){
		return getValue("WECHAT_SERVICE_CALLBACK");
	}
	
	/**
	 * 服务套餐支付成功微信回调通知的地址
	 * @return
	 */
	public static String getWechat_ServiceMeal_Callback(){
		return getValue("WECHAT_SERVICEMEAl_CALLBACK");
	}
	
	/**
	 * 定金支付成功微信回调通知的地址
	 * @return
	 */
	public static String getWechat_ServiceDep_Callback(){
	    return getValue("WECHAT_SERVICEDEP_CALLBACK");
	}
	
	/**
	 * 微信授权登入接口
	 * @return
	 */
	public static String getWechat_Login(){
		return getValue("WECHAT_LOGIN");
	}
	
	/**
	 * 业务员微信授权登入接口
	 * @return
	 */
	public static String getWechat_Salesman_Login(){
		return getValue("WECHAT_SALESMAN_LOGIN");
	}
	
	/**
	 * 授权微信返回信息接收的接口
	 * @return
	 */
	public static String getWechat_CallBackUserMSG(){
		return getValue("WECHAT_CALLBACK_USERMSG");
	}
	
	/**
	 * 业务员授权微信返回信息接收的接口
	 * @return
	 */
	public static String getSalesmanWechat_CallBackUserMSG(){
		return getValue("WECHAT_SALESMAN_CALLBACK_USERMSG");
	}
	
	/**
	 * 授权成功后跳转的页面
	 * @return
	 */
	public static String getLogin_Href(){
		return getValue("LOGIN_HREF");
	}
	
	public static void main(String[] args) {
		
	}

}














