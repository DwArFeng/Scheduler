package com.dwarfeng.scheduler.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Queue;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.dwarfeng.dwarffunction.threads.NadeRunner;
import com.dwarfeng.dwarffunction.threads.RunnerQueue;
import com.dwarfeng.scheduler.info.ExceptionStringFieldKey;
import com.dwarfeng.scheduler.info.ImageKeys;
import com.dwarfeng.scheduler.info.LabelStringFieldKey;
import com.dwarfeng.scheduler.info.MessageStringFieldKey;
import com.dwarfeng.scheduler.info.StringFieldType;
import com.dwarfeng.scheduler.typedef.cabstruct.ControlMgr;
import com.dwarfeng.scheduler.typedef.cabstruct.ModuleMgr;
import com.dwarfeng.scheduler.typedef.cabstruct.SchedulerControlPort;
import com.dwarfeng.scheduler.typedef.cabstruct.ViewMgr;

/**
 * �ƻ��������
 * <p> �ó������Ҫ�����ǽ���ƻ��Ĺ滮�����Լ��ʼ���������⡣�ó�����ҪΧ��������������б�д��
 * <br>&nbsp;&nbsp; 1.�ʼǼ�¼�Լ����������˲������������е��е��Ʊʼǵȱʼǹ��������������������ĳЩ���ܡ�
 * <br>&nbsp;&nbsp; 2.�������ͨ�������������ָ���ļƻ����񣬳������ϵͳʱ��������Щ������й���
 * <p> ����ṹ����MVC�ṹ�����Ҿ����ܵ������໥���롣
 * <br> <code>Scheduler</code>����ӵ��һ����̨ά�������κ���Ҫ���ú�̨�ķ����������йܵ����ά�����ϡ�
 * @author DwArFeng
 * @since 1.8
 */
public final class Scheduler {
	
	private final SchedulerControlPort scp = new SchedulerControlPort() {
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.cabstruct.SchedulerControlPort#backgroundInvoke(com.dwarfeng.dwarffunction.threads.NadeRunner)
		 */
		@Override
		public void backgroundInvoke(NadeRunner runner) {
			background.invoke(runner);
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.cabstruct.SchedulerControlPort#getBackgroundQueueSize()
		 */
		@Override
		public int getBackgroundQueueSize() {
			return background.getQueueSize();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.cabstruct.SchedulerControlPort#getBackgroundQueue()
		 */
		@Override
		public Queue<NadeRunner> getBackgroundQueue() {
			return background.getWaitingQueue();
		}
	};
	/**�йصĺ�̨*/
	private final static RunnerQueue<NadeRunner> background = new RunnerQueue<NadeRunner>();
	/**
	 * �ƻ��������ı��ֶΡ�
	 * <p> ���ฺ��洢���������г��ֵ��ı��ֶΡ�
	 * <br> ���е��ı��ֶο��Է�Ϊ���ࣺ�쳣�ı�����ǩ�ı�����Ϣ�ı���ÿ���ı������Զ������õ�����
	 * @author DwArFeng
	 * @since 1.8
	 */
	private static final class StringFields {
		
		private static final String EXCEPTION = "resource/lang/exceptionStringField";
		private static final String LABEL = "resource/lang/exceptionStringField";
		private static final String MESSAGE = "resource/lang/exceptionStringField";
		private static final String MISSING_FIELD = "�ֶ�ȱʧ";
		
		private ResourceBundle exceptionStringField;
		private ResourceBundle labelStringField;
		private ResourceBundle messageStringField;
		
		/**
		 * ����һ������Ĭ�ϵ��ı��ֶΡ�
		 */
		public StringFields(){
			this(Locale.getDefault());
		}
		
		/**
		 * ����һ������ָ���������ı��ֶΡ�
		 * @param locale ָ��������
		 */
		public StringFields(Locale locale){
			setLocale(locale);
		}
		
		/**
		 * ���õ�����
		 * <p> �ı��ֶλ��Զ����Ϊָ��������Ӧ���ֶΡ�
		 * @param locale ָ���ĵ�����
		 */
		public void setLocale(Locale locale){
			this.exceptionStringField = ResourceBundle.getBundle(EXCEPTION, locale, this.getClass().getClassLoader());
			this.labelStringField = ResourceBundle.getBundle(LABEL, locale, this.getClass().getClassLoader());
			this.messageStringField = ResourceBundle.getBundle(MESSAGE, locale, this.getClass().getClassLoader());
		}
		
		/**
		 * �趨ָ���ı����͵ĵ�����
		 * @param type ָ�����ı����͡�
		 * @param locale ָ���ĵ�����
		 */
		public void setLocale(StringFieldType type,Locale locale){
			switch (type) {
				case EXCEPTION:
					this.exceptionStringField = ResourceBundle.getBundle(EXCEPTION, locale, this.getClass().getClassLoader());
					break;
				case LABEL:
					this.labelStringField = ResourceBundle.getBundle(LABEL, locale, this.getClass().getClassLoader());
					break;
				case MESSAGE:
					this.messageStringField = ResourceBundle.getBundle(MESSAGE, locale, this.getClass().getClassLoader());
					break;
			}
		}
		
		/**
		 * �����쳣�ı���
		 * @param key �쳣�ı���������
		 * @return ������Ӧ���ı���
		 */
		public String getException(ExceptionStringFieldKey key){
			try{
				return exceptionStringField.getString(key.toString());
			}catch(MissingResourceException e){
				return MISSING_FIELD;
			}
		}
		
		/**
		 * ���ر�ǩ�ı���
		 * @param key ��ǩ�ı���������
		 * @return  ������Ӧ���ı���
		 */
		public String getLabel(LabelStringFieldKey key){
			try{
				return labelStringField.getString(key.toString());
			}catch(MissingResourceException e){
				return MISSING_FIELD;
			}
		}
		
		/**
		 * ������Ϣ�ı���
		 * @param key ��Ϣ�ı���������
		 * @return ������Ӧ���ı���
		 */
		public String getMessage(MessageStringFieldKey key){
			try{
				return messageStringField.getString(key.toString());
			}catch(MissingResourceException e){
				return MISSING_FIELD;
			}
		}

	}

	/**�йص��ı��ֶ�*/
	private static StringFields stringFields = new StringFields();
	
	/**
	 * �����쳣�ı���
	 * @param key �쳣�ı���������
	 * @return ������Ӧ���ı���
	 */
	public static String getException(ExceptionStringFieldKey key){
		return stringFields.getException(key);
	}
	
	/**
	 * ���ر�ǩ�ı���
	 * @param key ��ǩ�ı���������
	 * @return  ������Ӧ���ı���
	 */
	public static String getLabel(LabelStringFieldKey key){
		return stringFields.getLabel(key);
	}
	
	/**
	 * ������Ϣ�ı���
	 * @param key ��Ϣ�ı���������
	 * @return ������Ӧ���ı���
	 */
	public String getMessage(MessageStringFieldKey key){
		return stringFields.getMessage(key);
	}
	
	/**
	 * ���õ�����
	 * <p> �ı��ֶλ��Զ����Ϊָ��������Ӧ���ֶΡ�
	 * @param locale ָ���ĵ�����
	 */
	public static void setLocale(Locale locale){
		stringFields.setLocale(locale);
	}
	
	/**
	 * �趨ָ���ı����͵ĵ�����
	 * @param type ָ�����ı����͡�
	 * @param locale ָ���ĵ�����
	 */
	public static void setLocale(StringFieldType type,Locale locale){
		stringFields.setLocale(type, locale);
	}
	
	/**
	 * �ƻ�������ͼ���ֶΡ�
	 * @author DwArFeng
	 * @since 1.8
	 */
	private static final class ImageFields {
		
		private static ClassLoader cl = ImageFields.class.getClassLoader();
		private static BufferedImage defaultImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		static {
			Graphics g = defaultImage.getGraphics();
			g.setColor(Color.MAGENTA);
			g.fillRect(0, 0, 16, 16);
		}
		
		/**
		 * ����ָ��ͼƬ�ֶ�������Ӧ��ͼƬ��
		 * <p> ���ͼƬ�����ڣ��򷵻ظ��ඨ���Ĭ�ϵ�ͼƬ��
		 * @param key ͼƬ�ֶ�������
		 * @return ������Ӧ��ͼƬ��
		 */
		public Image getImage(ImageKeys key){
			try {
				return ImageIO.read(cl.getResource(key.getName()));
			} catch (IOException e) {
				return defaultImage;
			}
		}
		
		/**
		 * ��ͼ�����ʽ����ָ��ͼƬ�ֶζ�Ӧ��ͼƬ��
		 * <p> ���ͼƬ�����ڣ��򷵻ظ��ඨ���Ĭ�ϵ�ͼƬ��
		 * @param key ͼƬ�ֶε�������
		 * @return ������Ӧ��ͼƬ��ͼ����ʽ��
		 */
		public Icon getImageAsIcon(ImageKeys key){
			return new ImageIcon(getImage(key));
		}

	}
	
	private static ImageFields imageFields = new ImageFields();
	
	/**
	 * ����ָ��ͼƬ�ֶ�������Ӧ��ͼƬ��
	 * <p> ���ͼƬ�����ڣ��򷵻�Ĭ�ϵ�ͼƬ��
	 * @param key ͼƬ�ֶ�������
	 * @return ������Ӧ��ͼƬ��
	 */
	public Image getImage(ImageKeys key){
		return imageFields.getImage(key);
	}
	
	/**
	 * ��ͼ�����ʽ����ָ��ͼƬ�ֶζ�Ӧ��ͼƬ��
	 * <p> ���ͼƬ�����ڣ��򷵻�Ĭ�ϵ�ͼƬ��
	 * @param key ͼƬ�ֶε�������
	 * @return ������Ӧ��ͼƬ��ͼ����ʽ��
	 */
	public Icon getImageAsIcon(ImageKeys key){
		return imageFields.getImageAsIcon(key);
	}
	
	
	
	private ModuleMgr module;
	private ViewMgr view;
	private ControlMgr control;
	
	private Scheduler(ModuleMgr module, ViewMgr view, ControlMgr control){
		this.module = module;
		this.view = view;
		this.control = control;
		
		view.setControlPort(control.getControlPort());
		control.setViewControlPort(view.getViewControlPort());
		control.setSchedulerControlPort(scp);
	}
	
	
}
