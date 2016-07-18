package com.dwarfeng.scheduler.project;

import java.awt.Font;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import com.dwarfeng.scheduler.core.Scheduler;
import com.dwarfeng.scheduler.gui.ImprovisedPlantEditor;
import com.dwarfeng.scheduler.tools.PopupMenuActions;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectOutProjectTree;
import com.dwarfeng.scheduler.typedef.desint.Editable;
import com.dwarfeng.scheduler.typedef.desint.Editor;
import com.dwarfeng.scheduler.typedef.funcint.Deleteable;
import com.dwarfeng.scheduler.typedef.funcint.Moveable;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;
import com.dwarfeng.scheduler.typedef.funcint.Searchable;
import com.dwarfeng.scheduler.typedef.funcint.SerialParam;
import com.dwarfeng.scheduler.typedef.funcint.SerialParamSetable;

/**
 * 
 * @author DwArFeng
 * @since 1.8
 */
public final class ImprovisedPlant extends AbstractObjectInProjectTree 
implements Editable, Deleteable, Moveable,PopupInTree,Searchable,SerialParamSetable {
	
	/**XML附件*/
	private final PlainTextAttachment attachment;
	/**序列参数*/
	private SerialParam serialParam;
	/**正文是否启用自动换行*/
	private boolean lineWrap;
	
	/**
	 * 即兴计划构造者。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private final PlainTextAttachment attachment;
		private boolean lineWrap = true;
		private SerialParam serialParam = new SerialParam.Productor().product();
		
		/**
		 * 生成一个新的构造者实例。
		 * @param attachment 指定的附件。
		 * @throws NullPointerException 传入的附件为<code>null</code>。
		 */
		public Productor(PlainTextAttachment attachment){
			//设置附件
			if(attachment == null) throw new NullPointerException("Attachment can't be null");
			this.attachment = attachment;
		}
		
		/**
		 * 设置是否自动换行。
		 * @param val 是否自动换行，<code>true</code>为自动换行。
		 * @return 构造器自身。
		 */
		public Productor linWrap(boolean val){
			this.lineWrap = val;
			return this;
		}
		
		/**
		 * 设置序列参数。
		 * @param val 指定的序列参数，如果为<code>null</code>，则序列参数将置为默认值。
		 * @return 构造器自身。
		 */
		public Productor serialParam(SerialParam val){
			if(val != null){
				this.serialParam = val;
			}else{
				this.serialParam = new SerialParam.Productor().product();
			}
			return this;
		}
		
		/**
		 * 由构造器的内容构造即兴计划。
		 * @return 由构造器构造的即兴计划。
		 */
		public ImprovisedPlant product(){
			return new ImprovisedPlant(attachment, lineWrap, serialParam);
		}
		
	}
	
	/**
	 * 设置一个拥有指定附件，指定序列参数，的即兴计划。
	 * @param attachment 指定的附件。
	 * @param lineWrap 是否自动换行。
	 * @param serialParam 指定的序列参数。
	 */
	private ImprovisedPlant(PlainTextAttachment attachment,boolean lineWrap,SerialParam serialParam) {
		super(false);
		//设置附件
		this.attachment = attachment;
		this.attachment.setContext(getRootProject());
		//设置序列参数
		this.serialParam = serialParam;
		this.serialParam.setContext(getRootProject());
		//设置是否自动换行
		this.lineWrap = lineWrap;
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

	/**
	 * 返回该即兴计划对象的文本附件。
	 * @return 即兴计划对象的文本附件。
	 */
	public PlainTextAttachment getTextAttachment(){
		return this.attachment;
	}
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#renderLabel(javax.swing.JLabel)
	 */
	@Override
	public void renderLabel(JLabel label) {
		label.setIconTextGap(8);
		label.setIcon(new ImageIcon(Scheduler.class.getResource("/resource/tree/improvisedPlant.png")));
		label.setFont(new Font("SansSerif", Font.PLAIN, 12));
		label.setText(getSerialParam().getName());	
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.Deleteable#getConfirmWord()
	 */
	@Override
	public String getConfirmWord() {
		return 
				"即将删除：" + serialParam.getName() + "\n"
				+ "当前操作将会删除该文档，该操作不可恢复";
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#getEditorTitle()
	 */
	@Override
	public String getEditorTitle() {
		return getSerialParam().getName();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.PopupInTree#getJPopupMenu()
	 */
	@Override
	public JPopupMenu getJPopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		popup.add(PopupMenuActions.newEditItem("启动编辑器编辑该即兴计划", this));
		popup.add(PopupMenuActions.newParamSetItem("更改笔即兴计划的NTD属性", this));
		popup.add(PopupMenuActions.newDeleteItem("不可恢复地删除当前的即兴计划", this));
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
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#newEditor()
	 */
	@Override
	public Editor<ImprovisedPlant> newEditor(){
		return new ImprovisedPlantEditor(this);
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

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#canInsert(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree)
	 */
	@Override
	protected boolean canInsert(ObjectInProjectTree newChild) {
		return false;
	}

	/**
	 * 设置编辑器中的文本是否自动换行。
	 * @param lineWrap 自动换行标识，<code>true</code>为自动换行。
	 */
	public void setLineWrap(boolean lineWrap) {
		this.lineWrap = lineWrap;
	}

	/**
	 * 返回编辑器中的文本是否自动换行。
	 * @return 是否自动换行，<code>true</code>为自动换行。
	 */
	public boolean isLineWrap() {
		return this.lineWrap;
	}

}
