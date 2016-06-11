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
 * ��Ŀ�ࡣ
 * <p>�����������������Ļ����ļ���
 * @author DwArFeng
 * @since 1.8
 */
public final class Project extends AbstractObjectInProjectTree{
	
	/**
	 * ���̶���Ĺ�������
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		private TagMap tagMap = new TagMap();
		private NotebookCol notebookCol = new NotebookCol();
		private ImprovisedPlain improvisedPlain = new ImprovisedPlain();
		
		/**
		 * ����һ��Ĭ�ϵĹ��̶���������
		 */
		public Productor(){
			//Do nothing
		}
		
		/**
		 * ���幤�̶���ı�ǩӳ�䡣
		 * @param tagMap ���̶���ı�ǩӳ�䡣
		 * @return ����������
		 */
		public Productor tagMap(TagMap tagMap){
			if(tagMap != null) this.tagMap = tagMap;
			return this;
		}
		
		/**
		 * ���幤�̶�������бʼǱ���
		 * @param notebookCol ���̶�������бʼǱ���
		 * @return ����������
		 */
		public Productor notebookCol(NotebookCol notebookCol){
			if(notebookCol != null) this.notebookCol = notebookCol;
			return this;
		}
		
		/**
		 * ���幤�̶���ļ��˼ƻ���
		 * @param improvisedPlain ���̶���ļ��˼ƻ���
		 * @return ����������
		 */
		public Productor improvisedPlain(ImprovisedPlain improvisedPlain){
			if(improvisedPlain != null) this.improvisedPlain = improvisedPlain;
			return this;
		}
		
		/**
		 * �ɹ��̶����������칤�̶���
		 * @return ������Ĺ��̶���
		 */
		public Project product(){
			return new Project(tagMap, notebookCol,improvisedPlain);
		}
	}
	
	/**
	 * �����Ź��̶����������ӽڵ��ö�١�
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static enum CHILD{
		
		/**�������бʼǱ����ֶ�*/
		NOTEBOOK_COL(0),
		/**�����˼ƻ����ֶ�*/
		IMPROVISED_PLAIN(1);
		
		private int index;
		
		private CHILD(int index){
			this.index = index;
		}
		private int getIndex(){
			return this.index;
		}
	}

	/*�������д����*/
	//private boolean writeLocked;
	/**�����еı�ǩ-IDӳ��*/
	private TagMap tagMap;
	
	private Project(TagMap tagMap,NotebookCol notebookCol,ImprovisedPlain improvisedPlain){
		//���ø��෽��
		super(true);
		//��ʼ����ǩ��ͼ
		this.tagMap = tagMap;
		this.tagMap.setRootProject(this);
		//��ʼ��ȫ���ʼǱ�
		insert((ObjectInProjectTree)notebookCol,CHILD.NOTEBOOK_COL.getIndex());
		//��ʼ�����˼ƻ�
		insert((ObjectInProjectTree)improvisedPlain,CHILD.IMPROVISED_PLAIN.getIndex());
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
	 * ���ع����еı�ǩӳ�䡣
	 * @return ���صı�ǩӳ�䡣
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

	/**
	 * ���ع��̶����һ���ӽڵ㡣
	 * @param child �ӽڵ��ö�١�
	 * @return ָ�����ӽڵ㡣
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