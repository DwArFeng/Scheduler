package com.dwarfeng.scheduler.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

import com.dwarfeng.func.gui.JMenuItemAction;
import com.dwarfeng.scheduler.core.RunnerQueue;
import com.dwarfeng.scheduler.core.Scheduler;
import com.dwarfeng.scheduler.project.Note;
import com.dwarfeng.scheduler.project.PlainNote;
import com.dwarfeng.scheduler.typedef.desint.AbstractEditor;

/**
 * ���ı��༭����
 * <p> �Դ��ı����б༭���书��������Notepad.exe��
 * TODO �ñ༭�������������ơ�
 * @author DwArFeng
 * @since 1.8
 */
public class PlainNoteEditor extends AbstractEditor {
	
	private static final long serialVersionUID = 3575662662260632047L;
	
	/**�༭���Ĳ˵�*/
	private JMenuBar menu;
	/**��ʾ��ӭ��Ϣ�ı�ǩ*/
	private JLabel welcomeLabel;
	/**���༭���*/
	private JTextArea textArea;
	/**����������*/
	private UndoManager undoManager;
	/**����������*/
	private UndoableEditListener undoableEditListener;
	/**ָʾ�༭��״̬�ı�ǩ*/
	private JLabel statusLabel;
	/**�༭����Ҽ������˵�*/
	private JPopupMenu popupMenu;
	/**ָʾ�Ƿ��Զ����еĲ˵���*/
	private JMenuItem lineWrapMenu;
	private Image checkImage;
	private JMenuItem undoMenu;
	private JMenuItem redoMenu;
	
	
	public PlainNoteEditor() {
		this(null);
	}

	public PlainNoteEditor(PlainNote note) {
		super( note);
//		try {
//			editorInit();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	@Override
	public JMenuBar getMenuBar() {
		return menu;
	}

	@Override
	public PlainNote getEditable(){
		return (PlainNote) super.getEditable();
	}
	
	@Override
	protected void welcomeInit() {
		setLayout(new BorderLayout(0, 0));
		
		welcomeLabel = new JLabel("���ڶ�ȡ�ļ������Ժ�");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(welcomeLabel, BorderLayout.CENTER);
	}

	@Override
	protected void doWhenExceptionInLoad(Exception e) {
		// TODO ��Ҫ������
		e.printStackTrace();		
	}
	@Override
	protected void doWhenExceptionInInit(Exception e) {
		// TODO ��Ҫ������
		e.printStackTrace();
	}
	
	@Override
	protected void doWhenExceptionInSave(Exception e) {
		// TODO ��Ҫ������
		e.printStackTrace();
	}

	@Override
	protected void paramInit() {
		//��ʼ���˵�
		menu = new JMenuBar();
		
		JMenu fileMenu = new JMenu("�ļ�(F)");
		fileMenu.setMnemonic('F');
		
		fileMenu.add(new JMenuItemAction(
				null,
				"����(S)", 
				"���浱ǰ�ĵ�", 
				KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK), 
				KeyEvent.VK_S,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						textArea.setEnabled(false);
						statusLabel.setText("���ڱ���");
						Runnable runnable = new Runnable() {
							@Override
							public void run() {
								saveEditable();
								textArea.setEnabled(true);
								statusLabel.setText("����");
								textArea.requestFocus();
							}
						};
						RunnerQueue.invoke(runnable);
					}
				}
		));
		
		//TODO �Ժ���ӵ�������
		JMenu editMenu = new JMenu("�༭(E)");
		editMenu.setMnemonic('E');
		
		undoMenu = editMenu.add(new JMenuItemAction(null, 
				"����(Z)", 
				"������һ������", 
				KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_MASK),
				KeyEvent.VK_Z,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(undoManager.canUndo()) undoManager.undo();		
						checkUndoStatus();
					}
				}
		));
		redoMenu = editMenu.add(new JMenuItemAction(null, 
				"����(Y)", 
				"������һ�γ����Ĳ���", 
				KeyStroke.getKeyStroke(KeyEvent.VK_Y,InputEvent.CTRL_MASK),
				KeyEvent.VK_Y,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(undoManager.canRedo()) undoManager.redo();
						checkUndoStatus();
					}
				}
		));
		
		menu.add(fileMenu);
		menu.add(editMenu);
		//��ʼ����Ա����
		undoManager = new UndoManager();
	}

	@Override
	protected void editorInit() throws Exception {
		if(welcomeLabel != null) remove(welcomeLabel);
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel statusPanel = new JPanel();
		add(statusPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_statusPanel = new GridBagLayout();
		gbl_statusPanel.columnWidths = new int[]{89, 0};
		gbl_statusPanel.rowHeights = new int[]{0, 0};
		gbl_statusPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_statusPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		statusPanel.setLayout(gbl_statusPanel);
		
		statusLabel = new JLabel("����");
		statusLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_statusLabel = new GridBagConstraints();
		gbc_statusLabel.insets = new Insets(0, 5, 0, 0);
		gbc_statusLabel.anchor = GridBagConstraints.WEST;
		gbc_statusLabel.gridx = 0;
		gbc_statusLabel.gridy = 0;
		statusPanel.add(statusLabel, gbc_statusLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setLineWrap(getEditable().isLineWrap());
		textArea.setWrapStyleWord(true);
		undoableEditListener = new UndoableEditListener() {
			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				undoManager.addEdit(e.getEdit());
				checkUndoStatus();
			}
		};
		textArea.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		getEditable().getDocument().addUndoableEditListener(undoableEditListener);
		textArea.setDocument(getEditable().getDocument());
		scrollPane.setViewportView(textArea);
		
		checkImage = ImageIO.read(Scheduler.class.getResource("/resource/menu/check.png" ));
		
		popupMenu = new JPopupMenu();
		
		lineWrapMenu = popupMenu.add(new JMenuItemAction(
				((Note) editable).isLineWrap() ? checkImage : null,
				"�Զ�����", 
				"�ı��Ƿ��Զ�����", 
				null, 
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						boolean lineWarp = !textArea.getLineWrap();
						((Note) editable).setLineWarp(lineWarp);
						textArea.setLineWrap(lineWarp);
						lineWrapMenu.setIcon(((Note) editable).isLineWrap() ? new ImageIcon(checkImage) : null);
						textArea.revalidate();
					}
				}
		));
		checkUndoStatus();
		//ΪTextPane��ӿ�ݷ�ʽ
//		InputMap inputMap = textArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
//		ActionMap actionMap = textArea.getActionMap();
		
		revalidate();
	}

	@Override
	protected void doWhenDispose() {
		//�Ƴ�����������
		getEditable().getDocument().removeUndoableEditListener(undoableEditListener);
	}
	
	/**
	 * ����Ƿ���Գ�����������Ӧ�Ĳ˵���ť�Ŀ������ԡ�
	 */
	private void checkUndoStatus(){
		undoMenu.setEnabled(undoManager.canUndo());
		redoMenu.setEnabled(undoManager.canRedo());
	}
}
