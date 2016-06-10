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
 * 纯文本编辑器。
 * <p> 对纯文本进行编辑，其功能类似于Notepad.exe。
 * TODO 该编辑器功能正在完善。
 * @author DwArFeng
 * @since 1.8
 */
public class PlainNoteEditor extends AbstractEditor {
	
	private static final long serialVersionUID = 3575662662260632047L;
	
	/**编辑器的菜单*/
	private JMenuBar menu;
	/**显示欢迎信息的标签*/
	private JLabel welcomeLabel;
	/**主编辑面板*/
	private JTextArea textArea;
	/**撤销管理器*/
	private UndoManager undoManager;
	/**撤销侦听器*/
	private UndoableEditListener undoableEditListener;
	/**指示编辑器状态的标签*/
	private JLabel statusLabel;
	/**编辑框的右键弹出菜单*/
	private JPopupMenu popupMenu;
	/**指示是否自动换行的菜单键*/
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
		
		welcomeLabel = new JLabel("正在读取文件，请稍候");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(welcomeLabel, BorderLayout.CENTER);
	}

	@Override
	protected void doWhenExceptionInLoad(Exception e) {
		// TODO 需要逐步完善
		e.printStackTrace();		
	}
	@Override
	protected void doWhenExceptionInInit(Exception e) {
		// TODO 需要逐步完善
		e.printStackTrace();
	}
	
	@Override
	protected void doWhenExceptionInSave(Exception e) {
		// TODO 需要逐步完善
		e.printStackTrace();
	}

	@Override
	protected void paramInit() {
		//初始化菜单
		menu = new JMenuBar();
		
		JMenu fileMenu = new JMenu("文件(F)");
		fileMenu.setMnemonic('F');
		
		fileMenu.add(new JMenuItemAction(
				null,
				"保存(S)", 
				"保存当前文档", 
				KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK), 
				KeyEvent.VK_S,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						textArea.setEnabled(false);
						statusLabel.setText("正在保存");
						Runnable runnable = new Runnable() {
							@Override
							public void run() {
								saveEditable();
								textArea.setEnabled(true);
								statusLabel.setText("就绪");
								textArea.requestFocus();
							}
						};
						RunnerQueue.invoke(runnable);
					}
				}
		));
		
		//TODO 以后添加导出功能
		JMenu editMenu = new JMenu("编辑(E)");
		editMenu.setMnemonic('E');
		
		undoMenu = editMenu.add(new JMenuItemAction(null, 
				"撤销(Z)", 
				"撤销上一步操作", 
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
				"重做(Y)", 
				"重做上一次撤销的操作", 
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
		//初始化成员变量
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
		
		statusLabel = new JLabel("就绪");
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
				"自动换行", 
				"文本是否自动换行", 
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
		//为TextPane添加快捷方式
//		InputMap inputMap = textArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
//		ActionMap actionMap = textArea.getActionMap();
		
		revalidate();
	}

	@Override
	protected void doWhenDispose() {
		//移除撤销侦听。
		getEditable().getDocument().removeUndoableEditListener(undoableEditListener);
	}
	
	/**
	 * 检查是否可以撤销并设置相应的菜单按钮的可用属性。
	 */
	private void checkUndoStatus(){
		undoMenu.setEnabled(undoManager.canUndo());
		redoMenu.setEnabled(undoManager.canRedo());
	}
}
