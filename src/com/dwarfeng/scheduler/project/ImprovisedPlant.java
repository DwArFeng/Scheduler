package com.dwarfeng.scheduler.project;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.text.PlainDocument;

import com.dwarfeng.dwarffunction.gui.JAdjustableBorderPanel;
import com.dwarfeng.scheduler.core.Scheduler133;
import com.dwarfeng.scheduler.tools.PopupMenuActions;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectOutProjectTree;
import com.dwarfeng.scheduler.typedef.desint.AbstractEditor;
import com.dwarfeng.scheduler.typedef.desint.Editable;
import com.dwarfeng.scheduler.typedef.desint.Editor;
import com.dwarfeng.scheduler.typedef.exception.AttachmentException;
import com.dwarfeng.scheduler.typedef.funcint.Deleteable;
import com.dwarfeng.scheduler.typedef.funcint.Moveable;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;
import com.dwarfeng.scheduler.typedef.funcint.Searchable;
import com.dwarfeng.scheduler.typedef.funcint.SerialParam;
import com.dwarfeng.scheduler.typedef.funcint.SerialParamSetable;

/**
 * 
 * @author DwArFeng
 * @since 1.8
 */
public final class ImprovisedPlant extends AbstractObjectInProjectTree 
implements Editable, Deleteable, Moveable,PopupInTree,Searchable,SerialParamSetable {
	
	/**XML附件*/
	private final PlainTextAttachment attachment;
	/**序列参数*/
	private SerialParam serialParam;
	/**正文是否启用自动换行*/
	private boolean lineWrap;
	
	/**
	 * 即兴计划构造者。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private final PlainTextAttachment attachment;
		private boolean lineWrap = true;
		private SerialParam serialParam = new SerialParam.Productor().product();
		
		/**
		 * 生成一个新的构造者实例。
		 * @param attachment 指定的附件。
		 * @throws NullPointerException 传入的附件为<code>null</code>。
		 */
		public Productor(PlainTextAttachment attachment){
			//设置附件
			if(attachment == null) throw new NullPointerException("Attachment can't be null");
			this.attachment = attachment;
		}
		
		/**
		 * 设置是否自动换行。
		 * @param val 是否自动换行，<code>true</code>为自动换行。
		 * @return 构造器自身。
		 */
		public Productor linWrap(boolean val){
			this.lineWrap = val;
			return this;
		}
		
		/**
		 * 设置序列参数。
		 * @param val 指定的序列参数，如果为<code>null</code>，则序列参数将置为默认值。
		 * @return 构造器自身。
		 */
		public Productor serialParam(SerialParam val){
			if(val != null){
				this.serialParam = val;
			}else{
				this.serialParam = new SerialParam.Productor().product();
			}
			return this;
		}
		
		/**
		 * 由构造器的内容构造即兴计划。
		 * @return 由构造器构造的即兴计划。
		 */
		public ImprovisedPlant product(){
			return new ImprovisedPlant(attachment, lineWrap, serialParam);
		}
		
	}
	
	/**
	 * 设置一个拥有指定附件，指定序列参数，的即兴计划。
	 * @param attachment 指定的附件。
	 * @param lineWrap 是否自动换行。
	 * @param serialParam 指定的序列参数。
	 */
	private ImprovisedPlant(PlainTextAttachment attachment,boolean lineWrap,SerialParam serialParam) {
		super(false);
		//设置附件
		this.attachment = attachment;
		this.attachment.setContext(getRootProject());
		//设置序列参数
		this.serialParam = serialParam;
		this.serialParam.setContext(getRootProject());
		//设置是否自动换行
		this.lineWrap = lineWrap;
	}
	
	/**
	 * 是否自动换行。
	 * @return 文本是否自动换行。
	 */
	public boolean isLineWrap() {
		return lineWrap;
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#getObjectOutProjectTrees()
	 */
	@Override
	public Set<ObjectOutProjectTree> getObjectOutProjectTrees(){
		Set<ObjectOutProjectTree> set = new HashSet<ObjectOutProjectTree>();
		set.add(attachment);
		set.add(serialParam);
		return set;
	}

	/**
	 * 返回该即兴计划对象的文本附件。
	 * @return 即兴计划对象的文本附件。
	 */
	public PlainTextAttachment getTextAttachment(){
		return this.attachment;
	}
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#renderLabel(javax.swing.JLabel)
	 */
	@Override
	public void renderLabel(JLabel label) {
		label.setIconTextGap(8);
		label.setIcon(new ImageIcon(Scheduler133.class.getResource("/resource/tree/improvisedPlant.png")));
		label.setFont(new Font("SansSerif", Font.PLAIN, 12));
		label.setText(getSerialParam().getName());	
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.Deleteable#getConfirmWord()
	 */
	@Override
	public String getConfirmWord() {
		return 
				"即将删除：" + serialParam.getName() + "\n"
				+ "当前操作将会删除该文档，该操作不可恢复";
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#getEditorTitle()
	 */
	@Override
	public String getEditorTitle() {
		return getSerialParam().getName();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.PopupInTree#getJPopupMenu()
	 */
	@Override
	public JPopupMenu getJPopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		popup.add(PopupMenuActions.newEditItem("启动编辑器编辑该即兴计划", this));
		popup.add(PopupMenuActions.newParamSetItem("更改笔即兴计划的NTD属性", this));
		popup.add(PopupMenuActions.newDeleteItem("不可恢复地删除当前的即兴计划", this));
		return popup;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#setParent(javax.swing.tree.MutableTreeNode)
	 */
	@Override
	public void setParent(ObjectInProjectTree newParent){
		super.setParent(newParent);
		if(newParent instanceof ObjectInProject){
			attachment.setContext((ObjectInProject) newParent);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#newEditor()
	 */
	@Override
	public Editor<ImprovisedPlant> newEditor(){
		return new ImprovisedPlantEditor(this);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.SerialParamable#getSerialParam()
	 */
	@Override
	public SerialParam getSerialParam(){
		return this.serialParam == null ?
				new SerialParam.Productor().product() : serialParam;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.SerialParamable#setSerialParam(com.dwarfeng.scheduler.typedef.funcint.SerialParam)
	 */
	@Override
	public void setSerialParam(SerialParam serialParam){
		this.serialParam = serialParam == null ?
				new SerialParam.Productor().product() : serialParam;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#canInsert(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree)
	 */
	@Override
	protected boolean canInsert(ObjectInProjectTree newChild) {
		return false;
	}

	
	
	class ImprovisedPlantEditor extends AbstractEditor<ImprovisedPlant> {
		
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
				lineWrap = documentArea.getLineWrap();
				//变更序列参数
				SerialParam serialParam = getSerialParam();
				setSerialParam(new SerialParam.Productor()
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
				getTextAttachment().save(document);
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
			this.document = getTextAttachment().load();
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
			describeArea.setText(getSerialParam().getDescribe());
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
}
