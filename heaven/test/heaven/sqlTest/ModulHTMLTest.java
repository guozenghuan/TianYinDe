package heaven.sqlTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.framework.utils.StringUtil;
import com.framework.utils.URLConnectionDownloader;

public class ModulHTMLTest {
	
	@Test
	public void modulHTMLTest(){
		//读取modul.html文件
		String modulPath = "D:/software_main/Java/apache-tomcat-7.0.90/webapps/moduls/modul_04/modul.html";
		String saveImagePath = "D:/software_main/Java/apache-tomcat-7.0.90/webapps/moduls/modul_04/statics/";
		
		File file = new File(modulPath);  
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
        
        List<String> saveUrl = new ArrayList<>();
        String [] htmls = buffer.toString().split("&quot;");
        for(String html : htmls){
        	if(StringUtil.isIndexOfStr(html, "https://res.zhangu365.com/syspic/bg")){
        		saveUrl.add(html);
        	}
        }
        
        System.out.println(JSONObject.toJSON(saveUrl));
        
        for(String url : saveUrl){
        	System.out.println(url);
        	if(StringUtil.isIndexOfStr(url, "?")){
        		url = url.split("\\?")[0];
        	}
        	
        	String[] size = url.split("/");
        	String saveName = size[size.length-1];//图片名称
        	
        	System.out.println(saveImagePath+saveName);
        	
        	//保存图片
        	try {
				URLConnectionDownloader.download(url, saveImagePath+saveName);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        
	}
	
	@Test
	public void modulHTMLTwoTest(){
		//读取modul.html文件
		String modulPath = "D:/software_main/Java/apache-tomcat-7.0.90/webapps/moduls/modul_01/modul2.html";
		
		File file = new File(modulPath);  
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
        					endHtml = "</body>"+ends[1];
        				}
        			}
        		}
        	}
        }
        
        System.out.println(startHtml);
        System.out.println(endHtml);
        
	}
	
	@Test
	public void folderNamesTest(){
		String path = "C:\\Users\\Administrator\\Desktop\\农场游戏\\界面\\图片资源";
        File file = new File(path);  
        if (!file.exists()) {  
          return ;  
        }  
        if (!file.isDirectory()) {  
          return;  
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
        	   System.out.println(temp.getName());
//        	   File f_new = new File(path+"\\"+temp.getName(), i+".png");
        	   File f_new = new File(path+"\\"+i+".png");
        	   temp.renameTo(f_new);
           }  
           
        }  
	}

}
