package com.dwarfeng.scheduler.module;

/**
 * �ƻ����������ļ���λ��ʽ���ļ�����/�ָ���
 * <p>�����������ֲ�ͬ�ļ������λ�á���·����һ����·����ָʾ���ǹ����е��ļ�����ڹ���Ŀ¼���ǹ��̰��е�λ�á�
 * @author DwArFeng
 * @since 1.8
 */
public final class Scpath{
	
	private final String fileName;
	
	/**
	 * ����һ��ָ���Ĺ���·����
	 * <p> ��ʾĳ��·���Ӹù��̵Ĺ���Ŀ¼��ʼָ���ļ������·����
	 * @param fileName ����·����·����
	 */
	public Scpath(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * ���ظù���·����·����
	 * @return ·����
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
