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
 * ���˼ƻ��б��ࡣ
 * <p> ��������ż��˼ƻ��б�ڵ㡣
 * @author DwArFeng
 * @since 1.8
 */
public final class ImprovisedPlantCol extends AbstractObjectInProjectTree
implements PopupInTree,Editable{

	/**���˼ƻ��б�Ĺ̶������XML�����ĵ�ַ*/
	public final static Scpath ATT_PATH = new Scpath("implants" + File.separator + "overall.xml");
	
	/**�����XML����*/
	private final XmlAttachment attachment;
	
	/**
	 * ���˼ƻ��ϼ��Ĺ����ߡ�
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private List<ImprovisedPlant> improvisedPlains = new ArrayList<ImprovisedPlant>();
		
		/**
		 * ����һ���µļ��˼ƻ��ϼ��Ĺ����ߡ�
		 */
		public Productor(){}
		
		/**
		 * ���ü��˼ƻ��б�
		 * @param val ���˼ƻ��б����Ϊ<code>null</code>���򽫼ƻ��б���Ϊ�ա�
		 * @return ����������
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
		 * �ɹ���������ָ���ļ��˼ƻ����ϡ�
		 * @return ����ļ��˼ƻ����ϡ�
		 */
		public ImprovisedPlantCol product(){
			return new ImprovisedPlantCol(improvisedPlains);
		}
		
	}
	
	/**
	 * ���ɾ���ָ��
	 * @param improvisedPlains
	 */
	private ImprovisedPlantCol(List<ImprovisedPlant> improvisedPlains){
		super(true);
		//���ø���
		this.attachment = new XmlAttachment(ATT_PATH);
		this.attachment.setContext(getRootProject());
		//��notebooks�е�����notebook��ӽ�����
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
		label.setText("���˼ƻ�");	
	}

	/**
	 * ���ؼ��˼ƻ����XML������
	 * @return XML������
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
		return "���˼ƻ�";
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.PopupInTree#getJPopupMenu(com.dwarfeng.scheduler.gui.JProjectTree)
	 */
	@Override
	public JPopupMenu getJPopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		popup.add(PopupMenuActions.newEditItem("�����༭���༭�ñʼ�", this));
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
