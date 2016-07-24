package com.dwarfeng.scheduler.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * 规划器程序中的计时器控件。
 * @author DwArFeng
 * @since 1.8
 */
public class JTimer extends JPanel {

	private static final long serialVersionUID = 2754431666520587703L;
	private JLabel label_0;

	public JTimer() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{226, 0};
		gridBagLayout.rowHeights = new int[]{21, 18, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		label_0 = new JLabel("2016\u5E745\u67083\u65E5");
		label_0.setVerticalAlignment(SwingConstants.BOTTOM);
		label_0.setFont(new Font("宋体", Font.BOLD, 15));
		label_0.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_label_0 = new GridBagConstraints();
		gbc_label_0.anchor = GridBagConstraints.SOUTH;
		gbc_label_0.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_0.insets = new Insets(0, 0, 5, 0);
		gbc_label_0.gridx = 0;
		gbc_label_0.gridy = 0;
		add(label_0, gbc_label_0);
		
		JLabel label_1 = new JLabel("13:24:15");
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setFont(new Font("宋体", Font.BOLD, 20));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.NORTH;
		gbc_label_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 1;
		add(label_1, gbc_label_1);
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				Calendar cal = Calendar.getInstance();
				label_0.setText(cal.get(Calendar.YEAR) + "年" + (cal.get(Calendar.MONTH)+1) + "月" + cal.get(Calendar.DATE) + "日");
				label_1.setText(cal.get(Calendar.HOUR_OF_DAY ) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND));
			}
		}, 0, 250);
	}


}
