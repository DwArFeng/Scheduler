package com.dwarfeng.scheduler.io;

/**
 * 计划管理程序的文件定位格式，文件夹用/分隔。
 * <p>该类用于区分不同文件的相对位置。该路径是一个子路径，指示的是工程中的文件相对于工作目录或是工程包中的位置。
 * @author DwArFeng
 * @since 1.8
 */
public class Scpath{
	
	private String fileName;
	
	/**
	 * 
	 * @param fileName
	 */
	public Scpath(String fileName) {
		this.fileName = fileName;
	}
	
	public String getPathName(){
		return this.fileName;
	}
	
	@Override
	public boolean equals(Object object){
		if(object == this) return true;
		if(object == null) return false;
		if(!(object instanceof Scpath)) return false;
		return ((Scpath) object).getPathName().equals(fileName);
	}
	
	@Override
	public int hashCode(){
		return fileName.hashCode()*17;
	}
	
	@Override
	public String toString(){
		return this.getPathName();
	}

}
