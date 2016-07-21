package com.dwarfeng.scheduler.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.text.PlainDocument;

import com.dwarfeng.dwarffunction.gui.JAdjustableBorderPanel;
import com.dwarfeng.scheduler.project.ImprovisedPlant;
import com.dwarfeng.scheduler.typedef.desint.AbstractEditor;
import com.dwarfeng.scheduler.typedef.exception.AttachmentException;
import com.dwarfeng.scheduler.typedef.funcint.SerialParam;

/**
 * ���˼ƻ��ı༭����
 * @author DwArFeng
 * @since 1.8
 */
public class ImprovisedPlantEditor extends AbstractEditor<ImprovisedPlant> {
	
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
			getEditable().setLineWrap(documentArea.getLineWrap());
			//������в���
			SerialParam serialParam = getEditable().getSerialParam();
			getEditable().setSerialParam(new SerialParam.Productor()
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
			getEditable().getTextAttachment().save(document);
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
		this.document = getEditable().getTextAttachment().load();
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
		describeArea.setText(getEditable().getSerialParam().getDescribe());
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
