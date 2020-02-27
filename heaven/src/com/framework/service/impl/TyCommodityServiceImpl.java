package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.framework.dao.TyCommodityDao;
import com.framework.entity.TyCommodityEntity;
import com.framework.service.TyCommodityService;
import com.framework.utils.ConfigUtil;
import com.framework.utils.R;
import com.framework.utils.StringUtil;
import com.framework.utils.TransactionRollBack;
import com.framework.utils.images.ImgCompress;



@Service("tyCommodityService")
public class TyCommodityServiceImpl implements TyCommodityService {
	@Autowired
	private TyCommodityDao tyCommodityDao;
	
	@Override
	public TyCommodityEntity queryObject(Long id){
		TyCommodityEntity tyCommodityEntity = tyCommodityDao.queryObject(id);
		tyCommodityEntity.setCreatetime(null);
		return tyCommodityEntity;
	}
	
	@Override
	public List<TyCommodityEntity> queryList(Map<String, Object> map){
		return tyCommodityDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tyCommodityDao.queryTotal(map);
	}
	
	@Transactional
	@Override
	public R save(HttpServletRequest request){
		String s=request.getParameter("tyCommodity");
		//转换成实体类
		TyCommodityEntity tyCommodity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("tyCommodity")), TyCommodityEntity.class);
		
		//取出form-data中的二进制字段
		MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
		
		/**保存主图**/
		//判断主图是否上传
		MultipartFile mainImage = multipartRequest.getFile("mainImage");
		if(mainImage==null){
			return R.error("请上传主图!");
		}
		//判断图片类型
		String mainImageType = mainImage.getContentType();
		if(!mainImageType.equals("image/jpeg") && !mainImageType.equals("image/jpg") && !mainImageType.equals("image/png")){
			return R.error("主图图片格式错误!只能上传jpeg、jpg、png类型的图片!");
		}
		
		//保存信息获取id
		tyCommodity.setCreatetime(new Date());
		tyCommodityDao.save(tyCommodity);
		
		//图片路径和地址
		String[] names = mainImage.getOriginalFilename().split("\\.");
		String mainImagePath = ConfigUtil.getSystemCommodity(tyCommodity.getId().toString()) + StringUtil.randomUUID() + "." + names[names.length-1];
		
		// 上传
		try {
			mainImage.transferTo(new File(ConfigUtil.getTomcatWebapps()+mainImagePath));
			
			//压缩图片
			ImgCompress.compressImag(640, 320, ConfigUtil.getTomcatWebapps()+mainImagePath);
			
			//设置主图路径
			tyCommodity.setMainImage(mainImagePath);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate(mainImagePath);
			return R.error("保存图片失败!");
		} catch (IOException e) {
			e.printStackTrace();
			//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate(mainImagePath);
			return R.error("保存图片失败!");
		}
		
		/**保存轮播图**/
		//判断轮播图是否上传
		MultiValueMap<String,MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();
        List<MultipartFile> fileSet = new LinkedList<>();
        for(Entry<String, List<MultipartFile>> temp : multiFileMap.entrySet()){
        	String key = temp.getKey().trim();
        	if(key.indexOf("secondImage") != -1){
        		fileSet.addAll(temp.getValue());
        	}
        }
        
        if(fileSet.isEmpty() || fileSet.size() < 1){
        	//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate(mainImagePath);
        	return R.error("轮播图至少要上传一张!");
        }
        
        String secondImages = "";
        for(MultipartFile temp : fileSet){
        	//判断图片类型
        	String textImageType = temp.getContentType();
    		if(!textImageType.equals("image/jpeg") && !textImageType.equals("image/jpg") && !textImageType.equals("image/png")){
    			//操作失败执行数据回滚
    			TransactionRollBack.rollBackUpdate(mainImagePath);
    			return R.error("主图图片格式错误!只能上传jpeg、jpg、png类型的图片!");
    		}
    		
    		//保存轮播图
    		//图片路径和地址
    		String[] namesSecond = temp.getOriginalFilename().split("\\.");
    		String scondImagePath = ConfigUtil.getSystemCommodity(tyCommodity.getId().toString()) + StringUtil.randomUUID() + "." + namesSecond[namesSecond.length-1];
    		
    		// 上传
    		try {
    			temp.transferTo(new File(ConfigUtil.getTomcatWebapps()+scondImagePath));
    			
    			//压缩图片
    			ImgCompress.compressImag(640, 320, ConfigUtil.getTomcatWebapps()+scondImagePath);
    			
    			//设置轮播图
    			secondImages = secondImages + scondImagePath + ",";
    		} catch (IllegalStateException e) {
    			e.printStackTrace();
    			//操作失败执行数据回滚
    			TransactionRollBack.rollBackUpdate(mainImagePath);
    			return R.error("保存图片失败!");
    		} catch (IOException e) {
    			e.printStackTrace();
    			//操作失败执行数据回滚
    			TransactionRollBack.rollBackUpdate(mainImagePath);
    			return R.error("保存图片失败!");
    		}
        }
        //设置轮播图路径
		tyCommodity.setSecondImages(secondImages);
		
		/**保存内容图片**/
		//遍历html中的base64转存图片替换路径
		List<String> urlTextList = StringUtil.getHtmlImgUrl(tyCommodity.getText());
		if(urlTextList != null && !urlTextList.isEmpty() && urlTextList.size() >= 1){
			for(String urlName : urlTextList){
				//判断主图是否上传
				MultipartFile textImage = multipartRequest.getFile(urlName);
				if(textImage!=null){
					//判断图片类型
					String textImageType = textImage.getContentType();
					if(!textImageType.equals("image/jpeg") && !textImageType.equals("image/jpg") && !textImageType.equals("image/png")){
						return R.error("主图图片格式错误!只能上传jpeg、jpg、png类型的图片!");
					}
					
					//保存图片
					//图片路径和地址
	        		String[] namesText = textImage.getOriginalFilename().split("\\.");
	        		String textImagePath = ConfigUtil.getSystemCommodity(tyCommodity.getId().toString()) + StringUtil.randomUUID() + "." + namesText[namesText.length-1];
	        		
	        		// 上传
	        		try {
	        			textImage.transferTo(new File(ConfigUtil.getTomcatWebapps()+textImagePath));
	        			
	        			//压缩图片
	        			ImgCompress.compressImag(640, 320, ConfigUtil.getTomcatWebapps()+textImagePath);
	        			
	        			//设置轮播图路径
	        			tyCommodity.setText(tyCommodity.getText().replace(urlName, textImagePath));
	        		} catch (IllegalStateException e) {
	        			e.printStackTrace();
	        			//操作失败执行数据回滚
	        			TransactionRollBack.rollBackUpdate(mainImagePath);
	        			return R.error("保存图片失败!");
	        		} catch (IOException e) {
	        			e.printStackTrace();
	        			//操作失败执行数据回滚
	        			TransactionRollBack.rollBackUpdate(mainImagePath);
	        			return R.error("保存图片失败!");
	        		}
				}
			}
		}
		
		int updateFlag = tyCommodityDao.update(tyCommodity);
		if(updateFlag!=1){
			//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate(mainImagePath);
			return R.error("保存图片路径失败!!");
		}
		
		return R.ok("操作成功!");
	}
	
	@Transactional
	@Override
	public R update(HttpServletRequest request){
		//转换成实体类
		String s=request.getParameter("tyCommodity");
		TyCommodityEntity tyCommodity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("tyCommodity")), TyCommodityEntity.class);
		
		//判断信息是否存在
		TyCommodityEntity tyCommodityEntityFlag = new TyCommodityEntity();
		tyCommodityEntityFlag.setId(tyCommodity.getId());
		List<TyCommodityEntity> tyCommodityEntitiesList = tyCommodityDao.queryObjectByKey(tyCommodityEntityFlag);
		if(tyCommodityEntitiesList.isEmpty() || tyCommodityEntitiesList.size() <= 0){
			return R.error("操作的商品信息不存在,可能已被其他管理员删除,请刷新当前页面后重新操作!");
		}
		
		//取出form-data中的二进制字段
		MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
		
		/**存储文件路径，以防报错执行删除已经保存的文件**/
		List<String> delFilePathList = new ArrayList<>(); 
		
		/**需要删除的图片集合**/
		List<String> delFileList = new ArrayList<>();
		
		/**保存主图**/
		//判断主图是否上传
		MultipartFile mainImage = multipartRequest.getFile("mainImage");
		if(mainImage!=null){
			//存储需要删除的主图
			delFileList.add(tyCommodityEntitiesList.get(0).getMainImage());
			
			//判断图片类型
			String mainImageType = mainImage.getContentType();
			if(!mainImageType.equals("image/jpeg") && !mainImageType.equals("image/jpg") && !mainImageType.equals("image/png")){
				return R.error("主图图片格式错误!只能上传jpeg、jpg、png类型的图片!");
			}
			
			//图片路径和地址
			String[] names = mainImage.getOriginalFilename().split("\\.");
			String mainImagePath = ConfigUtil.getSystemCommodity(tyCommodity.getId().toString()) + StringUtil.randomUUID() + "." + names[names.length-1];
			
			// 上传
			try {
				mainImage.transferTo(new File(ConfigUtil.getTomcatWebapps()+mainImagePath));
				delFilePathList.add(mainImagePath);//删除集合
				
				//压缩图片
				ImgCompress.compressImag(640, 320, ConfigUtil.getTomcatWebapps()+mainImagePath);
				
				//设置主图路径
				tyCommodity.setMainImage(mainImagePath);
			} catch (IllegalStateException e) {
				e.printStackTrace();
				//删除文件集合
				ConfigUtil.deleteFile(delFilePathList);
				return R.error("保存图片失败!");
			} catch (IOException e) {
				e.printStackTrace();
				//删除文件集合
				ConfigUtil.deleteFile(delFilePathList);
				return R.error("保存图片失败!");
			}
		}
		
		/**保存轮播图**/
		//判断轮播图是否上传
		MultiValueMap<String,MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();
        List<MultipartFile> fileSet = new LinkedList<>();
        for(Entry<String, List<MultipartFile>> temp : multiFileMap.entrySet()){
        	String key = temp.getKey().trim();
        	if(key.indexOf("secondImage") != -1){
        		fileSet.addAll(temp.getValue());
        	}
        }
        
        
        //判断已经删除的图片过滤掉原有的图片是否还满足最低一张轮播图的条件
//    	List<String> sedRvePathList = (List<String>)JSONObject.toJavaObject(JSON.parseObject(request.getParameter("sedRvePathList")), List.class);
    	//轮播图路径
    	String historySed = tyCommodityEntitiesList.get(0).getSecondImages(); 
    	String sedRveStr = request.getParameter("sedRvePathList");
    	if(!StringUtil.isEmpty(sedRveStr) && StringUtil.isIndexOfStr(sedRveStr, ",")){
    		String[] sedRvePathList = sedRveStr.split(",");
        	if(sedRvePathList != null && sedRvePathList.length >= 1){
        		//去除删除的轮播图路径
            	for(String pathSed : sedRvePathList){
            		String delPathSed = pathSed;
            		pathSed = pathSed.trim()+",";
            		if(StringUtil.isIndexOfStr(historySed, pathSed)){
            			historySed = historySed.replace(pathSed, "");
            			//存储需要删除的轮播图
            			delFileList.add(delPathSed);
            		}
            	}
        	}
    	}
    	
        if(fileSet.isEmpty() || fileSet.size() < 1){
        	if(!StringUtil.isIndexOfStr(historySed, ",")){
        		//删除文件集合
    			ConfigUtil.deleteFile(delFilePathList);
            	return R.error("轮播图至少要上传一张!");
        	}
        }
        
        //添加上原始的轮播图
        String secondImages = historySed;
        for(MultipartFile temp : fileSet){
        	//判断图片类型
        	String textImageType = temp.getContentType();
    		if(!textImageType.equals("image/jpeg") && !textImageType.equals("image/jpg") && !textImageType.equals("image/png")){
    			//删除文件集合
    			ConfigUtil.deleteFile(delFilePathList);
    			return R.error("主图图片格式错误!只能上传jpeg、jpg、png类型的图片!");
    		}
    		
    		//保存轮播图
    		//图片路径和地址
    		String[] namesSecond = temp.getOriginalFilename().split("\\.");
    		String scondImagePath = ConfigUtil.getSystemCommodity(tyCommodity.getId().toString()) + StringUtil.randomUUID() + "." + namesSecond[namesSecond.length-1];
    		
    		// 上传
    		try {
    			temp.transferTo(new File(ConfigUtil.getTomcatWebapps()+scondImagePath));
    			delFilePathList.add(scondImagePath);//删除集合
    			
    			//压缩图片
    			ImgCompress.compressImag(640, 320, ConfigUtil.getTomcatWebapps()+scondImagePath);
    			
    			//设置轮播图
    			secondImages = secondImages + scondImagePath + ",";
    		} catch (IllegalStateException e) {
    			e.printStackTrace();
    			//删除文件集合
    			ConfigUtil.deleteFile(delFilePathList);
    			return R.error("保存图片失败!");
    		} catch (IOException e) {
    			e.printStackTrace();
    			//删除文件集合
    			ConfigUtil.deleteFile(delFilePathList);
    			return R.error("保存图片失败!");
    		}
        }
        //设置轮播图路径
		tyCommodity.setSecondImages(secondImages);
		
		/**保存内容图片**/
		//遍历html中的base64转存图片替换路径
		List<String> urlTextList = StringUtil.getHtmlImgUrl(tyCommodity.getText());
		for(String urlName : urlTextList){
			//判断主图是否上传
			MultipartFile textImage = multipartRequest.getFile(urlName);
			if(textImage!=null){
				//判断图片类型
				String textImageType = textImage.getContentType();
				if(!textImageType.equals("image/jpeg") && !textImageType.equals("image/jpg") && !textImageType.equals("image/png")){
					//删除文件集合
					ConfigUtil.deleteFile(delFilePathList);
					return R.error("主图图片格式错误!只能上传jpeg、jpg、png类型的图片!");
				}
				
				//保存图片
				//图片路径和地址
        		String[] namesText = textImage.getOriginalFilename().split("\\.");
        		String textImagePath = ConfigUtil.getSystemCommodity(tyCommodity.getId().toString()) + StringUtil.randomUUID() + "." + namesText[namesText.length-1];
        		
        		// 上传
        		try {
        			textImage.transferTo(new File(ConfigUtil.getTomcatWebapps()+textImagePath));
        			delFilePathList.add(textImagePath);//删除集合
        			
        			//压缩图片
        			ImgCompress.compressImag(640, 320, ConfigUtil.getTomcatWebapps()+textImagePath);
        			
        			//设置轮播图路径
        			tyCommodity.setText(tyCommodity.getText().replace(urlName, textImagePath));
        		} catch (IllegalStateException e) {
        			e.printStackTrace();
        			//删除文件集合
        			ConfigUtil.deleteFile(delFilePathList);
        			return R.error("保存图片失败!");
        		} catch (IOException e) {
        			e.printStackTrace();
        			//删除文件集合
        			ConfigUtil.deleteFile(delFilePathList);
        			return R.error("保存图片失败!");
        		}
			}
		}
		
		int updateFlag = tyCommodityDao.update(tyCommodity);
		if(updateFlag!=1){
			//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate();
			//删除文件集合
			ConfigUtil.deleteFile(delFilePathList);
			return R.error("保存图片路径失败!!");
		}
		
		try {
			/**删除图片**/
			/*删除主图、轮播图图片*/
			for(String delPath : delFileList){
				ConfigUtil.deleteFile(delPath);
			}
			/*删除内容中的图片*/
			List<String> urlTextHitory = StringUtil.getHtmlImgUrl(tyCommodityEntitiesList.get(0).getText());
			for(String url : urlTextHitory){
				if(!StringUtil.isIndexOfStr(tyCommodity.getText(), url)){
					ConfigUtil.deleteFile(url);
				}
			}
		} catch (Exception e) {
			//图片删除失败了不需要回滚
		}
		
		return R.ok("操作成功!");
	}
	
	@Override
	public void delete(Long id){
		tyCommodityDao.delete(id);
	}
	
	@Override
	public R deleteBatch(Long[] ids){
		//判断信息是否存在
		TyCommodityEntity tyCommodityEntityFlag = new TyCommodityEntity();
		tyCommodityEntityFlag.setId(ids[0]);
		List<TyCommodityEntity> tyCommodityEntitiesList = tyCommodityDao.queryObjectByKey(tyCommodityEntityFlag);
		if(tyCommodityEntitiesList.isEmpty() || tyCommodityEntitiesList.size() <= 0){
			return R.error("操作的商品信息不存在,可能已被其他管理员删除,请刷新当前页面后重新操作!");
		}
		tyCommodityDao.deleteBatch(ids);
		//删除图片文件
		ConfigUtil.delFolder(tyCommodityEntitiesList.get(0).getMainImage());
		return R.ok("操作成功!");
	}

	@Transactional
	@Override
	public void updateStatus(TyCommodityEntity tyCommodity) {
		tyCommodityDao.update(tyCommodity);
	}
	
}
