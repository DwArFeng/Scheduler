package com.dwarfeng.scheduler.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.dwarfeng.scheduler.core.Scheduler;
import com.dwarfeng.scheduler.project.Note;
import com.dwarfeng.scheduler.project.Notebook;
import com.dwarfeng.scheduler.project.NotebookCol;
import com.dwarfeng.scheduler.project.PlainNote;
import com.dwarfeng.scheduler.project.Project;
import com.dwarfeng.scheduler.project.RTFNote;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.funcint.Deleteable;
import com.dwarfeng.scheduler.typedef.funcint.Moveable;

public class JProjectTree extends JTree {
	
	private static final long serialVersionUID = -3100586083000288039L;
	
	private SchedulerGui mainFrame;
	private Project project;

	public JProjectTree() {
		super();
		init();
	}

//	public JProjectTree(Object[] value) {
//		super(value);
//		// TODO Auto-generated constructor stub
//	}
//
//	public JProjectTree(Vector<?> value) {
//		super(value);
//		// TODO Auto-generated constructor stub
//	}
//
//	public JProjectTree(Hashtable<?, ?> value) {
//		super(value);
//		// TODO Auto-generated constructor stub
//	}
//
//	public JProjectTree(TreeNode root) {
//		super(root);
//		// TODO Auto-generated constructor stub
//	}
//
//	public JProjectTree(TreeModel newModel) {
//		super(newModel);
//		// TODO Auto-generated constructor stub
//	}
//
//	public JProjectTree(TreeNode root, boolean asksAllowsChildren) {
//		super(root, asksAllowsChildren);
//		// TODO Auto-generated constructor stub
//	}


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
	 * ��������չ����ָ���Ľڵ㡣
	 * <p> �÷�����һ·չ���ýڵ�����и��ڵ㣬���չ������
	 * @param node ��Ҫչ�����Ľڵ㡣
	 */
	public void expandPath(ObjectInProjectTree node){
		for(
				Enumeration<ObjectInProjectTree> enu = new PathBetweenNodesEnumeration(node.getRootProject(),node);
				enu.hasMoreElements();
				//no expression
		){
			ObjectInProjectTree treeNode = enu.nextElement();
			TreePath treePath = new TreePath(getPath2Root(treeNode));
			expandPath(treePath);
			//�������������һ�����������Ҫȷ�����������ʾ�ں��ʵ�λ�á�
			if(!enu.hasMoreElements()) scrollPathToVisible(treePath);
		}
	}
	
	/**
	 * ���ظù���������ָ��Ĺ��̡�
	 * @return
	 */
	public Project getProject(){
		return this.project;
	}
	
	private ObjectInProjectTree[] getPath2Root(ObjectInProjectTree objectInProjectTree){
        List<ObjectInProjectTree> list = new ArrayList<ObjectInProjectTree>();
        for(
        		Enumeration<ObjectInProjectTree> enu = 
        				new PathBetweenNodesEnumeration(objectInProjectTree.getRootProject(), objectInProjectTree);
        		enu.hasMoreElements();
        		//no expression
        ){
        	list.add(enu.nextElement());
        }
        return list.toArray(new ObjectInProjectTree[0]);
	}
	
	/**
	 * ���ù�������ָ��Ĺ��̡�
	 * <p> ����ָ���Ĺ����Ƿ��ԭ������ȣ����ô˷���������¹�������ģ�͡�
	 * @param project ָ���Ĺ��̡�
	 */
	public void setProject(Project project){
		if(project != this.project){
			this.project = project;
		}
		repaintTreeModel();
		repaint();
	}
	
	public void repaintTreeModel(){
		setModel(new DefaultTreeModel(project));
	}
	
	/**
	 * ��ʼ��������
	 */
	private void init() {
		//������������
		setCellRenderer(new ProjectTreeRender());
		
		//��ӿ�ݼ�
		InputMap inputMap = getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap actionMap = getActionMap();
		
		//���ɾ����Delete��ݼ�
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "del");
		actionMap.put("del", new AbstractAction() {
			private static final long serialVersionUID = -1378680131920293448L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getSelectionPath() == null || getSelectionPath().getLastPathComponent() == null) return;
				
				Deleteable obj;
				try{
					obj = (Deleteable) getSelectionPath().getLastPathComponent();
				}catch(ClassCastException exception){
					return;
				}
				ObjectInProjectTree parent = obj.getParent();
				Scheduler.getInstance().requestDelete(obj);
				repaintTreeModel();
				JProjectTree.this.expandPath(parent);
			}
		});
		//��������Լ����ƵĿ�ݼ�
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,InputEvent.CTRL_MASK),"mup");
		actionMap.put("mup", new AbstractAction() {
			private static final long serialVersionUID = 605550244921615505L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getSelectionPath() == null || getSelectionPath().getLastPathComponent() == null) return;

				Moveable obj;
				try{
					obj = (Moveable) getSelectionPath().getLastPathComponent();
				}catch(ClassCastException exception){
					return;
				}
				Scheduler.getInstance().moveUp(obj);
				JProjectTree.this.repaintTreeModel();
				JProjectTree.this.expandPath(obj);
				JProjectTree.this.setSelectionPath(new TreePath(getPath2Root(obj)));
			}
		});
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,InputEvent.CTRL_MASK),"mdn");
		actionMap.put("mdn", new AbstractAction() {
			private static final long serialVersionUID = -3146120382154775628L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getSelectionPath() == null || getSelectionPath().getLastPathComponent() == null) return;

				Moveable obj;
				try{
					obj = (Moveable) getSelectionPath().getLastPathComponent();
				}catch(ClassCastException exception){
					return;
				}
				Scheduler.getInstance().moveDown(obj);
				JProjectTree.this.repaintTreeModel();
				JProjectTree.this.expandPath(obj);
				JProjectTree.this.setSelectionPath(new TreePath(getPath2Root(obj)));
			}
		});
	}
}









class PathBetweenNodesEnumeration implements Enumeration<ObjectInProjectTree>{
	
	  protected Stack<ObjectInProjectTree> stack;
	  
	  public PathBetweenNodesEnumeration(ObjectInProjectTree ancestor,ObjectInProjectTree descendant) {
		  super();

          if (ancestor == null || descendant == null) {
              throw new IllegalArgumentException("argument is null");
          }

          ObjectInProjectTree current;

          stack = new Stack<ObjectInProjectTree>();
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
	public ObjectInProjectTree nextElement() {
		 try {
             return stack.pop();
         } catch (EmptyStackException e) {
             throw new NoSuchElementException("No more elements");
         }
	}
	
}















class ProjectTreeRender extends DefaultTreeCellRenderer{
	
	private static final long serialVersionUID = -7941207932391169324L;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		this.setIconTextGap(8);
		if(value instanceof NotebookCol){
			this.setIcon(new ImageIcon(Scheduler.class.getResource("/resource/tree/notebookCollection.png")));
			this.setFont(new Font("SansSerif", Font.BOLD, 14));
			this.setText("���бʼǱ�");
		}
		if(value instanceof Notebook){
			this.setIcon(new ImageIcon(Scheduler.class.getResource("/resource/tree/notebook.png")));
			this.setFont(new Font("SansSerif", Font.PLAIN, 12));
			this.setText((String) ((Notebook) value).getParam(Notebook.NAME));
		}
		if(value instanceof RTFNote){
			this.setIcon(new ImageIcon(Scheduler.class.getResource("/resource/tree/rtfNote.png")));
			this.setFont(new Font("SansSerif", Font.PLAIN, 12));
			this.setText((String) ((Note) value).getParam(Note.NAME));
		}
		if(value instanceof PlainNote){
			this.setIcon(new ImageIcon(Scheduler.class.getResource("/resource/tree/plainNote.png")));
			this.setFont(new Font("SansSerif", Font.PLAIN, 12));
			this.setText((String) ((Note) value).getParam(Note.NAME));
		}
		return this;
	}
}
