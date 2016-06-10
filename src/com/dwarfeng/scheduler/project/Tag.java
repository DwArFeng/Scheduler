package com.dwarfeng.scheduler.project;

import com.dwarfeng.func.interfaces.Describeable;
import com.dwarfeng.func.interfaces.Nameable;

//FIXME ��дhashCode������
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
	 * @param name
	 */
	public void setName(String name){
		this.name = name == null ? "" : name;
	}
	@Override
	public String getDescribe() {
		return this.describe;
	}
	/**
	 * ����Tag��������
	 * @param describe
	 */
	public void setDescribe(String describe){
		this.describe = describe == null ? "" : describe;
	}
	@Override
	public boolean equals(Object o){
		if(o == this) return true;
		if(o == null) return false;
		if(!(o instanceof Tag)) return false;
		return ((Tag)o).getName().equals(this.getName());
	}
	
	@Override
	public int hashCode(){
		return getName().hashCode()*17;
	}
	
	@Override
	public String toString(){
		return "��ǩ��" + getName() + "��������" + getDescribe();
	}
}
