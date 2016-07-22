package com.dwarfeng.scheduler.project;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import com.dwarfeng.dwarffunction.gui.JMenuItemAction;
import com.dwarfeng.scheduler.core.Scheduler133;
import com.dwarfeng.scheduler.io.ProjectIoHelper;
import com.dwarfeng.scheduler.tools.PopupMenuActions;
import com.dwarfeng.scheduler.tools.UserInput;
import com.dwarfeng.scheduler.typedef.funcint.Deleteable;
import com.dwarfeng.scheduler.typedef.funcint.Moveable;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;
import com.dwarfeng.scheduler.typedef.funcint.Searchable;
import com.dwarfeng.scheduler.typedef.funcint.SerialParam;
import com.dwarfeng.scheduler.typedef.funcint.SerialParamSetable;
import com.dwarfeng.scheduler.typedef.pabstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.pabstruct.ObjectInProject;
import com.dwarfeng.scheduler.typedef.pabstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.pabstruct.ObjectOutProjectTree;

/**
 * �ʼǱ���
 * @author DwArFeng
 * @since 1.8
 */
public final class Notebook extends AbstractObjectInProjectTree 
implements PopupInTree,Deleteable,Moveable,Searchable,SerialParamSetable{
	
	/**���в���*/
	private SerialParam serialParam;
	
	/**
	 * �ʼǱ��Ĺ������ࡣ
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private List<Note<?, ?>> notes = new ArrayList<Note<?,?>>();
		private SerialParam serialParam = new SerialParam.Productor().product();
		
		/**
		 * ����һ���µıʼǱ��Ĺ������ࡣ
		 */
		public Productor(){}
		
		/**
		 * ���øñʼǱ��еıʼǡ�
		 * @param val �ʼ��б����Ϊ<code>null</code>�����б���ա�
		 * @return ����������
		 */
		public Productor notes(List<Note<?,?>> val){
			if(val == null){
				this.notes = new ArrayList<Note<?,?>>();
			}else{
				this.notes = val;
			}
			return this;
		}
		
		/**
		 * �������в�����
		 * @param val ���в��������Ϊ<code>null</code>�������в�������ΪĬ��ֵ��
		 * @return ����������
		 */
		public Productor serialParam(SerialParam val){
			if(val == null){
				this.serialParam = new SerialParam.Productor().product();
			}else{
				this.serialParam = val;
			}
			return this;
		}
		
		/**
		 * �ɹ��������趨����ʼǱ���
		 * @return ����ıʼǱ���
		 */
		public Notebook product(){
			return new Notebook(notes, serialParam);
		}
	}
	
	/**
	 * ����һ������ָ���ʼ��б�ָ�����Բ����ıʼǱ���
	 * @param notes ָ���ıʼ��б�
	 * @param serialParam ָ�������Բ�����
	 */
	private Notebook(List<Note<?,?>> notes,SerialParam serialParam){
		//���ø��๹�췽��
		super(true);
		//Ϊ������ӱʼǱ�
		if(notes != null){
			for(Note<?,?> note:notes){
				if(note != null) add(note);
			}
		}
		//�������в���
		this.serialParam = serialParam == null ?
				new SerialParam.Productor().product() : serialParam;
		this.serialParam.setContext(getRootProject());
	}	
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.PopupInTree#getJPopupMenu(com.dwarfeng.scheduler.gui.JProjectTree)
	 */
	@Override
	public JPopupMenu getJPopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		JMenu notebookTreePopupNew = new JMenu("�½�(N)");
		notebookTreePopupNew.setMnemonic(KeyEvent.VK_N);
		notebookTreePopupNew.setIcon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/new.png")));
		
		notebookTreePopupNew.add(new JMenuItemAction.Productor()
				.icon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/plainNote.png")))
				.name("���ı��ʼ�")
				.description("�½�һ�����ı���TXT���ʼ�")
				.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_T,0))
				.listener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//��ȡ���Բ�������ͨ���ж����Բ����Ƿ�Ϊnull���ж��Ƿ�����ȡ������
						SerialParam sp = UserInput.getSerialParam("�´��ı��ʼ�", null);
						if(sp == null) return;
						//�����µıʼǲ����ò���
						PlainNote note = new PlainNote
								.Productor(new PlainTextAttachment(ProjectIoHelper.genSchedulerURL(getRootProject(), "docs" + File.separator, ".txt")))
								.serialParam(sp)
								.product();
						//�򹤳����е�ָ���ʼǱ���Ӵ˱ʼ�
						add(note);
						//���½���
						Scheduler133.getInstance().refreshProjectTrees(getRootProject(), note);
					}
				})
				.product()
		);
		
		notebookTreePopupNew.add(new JMenuItemAction.Productor()
				.icon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/rtfNote.png")))
				.name("���ı��ʼ�")
				.description("�½�һ�����ı���RTF���ʼ�")
				.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_R,0))
				.listener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//��ȡ���Բ�������ͨ���ж����Բ����Ƿ�Ϊnull���ж��Ƿ�����ȡ������
						SerialParam sp = UserInput.getSerialParam("�¸��ı��ʼ�", null);
						if(sp == null) return;
						//�����µıʼǲ����ò���
						RTFNote note = new RTFNote
								.Productor(new RTFTextAttachment(ProjectIoHelper.genSchedulerURL(getRootProject(), "docs" + File.separator, ".rtf")))
								.serialParam(sp)
								.product();
						//�򹤳����е�ָ���ʼǱ���Ӵ˱ʼ�
						add(note);
						//���½���
						Scheduler133.getInstance().refreshProjectTrees(getRootProject(), note);
					}
				})
				.product()
		);
		
		popup = new JPopupMenu("�ʼǱ�");
		popup.add(notebookTreePopupNew);
		popup.add(PopupMenuActions.newParamSetItem("���ıʼǱ���NTD����", this));
		popup.add(PopupMenuActions.newDeleteItem("���ɻָ���ɾ����ǰ�ıʼǱ�", this));
		
		return popup;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.Deleteable#getConfirmWord()
	 */
	@Override
	public String getConfirmWord() {
		return
				"����ɾ����" + serialParam.getName() + "\n"
				+ "��ǰ��������ɾ���ñʼǱ����ò������ɻָ�";
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#getObjectOutProjectTrees()
	 */
	@Override
	public Set<ObjectOutProjectTree> getObjectOutProjectTrees(){
		Set<ObjectOutProjectTree> set = new HashSet<ObjectOutProjectTree>();
		set.add(serialParam);
		return set;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#renderLabel(javax.swing.JLabel)
	 */
	@Override
	public void renderLabel(JLabel label) {
		label.setIconTextGap(8);		
		label.setIcon(new ImageIcon(Scheduler133.class.getResource("/resource/tree/notebook.png")));
		label.setFont(new Font("SansSerif", Font.PLAIN, 12));
		label.setText(serialParam.getName());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#setParent(javax.swing.tree.MutableTreeNode)
	 */
	@Override
	public void setParent(ObjectInProjectTree newParent){
		super.setParent(newParent);
		if(newParent instanceof ObjectInProject){
			serialParam.setContext((ObjectInProject) newParent);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.SerialParamable#getSerialParam()
	 */
	@Override
	public SerialParam getSerialParam(){
		if(serialParam == null){
			SerialParam sp = new SerialParam.Productor().product();
			sp.setContext(getRootProject());
			return sp;
		}
		return serialParam;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.SerialParamable#setSerialParam(com.dwarfeng.scheduler.typedef.funcint.SerialParam)
	 */
	@Override
	public void setSerialParam(SerialParam serialParam){
		this.serialParam = serialParam == null ?
				new SerialParam.Productor().product() : serialParam;
		this.serialParam.setContext(getRootProject());
	}

	@Override
	protected boolean canInsert(ObjectInProjectTree newChild) {
		return newChild instanceof Note;
	}
}
