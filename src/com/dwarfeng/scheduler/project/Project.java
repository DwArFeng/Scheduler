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
 * ��Ŀ�ࡣ
 * <p>�����������������Ļ����ļ���
 * @author DwArFeng
 * @since 1.8
 */
@SuppressWarnings("unused")
public final class Project extends AbstractObjectInProjectTree{
	
	private static final long serialVersionUID = 6999230758242017223L;

	/**���бʼ���������ֵ*/
	public final static int NOTEBOOKCOL_INDEX = 0;

	/**�������д����*/
	//private boolean writeLocked;
	/**�����еı�ǩ-IDӳ��*/
	private TagMap tagMap;
	
	/**
	 * ����һ��Ĭ�ϵĹ��̡�
	 * <p> Ĭ�ϵĹ���û���κε����ݡ�
	 * <p> �÷����������½������ļ���ʱ��ʹ�á�
	 */
	public Project() {
		this(null,null);
	}
	
	/**
	 * ����һ������ָ����Tag-IDӳ���ָ���ıʼǱ��ϼ��Ĺ���ʵ����
	 * @param tagMap ָ����Tag-IDӳ���
	 * @param notebookCol ָ���ıʼǱ��ϼ���
	 */
	public Project(TagMap tagMap,NotebookCol notebookCol){
		//���ø��෽��
		super(true);
		//ʵ����ڴ��ĳ�Ա������
		this.tagMap = tagMap == null ? new TagMap() : tagMap;
		this.tagMap.setRootProject(this);
		//��ʼ��ȫ���ʼǱ�ѡ�
		NotebookCol notebookCol2 = new NotebookCol();
		if(notebookCol != null) notebookCol2 = notebookCol;
		insert((ObjectInProjectTree)notebookCol2,NOTEBOOKCOL_INDEX);
		//TODO ���Ź��ܵ����ƽ�������
	}


	/**
	 * ����һ�������е�����·����
	 * <p> ���ص�·�������п���ȷ���������null��
	 * @return �����е�����·�����ϡ�
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
	 * �����ܹ�������ʶ��İ�������������������
	 * @return �������ܹ�ʶ����������
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