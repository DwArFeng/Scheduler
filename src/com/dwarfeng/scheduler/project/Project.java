package com.dwarfeng.scheduler.project;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import com.dwarfeng.func.io.CT;
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
@SuppressWarnings("unused")
public final class Project extends AbstractObjectInProjectTree{
	
	private static final long serialVersionUID = 6999230758242017223L;

	/**所有笔记树的序列值*/
	public final static int NOTEBOOKCOL_INDEX = 0;

	/**工程类的写锁定*/
	//private boolean writeLocked;
	/**工程中的标签-ID映射*/
	private TagMap tagMap;
	
	/**
	 * 生成一个默认的工程。
	 * <p> 默认的工程没有任何的内容。
	 * <p> 该方法经常在新建工程文件的时候使用。
	 */
	public Project() {
		this(null,null);
	}
	
	/**
	 * 生成一个具有指定的Tag-ID映射表，指定的笔记本合集的工程实例。
	 * @param tagMap 指定的Tag-ID映射表。
	 * @param notebookCol 指定的笔记本合集。
	 */
	public Project(TagMap tagMap,NotebookCol notebookCol){
		//调用父类方法
		super(true);
		//实现入口处的成员变量。
		this.tagMap = tagMap == null ? new TagMap() : tagMap;
		this.tagMap.setRootProject(this);
		//初始化全部笔记本选项。
		NotebookCol notebookCol2 = new NotebookCol();
		if(notebookCol != null) notebookCol2 = notebookCol;
		insert((ObjectInProjectTree)notebookCol2,NOTEBOOKCOL_INDEX);
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
	 * 
	 * @return
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}