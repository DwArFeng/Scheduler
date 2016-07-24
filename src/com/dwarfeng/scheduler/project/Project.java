package com.dwarfeng.scheduler.project;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Vector;

import javax.swing.JLabel;

import com.dwarfeng.scheduler.core.module.Scpath;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectOutProjectTree;
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
		private NotebookCol notebookCol = new NotebookCol.Productor().product();
		private ImprovisedPlantCol improvisedPlain = new ImprovisedPlantCol.Productor().product();
		
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
		public Productor improvisedPlain(ImprovisedPlantCol improvisedPlain){
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
	public enum ChildType{
		
		/**代表所有笔记本的字段*/
		NOTEBOOKCOL(0,NotebookCol.class),
		/**代表即兴计划的字段*/
		IMPROVISEDLANTCOL(1,ImprovisedPlantCol.class);
		
		private final int index;
		private final Class<? extends ObjectInProjectTree> cls;
		
		private ChildType(int index,Class<? extends ObjectInProjectTree> cls ){
			this.index = index;
			this.cls = cls;
		}
		private int getIndex(){
			return this.index;
		}
		private Class<? extends ObjectInProjectTree> getCls(){
			return cls;
		}
	}

	/*工程类的写锁定*/
	//private boolean writeLocked;
	/**工程中的标签-ID映射*/
	private TagMap tagMap;
	
	private Project(TagMap tagMap,NotebookCol notebookCol,ImprovisedPlantCol improvisedPlain){
		//调用父类方法
		super(true);
		//初始化标签地图
		this.tagMap = tagMap;
		this.tagMap.setContext(this);
		//初始化全部笔记本
		insert((ObjectInProjectTree)notebookCol,ChildType.NOTEBOOKCOL.getIndex());
		//初始化即兴计划
		insert((ObjectInProjectTree)improvisedPlain,ChildType.IMPROVISEDLANTCOL.getIndex());
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
				Enumeration<ObjectInProjectTree> enu = breadthFirstEnumeration();
				enu.hasMoreElements();
				//no expression
		){
			ObjectInProjectTree obj = enu.nextElement();
			pi.add(obj);
			if(obj.getObjectOutProjectTrees() != null){
				for(ObjectOutProjectTree opt : obj.getObjectOutProjectTrees()){
					pi.add(opt);
				}
			}
		}
		return pi;
	}

	private Enumeration<ObjectInProjectTree> breadthFirstEnumeration() {
		 return new BreadthFirstEnumeration(this);
	}

	final class BreadthFirstEnumeration implements Enumeration<ObjectInProjectTree> {
		protected Queue queue;
		
		public BreadthFirstEnumeration(ObjectInProjectTree rootNode) {
			super();
			Vector<ObjectInProjectTree> v = new Vector<ObjectInProjectTree>(1);
			v.addElement(rootNode);     // PENDING: don't really need a vector
			queue = new Queue();
			queue.enqueue(v.elements());
		}
		
		
		@Override
		public boolean hasMoreElements() {
			return (!queue.isEmpty() && (queue.firstObject()).hasMoreElements());
		}

		@Override
		public ObjectInProjectTree nextElement() {
			Enumeration<ObjectInProjectTree> enumer = queue.firstObject();
			ObjectInProjectTree node = enumer.nextElement();
			Enumeration<ObjectInProjectTree> children = node.children();

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
				public Enumeration<ObjectInProjectTree> object;
				public QNode next;   // null if end
				public QNode(Enumeration<ObjectInProjectTree> object, QNode next) {
						this.object = object;
						this.next = next;
				}
			}

			public void enqueue(Enumeration<ObjectInProjectTree> anObject) {
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

			public Enumeration<ObjectInProjectTree> firstObject() {
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
	 * 从枚举中返回工程对象的一个子节点。
	 * @param child 子节点的类型枚举。
	 * @return 指定的子节点。
	 * @throws NullPointerException <code>child</code>为<code>null</code>的时候抛出的异常。
	 */
	public ObjectInProject getChildFromEnum(ChildType child){
		if(child == null) throw new NullPointerException("Child can't be null");
		return getChildAt(child.getIndex());
	}
	
	/**
	 * 从工程中返回指定类的子节点。
	 * @param type 指定的类。
	 * @param <T> 指定的类。
	 * @return 指定的类的子节点。
	 * @throws ClassNotFoundException 指定的类不是任何一个子节点的类。
	 */
	public<T extends ObjectInProjectTree> T getChildFromType(Class<T> type) throws ClassNotFoundException{
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
	protected boolean canInsert(ObjectInProjectTree newChild) {
		for(ChildType childType : ChildType.values()){
			if(childType.cls.equals(newChild.getClass())) return true;
		}
		return false;
	}
	
}