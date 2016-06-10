package com.dwarfeng.scheduler.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import com.sun.glass.events.KeyEvent;

public final class JNTDSettingDialog extends JDialog{

	private static final long serialVersionUID = -1871445815867936832L;
	
	private boolean confirmFlag;
	private String name;
	private String describe;
	private JTextArea describeText;
	private JTextField nameText;
	//TODO �ڽ����İ汾�����tag
	
	public JNTDSettingDialog(){
		this(null,null,null,null);
	}
	public JNTDSettingDialog(JFrame frame,String title){
		this(frame,title,null,null);
	}
	public JNTDSettingDialog(JFrame frame,String title,String name,String describe){
		super(frame,true);
		//��ʼ����Ա����
		confirmFlag = false;
		nameText = new JTextField();
		describeText = new JTextArea();
		//������������
		setTitle(title == null ? "NTD����" : title);
		//�����ڲ���Ա����
		if(name != null) nameText.setText(name);
		if(describe != null) describeText.setText(describe);
		//ִ�г�ʼ������
		init();
	}
	
	/**
	 * 
	 */
	public String getName(){
		if(!confirmFlag) return null;
		return name == null ? "" : name;
	}
	/**
	 * 
	 * @return
	 */
	public String getDescribe(){
		if(!confirmFlag) return null;
		return describe == null ? "" : describe;
	}
	
	private void init(){
		//������������
		setSize(new Dimension(400, 300));
		setResizable(false);
		this.setLocationRelativeTo(getOwner());
		//�����Ӵ�������
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{59, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel nameLabel = new JLabel("���ƣ�");
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.WEST;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 0;
		gbc_nameLabel.gridy = 0;
		getContentPane().add(nameLabel, gbc_nameLabel);
		
		GridBagConstraints gbc_nameText = new GridBagConstraints();
		gbc_nameText.gridwidth = 3;
		gbc_nameText.insets = new Insets(0, 0, 5, 0);
		gbc_nameText.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameText.gridx = 1;
		gbc_nameText.gridy = 0;
		getContentPane().add(nameText, gbc_nameText);
		nameText.setColumns(10);
		
		JLabel describeLabel = new JLabel("������");
		GridBagConstraints gbc_describeLabel = new GridBagConstraints();
		gbc_describeLabel.anchor = GridBagConstraints.WEST;
		gbc_describeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_describeLabel.gridx = 0;
		gbc_describeLabel.gridy = 1;
		getContentPane().add(describeLabel, gbc_describeLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		describeText.setLineWrap(true);
		scrollPane.setViewportView(describeText);
		
		JButton confirmButton = new JButton("ȷ��(CTRL+ENTER)");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {confirmed();}
		});
		GridBagConstraints gbc_confirmButton = new GridBagConstraints();
		gbc_confirmButton.insets = new Insets(0, 0, 0, 5);
		gbc_confirmButton.gridx = 2;
		gbc_confirmButton.gridy = 3;
		getContentPane().add(confirmButton, gbc_confirmButton);
		
		JButton cancelButton = new JButton("ȡ��(ESC)");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {canceled();}
		});
		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.gridx = 3;
		gbc_cancelButton.gridy = 3;
		getContentPane().add(cancelButton, gbc_cancelButton);
		
		//ע���ݼ�
		ActionMap actionMap =  ((JComponent) getContentPane()).getActionMap();
		InputMap inputMap = ((JComponent) getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,InputEvent.CTRL_MASK), "confirm");
		actionMap.put("confirm", new AbstractAction() {
			private static final long serialVersionUID = -4552833762402521021L;
			@Override
			public void actionPerformed(ActionEvent e) {confirmed();}
		});
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0), "cancel");
		actionMap.put("cancel", new AbstractAction() {
			private static final long serialVersionUID = -3422338237538339808L;
			@Override
			public void actionPerformed(ActionEvent e) {canceled();}
		});
	}
	private void canceled() {
		confirmFlag = false;
		dispose();		
	}
	private void confirmed() {
		confirmFlag = true;
		name = nameText.getText();
		describe = describeText.getText();
		dispose();		
	}
	
}