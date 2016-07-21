package com.dwarfeng.scheduler.project;

import com.dwarfeng.dwarffunction.interfaces.Describeable;
import com.dwarfeng.dwarffunction.interfaces.Nameable;

/**
 * 标签类，记录着标签以及定义标签的方法。
 * @author DwArFeng
 * @since 1.8
 */
public class Tag implements Nameable,Describeable{
	/**标签默认的名字*/
	public final static String DEFAULT_NAME = "未命名";
	
	private String name;
	private String describe;

	/**
	 * 生成一个默认的tag。
	 */
	public Tag(){
		this(DEFAULT_NAME,"");
	}
	/**
	 * 生成一个具有指定的名称，指定的描述的tag。
	 * @param name 指定的名称。
	 * @param describe 指定的描述。
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
	 * 设置Tag的名称。
	 * @param name Tag的名称。
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
	 * 设置Tag的描述。
	 * @param describe Tag的描述。
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
		return "标签：" + getName() + "，描述：" + getDescribe();
	}
}
