package com.dwarfeng.scheduler.project;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import com.dwarfeng.scheduler.core.Scheduler;
import com.dwarfeng.scheduler.gui.ImprovisedPlantColEditor;
import com.dwarfeng.scheduler.io.Scpath;
import com.dwarfeng.scheduler.tools.PopupMenuActions;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectOutProjectTree;
import com.dwarfeng.scheduler.typedef.desint.Editable;
import com.dwarfeng.scheduler.typedef.desint.Editor;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;

/**
 * 即兴计划列表类。
 * <p> 该类代表着即兴计划列表节点。
 * @author DwArFeng
 * @since 1.8
 */
public final class ImprovisedPlantCol extends AbstractObjectInProjectTree
implements PopupInTree,Editable{

	/**即兴计划列表的固定不变的XML附件的地址*/
	public final static Scpath ATT_PATH = new Scpath("implants" + File.separator + "overall.xml");
	
	/**对象的XML附件*/
	private final XmlAttachment attachment;
	
	/**
	 * 即兴计划合集的构造者。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private List<ImprovisedPlant> improvisedPlains = new ArrayList<ImprovisedPlant>();
		
		/**
		 * 生成一个新的即兴计划合集的构造者。
		 */
		public Productor(){}
		
		/**
		 * 设置即兴计划列表。
		 * @param val 即兴计划列表，如果为<code>null</code>，则将计划列表置为空。
		 * @return 构造器自身。
		 */
		public Productor improvisedPlains(List<ImprovisedPlant> val){
			if(val == null){
				this.improvisedPlains = new ArrayList<ImprovisedPlant>();
			}else{
				this.improvisedPlains = val;
			}
			return this;
		}
		
		/**
		 * 由构造器构造指定的即兴计划集合。
		 * @return 构造的即兴计划集合。
		 */
		public ImprovisedPlantCol product(){
			return new ImprovisedPlantCol(improvisedPlains);
		}
		
	}
	
	/**
	 * 生成具有指定
	 * @param improvisedPlains
	 */
	private ImprovisedPlantCol(List<ImprovisedPlant> improvisedPlains){
		super(true);
		//设置附件
		this.attachment = new XmlAttachment(ATT_PATH);
		this.attachment.setContext(getRootProject());
		//将notebooks中的所有notebook添加进树中
		if(improvisedPlains != null){
			for(ImprovisedPlant improvisedPlain : improvisedPlains){
				if(improvisedPlain != null) add(improvisedPlain);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#getObjectOutProjectTrees()
	 */
	@Override
	public Set<ObjectOutProjectTree> getObjectOutProjectTrees(){
		Set<ObjectOutProjectTree> set = new HashSet<ObjectOutProjectTree>();
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
		label.setIcon(new ImageIcon(Scheduler.class.getResource("/resource/tree/improvisedPlantCol.png")));
		label.setFont(new Font("SansSerif", Font.BOLD, 14));
		label.setText("即兴计划");	
	}

	/**
	 * 返回即兴计划组的XML附件。
	 * @return XML附件。
	 */
	public XmlAttachment getAttachment(){
		return this.attachment;
	}
	
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
	 * @see com.dwarfeng.scheduler.typedef.funcint.PopupInTree#getJPopupMenu(com.dwarfeng.scheduler.gui.JProjectTree)
	 */
	@Override
	public JPopupMenu getJPopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		popup.add(PopupMenuActions.newEditItem("启动编辑器编辑该笔记", this));
		return popup;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#setParent(javax.swing.tree.MutableTreeNode)
	 */
	@Override
	public void setParent(ObjectInProjectTree newParent){
		super.setParent(newParent);
		if(newParent instanceof ObjectInProject){
			attachment.setContext((ObjectInProject) newParent);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#startEdit()
	 */
	@Override
	public Editor<ImprovisedPlantCol> newEditor(){
		return new ImprovisedPlantColEditor(this);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#canInsert(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree)
	 */
	@Override
	protected boolean canInsert(ObjectInProjectTree newChild) {
		return newChild instanceof ImprovisedPlant;
	}
	
}
