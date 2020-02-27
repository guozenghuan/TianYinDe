package com.framework.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;

public class StringUtil {
	
	/**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
           Pattern pattern = Pattern.compile("[0-9]*");
           Matcher isNum = pattern.matcher(str);
           if( !isNum.matches() ){
               return false;
           }
           return true;
    }

	/**
	 * 字符串日期加1天，返还格式：yyyy-MM-dd
	 * @param today
	 * @return
	 */
	public static String addOneDayTime(String today){
		SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d = sj.parse(today);
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(d);
	        calendar.add(Calendar.DATE, 1);
	        return sj.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 字符串日期减1天，返还格式：yyyy-MM-dd
	 * @param today
	 * @return
	 */
	public static String delOneDayTime(String today){
		SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d = sj.parse(today);
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(d);
	        calendar.add(calendar.DATE, -1);
	        return sj.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 判断图片是否可以保存
	 * @param image
	 * @return
	 */
	public static Boolean isMultiFlag(MultipartFile image){
		if(image==null){
			return false;
		}
		String mainImageType = image.getContentType();
		if(!mainImageType.equals("image/jpeg") && !mainImageType.equals("image/jpg") && !mainImageType.equals("image/png")){
			return false;
		}
		return true;
	}
	
	/**
	 * date类型日期加1天，返还格式：yyyy-MM-dd
	 * @param today
	 * @return
	 */
	public static String addOneDayTimeByDate(Date today){
		SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.DATE, 1);
		return sj.format(calendar.getTime());
	}
	/**
	 * date类型日期减1天，返还格式：yyyy-MM-dd
	 * @param today
	 * @return
	 */
	public static String delOneDayTimeByDate(Date today){
		SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(calendar.DATE, -1);
		return sj.format(calendar.getTime());
	}
	
	/**
	 * 功能：产生min-max中的n个不重复的随机数
	 * 
	 * min:产生随机数的其实位置 mab：产生随机数的最大位置 n: 所要产生多少个随机数
	 *
	 */
	public static int[] randomNumber(int min, int max, int n) {

		// 判断是否已经达到索要输出随机数的个数
		if (n > (max - min + 1) || max < min) {
			return null;
		}

		int[] result = new int[n]; // 用于存放结果的数组

		int count = 0;
		while (count < n) {
			int num = (int) (Math.random() * (max - min)) + min;
			boolean flag = true;
			for (int j = 0; j < count; j++) {
				if (num == result[j]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				result[count] = num;
				count++;
			}
		}
		return result;
	}

	/**
	 * 创建UUID
	 * 
	 * @return uuid
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 判断是否为null或空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(Object str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}
	

	/**
	 * 判断是否为手机号
	 * 
	 * @param mobile
	 * @return "true" 为正确,否则返回错误信息error
	 */
	public static String isMobile(String mobile) {
		String error = "手机号码格式错误!";
		String regex = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
		if (mobile != null && mobile.matches(regex)) {
			return "true";
		}
		return error;
	}

	/**
	 * 判断是账号格式,只能由大小写英文和字母组合,长度为3-12位
	 * 
	 * @param account
	 * @return "true" 为正确,否则返回错误信息error
	 */
	public static String isAccount(String account) {
		String error = "账号格式错误！长度为3-12位,并且只能由英文字符和阿拉伯数字组合!";
		if (account == null || account.trim().equals("") || account.trim().equals("null")
				|| account.trim().equals("undefined") || !isLetterOrDigital(account,3,12)) {
			return error;
		} else {
			return "true";
		}
	}

	/**
	 * 判断是密码格式,只能由大小写英文和字母组合,长度6-18位
	 * 
	 * @param password
	 * @return "true" 为正确,否则返回错误信息error
	 */
	public static String isPassword(String password) {
		String error = "密码格式错误！长度为6-18位,并且只能由英文字符和阿拉伯数字组合!";
		if (password == null || password.trim().equals("") || password.trim().equals("null")
				|| password.trim().equals("undefined") || !isLetterOrDigital(password,6,18)) {
			return error;
		} else {
			return "true";
		}
	}
	
	/**
	 * 判断是昵称格式,只能由中文和大小写英文和字母组合,长度1-12位
	 * 
	 * @param nickName
	 * @return "true" 为正确,否则返回错误信息error
	 */
	public static String isNickName(String nickName) {
		String error = "昵称格式错误！长度为1-12位,并且只能由中文和英文字符和阿拉伯数字组合!";
		if (nickName == null || nickName.trim().equals("") || nickName.trim().equals("null")
				|| nickName.trim().equals("undefined") || !isChineseOrLetterOrDigital(nickName,1,12)) {
			return error;
		} else {
			return "true";
		}
	}

	/**
	 * 字符串只能由大小写英文和字母组合
	 * @param min 最小长度
	 * @param max 最大长度
	 */
	public static boolean isLetterOrDigital(String str,int min,int max) {
		String regex = "^[a-z0-9A-Z]{"+min+","+max+"}$";
		return str.matches(regex);
	}
	
	/**
	 * 字符串只能由中文和大小写英文和字母组合
	 * @param min 最小长度
	 * @param max 最大长度
	 */
	public static boolean isChineseOrLetterOrDigital(String str,int min,int max) {
		String regex = "^[a-z0-9A-Z\u4E00-\u9FA5]{"+min+","+max+"}$";
		return str.matches(regex);
	}
	
	
    /**
     * @Description: 将base64编码字符串转换为图片
     * @Author: 
     * @CreateTime: 
     * @param imgStr base64编码字符串
     * @param path 图片路径-具体到文件
     * @return
    */
    public static Boolean generateImage(String imgStr,String imgP, String path) {
    	if (imgStr == null) {
    		return false;
    	}
    	BASE64Decoder decoder = new BASE64Decoder();
    	// 解密
		byte[] b;
		try {
			b = decoder.decodeBuffer(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			
			File file = new File(imgP);
			//判断文件路径的文件夹是否存在不存在则创建
			if (!file.exists()) {
				file.mkdirs();
			}
			
			OutputStream out = new FileOutputStream(imgP+path);
			out.write(b);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
    	
    }
    
    //删除文件
    public static void delFile(String path,String filename){
        try {
        	File file=new File(path+"/"+filename);
            if(file.exists()&&file.isFile()) {
                file.delete();
            }
		} catch (Exception e) {
			
		}
    }
    
    //删除文件夹  
    public static void delFolder(String folderPath) {
         try {  
            delAllFile(folderPath); //删除完里面所有内容  
            String filePath = folderPath;  
            filePath = filePath.toString();  
            java.io.File myFilePath = new java.io.File(filePath);  
            myFilePath.delete(); //删除空文件夹  
         } catch (Exception e) {  
            e.printStackTrace();   
         }  
    }  
    
    //删除指定文件夹下的所有文件
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
	 * 判断字符串是否为纯数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断double是否是整数
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isIntegerForDouble(double obj) {
		double eps = 1e-10; // 精度范围
		return obj - Math.floor(obj) < eps;
	}

	/**
	 * 判断字符串是否包含某个特定的字符
	 * 
	 * @程序员: ruigeone
	 * @return false=不包含，true=包含
	 **/
	public static boolean isIndexOfStr(String str, String par) {
		if (isEmpty(str) || str.indexOf(par) == -1) {
			//不包含
			return false;
		} else {
			//包含
			return true;
		}
	}

	/**
	 * 随机生成六位数字
	 * 
	 * @return
	 */
	public static String getRandomCode() {
		Random random = new Random();
		int num = random.nextInt(1000000);
		if (num < 100000) {
			num += 100000;
		}
		return num + "";
	}

	/**
	 * 获取方括号里的字符数组
	 * 
	 * @param str
	 * @return
	 */
	public static String[] getBoxArray(String str) {
		if (str != null) {
			return str.replaceAll("\\[", "").replaceAll("]", "").split(",");
		}
		return null;
	}

	/**
	 * Oracle 获取当前页的开始数和结束数
	 * 
	 * @param pagecode
	 *            当前页
	 * @param limit
	 *            每页显示条数
	 * @return
	 */
	public static Map<String, Object> getStartAndEnd(int pagecode, int limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("start", limit * (pagecode - 1) + 1);
		map.put("end", limit * pagecode);
		return map;
	}

	/**
	 * MySql 获取当前页的开始数和显示条数
	 * 
	 * @param pagecode
	 *            页码
	 * @param limit
	 *            每页显示条数
	 * @return
	 */
	public static Map<String, Object> getStartAndLimit(int pagecode, int limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("start", limit * (pagecode - 1));
		map.put("limit", limit);
		return map;
	}

	/**
	 * 获取总页数
	 * 
	 * @param totalNum
	 *            总条数
	 * @param limit
	 *            每页显示条数
	 * @return
	 */
	public static int getTotalpage(long totalNum, int limit) {
		if (totalNum != 0 && totalNum % limit == 0) {
			return (int) (totalNum / limit);
		}
		return (int) (totalNum / limit + 1);
	}

	/**
	 * 随机生成6数字加当前时间
	 * 
	 * @return位
	 */
	public static String getRandomFileName() {

		SimpleDateFormat simpleDateFormat;

		simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

		Date date = new Date();

		String str = simpleDateFormat.format(date);

		Random random = new Random();

		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 1000;// 获取4位随机数
		int rannum2 = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10;// 获取2位随机数
		return str + rannum + rannum2;// 当前时间

	}

	/**
	 *
	 * @param s
	 *            要操作的字符串
	 * @param string
	 *            要删除的字符
	 * @param i
	 *            删除第几个
	 * @return
	 */
	public static String remove(String s, String string, int i) {
		if (i == 1) {
			int j = s.indexOf(string);
			s = s.substring(0, j) + s.substring(j + 1);
			i--;
			return s;
		} else {
			int j = s.indexOf(string);
			i--;
			return s.substring(0, j + 1) + remove(s.substring(j + 1), string, i);
		}

	}

	/***
	 * MD5加码 生成32位md5码
	 */
	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}

	/**
	 * 加密解密算法 执行一次加密，两次解密
	 */
	public static String convertMD5(String inStr) {

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;

	}

	// 测试主函数
	// public static void main(String args[]) {
	//// String s = new String("183760452130123544578132149872165465");
	//// System.out.println("原始：" + s);
	////// System.out.println("MD5后：" + string2MD5(s));
	//// System.out.println("加密的：" + convertMD5(s));
	//// System.out.println("解密的：" +
	// convertMD5("ELGCBD@AFEGDEFGA@@ACLEGFE@MLCFEBA@BA"));
	//// System.out.println(StringUtil.convertMD5("ELGCBD@AFEGDEFGA@@ACLEGFE@MLCFEBA@BA").replaceAll(StaticFinalParames.MONEY_END_ADDRESS.trim(),
	// ""));
	//
	// float f = levenshtein("世界五百强聘五险一金双休","世界五百强聘五险一金双休");
	// }

	// 字符相似度判断
	public static float levenshtein(String str1, String str2) {
		// 计算两个字符串的长度。
		str1 = str1.replaceAll(" ", "");
		str2 = str2.replaceAll(" ", "");
		int len1 = str1.length();
		int len2 = str2.length();
		// 建立上面说的数组，比字符长度大一个空间
		int[][] dif = new int[len1 + 1][len2 + 1];
		// 赋初值，步骤B。
		for (int a = 0; a <= len1; a++) {
			dif[a][0] = a;
		}
		for (int a = 0; a <= len2; a++) {
			dif[0][a] = a;
		}
		// 计算两个字符是否一样，计算左上的值
		int temp;
		for (int i = 1; i <= len1; i++) {
			for (int j = 1; j <= len2; j++) {
				if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
					temp = 0;
				} else {
					temp = 1;
				}
				// 取三个值中最小的
				dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1, dif[i - 1][j] + 1);
			}
		}
		// 取数组右下角的值，同样不同位置代表不同字符串的比较
		System.out.println("差异步骤：" + dif[len1][len2]);
		// 计算相似度
		float similarity = 1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());
		System.out.println("相似度：" + similarity);
		return similarity;
	}

	// 得到最小值
	private static int min(int... is) {
		int min = Integer.MAX_VALUE;
		for (int i : is) {
			if (min > i) {
				min = i;
			}
		}
		return min;
	}
	
	/***
	 * 转换为UTF8，以防乱码
	 */
	public static String toUTF8(HttpServletRequest request,String parame) {
        try {
        	request.setCharacterEncoding("UTF-8");
			return new String(request.getParameter(parame).getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}catch(Exception exception){
			return "";
		}
    }
	
	public static String toUTF8One(HttpServletRequest request,String parame) {
        try {
        	request.setCharacterEncoding("UTF-8");
			return new String(request.getParameter(parame)).trim();
		} catch (UnsupportedEncodingException e) {
			return null;
		}catch(Exception exception){
			return null;
		}
    }
	
	/**
     * @param s
     * @return 获得html代码中的图片src
     */
    public static List<String> getHtmlImgUrl(String s)
    {
    	if(StringUtil.isEmpty(s)){
    		return null;
    	}
        String regex;
        List<String> list = new ArrayList<String>();
        
        //"-双引号
        regex = "src=\"(.*?)\"";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(s);
        while (ma.find())
        {	
        	//删除src="和"只要中间的路径
        	String srcPhth = ma.group();
        	if(isIndexOfStr(srcPhth, "src=\"")){
        		srcPhth = srcPhth.replace("src=\"", "");
        	}
        	//判断最后一位字符是不是"
        	if (srcPhth.endsWith("\"")) {
        		srcPhth = srcPhth.substring(0,srcPhth.length() - 1);
            }
        	
            list.add(srcPhth);
        }
        
        //'-单个引号
        regex = "src='(.*?)'";
        Pattern pa2 = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma2 = pa2.matcher(s);
        while (ma2.find())
        {	
        	//删除src="和"只要中间的路径
        	String srcPhth = ma2.group();
        	if(isIndexOfStr(srcPhth, "src='")){
        		srcPhth = srcPhth.replace("src='", "");
        	}
        	//判断最后一位字符是不是"
        	if (srcPhth.endsWith("'")) {
        		srcPhth = srcPhth.substring(0,srcPhth.length() - 1);
            }
        	
            list.add(srcPhth);
        }
        
        return list;
    }
    
    /**
     * 去除两个list相同的值，只留下不一样的值
     * @param list1
     * @param list2
     * @return
     */
    public static List<String> delListSame(List<String> list1,List<String> list2){
    	for(String add : list2){
    		list1.add(add);
    	}
		
		List<String> dele = new ArrayList<>();
		for (int i = 0; i < list1.size() - 1; i++) {
            for (int j = list1.size() - 1; j > i; j--) {
                if (list1.get(j).equals(list1.get(i))) {
                	list1.remove(j);
                	dele.add(list1.get(i));
                }
            }
		 }
		
		for(int j = 0;j<list1.size();j++){
			for(String del : dele){
				if(list1.get(j).equals(del)){
					list1.remove(j);
				}
			}
		}
		
		return list1;
    }
	
	public static void main(String[] args) {
		System.out.println(isMobile("19176045213"));
	}
}
