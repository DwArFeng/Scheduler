package com.dwarfeng.scheduler.project;

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
import com.dwarfeng.scheduler.tools.PopupMenuActions;
import com.dwarfeng.scheduler.tools.UserInput;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectOutProjectTree;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;
import com.dwarfeng.scheduler.typedef.funcint.SerialParam;

/**
 * 笔记本集合。
 * <p> 笔记本集合是程序中笔记功能的子功能之一，程序中的所有笔记都被放置在这个节点之下。
 * @author DwArFeng
 * @since 1.8
 */
public final class NotebookCol extends AbstractObjectInProjectTree implements PopupInTree{
	
	/**
	 * 笔记本集合的构造者类。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private List<Notebook> notebooks = new ArrayList<Notebook>();
		
		/**
		 * 生成一个新的笔记本构造器。
		 */
		public Productor(){}
		
		/**
		 * 指定该笔记本集合的笔记本列表。
		 * @param val 笔记本列表，如果为<code>null</code>，则将列表清空。
		 * @return 构造器自身。
		 */
		public Productor notebooks(List<Notebook> val){
			if(val == null){
				this.notebooks = new ArrayList<Notebook>();
			}else{
				this.notebooks = val;
			}
			return this;
		}
		
		/**
		 * 通过构造器构造笔记本集合。
		 * @return 构造的笔记本集合。
		 */
		public NotebookCol product(){
			return new NotebookCol(notebooks);
		}
		
	}
	/**
	 * 生成一个具有指定笔记本的笔记本集合。
	 * <p> 笔记本是有序的。
	 * @param notebooks 笔记本列表。
	 */
	private NotebookCol(List<Notebook> notebooks){
		super(true);
		//将notebooks中的所有notebook添加进树中
		if(notebooks != null){
			for(Notebook notebook : notebooks){
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
				Notebook notebook = new Notebook.Productor().serialParam(sp).product();
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
	protected boolean canInsert(ObjectInProjectTree newChild) {
		return newChild instanceof Notebook;
	}
}
