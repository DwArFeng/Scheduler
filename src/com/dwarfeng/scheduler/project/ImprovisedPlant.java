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
	
	/**XML����*/
	private final PlainTextAttachment attachment;
	/**���в���*/
	private SerialParam serialParam;
	/**�����Ƿ������Զ�����*/
	private boolean lineWrap;
	
	/**
	 * ���˼ƻ������ߡ�
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private final PlainTextAttachment attachment;
		private boolean lineWrap = true;
		private SerialParam serialParam = new SerialParam.Productor().product();
		
		/**
		 * ����һ���µĹ�����ʵ����
		 * @param attachment ָ���ĸ�����
		 * @throws NullPointerException ����ĸ���Ϊ<code>null</code>��
		 */
		public Productor(PlainTextAttachment attachment){
			//���ø���
			if(attachment == null) throw new NullPointerException("Attachment can't be null");
			this.attachment = attachment;
		}
		
		/**
		 * �����Ƿ��Զ����С�
		 * @param val �Ƿ��Զ����У�<code>true</code>Ϊ�Զ����С�
		 * @return ����������
		 */
		public Productor linWrap(boolean val){
			this.lineWrap = val;
			return this;
		}
		
		/**
		 * �������в�����
		 * @param val ָ�������в��������Ϊ<code>null</code>�������в�������ΪĬ��ֵ��
		 * @return ����������
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
		 * �ɹ����������ݹ��켴�˼ƻ���
		 * @return �ɹ���������ļ��˼ƻ���
		 */
		public ImprovisedPlant product(){
			return new ImprovisedPlant(attachment, lineWrap, serialParam);
		}
		
	}
	
	/**
	 * ����һ��ӵ��ָ��������ָ�����в������ļ��˼ƻ���
	 * @param attachment ָ���ĸ�����
	 * @param lineWrap �Ƿ��Զ����С�
	 * @param serialParam ָ�������в�����
	 */
	private ImprovisedPlant(PlainTextAttachment attachment,boolean lineWrap,SerialParam serialParam) {
		super(false);
		//���ø���
		this.attachment = attachment;
		this.attachment.setContext(getRootProject());
		//�������в���
		this.serialParam = serialParam;
		this.serialParam.setContext(getRootProject());
		//�����Ƿ��Զ�����
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
	 * ���ظü��˼ƻ�������ı�������
	 * @return ���˼ƻ�������ı�������
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
				"����ɾ����" + serialParam.getName() + "\n"
				+ "��ǰ��������ɾ�����ĵ����ò������ɻָ�";
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
		popup.add(PopupMenuActions.newEditItem("�����༭���༭�ü��˼ƻ�", this));
		popup.add(PopupMenuActions.newParamSetItem("���ıʼ��˼ƻ���NTD����", this));
		popup.add(PopupMenuActions.newDeleteItem("���ɻָ���ɾ����ǰ�ļ��˼ƻ�", this));
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
	 * ���ñ༭���е��ı��Ƿ��Զ����С�
	 * @param lineWrap �Զ����б�ʶ��<code>true</code>Ϊ�Զ����С�
	 */
	public void setLineWrap(boolean lineWrap) {
		this.lineWrap = lineWrap;
	}

	/**
	 * ���ر༭���е��ı��Ƿ��Զ����С�
	 * @return �Ƿ��Զ����У�<code>true</code>Ϊ�Զ����С�
	 */
	public boolean isLineWrap() {
		return this.lineWrap;
	}

}
