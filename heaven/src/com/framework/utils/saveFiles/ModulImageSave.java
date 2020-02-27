package com.framework.utils.saveFiles;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.framework.utils.ConfigUtil;
import com.framework.utils.StringUtil;
import com.framework.utils.images.ImgCompress;

public class ModulImageSave {

	/**
	 * 保存模板图片
	 * @param multipartFile
	 * @param modulId
	 * @return 图片访问路径
	 */
	public static String saveMoulImage(MultipartFile multipartFile,Long modulId,String modulCode){
		//图片路径和地址
		String[] names = multipartFile.getOriginalFilename().split("\\.");
		String mainImagePath = ConfigUtil.getSystemModul(modulId,modulCode) + StringUtil.randomUUID() + "." + names[names.length-1];
		// 上传
		try {
			multipartFile.transferTo(new File(ConfigUtil.getTomcatWebapps()+mainImagePath));
			
			//压缩图片
			ImgCompress.compressImag(640, 960, ConfigUtil.getTomcatWebapps()+mainImagePath);
			
			return mainImagePath;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
