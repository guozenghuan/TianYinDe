package com.framework.utils.wechat;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class WechatUtils {
	
	/** auth:wtao
	  * InputStream流转换成String字符串
	  * @param inStream InputStream流
	  * @param encoding 编码格式
	  * @return String字符串
	  */
	    public static String inputStream2String(InputStream inStream, String encoding){
	     String result = null;
	     ByteArrayOutputStream outStream = null;
	     try {
	      if(inStream != null){
	       outStream = new ByteArrayOutputStream();
	       byte[] tempBytes = new byte[1024];
	       int count = 0;
	       while((count = inStream.read(tempBytes)) != -1){
	        outStream.write(tempBytes, 0, count);
	       }
	       tempBytes = null;
	       outStream.flush();
	       result = new String(outStream.toByteArray(), encoding);
	       outStream.close();
	      }
	     } catch (Exception e) {
	      result = null;
	     } 
	     return result;
	    }

}
