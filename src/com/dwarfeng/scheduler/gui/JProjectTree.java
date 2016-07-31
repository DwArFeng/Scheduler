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
	 * ���ָ��������档
	 * @return ָ��������档
	 */
	public SchedulerGui getMainFrame(){
		return this.mainFrame;
	}
	
	/**
	 * ����������Ϊָ���������档
	 * @param mainFrame ָ���������档
	 */
	public void setMainFrame(SchedulerGui mainFrame){
		this.mainFrame = mainFrame;
	}
	
	/**
	 * ���ع�������ָʾ�Ĺ��̡�
	 * @return �������еĹ��̡�
	 */
	public Project getProject(){
		if(model == null || model.getRoot() == null) return null;
		return (Project) model.getRoot();
	}
	
	/**
	 * ���ù�������ָ��Ĺ��̡�
	 * <p> ����ָ���Ĺ����Ƿ��ԭ������ȣ����ô˷���������¹�������ģ�͡�
	 * @param project ָ���Ĺ��̡�
	 */
	public void setProject(Project project){
		this.model = new DefaultTreeModel(project);
		this.setModel(model);
	}
	
	/**
	 * ���ع�����ģ�͡�
	 * <p> �����ط����������ع�������ģ�ͣ����ѹ�����չ��������֮ǰ�������
	 * <br> �����ڲ���<code>selectNode</code>��Ϊ<code>null</code>�����ٽ�ָ����·��չ����ѡ�С�
	 * @param selectNode ��Ҫչ����ѡ�еĽڵ㣬����Ϊ<code>null</code>����ʾ��ѡ���κνڵ㡣
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
	 * ·����ö�١�
	 * <p> �÷���ժ��{@linkplain DefaultMutableTreeNode}��
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
	 * ��ʼ��������
	 */
	private void init() {
		//������������
		setCellRenderer(new ProjectTreeRender());
	}
}








/**
 * ������Ⱦ�������Ĺ�������Ⱦ�ࡣ
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
