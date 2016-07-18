package com.dwarfeng.scheduler.project;

import java.awt.Font;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.tree.MutableTreeNode;

import com.dwarfeng.scheduler.gui.JProjectTree;
import com.dwarfeng.scheduler.io.Scpath;
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
public final class ImprovisedPlainCol extends AbstractObjectInProjectTree
implements PopupInTree,Editable{

	private final static Scpath ATT_PATH = new Scpath("implains" + File.separator + "overall.xml");
	
	private XmlAttachment attachment;
	
	
	/**
	 * 生成默认的即兴计划对象。
	 */
	public ImprovisedPlainCol() {
		this(null);
	}
	
	public ImprovisedPlainCol(List<ImprovisedPlain> improvisedPlains){
		super(true);
		//设置附件
		this.attachment = new XmlAttachment(ATT_PATH);
		//将notebooks中的所有notebook添加进树中
		if(improvisedPlains != null){
			for(ImprovisedPlain improvisedPlain : improvisedPlains){
				if(improvisedPlain != null) add(improvisedPlain);
			}
		}
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
		label.setIconTextGap(8);
		//TODO 添加图标
		//label.setIcon(new ImageIcon(Scheduler.class.getResource("/resource/tree/notebookCollection.png")));
		label.setFont(new Font("SansSerif", Font.BOLD, 14));
		label.setText("即兴计划");	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#getEditorTitle()
	 */
	@Override
	public String getEditorTitle() {
		return "即兴计划";
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
		attachment.load();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#save()
	 */
	@Override
	public void save() throws Exception {
		attachment.save();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#release()
	 */
	@Override
	public void release() {
		attachment.release();
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
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#setParent(javax.swing.tree.MutableTreeNode)
	 */
	@Override
	public void setParent(MutableTreeNode newParent){
		super.setParent(newParent);
		if(newParent instanceof ObjectInProject){
			attachment.setRootProject((ObjectInProject) newParent);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#insert(javax.swing.tree.MutableTreeNode, int)
	 */
	@Override
	public void insert(MutableTreeNode newChild, int childIndex) {
		//只能插入笔记本
		if(!(newChild instanceof ImprovisedPlain)) throw new IllegalArgumentException("New child must instance of ImprovisedPlain");
		super.insert(newChild, childIndex);
	}
}
