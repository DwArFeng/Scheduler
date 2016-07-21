package com.dwarfeng.scheduler.project;

import com.dwarfeng.dwarffunction.interfaces.Describeable;
import com.dwarfeng.dwarffunction.interfaces.Nameable;

/**
 * ��ǩ�࣬��¼�ű�ǩ�Լ������ǩ�ķ�����
 * @author DwArFeng
 * @since 1.8
 */
public class Tag implements Nameable,Describeable{
	/**��ǩĬ�ϵ�����*/
	public final static String DEFAULT_NAME = "δ����";
	
	private String name;
	private String describe;

	/**
	 * ����һ��Ĭ�ϵ�tag��
	 */
	public Tag(){
		this(DEFAULT_NAME,"");
	}
	/**
	 * ����һ������ָ�������ƣ�ָ����������tag��
	 * @param name ָ�������ơ�
	 * @param describe ָ����������
	 */
	public Tag(String name,String describe){
		setName(name);
		setDescribe(describe);
	}
	@Override
	public String getName() {
		return this.name;
	}
	/**
	 * ����Tag�����ơ�
	 * @param name Tag�����ơ�
	 */
	public void setName(String name){
		this.name = name == null ? "" : name;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.func.interfaces.Describeable#getDescribe()
	 */
	@Override
	public String getDescribe() {
		return this.describe;
	}
	
	/**
	 * ����Tag��������
	 * @param describe Tag��������
	 */
	public void setDescribe(String describe){
		this.describe = describe == null ? "" : describe;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o){
		if(o == this) return true;
		if(o == null) return false;
		if(!(o instanceof Tag)) return false;
		return ((Tag)o).getName().equals(this.getName());
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		return getName().hashCode()*17;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "��ǩ��" + getName() + "��������" + getDescribe();
	}
}
