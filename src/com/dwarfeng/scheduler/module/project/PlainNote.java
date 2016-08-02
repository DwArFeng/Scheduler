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
 * ���ı��ʼǡ�
 * <p> ��TXTΪ�����ıʼǣ��������ı���ʽ�������ڴ����ĵ�һ�Ե��ı���¼��
 * @author DwArFeng
 * @since 1.8
 */
final class PlainNote extends PNote<PlainDocument,DefaultEditorKit> {

	/**
	 * ���ı��ʼǵĹ������ࡣ
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private final PlainTextAttachment attachment;
		private boolean lineWrap = true;
		private SerialParam serialParam = new SerialParam.Productor().product();
		
		/**
		 * ����һ�����ı���������
		 * @param attachment ָ���Ĵ��ı�������
		 * @throws NullPointerException ָ���Ĵ��ı�����Ϊ<code>null</code>��
		 */
		public Productor(PlainTextAttachment attachment){
			if(attachment == null) throw new NullPointerException("Attachment can't be null");
			this.attachment = attachment;
		}
		
		/**
		 * �����Զ����з�ʽ��
		 * @param val �Զ����з�ʽ��<code>true</code>Ϊ�Զ����С�
		 * @return ����������
		 */
		public Productor lineWrap(boolean val){
			this.lineWrap = val;
			return this;
		}
		
		/**
		 * �������в�����
		 * @param val ���в�����ֵ��<code>null</code>����Ѳ�����ΪĬ��ֵ��
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
		 * �Ӹù������й���ָ���Ĵ��ı��ʼǡ�
		 * @return ����Ĵ��ı��ʼǡ�
		 */
		public PlainNote product(){
			return new PlainNote(attachment, lineWrap, serialParam);
		}
	}
	
	/**
	 * ����һ��ӵ��ָ�����ı�������ָ�����Զ����б�ʶ��ָ�������Բ����Ĵ��ı��ʼǡ�
	 * @param attachment ָ�����ı�������
	 * @param lineWrap �Զ����б�ǡ�
	 * @param serialParam ���Բ�����
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
		
		/**�༭���ı�*/
		private PlainDocument document;
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
		/**��ʾ�Ժŵ�ͼƬ*/
		private Icon checkIcon;
		/**�����˵���ť*/
		private JMenuItem undoMenu;
		/**�����˵���ť*/
		private JMenuItem redoMenu;
		/**�༭�����*/
		private JPanel editPanel;

		/**
		 * ����һ������ָ��ƽ���ı���ƽ���ı��༭����
		 * @param note ָ����ƽ���ı���
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
			//��ʼ���˵�
			menu = new JMenuBar();
			
			JMenu fileMenu = new JMenu("�ļ�(F)");
			fileMenu.setMnemonic('F');
			
			fileMenu.add(new JMenuItemAction.Productor()
					.icon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/save.png")))
					.name("����(S)")
					.description("���浱ǰ�ĵ�")
					.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK))
					.mnemonic(KeyEvent.VK_S)
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							textArea.setEnabled(false);
							statusLabel.setText("���ڱ���");
							Runnable runnable = new Runnable() {
								@Override
								public void run() {
									try {
										getTextAttachment().save(document);
										statusLabel.setText("����");
									} catch (AttachmentException e) {
										e.printStackTrace();
										statusLabel.setText("�����쳣");
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
			
			//TODO �Ժ���ӵ�������
			JMenu editMenu = new JMenu("�༭(E)");
			editMenu.setMnemonic('E');
			
			undoMenu = editMenu.add(new JMenuItemAction.Productor()
					.icon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/undo.png")))
					.name("����(Z)")
					.description("������һ������")
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
					.name("����(Y)")
					.description("������һ�γ����Ĳ���")
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
			//��ʼ����Ա����
			undoManager = new UndoManager();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.AbstractEditor#welcomeInit()
		 */
		@Override
		protected void welcomeInit() {
			setLayout(new BorderLayout(0, 0));
			
			welcomeLabel = new JLabel("���ڶ�ȡ�ļ������Ժ�...");
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
				//�����쳣�Ƿ����ļ���ʧ����
				if(ae.getCause() instanceof FileNotFoundException){
					int i = JOptionPane.showConfirmDialog(
							jf,
							"�༭�����Զ�ȡTXT�ļ�ʱ�����쳣��\n"
							+ "���쳣������ԭ����û���ҵ�����·���ж�Ӧ���ļ���\n"
							+ ae.getScpath() + "\n"
							+ "ѡ��\"��\"ʹ��Ĭ�ϵ��ļ������ʧ���ļ�\n"
							+ "ѡ�����ֹ���γ�ʼ��", 
							"�����쳣", 
							JOptionPane.YES_NO_OPTION, 
							JOptionPane.WARNING_MESSAGE, 
							null
					);
					if(i == JOptionPane.YES_OPTION){
						this.document = getTextAttachment().createDefaultObject();
						return false;
					}else{
						welcomeLabel.setText("û���ҵ��ļ���" + ae.getScpath() + "����ϸ����Ϣ����ӡ�ڿ���̨�ϡ�");
						e.printStackTrace();
						return true;
					}
				}else if(ae.getCause() instanceof IOException){
					int i = JOptionPane.showConfirmDialog(
							jf,
							"�༭�����Զ�ȡTXT�ļ�ʱ�����쳣��\n"
							+ "���쳣������ԭ����IOͨ���쳣��\n"
							+ "ѡ��\"��\"ʹ��Ĭ�ϵ��ļ������ʧ���ļ�\n"
							+ "ѡ�����ֹ���γ�ʼ��", 
							"�����쳣", 
							JOptionPane.YES_NO_OPTION, 
							JOptionPane.WARNING_MESSAGE, 
							null
					);
					if(i == JOptionPane.YES_OPTION){
						this.document = getTextAttachment().createDefaultObject();
						return false;
					}else{
						welcomeLabel.setText("û���ҵ��ļ���" + ae.getScpath() + "����ϸ����Ϣ����ӡ�ڿ���̨�ϡ�");
						e.printStackTrace();
						return true;
					}
				}else{
					e.printStackTrace();
					int i = JOptionPane.showConfirmDialog(
							jf,
							"�༭�����Զ�ȡTXT�ļ�ʱ�����쳣��\n"
							+ "���쳣������ԭ���в���ȷ��\n"
							+ "�쳣����ϸ��Ϣ�������ڿ���̨��\n"
							+ "ѡ��\"��\"ʹ��Ĭ�ϵ��ļ������ʧ���ļ�\n"
							+ "ѡ�����ֹ���γ�ʼ��", 
							"�����쳣", 
							JOptionPane.YES_NO_OPTION, 
							JOptionPane.WARNING_MESSAGE, 
							null
					);
					if(i == JOptionPane.YES_OPTION){
						this.document = getTextAttachment().createDefaultObject();
						return false;
					}else{
						welcomeLabel.setText("û���ҵ��ļ���" + ae.getScpath() + "����ϸ����Ϣ����ӡ�ڿ���̨�ϡ�");
						e.printStackTrace();
						return true;
					}
				}
			}else{
				JOptionPane.showMessageDialog(
						jf,
						"�༭����ʼ�������������޷�������쳣�����쳣����Ϣ�ǣ�\n"
						+ e.getMessage() + "\n"
						+ "��ϸ����Ϣ����ӡ�ڿ���̨��"
						+ "��ʼ�����̽���ֹ", 
						"���ܴ�����쳣",
						JOptionPane.WARNING_MESSAGE,
						null
				);
				e.printStackTrace();
				return true;
			}
		}
		@Override
		protected void doWhenExceptionInInit(Exception e) {
			// TODO ��Ҫ������
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
					.name("�Զ�����")
					.description("�ı��Ƿ��Զ�����")
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
			//ΪTextPane��ӿ�ݷ�ʽ
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
			//���״̬���ã�ִ�б��涯�����˳�
			if(getState() == State.SUCC){
				try{
					//�Ƴ�����������
					document.removeUndoableEditListener(undoableEditListener);
					//����Զ����з�ʽ
					lineWrap = textArea.getLineWrap();
					//�����ļ�
					getTextAttachment().save(document);
					//����ֵ
					return true;
				}catch(Exception e){
					SchedulerGui gui = Scheduler133.getInstance().getGui();
					if(e instanceof AttachmentException){
						e.printStackTrace();
						int i = JOptionPane.showConfirmDialog(
								gui, 
								"�����ڱ���ʱ�����쳣\n"
								+ "�쳣��Ϣ����ڿ���̨��\n"
								+ "�Ƿ�����˳���", 
								"�����쳣", 
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE, 
								null
						);
						return i == JOptionPane.YES_OPTION ? true : false;
					}else{
						e.printStackTrace();
						int i = JOptionPane.showConfirmDialog(
								gui, 
								"�����ڱ���ʱ����δ֪�쳣\n"
								+ "�쳣��Ϣ����ڿ���̨��\n"
								+ "�Ƿ�����˳���", 
								"�����쳣", 
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE, 
								null
						);
						return i == JOptionPane.YES_OPTION ? true : false;
					}
				}
			}
			//���״̬�����ã������κε���ֱ���˳�
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
		 * ����Ƿ���Գ�����������Ӧ�Ĳ˵���ť�Ŀ������ԡ�
		 */
		private void checkUndoStatus(){
			undoMenu.setEnabled(undoManager.canUndo());
			redoMenu.setEnabled(undoManager.canRedo());
		}
	}

}
