package com.dwarfeng.scheduler.module.project;

import java.awt.BorderLayout;
import java.awt.Font;
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

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.PlainDocument;
import javax.swing.undo.UndoManager;

import com.dwarfeng.dwarffunction.gui.JMenuItemAction;
import com.dwarfeng.scheduler.core.Scheduler133;
import com.dwarfeng.scheduler.module.project.abstruct.ProjectTreeNode;
import com.dwarfeng.scheduler.module.project.funcint.SerialParam;
import com.dwarfeng.scheduler.typedef.desint.AbstractEditor;
import com.dwarfeng.scheduler.typedef.desint.Editor;
import com.dwarfeng.scheduler.typedef.exception.AttachmentException;
import com.dwarfeng.scheduler.view.SchedulerGui;

/**
 * 纯文本笔记。
 * <p> 以TXT为基础的笔记，不包含文本格式，适用于大量的单一性的文本记录。
 * @author DwArFeng
 * @since 1.8
 */
final class PlainNote extends PNote<PlainDocument,DefaultEditorKit> {

	/**
	 * 纯文本笔记的构造者类。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private final PlainTextAttachment attachment;
		private boolean lineWrap = true;
		private SerialParam serialParam = new SerialParam.Productor().product();
		
		/**
		 * 生成一个纯文本构造器。
		 * @param attachment 指定的纯文本附件。
		 * @throws NullPointerException 指定的纯文本附件为<code>null</code>。
		 */
		public Productor(PlainTextAttachment attachment){
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
		 * 从该构造器中构造指定的纯文本笔记。
		 * @return 构造的纯文本笔记。
		 */
		public PlainNote product(){
			return new PlainNote(attachment, lineWrap, serialParam);
		}
	}
	
	/**
	 * 生成一个拥有指定的文本附件、指定的自动换行标识、指定的线性参数的纯文本笔记。
	 * @param attachment 指定的文本附件。
	 * @param lineWrap 自动换行标记。
	 * @param serialParam 线性参数。
	 */
	private PlainNote(PlainTextAttachment attachment,boolean lineWrap,SerialParam serialParam) {
		super(attachment,lineWrap,serialParam);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#renderLabel(javax.swing.JLabel)
	 */
	@Override
	public void renderLabel(JLabel label) {
		label.setIconTextGap(8);		
		label.setIcon(new ImageIcon(Scheduler133.class.getResource("/resource/tree/plainNote.png")));
		label.setFont(new Font("SansSerif", Font.PLAIN, 12));
		label.setText(serialParam.getName());
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#newEditor()
	 */
	@Override
	public Editor<PlainNote> newEditor(){
		return new PlainNoteEditor();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#canInsert(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree)
	 */
	@Override
	protected boolean canInsert(ProjectTreeNode newChild) {
		return false;
	}
	
	
	
	private final class PlainNoteEditor extends AbstractEditor<PlainNote> {
		
		private static final long serialVersionUID = 6042906920903183850L;
		
		/**编辑的文本*/
		private PlainDocument document;
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
		/**表示对号的图片*/
		private Icon checkIcon;
		/**撤销菜单按钮*/
		private JMenuItem undoMenu;
		/**重做菜单按钮*/
		private JMenuItem redoMenu;
		/**编辑根面板*/
		private JPanel editPanel;

		/**
		 * 生成一个具有指定平面文本的平面文本编辑器。
		 * @param note 指定的平面文本。
		 */
		public PlainNoteEditor() {
			super(PlainNote.this);
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.Editor#getMenuBar()
		 */
		@Override
		public JMenuBar getMenuBar() {
			return menu;
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
							textArea.setEnabled(false);
							statusLabel.setText("正在保存");
							Runnable runnable = new Runnable() {
								@Override
								public void run() {
									try {
										getTextAttachment().save(document);
										statusLabel.setText("就绪");
									} catch (AttachmentException e) {
										e.printStackTrace();
										statusLabel.setText("出现异常");
									}finally{
										textArea.setEnabled(true);
										textArea.requestFocus();
									}
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
			undoManager = new UndoManager();
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
			add(welcomeLabel, BorderLayout.CENTER);
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
			JFrame jf = Scheduler133.getInstance().getGui();
			if(e instanceof AttachmentException){
				AttachmentException ae = (AttachmentException) e;
				//检查此异常是否由文件丢失引起
				if(ae.getCause() instanceof FileNotFoundException){
					int i = JOptionPane.showConfirmDialog(
							jf,
							"编辑器尝试读取TXT文件时发生异常。\n"
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
							"编辑器尝试读取TXT文件时发生异常。\n"
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
						welcomeLabel.setText("没有找到文件：" + ae.getScpath() + "，详细的信息将打印在控制台上。");
						e.printStackTrace();
						return true;
					}
				}else{
					e.printStackTrace();
					int i = JOptionPane.showConfirmDialog(
							jf,
							"编辑器尝试读取TXT文件时发生异常。\n"
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
						welcomeLabel.setText("没有找到文件：" + ae.getScpath() + "，详细的信息将打印在控制台上。");
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
				e.printStackTrace();
				return true;
			}
		}
		@Override
		protected void doWhenExceptionInInit(Exception e) {
			// TODO 需要逐步完善
			e.printStackTrace();
		}
		
		@Override
		protected void editorInit() throws Exception {
			if(welcomeLabel != null) remove(welcomeLabel);
			
			setLayout(new BorderLayout(0, 0));
			undoableEditListener = new UndoableEditListener() {
				@Override
				public void undoableEditHappened(UndoableEditEvent e) {
					undoManager.addEdit(e.getEdit());
					checkUndoStatus();
				}
			};
			document.addUndoableEditListener(undoableEditListener);
			
			editPanel = new JPanel();
			add(editPanel, BorderLayout.CENTER);
			editPanel.setLayout(new BorderLayout(0, 0));
			
			JPanel statusPanel = new JPanel();
			editPanel.add(statusPanel, BorderLayout.SOUTH);
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
			editPanel.add(scrollPane, BorderLayout.CENTER);
			
			textArea = new JTextArea();
			textArea.setLineWrap(isLineWrap());
			textArea.setWrapStyleWord(true);
			textArea.addMouseListener(new MouseAdapter() {
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
			textArea.setDocument(document);
			scrollPane.setViewportView(textArea);
			
			checkIcon = new ImageIcon(Scheduler133.class.getResource("/resource/menu/check.png"));
			
			popupMenu = new JPopupMenu();
			
			lineWrapMenu = popupMenu.add(new JMenuItemAction.Productor()
					.icon(textArea.getLineWrap() ? checkIcon : null)
					.name("自动换行")
					.description("文本是否自动换行")
					.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0))
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							boolean lineWrap = !textArea.getLineWrap();
							textArea.setLineWrap(lineWrap);
							lineWrapMenu.setIcon(textArea.getLineWrap() ? checkIcon : null);
							textArea.revalidate();
						}
					})
					.product()
			);
			checkUndoStatus();
			//为TextPane添加快捷方式
//			InputMap inputMap = textArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
//			ActionMap actionMap = textArea.getActionMap();
			revalidate();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.Editor#stopEdit()
		 */
		@Override
		public boolean stopEdit() {
			//如果状态良好，执行保存动作再退出
			if(getState() == State.SUCC){
				try{
					//移除撤销侦听。
					document.removeUndoableEditListener(undoableEditListener);
					//变更自动换行方式
					lineWrap = textArea.getLineWrap();
					//保存文件
					getTextAttachment().save(document);
					//返回值
					return true;
				}catch(Exception e){
					SchedulerGui gui = Scheduler133.getInstance().getGui();
					if(e instanceof AttachmentException){
						e.printStackTrace();
						int i = JOptionPane.showConfirmDialog(
								gui, 
								"附件在保存时遇到异常\n"
								+ "异常信息输出在控制台中\n"
								+ "是否继续退出？", 
								"发生异常", 
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE, 
								null
						);
						return i == JOptionPane.YES_OPTION ? true : false;
					}else{
						e.printStackTrace();
						int i = JOptionPane.showConfirmDialog(
								gui, 
								"附件在保存时发生未知异常\n"
								+ "异常信息输出在控制台中\n"
								+ "是否继续退出？", 
								"发生异常", 
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE, 
								null
						);
						return i == JOptionPane.YES_OPTION ? true : false;
					}
				}
			}
			//如果状态不良好，不做任何调度直接退出
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
	}

}
