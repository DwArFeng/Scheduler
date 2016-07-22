package com.dwarfeng.scheduler.project;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import com.dwarfeng.dwarffunction.gui.JMenuItemAction;
import com.dwarfeng.scheduler.core.Scheduler133;
import com.dwarfeng.scheduler.io.ProjectIoHelper;
import com.dwarfeng.scheduler.tools.PopupMenuActions;
import com.dwarfeng.scheduler.tools.UserInput;
import com.dwarfeng.scheduler.typedef.funcint.Deleteable;
import com.dwarfeng.scheduler.typedef.funcint.Moveable;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;
import com.dwarfeng.scheduler.typedef.funcint.Searchable;
import com.dwarfeng.scheduler.typedef.funcint.SerialParam;
import com.dwarfeng.scheduler.typedef.funcint.SerialParamSetable;
import com.dwarfeng.scheduler.typedef.pabstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.pabstruct.ObjectInProject;
import com.dwarfeng.scheduler.typedef.pabstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.pabstruct.ObjectOutProjectTree;

/**
 * 笔记本。
 * @author DwArFeng
 * @since 1.8
 */
public final class Notebook extends AbstractObjectInProjectTree 
implements PopupInTree,Deleteable,Moveable,Searchable,SerialParamSetable{
	
	/**序列参数*/
	private SerialParam serialParam;
	
	/**
	 * 笔记本的构造者类。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private List<Note<?, ?>> notes = new ArrayList<Note<?,?>>();
		private SerialParam serialParam = new SerialParam.Productor().product();
		
		/**
		 * 生成一个新的笔记本的构造者类。
		 */
		public Productor(){}
		
		/**
		 * 设置该笔记本中的笔记。
		 * @param val 笔记列表，如果为<code>null</code>，则将列表清空。
		 * @return 构造器自身。
		 */
		public Productor notes(List<Note<?,?>> val){
			if(val == null){
				this.notes = new ArrayList<Note<?,?>>();
			}else{
				this.notes = val;
			}
			return this;
		}
		
		/**
		 * 设置序列参数。
		 * @param val 序列参数，如果为<code>null</code>，则将序列参数设置为默认值。
		 * @return 构造器自身。
		 */
		public Productor serialParam(SerialParam val){
			if(val == null){
				this.serialParam = new SerialParam.Productor().product();
			}else{
				this.serialParam = val;
			}
			return this;
		}
		
		/**
		 * 由构造器的设定构造笔记本。
		 * @return 构造的笔记本。
		 */
		public Notebook product(){
			return new Notebook(notes, serialParam);
		}
	}
	
	/**
	 * 生成一个具有指定笔记列表，指定线性参数的笔记本。
	 * @param notes 指定的笔记列表。
	 * @param serialParam 指定的线性参数。
	 */
	private Notebook(List<Note<?,?>> notes,SerialParam serialParam){
		//调用父类构造方法
		super(true);
		//为自身添加笔记本
		if(notes != null){
			for(Note<?,?> note:notes){
				if(note != null) add(note);
			}
		}
		//设置序列参数
		this.serialParam = serialParam == null ?
				new SerialParam.Productor().product() : serialParam;
		this.serialParam.setContext(getRootProject());
	}	
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.PopupInTree#getJPopupMenu(com.dwarfeng.scheduler.gui.JProjectTree)
	 */
	@Override
	public JPopupMenu getJPopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		JMenu notebookTreePopupNew = new JMenu("新建(N)");
		notebookTreePopupNew.setMnemonic(KeyEvent.VK_N);
		notebookTreePopupNew.setIcon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/new.png")));
		
		notebookTreePopupNew.add(new JMenuItemAction.Productor()
				.icon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/plainNote.png")))
				.name("纯文本笔记")
				.description("新建一个纯文本（TXT）笔记")
				.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_T,0))
				.listener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//获取线性参数，并通过判断线性参数是否为null来判断是否按下了取消键。
						SerialParam sp = UserInput.getSerialParam("新纯文本笔记", null);
						if(sp == null) return;
						//构造新的笔记并设置参数
						PlainNote note = new PlainNote
								.Productor(new PlainTextAttachment(ProjectIoHelper.genSchedulerURL(getRootProject(), "docs" + File.separator, ".txt")))
								.serialParam(sp)
								.product();
						//向工程树中的指定笔记本添加此笔记
						add(note);
						//更新界面
						Scheduler133.getInstance().refreshProjectTrees(getRootProject(), note);
					}
				})
				.product()
		);
		
		notebookTreePopupNew.add(new JMenuItemAction.Productor()
				.icon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/rtfNote.png")))
				.name("富文本笔记")
				.description("新建一个富文本（RTF）笔记")
				.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_R,0))
				.listener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//获取线性参数，并通过判断线性参数是否为null来判断是否按下了取消键。
						SerialParam sp = UserInput.getSerialParam("新富文本笔记", null);
						if(sp == null) return;
						//构造新的笔记并设置参数
						RTFNote note = new RTFNote
								.Productor(new RTFTextAttachment(ProjectIoHelper.genSchedulerURL(getRootProject(), "docs" + File.separator, ".rtf")))
								.serialParam(sp)
								.product();
						//向工程树中的指定笔记本添加此笔记
						add(note);
						//更新界面
						Scheduler133.getInstance().refreshProjectTrees(getRootProject(), note);
					}
				})
				.product()
		);
		
		popup = new JPopupMenu("笔记本");
		popup.add(notebookTreePopupNew);
		popup.add(PopupMenuActions.newParamSetItem("更改笔记本的NTD属性", this));
		popup.add(PopupMenuActions.newDeleteItem("不可恢复地删除当前的笔记本", this));
		
		return popup;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.Deleteable#getConfirmWord()
	 */
	@Override
	public String getConfirmWord() {
		return
				"即将删除：" + serialParam.getName() + "\n"
				+ "当前操作将会删除该笔记本，该操作不可恢复";
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#getObjectOutProjectTrees()
	 */
	@Override
	public Set<ObjectOutProjectTree> getObjectOutProjectTrees(){
		Set<ObjectOutProjectTree> set = new HashSet<ObjectOutProjectTree>();
		set.add(serialParam);
		return set;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#renderLabel(javax.swing.JLabel)
	 */
	@Override
	public void renderLabel(JLabel label) {
		label.setIconTextGap(8);		
		label.setIcon(new ImageIcon(Scheduler133.class.getResource("/resource/tree/notebook.png")));
		label.setFont(new Font("SansSerif", Font.PLAIN, 12));
		label.setText(serialParam.getName());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#setParent(javax.swing.tree.MutableTreeNode)
	 */
	@Override
	public void setParent(ObjectInProjectTree newParent){
		super.setParent(newParent);
		if(newParent instanceof ObjectInProject){
			serialParam.setContext((ObjectInProject) newParent);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.SerialParamable#getSerialParam()
	 */
	@Override
	public SerialParam getSerialParam(){
		if(serialParam == null){
			SerialParam sp = new SerialParam.Productor().product();
			sp.setContext(getRootProject());
			return sp;
		}
		return serialParam;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.SerialParamable#setSerialParam(com.dwarfeng.scheduler.typedef.funcint.SerialParam)
	 */
	@Override
	public void setSerialParam(SerialParam serialParam){
		this.serialParam = serialParam == null ?
				new SerialParam.Productor().product() : serialParam;
		this.serialParam.setContext(getRootProject());
	}

	@Override
	protected boolean canInsert(ObjectInProjectTree newChild) {
		return newChild instanceof Note;
	}
}
