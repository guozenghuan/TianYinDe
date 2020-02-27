package com.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.misc.BASE64Decoder;

/**
 * 处理模板的工具
 * @author Administrator
 *
 */
public class ModulHtmlUtils {
	
	 /**
     * 清空模板内容
     * @param fileName
     */
    public static void clearInfoForFile(String fileName) {
        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 读取html文件并且修改
     * @param modulPath
     * @param bodyHtml
     * @return
     */
    public static R rederAndUpdateHtmlFile(String modulPath,String bodyHtml){
    	//读取modul.html文件
		File file = new File(ConfigUtil.getTomcatWebapps()+modulPath);
        InputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  
        StringBuffer buffer = new StringBuffer();  
        byte[] bytes = new byte[1024];
        try {
            for(int n ; (n = input.read(bytes))!=-1 ; ){
                buffer.append(new String(bytes,0,n,"UTF-8"));  
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String startHtml = "";
        String cencerHtml = "";
        String endHtml = "";
        
        String html = buffer.toString();
        if(!StringUtil.isEmpty(html)){
        	if(StringUtil.isIndexOfStr(html, "<body>")){
        		String[] starts = html.split("<body>");
        		startHtml = starts[0]+"<body>";
        		if(starts.length==2){
        			if(StringUtil.isIndexOfStr(starts[1], "</body>")){
        				String[] ends = starts[1].split("</body>");
        				if(ends.length==2){
        					cencerHtml = ends[0];
        					endHtml = "</body>"+ends[1];
        				}else{
        					return R.error("查找不到该请帖,可能已经删除,请重新选择请帖后操作!");
        				}
        			}else{
        				return R.error("查找不到该请帖,可能已经删除,请重新选择请帖后操作!");
        			}
        		}else{
        			return R.error("查找不到该请帖,可能已经删除,请重新选择请帖后操作!");
        		}
        	}else {
        		return R.error("查找不到该请帖,可能已经删除,请重新选择请帖后操作!");
			}
        }else{
        	return R.error("查找不到该请帖,可能已经删除,请重新选择请帖后操作!");
        }
        //保存图片base64到本地
        R saveImageR = saveBase64ByImageFile(bodyHtml,modulPath);
        if(Integer.valueOf(saveImageR.get("code").toString()) != 0){
        	return saveImageR;
        }
        bodyHtml = saveImageR.get("html").toString();
      
        //写入html代码
        String saveHtml = startHtml.trim()+bodyHtml.trim()+endHtml.trim();
        wriderHtml(ConfigUtil.getTomcatWebapps()+modulPath,saveHtml);
        
        //删除掉已经修改的图片
        try {
        	deleteUpdateHtml(modulPath,cencerHtml,bodyHtml);
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        return R.ok("操作成功!");
    }
    
    /**
     * 删除图片
     * @param historyHtml 旧的html源码
     * @param updateHtml 已修改好的html源码
     */
    public static void deleteUpdateHtml(String modulPath,String historyHtml,String updateHtml){
    	//判断修改的文件是属于系统还是用户
    	String saveFile = "statics";//默认系统
    	String[] modulPathStrs = modulPath.split("/");
    	String typeName = modulPathStrs[modulPathStrs.length-1];
    	if(StringUtil.isEmpty(typeName) || !StringUtil.isIndexOfStr(typeName, ".")){
    		return;
    	}
    	if(!typeName.trim().equals("modul.html")){
    		//就是用户
    		saveFile = typeName.split(".")[0].trim();
    	}
    	//保存图片的文件夹路径
    	String webappsFilePath = modulPath.replace(typeName.trim(), saveFile+"/");//保存到html代码中的文件夹路径
    	
    	//获取历史html路径
    	List<String> history = new ArrayList<>();
    	while (true) {
    		String historyOneSrc = getHtmlSrcByKey(webappsFilePath,historyHtml);
    		if(!StringUtil.isEmpty(historyOneSrc)){
    			historyHtml = historyHtml.replace(historyOneSrc, "=img=");
    			history.add(historyOneSrc);
    		}else{
    			break;
    		}
		}
    	
    	//获取修改好的html路径
    	List<String> update = new ArrayList<>();
    	while (true) {
    		String historyOneSrc = getHtmlSrcByKey(webappsFilePath,updateHtml);
    		if(!StringUtil.isEmpty(historyOneSrc)){
    			updateHtml = updateHtml.replace(historyOneSrc, "=img=");
    			update.add(historyOneSrc);
    		}else{
    			break;
    		}
		}
    	
    	//判断哪些被删除
    	for(String flagSrc : history){
    		if(!update.contains(flagSrc)){
    			//删除文件
    			ConfigUtil.deleteFile(flagSrc);
    		}
    	}
    }
    
    /**
    * 获取两个List的不同元素
    * @param list1
    * @param list2
    * @return
    */
    public static List<String> getDiffrent(List<String> list1, List<String> list2) {
	    List<String> diff = new ArrayList<String>();
	    for(String str:list1){
		    if(!list2.contains(str)){
		    	diff.add(str);
		    }
	    }
	    return diff;
    }

    
    /**
     * 保存html中的图片
     * @param html
     * @return
     */
    public static R saveBase64ByImageFile(String html,String modulPath){
    	List<Map<String, String>> mapsList = new ArrayList<>();
    	if(!StringUtil.isEmpty(html)){
    		int indexCode = 0;
    		String base64 = getHtmlBase64(html);
        	if(!StringUtil.isEmpty(base64)){
        		//保存
        		Map<String, String> mapOne = new HashMap<>();
        		mapOne.put("indexCode", "==indexCode_"+indexCode+"==");
        		mapOne.put("base64", base64);
        		mapsList.add(mapOne);
        		html = html.replace(base64, "==indexCode_"+indexCode+"==");
        		while (true) {
        			indexCode += 1;
        			base64 = getHtmlBase64(html);
        			if(!StringUtil.isEmpty(base64)){
        				//保存
                		Map<String, String> mapSecens = new HashMap<>();
                		mapSecens.put("indexCode", "==indexCode_"+indexCode+"==");
                		mapSecens.put("base64", base64);
                		mapsList.add(mapSecens);
                		html = html.replace(base64, "==indexCode_"+indexCode+"==");
        			}else{
        				break;
        			}
        		}
        	}
    	}
    	
    	//判断修改的文件是属于系统还是用户
    	String saveFile = "statics";//默认系统
    	String[] modulPathStrs = modulPath.split("/");
    	String typeName = modulPathStrs[modulPathStrs.length-1];
    	if(StringUtil.isEmpty(typeName) || !StringUtil.isIndexOfStr(typeName, ".")){
    		return R.error("查找不到该请帖,可能已经删除,请重新选择请帖后操作!");
    	}
    	if(!typeName.trim().equals("modul.html")){
    		//就是用户
    		saveFile = typeName.split("\\.")[0].trim();
    	}
    	//保存图片的文件夹路径
    	String webappsFilePath = modulPath.replace(typeName.trim(), saveFile+"/");//保存到html代码中的文件夹路径
    	String folderPath = ConfigUtil.getTomcatWebapps()+webappsFilePath;//保存到tomcat模板中的路径
    	
    	//保存图片到本地
    	for(Map<String, String> saveMap : mapsList){
    		String indexCode = saveMap.get("indexCode");
    		String base64 = saveMap.get("base64");
    		String imageName = generateImage(base64,folderPath);
    		String imageSavePath = webappsFilePath + imageName;
    		//替换html中的路径
    		html = html.replace(indexCode, imageSavePath);
    	}
    	
    	return R.ok().put("html", html);
    }
    
    /**
     * 将base64编码字符串转换为图片
     * @param base64 base64编码
     * @param folderPath 保存的路径
     * @param fileName 保存的文件名
     * @return
     */
    public static String generateImage(String base64,String folderPath) {
    	if (base64 == null) {
    		return "";
    	}
    	if(StringUtil.isIndexOfStr(base64, ";base64,")){
    		base64 = base64.split(";base64,")[1].trim();
    	}else{
    		return "";
    	}
    	
    	//创建图片文件
    	String fileName = StringUtil.randomUUID().trim()+".png";
    	
    	BASE64Decoder decoder = new BASE64Decoder();
    	// 解密
		byte[] b;
		try {
			b = decoder.decodeBuffer(base64);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			
			File file = new File(folderPath);
			//判断文件路径的文件夹是否存在不存在则创建
			if (!file.exists()) {
				file.mkdirs();
			}
			
			OutputStream out = new FileOutputStream(folderPath+fileName);
			out.write(b);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fileName;
    }
    
    /**
     * 获取html代码中的base64编码
     * @param html
     * @return
     */
    public static String getHtmlBase64(String html){
    	int startIndex = 0;
    	int endIndex = 0;
    	
    	String baseCode = "data:image/png;base64,";
    	int baseIndex = html.indexOf(baseCode);
    	if(baseIndex==-1){
    		return "";
    	}
    	char indexCode = html.charAt(baseIndex);
    	startIndex = baseIndex;
    	while (true) {
    		baseIndex += 1;
    		indexCode = html.charAt(baseIndex);
    		
    		if(StringUtil.isEmpty(String.valueOf(indexCode))){
    			return "";
    		}
    		
			endIndex = baseIndex;
    		if(String.valueOf(indexCode).trim().equals("\"") || String.valueOf(indexCode).trim().equals("&")){
    			String base64Code = html.substring(startIndex, endIndex);
    	    	return base64Code;
    		}
		}
    }
    
    /**
     * 获取html代码中的base64编码
     * @param html
     * @return
     */
    public static String getHtmlSrcByKey(String key,String html){
    	int startIndex = 0;
    	int endIndex = 0;
    	
    	String baseCode = key.trim();
    	int baseIndex = html.indexOf(baseCode);
    	if(baseIndex==-1){
    		return "";
    	}
    	char indexCode = html.charAt(baseIndex);
    	startIndex = baseIndex;
    	while (true) {
    		baseIndex += 1;
    		indexCode = html.charAt(baseIndex);
    		
    		if(StringUtil.isEmpty(String.valueOf(indexCode))){
    			return "";
    		}
    		
			endIndex = baseIndex;
    		if(String.valueOf(indexCode).trim().equals("\"") || String.valueOf(indexCode).trim().equals("&")){
    			String base64Code = html.substring(startIndex, endIndex);
    	    	return base64Code;
    		}
		}
    }
    
    /**
     * 
     * @param rows
     * @param filePath
     * @param fileName
     * @return
     */
    public static void wriderHtml(String filePath,String saveHtml) {
		try {
			FileWriter fw = new FileWriter(filePath);
			 fw.write(saveHtml);
	        fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
