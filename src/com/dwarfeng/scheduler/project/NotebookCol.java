package com.dwarfeng.scheduler.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Set;

import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.tree.MutableTreeNode;

import com.dwarfeng.func.gui.JMenuItemAction;
import com.dwarfeng.scheduler.core.Scheduler;
import com.dwarfeng.scheduler.gui.JNTDSettingDialog;
import com.dwarfeng.scheduler.gui.JProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;

/**
 * 笔记本集合。
 * <p> 笔记本集合是程序中笔记功能的子功能之一，程序中的所有笔记都被放置在这个节点之下。
 * @author DwArFeng
 * @since 1.8
 */
public class NotebookCol extends AbstractObjectInProjectTree implements PopupInTree{
	
	private static final long serialVersionUID = 5955486917051297791L;

	/**
	 * 生成一个默认的，不含有笔记本元素的笔记本集合。
	 */
	public NotebookCol(){
		this(null);
	}
	/**
	 * 生成一个具有指定笔记本的笔记本集合。
	 * <p> 笔记本是有序的。
	 * @param notebooks 笔记本列表。
	 */
	public NotebookCol(List<Notebook> notebooks){
		super(true);
		//将notebooks中的所有notebook添加进树中
		if(notebooks != null){
			for(Notebook notebook : notebooks){
				if(notebook != null) add(notebook);
			}
		}
	}
	@Override
	public void insert(MutableTreeNode newChild, int childIndex) {
		//只能插入笔记本
		if(!(newChild instanceof Notebook)) throw new IllegalArgumentException("New child must instance of Notebook");
		super.insert(newChild, childIndex);
	}
	
	@Override
	public JPopupMenu getJPopupMenu(JProjectTree jProjectTree) {
		JPopupMenu popup = new JPopupMenu();
		popup.add(new JMenuItemAction(
				null,																										//XXX 	不要忘记添加图标
				"新建", 
				"新建一个笔记本", 
				KeyStroke.getKeyStroke(KeyEvent.VK_N,0),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//打开名称和描述的设置面板。
						JNTDSettingDialog dialog = new JNTDSettingDialog(Scheduler.getInstance().getGui(),"新笔记本");
						dialog.setVisible(true);
						//获取名称，并通过判断名字是否为null来判断是否按下了取消键。
						String name = dialog.getName();
						if(name == null) return;
						String describe = dialog.getDescribe();
						//构造新的笔记并设置参数
						Notebook notebook = new Notebook();
						notebook.setParam(Notebook.NAME, name);
						notebook.setParam(Notebook.DESCRIBE, describe);
						//向工程树中的指定笔记本集添加此笔记本
						add(notebook);
						jProjectTree.repaintTreeModel();
						//展开到被新建的笔记本节点
						jProjectTree.expandPath(notebook);
					}
				}
		));
		
		return popup;
	}
	
	@Override
	public Set<ObjectInProject> getOtherObjectInProjects() {
		return null;
	}
}
