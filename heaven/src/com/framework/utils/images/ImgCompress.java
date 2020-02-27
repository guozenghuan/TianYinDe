package com.framework.utils.images;

import java.io.*;  
import java.awt.*;  
import java.awt.image.*;  
import javax.imageio.ImageIO;

import com.framework.utils.linux.ShellByLinux;
import com.sun.image.codec.jpeg.*;  

/** 
 * 图片压缩处理 
 * @author
 */  
public class ImgCompress {  
    private Image img;  
    private int width;  
    private int height;  
    public static void main(String[] args) throws Exception {  
    	String path = "D:\\software_main\\Java\\apache-tomcat-7.0.90\\webapps\\files_heaven\\salesman\\16_file\\portrait\\1_2017-06-03 - 副本.jpg";
        ImgCompress imgCom = new ImgCompress(path);  
        imgCom.resizeFix(640, 960,path);
    }
    
    /**
     * 图片压缩
     * @param width 	全屏：640*960；头像：400*400；轮播图：640*320
     * @param height	
     * @param path
     * @return
     */
    public static boolean compressImag(int width, int height,String path){
    	ImgCompress imgCom;
		try {
			imgCom = new ImgCompress(path);
			//手机分辨率
			imgCom.resizeFix(width, height,path);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}  
    }
    
    /** 
     * 构造函数 
     */  
    public ImgCompress(String fileName) throws IOException {  
        File file = new File(fileName);// 读入文件  
        img = ImageIO.read(file);      // 构造Image对象  
        width = img.getWidth(null);    // 得到源图宽  
        height = img.getHeight(null);  // 得到源图长  
    }  
    /** 
     * 按照宽度还是高度进行压缩 
     * @param w int 最大宽度 
     * @param h int 最大高度 
     */
    public void resizeFix(int w, int h,String path) throws IOException {  
        if (width / height > w / h) {
            resizeByWidth(w,path);
        } else {  
            resizeByHeight(h,path);
        }  
    }  
    /** 
     * 以宽度为基准，等比例放缩图片 
     * @param w int 新宽度 
     */  
    public void resizeByWidth(int w,String path) throws IOException {  
        int h = (int) (height * w / width);  
        resize(w, h,path);  
    }  
    /** 
     * 以高度为基准，等比例缩放图片 
     * @param h int 新高度 
     */  
    public void resizeByHeight(int h,String path) throws IOException {  
        int w = (int) (width * h / height);  
        resize(w, h,path);  
    }  
    /** 
     * 强制压缩/放大图片到固定的大小    r 
     * @param w int 新宽度 
     * @param h int 新高度 
     * 
     * linux系统压缩方法
     * 安装命令：sudo apt-get install imagemagick
     * 使用命令：
     * （1）convert -resize 1024x768 xxx.jpg   xxx1.jpg    将图像的像素改为1024*768，注意1024与768之间是小写字母x
	 * （2）convert -sample 50%x50%  xxx.jpg  xxx1.jpg   将图像的缩减为原来的50%*50%
     * （3）旋转图像：  convert -rotate 270 sky.jpg sky-final.jpg      将图像顺时针旋转270度
	 * （4）使用-draw选项还可以在图像里面添加文字：  convert -fill black -pointsize 60 -font helvetica -draw 'text 10,80 "Hello, World!" ‘  hello.jpg  helloworld.jpg
     */  
    public void resize(int w, int h,String path) throws IOException {
    	//判断当前操作系统
    	String os = System.getProperty("os.name");  
    	if(os.toLowerCase().startsWith("win")){//windos系统  
    		// SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢  
            BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );
            image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
            File destFile = new File(path);
            FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
            // 可以正常实现bmp、png、gif转jpg
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image); // JPEG编码
            out.close();
    	} else {
    		ShellByLinux.shell("convert -resize "+w+"x"+w+" "+path+" "+path);
    	}
    	
    	
    }  
}  
