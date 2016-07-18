package com.dwarfeng.scheduler.project;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.tree.MutableTreeNode;

import com.dwarfeng.func.gui.JMenuItemAction;
import com.dwarfeng.scheduler.core.Scheduler;
import com.dwarfeng.scheduler.gui.JNTDSettingDialog;
import com.dwarfeng.scheduler.gui.JProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;

/**
 * �ʼǱ����ϡ�
 * <p> �ʼǱ������ǳ����бʼǹ��ܵ��ӹ���֮һ�������е����бʼǶ�������������ڵ�֮�¡�
 * @author DwArFeng
 * @since 1.8
 */
public class NotebookCol extends AbstractObjectInProjectTree implements PopupInTree{
	
	private static final long serialVersionUID = -19553197501386083L;

	/**
	 * ����һ��Ĭ�ϵģ������бʼǱ�Ԫ�صıʼǱ����ϡ�
	 */
	public NotebookCol(){
		this(null);
	}
	/**
	 * ����һ������ָ���ʼǱ��ıʼǱ����ϡ�
	 * <p> �ʼǱ�������ġ�
	 * @param notebooks �ʼǱ��б���
	 */
	public NotebookCol(List<Notebook> notebooks){
		super(true);
		//��notebooks�е�����notebook���ӽ�����
		if(notebooks != null){
			for(Notebook notebook : notebooks){
				if(notebook != null) add(notebook);
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#insert(javax.swing.tree.MutableTreeNode, int)
	 */
	@Override
	public void insert(MutableTreeNode newChild, int childIndex) {
		//ֻ�ܲ���ʼǱ�
		if(!(newChild instanceof Notebook)) throw new IllegalArgumentException("New child must instance of Notebook");
		super.insert(newChild, childIndex);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.PopupInTree#getJPopupMenu(com.dwarfeng.scheduler.gui.JProjectTree)
	 */
	@Override
	public JPopupMenu getJPopupMenu(JProjectTree jProjectTree) {
		JPopupMenu popup = new JPopupMenu();
		popup.add(new JMenuItemAction(
				null,																										//XXX 	��Ҫ��������ͼ��
				"�½�", 
				"�½�һ���ʼǱ�", 
				KeyStroke.getKeyStroke(KeyEvent.VK_N,0),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//�����ƺ�������������塣
						JNTDSettingDialog dialog = new JNTDSettingDialog(Scheduler.getInstance().getGui(),"�±ʼǱ�");
						dialog.setVisible(true);
						//��ȡ���ƣ���ͨ���ж������Ƿ�Ϊnull���ж��Ƿ�����ȡ������
						String name = dialog.getName();
						if(name == null) return;
						String describe = dialog.getDescribe();
						//�����µıʼǲ����ò���
						Notebook notebook = new Notebook();
						notebook.setParam(Notebook.NAME, name);
						notebook.setParam(Notebook.DESCRIBE, describe);
						//�򹤳����е�ָ���ʼǱ������Ӵ˱ʼǱ�
						add(notebook);
						jProjectTree.repaintTreeModel();
						//չ�������½��ıʼǱ��ڵ�
						jProjectTree.expandPath(notebook);
					}
				}
		));
		
		return popup;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#getOtherObjectInProjects()
	 */
	@Override
	public Set<ObjectInProject> getOtherObjectInProjects() {
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#renderLabel(javax.swing.JLabel)
	 */
	@Override
	public void renderLabel(JLabel label) {
		label.setIconTextGap(8);
		label.setIcon(new ImageIcon(Scheduler.class.getResource("/resource/tree/notebookCollection.png")));
		label.setFont(new Font("SansSerif", Font.BOLD, 14));
		label.setText("���бʼǱ�");
	}
}