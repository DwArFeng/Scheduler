package com.dwarfeng.scheduler.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.TreePath;

import com.dwarfeng.func.gui.JAdjustableBorderPanel;
import com.dwarfeng.func.gui.JMenuItemAction;
import com.dwarfeng.func.gui.JOutOnlyConsolePanel;
import com.dwarfeng.func.io.CT;
import com.dwarfeng.scheduler.core.RunnerQueue;
import com.dwarfeng.scheduler.core.Scheduler;
import com.dwarfeng.scheduler.info.AppearanceInfo;
import com.dwarfeng.scheduler.io.ConfigHelper;
import com.dwarfeng.scheduler.io.ProjectHelper;
import com.dwarfeng.scheduler.project.Project;
import com.dwarfeng.scheduler.project.Tag;
import com.dwarfeng.scheduler.typedef.desint.Editable;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;


public class SchedulerGui extends JFrame {
	
	private static final long serialVersionUID = -1589971634374841967L;
	
	/**����̨*/
	private JOutOnlyConsolePanel console;
	/**�ɵ����1*/
	private JAdjustableBorderPanel mainPanel_1;
	/**�ɵ����0*/
	private JAdjustableBorderPanel mainPanel_0;
	/**������*/
	private JProjectTree projectTree;
	/**�������*/
	private JDesktopPaneS desktopPane;
	private JTextField serachTextField;
	private TagListModel tagListModel;
	private JList<TagToStringObject> tagList;
	/**�������*/
	private JPanel functionPanel;
	
	/**����̨�����ر��*/
	private boolean consoleVisible;
	/**�����������ر��*/
	private boolean projectTreeVisible;
	/**���Ա�����ر��*/
	private boolean paramVisible;
	/**���ܰ�����ر��*/
	private boolean functionPanelVisible;
	/**�Ƿ�ȫ����ʾ�ı��*/
	private boolean fullScreen;

	private SchedulerMenu menuBar;
	
	/**
	 * ����һ���������ģ�����Ĭ��������Ե������档
	 * <p> �÷����������ڲ����ã�ֱ�ӵ��õĻ��׳�{@linkplain NullPointerException}
	 * @throws IOException ͨ���쳣��
	 */
	protected SchedulerGui() throws IOException{
		this(null);
	}
	/**
	 * ����һ������ָ��������Ե������档
	 * @param appearanceSet ָ����������Ժϼ���
	 * @param Scheduler.getInstance() ���������ġ�
	 * @throws IOException  ͨ���쳣��
	 * @throws NullPointerException ����δָ����
	 */
	public SchedulerGui(AppearanceInfo appearanceSet) throws IOException{
		//�жϳ��������Ƿ�Ϊ��
		if(Scheduler.getInstance() == null) throw new NullPointerException("Context can't be null");
		init(appearanceSet == null ? ConfigHelper.createDefaultAppearanceInfo() : appearanceSet);
	}
	
	/*
	 * 
	 * �����ǽ����йط�����
	 * 
	 */
	
	/**
	 * �������������ڵĹ�������
	 * @return
	 */
	public JProjectTree getProjectTree(){
		return this.projectTree;
	}
	/**
	 * ����������塣
	 * @return �������������塣
	 */
	public JDesktopPaneS getDesktopPane(){
		return this.desktopPane;
	}
	/**
	 * ���½������ݣ�һ���ڳ�����ָʾ����ʱ��Ҫ���á�
	 * <p>����˳��Ϊ
	 * <br> 1�� ���ñ��⡣
	 * <br> 2�� ���ù���������ģ�͡�
	 * <br>	3�� ���ư�ť��
	 */
	public void refreshData(){
		resetTitle();
		projectTree.setProject(Scheduler.getInstance().getFrontProject());
		menuBar.resetMenu();
	}
	
	private void resetTitle(){
		String str = "Scheduler " + Scheduler.getInstance().getShortVersion();
		try{
			if(Scheduler.getInstance().getFrontProject() != null)
				str += " - " + ProjectHelper.getProjectFile(Scheduler.getInstance().getFrontProject()).getAbsolutePath();
		}catch(IllegalStateException e){}
		setTitle(str);
	}
	
//	private void setTagPanelEnabled(boolean aFlag){
//		this.tagList.setEnabled(aFlag);
//	}
	
	private void projectTreeValueChanged(TreePath selPath) {
		//TODO ��Ҫ�ڽ����İ汾�������ơ�
//		//�������Tag�б��е�����
//		setTagPanelEnabled(false);
//		tagListModel.removeAll();
//		if(selPath == null) return;
//		Object object = selPath.getLastPathComponent();
//		if(object instanceof DefaultMutableTreeNode){
//			Object userObject = ((DefaultMutableTreeNode) object).getUserObject();
//			//���node�еĶ������ڱ�ǩ����
//			if(userObject instanceof Tagable){
//				setTagPanelEnabled(true);
//				Tagable tagable = (Tagable) userObject;
//				Tag[] tags = tagable.getTags();
//				tagListModel.addTags(tags);
//			}
//		}		
	}
	
	
	/**
	 * ��ȡ����̨�Ƿ�ɼ���
	 * @return ����̨�Ƿ�ɼ���
	 */
	private boolean isConsoleVisible(){
		return consoleVisible;
	}
	/**
	 * ���ÿ���̨�Ƿ�ɼ���
	 * @param aFlag ����̨�Ƿ�ɼ���
	 */
	private void setConsoleVisible(boolean aFlag){
		this.consoleVisible = aFlag;
		mainPanel_1.setSouthVisible(aFlag && !fullScreen);
	}
	/**
	 * 
	 * @return
	 */
	private boolean isProjectTreeVisible(){
		return this.projectTreeVisible;
	}
	/**
	 * 
	 * @param aFlag
	 * @return
	 */
	private void setProjectTreeVisible(boolean aFlag){
		this.projectTreeVisible = aFlag;
		mainPanel_0.setWestVisible(aFlag && !fullScreen);
	}
	/**
	 * 
	 * @return
	 */
	private boolean isPrarmVisible(){
		return this.paramVisible;
	}
	/**
	 * 
	 * @param aFlag
	 */
	private void setParamVisible(boolean aFlag){
		this.paramVisible = aFlag;
		mainPanel_0.setEastVisible(aFlag && !fullScreen);
	}
	/**
	 * 
	 * @return
	 */
	private boolean isFunctionPanelVisible(){
		return this.functionPanelVisible;
	}
	/**
	 * 
	 * @param aFlag
	 */
	private void setFunctionPanelVisible(boolean aFlag){
		this.functionPanelVisible = aFlag;
		functionPanel.setVisible(aFlag && !fullScreen);
	}
	/**
	 * 
	 * @return
	 */
	private boolean isFullScreen(){
		return this.fullScreen;
	}
	/**
	 * 
	 * @param aFlag
	 */
	private void setFullScreen(boolean aFlag){
		this.fullScreen = aFlag;
		setConsoleVisible(isConsoleVisible());
		setProjectTreeVisible(isProjectTreeVisible());
		setParamVisible(isPrarmVisible());
		setFunctionPanelVisible(isFunctionPanelVisible());
	}
	
	/**
	 * ��ȡ��ǰ�����������Լ���
	 * @return ��ǰ�����������Լ���
	 */
	public AppearanceInfo getAppearanceInfo(){
		int console = mainPanel_1.getSouthPreferredValue();
		int projectTree = mainPanel_0.getWestPreferredValue();
		int param = mainPanel_0.getEastPreferredValue();
		
		int frameExtension = getExtendedState();
		int frameWidth = getWidth();
		int frameHeight = getHeight();
		
		if(getExtendedState() == MAXIMIZED_BOTH){
			frameWidth = 800;
			frameHeight = 600;
		}else if(getExtendedState() == MAXIMIZED_HORIZ){
			frameWidth = 800;
		}else if(getExtendedState() == MAXIMIZED_VERT){
			frameHeight = 600;
		}
		
		return new AppearanceInfo
				.Productor()
				.frameExtension(frameExtension)
				.frameWidth(frameWidth)
				.frameHeight(frameHeight)
				.consoleVisible(consoleVisible)
				.projectTreeVisible(projectTreeVisible)
				.paramVisible(paramVisible)
				.functionPanelVisible(functionPanelVisible)
				.consoleHeight(console)
				.projectTreeWidth(projectTree)
				.paramWidth(param)
				.product();
	}

	
	private void init(AppearanceInfo appearanceSet) throws IOException {
		//��ʼ����Ա������
		//this.model = MODEL_OVERALL; XXX
		this.consoleVisible = appearanceSet.isConsoleVisible();
		this.projectTreeVisible = appearanceSet.isProjectTreeVisible();
		this.paramVisible = appearanceSet.isParamVisible();
		this.functionPanelVisible = appearanceSet.isFunctionPanelVisible();
		this.fullScreen = false;
		
		//������������
		setIconImage(ImageIO.read(Scheduler.class.getResource("/resource/sys/scheduler_small.png")));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(appearanceSet.getFrameWidth(), appearanceSet.getFrameHeight());
		setExtendedState(appearanceSet.getFrameExtension());
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						Scheduler.getInstance().exitProgram();
					}
				};
				RunnerQueue.invoke(runnable);
			}
		});
		
		//��ʼ����Ա�Լ����ó�Ա����
		mainPanel_0 = new JAdjustableBorderPanel();
		mainPanel_0.setEastMinValue(150);
		mainPanel_0.setEastPreferredValue(appearanceSet.getParamWidth());
		mainPanel_0.setWestMinValue(150);
		mainPanel_0.setWestPreferredValue(appearanceSet.getProjectTreeWidth());
		mainPanel_0.setSeperatorThickness(5);
		mainPanel_0.setSouth(this);
		mainPanel_0.setBorder(new TitledBorder(null, "\u64CD\u4F5C\u9762\u677F", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		mainPanel_0.setWestEnabled(true);
		mainPanel_0.setEastEnabled(true);
		getContentPane().add(mainPanel_0, BorderLayout.CENTER);
		setProjectTreeVisible(isProjectTreeVisible());
		setParamVisible(isPrarmVisible());
		
		mainPanel_1 = new JAdjustableBorderPanel();
		mainPanel_1.setSeperatorThickness(5);
		mainPanel_1.setSouthPreferredValue(appearanceSet.getConsoleHeight());
		mainPanel_1.setSouthVisible(isConsoleVisible());
		mainPanel_1.setSouthEnabled(true);
		mainPanel_0.add(mainPanel_1, BorderLayout.CENTER);
		setConsoleVisible(isConsoleVisible());
		
		console = new JOutOnlyConsolePanel();
		mainPanel_1.add(console, BorderLayout.SOUTH);
		
		desktopPane = new JDesktopPaneS();
		mainPanel_1.add(desktopPane, BorderLayout.CENTER);
		
		JPanel treePanel = new JPanel();
		mainPanel_0.add(treePanel, BorderLayout.WEST);
		treePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		treePanel.add(scrollPane, BorderLayout.CENTER);
		
		projectTree = new JProjectTree();
		projectTree.setRootVisible(false);
		projectTree.setModel(null);
		projectTree.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				projectTreeValueChanged(e.getNewLeadSelectionPath());
			}
		});
		projectTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
		        int selRow = projectTree.getRowForLocation(e.getX(), e.getY());
		        //��굥��ʱ�����ĵ���
		        if(selRow != -1) {
		        	Object obj = projectTree.getSelectionPath().getLastPathComponent();
		             if(e.getClickCount() == 1) {
		                 //projectTreeSingleClick(selRow, selPath);
		             }
		             //���˫�������ĵ���
		             else if(e.getClickCount() == 2) {
		            	 if(obj instanceof Editable){
		            		 desktopPane.edit((Editable) obj);
		            		 return;
		            	 }
		             }
		         }
			}
			@Override
			public void mousePressed(MouseEvent e){
		        TreePath selPath = projectTree.getPathForLocation(e.getX(), e.getY());
		        projectTree.setSelectionPath(selPath);
				checkAndShowPopup(e);
			}
			@Override
			public void mouseReleased(MouseEvent e){
		        TreePath selPath = projectTree.getPathForLocation(e.getX(), e.getY());
		        projectTree.setSelectionPath(selPath);
				checkAndShowPopup(e);
			}
			private void showMenu(MouseEvent e,JPopupMenu popup) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
			private void checkAndShowPopup(MouseEvent e) {
				if(e.isPopupTrigger()){
					TreePath path = projectTree.getSelectionPath();
					//����ڹ������ϵ�ĳ���ڵ���ʱ���еĵ���
					if(path != null){
						Object obj = path.getLastPathComponent();
						if(obj instanceof PopupInTree){
							if(((PopupInTree) obj).getJPopupMenu(projectTree) != null)
							showMenu(e,((PopupInTree) obj).getJPopupMenu(projectTree));
						}
					}
					//����ڹ������ϵĿհ�������ʱ���еĵ���
					else{
						//TODO
					}
				}					
			}
		});
		scrollPane.setViewportView(projectTree);
		
		JAdjustableBorderPanel mainPanel_2 = new JAdjustableBorderPanel();
		mainPanel_2.setNorthPreferredValue(200);
		mainPanel_2.setNorthMinValue(100);
		mainPanel_2.setNorthEnabled(true);
		mainPanel_0.add(mainPanel_2, BorderLayout.EAST);
		
		JPanel tagsPanel_1 = new JPanel();
		mainPanel_2.add(tagsPanel_1, BorderLayout.NORTH);
		tagsPanel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel tagsPanel_2 = new JPanel();
		tagsPanel_2.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		tagsPanel_1.add(tagsPanel_2, BorderLayout.NORTH);
		tagsPanel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel addTagLabel = new JLabel("�±�ǩ");
		tagsPanel_2.add(addTagLabel, BorderLayout.WEST);
		
		JScrollPane tagsListScrollPane = new JScrollPane();
		tagsPanel_1.add(tagsListScrollPane, BorderLayout.CENTER);
		
		tagList = new JList<TagToStringObject>();
		tagListModel = new TagListModel();
		tagList.setModel(tagListModel);
		tagsListScrollPane.setViewportView(tagList);
		
		
		functionPanel = new JPanel();
		functionPanel.setPreferredSize(new Dimension(10, 90));
		getContentPane().add(functionPanel, BorderLayout.NORTH);
		setFunctionPanelVisible(isFunctionPanelVisible());
		functionPanel.setLayout(new BorderLayout(0, 0));
		
		JTimer timer = new JTimer();
		functionPanel.add(timer, BorderLayout.EAST);
		timer.setBorder(new TitledBorder(null, "\u65F6\u949F", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		GridBagLayout gridBagLayout = (GridBagLayout) timer.getLayout();
		gridBagLayout.columnWidths = new int[]{139};
		
		JPanel toolFunctionPanel = new JPanel();
		toolFunctionPanel.setBorder(new TitledBorder(null, "\u5DE5\u5177\u680F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		functionPanel.add(toolFunctionPanel, BorderLayout.CENTER);
		toolFunctionPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel tooBarPanel_0 = new JPanel();
		toolFunctionPanel.add(tooBarPanel_0, BorderLayout.NORTH);
		tooBarPanel_0.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		JToolBar typeToolBar = new JToolBar();
		typeToolBar.setBorder(null);
		tooBarPanel_0.add(typeToolBar);
		
		JButton noteTypeButton = new JButton("");
		noteTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		noteTypeButton.setIcon(new ImageIcon(this.getClass().getResource("/resource/toolBar_note.png")));
		typeToolBar.add(noteTypeButton);
		
		JPanel combineFunctionPanel = new JPanel();
		toolFunctionPanel.add(combineFunctionPanel, BorderLayout.SOUTH);
		combineFunctionPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel searchPanel = new JPanel();
		combineFunctionPanel.add(searchPanel, BorderLayout.EAST);
		searchPanel.setLayout(new BorderLayout(0, 0));
		
		serachTextField = new JTextField();
		searchPanel.add(serachTextField, BorderLayout.CENTER);
		serachTextField.setColumns(10);
		
		JComboBox<String> serachTypeComboBox = new JComboBox<String>();
		searchPanel.add(serachTypeComboBox, BorderLayout.WEST);
		serachTypeComboBox.setPreferredSize(new Dimension(80, 28));
		
		JPanel toolBarPanel_1 = new JPanel();
		combineFunctionPanel.add(toolBarPanel_1, BorderLayout.CENTER);
		toolBarPanel_1.setLayout(new BoxLayout(toolBarPanel_1, BoxLayout.X_AXIS));
		
		menuBar = new SchedulerMenu();
		setJMenuBar(menuBar);
	
		
		
		//��������
		refreshData();
	}
	
	
	
	
	/**
	 * �˵���
	 * @author DwArFeng
	 * @since 1.8
	 */
	private class SchedulerMenu extends JMenuBar{
		
		private static final long serialVersionUID = -3601437957175173503L;
		
		private JMenuItem save;
		private JMenuItem close;
		private JMenuItem saveAs;

		public SchedulerMenu() throws IOException{
			JMenu fileMenu = new JMenu("�ļ�(F)");
			fileMenu.setMnemonic('F');
			add(fileMenu);
			
			fileMenu.add(new JMenuItemAction(
					null,	//TODO
					"�½�(N)", 
					"�½�һ�������ļ�", 
					KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK), 
					KeyEvent.VK_N,
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							JFileChooser chooser = new JFileChooserS(Scheduler.getInstance().getArchivePath());
							chooser.setAcceptAllFileFilterUsed(false);
							chooser.setFileFilter(new FileNameExtensionFilter("�ƻ��������ļ�", "sch"));
							chooser.showDialog(SchedulerGui.this, "ѡ��");
							File temp = chooser.getSelectedFile();
							if(temp == null) return;		//���ѡ����ȡ�����򷵻ء�
							File file = new File(temp.getAbsolutePath().split("\\.")[0] + ".sch");
							if(file.exists()){
								int i = JOptionPane.showConfirmDialog(
										SchedulerGui.this,
										file.getName() + "�Ѿ����ڣ��Ƿ���Ҫ�滻", 
										"�滻�ļ�ȷ��",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.WARNING_MESSAGE, 
										null
								);
								if(i == JOptionPane.NO_OPTION) return;
							}
							Project fp = Scheduler.getInstance().getFrontProject();
							Scheduler.getInstance().setFrontProject(null);
							Runnable runnable = new Runnable() {
								@Override
								public void run() {
									/*
									 * ˳�����ߴ������ĵ�������ʾΪǰ̨�����ű�����һ�����̣��ٱ��浱ǰ���̡�
									 * ���������µĹ��̽�������̵�ʱ������ڽ����ϡ�
									 * �ȱ�����ĵ���������ͨ����˵�����ĵ��ǱȽϴ�ģ����ݱȽ϶�ģ��ȱ�����Խ���
									 * ���ݶ�ʧ�ķ��ա� 
									 */
									CT.trace("���ڴ������ļ���" + file.getAbsolutePath());
									//�ȴ���
									Project project = Scheduler.getInstance().createNewProject(file);
									//����ʾΪǰ̨
									Scheduler.getInstance().setFrontProject(project);
									//�ͷ���һ�����̵ı༭����
									Scheduler.getInstance().disposeEditor(fp);
									//����½����ļ�·�������ǵ�ǰ���̵�·�������������Ҫ���浱ǰ���ĵ������򱣴�
									if(		
										fp == null ||
										!ProjectHelper.getProjectFile(fp).equals(file)
									)
									Scheduler.getInstance().saveProject(fp);
									//�ر���һ�����̡�
									Scheduler.getInstance().closeProject(fp);
									//���浱ǰ����
									Scheduler.getInstance().saveProject(project);
								}
							};
							RunnerQueue.invoke(runnable);
						}
					}
			));
			fileMenu.add(new JMenuItemAction(
					null,	//TODO
					"��(O)", 
					"��һ�������ļ�", 
					KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK),
					KeyEvent.VK_O,
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							JFileChooser chooser = new JFileChooserS(Scheduler.getInstance().getArchivePath());
							chooser.setMultiSelectionEnabled(false);
							chooser.setAcceptAllFileFilterUsed(false);
							chooser.setFileFilter(new FileNameExtensionFilter("�ƻ������������ļ�", "sch"));
							chooser.showOpenDialog(SchedulerGui.this);
							File file = chooser.getSelectedFile();
							//���������ȡ����ť����û��ѡ���κ��ļ����򷵻ء�
							if(file == null) return;
							Project fp = Scheduler.getInstance().getFrontProject();
							Scheduler.getInstance().setFrontProject(null);
							Runnable runnable = new Runnable() {
								@Override
								public void run() {
									/*
									 * �������������򿪵��ļ�����������ô�������κβ�����
									 * ���ࣺ Ϊ�˾��콫�µĹ��̳��ָ��û����ȼ��ع��̣��ٱ���ǰһ�����̡�
									 */
									//�����������
									for(Project al : ProjectHelper.getAssociatedProjects()){
										if(ProjectHelper.getProjectFile(al).equals(file)){
											CT.trace("��⵽�ļ��Ѿ���");
											Scheduler.getInstance().setFrontProject(al);
											return;
										}
									}
									//�ȼ��ع���
									Project project = Scheduler.getInstance().loadProject(file);
									//��������ʾ��ǰ̨
									Scheduler.getInstance().setFrontProject(project);
									//�ر���һ������
									Scheduler.getInstance().disposeEditor(fp);
									Scheduler.getInstance().saveProject(fp);
									Scheduler.getInstance().closeProject(fp);
								}
							};
							RunnerQueue.invoke(runnable);
						}
					}
			));
			fileMenu.addSeparator();
			close = fileMenu.add(new JMenuItemAction(
					null, 		//TODO
					"�ر�(C)", 
					"�رյ�ǰ����", 
					KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK), 
					KeyEvent.VK_C, 
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Project fp = Scheduler.getInstance().getFrontProject();
							Scheduler.getInstance().setFrontProject(null);
							Runnable runnable = new Runnable() {
								@Override
								public void run() {
									// �ر�ǰһ������
									Scheduler.getInstance().disposeEditor(fp);
									Scheduler.getInstance().saveProject(fp);
									Scheduler.getInstance().closeProject(fp);
								}
							};
							RunnerQueue.invoke(runnable);
						}
					}
			));
			fileMenu.addSeparator();
			save = fileMenu.add(new JMenuItemAction(
					null, 		//TODO
					"����(S)",
					"���浱ǰ����", 
					KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK),
					KeyEvent.VK_S,
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Runnable runnable = new Runnable() {
								@Override
								public void run() {
									Scheduler.getInstance().saveProject(Scheduler.getInstance().getFrontProject());
								}
							};
							RunnerQueue.invoke(runnable);
						}
					}
			));
			saveAs = fileMenu.add(new JMenuItemAction(
					null,		//TODO
					"���Ϊ(A)", 
					"����ǰ���̴洢Ϊ����ļ�", 
					null, 
					KeyEvent.VK_A, 
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							JFileChooser chooser = new JFileChooserS(Scheduler.getInstance().getArchivePath());
							chooser.setAcceptAllFileFilterUsed(false);
							chooser.setFileFilter(new FileNameExtensionFilter("�ƻ��������ļ�", "sch"));
							chooser.showDialog(SchedulerGui.this, "ѡ��");
							File temp = chooser.getSelectedFile();
							if(temp == null) return;		//���ѡ����ȡ�����򷵻ء�
							File file = new File(temp.getAbsolutePath().split("\\.")[0] + ".sch");
							if(file.exists()){
								int i = JOptionPane.showConfirmDialog(
										SchedulerGui.this,
										file.getName() + "�Ѿ����ڣ��Ƿ���Ҫ�滻", 
										"�滻�ļ�ȷ��",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.WARNING_MESSAGE, 
										null
								);
								if(i == JOptionPane.NO_OPTION) return;
							}
							Project fp = Scheduler.getInstance().getFrontProject();
							File backup = ProjectHelper.getProjectFile(fp);
							CT.trace("����ӳ�����ļ���" + file.getAbsolutePath());
							/*
							 * ������������Ϊ���ļ����������ļ�����ʱ���൱�ڼ򵥵ı��档
							 * ��������������߳��и���ӳ���ļ��������Ҫ���ܹ����లȫ����
							 */
							//����ӳ���ļ�
							if(!ProjectHelper.getProjectFile(fp).equals(file)){
								try{
									ProjectHelper.setProjectFile(fp, file);
								}catch(Exception ex0){
									ex0.printStackTrace();
									CT.trace("�����ļ�ӳ��ʧ�ܣ����ڻ�ԭ�ļ�ӳ��");
									try{
										ProjectHelper.setProjectFile(fp, backup);
									}catch(Exception ex1){
										ex1.printStackTrace();
										CT.trace("��ԭӳ��ʧ�ܣ����ڳ��Թر��ļ�");
										Scheduler.getInstance().closeProject(fp);
									}
									return;
								}
							}
							//ͬ�������ĵ�
							Runnable runnable = new Runnable() {
								@Override
								public void run() {
									//�������µĹ���Ϊǰ̨
									Scheduler.getInstance().setFrontProject(fp);
									//�����ļ�
									Scheduler.getInstance().saveProject(fp);
								}
							};
							RunnerQueue.invoke(runnable);
						}
					}
			));
			fileMenu.addSeparator();
			fileMenu.add(new JMenuItemAction(
					null, 
					"�˳�(X)", 
					"�˳���ǰ����", 
					null, 
					KeyEvent.VK_X, 
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Runnable runnable = new Runnable() {
								@Override
								public void run() {
									Scheduler.getInstance().exitProgram();
								}
							};
							RunnerQueue.invoke(runnable);
						}
					}
			));
			
			JMenu editMenu = new JMenu("�༭(E)");
			editMenu.setMnemonic('E');
			add(editMenu);
			
			JMenu toolMenu = new JMenu("����(T)");
			toolMenu.setMnemonic('T');
			add(toolMenu);
			
			toolMenu.add(new JMenuItemAction(
					null, //TODO
					"��ǩ����", 
					"�������������ļ��е����б�ǩ", 
					KeyStroke.getKeyStroke(KeyEvent.VK_T,InputEvent.CTRL_MASK), 
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(Scheduler.getInstance().getFrontProject() == null) return;
							new JTagManager(SchedulerGui.this,Scheduler.getInstance().getFrontProject()).setVisible(true);
						}
					}
			));
			
			JMenu windowMenu = new JMenu("����(W)");
			windowMenu.setMnemonic('W');
			add(windowMenu);
			
			windowMenu.add(new JMenuItemAction(
					ImageIO.read(Scheduler.class.getResource("/resource/menu/console.png")),
					"����̨", 
					"��ʾ/���ؿ���̨", 
					KeyStroke.getKeyStroke(KeyEvent.VK_L,InputEvent.CTRL_MASK), 
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {setConsoleVisible(!isConsoleVisible());}
			}));
			windowMenu.add(new JMenuItemAction(
					ImageIO.read(Scheduler.class.getResource("/resource/menu/projectTree.png")),
					"������", 
					"��ʾ/���ع�����", 
					null,
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {setProjectTreeVisible(!isProjectTreeVisible());}
			}));
			windowMenu.add(new JMenuItemAction(
					null,										//TODO
					"������", 
					"��ʾ/����������", 
					null,
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {setParamVisible(!isPrarmVisible());}
			}));
			windowMenu.add(new JMenuItemAction(
					ImageIO.read(Scheduler.class.getResource("/resource/menu/toolBar.png")),
					"������", 
					"��ʾ/���ع�����", 
					null,
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {setFunctionPanelVisible(!isFunctionPanelVisible());}
			}));
			windowMenu.addSeparator();
			windowMenu.add(new JMenuItemAction(
					null,										//TODO
					"�������", 
					"���/��ԭ���洰��", 
					KeyStroke.getKeyStroke(KeyEvent.VK_M,InputEvent.CTRL_MASK), 
					new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {setFullScreen(!isFullScreen());}
			}));
		}

		private void resetMenu() {
			if(Scheduler.getInstance().getFrontProject() != null){
				save.setEnabled(true);
				saveAs.setEnabled(true);
				close.setEnabled(true);
			}else{
				save.setEnabled(false);
				saveAs.setEnabled(false);
				close.setEnabled(false);
			}
		}
	}
	
}





















class TagToStringObject{
	
	private Tag tag;
	
	public TagToStringObject(Tag tag){
		if(tag == null) throw new NullPointerException("Tag can't be null");
		this.tag = tag;
	}
	
	public Tag getTag(){
		return this.tag;
	}
	
	@Override
	public boolean equals(Object object){
		if(object == this) return true;
		if(object == null) return false;
		if(!(object instanceof TagToStringObject)) return false;
		return ((TagToStringObject) object).getTag().equals(tag);
	}
	
	@Override
	public int hashCode(){
		return tag.hashCode()*17;
	}
	
	@Override
	public String toString(){
		return tag.getName();
	}
	
}
class TagListModel extends DefaultListModel<TagToStringObject>{
	
	private static final long serialVersionUID = 1057594562902756290L;
	
	public TagListModel(){}
	
	public Tag getTag(int index){
		return super.getElementAt(index).getTag();
	}
	public void addTag(Tag tag){
		if(tag == null) return;
		super.addElement(new TagToStringObject(tag));
	}
	public void addTags(Tag[] tags){
		if(tags == null) return;
		for(Tag tag:tags){
			addTag(tag);
		}
	}
	public void removeTag(Tag tag){
		if(tag == null) return;
		super.removeElement(new TagToStringObject(tag));
	}
	public void removeTags(Tag[] tags){
		if(tags == null) return;
		for(Tag tag:tags){
			removeTag(tag);
		}
	}
	public void removeAll(){
		super.removeAllElements();
	}
}
class TagsComboModel extends DefaultComboBoxModel<TagToStringObject>{
	
	private static final long serialVersionUID = -2933842158036614995L;
	
	public TagsComboModel(){}
	
	public Tag getTag(int index){
		return super.getElementAt(index).getTag();
	}
	public void addTag(Tag tag){
		if(tag == null) return;
		super.addElement(new TagToStringObject(tag));
	}
	public void addTags(Tag[] tags){
		if(tags == null) return;
		for(Tag tag:tags){
			addTag(tag);
		}
	}
	public void removeTag(Tag tag){
		if(tag == null) return;
		super.removeElement(new TagToStringObject(tag));
	}
	public void removeTags(Tag[] tags){
		if(tags == null) return;
		for(Tag tag:tags){
			removeTag(tag);
		}
	}
	public void removeAll(){
		super.removeAllElements();
	}
}

class JFileChooserS extends JFileChooser{
	
	private static final long serialVersionUID = -7053063125100545534L;

	public JFileChooserS() {
		super();
	}
	public JFileChooserS(File currentDirectory){
		super(currentDirectory);
	}
	public JFileChooserS(String currentDirectoryPath){
		super(currentDirectoryPath);
	}
	
	@Override
	public void cancelSelection(){
		setSelectedFile(null);
		super.cancelSelection();
	}
}
