package com.framework.entity;

/**
 * 文件保存
 * @author Administrator
 *
 */
public class SaveFileEntity {

	
	
	//保存是否成功
	private Boolean flag;
	
	//返还文件的路径
	private String path;

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public SaveFileEntity(Boolean flag, String path) {
		super();
		this.flag = flag;
		this.path = path;
	}
	
	
	
}
