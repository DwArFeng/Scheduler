package com.dwarfeng.scheduler.module;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import com.dwarfeng.scheduler.core.Scheduler133;
import com.dwarfeng.scheduler.module.project.abstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.module.project.abstruct.ObjectOutProjectTree;
import com.dwarfeng.scheduler.project.funcint.PopupInTree;
import com.dwarfeng.scheduler.project.funcint.SerialParam;
import com.dwarfeng.scheduler.tools.PopupMenuActions;
import com.dwarfeng.scheduler.tools.UserInput;

/**
 * 笔记本集合。
 * <p> 笔记本集合是程序中笔记功能的子功能之一，程序中的所有笔记都被放置在这个节点之下。
 * @author DwArFeng
 * @since 1.8
 */
final class PNotebookCol extends AbstractObjectInProjectTree implements PopupInTree{
	
	/**
	 * 笔记本集合的构造者类。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private List<PNotebook> notebooks = new ArrayList<PNotebook>();
		
		/**
		 * 生成一个新的笔记本构造器。
		 */
		public Productor(){}
		
		/**
		 * 指定该笔记本集合的笔记本列表。
		 * @param val 笔记本列表，如果为<code>null</code>，则将列表清空。
		 * @return 构造器自身。
		 */
		public Productor notebooks(List<PNotebook> val){
			if(val == null){
				this.notebooks = new ArrayList<PNotebook>();
			}else{
				this.notebooks = val;
			}
			return this;
		}
		
		/**
		 * 通过构造器构造笔记本集合。
		 * @return 构造的笔记本集合。
		 */
		public PNotebookCol product(){
			return new PNotebookCol(notebooks);
		}
		
	}
	/**
	 * 生成一个具有指定笔记本的笔记本集合。
	 * <p> 笔记本是有序的。
	 * @param notebooks 笔记本列表。
	 */
	private PNotebookCol(List<PNotebook> notebooks){
		super(true);
		//将notebooks中的所有notebook添加进树中
		if(notebooks != null){
			for(PNotebook notebook : notebooks){
				if(notebook != null) add(notebook);
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.PopupInTree#getJPopupMenu(com.dwarfeng.scheduler.gui.JProjectTree)
	 */
	@Override
	public JPopupMenu getJPopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		
		popup.add(PopupMenuActions.newNewItem("新建一个笔记本", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//获取线性参数，并通过判断线性参数是否为null来判断是否按下了取消键。
				SerialParam sp = UserInput.getSerialParam("新笔记本", null);
				if(sp == null) return;
				//构造新的笔记并设置参数
				PNotebook notebook = new PNotebook.Productor().serialParam(sp).product();
				//向工程树中的指定笔记本集添加此笔记本
				add(notebook);
				//更新界面
				Scheduler133.getInstance().refreshProjectTrees(getRootProject(), notebook);
			}
		}));
		
		return popup;
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#getObjectOutProjectTrees()
	 */
	@Override
	public Set<ObjectOutProjectTree> getObjectOutProjectTrees(){
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#renderLabel(javax.swing.JLabel)
	 */
	@Override
	public void renderLabel(JLabel label) {
		label.setIconTextGap(8);
		label.setIcon(new ImageIcon(Scheduler133.class.getResource("/resource/tree/notebookCollection.png")));
		label.setFont(new Font("SansSerif", Font.BOLD, 14));
		label.setText("所有笔记本");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#canInsert(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree)
	 */
	@Override
	protected boolean canInsert(PProjectTreeNode newChild) {
		return newChild instanceof PNotebook;
	}
}
