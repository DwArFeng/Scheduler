package com.dwarfeng.scheduler.io;

/**
 * �ƻ����������ļ���λ��ʽ���ļ�����/�ָ���
 * <p>�����������ֲ�ͬ�ļ������λ�á���·����һ����·����ָʾ���ǹ����е��ļ�����ڹ���Ŀ¼���ǹ��̰��е�λ�á�
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
