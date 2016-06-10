package com.dwarfeng.scheduler.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

import com.dwarfeng.func.gui.JImagePanel;
import com.sun.awt.AWTUtilities;

public class JSplashWindow extends JWindow {
	
	private static final long serialVersionUID = -5651799949948939410L;
	
	private JLabel message;
	
	/**
	 * ����һ��Ĭ�ϵ����ִ��塣
	 * @throws IOException ͨ���쳣����ȡ����ͼƬʱ���ܻᷢ������
	 */
	public JSplashWindow() throws IOException {
		
		//���ñ���͸��
		AWTUtilities.setWindowOpaque(this, false);
		//������������
		setSize(new Dimension(400, 250));
		getContentPane().setBackground(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		
		//�����ڲ�����
		JImagePanel imagePanel = new JImagePanel();
		imagePanel.setImage(ImageIO.read(SchedulerGui.class.getResource("/resource/sys/splash_background.png")));
		getContentPane().add(imagePanel);
		imagePanel.setLayout(null);
		
		message = new JLabel();
		message.setFont(new Font("SansSerif", Font.BOLD, 12));
		message.setHorizontalAlignment(SwingConstants.TRAILING);
		message.setBounds(162, 225, 228, 15);
		imagePanel.add(message);
	}
	
	public String getMessage(){
		return this.message.getText();
	}
	
	public void setMessage(String message){
		this.message.setText(message);
	}
}
