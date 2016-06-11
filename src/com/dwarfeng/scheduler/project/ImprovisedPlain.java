package com.dwarfeng.scheduler.project;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import com.dwarfeng.scheduler.gui.JProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.typedef.desint.AbstractEditor;
import com.dwarfeng.scheduler.typedef.desint.Editable;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;

/**
 * 即兴计划类。
 * <p> 该类代表着即兴计划节点。
 * @author DwArFeng
 * @since 1.8
 */
public final class ImprovisedPlain extends AbstractObjectInProjectTree implements PopupInTree,Editable{

	private XmlAttachment attachment;
	
	/**
	 * 
	 */
	public ImprovisedPlain() {
		super(true);
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

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#renderLabel(javax.swing.JLabel)
	 */
	@Override
	public void renderLabel(JLabel label) {
		// TODO 添加一个自己的图标。
	}

	@Override
	public String getEditorTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#getEditor()
	 */
	@Override
	public AbstractEditor getEditor() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#firedEditorClose()
	 */
	@Override
	public void firedEditorClose() {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#load()
	 */
	@Override
	public void load() throws Exception {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#save()
	 */
	@Override
	public void save() throws Exception {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#release()
	 */
	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.PopupInTree#getJPopupMenu(com.dwarfeng.scheduler.gui.JProjectTree)
	 */
	@Override
	public JPopupMenu getJPopupMenu(JProjectTree jProjectTree) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
