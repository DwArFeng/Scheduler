package com.dwarfeng.scheduler.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.dwarfeng.scheduler.core.Scheduler;
import com.dwarfeng.scheduler.info.AppearanceInfo;
import com.dwarfeng.scheduler.io.ConfigHelper;

import java.awt.Color;

/**
 * 崩溃界面。
 * <p> 程序发生致命的错误时弹出的界面。
 * @author DwArFeng
 * @since 1.8
 */
public class JCrashFrame extends JFrame {
	
	private static final long serialVersionUID = 5844155165507432308L;

	/**
	 * 生成一个默认的崩溃界面。
	 */
	public JCrashFrame(){
		this(null);
	}
	/**
	 * 生成一个具有指定外观设置的崩溃界面。
	 * @param appearanceSet 指定的外观设置。
	 */
	public JCrashFrame(AppearanceInfo appearanceSet){
		init(appearanceSet == null ? ConfigHelper.createDefaultAppearanceInfo() : appearanceSet);
	}
	
	/**
	 * 初始化方法。
	 * @param appearanceSet 指定的外观设置。
	 */
	private void init(AppearanceInfo appearanceSet){
		
		//设置自身属性
		setTitle("崩溃界面");
		setAlwaysOnTop(true);
		getContentPane().setBackground(new Color(70, 130, 180));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			setIconImage(ImageIO.read(Scheduler.class.getResource("/resource/sys/crash_small.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setSize(563, 371);
		setExtendedState(appearanceSet.getFrameExtension());
		setLocationRelativeTo(null);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 547, 15, 0};
		gridBagLayout.rowHeights = new int[]{50, 332, 30, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
	}
	
}
