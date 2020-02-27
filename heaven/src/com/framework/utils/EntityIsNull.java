package com.framework.utils;

import java.lang.reflect.Field;

/**
 * 实体类遍历是否有空值
 * @author Administrator
 *
 */
public class EntityIsNull {
	
	public static void isNulls(Object eObject){
		Class cls = eObject.getClass();
        Field[] fields = cls.getDeclaredFields(); 
        for(int i=0; i<fields.length; i++){
            Field f = fields[i];  
            f.setAccessible(true);  
            try {
				System.out.println("属性名:" + f.getName() + " 属性值:" + f.get(eObject));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}  
        } 
	}
	
}
