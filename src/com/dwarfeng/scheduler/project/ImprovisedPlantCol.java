package com.dwarfeng.scheduler.project;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.dwarfeng.dwarffunction.gui.JAdjustableBorderPanel;
import com.dwarfeng.scheduler.core.Scheduler133;
import com.dwarfeng.scheduler.core.module.ProjectIoHelper;
import com.dwarfeng.scheduler.core.module.Scpath;
import com.dwarfeng.scheduler.project.Im.ImTag;
import com.dwarfeng.scheduler.tools.PopupMenuActions;
import com.dwarfeng.scheduler.tools.UserInput;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectOutProjectTree;
import com.dwarfeng.scheduler.typedef.desint.AbstractEditor;
import com.dwarfeng.scheduler.typedef.desint.Editable;
import com.dwarfeng.scheduler.typedef.desint.Editor;
import com.dwarfeng.scheduler.typedef.exception.AttachmentException;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;
import com.dwarfeng.scheduler.typedef.funcint.SerialParam;
import com.sun.glass.events.KeyEvent;

/**
 * ���˼ƻ��б��ࡣ
 * <p> ��������ż��˼ƻ��б�ڵ㡣
 * @author DwArFeng
 * @since 1.8
 */
public final class ImprovisedPlantCol extends AbstractObjectInProjectTree
implements PopupInTree,Editable{

	/**���˼ƻ��б�Ĺ̶������XML�����ĵ�ַ*/
	public final static Scpath ATT_PATH = new Scpath("implants" + File.separator + "overall.xml");
	
	/**�����XML����*/
	private final XmlAttachment attachment;
	
	/**
	 * ���˼ƻ��ϼ��Ĺ����ߡ�
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private List<ImprovisedPlant> improvisedPlains = new ArrayList<ImprovisedPlant>();
		
		/**
		 * ����һ���µļ��˼ƻ��ϼ��Ĺ����ߡ�
		 */
		public Productor(){}
		
		/**
		 * ���ü��˼ƻ��б�
		 * @param val ���˼ƻ��б����Ϊ<code>null</code>���򽫼ƻ��б���Ϊ�ա�
		 * @return ����������
		 */
		public Productor improvisedPlains(List<ImprovisedPlant> val){
			if(val == null){
				this.improvisedPlains = new ArrayList<ImprovisedPlant>();
			}else{
				this.improvisedPlains = val;
			}
			return this;
		}
		
		/**
		 * �ɹ���������ָ���ļ��˼ƻ����ϡ�
		 * @return ����ļ��˼ƻ����ϡ�
		 */
		public ImprovisedPlantCol product(){
			return new ImprovisedPlantCol(improvisedPlains);
		}
		
	}
	
	/**
	 * ���ɾ���ָ��
	 * @param improvisedPlains
	 */
	private ImprovisedPlantCol(List<ImprovisedPlant> improvisedPlains){
		super(true);
		//���ø���
		this.attachment = new XmlAttachment(ATT_PATH);
		this.attachment.setContext(getRootProject());
		//��notebooks�е�����notebook��ӽ�����
		if(improvisedPlains != null){
			for(ImprovisedPlant improvisedPlain : improvisedPlains){
				if(improvisedPlain != null) add(improvisedPlain);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#getObjectOutProjectTrees()
	 */
	@Override
	public Set<ObjectOutProjectTree> getObjectOutProjectTrees(){
		Set<ObjectOutProjectTree> set = new HashSet<ObjectOutProjectTree>();
		set.add(attachment);
		return set;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree#renderLabel(javax.swing.JLabel)
	 */
	@Override
	public void renderLabel(JLabel label) {
		label.setIconTextGap(8);
		label.setIcon(new ImageIcon(Scheduler133.class.getResource("/resource/tree/improvisedPlantCol.png")));
		label.setFont(new Font("SansSerif", Font.BOLD, 14));
		label.setText("���˼ƻ�");	
	}

	/**
	 * ���ؼ��˼ƻ����XML������
	 * @return XML������
	 */
	public XmlAttachment getAttachment(){
		return this.attachment;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#getEditorTitle()
	 */
	@Override
	public String getEditorTitle() {
		return "���˼ƻ�";
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.funcint.PopupInTree#getJPopupMenu(com.dwarfeng.scheduler.gui.JProjectTree)
	 */
	@Override
	public JPopupMenu getJPopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		popup.add(PopupMenuActions.newEditItem("�����༭���༭�ñʼ�", this));
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
	 * @see com.dwarfeng.scheduler.typedef.desint.Editable#startEdit()
	 */
	@Override
	public Editor<ImprovisedPlantCol> newEditor(){
		return new ImprovisedPlantColEditor(this);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree#canInsert(com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree)
	 */
	@Override
	protected boolean canInsert(ObjectInProjectTree newChild) {
		return newChild instanceof ImprovisedPlant;
	}
	
	
	
	
	private class ImprovisedPlantColEditor extends AbstractEditor<ImprovisedPlantCol> {

		private static final long serialVersionUID = -233611733367519088L;
		
		/**�б��ģ��*/
		private DefaultListModel<Im> listModel;
		/**�����������*/
		private Random random;
		/**��ʾ��ӭ�ı��ı�ǩ*/
		private JLabel welcomeLabel;
		/**�༭��������*/
		private JPanel editPanel;
		private JPanel statusPanel;
		private JTextField textField;
		private JList<Im> imJList;
		/***/
		private JTextArea textArea;
		private JToggleButton excitingButton;
		private JToggleButton interestingButton;
		private JToggleButton funButton;
		private JToggleButton wantDoButton;
		private JButton deleteButton;
		private JButton plantButton;
		private JButton randomButton;
		
		private ImprovisedPlantColEditor(){
			super(new ImprovisedPlantCol.Productor().product(),0);

			try {
				editorInit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/**
		 * ����һ������ָ�����˼ƻ��ϼ��ļ��˼ƻ��ϼ��༭����
		 * @param editable ָ���ļ��˼ƻ��ϼ���
		 */
		public ImprovisedPlantColEditor(ImprovisedPlantCol editable) {
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
		 * @see com.dwarfeng.scheduler.typedef.desint.AbstractEditor#welcomeInit()
		 */
		@Override
		protected void welcomeInit() {
			setLayout(new BorderLayout(0, 0));
			
			welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
			welcomeLabel.setText("���ڶ�ȡ�ļ������Ժ�...");
			add(welcomeLabel, BorderLayout.CENTER);
			
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.AbstractEditor#prepareInit()
		 */
		@Override
		protected void prepareInit() throws Exception {
			Element element = getEditable().getAttachment().load().getRootElement();
			List<?> l = element.selectNodes("/imList/im");
			for(Object obj : l){
				if(obj instanceof Element){
					listModel.add(listModel.size(), genIm((Element) obj));
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.AbstractEditor#doWhenExceptionInInit(java.lang.Exception)
		 */
		@Override
		protected void doWhenExceptionInInit(Exception e) {
			// TODO Auto-generated method stub
			
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.AbstractEditor#doWhenExceptionInPrepare(java.lang.Exception)
		 */
		@Override
		protected boolean doWhenExceptionInPrepare(Exception e) {
			e.printStackTrace();
			return false;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.AbstractEditor#paramInit()
		 */
		@Override
		protected void paramInit() {
			listModel = new DefaultListModel<Im>();
			random = new Random();
			welcomeLabel = new JLabel();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.AbstractEditor#editorInit()
		 */
		@Override
		protected void editorInit() throws Exception {
			remove(welcomeLabel);
			setLayout(new BorderLayout(0, 0));
			
			editPanel = new JPanel();
			add(editPanel, BorderLayout.CENTER);
			editPanel.setLayout(new BorderLayout(0, 0));
			
			JPanel panel = new JPanel();
			panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			editPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			
			JAdjustableBorderPanel mp = new JAdjustableBorderPanel();
			mp.setSeperatorThickness(5);
			mp.setWestEnabled(true);
			panel.add(mp, BorderLayout.CENTER);
			
			JPanel listPanel = new JPanel();
			mp.add(listPanel, BorderLayout.WEST);
			listPanel.setLayout(new BorderLayout(0, 0));
			
			JScrollPane nodeScroll = new JScrollPane();
			listPanel.add(nodeScroll, BorderLayout.CENTER);
			
			imJList = new JList<Im>();
			imJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			imJList.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					Im im = imJList.getSelectedValue();
					if(im != null){
						deleteButton.setEnabled(true);
						plantButton.setEnabled(true);
						
						SimpleDateFormat df = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");//�������ڸ�ʽ
						
						StringBuilder sb = new StringBuilder();
						sb.append(im.getImText())
								.append("\n").append("\t")
								.append(df.format(im.getPublicDate()));
						
						textArea.setText(sb.toString());
						Set<ImTag> tags = im.getImTags();
						
						excitingButton.setSelected(tags.contains(ImTag.EXCITING));
						interestingButton.setSelected(tags.contains(ImTag.INTERESTING));
						funButton.setSelected(tags.contains(ImTag.FUN));
						wantDoButton.setSelected(tags.contains(ImTag.WANTDO));
					}else{
						deleteButton.setEnabled(false);
						plantButton.setEnabled(false);
					}
					
				}
			});
			imJList.setModel(listModel);
			nodeScroll.setViewportView(imJList);
			
			JPanel modifyPanel = new JPanel();
			mp.add(modifyPanel, BorderLayout.CENTER);
			modifyPanel.setLayout(new BorderLayout(0, 0));
			
			JPanel inputPanel = new JPanel();
			modifyPanel.add(inputPanel, BorderLayout.SOUTH);
			GridBagLayout gbl_inputPanel = new GridBagLayout();
			gbl_inputPanel.columnWidths = new int[]{0, 0, 0};
			gbl_inputPanel.rowHeights = new int[]{0, 0};
			gbl_inputPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
			gbl_inputPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			inputPanel.setLayout(gbl_inputPanel);
			
			textField = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.insets = new Insets(0, 0, 0, 5);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 0;
			gbc_textField.gridy = 0;
			inputPanel.add(textField, gbc_textField);
			textField.setColumns(10);
			
			JButton inputButton = new JButton("����");
			inputButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					inputIm();
				}
			});
			GridBagConstraints gbc_inputButton = new GridBagConstraints();
			gbc_inputButton.gridx = 1;
			gbc_inputButton.gridy = 0;
			inputPanel.add(inputButton, gbc_inputButton);
			
			JScrollPane displayPanel = new JScrollPane();
			modifyPanel.add(displayPanel, BorderLayout.CENTER);
			
			textArea = new JTextArea();
			textArea.setFont(new Font("SansSerif", Font.PLAIN, 24));
			textArea.setEditable(false);
			textArea.setLineWrap(true);
			displayPanel.setViewportView(textArea);
			
			JToolBar toolBar = new JToolBar();
			toolBar.setFloatable(false);
			toolBar.setName("״̬��");
			modifyPanel.add(toolBar, BorderLayout.NORTH);
			
			randomButton = new JButton();
			randomButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int index = random.nextInt(listModel.size());
					imJList.setSelectedIndex(index);
				}
			});
			randomButton.setIcon(new ImageIcon(Scheduler133.class.getResource("/resource/editor/implantEditor/random.png")));
			
			deleteButton = new JButton();
			deleteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Im im = imJList.getSelectedValue();
					if(im != null){
						listModel.removeElement(im);
						
						textArea.setText("");
						excitingButton.setSelected(false);
						interestingButton.setSelected(false);
						funButton.setSelected(false);
						wantDoButton.setSelected(false);
						
						if(listModel.size() == 0){
							randomButton.setEnabled(false);
						}
					}
				}
			});
			deleteButton.setIcon(new ImageIcon(Scheduler133.class.getResource("/resource/editor/implantEditor/delete.png")));
			
			plantButton = new JButton();
			plantButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Im im = imJList.getSelectedValue();
					if(im == null) return;
					SerialParam serialParam = new SerialParam.Productor()
							.name("���˼ƻ�")
							.describe(im.getImText())
							.product();
					
					SerialParam sp = UserInput.getSerialParam("�¼��˵���", serialParam);
					if(sp == null) return;
					
					ImprovisedPlant improvisedPlant = new ImprovisedPlant
							.Productor(new PlainTextAttachment(ProjectIoHelper.genSchedulerURL(getEditable().getRootProject(), "implants" + File.separator,".txt")))
							.serialParam(sp)
							.product();
					
					getEditable().add(improvisedPlant);
					Scheduler133.getInstance().refreshProjectTrees(getEditable().getRootProject(),improvisedPlant);
				}
			});
			plantButton.setIcon(new ImageIcon(Scheduler133.class.getResource("/resource/editor/implantEditor/plant.png")));
			
			excitingButton = new JToggleButton();
			excitingButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					boolean selFlag = excitingButton.isSelected();
					Im im = imJList.getSelectedValue();
					if(im == null) return;
					if(selFlag){
						im.addTag(ImTag.EXCITING);
					}else{
						im.removeTag(ImTag.EXCITING);
					}
					listModel.set(listModel.indexOf(im), im);
				}
			});
			excitingButton.setIcon(new ImageIcon(Scheduler133.class.getResource("/resource/editor/implantEditor/exciting.png")));
			
			interestingButton = new JToggleButton();
			interestingButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					boolean selFlag = interestingButton.isSelected();
					Im im = imJList.getSelectedValue();
					if(im == null) return;
					if(selFlag){
						im.addTag(ImTag.INTERESTING);
					}else{
						im.removeTag(ImTag.INTERESTING);
					}
					listModel.set(listModel.indexOf(im), im);
				}
			});
			interestingButton.setIcon(new ImageIcon(Scheduler133.class.getResource("/resource/editor/implantEditor/interesting.png")));

			funButton = new JToggleButton();
			funButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					boolean selFlag = funButton.isSelected();
					Im im = imJList.getSelectedValue();
					if(im == null) return;
					if(selFlag){
						im.addTag(ImTag.FUN);
					}else{
						im.removeTag(ImTag.FUN);
					}
					listModel.set(listModel.indexOf(im), im);
				}
			});
			funButton.setIcon(new ImageIcon(Scheduler133.class.getResource("/resource/editor/implantEditor/fun.png")));

			wantDoButton = new JToggleButton();
			wantDoButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					boolean selFlag = wantDoButton.isSelected();
					Im im = imJList.getSelectedValue();
					if(im == null) return;
					if(selFlag){
						im.addTag(ImTag.WANTDO);
					}else{
						im.removeTag(ImTag.WANTDO);
					}
					listModel.set(listModel.indexOf(im), im);
				}
			});
			wantDoButton.setIcon(new ImageIcon(Scheduler133.class.getResource("/resource/editor/implantEditor/want-do.png")));

			toolBar.add(randomButton);
			toolBar.add(deleteButton);
			toolBar.add(plantButton);
			toolBar.addSeparator();
			toolBar.add(excitingButton);
			toolBar.add(interestingButton);
			toolBar.add(funButton);
			toolBar.add(wantDoButton);
			
			statusPanel = new JPanel();
			editPanel.add(statusPanel, BorderLayout.SOUTH);
			GridBagLayout gbl_statusPanel = new GridBagLayout();
			gbl_statusPanel.columnWidths = new int[]{0, 0};
			gbl_statusPanel.rowHeights = new int[]{0, 0};
			gbl_statusPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_statusPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			statusPanel.setLayout(gbl_statusPanel);
			
			JLabel statusLabel = new JLabel("����");
			GridBagConstraints gbc_statusLabel = new GridBagConstraints();
			gbc_statusLabel.insets = new Insets(0, 5, 0, 0);
			gbc_statusLabel.anchor = GridBagConstraints.WEST;
			gbc_statusLabel.gridx = 0;
			gbc_statusLabel.gridy = 0;
			statusPanel.add(statusLabel, gbc_statusLabel);
			
			ActionMap textFieldActionMap = textField.getActionMap();
			InputMap textFieldInputMap = textField.getInputMap(WHEN_FOCUSED);
			
			textFieldInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
			textFieldActionMap.put("enter", new AbstractAction() {
				private static final long serialVersionUID = -7889464235621963907L;
				@Override
				public void actionPerformed(ActionEvent e) {
					inputIm();
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.desint.Editor#stopEdit()
		 */
		@Override
		public boolean stopEdit() {
			//TODO ��Ҫ�����쳣�жϡ�
			//���״̬���ã��򱣴沢�˳�
			if(getState() == State.SUCC){
				//�����ļ���
				saveEdit();
				//����ֵ
				return true;
			}
			//״̬��������ֱ���˳�
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
			
			Element root = DocumentHelper.createElement("imList");
			
			for(int i = 0 ; i < listModel.getSize() ; i ++){
				root.add(genImElement(listModel.getElementAt(i)));
			}
			
			Document document = DocumentHelper.createDocument(root);
			document.setXMLEncoding("UTF-8");
			try {
				getEditable().getAttachment().save(document);
			} catch (AttachmentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/**
		 * ��ָ����XMLԪ�������ɼ��˵��ӡ�
		 * @param element ָ����Ԫ�ء�
		 * @return ���˵��ӡ�
		 * @throws DocumentException XML�ṹ�𻵻�����ԭ������޷���ȷ����ʱ�׳����쳣��
		 */
		private Im genIm(Element element) throws DocumentException{
			String imText = null;
			long pubTime = 0;
			Set<ImTag> tags = new HashSet<ImTag>();
			
			imText = ((Element) element.selectNodes("text").get(0)).getText();
			if(imText == null || imText.equals("")) throw new DocumentException("Bad XML struct");
			try{
				pubTime = new Long(element.attributeValue("pt"));
			}catch(Exception e){
				pubTime = 0;
			}
			List<?> tl = element.selectNodes("tag");
			for(Object obj : tl){
				if(obj instanceof Element){
					Element ele = (Element) obj;
					String tagLabel = ele.getText();
					ImTag tag = ImTag.valueOfLabel(tagLabel);
					if(tag != null) tags.add(tag);
				}
			}
			
			return new Im.Productor(imText).pubTime(pubTime).tags(tags).product();
		}
		
		/**
		 * ���ָ����ָ�����˵��ӡ�
		 * @param elementAt ָ���ļ��˵��ӡ�
		 * @return ���˵��ӱ�ʾ��Ԫ�ء�
		 */
		private Element genImElement(Im im) {
			Element root = DocumentHelper.createElement("im");
			
			root.addAttribute("pt", "" + im.getPublicDate().getTime());
			
			Element textElement = DocumentHelper.createElement("text");
			textElement.addCDATA(im.getImText());
			root.add(textElement);
			
			for(ImTag tag : im.getImTags()){
				Element tagElement = DocumentHelper.createElement("tag");
				tagElement.setText(tag + "");
				root.add(tagElement);
			}
			
			return root;
		}
		
		/**
		 * ���뼴�˵��ӡ�
		 */
		private void inputIm(){
			if(textField.getText() != null &&  !textField.getText().equals("")){
				Im im = new Im.Productor(textField.getText()).pubTime(System.currentTimeMillis()).product();
				this.listModel.add(listModel.size(),im);
				textField.setText("");
				randomButton.setEnabled(true);
			}
		}
		
	}
	
}




/**
 * ���˵����ࡣ
 * <p> �����¼�˼��˼ƻ����������б���ļ��˵��ӣ���ָʾ�������ԡ�
 * @author DwArFeng
 * @since 1.8
 */
class Im{
	
	/**
	 * ���˵��ӵı�ǩ������
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum ImTag{
		
		/**���˼�����*/
		EXCITING("exciting","������"),
		/**��Ȥ��*/
		INTERESTING("interesting","��Ȥ��"),
		/**���ֵ�*/
		FUN("fun","���ֵ�"),
		/**������*/
		WANTDO("want-do","������");
		
		private final String label;
		private final String describe;
		
		private ImTag(String label,String describe) {
			this.label = label;
			this.describe = describe;
		}
		
		@Override
		public String toString(){
			return this.label;
		}
		/**
		 * ���ձ�ǩ����ָ����ö�١�
		 * @param name ��ǩֵ��
		 * @return ���ص�ö�١�
		 * @throws IllegalArgumentException ����ǩֵ�����ڼ���ö��ʱ�׳����쳣��
		 */
		public static ImTag valueOfLabel(String name){
			switch (name) {
				case "exciting":
					return EXCITING;
				case "interesting":
					return INTERESTING;
				case "fun":
					return FUN;
				case "want-do":
					return WANTDO;
				default:
					throw new IllegalArgumentException("Bad ImTag Label");
			}
		}
		
	}
	
	/**���˵��ӵ��ı�����*/
	private final String imText;
	/**���˵��ӵļ�¼ʱ�䣨����1970��1��1�յĺ�������*/
	private final long pubTime;
	/**���˵��ӵı�ǩ�б�*/
	private Set<ImTag> tags;
	
	
	/**
	 * ���˵��ӵĹ������ࡣ
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private final String imText;
		private long pubTime = 0;
		private Set<ImTag> tags = new HashSet<ImTag>();
		
		
		/**
		 * ����һ�����˵��ӵĹ����ߡ�
		 * @param imText ���˵��ӵ��ı����ݡ�
		 */
		public Productor(String imText){
			if(imText == null || imText.equals("")) throw new NullPointerException("ImText can't be null or empty");
			this.imText = imText;
		}
		
		/**
		 * ���øü��˵��ӵķ���ʱ�䡣
		 * @param val ���˵��ӵķ���ʱ�䡣
		 * @return ����������
		 */
		public Productor pubTime(long val){
			this.pubTime = val;
			return this;
		}
		
		/**
		 * ���ü��˵��ӵı�ǩ���ϡ�
		 * @param val ���˵��ӵı�ǩ���ϡ�
		 * @return ����������
		 */
		public Productor tags(Set<ImTag> val){
			if(val == null){
				this.tags = new HashSet<ImTag>();
			}else{
				this.tags = val;
			}
			return this;
		}
		
		/**
		 * ���켴�˵��ӡ�
		 * @return ���ݹ��������趨������ļ��˵��ӡ�
		 */
		public Im product(){
			return new Im(imText, pubTime,tags);
		}
		
	}
	
	/**
	 * ����һ�����˵��ӡ�
	 * @param imText ���˵��ӵ��ı����ݡ�
	 * @param pubTime ���˵��ӵķ���ʱ�䡣
	 */
	private Im(String imText,long pubTime,Set<ImTag> tags){
		this.imText = imText;
		this.pubTime = pubTime;
		this.tags = tags;
	}
	
	/**
	 * ���ؼ��˵��Ӷ�����ı����ݡ�
	 * @return ���˵��Ӷ�����ı����ݡ�
	 */
	public String getImText(){
		return this.imText;
	}
	
	/**
	 * ���ظü��˵��ӵķ���ʱ�䡣
	 * @return �ü��˵��ӵķ���ʱ�䡣
	 */
	public Date getPublicDate(){
		return new Date(pubTime);
	}
	
	/**
	 * ���ظü��˵��������еı�ǩ��
	 * <p> ���صļ����Ǹ������м��ϵĸ��������ø÷��ؼ��ϵ�����{@linkplain Set#add(Object)}�ȷ�������ı�
	 * �ü��˵��Ӷ���ı�ǩ������Ҫ�޸ļ��˵��ӵı�ǩ����ʹ�ü��˵����е���Ӧ������
	 * @return ���˵��������б�ǩ�ĸ�����
	 */
	public Set<ImTag> getImTags(){
		return new HashSet<ImTag>(this.tags);
	}
	
	/**
	 * ��ü��˵��Ӷ�������ӱ�ǩ��
	 * @param tag ָ���ı�ǩ��
	 */
	public void addTag(ImTag tag){
		this.tags.add(tag);
	}
	
	/**
	 * �Ӹü��˵������Ƴ���ǩ��
	 * @param tag ָ���ı�ǩ��
	 */
	public void removeTag(ImTag tag){
		this.tags.remove(tag);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("���˵���");
		if(tags.size() > 0){
			sb.append(":");
			ImTag[] ta = tags.toArray(new ImTag[0]);
			Arrays.sort(ta);
			for(int i = 0 ; i < ta.length ; i ++){
				if(i != 0) sb.append(",");
				sb.append(ta[i].describe);
			}
		}
		return sb.toString();
	}
	
}
