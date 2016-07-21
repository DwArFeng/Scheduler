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
 * 即兴计划的编辑器。
 * @author DwArFeng
 * @since 1.8
 */
public class ImprovisedPlantEditor extends AbstractEditor<ImprovisedPlant> {
	
	private static final long serialVersionUID = 2519345985200812052L;
	
	/**欢迎标签*/
	private JLabel welcomeLabel;
	/**状态标签*/
	private JLabel statusLabel;
	/**详细内容文本*/
	private PlainDocument document;
	/**描述区文本*/
	private JTextArea describeArea;
	/**文档区文本*/
	private JTextArea documentArea;
	
	/**
	 * 调试用的构造器方法。
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
	 * 生成一个具有指定即兴计划的即兴计划编辑器。
	 * @param editable 指定的即兴计划。
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
		//TODO 需要完善异常判断。
		//如果状态良好，则保存并退出
		if(getState() == State.SUCC){
			//变更自动换行方式
			getEditable().setLineWrap(documentArea.getLineWrap());
			//变更序列参数
			SerialParam serialParam = getEditable().getSerialParam();
			getEditable().setSerialParam(new SerialParam.Productor()
					.name(serialParam.getName())
					.describe(describeArea.getText())
					.tagIds(serialParam.getTagIds())
					.product()
			);
			//保存文件。
			saveEdit();
			//返回值
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
		
		welcomeLabel = new JLabel("正在加载中，请稍后...");
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
		// TODO 该方法需要完善。
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
		
		JLabel lblNewLabel = new JLabel("描述");
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
		
		JLabel lblNewLabel_2 = new JLabel("详细内容");
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
		
		statusLabel = new JLabel("就绪");
		GridBagConstraints gbc_statusLabel = new GridBagConstraints();
		gbc_statusLabel.anchor = GridBagConstraints.WEST;
		gbc_statusLabel.insets = new Insets(0, 5, 0, 0);
		gbc_statusLabel.gridx = 0;
		gbc_statusLabel.gridy = 0;
		statusPanel.add(statusLabel, gbc_statusLabel);
		
	}

}
