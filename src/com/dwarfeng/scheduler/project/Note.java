package com.dwarfeng.scheduler.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.text.Document;
import javax.swing.tree.MutableTreeNode;

import com.dwarfeng.func.gui.JMenuItemAction;
import com.dwarfeng.scheduler.core.Scheduler;
import com.dwarfeng.scheduler.gui.JNTDSettingDialog;
import com.dwarfeng.scheduler.gui.JProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractSerialParamableTreeNode;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.SerialParamable;
import com.dwarfeng.scheduler.typedef.desint.AbstractEditor;
import com.dwarfeng.scheduler.typedef.desint.Editable;
import com.dwarfeng.scheduler.typedef.funcint.Deleteable;
import com.dwarfeng.scheduler.typedef.funcint.Moveable;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;

public abstract class Note extends AbstractSerialParamableTreeNode 
implements Editable,PopupInTree,Moveable,Deleteable{

	private static final long serialVersionUID = 8454528838313738006L;
	
	/**文本附件*/
	protected TextAttachment attachment;
	/**是否启用自动换行*/
	private boolean lineWrap;
	/**正在指向自己的编辑器*/
	protected AbstractEditor editor;
	
	/**
	 * 生成一个默认的笔记，具有指定的附件。
	 * @param attachment 指定的文本附件。
	 */
	public Note(TextAttachment attachment) {
		this(attachment,null,null,null);
	}
	/**
	 * 
	 * @param attachment
	 * @param name
	 * @param describe
	 * @param tagIdList
	 */
	public Note(TextAttachment attachment,String name,String describe,List<Integer> tagIdList) {
		this(attachment,true,name,describe,tagIdList);
	}
	/**
	 * 
	 * @param attachment
	 * @param lineWarp
	 * @param name
	 * @param describe
	 * @param tagIdList
	 */
	public Note(TextAttachment attachment,boolean lineWarp,String name,String describe,List<Integer> tagIdList) {
		//调用父类构造方法
		super(false,name,describe,tagIdList);
		//设置附件
		this.attachment = attachment == null ? 
				createDefaultTextAttachment() : attachment;
		this.attachment.setRootProject(getRootProject());
		//设置是否自动换行
		this.setLineWarp(lineWarp);
	}

	/**
	 * 返回文本附件。
	 * @return 文本附件。
	 */
	public TextAttachment getTextAttachment(){
		return attachment;
	}
	
	@Override
	public String getEditorTitle(){
		return (String) getParam(SerialParamable.NAME);
	}
	
	@Override
	public AbstractEditor getEditor() throws Exception {
		if(editor == null) editor = createEditor();
		return editor;
	}

	@Override
	public void firedEditorClose() {
		editor = null;
	}

	@Override
	public void load() throws Exception {
		attachment.load();		
	}

	/**
	 * 返回该笔记的文档。
	 * <p> 可以保证该方法永远不返回null值。
	 * @return 返回该笔记的文档。
	 */
	public Document getDocument() {
		return attachment.getAttachObject();
	}

	public void setDocument(Document document) {
			attachment.setAttachObject(document);
	}

	@Override
	public void save() throws Exception {
		attachment.save();		
	}

	@Override
	public void release() {
		attachment.release();		
	}
	
	@Override
	public void setParent(MutableTreeNode newParent){
		super.setParent(newParent);
		if(newParent instanceof ObjectInProject){
			attachment.setRootProject((ObjectInProject) newParent);
		}
	}
	
	/**
	 * 当该对象没有指向的编辑器时，该方法负责生成编辑器。
	 * @throws Exception 生成编辑器时发生异常。
	 */
	protected abstract AbstractEditor createEditor() throws Exception;
	
	protected abstract TextAttachment createDefaultTextAttachment();
	
	/**
	 * 是否自动换行。
	 * @return 文本是否自动换行。
	 */
	public boolean isLineWrap() {
		return lineWrap;
	}
	/**
	 * 设置文本是否自动换行。
	 * @param lineWrap 文本是否自动换行。
	 */
	public void setLineWarp(boolean lineWrap) {
		this.lineWrap = lineWrap;
	}

	@Override
	public JPopupMenu getJPopupMenu(JProjectTree jProjectTree){
		JPopupMenu popup = new JPopupMenu();
		popup.add(new JMenuItemAction(
				null,																										//XXX 	不要忘记添加图标
				"更改属性",
				"更改笔记的NTD属性",
				KeyStroke.getKeyStroke(KeyEvent.VK_C,0),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JNTDSettingDialog dialog = new JNTDSettingDialog(
								Scheduler.getInstance().getGui(),
								"笔记属性修改",
								(String)getParam(RTFNote.NAME),
								(String)getParam(RTFNote.DESCRIBE)
						);
						dialog.setVisible(true);
						String name = dialog.getName();
						String describe = dialog.getDescribe();
						//XXX																										将来还要添加标签进去
						//判断是否按下了取消键
						if(name == null) return;
						setParam(RTFNote.NAME, name);
						setParam(RTFNote.DESCRIBE, describe);
						//更新工程树的数据模型
						jProjectTree.repaintTreeModel();
						//展开到该笔记本
						jProjectTree.expandPath(Note.this);
					}
				}
		));
		popup.add(new JMenuItemAction(
				null,																										//XXX 	不要忘记添加图标
				"删除",
				"不可恢复地删除当前的笔记",
				KeyStroke.getKeyStroke(KeyEvent.VK_D,0),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ObjectInProjectTree parent = getParent();
						Scheduler.getInstance().requestDelete(Note.this);
						//更新工程树的数据模型
						jProjectTree.repaintTreeModel();
						//展开到该笔记本
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
	public void delete(){
		removeFromParent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.Deleteable#getConfirmWord()
	 */
	@Override
	public String getConfirmWord(){
		return 
				"即将删除：" + (String)getParam(RTFNote.NAME) + "\n"
				+ "当前操作将会删除该文档，该操作不可恢复";
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#getOtherObjectInProjects()
	 */
	@Override
	public Set<ObjectInProject> getOtherObjectInProjects() {
		Set<ObjectInProject> set = new HashSet<ObjectInProject>();
		set.add(attachment);
		return set;
	}
}
