package com.dwarfeng.scheduler.project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.rtf.RTFEditorKit;
import javax.swing.undo.UndoManager;

import com.dwarfeng.dwarffunction.gui.JMenuItemAction;
import com.dwarfeng.scheduler.core.RunnerQueue;
import com.dwarfeng.scheduler.core.Scheduler133;
import com.dwarfeng.scheduler.gui.JLineWrapableTextPane;
import com.dwarfeng.scheduler.gui.LineWrapableViewportLayout;
import com.dwarfeng.scheduler.gui.SchedulerGui;
import com.dwarfeng.scheduler.typedef.desint.AbstractEditor;
import com.dwarfeng.scheduler.typedef.desint.Editor;
import com.dwarfeng.scheduler.typedef.exception.AttachmentException;
import com.dwarfeng.scheduler.typedef.funcint.SerialParam;
import com.dwarfeng.scheduler.typedef.pabstruct.ObjectInProjectTree;

/**
 * RTF笔记类。
 * @author DwArFeng
 * @since 1.8
 */
public final class RTFNote extends Note<StyledDocument,RTFEditorKit>{

	/**
	 * 富文本笔记的构造者。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private final RTFTextAttachment attachment;
		private boolean lineWrap = true;
		private SerialParam serialParam = new SerialParam.Productor().product();
		
		public Productor(RTFTextAttachment attachment){
			if(attachment == null) throw new NullPointerException("Attachment can't be null");
			this.attachment = attachment;
		}
		
		/**
		 * 设置自动换行方式。
		 * @param val 自动换行方式，<code>true</code>为自动换行。
		 * @return 构造器自身。
		 */
		public Productor lineWrap(boolean val){
			this.lineWrap = val;
			return this;
		}
		
		/**
		 * 设置序列参数。
		 * @param val 序列参数的值，<code>null</code>将会把参数设为默认值。
		 * @return 构造器自身。
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
		 * 从该构造器中构造指定的纯富文本笔记。
		 * @return 构造的富文本笔记。
		 */
		public RTFNote product(){
			return new RTFNote(attachment, lineWrap, serialParam);
		}
	}
	
	/**
	 * 生成一个拥有指定的文本附件、指定的自动换行标识、指定的线性参数的RTF笔记。
	 * @param attachment 指定的文本附件。
	 * @param lineWrap 自动换行标记。
	 * @param serialParam 线性参数。
	 */
	private RTFNote(RTFTextAttachment attachment,boolean lineWrap,SerialParam sp){
		super(attachment,lineWrap,sp);
	}
		
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#renderLabel(javax.swing.JLabel)
	 */
	@Override
	public void renderLabel(JLabel label) {
		label.setIconTextGap(8);		
		label.setIcon(new ImageIcon(Scheduler133.class.getResource("/resource/tree/rtfNote.png")));
		label.setFont(new Font("SansSerif", Font.PLAIN, 12));
		label.setText(serialParam.getName());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#newEditor()
	 */
	@Override
	public Editor<RTFNote> newEditor(){
		return new RTFNoteEditor();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#canInsert(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree)
	 */
	@Override
	protected boolean canInsert(ObjectInProjectTree newChild) {
		return false;
	}
	
	private final class RTFNoteEditor extends AbstractEditor<RTFNote> {
		
		private static final long serialVersionUID = 1119291749742981434L;

		private boolean isReplace = false;

		/**编辑的文本*/
		private StyledDocument document = null;
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
		private JToggleButton underlineButton;
		/**欢迎标签*/
		private JLabel welcomeLabel;
		private JToggleButton strikeThroughButton;
		/**是否自动换行的菜单*/
		private JMenuItem lineWrapMenu;
		/**代表对号的图片*/
		private Icon checkIcon;
		/**菜单*/
		private JMenuBar menu;
		/**撤销菜单按钮*/
		private JMenuItem undoMenu;
		/**重做菜单按钮*/
		private JMenuItem redoMenu;
		/**状态标签*/
		private JLabel statusLabel;
		/**工具栏*/
		private JToolBar toolBar;
		/**编辑根容器*/
		private JPanel editPanel;

		/**
		 * 生成一个具有指定富文本笔记的编辑器。
		 * @param note 指定的富文本笔记。
		 */
		public RTFNoteEditor(){
			super(RTFNote.this);
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.Editor#getMenuBar()
		 */
		@Override
		public JMenuBar getMenuBar() {
			return menu;
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
			MutableAttributeSet attr = new SimpleAttributeSet(); 
			StyleConstants.setFontSize(attr, sizeInt); 
			setCharacterAttributes(attr, isReplace); 
		} 
		
		private void setCharacterAttributes(AttributeSet attr, boolean replace) { 
			 int p0 = textPane.getSelectionStart(); 
			  int p1 = textPane.getSelectionEnd(); 
			  if (p0 != p1) { 
				  document.setCharacterAttributes(p0, p1 - p0, attr, replace); 
			  } 
			  StyledEditorKit k = (StyledEditorKit) textPane.getEditorKit();
			  MutableAttributeSet inputAttributes = k.getInputAttributes(); 
			  if (replace) { 
			   inputAttributes.removeAttributes(inputAttributes); 
			  } 
			  inputAttributes.addAttributes(attr); 
		} 
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.AbstractEditor#paramInit()
		 */
		@Override
		protected void paramInit() {
			//初始化菜单
			menu = new JMenuBar();
			
			JMenu fileMenu = new JMenu("文件(F)");
			fileMenu.setMnemonic('F');
			
			fileMenu.add(new JMenuItemAction.Productor()
					.icon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/save.png")))
					.name("保存(S)")
					.description("保存当前文档")
					.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK))
					.mnemonic(KeyEvent.VK_S)
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							textPane.setEnabled(false);
							statusLabel.setText("正在保存");
							Runnable runnable = new Runnable() {
								@Override
								public void run() {
									saveEdit();
									textPane.setEnabled(true);
									statusLabel.setText("就绪");
									textPane.requestFocus();
								}
							};
							RunnerQueue.invoke(runnable);
						}
					})
					.product()
			);
			
			//TODO 以后添加导出功能
			JMenu editMenu = new JMenu("编辑(E)");
			editMenu.setMnemonic('E');
			
			undoMenu = editMenu.add(new JMenuItemAction.Productor()
					.icon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/undo.png")))
					.name("撤销(Z)")
					.description("撤销上一步操作")
					.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_MASK))
					.mnemonic(	KeyEvent.VK_Z)
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(undoManager.canUndo()) undoManager.undo();		
							checkUndoStatus();
						}
					})
					.product()
			);

			redoMenu = editMenu.add(new JMenuItemAction.Productor()
					.icon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/redo.png")))
					.name("重做(Y)")
					.description("重做上一次撤销的操作")
					.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_Y,InputEvent.CTRL_MASK))
					.mnemonic(KeyEvent.VK_Y)
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(undoManager.canRedo()) undoManager.redo();
							checkUndoStatus();
						}
					})
					.product()
			);
			
			menu.add(fileMenu);
			menu.add(editMenu);
			//初始化成员变量
			this.carteFlag = false;
			this.undoManager = new UndoManager();
		}
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.AbstractEditor#welcomeInit()
		 */
		@Override
		protected void welcomeInit() {
			setLayout(new BorderLayout(0, 0));
			welcomeLabel = new JLabel("正在读取文件，请稍候...");
			welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
			add(welcomeLabel);
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.AbstractEditor#prepareInit()
		 */
		@Override
		protected void prepareInit() throws AttachmentException {
			this.document = getTextAttachment().load();
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.AbstractEditor#doWhenExceptionInPrepare(java.lang.Exception)
		 */
		@Override
		protected boolean doWhenExceptionInPrepare(Exception e) {
			JComponent jf = this.welcomeLabel;
			if(e instanceof AttachmentException){
				AttachmentException ae = (AttachmentException) e;
				//检查此异常是否由文件丢失引起
				if(ae.getCause() instanceof FileNotFoundException){
					int i = JOptionPane.showConfirmDialog(
							jf,
							"编辑器尝试读取RTF文件时发生异常。\n"
							+ "该异常发生的原因是没有找到工作路径中对应的文件：\n"
							+ ae.getScpath() + "\n"
							+ "选择\"是\"使用默认的文件替代丢失的文件\n"
							+ "选择否中止本次初始化", 
							"发现异常", 
							JOptionPane.YES_NO_OPTION, 
							JOptionPane.WARNING_MESSAGE, 
							null
					);
					if(i == JOptionPane.YES_OPTION){
						this.document = getTextAttachment().createDefaultObject();
						return false;
					}else{
						welcomeLabel.setText("没有找到文件：" + ae.getScpath() + "，详细的信息将打印在控制台上。");
						e.printStackTrace();
						return true;
					}
				}else if(ae.getCause() instanceof IOException){
					int i = JOptionPane.showConfirmDialog(
							jf,
							"编辑器尝试读取RTF文件时发生异常。\n"
							+ "该异常发生的原因是IO通信异常：\n"
							+ "选择\"是\"使用默认的文件替代丢失的文件\n"
							+ "选择否中止本次初始化", 
							"发现异常", 
							JOptionPane.YES_NO_OPTION, 
							JOptionPane.WARNING_MESSAGE, 
							null
					);
					if(i == JOptionPane.YES_OPTION){
						this.document = getTextAttachment().createDefaultObject();
						return false;
					}else{
						welcomeLabel.setText("IO通信异常，详细的信息将打印在控制台上。");
						e.printStackTrace();
						return true;
					}
				}else{
					e.printStackTrace();
					int i = JOptionPane.showConfirmDialog(
							jf,
							"编辑器尝试读取RTF文件时发生异常。\n"
							+ "该异常发生的原因尚不明确：\n"
							+ "异常的详细信息将当因在控制台上\n"
							+ "选择\"是\"使用默认的文件替代丢失的文件\n"
							+ "选择否中止本次初始化", 
							"发现异常", 
							JOptionPane.YES_NO_OPTION, 
							JOptionPane.WARNING_MESSAGE, 
							null
					);
					if(i == JOptionPane.YES_OPTION){
						this.document = getTextAttachment().createDefaultObject();
						return false;
					}else{
						welcomeLabel.setText("没有明确原因的附件读取异常，详细的信息将打印在控制台上。");
						e.printStackTrace();
						return true;
					}
				}
			}else{
				JOptionPane.showMessageDialog(
						jf,
						"编辑器初始化过程中遇到无法处理的异常，该异常的信息是：\n"
						+ e.getMessage() + "\n"
						+ "详细的信息将打印在控制台上"
						+ "初始化过程将中止", 
						"不能处理的异常",
						JOptionPane.WARNING_MESSAGE,
						null
				);
				welcomeLabel.setText("未知的异常，详细的信息将打印在控制台上。");
				e.printStackTrace();
				return true;
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.AbstractEditor#doWhenExceptionInInit(java.lang.Exception)
		 */
		@Override
		protected void doWhenExceptionInInit(Exception e) {
			// TODO 需要逐步完善
			e.printStackTrace();
		}
		
		@Override
		protected void editorInit() throws IOException{
			if(welcomeLabel != null) remove(welcomeLabel);
			
			setLayout(new BorderLayout(0, 0));
			
			editPanel = new JPanel();
			editPanel.setLayout(new BorderLayout());
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.getViewport().setLayout(new LineWrapableViewportLayout());
			editPanel.add(scrollPane, BorderLayout.CENTER);
			
			textPane = new JLineWrapableTextPane(isLineWrap());
			textPane.addCaretListener(new CaretListener() {
				@Override
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
				@Override
				public void mousePressed(MouseEvent e) {
					if (e.isPopupTrigger()) {
						showMenu(e);
					}
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					if (e.isPopupTrigger()) {
						showMenu(e);
					}
				}
				private void showMenu(MouseEvent e) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			});
			textPane.setEditorKit(getTextAttachment().getEditorKit());
			undoableEditListener = new UndoableEditListener() {
				@Override
				public void undoableEditHappened(UndoableEditEvent e) {
					undoManager.addEdit(e.getEdit());
					checkUndoStatus();
				}
			};
			document.addUndoableEditListener(undoableEditListener);
			textPane.setDocument(document);
			scrollPane.setViewportView(textPane);
			
			checkIcon = new ImageIcon(Scheduler133.class.getResource("/resource/menu/check.png"));
			
			popupMenu = new JPopupMenu();
			
			lineWrapMenu = popupMenu.add(new JMenuItemAction.Productor()
					.icon(textPane.isLineWrap() ? checkIcon : null)
					.name("自动换行")
					.description("文本是否自动换行")
					.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0))
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {	
							boolean lineWrap = !textPane.isLineWrap();
							textPane.setLineWrap(lineWrap);
							lineWrapMenu.setIcon(textPane.isLineWrap() ? checkIcon : null);
							textPane.revalidate();
						}
					})
					.product()
			);
			
			JPanel functionPanel = new JPanel();
			functionPanel.setPreferredSize(new Dimension(10, 30));
			editPanel.add(functionPanel, BorderLayout.NORTH);
			functionPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
			
			toolBar = new JToolBar();
			toolBar.setFloatable(false);
			functionPanel.add(toolBar);
			
			boldButton = new JToggleButton("");
			boldButton.setIcon(new ImageIcon(SchedulerGui.class.getResource("/resource/editor/textEditor/bold.png")));
			boldButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {setBold();}
			});
			
			fontComboBox = new JComboBox<String>();
			fontComboBox.addActionListener(new ActionListener() {
				@Override
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
				@Override
				public void actionPerformed(ActionEvent e) {if(!carteFlag){setFontSize();}
				}
			});
			sizeTextField.setHorizontalAlignment(SwingConstants.RIGHT);
			toolBar.add(sizeTextField);
			sizeTextField.setColumns(3);
			toolBar.add(boldButton);
			
			italicButton = new JToggleButton("");
			italicButton.setIcon(new ImageIcon(SchedulerGui.class.getResource("/resource/editor/textEditor/italic.png")));
			italicButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {setItalic();}
			});
			toolBar.add(italicButton);
			
			underlineButton = new JToggleButton("");
			underlineButton.setIcon(new ImageIcon(RTFNoteEditor.class.getResource("/resource/editor/textEditor/underline.png")));
			underlineButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {setUnderline();}
			});
			toolBar.add(underlineButton);
			
			strikeThroughButton = new JToggleButton("s");
			toolBar.add(strikeThroughButton);
			strikeThroughButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {setStrikeThrough();}
			});
			
			JPanel statusPanel = new JPanel();
			editPanel.add(statusPanel, BorderLayout.SOUTH);
			GridBagLayout gbl_statusPanel = new GridBagLayout();
			gbl_statusPanel.columnWidths = new int[]{0, 0};
			gbl_statusPanel.rowHeights = new int[]{0, 0};
			gbl_statusPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
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
			
			add(editPanel,BorderLayout.CENTER);
			checkUndoStatus();
			revalidate();
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.Editor#stopEdit()
		 */
		@Override
		public boolean stopEdit() {
			//TODO 异常判断方式需要完善。
			//如果状态良好，则保存并退出
			if(getState() == State.SUCC){
				//移除撤销侦听。
				document.removeUndoableEditListener(undoableEditListener);
				//变更自动换行方式
				textPane.isLineWrap();
				//保存文件。
				saveEdit();
				return true;
			}
			//状态不良好则直接退出
			else{
				return true;
			}
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.Editor#forceStopEdit()
		 */
		@Override
		public void forceStopEdit() {
			// TODO Auto-generated method stub
			
		}
		
		/**
		 * 检查是否可以撤销并设置相应的菜单按钮的可用属性。
		 */
		private void checkUndoStatus(){
			undoMenu.setEnabled(undoManager.canUndo());
			redoMenu.setEnabled(undoManager.canRedo());
		}
		
		private void saveEdit() {
			try {
				getTextAttachment().save(document);
			} catch (AttachmentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
