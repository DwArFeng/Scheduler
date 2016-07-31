package com.dwarfeng.scheduler.module;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import com.dwarfeng.scheduler.core.Scheduler133;
import com.dwarfeng.scheduler.module.project.abstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.module.project.abstruct.ObjectOutProjectTree;
import com.dwarfeng.scheduler.project.funcint.PopupInTree;
import com.dwarfeng.scheduler.project.funcint.SerialParam;
import com.dwarfeng.scheduler.tools.PopupMenuActions;
import com.dwarfeng.scheduler.tools.UserInput;

/**
 * �ʼǱ����ϡ�
 * <p> �ʼǱ������ǳ����бʼǹ��ܵ��ӹ���֮һ�������е����бʼǶ�������������ڵ�֮�¡�
 * @author DwArFeng
 * @since 1.8
 */
final class PNotebookCol extends AbstractObjectInProjectTree implements PopupInTree{
	
	/**
	 * �ʼǱ����ϵĹ������ࡣ
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private List<PNotebook> notebooks = new ArrayList<PNotebook>();
		
		/**
		 * ����һ���µıʼǱ���������
		 */
		public Productor(){}
		
		/**
		 * ָ���ñʼǱ����ϵıʼǱ��б�
		 * @param val �ʼǱ��б����Ϊ<code>null</code>�����б���ա�
		 * @return ����������
		 */
		public Productor notebooks(List<PNotebook> val){
			if(val == null){
				this.notebooks = new ArrayList<PNotebook>();
			}else{
				this.notebooks = val;
			}
			return this;
		}
		
		/**
		 * ͨ������������ʼǱ����ϡ�
		 * @return ����ıʼǱ����ϡ�
		 */
		public PNotebookCol product(){
			return new PNotebookCol(notebooks);
		}
		
	}
	/**
	 * ����һ������ָ���ʼǱ��ıʼǱ����ϡ�
	 * <p> �ʼǱ�������ġ�
	 * @param notebooks �ʼǱ��б�
	 */
	private PNotebookCol(List<PNotebook> notebooks){
		super(true);
		//��notebooks�е�����notebook��ӽ�����
		if(notebooks != null){
			for(PNotebook notebook : notebooks){
				if(notebook != null) add(notebook);
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.PopupInTree#getJPopupMenu(com.dwarfeng.scheduler.gui.JProjectTree)
	 */
	@Override
	public JPopupMenu getJPopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		
		popup.add(PopupMenuActions.newNewItem("�½�һ���ʼǱ�", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//��ȡ���Բ�������ͨ���ж����Բ����Ƿ�Ϊnull���ж��Ƿ�����ȡ������
				SerialParam sp = UserInput.getSerialParam("�±ʼǱ�", null);
				if(sp == null) return;
				//�����µıʼǲ����ò���
				PNotebook notebook = new PNotebook.Productor().serialParam(sp).product();
				//�򹤳����е�ָ���ʼǱ�����Ӵ˱ʼǱ�
				add(notebook);
				//���½���
				Scheduler133.getInstance().refreshProjectTrees(getRootProject(), notebook);
			}
		}));
		
		return popup;
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#getObjectOutProjectTrees()
	 */
	@Override
	public Set<ObjectOutProjectTree> getObjectOutProjectTrees(){
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#renderLabel(javax.swing.JLabel)
	 */
	@Override
	public void renderLabel(JLabel label) {
		label.setIconTextGap(8);
		label.setIcon(new ImageIcon(Scheduler133.class.getResource("/resource/tree/notebookCollection.png")));
		label.setFont(new Font("SansSerif", Font.BOLD, 14));
		label.setText("���бʼǱ�");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#canInsert(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree)
	 */
	@Override
	protected boolean canInsert(PProjectTreeNode newChild) {
		return newChild instanceof PNotebook;
	}
}
