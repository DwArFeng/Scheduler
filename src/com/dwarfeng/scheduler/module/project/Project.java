package com.dwarfeng.scheduler.module.project;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Vector;

import javax.swing.JLabel;

import com.dwarfeng.scheduler.module.Scpath;
import com.dwarfeng.scheduler.module.project.abstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.module.project.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.module.project.abstruct.Scpathable;

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
		private PTagMap tagMap = new PTagMap();
		private PNotebookCol notebookCol = new PNotebookCol.Productor().product();
		private PImprovisedPlantCol improvisedPlain = new PImprovisedPlantCol.Productor().product();
		
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
		public Productor tagMap(PTagMap tagMap){
			if(tagMap != null) this.tagMap = tagMap;
			return this;
		}
		
		/**
		 * ���幤�̶�������бʼǱ���
		 * @param notebookCol ���̶�������бʼǱ���
		 * @return ����������
		 */
		public Productor notebookCol(PNotebookCol notebookCol){
			if(notebookCol != null) this.notebookCol = notebookCol;
			return this;
		}
		
		/**
		 * ���幤�̶���ļ��˼ƻ���
		 * @param improvisedPlain ���̶���ļ��˼ƻ���
		 * @return ����������
		 */
		public Productor improvisedPlain(PImprovisedPlantCol improvisedPlain){
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
	public enum ChildType{
		
		/**�������бʼǱ����ֶ�*/
		NOTEBOOKCOL(0,PNotebookCol.class),
		/**�����˼ƻ����ֶ�*/
		IMPROVISEDLANTCOL(1,PImprovisedPlantCol.class);
		
		private final int index;
		private final Class<? extends ProjectTreeNode> cls;
		
		private ChildType(int index,Class<? extends ProjectTreeNode> cls ){
			this.index = index;
			this.cls = cls;
		}
		private int getIndex(){
			return this.index;
		}
		private Class<? extends ProjectTreeNode> getCls(){
			return cls;
		}
	}

	/*�������д����*/
	//private boolean writeLocked;
	/**�����еı�ǩ-IDӳ��*/
	private PTagMap tagMap;
	
	private Project(PTagMap tagMap,PNotebookCol notebookCol,PImprovisedPlantCol improvisedPlain){
		//���ø��෽��
		super(true);
		//��ʼ����ǩ��ͼ
		this.tagMap = tagMap;
		this.tagMap.setContext(this);
		//��ʼ��ȫ���ʼǱ�
		insert((ProjectTreeNode)notebookCol,ChildType.NOTEBOOKCOL.getIndex());
		//��ʼ�����˼ƻ�
		insert((ProjectTreeNode)improvisedPlain,ChildType.IMPROVISEDLANTCOL.getIndex());
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
	public PTagMap getTagMap(){
		return this.tagMap;
	}
	
	/**
	 * �����ܹ�������ʶ��İ�������������������
	 * @return �������ܹ�ʶ����������
	 */
	public Set<ObjectInProject> getProjectItems(){
		Set<ObjectInProject> pi = new HashSet<ObjectInProject>();
		for(
				Enumeration<ProjectTreeNode> enu = breadthFirstEnumeration();
				enu.hasMoreElements();
				//no expression
		){
			ProjectTreeNode obj = enu.nextElement();
			pi.add(obj);
			if(obj.getObjectOutProjectTrees() != null){
				for(ObjectOutProjectTree opt : obj.getObjectOutProjectTrees()){
					pi.add(opt);
				}
			}
		}
		return pi;
	}

	private Enumeration<ProjectTreeNode> breadthFirstEnumeration() {
		 return new BreadthFirstEnumeration(this);
	}

	final class BreadthFirstEnumeration implements Enumeration<ProjectTreeNode> {
		protected Queue queue;
		
		public BreadthFirstEnumeration(ProjectTreeNode rootNode) {
			super();
			Vector<ProjectTreeNode> v = new Vector<ProjectTreeNode>(1);
			v.addElement(rootNode);     // PENDING: don't really need a vector
			queue = new Queue();
			queue.enqueue(v.elements());
		}
		
		
		@Override
		public boolean hasMoreElements() {
			return (!queue.isEmpty() && (queue.firstObject()).hasMoreElements());
		}

		@Override
		public ProjectTreeNode nextElement() {
			Enumeration<ProjectTreeNode> enumer = queue.firstObject();
			ProjectTreeNode node = enumer.nextElement();
			Enumeration<ProjectTreeNode> children = node.children();

			if (!enumer.hasMoreElements()) {
					queue.dequeue();
			}
			if (children.hasMoreElements()) {
					queue.enqueue(children);
			}
			return node;
		}

		// A simple queue with a linked list data structure.
		final class Queue {

			QNode head; // null if empty
			QNode tail;

			final class QNode {
				public Enumeration<ProjectTreeNode> object;
				public QNode next;   // null if end
				public QNode(Enumeration<ProjectTreeNode> object, QNode next) {
						this.object = object;
						this.next = next;
				}
			}

			public void enqueue(Enumeration<ProjectTreeNode> anObject) {
				if (head == null) {
					head = tail = new QNode(anObject, null);
				} else {
					tail.next = new QNode(anObject, null);
					tail = tail.next;
				}
			}

			public Object dequeue() {
				if (head == null) {
					throw new NoSuchElementException("No more elements");
				}
				Object retval = head.object;
				QNode oldHead = head;
				head = head.next;
				if (head == null) {
						tail = null;
				} else {
						oldHead.next = null;
				}
				return retval;
			}

			public Enumeration<ProjectTreeNode> firstObject() {
				if (head == null) {
					throw new NoSuchElementException("No more elements");
				}

				return head.object;
			}

			public boolean isEmpty() {
				return head == null;
			}

		} // End of class Queue


	}  // End of class BreadthFirstEnumeration
	   
	   
	   
	/**
	 * ��ö���з��ع��̶����һ���ӽڵ㡣
	 * @param child �ӽڵ������ö�١�
	 * @return ָ�����ӽڵ㡣
	 * @throws NullPointerException <code>child</code>Ϊ<code>null</code>��ʱ���׳����쳣��
	 */
	public ObjectInProject getChildFromEnum(ChildType child){
		if(child == null) throw new NullPointerException("Child can't be null");
		return getChildAt(child.getIndex());
	}
	
	/**
	 * �ӹ����з���ָ������ӽڵ㡣
	 * @param type ָ�����ࡣ
	 * @param <T> ָ�����ࡣ
	 * @return ָ��������ӽڵ㡣
	 * @throws ClassNotFoundException ָ�����಻���κ�һ���ӽڵ���ࡣ
	 */
	public<T extends ProjectTreeNode> T getChildFromType(Class<T> type) throws ClassNotFoundException{
		if(type == null) throw new NullPointerException("Type can't be null");
		for(ChildType childType : ChildType.values()){
			if(type.equals(childType.getCls())){
				return type.cast(getChildFromEnum(childType));
			}
		}
		throw new ClassNotFoundException("Can't find class : " + type);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#getObjectOutProjectTrees()
	 */
	@Override
	public Set<ObjectOutProjectTree> getObjectOutProjectTrees(){
		Set<ObjectOutProjectTree> set = new HashSet<ObjectOutProjectTree>();
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

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#canInsert(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree)
	 */
	@Override
	protected boolean canInsert(ProjectTreeNode newChild) {
		for(ChildType childType : ChildType.values()){
			if(childType.cls.equals(newChild.getClass())) return true;
		}
		return false;
	}
	
}