package com.dwarfeng.scheduler.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.Set;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.tree.MutableTreeNode;

import com.dwarfeng.func.gui.JMenuItemAction;
import com.dwarfeng.scheduler.core.Scheduler;
import com.dwarfeng.scheduler.gui.JNTDSettingDialog;
import com.dwarfeng.scheduler.gui.JProjectTree;
import com.dwarfeng.scheduler.io.ProjectHelper;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractSerialParamableTreeNode;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.funcint.Deleteable;
import com.dwarfeng.scheduler.typedef.funcint.Moveable;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;

/**
 * 笔记本。
 * @author DwArFeng
 * @since 1.8
 */
public class Notebook extends AbstractSerialParamableTreeNode 
	implements PopupInTree,Deleteable,Moveable{
	
	private static final long serialVersionUID = 6523774284338795747L;
	
	/**
	 * 生成一个默认的笔记本。
	 */
	public Notebook(){
		this(null,null,null,null);
	}
	/**
	 * 
	 * @param name
	 * @param describe
	 * @param tagIdList
	 * @param notes
	 */
	public Notebook(String name,String describe,List<Integer> tagIdList,List<Note> notes){
		//调用父类构造方法
		super(true,name,describe,tagIdList);
		//为自身添加笔记本
		if(notes != null){
			for(Note note:notes){
				if(note != null) add(note);
			}
		}
	}
	@Override
	public void insert(MutableTreeNode newChild, int childIndex) {
		//只能插入笔记
		if(!(newChild instanceof Note)) throw new IllegalArgumentException("New child must instance of Note");
		super.insert(newChild, childIndex);
	}
	
	
	@Override
	public JPopupMenu getJPopupMenu(JProjectTree jProjectTree) {
		JPopupMenu popup = new JPopupMenu();
		JMenu notebookTreePopupNew = new JMenu("新建");
		notebookTreePopupNew.add(new JMenuItemAction(
				null,																										//XXX 	不要忘记添加图标
				"纯文本笔记", 
				"新建一个纯文本（TXT）笔记", 
				KeyStroke.getKeyStroke(KeyEvent.VK_T,0),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//打开名称和描述的设置面板。
						JNTDSettingDialog dialog = new JNTDSettingDialog(Scheduler.getInstance().getGui(),"新纯文本笔记");
						dialog.setVisible(true);
						//获取名称，并通过判断名字是否为null来判断是否按下了取消键。
						String name = dialog.getName();
						if(name == null) return;
						String describe = dialog.getDescribe();
						//构造新的笔记并设置参数
						PlainNote note = new PlainNote(new PlainTextAttachment(ProjectHelper.genSchedulerURL(getRootProject(), "docs" + File.separator, ".txt")));
						note.setParam(RTFNote.NAME, name);
						note.setParam(RTFNote.DESCRIBE, describe);
						//向工程树中的指定笔记本添加此笔记
						add(note);
						//重新绘制工程树数据模型
						jProjectTree.repaintTreeModel();
						//展开到新建的笔记
						jProjectTree.expandPath(note);
					}
				}
		));
		notebookTreePopupNew.add(new JMenuItemAction(
				null,																										//XXX 	不要忘记添加图标
				"富文本笔记", 
				"新建一个富文本（RTF）笔记", 
				KeyStroke.getKeyStroke(KeyEvent.VK_R,0),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//打开名称和描述的设置面板。
						JNTDSettingDialog dialog = new JNTDSettingDialog(Scheduler.getInstance().getGui(),"新富文本笔记");
						dialog.setVisible(true);
						//获取名称，并通过判断名字是否为null来判断是否按下了取消键。
						String name = dialog.getName();
						if(name == null) return;
						String describe = dialog.getDescribe();
						//构造新的笔记并设置参数
						RTFNote note = new RTFNote(new StyledTextAttachment(ProjectHelper.genSchedulerURL(getRootProject(), "docs" + File.separator, ".rtf")));
						note.setParam(RTFNote.NAME, name);
						note.setParam(RTFNote.DESCRIBE, describe);
						//向工程树中的指定笔记本添加此笔记
						add(note);
						//重新绘制工程树数据模型
						jProjectTree.repaintTreeModel();
						//展开到新建的笔记
						jProjectTree.expandPath(note);
					}
				}
		));
		
		popup = new JPopupMenu("笔记本");
		
		popup.add(notebookTreePopupNew);
		popup.add(new JMenuItemAction(
				null,																										//XXX 	不要忘记添加图标
				"更改属性",
				"更改笔记本的NTD属性",
				KeyStroke.getKeyStroke(KeyEvent.VK_C,0),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JNTDSettingDialog dialog = new JNTDSettingDialog(
								Scheduler.getInstance().getGui(),
								"笔记属性修改",
								(String)getParam(Notebook.NAME),
								(String)getParam(Notebook.DESCRIBE)
						);
						dialog.setVisible(true);
						String name = dialog.getName();
						String describe = dialog.getDescribe();
						//XXX																										将来还要添加标签进去
						//判断是否按下了取消键
						if(name == null) return;
						setParam(Notebook.NAME, name);
						setParam(Notebook.DESCRIBE, describe);
						//重新绘制工程树数据模型
						jProjectTree.repaintTreeModel();
						//展开到新建的笔记本
						jProjectTree.expandPath(Notebook.this);
					}
				}
		));
		popup.add(new JMenuItemAction(
				null,																										//XXX 	不要忘记添加图标
				"删除",
				"不可恢复地删除当前的笔记本",
				KeyStroke.getKeyStroke(KeyEvent.VK_D,0),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ObjectInProjectTree parent = getParent();
						Scheduler.getInstance().requestDelete(Notebook.this);
						jProjectTree.repaintTreeModel();
						//展开到新建的笔记本
						jProjectTree.expandPath(parent);
					}
				}
		));
		
		return popup;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.Deleteable#delete()
	 */
	@Override
	public void delete() {
		removeFromParent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.Deleteable#getConfirmWord()
	 */
	@Override
	public String getConfirmWord() {
		return
				"即将删除：" + (String)getParam(Notebook.NAME) + "\n"
				+ "当前操作将会删除该笔记本，该操作不可恢复";
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#getOtherObjectInProjects()
	 */
	@Override
	public Set<ObjectInProject> getOtherObjectInProjects() {
		return null;
	}
	
}
