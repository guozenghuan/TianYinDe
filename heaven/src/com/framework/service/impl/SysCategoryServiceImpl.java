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
import com.framework.dao.SysCategoryDao;
import com.framework.entity.SysCategoryEntity;
import com.framework.entity.TyCommodityEntity;
import com.framework.service.SysCategoryService;
import com.framework.utils.ConfigUtil;
import com.framework.utils.R;
import com.framework.utils.StringUtil;
import com.framework.utils.TransactionRollBack;
import com.framework.utils.images.ImgCompress;
import com.framework.utils.R;



@Service("sysCategoryService")
public class SysCategoryServiceImpl implements SysCategoryService {
	@Autowired
	private SysCategoryDao sysCategoryDao;
	
	@Override
	public SysCategoryEntity queryObject(Integer id){
		return sysCategoryDao.queryObject(id);
	}
	
	@Override
	public List<SysCategoryEntity> queryObjectByKey(SysCategoryEntity sysCategory){
		return sysCategoryDao.queryObjectByKey(sysCategory);
	}
	
	@Override
	public List<SysCategoryEntity> queryList(Map<String, Object> map){
		return sysCategoryDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysCategoryDao.queryTotal(map);
	}
	
	@Override
	public void save(SysCategoryEntity sysCategory){
		sysCategoryDao.save(sysCategory);
	}
	
	@Transactional
	public R saveImg(HttpServletRequest request){
		//转换成实体类
		SysCategoryEntity sysCategory = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("sysCategory")), SysCategoryEntity.class);
		
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
//		sysCategory.setAddTime(new Date());
		sysCategoryDao.save(sysCategory);
		
		//图片路径和地址
		String[] names = mainImage.getOriginalFilename().split("\\.");
		String mainImagePath = ConfigUtil.getSystemCommodity(sysCategory.getId().toString()) + StringUtil.randomUUID() + "." + names[names.length-1];
		System.out.println("****************生成的图片路径*******************");
		System.out.println(mainImagePath);
		// 上传
		try {
			mainImage.transferTo(new File(ConfigUtil.getTomcatWebapps()+mainImagePath));
			
			//压缩图片
			ImgCompress.compressImag(640, 320, ConfigUtil.getTomcatWebapps()+mainImagePath);
			
			//设置主图路径
			sysCategory.setIconUrl(mainImagePath);
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
		
		int updateFlag = sysCategoryDao.update(sysCategory);
		if(updateFlag!=1){
			//操作失败执行数据回滚
			TransactionRollBack.rollBackUpdate(mainImagePath);
			return R.error("保存图片路径失败!!");
		}
		return R.ok("操作成功!");
	}
	@Override
	public void update(SysCategoryEntity sysCategory){
		sysCategoryDao.update(sysCategory);
	}
	
	@Override
	public void delete(Integer id){
		sysCategoryDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		sysCategoryDao.deleteBatch(ids);
	}
	/**
	 * 更新 --带图片
	 */
	@Override
	public R updateImg(HttpServletRequest request) {
			//转换成实体类
			String  s=request.getParameter("sysCategory");
			JSONObject  jo=JSON.parseObject(s);
			SysCategoryEntity sysCategory = JSONObject.toJavaObject(jo, SysCategoryEntity.class);
			
			//判断信息是否存在
			SysCategoryEntity sysCategoryFlag = new SysCategoryEntity();
			sysCategoryFlag.setId(sysCategory.getId());
			List<SysCategoryEntity> sysCategoryEntitiesList = sysCategoryDao.queryObjectByKey(sysCategoryFlag);
			if(sysCategoryEntitiesList.isEmpty() || sysCategoryEntitiesList.size() <= 0){
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
				delFileList.add(sysCategoryEntitiesList.get(0).getIconUrl());
				
				//判断图片类型
				String mainImageType = mainImage.getContentType();
				if(!mainImageType.equals("image/jpeg") && !mainImageType.equals("image/jpg") && !mainImageType.equals("image/png")){
					return R.error("主图图片格式错误!只能上传jpeg、jpg、png类型的图片!");
				}
				
				//图片路径和地址
				String[] names = mainImage.getOriginalFilename().split("\\.");
				String mainImagePath = ConfigUtil.getSystemCommodity(sysCategory.getId().toString()) + StringUtil.randomUUID() + "." + names[names.length-1];
				
				// 上传
				try {
					mainImage.transferTo(new File(ConfigUtil.getTomcatWebapps()+mainImagePath));
					delFilePathList.add(mainImagePath);//删除集合
					
					//压缩图片
					ImgCompress.compressImag(640, 320, ConfigUtil.getTomcatWebapps()+mainImagePath);
					
					//设置主图路径
					sysCategory.setIconUrl(mainImagePath);
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
			
		 
			
			int updateFlag = sysCategoryDao.update(sysCategory);
			if(updateFlag!=1){
				//操作失败执行数据回滚
				TransactionRollBack.rollBackUpdate();
				//删除文件集合
				ConfigUtil.deleteFile(delFilePathList);
				return R.error("保存图片路径失败!!");
			}
			
			 
			return R.ok("操作成功!");
		}
	
}
