package com.dwarfeng.scheduler.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
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
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.plaf.metal.MetalComboBoxEditor;
import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.undo.UndoManager;

import com.dwarfeng.func.gui.JMenuItemAction;
import com.dwarfeng.func.io.CT;
import com.dwarfeng.scheduler.core.RunnerQueue;
import com.dwarfeng.scheduler.core.Scheduler;
import com.dwarfeng.scheduler.project.Note;
import com.dwarfeng.scheduler.project.RTFNote;
import com.dwarfeng.scheduler.typedef.desint.AbstractEditor;

public class RTFNoteEditor extends AbstractEditor {
	
	private static final long serialVersionUID = -4655384648667839727L;

	private static boolean isReplace = false;

	/**撤销管理器*/
	private UndoManager undoManager;
	/**撤销侦听器*/
	private UndoableEditListener undoableEditListener;
	/**支持自动换行的文本面板*/
	private JLineWrapableTextPane textPane;
	/**加粗按钮*/
	private JToggleButton boldButton;
	/**倾斜按钮*/
	private JToggleButton italicButton;
	
	private boolean carteFlag;
	/**字体选择列表*/
	private JComboBox<String> fontComboBox;
	/**字号的选择列表*/
	private JTextField sizeTextField;
	/**编辑框的右键弹出菜单*/
	private JPopupMenu popupMenu;
	/**编辑器的功能性面板*/
	private JPanel statusPanel;
	private JToggleButton underlineButton;
	/**欢迎标签*/
	private JLabel welcomeLabel;
	private JToggleButton strikeThroughButton;
	/**是否自动换行的菜单*/
	private JMenuItem lineWarpMenu;
	/**是否正在存储中的标签*/
	//TODO
	@SuppressWarnings("unused")
	private boolean saving;
	/**代表对号的图片*/
	private Image checkImage;
	/**菜单*/
	private JMenuBar menu;
	/**撤销菜单按钮*/
	private JMenuItem undoMenu;
	/**重做菜单按钮*/
	private JMenuItem redoMenu;
	private JLabel statusLabel;
	private JScrollPane scrollPane;
	private JPanel functionPanel;
	private JToolBar toolBar;
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected RTFNoteEditor(){
		this(
				//new RTFNote(new StyledTextAttachment(new Scpath("123")))
				null
		);
	}
	/**
	 * 
	 * @param note
	 * @throws Exception 
	 */
	public RTFNoteEditor(RTFNote note){
		super(note);
//		try {
//			editorInit();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	@Override
	public RTFNote getEditable(){
		return (RTFNote) super.getEditable();
	}
	
	private void setBold(){
		MutableAttributeSet attr = new SimpleAttributeSet(); 
		StyleConstants.setBold(attr, boldButton.isSelected());
		setCharacterAttributes(attr, isReplace); 
	}
	private void setItalic(){
		MutableAttributeSet attr = new SimpleAttributeSet(); 
		StyleConstants.setItalic(attr, italicButton.isSelected());
		setCharacterAttributes(attr, isReplace); 
	}
	private void setUnderline(){
		MutableAttributeSet attr = new SimpleAttributeSet(); 
		StyleConstants.setUnderline(attr, underlineButton.isSelected());
		setCharacterAttributes(attr, isReplace); 
	}
	private void setStrikeThrough(){
		MutableAttributeSet attr = new SimpleAttributeSet(); 
		StyleConstants.setStrikeThrough(attr, strikeThroughButton.isSelected());
		setCharacterAttributes(attr, isReplace); 
	}
	private void setFont(){
		MutableAttributeSet attr = new SimpleAttributeSet(); 
		StyleConstants.setFontFamily(attr, fontComboBox.getSelectedItem().toString());
		setCharacterAttributes(attr, isReplace); 
	}
	public void setFontSize() {
		int sizeInt = 12;
		try{
			sizeInt = new Integer(sizeTextField.getText()).intValue();
			
		}catch(Exception e){
			sizeInt = 12;
		}
		CT.trace(new HTMLEditorKit().getStyleSheet().getPointSize(sizeInt));
		MutableAttributeSet attr = new SimpleAttributeSet(); 
		StyleConstants.setFontSize(attr, sizeInt); 
		setCharacterAttributes(attr, isReplace); 
	} 
	private void setCharacterAttributes(AttributeSet attr, boolean replace) { 
		 int p0 = textPane.getSelectionStart(); 
		  int p1 = textPane.getSelectionEnd(); 
		  if (p0 != p1) { 
			  getEditable().getDocument().setCharacterAttributes(p0, p1 - p0, attr, replace); 
		  } 
		  StyledEditorKit k = (StyledEditorKit) textPane.getEditorKit();
		  MutableAttributeSet inputAttributes = k.getInputAttributes(); 
		  if (replace) { 
		   inputAttributes.removeAttributes(inputAttributes); 
		  } 
		  inputAttributes.addAttributes(attr); 
	} 
	
	protected void welcomeInit() {
		setLayout(new BorderLayout(0, 0));
		welcomeLabel = new JLabel("正在读取文件，请稍候");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(welcomeLabel);
	}
	
	@Override
	public JMenuBar getMenuBar() {
		return menu;
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
						textPane.setEnabled(false);
						statusLabel.setText("正在保存");
						Runnable runnable = new Runnable() {
							@Override
							public void run() {
								saveEditable();
								textPane.setEnabled(true);
								statusLabel.setText("就绪");
								textPane.requestFocus();
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
		this.carteFlag = false;
		this.undoManager = new UndoManager();
		this.saving = false;
	}
	@Override
	protected void editorInit() throws IOException{
		if(welcomeLabel != null) remove(welcomeLabel);
		
		setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		scrollPane.getViewport().setLayout(new LineWarpableViewportLayout());
		add(scrollPane, BorderLayout.CENTER);
		
		textPane = new JLineWrapableTextPane(((Note)getEditable()).isLineWrap());
		textPane.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				try{
					carteFlag = true;
					  int p0 = textPane.getSelectionStart(); 
					  int p1 = textPane.getSelectionEnd(); 
					AttributeSet attributeSet = p0 == p1 ? textPane.getInputAttributes() :  textPane.getCharacterAttributes();
					if(attributeSet == null) return;
					boldButton.setSelected(StyleConstants.isBold(attributeSet));
					italicButton.setSelected(StyleConstants.isItalic(attributeSet));
					underlineButton.setSelected(StyleConstants.isUnderline(attributeSet));
					strikeThroughButton.setSelected(StyleConstants.isStrikeThrough(attributeSet));
					fontComboBox.setSelectedItem(StyleConstants.getFontFamily(attributeSet));
					sizeTextField.setText(StyleConstants.getFontSize(attributeSet) + "");
					//TODO 随着完善添加更多
				}finally{
					carteFlag = false;
				}
			}
		});
		textPane.addMouseListener(new MouseAdapter() {
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
		textPane.setEditorKit(getEditable().getEditorKit());
		undoableEditListener = new UndoableEditListener() {
			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				undoManager.addEdit(e.getEdit());
				checkUndoStatus();
			}
		};
		getEditable().getDocument().addUndoableEditListener(undoableEditListener);
		textPane.setDocument(getEditable().getDocument());
		scrollPane.setViewportView(textPane);
		
		checkImage = ImageIO.read(Scheduler.class.getResource("/resource/menu/check.png" ));
		
		popupMenu = new JPopupMenu();
		
		lineWarpMenu = popupMenu.add(new JMenuItemAction(
				((Note) editable).isLineWrap() ? checkImage : null,
				"自动换行", 
				"文本是否自动换行", 
				null, 
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						boolean lineWarp = !textPane.isLineWarp();
						((Note) editable).setLineWarp(lineWarp);
						textPane.setLineWarp(lineWarp);
						lineWarpMenu.setIcon(((Note) editable).isLineWrap() ? new ImageIcon(checkImage) : null);
						textPane.revalidate();
					}
				}
		));
		
		functionPanel = new JPanel();
		functionPanel.setPreferredSize(new Dimension(10, 30));
		add(functionPanel, BorderLayout.NORTH);
		functionPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		functionPanel.add(toolBar);
		
		boldButton = new JToggleButton("");
		boldButton.setIcon(new ImageIcon(SchedulerGui.class.getResource("/resource/bold.png")));
		boldButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {setBold();}
		});
		
		fontComboBox = new JComboBox<String>();
		fontComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {if(!carteFlag){setFont();}
			}
		});

		fontComboBox.setEditable(true);
		fontComboBox.setPreferredSize(new Dimension(150, 28));
		DefaultComboBoxModel<String> allFonts = new DefaultComboBoxModel<String>();
		for(String str : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()){
			allFonts.addElement(str);
		}
		fontComboBox.setModel(allFonts);
		fontComboBox.setEditor(new MetalComboBoxEditor());
		toolBar.add(fontComboBox);
		
		sizeTextField = new JTextField();
		sizeTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {if(!carteFlag){setFontSize();}
			}
		});
		sizeTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		toolBar.add(sizeTextField);
		sizeTextField.setColumns(3);
		toolBar.add(boldButton);
		
		italicButton = new JToggleButton("");
		italicButton.setIcon(new ImageIcon(SchedulerGui.class.getResource("/resource/italic.png")));
		italicButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {setItalic();}
		});
		toolBar.add(italicButton);
		
		underlineButton = new JToggleButton("");
		underlineButton.setIcon(new ImageIcon(RTFNoteEditor.class.getResource("/resource/underline.png")));
		underlineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {setUnderline();}
		});
		toolBar.add(underlineButton);
		
		strikeThroughButton = new JToggleButton("s");
		toolBar.add(strikeThroughButton);
		strikeThroughButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {setStrikeThrough();}
		});
		
		statusPanel = new JPanel();
		add(statusPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_statusPanel = new GridBagLayout();
		gbl_statusPanel.columnWidths = new int[]{0, 80, 0, 0, 0};
		gbl_statusPanel.rowHeights = new int[]{0, 0};
		gbl_statusPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_statusPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		statusPanel.setLayout(gbl_statusPanel);
		
		statusLabel = new JLabel("就绪");
		GridBagConstraints gbc_statusLabel = new GridBagConstraints();
		gbc_statusLabel.anchor = GridBagConstraints.WEST;
		gbc_statusLabel.insets = new Insets(0, 5, 0, 0);
		gbc_statusLabel.gridx = 0;
		gbc_statusLabel.gridy = 0;
		statusPanel.add(statusLabel, gbc_statusLabel);
		
		//为TextPane添加快捷方式
		InputMap inputMap = textPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = textPane.getActionMap();
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_B,InputEvent.CTRL_MASK), "bold");
		actionMap.put("bold", new AbstractAction() {
			private static final long serialVersionUID = -7142072987184403701L;
			@Override
			public void actionPerformed(ActionEvent e) {
				boldButton.setSelected(!boldButton.isSelected());
				setBold();
			}
		});
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_I,InputEvent.CTRL_MASK), "italic");
		actionMap.put("italic", new AbstractAction() {
			private static final long serialVersionUID = -7506459785440038973L;
			@Override
			public void actionPerformed(ActionEvent e) {
				italicButton.setSelected(!italicButton.isSelected());
				setItalic();		
			}
		});
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_U,InputEvent.CTRL_MASK), "underline");
		actionMap.put("underline", new AbstractAction() {
			private static final long serialVersionUID = 1350468217878063164L;
			@Override
			public void actionPerformed(ActionEvent e) {
				underlineButton.setSelected(!underlineButton.isSelected());
				setUnderline();		
			}
		});
		checkUndoStatus();
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