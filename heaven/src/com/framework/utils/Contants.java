package com.framework.utils;

public class Contants {
	/**
	 * @Description:(3.工具类主类)设置转码工具的各个路径
	 * @param:@param args   
	 * @return:void  
	 * @author:Zoutao
	 * @date:2018-6-22
	 * @version:V1.0
	 */
	
	public static final String ffmpegpath = ConfigUtil.getFfmpegPath();// ffmpeg工具安装位置
	public static final String mencoderpath = ConfigUtil.getMencoderPath();// mencoder工具安装的位置
	
	public static final String videofolder = ConfigUtil.getVideosPath(); 	// 需要被转换格式的视频目录
	public static final String videoRealPath = ConfigUtil.getVideosPath(); 	// 需要被截图的视频目录
	
	public static final String targetfolder = ConfigUtil.getVideosPath(); // 转码后视频保存的目录
	public static final String imageRealPath = ConfigUtil.getVideoImagesPath(); // 截图的存放目录
}
