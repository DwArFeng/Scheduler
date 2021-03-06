package com.dwarfeng.scheduler.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import com.dwarfeng.scheduler.io.ProjectIoHelper;
import com.dwarfeng.scheduler.project.Project;
import com.dwarfeng.scheduler.project.Tag;
import com.dwarfeng.scheduler.tools.ProjectOperationHelper;
import com.dwarfeng.scheduler.typedef.desint.Editable;
import com.dwarfeng.scheduler.typedef.funcint.Deleteable;
import com.dwarfeng.scheduler.typedef.funcint.Moveable;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;
import com.dwarfeng.scheduler.typedef.funcint.SerialParamSetable;


public class SchedulerGui extends JFrame {
	
	private static final long serialVersionUID = -6917871614497310250L;
	
	/**控制台*/
	private JOutOnlyConsolePanel console;
	/**可调面板1*/
	private JAdjustableBorderPanel mainPanel_1;
	/**可调面板0*/
	private JAdjustableBorderPanel mainPanel_0;
	/**工程树*/
	private JProjectTree projectTree;
	/**桌面面板*/
	private EditorDesktopPane desktopPane;
	private JTextField serachTextField;
	private TagListModel tagListModel;
	private JList<TagToStringObject> tagList;
	/**功能面板*/
	private JPanel functionPanel;
	
	/**控制台的隐藏标记*/
	private boolean consoleVisible;
	/**工程树的隐藏标记*/
	private boolean projectTreeVisible;
	/**属性表的隐藏标记*/
	private boolean paramVisible;
	/**功能版的隐藏标记*/
	private boolean functionPanelVisible;
	/**是否全屏显示的标记*/
	private boolean fullScreen;

	private SchedulerMenu menuBar;
	private JLabel lblNewLabel;
	
	/**
	 * 生成一个无上下文，具有默认外观属性的主界面。
	 * <p> 该方法仅仅在内部调用，直接调用的会抛出{@linkplain NullPointerException}
	 * @throws IOException 通信异常。
	 */
	protected SchedulerGui() throws IOException{
		this(null);
	}
	/**
	 * 生成一个具有指定外观属性的主界面。
	 * @param appearanceSet 指定的外观属性合集。
	 * @throws IOException  通信异常。
	 * @throws NullPointerException 上文未指定。
	 */
	public SchedulerGui(AppearanceInfo appearanceSet) throws IOException{
		//判断程序上文是否为空
		if(Scheduler.getInstance() == null) throw new NullPointerException("Context can't be null");
		init(appearanceSet == null ? ConfigHelper.createDefaultAppearanceInfo() : appearanceSet);
	}
	
	/*
	 * 
	 * 以下是界面有关方法。
	 * 
	 */
	
	/**
	 * 返回主界面所在的工程树。
	 * @return 主界面所在的工程树。
	 */
	public JProjectTree getProjectTree(){
		return this.projectTree;
	}
	/**
	 * 返回桌面面板。
	 * @return 主界面的桌面面板。
	 */
	public EditorDesktopPane getDesktopPane(){
		return this.desktopPane;
	}
	
	/**
	 * 更新界面数据，一般在程序变更指示工程时需要调用。
	 * <p>它的顺序为
	 * <br> 1、 重置标题。
	 * <br> 2、 重置工程树数据模型。
	 * <br> 3、 绘制按钮。
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
				str += " - " + ProjectIoHelper.getProjectFile(Scheduler.getInstance().getFrontProject()).getAbsolutePath();
		}catch(IllegalStateException e){}
		setTitle(str);
	}
	
//	private void setTagPanelEnabled(boolean aFlag){
//		this.tagList.setEnabled(aFlag);
//	}
	
	private void projectTreeValueChanged(TreePath selPath) {
		//TODO 需要在将来的版本中逐步完善。
//		//首先清除Tag列表中的内容
//		setTagPanelEnabled(false);
//		tagListModel.removeAll();
//		if(selPath == null) return;
//		Object object = selPath.getLastPathComponent();
//		if(object instanceof DefaultMutableTreeNode){
//			Object userObject = ((DefaultMutableTreeNode) object).getUserObject();
//			//如果node中的对象属于标签类型
//			if(userObject instanceof Tagable){
//				setTagPanelEnabled(true);
//				Tagable tagable = (Tagable) userObject;
//				Tag[] tags = tagable.getTags();
//				tagListModel.addTags(tags);
//			}
//		}		
	}
	
	
	/**
	 * 获取控制台是否可见。
	 * @return 控制台是否可见。
	 */
	private boolean isConsoleVisible(){
		return consoleVisible;
	}
	/**
	 * 设置控制台是否可见。
	 * @param aFlag 控制台是否可见。
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
	 * 获取当前界面的外观属性集。
	 * @return 当前界面的外观属性集。
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
		//初始化成员变量。
		this.consoleVisible = appearanceSet.isConsoleVisible();
		this.projectTreeVisible = appearanceSet.isProjectTreeVisible();
		this.paramVisible = appearanceSet.isParamVisible();
		this.functionPanelVisible = appearanceSet.isFunctionPanelVisible();
		this.fullScreen = false;
		
		//设置自身属性
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
		
		//初始化成员以及设置成员属性
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
		
		desktopPane = new EditorDesktopPane();
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
		        //鼠标单击时发生的调度
		        if(selRow != -1) {
		        	Object obj = projectTree.getSelectionPath().getLastPathComponent();
		             if(e.getClickCount() == 1) {
		                 //projectTreeSingleClick(selRow, selPath);
		             }
		             //鼠标双击发生的调度
		             else if(e.getClickCount() == 2) {
		            	 if(obj instanceof Editable){
		            		 ProjectOperationHelper.edit((Editable) obj);
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
			private void checkAndShowPopup(MouseEvent e) {
				if(e.isPopupTrigger()){
					TreePath path = projectTree.getSelectionPath();
					//鼠标在工程树上的某个节点点击时进行的调度
					if(path != null){
						Object obj = path.getLastPathComponent();
						if(obj instanceof PopupInTree) ProjectOperationHelper.showPopupInProjectTree(projectTree, (PopupInTree) obj, e.getX(), e.getY());
					}
					//鼠标在工程树上的空白区域点击时进行的调度
					else{
						//TODO 鼠标在工程树上的空白区域点击时进行的调度
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
		
		JLabel addTagLabel = new JLabel("新标签");
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
			@Override
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
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{784, 0};
		gbl_panel.rowHeights = new int[]{18, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblNewLabel = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 10, 0, 0);
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		menuBar = new SchedulerMenu();
		setJMenuBar(menuBar);
	
		//设置工程树的快捷键
		setProjectTreeAction();
		//更新数据
		refreshData();
	}
	
	
	
	private void setProjectTreeAction() {
		//设置快捷键
		InputMap projectTreeInputMap = projectTree.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap projectTreeActionMap = projectTree.getActionMap();
		
		//添加删除的Delete快捷键
		projectTreeInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "del");
		projectTreeActionMap.put("del", new AbstractAction() {
			private static final long serialVersionUID = -1378680131920293448L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(projectTree.getSelectionPath() == null || projectTree.getSelectionPath().getLastPathComponent() == null) return;
				
				Object obj = projectTree.getSelectionPath().getLastPathComponent();
				if(obj instanceof Deleteable){
					ProjectOperationHelper.requestDelete((Deleteable) obj);
				}
			}
		});
		//添加上移以及下移的快捷键
		projectTreeInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,InputEvent.CTRL_MASK),"mup");
		projectTreeActionMap.put("mup", new AbstractAction() {
			private static final long serialVersionUID = 605550244921615505L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(projectTree.getSelectionPath() == null || projectTree.getSelectionPath().getLastPathComponent() == null) return;

				Object obj = projectTree.getSelectionPath().getLastPathComponent();
				if(obj instanceof Moveable){
					ProjectOperationHelper.moveUp((Moveable) obj);
				}
			}
		});
		projectTreeInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,InputEvent.CTRL_MASK),"mdn");
		projectTreeActionMap.put("mdn", new AbstractAction() {
			private static final long serialVersionUID = -3146120382154775628L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(projectTree.getSelectionPath() == null || projectTree.getSelectionPath().getLastPathComponent() == null) return;

				Object obj = projectTree.getSelectionPath().getLastPathComponent();
				if(obj instanceof Moveable){
					ProjectOperationHelper.moveDown((Moveable) obj);
				}
			}
		});
		//添加更改序列参数的快捷键
		projectTreeInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0),"setp");
		projectTreeActionMap.put("setp", new AbstractAction() {
			private static final long serialVersionUID = -3571592601318400567L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(projectTree.getSelectionPath() == null || projectTree.getSelectionPath().getLastPathComponent() == null) return;

				Object obj = projectTree.getSelectionPath().getLastPathComponent();
				if(obj instanceof SerialParamSetable){
					ProjectOperationHelper.setSerialParam((SerialParamSetable) obj);
				}
			}
		});
		//添加回车键编辑对象的快捷键		
		projectTreeInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),"edit");
		projectTreeActionMap.put("edit", new AbstractAction() {
			private static final long serialVersionUID = -8545366270248966960L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(projectTree.getSelectionPath() == null || projectTree.getSelectionPath().getLastPathComponent() == null) return;

				Object obj = projectTree.getSelectionPath().getLastPathComponent();
				if(obj instanceof Editable){
					ProjectOperationHelper.edit((Editable) obj);
				}
			}
		});
	}




	/**
	 * 菜单栏
	 * @author DwArFeng
	 * @since 1.8
	 */
	private class SchedulerMenu extends JMenuBar{
		
		private static final long serialVersionUID = -3601437957175173503L;
		
		private JMenuItem save;
		private JMenuItem close;
		private JMenuItem saveAs;

		public SchedulerMenu() throws IOException{
			JMenu fileMenu = new JMenu("文件(F)");
			fileMenu.setMnemonic('F');
			add(fileMenu);
			
			fileMenu.add(new JMenuItemAction.Productor()
					.icon(new ImageIcon(Scheduler.class.getResource("/resource/menu/new.png")))
					.name("新建(N)")
					.description("新建一个工程文件")
					.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK))
					.mnemonic(KeyEvent.VK_N)
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							JFileChooser chooser = new JFileChooserS(Scheduler.getInstance().getArchivePath());
							chooser.setAcceptAllFileFilterUsed(false);
							chooser.setFileFilter(new FileNameExtensionFilter("计划管理工程文件", "sch"));
							chooser.showDialog(SchedulerGui.this, "选择");
							File temp = chooser.getSelectedFile();
							if(temp == null) return;		//如果选择了取消，则返回。
							//FIXME 这种分隔方式不对
							File file = new File(temp.getAbsolutePath().split("\\.")[0] + ".sch");
							if(file.exists()){
								int i = JOptionPane.showConfirmDialog(
										SchedulerGui.this,
										file.getName() + "已经存在，是否需要替换", 
										"替换文件确认",
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
									 * 顺序是线创建新文档，在显示为前台，接着保存上一个工程，再保存当前工程。
									 * 这样，用新的工程将会以最短的时间绘制在界面上。
									 * 先保存旧文档的意义是通常来说，旧文档是比较大的，内容比较多的，先保存可以降低
									 * 数据丢失的风险。 
									 */
									if(fp != null){
										boolean flag = false;
										//释放上一个工程的编辑窗口,如果新建的文件路径正好是当前工程的路径，则强制关闭编辑器
										CT.trace(ProjectIoHelper.getProjectFile(fp));
										CT.trace(file);
										CT.trace(ProjectIoHelper.getProjectFile(fp).equals(file));
										if(ProjectIoHelper.getProjectFile(fp).equals(file)){
											ProjectOperationHelper.forceDisposeEditor(fp);
											flag = true;
										}else{
											flag = ProjectOperationHelper.disposeEditor(fp,0);
										}
										//判断关闭时是否出现了异常。
										if(!flag){
											//重新将前台文件设置为fp
											Scheduler.getInstance().setFrontProject(fp);
											//停止执行后续代码
											return;
										}else{
											CT.trace("正在创建新文件：" + file.getAbsolutePath());
											//先创建
											Project project = ProjectOperationHelper.createNewProject(file);
											//再显示为前台
											Scheduler.getInstance().setFrontProject(project);
											//如果新建的文件路径正好是当前工程的路径，则根本不需要保存当前的文档，否则保存
											if(	!ProjectIoHelper.getProjectFile(fp).equals(file)){
												ProjectOperationHelper.saveProject(fp);
											}
											//关闭上一个工程。
											ProjectOperationHelper.closeProject(fp);
											//保存当前工程
											ProjectOperationHelper.saveProject(project);
										}
									}else{
										CT.trace("正在创建新文件：" + file.getAbsolutePath());
										//先创建
										Project project = ProjectOperationHelper.createNewProject(file);
										//再显示为前台
										Scheduler.getInstance().setFrontProject(project);
									}
								}
							};
							RunnerQueue.invoke(runnable);
						}
					})
					.product()
			);
			
			fileMenu.add(new JMenuItemAction.Productor()
					.icon(new ImageIcon(Scheduler.class.getResource("/resource/menu/open.png")))
					.name("打开(O)")
					.description("打开一个工程文件")
					.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK))
					.mnemonic(KeyEvent.VK_O)
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							JFileChooser chooser = new JFileChooserS(Scheduler.getInstance().getArchivePath());
							chooser.setMultiSelectionEnabled(false);
							chooser.setAcceptAllFileFilterUsed(false);
							chooser.setFileFilter(new FileNameExtensionFilter("计划管理器工程文件", "sch"));
							chooser.showOpenDialog(SchedulerGui.this);
							File file = chooser.getSelectedFile();
							//如果按下了取消按钮或者没有选择任何文件，则返回。
							if(file == null) return;
							Project fp = Scheduler.getInstance().getFrontProject();
							Scheduler.getInstance().setFrontProject(null);
							Runnable runnable = new Runnable() {
								@Override
								public void run() {
									if(fp != null){
										/*
										 * 特殊情况：如果打开的文件就是自身，那么不进行任何操作。
										 * 进一步：	如果打开的工程已经被加载了，则不用重新加载。
										 * 其余： 为了尽快将新的工程呈现给用户，先加载工程，再保存前一个工程。
										 */
										//考虑特殊情况
										if(ProjectIoHelper.getProjectFile(fp).equals(file)){
											Scheduler.getInstance().setFrontProject(fp);
											return;
										}
										//如果不是以上的极特殊情况，则需要先关闭前一个工程的编辑器。
										boolean flag = ProjectOperationHelper.disposeEditor(fp,0);
										if(!flag){
											//如果前者的编辑器不能正常关闭，则还原前台工程，并返回。
											Scheduler.getInstance().setFrontProject(fp);
											return;
										}else{
											boolean ap = false;
											//进一步检测特殊情况
											for(Project al : ProjectIoHelper.getAssociatedProjects()){
												if(ProjectIoHelper.getProjectFile(al).equals(file)){
													CT.trace("检测到文件已经打开");
													Scheduler.getInstance().setFrontProject(al);
													ap = true;
													break;
												}
											}
											if(!ap){
												//先加载工程
												Project project = ProjectOperationHelper.loadProject(file);
												//将工程显示在前台
												Scheduler.getInstance().setFrontProject(project);
											}
											ProjectOperationHelper.saveProject(fp);
											ProjectOperationHelper.closeProject(fp);
										}
									}else{
										//先加载工程
										Project project = ProjectOperationHelper.loadProject(file);
										//将工程显示在前台
										Scheduler.getInstance().setFrontProject(project);
									}
								}
							};
							RunnerQueue.invoke(runnable);
						}
					})
					.product()
			);
			fileMenu.addSeparator();
			
			close = fileMenu.add(new JMenuItemAction.Productor()
					.name("关闭(C)")
					.description("关闭当前工程")
					.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK))
					.mnemonic(KeyEvent.VK_C)
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Project fp = Scheduler.getInstance().getFrontProject();
							Scheduler.getInstance().setFrontProject(null);
							Runnable runnable = new Runnable() {
								@Override
								public void run() {
									if(fp != null){
										boolean flag = ProjectOperationHelper.disposeEditor(fp,0);
										if(!flag){
											Scheduler.getInstance().setFrontProject(fp);
											return;
										}else{
											// 关闭前一个工程
											ProjectOperationHelper.saveProject(fp);
											ProjectOperationHelper.closeProject(fp);
										}
									}else{
										return;
									}
								}
							};
							RunnerQueue.invoke(runnable);
						}
					})
					.product()
			);
			fileMenu.addSeparator();
			
			save = fileMenu.add(new JMenuItemAction.Productor()
					.icon(new ImageIcon(Scheduler.class.getResource("/resource/menu/save.png")))
					.name("保存(S)")
					.description("保存当前工程")
					.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK))
					.mnemonic(KeyEvent.VK_S)
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Runnable runnable = new Runnable() {
								@Override
								public void run() {
									Project fp = Scheduler.getInstance().getFrontProject();
									if(fp != null) ProjectOperationHelper.saveProject(fp);
								}
							};
							RunnerQueue.invoke(runnable);
						}
					})
					.product()
			);
			
			saveAs = fileMenu.add(new JMenuItemAction.Productor()
					.icon(new ImageIcon(Scheduler.class.getResource("/resource/menu/saveAs.png")))
					.name("另存为(A)")
					.description("将当前工程存储为别的文件")
					.mnemonic(KeyEvent.VK_A)
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							JFileChooser chooser = new JFileChooserS(Scheduler.getInstance().getArchivePath());
							chooser.setAcceptAllFileFilterUsed(false);
							chooser.setFileFilter(new FileNameExtensionFilter("计划管理工程文件", "sch"));
							chooser.showDialog(SchedulerGui.this, "选择");
							File temp = chooser.getSelectedFile();
							if(temp == null) return;		//如果选择了取消，则返回。
							//FIXME 这种分隔方式不对
							File file = new File(temp.getAbsolutePath().split("\\.")[0] + ".sch");
							if(file.exists()){
								int i = JOptionPane.showConfirmDialog(
										SchedulerGui.this,
										file.getName() + "已经存在，是否需要替换", 
										"替换文件确认",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.WARNING_MESSAGE, 
										null
								);
								if(i == JOptionPane.NO_OPTION) return;
							}
							Project fp = Scheduler.getInstance().getFrontProject();
							File backup = ProjectIoHelper.getProjectFile(fp);
							if(fp != null){
								CT.trace("正在映射新文件：" + file.getAbsolutePath());
								/*
								 * 特殊情况：另存为的文件等于自身文件，这时候相当于简单的保存。
								 * 其它情况：在主线程中更改映射文件，这很重要，能规避许多安全问题
								 */
								//重新映射文件
								if(!ProjectIoHelper.getProjectFile(fp).equals(file)){
									try{
										ProjectIoHelper.setProjectFile(fp, file);
									}catch(Exception ex0){
										ex0.printStackTrace();
										CT.trace("更新文件映射失败，正在还原文件映射");
										try{
											ProjectIoHelper.setProjectFile(fp, backup);
										}catch(Exception ex1){
											ex1.printStackTrace();
											CT.trace("还原映射失败，正在尝试关闭文件");
											ProjectOperationHelper.closeProject(fp);
										}
										return;
									}
								}
								//同步保存文档
								Runnable runnable = new Runnable() {
									@Override
									public void run() {
										//设置最新的工程为前台
										Scheduler.getInstance().setFrontProject(fp);
										//保存文件
										ProjectOperationHelper.saveProject(fp);
									}
								};
								RunnerQueue.invoke(runnable);
								
							}
						}
					})
					.product()
			);
			
			fileMenu.addSeparator();
			
			fileMenu.add(new JMenuItemAction.Productor()
					.icon(new ImageIcon(Scheduler.class.getResource("/resource/menu/exit.png")))
					.name("退出(X)")
					.description("退出当前程序")
					.mnemonic(KeyEvent.VK_X)
					.listener(new ActionListener() {
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
					})
					.product()
			);
			
			JMenu editMenu = new JMenu("编辑(E)");
			editMenu.setMnemonic('E');
			add(editMenu);
			
			JMenu toolMenu = new JMenu("工具(T)");
			toolMenu.setMnemonic('T');
			add(toolMenu);
			
			toolMenu.add(new JMenuItemAction.Productor()
					.icon(new ImageIcon(Scheduler.class.getResource("/resource/menu/tag.png")))
					.name("标签管理(T)")
					.description("管理整个工程文件中的所有标签")
					.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_T,InputEvent.CTRL_MASK))
					.mnemonic(KeyEvent.VK_T)
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(Scheduler.getInstance().getFrontProject() == null) return;
							new JTagManager(SchedulerGui.this,Scheduler.getInstance().getFrontProject()).setVisible(true);
						}
					})
					.product()
			);
			
			JMenu windowMenu = new JMenu("窗口(W)");
			windowMenu.setMnemonic('W');
			add(windowMenu);
			
			windowMenu.add(new JMenuItemAction.Productor()
					.icon(new ImageIcon(Scheduler.class.getResource("/resource/menu/console.png")))
					.name("控制台(C)")
					.description("显示/隐藏控制台")
					.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_L,InputEvent.CTRL_MASK))
					.mnemonic(KeyEvent.VK_C)
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {setConsoleVisible(!isConsoleVisible());}
					})
					.product()
			);
			
			windowMenu.add(new JMenuItemAction.Productor()
					.icon(new ImageIcon(Scheduler.class.getResource("/resource/menu/projectTree.png")))
					.name("工程树(T)")
					.description("显示/隐藏工程树")
					.mnemonic(KeyEvent.VK_T)
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {setProjectTreeVisible(!isProjectTreeVisible());}
					})
					.product()
			);
			
			windowMenu.add(new JMenuItemAction.Productor()
					.icon(new ImageIcon(Scheduler.class.getResource("/resource/menu/paramPanel.png")))
					.name("属性栏(P)")
					.description("显示/隐藏属性栏")
					.mnemonic(KeyEvent.VK_P)
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {setParamVisible(!isPrarmVisible());}
					})
					.product()
			);
			
			windowMenu.add(new JMenuItemAction.Productor()
					.icon(new ImageIcon(Scheduler.class.getResource("/resource/menu/toolBar.png")))
					.name("工具栏(F)")
					.description("显示/隐藏工具栏")
					.mnemonic(KeyEvent.VK_F)
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {setFunctionPanelVisible(!isFunctionPanelVisible());}
					})
					.product()
			);
			
			windowMenu.addSeparator();
			
			windowMenu.add(new JMenuItemAction.Productor()
					.icon(new ImageIcon(Scheduler.class.getResource("/resource/menu/fullScreen.png")))
					.name("桌面最大化(M)")
					.description("最大化/还原桌面窗口")
					.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_M,InputEvent.CTRL_MASK))
					.mnemonic(KeyEvent.VK_M)
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {setFullScreen(!isFullScreen());}
					})
					.product()
			);
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
