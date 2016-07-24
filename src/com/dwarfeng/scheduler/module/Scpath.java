package com.dwarfeng.scheduler.module;

/**
 * 计划管理程序的文件定位格式，文件夹用/分隔。
 * <p>该类用于区分不同文件的相对位置。该路径是一个子路径，指示的是工程中的文件相对于工作目录或是工程包中的位置。
 * @author DwArFeng
 * @since 1.8
 */
public final class Scpath{
	
	private final String fileName;
	
	/**
	 * 生成一个指定的工程路径。
	 * <p> 表示某个路径从该工程的工作目录开始指向文件的相对路径。
	 * @param fileName 工程路径的路径。
	 */
	public Scpath(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * 返回该工程路径的路径。
	 * @return 路径。
	 */
	public String getPathName(){
		return this.fileName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object){
		if(object == this) return true;
		if(object == null) return false;
		if(!(object instanceof Scpath)) return false;
		return ((Scpath) object).getPathName().equals(fileName);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		return fileName.hashCode()*17;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return this.getPathName();
	}

}
