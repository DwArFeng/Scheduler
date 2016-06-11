package com.dwarfeng.scheduler.project;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;

import com.dwarfeng.scheduler.io.Scpath;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.Scpathable;

/**
 * 项目类。
 * <p>该类是整个管理程序的基础文件。
 * @author DwArFeng
 * @since 1.8
 */
public final class Project extends AbstractObjectInProjectTree{
	
	/**
	 * 工程对象的构造器。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		private TagMap tagMap = new TagMap();
		private NotebookCol notebookCol = new NotebookCol();
		private ImprovisedPlain improvisedPlain = new ImprovisedPlain();
		
		/**
		 * 生成一个默认的工程对象构造器。
		 */
		public Productor(){
			//Do nothing
		}
		
		/**
		 * 定义工程对象的标签映射。
		 * @param tagMap 工程对象的标签映射。
		 * @return 构造器自身。
		 */
		public Productor tagMap(TagMap tagMap){
			if(tagMap != null) this.tagMap = tagMap;
			return this;
		}
		
		/**
		 * 定义工程对象的所有笔记本。
		 * @param notebookCol 工程对象的所有笔记本。
		 * @return 构造器自身。
		 */
		public Productor notebookCol(NotebookCol notebookCol){
			if(notebookCol != null) this.notebookCol = notebookCol;
			return this;
		}
		
		/**
		 * 定义工程对象的即兴计划。
		 * @param improvisedPlain 工程对象的即兴计划。
		 * @return 构造器自身。
		 */
		public Productor improvisedPlain(ImprovisedPlain improvisedPlain){
			if(improvisedPlain != null) this.improvisedPlain = improvisedPlain;
			return this;
		}
		
		/**
		 * 由工程对象构造器构造工程对象。
		 * @return 构造出的工程对象。
		 */
		public Project product(){
			return new Project(tagMap, notebookCol,improvisedPlain);
		}
	}
	
	/**
	 * 代表着工程对象中所有子节点的枚举。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static enum CHILD{
		
		/**代表所有笔记本的字段*/
		NOTEBOOK_COL(0),
		/**代表即兴计划的字段*/
		IMPROVISED_PLAIN(1);
		
		private int index;
		
		private CHILD(int index){
			this.index = index;
		}
		private int getIndex(){
			return this.index;
		}
	}

	/*工程类的写锁定*/
	//private boolean writeLocked;
	/**工程中的标签-ID映射*/
	private TagMap tagMap;
	
	private Project(TagMap tagMap,NotebookCol notebookCol,ImprovisedPlain improvisedPlain){
		//调用父类方法
		super(true);
		//初始化标签地图
		this.tagMap = tagMap;
		this.tagMap.setRootProject(this);
		//初始化全部笔记本
		insert((ObjectInProjectTree)notebookCol,CHILD.NOTEBOOK_COL.getIndex());
		//初始化即兴计划
		insert((ObjectInProjectTree)improvisedPlain,CHILD.IMPROVISED_PLAIN.getIndex());
		//TODO 随着功能的完善进行增加
	}


	/**
	 * 返回一个工程中的所有路径。
	 * <p> 返回的路径集合中可以确保不会出现null。
	 * @return 工程中的所有路径集合。
	 */
	public Set<Scpath> getScpaths(){
		Set<Scpath> scpaths = new HashSet<Scpath>();
		for(ObjectInProject objectInProject : getProjectItems()){
			if(objectInProject instanceof Scpathable){
				scpaths.add(((Scpathable) objectInProject).getScpath());
			}
		}
		return scpaths;
	}
	
	/**
	 * 返回工程中的标签映射。
	 * @return 返回的标签映射。
	 */
	public TagMap getTagMap(){
		return this.tagMap;
	}
	
	/**
	 * 返回能够被工程识别的包括工程自身的所有事物。
	 * @return 工程中能够识别的所有事物。
	 */
	public Set<ObjectInProject> getProjectItems(){
		Set<ObjectInProject> pi = new HashSet<ObjectInProject>();
		for(
				Enumeration<?> enu = breadthFirstEnumeration();
				enu.hasMoreElements();
				//no expression
		){
			ObjectInProjectTree obj = (ObjectInProjectTree) enu.nextElement();
			pi.add(obj);
			if(obj.getOtherObjectInProjects() != null) pi.addAll(obj.getOtherObjectInProjects());
		}
		return pi;
	}

	/**
	 * 返回工程对象的一个子节点。
	 * @param child 子节点的枚举。
	 * @return 指定的子节点。
	 */
	public ObjectInProject getChildFrom(CHILD child){
		return getChildAt(child.getIndex());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#getOtherObjectInProjects()
	 */
	@Override
	public Set<ObjectInProject> getOtherObjectInProjects() {
		Set<ObjectInProject> set = new HashSet<ObjectInProject>();
		set.add(tagMap);
		return set;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#renderLabel(javax.swing.JLabel)
	 */
	@Override
	public void renderLabel(JLabel label) {
		//Do nothing
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}