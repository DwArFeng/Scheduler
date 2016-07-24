package com.dwarfeng.scheduler.project;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JPopupMenu;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;

import com.dwarfeng.scheduler.tools.PopupMenuActions;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectOutProjectTree;
import com.dwarfeng.scheduler.typedef.desint.Editable;
import com.dwarfeng.scheduler.typedef.funcint.Deleteable;
import com.dwarfeng.scheduler.typedef.funcint.Moveable;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;
import com.dwarfeng.scheduler.typedef.funcint.Searchable;
import com.dwarfeng.scheduler.typedef.funcint.SerialParam;
import com.dwarfeng.scheduler.typedef.funcint.SerialParamSetable;

/**
 * ����ʼ��ࡣ
 * <p> ������Ϊ�������ͱʼǵĸ��ࡣ
 * @author DwArFeng
 * @since 1.8
 */
public abstract class Note<T extends Document,S extends EditorKit> extends AbstractObjectInProjectTree 
implements Editable,PopupInTree,Moveable,Deleteable,Searchable,SerialParamSetable{

	/**�ı�����*/
	protected final TextAttachment<T,S> attachment;
	/**���в���*/
	protected SerialParam serialParam;
	/**�Ƿ������Զ�����*/
	protected boolean lineWrap;
	
	/**
	 * ����һ������ָ���ĸ�����ָ�����Զ����еı�ʶ��ָ�������Բ����ĳ���ʼ��ࡣ
	 * @param attachment ָ���ĸ�����
	 * @param lineWrap �Ƿ��Զ����еı�ʶ��
	 * @param serialParam ָ�������Բ�����
	 * @throws NullPointerException ָ���ĸ���Ϊ<code>null</code>��
	 */
	protected Note(TextAttachment<T,S> attachment,boolean lineWrap,SerialParam serialParam) {
		//���ø��๹�췽��
		super(false);
		//���ø���
		if(attachment == null) throw new NullPointerException("Attachment can't be null");
		this.attachment = attachment;
		this.attachment.setContext(getRootProject());
		//�������в���
		this.serialParam = serialParam == null ?
				new SerialParam.Productor().product() : serialParam;
		this.serialParam.setContext(getRootProject());
		//�����Ƿ��Զ�����
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
	 * ���رʼǵ��ı�������
	 * @return �ʼǵ��ı�������
	 */
	public TextAttachment<T,S> getTextAttachment(){
		return this.attachment;
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
			serialParam.setContext((ObjectInProject) newParent);
		}
	}
	
	/**
	 * �Ƿ��Զ����С�
	 * @return �ı��Ƿ��Զ����С�
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
		popup.add(PopupMenuActions.newEditItem("�����༭���༭�ñʼ�", this));
		popup.add(PopupMenuActions.newParamSetItem("���ıʼǵ�NTD����", this));
		popup.add(PopupMenuActions.newDeleteItem("���ɻָ���ɾ����ǰ�ıʼ�", this));
		return popup;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.Deleteable#getConfirmWord()
	 */
	@Override
	public String getConfirmWord(){
		return 
				"����ɾ����" + serialParam.getName() + "\n"
				+ "��ǰ��������ɾ�����ĵ����ò������ɻָ�";
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
