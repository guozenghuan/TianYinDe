package heaven.sqlTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.framework.dao.ModulDao;
import com.framework.dao.TcUserDao;
import com.framework.dao.TyInvitationToinviteDao;
import com.framework.entity.Modul01Entity;
import com.framework.entity.Modul02Entity;
import com.framework.entity.ModulEntity;
import com.framework.entity.TsUserEntity;
import com.framework.entity.TyInvitationToinviteEntity;
import com.framework.utils.ConfigUtil;
import com.framework.utils.R;
import com.framework.utils.StringUtil;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-jdbc.xml" })
public class SqlTest {
	
	@Autowired
	private TcUserDao tcUserDao;
	
	@Autowired
	private TyInvitationToinviteDao tyInvitationToinviteDao;
	
	@Autowired
	private ModulDao modulDao;
	
	@Test
	public void testOne(){
		ModulEntity modulEntity = modulDao.queryObject(39L);
		System.out.println(JSONObject.toJSON(modulEntity));
		if(StringUtil.isEmpty(modulEntity)){
			System.out.println("1获取不到模板信息,请重新加载!");
		}
		//删除主模板中的信息
		String codeAndId = "modul_01=14;";
		if(!StringUtil.isIndexOfStr(modulEntity.getModulNameAndId(), codeAndId)){
			System.out.println("2获取不到模板信息,请重新加载!");
		}
	}
	
	@Test
	public void testTwo(){
		int score = 6;
		if(score != 1 && score != 2 && score != 3 && score != 4 && score != 5){
			System.out.println("评分格式错误");
		}
	}
	
	@Test
	public void modulCodeTest(){
		/*String modulCode = "Modul#size#Entity modul#size#Entity = new Modul#size#Entity();modul#size#Entity.setModulPath(\"/statics/modul/\"+modulCode+\".html\");modul#size#Entity.setModulType(\"system\");modul#size#Dao.save(modul#size#Entity);";
		
		for(int i=0;i<12;i++){
			String size = "";
			if(i<10){
				size = "0"+i;
			}else {
				size = ""+i;
			}
			System.out.println("====="+i);
			System.err.println(modulCode.replace("#size#", size));
		}*/
		
		/*String modulCode = "Modul#size#Entity modul#size#Entity = modul#size#Dao.queryObject(modulId);moduleData = modul#size#Entity.getModulPath()+\"?\"+\"seetingBtn=\"+seetingBtn+\"&type=\"+type+\"&id=\"+modul#size#Entity.getId();";
		
		for(int i=2;i<12;i++){
			String size = "";
			if(i<10){
				size = "0"+i;
			}else {
				size = ""+i;
			}
			System.out.println("====="+i);
			System.err.println(modulCode.replace("#size#", size));
		}*/
		
//		String modulCode = "//转换成实体类\n"+
//			"Modul#size#Entity modul#size#Entity = JSONObject.toJavaObject(JSON.parseObject(request.getParameter(\"entity\")), Modul#size#Entity.class);\n"+
//			"if(StringUtil.isEmpty(modul#size#Entity) || modul#size#Entity.getId() == null){\n"+
//			"	return R.error(\"请填写模板信息!\");\n"+
//			"}\n"+
//			"//获取原来的信息\n"+
//			"Modul#size#Entity modul#size#EntityHistory = modul#size#Dao.queryObject(modul#size#Entity.getId());\n"+
//			"if(StringUtil.isEmpty(modul#size#EntityHistory)){\n"+
//			"	return R.error(\"获取不到模板信息,请重新选择模板后编辑!\");\n"+
//			"}\n"+
//			"/**保存图片**/\n"+
//			"MultipartFile image_01 = multipartRequest.getFile(\"image_01\");\n"+
//			"if(image_01!=null && image_01.getContentType().equals(\"image/jpeg\") || image_01.getContentType().equals(\"image/jpg\") || image_01.getContentType().equals(\"image/png\")){\n"+
//			"	//保存图片\n"+
//			"	modul#size#Entity.setImage01(ModulImageSave.saveMoulImage(image_01,modul#size#Entity.getId()));\n"+
//			"	//删除原来的图片\n"+
//			"	ConfigUtil.deleteFile(modul#size#EntityHistory.getImage01());\n"+
//			"}\n"+
//			"//更新\n"+
//			"modul#size#Dao.update(modul#size#Entity);\n"+
//			"return R.ok();\n";
//		
//		for(int i=2;i<12;i++){
//			String size = "";
//			if(i<10){
//				size = "0"+i;
//			}else {
//				size = ""+i;
//			}
//			System.out.println("====="+i);
//			System.out.println(modulCode.replace("#size#", size));
//		}
		
		String modulCode = "modul#size#Entity.setId(null);\n"+
							"modul#size#Entity.setModulType(\"user\");\n"+
							"modul#size#Dao.save(modul#size#Entity);\n"+
							"modul#size#Entity.setImage01(ConfigUtil.copyModulImagesByUser(modul#size#Entity.getId(), \"modul_#size#\", modul#size#Entity.getImage#size#()));\n"+
							"modul#size#Dao.update(modul#size#Entity);\n"+
							"userModulNameAndId = userModulNameAndId+\"modul_#size#=\"+modul#size#Entity.getId()+\";\";\n";
		
		for(int i=2;i<12;i++){
		String size = "";
		if(i<10){
			size = "0"+i;
		}else {
			size = ""+i;
		}
		System.out.println("====="+i);
		System.out.println(modulCode.replace("#size#", size));
	}
		
	}
}
