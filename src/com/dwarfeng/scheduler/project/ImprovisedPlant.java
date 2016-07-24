package com.dwarfeng.scheduler.project;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.text.PlainDocument;

import com.dwarfeng.dwarffunction.gui.JAdjustableBorderPanel;
import com.dwarfeng.scheduler.core.Scheduler133;
import com.dwarfeng.scheduler.tools.PopupMenuActions;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectOutProjectTree;
import com.dwarfeng.scheduler.typedef.desint.AbstractEditor;
import com.dwarfeng.scheduler.typedef.desint.Editable;
import com.dwarfeng.scheduler.typedef.desint.Editor;
import com.dwarfeng.scheduler.typedef.exception.AttachmentException;
import com.dwarfeng.scheduler.typedef.funcint.Deleteable;
import com.dwarfeng.scheduler.typedef.funcint.Moveable;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;
import com.dwarfeng.scheduler.typedef.funcint.Searchable;
import com.dwarfeng.scheduler.typedef.funcint.SerialParam;
import com.dwarfeng.scheduler.typedef.funcint.SerialParamSetable;

/**
 * 
 * @author DwArFeng
 * @since 1.8
 */
public final class ImprovisedPlant extends AbstractObjectInProjectTree 
implements Editable, Deleteable, Moveable,PopupInTree,Searchable,SerialParamSetable {
	
	/**XML����*/
	private final PlainTextAttachment attachment;
	/**���в���*/
	private SerialParam serialParam;
	/**�����Ƿ������Զ�����*/
	private boolean lineWrap;
	
	/**
	 * ���˼ƻ������ߡ�
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private final PlainTextAttachment attachment;
		private boolean lineWrap = true;
		private SerialParam serialParam = new SerialParam.Productor().product();
		
		/**
		 * ����һ���µĹ�����ʵ����
		 * @param attachment ָ���ĸ�����
		 * @throws NullPointerException ����ĸ���Ϊ<code>null</code>��
		 */
		public Productor(PlainTextAttachment attachment){
			//���ø���
			if(attachment == null) throw new NullPointerException("Attachment can't be null");
			this.attachment = attachment;
		}
		
		/**
		 * �����Ƿ��Զ����С�
		 * @param val �Ƿ��Զ����У�<code>true</code>Ϊ�Զ����С�
		 * @return ����������
		 */
		public Productor linWrap(boolean val){
			this.lineWrap = val;
			return this;
		}
		
		/**
		 * �������в�����
		 * @param val ָ�������в��������Ϊ<code>null</code>�������в�������ΪĬ��ֵ��
		 * @return ����������
		 */
		public Productor serialParam(SerialParam val){
			if(val != null){
				this.serialParam = val;
			}else{
				this.serialParam = new SerialParam.Productor().product();
			}
			return this;
		}
		
		/**
		 * �ɹ����������ݹ��켴�˼ƻ���
		 * @return �ɹ���������ļ��˼ƻ���
		 */
		public ImprovisedPlant product(){
			return new ImprovisedPlant(attachment, lineWrap, serialParam);
		}
		
	}
	
	/**
	 * ����һ��ӵ��ָ��������ָ�����в������ļ��˼ƻ���
	 * @param attachment ָ���ĸ�����
	 * @param lineWrap �Ƿ��Զ����С�
	 * @param serialParam ָ�������в�����
	 */
	private ImprovisedPlant(PlainTextAttachment attachment,boolean lineWrap,SerialParam serialParam) {
		super(false);
		//���ø���
		this.attachment = attachment;
		this.attachment.setContext(getRootProject());
		//�������в���
		this.serialParam = serialParam;
		this.serialParam.setContext(getRootProject());
		//�����Ƿ��Զ�����
		this.lineWrap = lineWrap;
	}
	
	/**
	 * �Ƿ��Զ����С�
	 * @return �ı��Ƿ��Զ����С�
	 */
	public boolean isLineWrap() {
		return lineWrap;
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#getObjectOutProjectTrees()
	 */
	@Override
	public Set<ObjectOutProjectTree> getObjectOutProjectTrees(){
		Set<ObjectOutProjectTree> set = new HashSet<ObjectOutProjectTree>();
		set.add(attachment);
		set.add(serialParam);
		return set;
	}

	/**
	 * ���ظü��˼ƻ�������ı�������
	 * @return ���˼ƻ�������ı�������
	 */
	public PlainTextAttachment getTextAttachment(){
		return this.attachment;
	}
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#renderLabel(javax.swing.JLabel)
	 */
	@Override
	public void renderLabel(JLabel label) {
		label.setIconTextGap(8);
		label.setIcon(new ImageIcon(Scheduler133.class.getResource("/resource/tree/improvisedPlant.png")));
		label.setFont(new Font("SansSerif", Font.PLAIN, 12));
		label.setText(getSerialParam().getName());	
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.Deleteable#getConfirmWord()
	 */
	@Override
	public String getConfirmWord() {
		return 
				"����ɾ����" + serialParam.getName() + "\n"
				+ "��ǰ��������ɾ�����ĵ����ò������ɻָ�";
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#getEditorTitle()
	 */
	@Override
	public String getEditorTitle() {
		return getSerialParam().getName();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.PopupInTree#getJPopupMenu()
	 */
	@Override
	public JPopupMenu getJPopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		popup.add(PopupMenuActions.newEditItem("�����༭���༭�ü��˼ƻ�", this));
		popup.add(PopupMenuActions.newParamSetItem("���ıʼ��˼ƻ���NTD����", this));
		popup.add(PopupMenuActions.newDeleteItem("���ɻָ���ɾ����ǰ�ļ��˼ƻ�", this));
		return popup;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#setParent(javax.swing.tree.MutableTreeNode)
	 */
	@Override
	public void setParent(ObjectInProjectTree newParent){
		super.setParent(newParent);
		if(newParent instanceof ObjectInProject){
			attachment.setContext((ObjectInProject) newParent);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#newEditor()
	 */
	@Override
	public Editor<ImprovisedPlant> newEditor(){
		return new ImprovisedPlantEditor(this);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.SerialParamable#getSerialParam()
	 */
	@Override
	public SerialParam getSerialParam(){
		return this.serialParam == null ?
				new SerialParam.Productor().product() : serialParam;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.SerialParamable#setSerialParam(com.dwarfeng.scheduler.typedef.funcint.SerialParam)
	 */
	@Override
	public void setSerialParam(SerialParam serialParam){
		this.serialParam = serialParam == null ?
				new SerialParam.Productor().product() : serialParam;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#canInsert(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree)
	 */
	@Override
	protected boolean canInsert(ObjectInProjectTree newChild) {
		return false;
	}

	
	
	class ImprovisedPlantEditor extends AbstractEditor<ImprovisedPlant> {
		
		private static final long serialVersionUID = 2519345985200812052L;
		
		/**��ӭ��ǩ*/
		private JLabel welcomeLabel;
		/**״̬��ǩ*/
		private JLabel statusLabel;
		/**��ϸ�����ı�*/
		private PlainDocument document;
		/**�������ı�*/
		private JTextArea describeArea;
		/**�ĵ����ı�*/
		private JTextArea documentArea;
		
		/**
		 * �����õĹ�����������
		 */
		private ImprovisedPlantEditor(){
			super(new ImprovisedPlant.Productor(null).product(),0);
			try {
				editorInit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/**
		 * ����һ������ָ�����˼ƻ��ļ��˼ƻ��༭����
		 * @param editable ָ���ļ��˼ƻ���
		 */
		public ImprovisedPlantEditor(ImprovisedPlant editable) {
			super(editable);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.Editor#getMenuBar()
		 */
		@Override
		public JMenuBar getMenuBar() {
			// TODO Auto-generated method stub
			return null;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.Editor#stopEdit()
		 */
		@Override
		public boolean stopEdit() {
			//TODO ��Ҫ�����쳣�жϡ�
			//���״̬���ã��򱣴沢�˳�
			if(getState() == State.SUCC){
				//����Զ����з�ʽ
				lineWrap = documentArea.getLineWrap();
				//������в���
				SerialParam serialParam = getSerialParam();
				setSerialParam(new SerialParam.Productor()
						.name(serialParam.getName())
						.describe(describeArea.getText())
						.tagIds(serialParam.getTagIds())
						.product()
				);
				//�����ļ���
				saveEdit();
				//����ֵ
				return true;
			}
			//״̬��������ֱ���˳�
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
		
		private void saveEdit() {
			try {
				getTextAttachment().save(document);
			} catch (AttachmentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.AbstractEditor#welcomeInit()
		 */
		@Override
		protected void welcomeInit() {
			setLayout(new BorderLayout(0, 0));
			
			welcomeLabel = new JLabel("���ڼ����У����Ժ�...");
			welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
			add(welcomeLabel, BorderLayout.CENTER);		
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.AbstractEditor#prepareInit()
		 */
		@Override
		protected void prepareInit() throws Exception {
			this.document = getTextAttachment().load();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.AbstractEditor#doWhenExceptionInInit(java.lang.Exception)
		 */
		@Override
		protected void doWhenExceptionInInit(Exception e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
			
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.AbstractEditor#doWhenExceptionInPrepare(java.lang.Exception)
		 */
		@Override
		protected boolean doWhenExceptionInPrepare(Exception e) {
			// TODO �÷�����Ҫ���ơ�
			return false;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.AbstractEditor#paramInit()
		 */
		@Override
		protected void paramInit() {
			// TODO Auto-generated method stub
			
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.AbstractEditor#editorInit()
		 */
		@Override
		protected void editorInit() throws Exception {
			remove(welcomeLabel);
			
			setLayout(new BorderLayout(0, 0));
			
			JAdjustableBorderPanel adjustableBorderPanel = new JAdjustableBorderPanel();
			adjustableBorderPanel.setSeperatorThickness(5);
			adjustableBorderPanel.setWestEnabled(true);
			add(adjustableBorderPanel, BorderLayout.CENTER);
			
			JPanel dp = new JPanel();
			adjustableBorderPanel.add(dp, BorderLayout.WEST);
			dp.setLayout(new BorderLayout(0, 0));
			
			JLabel lblNewLabel = new JLabel("����");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			dp.add(lblNewLabel, BorderLayout.NORTH);
			
			JScrollPane scrollPane = new JScrollPane();
			dp.add(scrollPane, BorderLayout.CENTER);
			
			describeArea = new JTextArea();
			describeArea.setLineWrap(true);
			describeArea.setText(getSerialParam().getDescribe());
			scrollPane.setViewportView(describeArea);
			
			JPanel tp = new JPanel();
			adjustableBorderPanel.add(tp, BorderLayout.CENTER);
			tp.setLayout(new BorderLayout(0, 0));
			
			JLabel lblNewLabel_2 = new JLabel("��ϸ����");
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			tp.add(lblNewLabel_2, BorderLayout.NORTH);
			
			JScrollPane scrollPane_1 = new JScrollPane();
			tp.add(scrollPane_1, BorderLayout.CENTER);
			
			documentArea = new JTextArea();
			documentArea.setDocument(document);
			documentArea.setEditable(true);
			scrollPane_1.setViewportView(documentArea);
			
			JPanel statusPanel = new JPanel();
			add(statusPanel, BorderLayout.SOUTH);
			GridBagLayout gbl_statusPanel = new GridBagLayout();
			gbl_statusPanel.columnWidths = new int[]{0, 0};
			gbl_statusPanel.rowHeights = new int[]{0, 0};
			gbl_statusPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_statusPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			statusPanel.setLayout(gbl_statusPanel);
			
			statusLabel = new JLabel("����");
			GridBagConstraints gbc_statusLabel = new GridBagConstraints();
			gbc_statusLabel.anchor = GridBagConstraints.WEST;
			gbc_statusLabel.insets = new Insets(0, 5, 0, 0);
			gbc_statusLabel.gridx = 0;
			gbc_statusLabel.gridy = 0;
			statusPanel.add(statusLabel, gbc_statusLabel);
			
		}

	}
}
