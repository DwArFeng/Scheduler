package com.dwarfeng.scheduler.gui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.dwarfeng.scheduler.module.PProjectTreeNode;
import com.dwarfeng.scheduler.module.project.Project;

public class JProjectTree extends JTree {
	
	private static final long serialVersionUID = -3921593828136775074L;
	
	private SchedulerGui mainFrame;
	private DefaultTreeModel model;

	public JProjectTree() {
		super();
		init();
	}
	
	/**
	 * 获得指向的主界面。
	 * @return 指向的主界面。
	 */
	public SchedulerGui getMainFrame(){
		return this.mainFrame;
	}
	
	/**
	 * 设置主界面为指定的主界面。
	 * @param mainFrame 指定的主界面。
	 */
	public void setMainFrame(SchedulerGui mainFrame){
		this.mainFrame = mainFrame;
	}
	
	/**
	 * 返回工程树中指示的工程。
	 * @return 工程树中的工程。
	 */
	public Project getProject(){
		if(model == null || model.getRoot() == null) return null;
		return (Project) model.getRoot();
	}
	
	/**
	 * 设置工程树所指向的工程。
	 * <p> 无论指定的工程是否和原工程相等，调用此方法都会更新工程树的模型。
	 * @param project 指定的工程。
	 */
	public void setProject(Project project){
		this.model = new DefaultTreeModel(project);
		this.setModel(model);
	}
	
	/**
	 * 重载工程树模型。
	 * <p> 该重载方法首先重载工程树的模型，并把工程树展开到更新之前的情况。
	 * <br> 如果入口参数<code>selectNode</code>不为<code>null</code>，则再将指定的路径展开并选中。
	 * @param selectNode 需要展开并选中的节点，可以为<code>null</code>，表示不选中任何节点。
	 */
	public void refresh(PProjectTreeNode selectNode){
		Enumeration<TreePath> tps = getExpandedDescendants(new TreePath(getProject()));
		this.model.reload();
		if(tps != null){
			while(tps.hasMoreElements()){
				expandPath(tps.nextElement());
			}
		}
		if(selectNode != null){
			TreePath tp = new TreePath(getPath2Root(selectNode));
			addSelectionPath(tp);
			scrollPathToVisible(tp);
		}
	}
	
	private PProjectTreeNode[] getPath2Root(PProjectTreeNode objectInProjectTree){
        List<PProjectTreeNode> list = new ArrayList<PProjectTreeNode>();
        for(
        		Enumeration<PProjectTreeNode> enu = 
        				new PathBetweenNodesEnumeration(objectInProjectTree.getRootProject(), objectInProjectTree);
        		enu.hasMoreElements();
        		//no expression
        ){
        	list.add(enu.nextElement());
        }
        return list.toArray(new PProjectTreeNode[0]);
	}
	
	/**
	 * 路径间枚举。
	 * <p> 该方法摘自{@linkplain DefaultMutableTreeNode}。
	 * @author DwArFeng
	 * @since 1.8
	 */
	private static class PathBetweenNodesEnumeration implements Enumeration<PProjectTreeNode>{
		
		  protected Stack<PProjectTreeNode> stack;
		  
		  public PathBetweenNodesEnumeration(PProjectTreeNode ancestor,PProjectTreeNode descendant) {
			  super();

	        if (ancestor == null || descendant == null) {
	            throw new IllegalArgumentException("argument is null");
	        }

	        PProjectTreeNode current;

	        stack = new Stack<PProjectTreeNode>();
	        stack.push(descendant);

	        current = descendant;
	        while (current != ancestor) {
	            current = current.getParent();
	            if (current == null && descendant != ancestor) {
	                throw new IllegalArgumentException("node " + ancestor +
	                            " is not an ancestor of " + descendant);
	            }
	            stack.push(current);
	        }
		 }
		
		@Override
		public boolean hasMoreElements() {
			return stack.size() > 0;
		}

		@Override
		public PProjectTreeNode nextElement() {
			 try {
	           return stack.pop();
	       } catch (EmptyStackException e) {
	           throw new NoSuchElementException("No more elements");
	       }
		}
		
	}
	
	
	/**
	 * 初始化方法。
	 */
	private void init() {
		//设置自身属性
		setCellRenderer(new ProjectTreeRender());
	}
}








/**
 * 负责渲染工程树的工程树渲染类。
 * @author DwArFeng
 * @since 1.8
 */
final class ProjectTreeRender extends DefaultTreeCellRenderer{
	
	private static final long serialVersionUID = -8326159993695572250L;

	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
	 */
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		if(value instanceof PProjectTreeNode) ((PProjectTreeNode) value).renderLabel(label);
		return label;
	}
}
