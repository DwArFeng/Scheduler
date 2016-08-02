package com.dwarfeng.scheduler.module.project;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JPopupMenu;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;

import com.dwarfeng.scheduler.module.project.abstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.module.project.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.module.project.abstruct.ObjectOutProjectTree;
import com.dwarfeng.scheduler.module.project.abstruct.ProjectTreeNode;
import com.dwarfeng.scheduler.module.project.funcint.Deleteable;
import com.dwarfeng.scheduler.module.project.funcint.Moveable;
import com.dwarfeng.scheduler.module.project.funcint.PopupInTree;
import com.dwarfeng.scheduler.module.project.funcint.Searchable;
import com.dwarfeng.scheduler.module.project.funcint.SerialParam;
import com.dwarfeng.scheduler.module.project.funcint.SerialParamSetable;
import com.dwarfeng.scheduler.tools.PopupMenuActions;
import com.dwarfeng.scheduler.typedef.desint.Editable;

/**
 * 抽象笔记类。
 * <p> 该类作为所有类型笔记的父类。
 * @author DwArFeng
 * @since 1.8
 */
abstract class PNote<T extends Document,S extends EditorKit> extends AbstractObjectInProjectTree 
implements Editable,PopupInTree,Moveable,Deleteable,Searchable,SerialParamSetable{

	/**文本附件*/
	protected final PTextAttachment<T,S> attachment;
	/**序列参数*/
	protected SerialParam serialParam;
	/**是否启用自动换行*/
	protected boolean lineWrap;
	
	/**
	 * 生成一个具有指定的附件、指定是自动否换行的标识、指定的线性参数的抽象笔记类。
	 * @param attachment 指定的附件。
	 * @param lineWrap 是否自动换行的标识。
	 * @param serialParam 指定的线性参数。
	 * @throws NullPointerException 指定的附件为<code>null</code>。
	 */
	protected PNote(PTextAttachment<T,S> attachment,boolean lineWrap,SerialParam serialParam) {
		//调用父类构造方法
		super(false);
		//设置附件
		if(attachment == null) throw new NullPointerException("Attachment can't be null");
		this.attachment = attachment;
		this.attachment.setContext(getRootProject());
		//设置序列参数
		this.serialParam = serialParam == null ?
				new SerialParam.Productor().product() : serialParam;
		this.serialParam.setContext(getRootProject());
		//设置是否自动换行
		this.lineWrap = lineWrap;
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#getEditorTitle()
	 */
	@Override
	public String getEditorTitle(){
		return serialParam.getName();
	}
	
	/**
	 * 返回笔记的文本附件。
	 * @return 笔记的文本附件。
	 */
	public PTextAttachment<T,S> getTextAttachment(){
		return this.attachment;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#setParent(javax.swing.tree.MutableTreeNode)
	 */
	@Override
	public void setParent(ProjectTreeNode newParent){
		super.setParent(newParent);
		if(newParent instanceof ObjectInProject){
			attachment.setContext((ObjectInProject) newParent);
			serialParam.setContext((ObjectInProject) newParent);
		}
	}
	
	/**
	 * 是否自动换行。
	 * @return 文本是否自动换行。
	 */
	public boolean isLineWrap() {
		return lineWrap;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.PopupInTree#getJPopupMenu(com.dwarfeng.scheduler.gui.JProjectTree)
	 */
	@Override
	public JPopupMenu getJPopupMenu(){
		JPopupMenu popup = new JPopupMenu();
		popup.add(PopupMenuActions.newEditItem("启动编辑器编辑该笔记", this));
		popup.add(PopupMenuActions.newParamSetItem("更改笔记的NTD属性", this));
		popup.add(PopupMenuActions.newDeleteItem("不可恢复地删除当前的笔记", this));
		return popup;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.Deleteable#getConfirmWord()
	 */
	@Override
	public String getConfirmWord(){
		return 
				"即将删除：" + serialParam.getName() + "\n"
				+ "当前操作将会删除该文档，该操作不可恢复";
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#getObjectOutProjectTrees()
	 */
	@Override
	public Set<ObjectOutProjectTree> getObjectOutProjectTrees(){
		Set<ObjectOutProjectTree> set = new HashSet<ObjectOutProjectTree>();
		set.add(attachment);
		set.add(serialParam);
		return set;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.SerialParamable#getSerialParam()
	 */
	@Override
	public SerialParam getSerialParam(){
		return this.serialParam == null ?
				new SerialParam.Productor().product() : serialParam;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.SerialParamable#setSerialParam(com.dwarfeng.scheduler.typedef.funcint.SerialParam)
	 */
	@Override
	public void setSerialParam(SerialParam serialParam){
		this.serialParam = serialParam == null ?
				new SerialParam.Productor().product() : serialParam;
	}
	
}
