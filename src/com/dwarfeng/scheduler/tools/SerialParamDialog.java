package com.dwarfeng.scheduler.tools;

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

import com.dwarfeng.scheduler.project.funcint.SerialParam;
import com.sun.glass.events.KeyEvent;

/**
 * 用于生成线性参数的对话框。
 * @author DwArFeng
 * @since 1.8
 */
final class SerialParamDialog extends JDialog{

	private static final long serialVersionUID = -8767211343077600024L;
	
	private boolean confirmFlag;
	private JTextArea describeText;
	private JTextField nameText;
	//TODO 在将来的版本中添加tag
	
	/**
	 * 生成一个默认的线性参数对话框。
	 */
	public SerialParamDialog(){
		this(null,null,null);
	}
	/**
	 * 生成一个拥有拥有者窗口、指定标题的线性参数对话框。
	 * @param frame 指定的拥有者窗口。
	 * @param title 指定的标题。
	 */
	public SerialParamDialog(JFrame frame,String title){
		this(frame,title,null);
	}
	
	/**
	 * 生成一个拥有拥有者窗口、指定标题、指定参考线性参数的线性参数对话框。
	 * @param frame 指定的拥有者窗口。
	 * @param title 指定的标题。
	 * @param serialParam 指定的参考线性参数。
	 */
	public SerialParamDialog(JFrame frame,String title,SerialParam serialParam){
		super(frame,true);
		//初始化成员变量
		confirmFlag = false;
		nameText = new JTextField();
		describeText = new JTextArea();
		//设置自身属性
		setTitle(title == null ? "属性设置" : title);
		//设置内部成员属性
		SerialParam sp = serialParam == null ?
				new SerialParam.Productor().product() : serialParam;
		nameText.setText(sp.getName());
		describeText.setText(sp.getDescribe());
		//执行初始化调度
		init();
	}
	
	/**
	 * 返回由该线性参数对话框生成的线性参数。
	 * <p> 当用户按下取消或者关闭按钮时，该方法返回<code>null</code>。
	 * @return 由该线性参数对话框生成的线性参数。
	 */
	public SerialParam getSerialParam(){
		if(!confirmFlag) return null;
		return new SerialParam.Productor()
				.name(this.nameText.getText())
				.describe(this.describeText.getText())
				.product();
	}
	
	/**
	 * 初始化方法
	 */
	private void init(){
		//设置自身属性
		setSize(new Dimension(400, 300));
		setResizable(false);
		this.setLocationRelativeTo(getOwner());
		//设置子窗格属性
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{59, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel nameLabel = new JLabel("名称：");
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
		
		JLabel describeLabel = new JLabel("描述：");
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
		
		JButton confirmButton = new JButton("确认(CTRL+ENTER)");
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {confirmed();}
		});
		GridBagConstraints gbc_confirmButton = new GridBagConstraints();
		gbc_confirmButton.insets = new Insets(0, 0, 0, 5);
		gbc_confirmButton.gridx = 2;
		gbc_confirmButton.gridy = 3;
		getContentPane().add(confirmButton, gbc_confirmButton);
		
		JButton cancelButton = new JButton("取消(ESC)");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {canceled();}
		});
		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.gridx = 3;
		gbc_cancelButton.gridy = 3;
		getContentPane().add(cancelButton, gbc_cancelButton);
		
		//注册快捷键
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
		dispose();		
	}
	
}