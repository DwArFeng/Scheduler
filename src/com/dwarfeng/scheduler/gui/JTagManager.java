package com.dwarfeng.scheduler.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.dwarfeng.func.io.CT;
import com.dwarfeng.scheduler.project.Project;
import com.dwarfeng.scheduler.project.Tag;

public class JTagManager extends JDialog {

	private static final long serialVersionUID = 2133960867137583427L;
	
	private Project project;
	private JTable table;
	private JTextField textField;
	private class TagManagerTableModel extends DefaultTableModel{

		private static final long serialVersionUID = -6977772213581345304L;

		public TagManagerTableModel(){
			super(new Object[]{"注册ID","标题","描述","操作"},0);
			for(int i:project.getTagMap().getTagIds()){
				Tag tag = project.getTagMap().getTag(i);
				addRow(new Object[]{i,tag.getName(),tag.getDescribe(),true});
			}
			addRow(new Object[]{"","","",false});
		}
		
		@Override
		public boolean isCellEditable(int row,int column){
			return column != 0 && column != 3;
		}
	}
//	private class TagManagerTableCellRender extends DefaultTableCellRenderer implements TableCellRenderer{
//
//		@Override
//		public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus, int row,int column) {
//			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//			return this;
//		}
//		
//	}

	/**
	 * 
	 */
	public JTagManager() {
		this(null,null);
	}
	/**
	 * @param owner
	 */
	public JTagManager(Frame owner,Project project) {
		super(owner,true);
		this.project = project == null ? new Project.Productor().product() : project;
		init();
	}

	public Project getProject() {
		return project;
	}
	
	private void init(){
		setSize(new Dimension(550, 400));
		setLocationRelativeTo(getOwner());
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setType(Type.UTILITY);
		setTitle("标签管理");
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setModel(new TagManagerTableModel());
		CT.trace(table.getColumnClass(0));
		//table.setDefaultRenderer(table.getColumnClass(3), new TagManagerTableCellRender());
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("标签查找（正则模式）");
		panel.add(lblNewLabel, BorderLayout.WEST);
		
		textField = new JTextField();
		panel.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
	}

}
