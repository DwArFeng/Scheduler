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

	/**����������*/
	private UndoManager undoManager;
	/**����������*/
	private UndoableEditListener undoableEditListener;
	/**֧���Զ����е��ı����*/
	private JLineWrapableTextPane textPane;
	/**�Ӵְ�ť*/
	private JToggleButton boldButton;
	/**��б��ť*/
	private JToggleButton italicButton;
	
	private boolean carteFlag;
	/**����ѡ���б�*/
	private JComboBox<String> fontComboBox;
	/**�ֺŵ�ѡ���б�*/
	private JTextField sizeTextField;
	/**�༭����Ҽ������˵�*/
	private JPopupMenu popupMenu;
	/**�༭���Ĺ��������*/
	private JPanel statusPanel;
	private JToggleButton underlineButton;
	/**��ӭ��ǩ*/
	private JLabel welcomeLabel;
	private JToggleButton strikeThroughButton;
	/**�Ƿ��Զ����еĲ˵�*/
	private JMenuItem lineWarpMenu;
	/**�Ƿ����ڴ洢�еı�ǩ*/
	//TODO
	@SuppressWarnings("unused")
	private boolean saving;
	/**����Ժŵ�ͼƬ*/
	private Image checkImage;
	/**�˵�*/
	private JMenuBar menu;
	/**�����˵���ť*/
	private JMenuItem undoMenu;
	/**�����˵���ť*/
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
		welcomeLabel = new JLabel("���ڶ�ȡ�ļ������Ժ�");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(welcomeLabel);
	}
	
	@Override
	public JMenuBar getMenuBar() {
		return menu;
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
						textPane.setEnabled(false);
						statusLabel.setText("���ڱ���");
						Runnable runnable = new Runnable() {
							@Override
							public void run() {
								saveEditable();
								textPane.setEnabled(true);
								statusLabel.setText("����");
								textPane.requestFocus();
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
					//TODO ����������Ӹ���
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
				"�Զ�����", 
				"�ı��Ƿ��Զ�����", 
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
		
		statusLabel = new JLabel("����");
		GridBagConstraints gbc_statusLabel = new GridBagConstraints();
		gbc_statusLabel.anchor = GridBagConstraints.WEST;
		gbc_statusLabel.insets = new Insets(0, 5, 0, 0);
		gbc_statusLabel.gridx = 0;
		gbc_statusLabel.gridy = 0;
		statusPanel.add(statusLabel, gbc_statusLabel);
		
		//ΪTextPane��ӿ�ݷ�ʽ
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