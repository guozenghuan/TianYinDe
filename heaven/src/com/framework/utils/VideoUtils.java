package com.framework.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.framework.utils.StringUtil;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.InputFormatException;
import it.sauronsoftware.jave.MultimediaInfo;

public class VideoUtils {

	/**
	 * 判断视频文件是否是mp4中的H264，是则不用转，不是则转换
	 * 
	 * @param videoPath
	 * @return
	 */
	public static Boolean isVideoH264(String videoPath) {
		Encoder encoder = new Encoder();
		File target1 = new File(videoPath);
		try {
			MultimediaInfo info1 = encoder.getInfo(target1);
			if (StringUtil.isEmpty(info1)) {
				return false;
			}
			String fileType = net.sf.json.JSONObject.fromObject(net.sf.json.JSONObject.fromObject(info1).get("video")).get("decoder").toString();
			if (fileType.trim().equals("h264")) {
				return true;
			} else {
				return false;
			}
		} catch (InputFormatException e) {
			e.printStackTrace();
		} catch (EncoderException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 视频转码
	 * @param inputFile
	 * @param outputFile
	 * @return
	 * 
	 * Linux安装方法：
	 * 
	 * 下载yasm
	 * wget http://www.tortall.net/projects/yasm/releases/yasm-1.3.0.tar.gz
	 * 进入yasm目录执行：./configure
	 * 执行安装：make && make install
	 * 
	 * 下载ffmpeg
	 * 进入ffmpeg目录执行：./configure
	 * 执行安装：make && make install
	 * wget http://ffmpeg.org/releases/ffmpeg-3.3.4.tar.bz2
	 * 
	 * 安装x264库
	 * 进入x264目录执行：./configure --enable-shared --disable-asm
	 * 执行安装：make && make install
	 * 
	 * 重新编译ffmpeg
	 * ./configure --disable-yasm --enable-libx264 --enable-swscale --enable-avresample --enable-gpl --enable-shared
	 * 重新执行安装：make && make install
	 * 
	 * 提示安装失败则安装make和gcc
	 * sudo apt-get install make
	 * sudo apt-get install gcc
	 * 
	 * 然后还是不行，我也不懂了
	 */
	public static boolean changeVCodec(String inputFile, String outputFile) {
        List<String> command= new ArrayList<String>();
        command.add("/home/ffmpeg-3.3.4/ffmpeg");//ffmpeg工具的本地路径
        command.add("-i");
        command.add(inputFile);// 要转的文件路径
        command.add("-vcodec");
        command.add("h264"); // 设置自己想要的视频编码信息
        command.add("-acodec"); 
        command.add("copy");// 因为对音频没改变需求,所以直接copy
        command.add("-f");
        command.add("mov");
        command.add("-y");
        command.add(outputFile); // 转码之后的文件路径
        StringBuffer test = new StringBuffer();
        for (int i = 0; i < command.size(); i++) {
            test.append(command.get(i) + " ");
        }

        System.out.println("==================转码结果===================");
        System.out.println(test);

        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(command);
            builder.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

	
}
