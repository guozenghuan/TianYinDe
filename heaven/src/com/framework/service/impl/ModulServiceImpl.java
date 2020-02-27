package com.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.framework.dao.Modul01Dao;
import com.framework.dao.Modul02Dao;
import com.framework.dao.Modul03Dao;
import com.framework.dao.Modul04Dao;
import com.framework.dao.Modul05Dao;
import com.framework.dao.Modul06Dao;
import com.framework.dao.Modul07Dao;
import com.framework.dao.Modul08Dao;
import com.framework.dao.Modul09Dao;
import com.framework.dao.Modul10Dao;
import com.framework.dao.Modul11Dao;
import com.framework.dao.ModulDao;
import com.framework.entity.Modul01Entity;
import com.framework.entity.Modul02Entity;
import com.framework.entity.Modul03Entity;
import com.framework.entity.Modul04Entity;
import com.framework.entity.Modul05Entity;
import com.framework.entity.Modul06Entity;
import com.framework.entity.Modul07Entity;
import com.framework.entity.Modul08Entity;
import com.framework.entity.Modul09Entity;
import com.framework.entity.Modul10Entity;
import com.framework.entity.Modul11Entity;
import com.framework.entity.ModulEntity;
import com.framework.service.ModulService;
import com.framework.utils.ConfigUtil;
import com.framework.utils.ModulHtmlUtils;
import com.framework.utils.R;
import com.framework.utils.StringUtil;
import com.framework.utils.TransactionRollBack;
import com.framework.utils.saveFiles.ModulImageSave;

@Service("modulService")
public class ModulServiceImpl implements ModulService {
	@Autowired
	private ModulDao modulDao;

	@Autowired
	private Modul01Dao modul01Dao;
	@Autowired
	private Modul02Dao modul02Dao;
	@Autowired
	private Modul03Dao modul03Dao;
	@Autowired
	private Modul04Dao modul04Dao;
	@Autowired
	private Modul05Dao modul05Dao;
	@Autowired
	private Modul06Dao modul06Dao;
	@Autowired
	private Modul07Dao modul07Dao;
	@Autowired
	private Modul08Dao modul08Dao;
	@Autowired
	private Modul09Dao modul09Dao;
	@Autowired
	private Modul10Dao modul10Dao;
	@Autowired
	private Modul11Dao modul11Dao;

	@Override
	public ModulEntity queryObject(Long id) {
		ModulEntity modulEntity = modulDao.queryObject(id);
		/*if(!StringUtil.isEmpty(modulEntity)){
			modulEntity.setModulNameAndId(modulEntity.getModulNameAndId()+"?now="+new Date().getTime());
		}*/
		return modulEntity;
	}
	
	@Override
	public ModulEntity queryObject(Long id,Boolean seetingBtn) {
		ModulEntity modulEntity = modulDao.queryObject(id);

		List<String> moduleList = new ArrayList<>();

		String type = modulEntity.getModulType();//system系统，userid=用户

		String modulNameAndId = modulEntity.getModulNameAndId();
		if (!StringUtil.isEmpty(modulNameAndId) && StringUtil.isIndexOfStr(modulNameAndId, ";")) {
			String[] strings = modulNameAndId.split(";");
			for (String s : strings) {
				if (StringUtil.isIndexOfStr(s, "=")) {
					String modulCode = s.split("=")[0];
					try {
						Long modulId = Long.valueOf(s.split("=")[1]);

						String moduleData = "";
						if (modulCode.trim().equals("modul_01")) {
							Modul01Entity modul01Entity = modul01Dao.queryObject(modulId);
							moduleData = modul01Entity.getModulPath() + "?" + "seetingBtn=" + seetingBtn + "&type="
									+ type + "&id=" + modul01Entity.getId();

						} else if (modulCode.trim().equals("modul_02")) {
							Modul02Entity modul02Entity = modul02Dao.queryObject(modulId);
							moduleData = modul02Entity.getModulPath() + "?" + "seetingBtn=" + seetingBtn + "&type="
									+ type + "&id=" + modul02Entity.getId();

						} else if (modulCode.trim().equals("modul_03")) {
							Modul03Entity modul03Entity = modul03Dao.queryObject(modulId);
							moduleData = modul03Entity.getModulPath() + "?" + "seetingBtn=" + seetingBtn + "&type="
									+ type + "&id=" + modul03Entity.getId();

						} else if (modulCode.trim().equals("modul_04")) {
							Modul04Entity modul04Entity = modul04Dao.queryObject(modulId);
							moduleData = modul04Entity.getModulPath() + "?" + "seetingBtn=" + seetingBtn + "&type="
									+ type + "&id=" + modul04Entity.getId();

						} else if (modulCode.trim().equals("modul_05")) {
							Modul05Entity modul05Entity = modul05Dao.queryObject(modulId);
							moduleData = modul05Entity.getModulPath() + "?" + "seetingBtn=" + seetingBtn + "&type="
									+ type + "&id=" + modul05Entity.getId();

						} else if (modulCode.trim().equals("modul_06")) {
							Modul06Entity modul06Entity = modul06Dao.queryObject(modulId);
							moduleData = modul06Entity.getModulPath() + "?" + "seetingBtn=" + seetingBtn + "&type="
									+ type + "&id=" + modul06Entity.getId();

						} else if (modulCode.trim().equals("modul_07")) {
							Modul07Entity modul07Entity = modul07Dao.queryObject(modulId);
							moduleData = modul07Entity.getModulPath() + "?" + "seetingBtn=" + seetingBtn + "&type="
									+ type + "&id=" + modul07Entity.getId();

						} else if (modulCode.trim().equals("modul_08")) {
							Modul08Entity modul08Entity = modul08Dao.queryObject(modulId);
							moduleData = modul08Entity.getModulPath() + "?" + "seetingBtn=" + seetingBtn + "&type="
									+ type + "&id=" + modul08Entity.getId();

						} else if (modulCode.trim().equals("modul_09")) {
							Modul09Entity modul09Entity = modul09Dao.queryObject(modulId);
							moduleData = modul09Entity.getModulPath() + "?" + "seetingBtn=" + seetingBtn + "&type="
									+ type + "&id=" + modul09Entity.getId();

						} else if (modulCode.trim().equals("modul_10")) {
							Modul10Entity modul10Entity = modul10Dao.queryObject(modulId);
							moduleData = modul10Entity.getModulPath() + "?" + "seetingBtn=" + seetingBtn + "&type="
									+ type + "&id=" + modul10Entity.getId();

						} else if (modulCode.trim().equals("modul_11")) {
							Modul11Entity modul11Entity = modul11Dao.queryObject(modulId);
							moduleData = modul11Entity.getModulPath() + "?" + "seetingBtn=" + seetingBtn + "&type="
									+ type + "&id=" + modul11Entity.getId();

						}

						moduleData = moduleData+"&"+"mainModulId="+id;
						moduleList.add(moduleData);
					} catch (Exception e) {
					}

				}
			}
		}
		
		modulEntity.setModuleList(moduleList);
		return modulEntity;
	}

	@Override
	public List<ModulEntity> queryObjectByKey(ModulEntity modul) {
		return modulDao.queryObjectByKey(modul);
	}
	
	@Override
	public List<ModulEntity> queryListByNow(Map<String, Object> map) {
		return modulDao.queryList(map);
	}

	@Override
	public List<ModulEntity> queryList(Map<String, Object> map) {
		Boolean seetingBtn = false;//不可编辑
		
		List<ModulEntity> modulList = modulDao.queryList(map);
		for(ModulEntity modulEntity : modulList){
			String modulNameAndId = modulEntity.getModulNameAndId();
			if(!StringUtil.isEmpty(modulNameAndId)){
				Long mainModulId = modulEntity.getId();//主模板id
				String modulCode = modulNameAndId.split(";")[0].split("=")[0];//子模板类型
				Long modulId = Long.valueOf(modulNameAndId.split(";")[0].split("=")[1]);//子模板id
				
				String oneModulPath = "/statics/modul/"+modulCode+".html?seetingBtn="+seetingBtn+"&mainModulId="+mainModulId+"&modulCode="+modulCode+"&id="+modulId;
				
				modulEntity.setOneModulId(modulId);
				modulEntity.setOneModulPath(oneModulPath);
			}
		}
		return modulList;
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return modulDao.queryTotal(map);
	}

	@Override
	public Long save(ModulEntity modul) {
		modul.setCreatetime(new Date());
		modul.setModulType("system");
		modul.setUserId(0L);
		modulDao.save(modul);
		return modul.getId();
	}

	@Transactional
	@Override
	public R update(ModulEntity modul) {
		ModulEntity hEntity = modulDao.queryObject(modul.getId());
		if(StringUtil.isEmpty(hEntity)){
			return R.error("查找不到请帖信息,可能请帖已经删除,请刷新后选择请帖操作!");
		}
		ModulEntity save = new ModulEntity();
		save.setId(modul.getId());
		save.setMusicId(modul.getMusicId());
		save.setStatus(modul.getStatus());
		
		if(StringUtil.isEmpty(modul.getOneModulPath())){
			return R.error("获取修改的信息失败,请重新保存,或刷新页面后重新选择请帖操作!");
		}
		
		//修改html代码信息
		R flag = ModulHtmlUtils.rederAndUpdateHtmlFile(hEntity.getModulNameAndId(), modul.getOneModulPath());
		if(Integer.valueOf(flag.get("code").toString()) != 0){
			return flag;
		}
		modulDao.update(save);
		return R.ok("操作成功!");
	}
	
	@Transactional
	@Override
	public void updateSql(ModulEntity modul) {
		modulDao.update(modul);
	}

	@Override
	public void delete(Long id) {
		modulDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		modulDao.deleteBatch(ids);
	}
	
	@Transactional
	@Override
	public R userSelectModulNow(Long userid, Long mainModulId) {
		if(StringUtil.isEmpty(mainModulId)){
			return R.error("获取不到模板信息,请重新加载!");
		}
		//获取主模板信息
		ModulEntity modulEntity = modulDao.queryObject(mainModulId);
		if(StringUtil.isEmpty(modulEntity)){
			return R.error("获取不到模板信息,请重新加载!");
		}
		
		ModulEntity userModulEntity = new ModulEntity();
		userModulEntity.setModulType("user");
		userModulEntity.setMusicId(modulEntity.getMusicId());
		userModulEntity.setStatus("show");
		userModulEntity.setUserId(userid);
		userModulEntity.setCreatetime(new Date());
		
		//复制模板路径
		String[] pathS = modulEntity.getModulNameAndId().split("/");
		String rep = pathS[pathS.length-1];
		String newPath = modulEntity.getModulNameAndId().replace(rep, StringUtil.randomUUID()+".html");
		
		try {
			ConfigUtil.copyFile(modulEntity.getModulNameAndId(), newPath);
			userModulEntity.setModulNameAndId(newPath);
			modulDao.save(userModulEntity);
		} catch (IOException e) {
			e.printStackTrace();
			return R.error("选择模板失败,请重新选择!");
		}
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("modulId", userModulEntity.getId());
		return R.ok("操作成功!").put("data", dataMap);
	}
	
	@Transactional
	@Override
	public R userSelectModul(Long userid, Long mainModulId) {
		if(StringUtil.isEmpty(mainModulId)){
			return R.error("获取不到模板信息,请重新加载!");
		}
		//获取主模板信息
		ModulEntity modulEntity = modulDao.queryObject(mainModulId);
		if(StringUtil.isEmpty(modulEntity)){
			return R.error("获取不到模板信息,请重新加载!");
		}
		
		ModulEntity userModulEntity = new ModulEntity();
		userModulEntity.setModulType("user");
		userModulEntity.setMusicId(modulEntity.getMusicId());
		userModulEntity.setStatus("show");
		userModulEntity.setUserId(userid);
		userModulEntity.setCreatetime(new Date());
		
		String userModulNameAndId = "";
		if(!StringUtil.isEmpty(modulEntity.getModulNameAndId())){
			//复制模板信息
			String modulNameAndId = modulEntity.getModulNameAndId();
			if (!StringUtil.isEmpty(modulNameAndId) && StringUtil.isIndexOfStr(modulNameAndId, ";")) {
				String[] strings = modulNameAndId.split(";");
				for (String s : strings) {
					if (StringUtil.isIndexOfStr(s, "=")) {
						String modulCode = s.split("=")[0];
						try {
							Long modulId = Long.valueOf(s.split("=")[1]);

							if (modulCode.trim().equals("modul_01")) {
								Modul01Entity modul01Entity = modul01Dao.queryObject(modulId);
								modul01Entity.setId(null);
								modul01Entity.setModulType("user");
								modul01Dao.save(modul01Entity);
								modul01Entity.setImage01(ConfigUtil.copyModulImagesByUser(modul01Entity.getId(), "modul_01", modul01Entity.getImage01()));
								modul01Dao.update(modul01Entity);
								userModulNameAndId = userModulNameAndId+"modul_01="+modul01Entity.getId()+";";
							
							} else if (modulCode.trim().equals("modul_02")) {
								Modul02Entity modul02Entity = modul02Dao.queryObject(modulId);
								modul02Entity.setId(null);
								modul02Entity.setModulType("user");
								modul02Dao.save(modul02Entity);
								modul02Entity.setImage01(ConfigUtil.copyModulImagesByUser(modul02Entity.getId(), "modul_02", modul02Entity.getImage01()));
								modul02Entity.setImage02(ConfigUtil.copyModulImagesByUser(modul02Entity.getId(), "modul_02", modul02Entity.getImage02()));
								modul02Dao.update(modul02Entity);
								userModulNameAndId = userModulNameAndId+"modul_02="+modul02Entity.getId()+";";

							} else if (modulCode.trim().equals("modul_03")) {
								Modul03Entity modul03Entity = modul03Dao.queryObject(modulId);
								modul03Entity.setId(null);
								modul03Entity.setModulType("user");
								modul03Dao.save(modul03Entity);
								modul03Entity.setImage01(ConfigUtil.copyModulImagesByUser(modul03Entity.getId(), "modul_03", modul03Entity.getImage01()));
								modul03Entity.setImage02(ConfigUtil.copyModulImagesByUser(modul03Entity.getId(), "modul_03", modul03Entity.getImage02()));
								modul03Entity.setImage03(ConfigUtil.copyModulImagesByUser(modul03Entity.getId(), "modul_03", modul03Entity.getImage03()));
								modul03Dao.update(modul03Entity);
								userModulNameAndId = userModulNameAndId+"modul_03="+modul03Entity.getId()+";";

							} else if (modulCode.trim().equals("modul_04")) {
								Modul04Entity modul04Entity = modul04Dao.queryObject(modulId);
								modul04Entity.setId(null);
								modul04Entity.setModulType("user");
								modul04Dao.save(modul04Entity);
								modul04Entity.setImage01(ConfigUtil.copyModulImagesByUser(modul04Entity.getId(), "modul_04", modul04Entity.getImage01()));
								modul04Entity.setImage02(ConfigUtil.copyModulImagesByUser(modul04Entity.getId(), "modul_04", modul04Entity.getImage02()));
								modul04Entity.setImage03(ConfigUtil.copyModulImagesByUser(modul04Entity.getId(), "modul_04", modul04Entity.getImage03()));
								modul04Entity.setImage04(ConfigUtil.copyModulImagesByUser(modul04Entity.getId(), "modul_04", modul04Entity.getImage04()));
								modul04Dao.update(modul04Entity);
								userModulNameAndId = userModulNameAndId+"modul_04="+modul04Entity.getId()+";";

							} else if (modulCode.trim().equals("modul_05")) {
								Modul05Entity modul05Entity = modul05Dao.queryObject(modulId);
								modul05Entity.setId(null);
								modul05Entity.setModulType("user");
								modul05Dao.save(modul05Entity);
								modul05Entity.setImage01(ConfigUtil.copyModulImagesByUser(modul05Entity.getId(), "modul_05", modul05Entity.getImage01()));
								modul05Entity.setImage02(ConfigUtil.copyModulImagesByUser(modul05Entity.getId(), "modul_05", modul05Entity.getImage02()));
								modul05Entity.setImage03(ConfigUtil.copyModulImagesByUser(modul05Entity.getId(), "modul_05", modul05Entity.getImage03()));
								modul05Dao.update(modul05Entity);
								userModulNameAndId = userModulNameAndId+"modul_05="+modul05Entity.getId()+";";

							} else if (modulCode.trim().equals("modul_06")) {
								Modul06Entity modul06Entity = modul06Dao.queryObject(modulId);
								modul06Entity.setId(null);
								modul06Entity.setModulType("user");
								modul06Dao.save(modul06Entity);
								modul06Entity.setImage01(ConfigUtil.copyModulImagesByUser(modul06Entity.getId(), "modul_06", modul06Entity.getImage01()));
								modul06Dao.update(modul06Entity);
								userModulNameAndId = userModulNameAndId+"modul_06="+modul06Entity.getId()+";";

							} else if (modulCode.trim().equals("modul_07")) {
								Modul07Entity modul07Entity = modul07Dao.queryObject(modulId);
								modul07Entity.setId(null);
								modul07Entity.setModulType("user");
								modul07Dao.save(modul07Entity);
								modul07Entity.setImage01(ConfigUtil.copyModulImagesByUser(modul07Entity.getId(), "modul_07", modul07Entity.getImage01()));
								modul07Entity.setImage02(ConfigUtil.copyModulImagesByUser(modul07Entity.getId(), "modul_07", modul07Entity.getImage02()));
								modul07Entity.setImage03(ConfigUtil.copyModulImagesByUser(modul07Entity.getId(), "modul_07", modul07Entity.getImage03()));
								modul07Dao.update(modul07Entity);
								userModulNameAndId = userModulNameAndId+"modul_07="+modul07Entity.getId()+";";

							} else if (modulCode.trim().equals("modul_08")) {
								Modul08Entity modul08Entity = modul08Dao.queryObject(modulId);
								modul08Entity.setId(null);
								modul08Entity.setModulType("user");
								modul08Dao.save(modul08Entity);
								modul08Entity.setImage01(ConfigUtil.copyModulImagesByUser(modul08Entity.getId(), "modul_08", modul08Entity.getImage01()));
								modul08Entity.setImage02(ConfigUtil.copyModulImagesByUser(modul08Entity.getId(), "modul_08", modul08Entity.getImage02()));
								modul08Entity.setImage03(ConfigUtil.copyModulImagesByUser(modul08Entity.getId(), "modul_08", modul08Entity.getImage03()));
								modul08Dao.update(modul08Entity);
								userModulNameAndId = userModulNameAndId+"modul_08="+modul08Entity.getId()+";";

							} else if (modulCode.trim().equals("modul_09")) {
								Modul09Entity modul09Entity = modul09Dao.queryObject(modulId);
								modul09Entity.setId(null);
								modul09Entity.setModulType("user");
								modul09Dao.save(modul09Entity);
								modul09Entity.setImage01(ConfigUtil.copyModulImagesByUser(modul09Entity.getId(), "modul_09", modul09Entity.getImage01()));
								modul09Entity.setImage02(ConfigUtil.copyModulImagesByUser(modul09Entity.getId(), "modul_09", modul09Entity.getImage02()));
								modul09Dao.update(modul09Entity);
								userModulNameAndId = userModulNameAndId+"modul_09="+modul09Entity.getId()+";";

							} else if (modulCode.trim().equals("modul_10")) {
								Modul10Entity modul10Entity = modul10Dao.queryObject(modulId);
								modul10Entity.setId(null);
								modul10Entity.setModulType("user");
								modul10Dao.save(modul10Entity);
								modul10Entity.setImage01(ConfigUtil.copyModulImagesByUser(modul10Entity.getId(), "modul_10", modul10Entity.getImage01()));
								modul10Entity.setImage02(ConfigUtil.copyModulImagesByUser(modul10Entity.getId(), "modul_10", modul10Entity.getImage02()));
								modul10Entity.setImage03(ConfigUtil.copyModulImagesByUser(modul10Entity.getId(), "modul_10", modul10Entity.getImage03()));
								modul10Dao.update(modul10Entity);
								userModulNameAndId = userModulNameAndId+"modul_10="+modul10Entity.getId()+";";

							} else if (modulCode.trim().equals("modul_11")) {
								Modul11Entity modul11Entity = modul11Dao.queryObject(modulId);
								modul11Entity.setId(null);
								modul11Entity.setModulType("user");
								modul11Dao.save(modul11Entity);
								modul11Entity.setImage01(ConfigUtil.copyModulImagesByUser(modul11Entity.getId(), "modul_11", modul11Entity.getImage01()));
								modul11Entity.setImage02(ConfigUtil.copyModulImagesByUser(modul11Entity.getId(), "modul_11", modul11Entity.getImage02()));
								modul11Entity.setImage03(ConfigUtil.copyModulImagesByUser(modul11Entity.getId(), "modul_11", modul11Entity.getImage03()));
								modul11Dao.update(modul11Entity);
								userModulNameAndId = userModulNameAndId+"modul_11="+modul11Entity.getId()+";";
							}

						} catch (Exception e) {
						}

					}
				}
			}
		}
		
		userModulEntity.setModulNameAndId(userModulNameAndId);
		modulDao.save(userModulEntity);
		Map<String, Object> data = new HashMap<>();
		data.put("modulId", userModulEntity.getId());
		return R.ok().put("data", data);
	}
	
	@Transactional
	@Override
	public R deleteModul(Long mainModulId,Long id, String modulCode) {
		//获取主模板信息
		ModulEntity modulEntity = modulDao.queryObject(mainModulId);
		if(StringUtil.isEmpty(modulEntity)){
			return R.error("获取不到模板信息,请重新加载!");
		}
		//删除主模板中的信息
		String codeAndId = modulCode.trim()+"="+id+";";
		if(!StringUtil.isIndexOfStr(modulEntity.getModulNameAndId(), codeAndId)){
			return R.error("获取不到模板信息,请重新加载!");
		}
		modulEntity.setModulNameAndId(modulEntity.getModulNameAndId().replace(codeAndId, ""));
		modulDao.update(modulEntity);
		
		if (modulCode.trim().equals("modul_01")) {
			Modul01Entity modul01Entity = modul01Dao.queryObject(id);
			if(StringUtil.isEmpty(modul01Entity)){
				TransactionRollBack.rollBackUpdate();//回滚数据
				return R.error("获取不到模板信息,请重新加载!");
			}
			
			//删除模板信息
			modul01Dao.delete(modul01Entity.getId());
			//删除模板图片文件夹
			ConfigUtil.delModulFolder(id, modulCode);
			return R.ok();
		} else if (modulCode.trim().equals("modul_02")) {
			Modul02Entity modul02Entity = modul02Dao.queryObject(id);
			if(StringUtil.isEmpty(modul02Entity)){
				TransactionRollBack.rollBackUpdate();//回滚数据
				return R.error("获取不到模板信息,请重新加载!");
			}

			//删除模板信息
			modul02Dao.delete(modul02Entity.getId());
			//删除模板图片文件夹
			ConfigUtil.delModulFolder(id, modulCode);
			return R.ok();
		} else if (modulCode.trim().equals("modul_03")) {
			Modul03Entity modul03Entity = modul03Dao.queryObject(id);
			if(StringUtil.isEmpty(modul03Entity)){
				TransactionRollBack.rollBackUpdate();//回滚数据
				return R.error("获取不到模板信息,请重新加载!");
			}

			//删除模板信息
			modul03Dao.delete(modul03Entity.getId());
			//删除模板图片文件夹
			ConfigUtil.delModulFolder(id, modulCode);
			return R.ok();
		} else if (modulCode.trim().equals("modul_04")) {
			Modul04Entity modul04Entity = modul04Dao.queryObject(id);
			if(StringUtil.isEmpty(modul04Entity)){
				TransactionRollBack.rollBackUpdate();//回滚数据
				return R.error("获取不到模板信息,请重新加载!");
			}

			//删除模板信息
			modul04Dao.delete(modul04Entity.getId());
			//删除模板图片文件夹
			ConfigUtil.delModulFolder(id, modulCode);
			return R.ok();
		} else if (modulCode.trim().equals("modul_05")) {
			Modul05Entity modul05Entity = modul05Dao.queryObject(id);
			if(StringUtil.isEmpty(modul05Entity)){
				TransactionRollBack.rollBackUpdate();//回滚数据
				return R.error("获取不到模板信息,请重新加载!");
			}

			//删除模板信息
			modul05Dao.delete(modul05Entity.getId());
			//删除模板图片文件夹
			ConfigUtil.delModulFolder(id, modulCode);
			return R.ok();
		} else if (modulCode.trim().equals("modul_06")) {
			Modul06Entity modul06Entity = modul06Dao.queryObject(id);
			if(StringUtil.isEmpty(modul06Entity)){
				TransactionRollBack.rollBackUpdate();//回滚数据
				return R.error("获取不到模板信息,请重新加载!");
			}

			//删除模板信息
			modul06Dao.delete(modul06Entity.getId());
			//删除模板图片文件夹
			ConfigUtil.delModulFolder(id, modulCode);
			return R.ok();
		} else if (modulCode.trim().equals("modul_07")) {
			Modul07Entity modul07Entity = modul07Dao.queryObject(id);
			if(StringUtil.isEmpty(modul07Entity)){
				TransactionRollBack.rollBackUpdate();//回滚数据
				return R.error("获取不到模板信息,请重新加载!");
			}

			//删除模板信息
			modul07Dao.delete(modul07Entity.getId());
			//删除模板图片文件夹
			ConfigUtil.delModulFolder(id, modulCode);
			return R.ok();
		} else if (modulCode.trim().equals("modul_08")) {
			Modul08Entity modul08Entity = modul08Dao.queryObject(id);
			if(StringUtil.isEmpty(modul08Entity)){
				TransactionRollBack.rollBackUpdate();//回滚数据
				return R.error("获取不到模板信息,请重新加载!");
			}

			//删除模板信息
			modul08Dao.delete(modul08Entity.getId());
			//删除模板图片文件夹
			ConfigUtil.delModulFolder(id, modulCode);
			return R.ok();
		} else if (modulCode.trim().equals("modul_09")) {
			Modul09Entity modul09Entity = modul09Dao.queryObject(id);
			if(StringUtil.isEmpty(modul09Entity)){
				TransactionRollBack.rollBackUpdate();//回滚数据
				return R.error("获取不到模板信息,请重新加载!");
			}

			//删除模板信息
			modul09Dao.delete(modul09Entity.getId());
			//删除模板图片文件夹
			ConfigUtil.delModulFolder(id, modulCode);
			return R.ok();
		} else if (modulCode.trim().equals("modul_10")) {
			Modul10Entity modul10Entity = modul10Dao.queryObject(id);
			if(StringUtil.isEmpty(modul10Entity)){
				TransactionRollBack.rollBackUpdate();//回滚数据
				return R.error("获取不到模板信息,请重新加载!");
			}

			//删除模板信息
			modul10Dao.delete(modul10Entity.getId());
			//删除模板图片文件夹
			ConfigUtil.delModulFolder(id, modulCode);
			return R.ok();
		} else if (modulCode.trim().equals("modul_11")) {
			Modul11Entity modul11Entity = modul11Dao.queryObject(id);
			if(StringUtil.isEmpty(modul11Entity)){
				TransactionRollBack.rollBackUpdate();//回滚数据
				return R.error("获取不到模板信息,请重新加载!");
			}

			//删除模板信息
			modul11Dao.delete(modul11Entity.getId());
			//删除模板图片文件夹
			ConfigUtil.delModulFolder(id, modulCode);
			return R.ok();
		}
		
		return R.error("获取不到模板信息,请重新加载!");
	}

	
	@Override
	public R queryModulInfo(Long id, String modulCode) {
		if(StringUtil.isEmpty(id) || StringUtil.isEmpty(modulCode)){
			return R.error("获取不到模板信息,请重新加载!");
		}
		
		if (modulCode.trim().equals("modul_01")) {
			Modul01Entity modul01Entity = modul01Dao.queryObject(id);
			if(StringUtil.isEmpty(modul01Entity)){
				return R.error("获取不到模板信息,请重新加载!");
			}
			return R.ok().put("modulData", modul01Entity);
		} else if (modulCode.trim().equals("modul_02")) {
			Modul02Entity modul02Entity = modul02Dao.queryObject(id);
			if(StringUtil.isEmpty(modul02Entity)){
				return R.error("获取不到模板信息,请重新加载!");
			}
			return R.ok().put("modulData", modul02Entity);
		} else if (modulCode.trim().equals("modul_03")) {
			Modul03Entity modul03Entity = modul03Dao.queryObject(id);
			if(StringUtil.isEmpty(modul03Entity)){
				return R.error("获取不到模板信息,请重新加载!");
			}
			return R.ok().put("modulData", modul03Entity);
		} else if (modulCode.trim().equals("modul_04")) {
			Modul04Entity modul04Entity = modul04Dao.queryObject(id);
			if(StringUtil.isEmpty(modul04Entity)){
				return R.error("获取不到模板信息,请重新加载!");
			}
			return R.ok().put("modulData", modul04Entity);
		} else if (modulCode.trim().equals("modul_05")) {
			Modul05Entity modul05Entity = modul05Dao.queryObject(id);
			if(StringUtil.isEmpty(modul05Entity)){
				return R.error("获取不到模板信息,请重新加载!");
			}
			return R.ok().put("modulData", modul05Entity);
		} else if (modulCode.trim().equals("modul_06")) {
			Modul06Entity modul06Entity = modul06Dao.queryObject(id);
			if(StringUtil.isEmpty(modul06Entity)){
				return R.error("获取不到模板信息,请重新加载!");
			}
			return R.ok().put("modulData", modul06Entity);
		} else if (modulCode.trim().equals("modul_07")) {
			Modul07Entity modul07Entity = modul07Dao.queryObject(id);
			if(StringUtil.isEmpty(modul07Entity)){
				return R.error("获取不到模板信息,请重新加载!");
			}
			return R.ok().put("modulData", modul07Entity);
		} else if (modulCode.trim().equals("modul_08")) {
			Modul08Entity modul08Entity = modul08Dao.queryObject(id);
			if(StringUtil.isEmpty(modul08Entity)){
				return R.error("获取不到模板信息,请重新加载!");
			}
			return R.ok().put("modulData", modul08Entity);
		} else if (modulCode.trim().equals("modul_09")) {
			Modul09Entity modul09Entity = modul09Dao.queryObject(id);
			if(StringUtil.isEmpty(modul09Entity)){
				return R.error("获取不到模板信息,请重新加载!");
			}
			return R.ok().put("modulData", modul09Entity);
		} else if (modulCode.trim().equals("modul_10")) {
			Modul10Entity modul10Entity = modul10Dao.queryObject(id);
			if(StringUtil.isEmpty(modul10Entity)){
				return R.error("获取不到模板信息,请重新加载!");
			}
			return R.ok().put("modulData", modul10Entity);
		} else if (modulCode.trim().equals("modul_11")) {
			Modul11Entity modul11Entity = modul11Dao.queryObject(id);
			if(StringUtil.isEmpty(modul11Entity)){
				return R.error("获取不到模板信息,请重新加载!");
			}
			return R.ok().put("modulData", modul11Entity);
		}
		
		return R.error("获取不到模板信息,请重新加载!");
	}

	@Override
	public Long saveModul(String modulCode) {
		// 保存模板信息
		if (modulCode.trim().equals("modul_01")) {
			Modul01Entity modul01Entity = new Modul01Entity();
			modul01Entity.setModulPath("/statics/modul/" + modulCode + ".html");
			modul01Entity.setModulType("system");
			modul01Dao.save(modul01Entity);

			return modul01Entity.getId();
		} else if (modulCode.trim().equals("modul_02")) {
			Modul02Entity modul02Entity = new Modul02Entity();
			modul02Entity.setModulPath("/statics/modul/" + modulCode + ".html");
			modul02Entity.setModulType("system");
			modul02Dao.save(modul02Entity);

			return modul02Entity.getId();
		} else if (modulCode.trim().equals("modul_03")) {
			Modul03Entity modul03Entity = new Modul03Entity();
			modul03Entity.setModulPath("/statics/modul/" + modulCode + ".html");
			modul03Entity.setModulType("system");
			modul03Dao.save(modul03Entity);

			return modul03Entity.getId();
		} else if (modulCode.trim().equals("modul_04")) {
			Modul04Entity modul04Entity = new Modul04Entity();
			modul04Entity.setModulPath("/statics/modul/" + modulCode + ".html");
			modul04Entity.setModulType("system");
			modul04Dao.save(modul04Entity);

			return modul04Entity.getId();
		} else if (modulCode.trim().equals("modul_05")) {
			Modul05Entity modul05Entity = new Modul05Entity();
			modul05Entity.setModulPath("/statics/modul/" + modulCode + ".html");
			modul05Entity.setModulType("system");
			modul05Dao.save(modul05Entity);

			return modul05Entity.getId();
		} else if (modulCode.trim().equals("modul_06")) {
			Modul06Entity modul06Entity = new Modul06Entity();
			modul06Entity.setModulPath("/statics/modul/" + modulCode + ".html");
			modul06Entity.setModulType("system");
			modul06Dao.save(modul06Entity);

			return modul06Entity.getId();
		} else if (modulCode.trim().equals("modul_07")) {
			Modul07Entity modul07Entity = new Modul07Entity();
			modul07Entity.setModulPath("/statics/modul/" + modulCode + ".html");
			modul07Entity.setModulType("system");
			modul07Dao.save(modul07Entity);

			return modul07Entity.getId();
		} else if (modulCode.trim().equals("modul_08")) {
			Modul08Entity modul08Entity = new Modul08Entity();
			modul08Entity.setModulPath("/statics/modul/" + modulCode + ".html");
			modul08Entity.setModulType("system");
			modul08Dao.save(modul08Entity);

			return modul08Entity.getId();
		} else if (modulCode.trim().equals("modul_09")) {
			Modul09Entity modul09Entity = new Modul09Entity();
			modul09Entity.setModulPath("/statics/modul/" + modulCode + ".html");
			modul09Entity.setModulType("system");
			modul09Dao.save(modul09Entity);

			return modul09Entity.getId();
		} else if (modulCode.trim().equals("modul_10")) {
			Modul10Entity modul10Entity = new Modul10Entity();
			modul10Entity.setModulPath("/statics/modul/" + modulCode + ".html");
			modul10Entity.setModulType("system");
			modul10Dao.save(modul10Entity);

			return modul10Entity.getId();
		} else if (modulCode.trim().equals("modul_11")) {
			Modul11Entity modul11Entity = new Modul11Entity();
			modul11Entity.setModulPath("/statics/modul/" + modulCode + ".html");
			modul11Entity.setModulType("system");
			modul11Dao.save(modul11Entity);

			return modul11Entity.getId();
		}
		return null;
	}

	@Override
	public R saveInformationModul(HttpServletRequest request) {
		String modulCode = request.getParameter("modul");
		if(StringUtil.isEmpty(modulCode)){
			return R.error("获取不到模板信息,请重新选择模板后编辑!");
		}
		//取出form-data中的二进制字段
		MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
		
		if (modulCode.trim().equals("modul_01")) {
			//转换成实体类
			Modul01Entity modul01Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul01Entity.class);
			if(StringUtil.isEmpty(modul01Entity) || modul01Entity.getId() == null){
				return R.error("请填写模板信息!");
			}
			//获取原来的信息
			Modul01Entity modul01EntityHistory = modul01Dao.queryObject(modul01Entity.getId());
			if(StringUtil.isEmpty(modul01EntityHistory)){
				return R.error("获取不到模板信息,请重新选择模板后编辑!");
			}
			/**保存图片**/
			MultipartFile image_01 = multipartRequest.getFile("image_01");
			if(StringUtil.isMultiFlag(image_01)){
				//保存图片
				modul01Entity.setImage01(ModulImageSave.saveMoulImage(image_01,modul01Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul01EntityHistory.getImage01());
			}
			//更新
			modul01Dao.update(modul01Entity);
			return R.ok();
		} else if (modulCode.trim().equals("modul_02")) {
			//转换成实体类
			Modul02Entity modul02Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul02Entity.class);
			if(StringUtil.isEmpty(modul02Entity) || modul02Entity.getId() == null){
				return R.error("请填写模板信息!");
			}
			//获取原来的信息
			Modul02Entity modul02EntityHistory = modul02Dao.queryObject(modul02Entity.getId());
			if(StringUtil.isEmpty(modul02EntityHistory)){
				return R.error("获取不到模板信息,请重新选择模板后编辑!");
			}
			/**保存图片**/
			MultipartFile image_01 = multipartRequest.getFile("image_01");
			if(StringUtil.isMultiFlag(image_01)){
				//保存图片
				modul02Entity.setImage01(ModulImageSave.saveMoulImage(image_01,modul02Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul02EntityHistory.getImage01());
			}
			MultipartFile image_02 = multipartRequest.getFile("image_02");
			if(StringUtil.isMultiFlag(image_02)){
				//保存图片
				modul02Entity.setImage02(ModulImageSave.saveMoulImage(image_02,modul02Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul02EntityHistory.getImage02());
			}
			//更新
			modul02Dao.update(modul02Entity);
			return R.ok();
		} else if (modulCode.trim().equals("modul_03")) {
			//转换成实体类
			Modul03Entity modul03Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul03Entity.class);
			if(StringUtil.isEmpty(modul03Entity) || modul03Entity.getId() == null){
				return R.error("请填写模板信息!");
			}
			//获取原来的信息
			Modul03Entity modul03EntityHistory = modul03Dao.queryObject(modul03Entity.getId());
			if(StringUtil.isEmpty(modul03EntityHistory)){
				return R.error("获取不到模板信息,请重新选择模板后编辑!");
			}
			/**保存图片**/
			MultipartFile image_01 = multipartRequest.getFile("image_01");
			if(StringUtil.isMultiFlag(image_01)){
				//保存图片
				modul03Entity.setImage01(ModulImageSave.saveMoulImage(image_01,modul03Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul03EntityHistory.getImage01());
			}
			MultipartFile image_02 = multipartRequest.getFile("image_02");
			if(StringUtil.isMultiFlag(image_02)){
				//保存图片
				modul03Entity.setImage02(ModulImageSave.saveMoulImage(image_02,modul03Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul03EntityHistory.getImage02());
			}
			MultipartFile image_03 = multipartRequest.getFile("image_03");
			if(StringUtil.isMultiFlag(image_03)){
				//保存图片
				modul03Entity.setImage03(ModulImageSave.saveMoulImage(image_03,modul03Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul03EntityHistory.getImage03());
			}
			//更新
			modul03Dao.update(modul03Entity);
			return R.ok();
		} else if (modulCode.trim().equals("modul_04")) {
			//转换成实体类
			Modul04Entity modul04Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul04Entity.class);
			if(StringUtil.isEmpty(modul04Entity) || modul04Entity.getId() == null){
				return R.error("请填写模板信息!");
			}
			//获取原来的信息
			Modul04Entity modul04EntityHistory = modul04Dao.queryObject(modul04Entity.getId());
			if(StringUtil.isEmpty(modul04EntityHistory)){
				return R.error("获取不到模板信息,请重新选择模板后编辑!");
			}
			/**保存图片**/
			MultipartFile image_01 = multipartRequest.getFile("image_01");
			if(StringUtil.isMultiFlag(image_01)){
				//保存图片
				modul04Entity.setImage01(ModulImageSave.saveMoulImage(image_01,modul04Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul04EntityHistory.getImage01());
			}
			MultipartFile image_02 = multipartRequest.getFile("image_02");
			if(StringUtil.isMultiFlag(image_02)){
				//保存图片
				modul04Entity.setImage02(ModulImageSave.saveMoulImage(image_02,modul04Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul04EntityHistory.getImage02());
			}
			MultipartFile image_03 = multipartRequest.getFile("image_03");
			if(StringUtil.isMultiFlag(image_03)){
				//保存图片
				modul04Entity.setImage03(ModulImageSave.saveMoulImage(image_03,modul04Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul04EntityHistory.getImage03());
			}
			MultipartFile image_04 = multipartRequest.getFile("image_04");
			if(StringUtil.isMultiFlag(image_04)){
				//保存图片
				modul04Entity.setImage04(ModulImageSave.saveMoulImage(image_04,modul04Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul04EntityHistory.getImage04());
			}
			//更新
			modul04Dao.update(modul04Entity);
			return R.ok();
		} else if (modulCode.trim().equals("modul_05")) {
			//转换成实体类
			Modul05Entity modul05Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul05Entity.class);
			if(StringUtil.isEmpty(modul05Entity) || modul05Entity.getId() == null){
				return R.error("请填写模板信息!");
			}
			//获取原来的信息
			Modul05Entity modul05EntityHistory = modul05Dao.queryObject(modul05Entity.getId());
			if(StringUtil.isEmpty(modul05EntityHistory)){
				return R.error("获取不到模板信息,请重新选择模板后编辑!");
			}
			/**保存图片**/
			MultipartFile image_01 = multipartRequest.getFile("image_01");
			if(StringUtil.isMultiFlag(image_01)){
				//保存图片
				modul05Entity.setImage01(ModulImageSave.saveMoulImage(image_01,modul05Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul05EntityHistory.getImage01());
			}
			MultipartFile image_02 = multipartRequest.getFile("image_02");
			if(StringUtil.isMultiFlag(image_02)){
				//保存图片
				modul05Entity.setImage02(ModulImageSave.saveMoulImage(image_02,modul05Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul05EntityHistory.getImage02());
			}
			MultipartFile image_03 = multipartRequest.getFile("image_03");
			if(StringUtil.isMultiFlag(image_03)){
				//保存图片
				modul05Entity.setImage03(ModulImageSave.saveMoulImage(image_03,modul05Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul05EntityHistory.getImage03());
			}
			//更新
			modul05Dao.update(modul05Entity);
			return R.ok();
		} else if (modulCode.trim().equals("modul_06")) {
			//转换成实体类
			Modul06Entity modul06Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul06Entity.class);
			if(StringUtil.isEmpty(modul06Entity) || modul06Entity.getId() == null){
				return R.error("请填写模板信息!");
			}
			//获取原来的信息
			Modul06Entity modul06EntityHistory = modul06Dao.queryObject(modul06Entity.getId());
			if(StringUtil.isEmpty(modul06EntityHistory)){
				return R.error("获取不到模板信息,请重新选择模板后编辑!");
			}
			/**保存图片**/
			MultipartFile image_01 = multipartRequest.getFile("image_01");
			if(StringUtil.isMultiFlag(image_01)){
				//保存图片
				modul06Entity.setImage01(ModulImageSave.saveMoulImage(image_01,modul06Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul06EntityHistory.getImage01());
			}
			//更新
			modul06Dao.update(modul06Entity);
			return R.ok();
		} else if (modulCode.trim().equals("modul_07")) {
			//转换成实体类
			Modul07Entity modul07Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul07Entity.class);
			if(StringUtil.isEmpty(modul07Entity) || modul07Entity.getId() == null){
				return R.error("请填写模板信息!");
			}
			//获取原来的信息
			Modul07Entity modul07EntityHistory = modul07Dao.queryObject(modul07Entity.getId());
			if(StringUtil.isEmpty(modul07EntityHistory)){
				return R.error("获取不到模板信息,请重新选择模板后编辑!");
			}
			/**保存图片**/
			MultipartFile image_01 = multipartRequest.getFile("image_01");
			if(StringUtil.isMultiFlag(image_01)){
				//保存图片
				modul07Entity.setImage01(ModulImageSave.saveMoulImage(image_01,modul07Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul07EntityHistory.getImage01());
			}
			MultipartFile image_02 = multipartRequest.getFile("image_02");
			if(StringUtil.isMultiFlag(image_02)){
				//保存图片
				modul07Entity.setImage02(ModulImageSave.saveMoulImage(image_02,modul07Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul07EntityHistory.getImage02());
			}
			MultipartFile image_03 = multipartRequest.getFile("image_03");
			if(StringUtil.isMultiFlag(image_03)){
				//保存图片
				modul07Entity.setImage03(ModulImageSave.saveMoulImage(image_03,modul07Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul07EntityHistory.getImage03());
			}
			//更新
			modul07Dao.update(modul07Entity);
			return R.ok();
		} else if (modulCode.trim().equals("modul_08")) {
			//转换成实体类
			Modul08Entity modul08Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul08Entity.class);
			if(StringUtil.isEmpty(modul08Entity) || modul08Entity.getId() == null){
				return R.error("请填写模板信息!");
			}
			//获取原来的信息
			Modul08Entity modul08EntityHistory = modul08Dao.queryObject(modul08Entity.getId());
			if(StringUtil.isEmpty(modul08EntityHistory)){
				return R.error("获取不到模板信息,请重新选择模板后编辑!");
			}
			/**保存图片**/
			MultipartFile image_01 = multipartRequest.getFile("image_01");
			if(StringUtil.isMultiFlag(image_01)){
				//保存图片
				modul08Entity.setImage01(ModulImageSave.saveMoulImage(image_01,modul08Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul08EntityHistory.getImage01());
			}
			MultipartFile image_02 = multipartRequest.getFile("image_02");
			if(StringUtil.isMultiFlag(image_02)){
				//保存图片
				modul08Entity.setImage02(ModulImageSave.saveMoulImage(image_02,modul08Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul08EntityHistory.getImage02());
			}
			MultipartFile image_03 = multipartRequest.getFile("image_03");
			if(StringUtil.isMultiFlag(image_03)){
				//保存图片
				modul08Entity.setImage03(ModulImageSave.saveMoulImage(image_03,modul08Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul08EntityHistory.getImage03());
			}
			//更新
			modul08Dao.update(modul08Entity);
			return R.ok();
		} else if (modulCode.trim().equals("modul_09")) {
			//转换成实体类
			Modul09Entity modul09Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul09Entity.class);
			if(StringUtil.isEmpty(modul09Entity) || modul09Entity.getId() == null){
				return R.error("请填写模板信息!");
			}
			//获取原来的信息
			Modul09Entity modul09EntityHistory = modul09Dao.queryObject(modul09Entity.getId());
			if(StringUtil.isEmpty(modul09EntityHistory)){
				return R.error("获取不到模板信息,请重新选择模板后编辑!");
			}
			/**保存图片**/
			MultipartFile image_01 = multipartRequest.getFile("image_01");
			if(StringUtil.isMultiFlag(image_01)){
				//保存图片
				modul09Entity.setImage01(ModulImageSave.saveMoulImage(image_01,modul09Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul09EntityHistory.getImage01());
			}
			MultipartFile image_02 = multipartRequest.getFile("image_02");
			if(StringUtil.isMultiFlag(image_02)){
				//保存图片
				modul09Entity.setImage02(ModulImageSave.saveMoulImage(image_02,modul09Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul09EntityHistory.getImage02());
			}
			//更新
			modul09Dao.update(modul09Entity);
			return R.ok();
		} else if (modulCode.trim().equals("modul_10")) {
			//转换成实体类
			Modul10Entity modul10Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul10Entity.class);
			if(StringUtil.isEmpty(modul10Entity) || modul10Entity.getId() == null){
				return R.error("请填写模板信息!");
			}
			//获取原来的信息
			Modul10Entity modul10EntityHistory = modul10Dao.queryObject(modul10Entity.getId());
			if(StringUtil.isEmpty(modul10EntityHistory)){
				return R.error("获取不到模板信息,请重新选择模板后编辑!");
			}
			/**保存图片**/
			MultipartFile image_01 = multipartRequest.getFile("image_01");
			if(StringUtil.isMultiFlag(image_01)){
				//保存图片
				modul10Entity.setImage01(ModulImageSave.saveMoulImage(image_01,modul10Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul10EntityHistory.getImage01());
			}
			MultipartFile image_02 = multipartRequest.getFile("image_02");
			if(StringUtil.isMultiFlag(image_02)){
				//保存图片
				modul10Entity.setImage02(ModulImageSave.saveMoulImage(image_02,modul10Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul10EntityHistory.getImage02());
			}
			MultipartFile image_03 = multipartRequest.getFile("image_03");
			if(StringUtil.isMultiFlag(image_03)){
				//保存图片
				modul10Entity.setImage03(ModulImageSave.saveMoulImage(image_03,modul10Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul10EntityHistory.getImage03());
			}
			//更新
			modul10Dao.update(modul10Entity);
			return R.ok();
		} else if (modulCode.trim().equals("modul_11")) {
			//转换成实体类
			Modul11Entity modul11Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter("entity")), Modul11Entity.class);
			if(StringUtil.isEmpty(modul11Entity) || modul11Entity.getId() == null){
				return R.error("请填写模板信息!");
			}
			//获取原来的信息
			Modul11Entity modul11EntityHistory = modul11Dao.queryObject(modul11Entity.getId());
			if(StringUtil.isEmpty(modul11EntityHistory)){
				return R.error("获取不到模板信息,请重新选择模板后编辑!");
			}
			/**保存图片**/
			MultipartFile image_01 = multipartRequest.getFile("image_01");
			if(StringUtil.isMultiFlag(image_01)){
				//保存图片
				modul11Entity.setImage01(ModulImageSave.saveMoulImage(image_01,modul11Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul11EntityHistory.getImage01());
			}
			MultipartFile image_02 = multipartRequest.getFile("image_02");
			if(StringUtil.isMultiFlag(image_02)){
				//保存图片
				modul11Entity.setImage02(ModulImageSave.saveMoulImage(image_02,modul11Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul11EntityHistory.getImage02());
			}
			MultipartFile image_03 = multipartRequest.getFile("image_03");
			if(StringUtil.isMultiFlag(image_03)){
				//保存图片
				modul11Entity.setImage03(ModulImageSave.saveMoulImage(image_03,modul11Entity.getId(),modulCode.trim()));
				//删除原来的图片
				ConfigUtil.deleteFile(modul11EntityHistory.getImage03());
			}
			
			//更新
			modul11Dao.update(modul11Entity);
			return R.ok();
		}
		
		return R.error("获取不到模板信息,请重新选择模板后编辑!");
	}

}
