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
import com.dwarfeng.scheduler.info.ImageKeys;
import com.dwarfeng.scheduler.info.StringFieldKey;
import com.dwarfeng.scheduler.typedef.cabstruct.ControlMgr;
import com.dwarfeng.scheduler.typedef.cabstruct.ModuleMgr;
import com.dwarfeng.scheduler.typedef.cabstruct.SchedulerControlPort;
import com.dwarfeng.scheduler.typedef.cabstruct.ViewMgr;

/**
 * 计划管理程序。
 * <p> 该程序的主要作用是解决计划的规划问题以及笔记整理的问题。该程序主要围绕着两个方向进行编写。
 * <br>&nbsp;&nbsp; 1.笔记记录以及整理工作，此部分类似于现有的有道云笔记等笔记管理程序，在其基础上再添加某些功能。
 * <br>&nbsp;&nbsp; 2.任务管理。通过想程序中输入指定的计划任务，程序根据系统时间来对这些任务进行管理。
 * <p> 程序结构采用MVC结构，并且尽可能的做到相互分离。
 * <br> <code>Scheduler</code>本身拥有一个后台维护器，任何需要调用后台的方法都可以托管到这个维护器上。
 * @author DwArFeng
 * @since 1.8
 */
public final class Scheduler233 {
	
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
	/**有关的后台*/
	private final static RunnerQueue<NadeRunner> background = new RunnerQueue<NadeRunner>();
	/**
	 * 计划管理器文本字段。
	 * <p> 该类负责存储程序中所有出现的文本字段。
	 * <br> 所有的文本字段可以分为三类：异常文本、标签文本、信息文本。每个文本都可以独立设置地区。
	 * @author DwArFeng
	 * @since 1.8
	 */
	private static final class StringFields {
		
		private static final String EXCEPTION = "resource/lang/exceptionStringField";
		private static final String LABEL = "resource/lang/exceptionStringField";
		private static final String MESSAGE = "resource/lang/exceptionStringField";
		private static final String MISSING_FIELD = "字段缺失";
		
		private ResourceBundle exceptionStringField;
		private ResourceBundle labelStringField;
		private ResourceBundle messageStringField;
		
		/**
		 * 创建一个地区默认的文本字段。
		 */
		public StringFields(){
			this(Locale.getDefault());
		}
		
		/**
		 * 生成一个具有指定地区的文本字段。
		 * @param locale 指定地区。
		 */
		public StringFields(Locale locale){
			setLocale(locale);
		}
		
		/**
		 * 设置地区。
		 * <p> 文本字段会自动变更为指定地区对应的字段。
		 * @param locale 指定的地区。
		 */
		public void setLocale(Locale locale){
			this.exceptionStringField = ResourceBundle.getBundle(EXCEPTION, locale, this.getClass().getClassLoader());
			this.labelStringField = ResourceBundle.getBundle(LABEL, locale, this.getClass().getClassLoader());
			this.messageStringField = ResourceBundle.getBundle(MESSAGE, locale, this.getClass().getClassLoader());
		}
		
		/**
		 * 返回文本主键对应的文本。
		 * @param key 文本的主键。
		 * @return 主键对应的文本。
		 */
		public String getText(StringFieldKey key){
			try{
				return messageStringField.getString(key.toString());
			}catch(MissingResourceException e){
				return MISSING_FIELD;
			}
		}

	}

	/**有关的文本字段*/
	private static StringFields stringFields = new StringFields();
	
	/**
	 * 返回文本主键对应的文本。
	 * @param key 文本的主键。
	 * @return 主键对应的文本。
	 */
	public String getText(StringFieldKey key){
		return stringFields.getText(key);
	}
	
	/**
	 * 设置地区。
	 * <p> 文本字段会自动变更为指定地区对应的字段。
	 * @param locale 指定的地区。
	 */
	public static void setLocale(Locale locale){
		stringFields.setLocale(locale);
	}
	
	/**
	 * 计划管理器图像字段。
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
		 * 返回指定图片字段主键对应的图片。
		 * <p> 如果图片不存在，则返回该类定义的默认的图片。
		 * @param key 图片字段主键。
		 * @return 主键对应的图片。
		 */
		public Image getImage(ImageKeys key){
			try {
				return ImageIO.read(cl.getResource(key.getName()));
			} catch (IOException e) {
				return defaultImage;
			}
		}
		
		/**
		 * 以图标的形式返回指定图片字段对应的图片。
		 * <p> 如果图片不存在，则返回该类定义的默认的图片。
		 * @param key 图片字段的主键。
		 * @return 主键对应的图片的图标形式。
		 */
		public Icon getImageAsIcon(ImageKeys key){
			return new ImageIcon(getImage(key));
		}

	}
	
	private static ImageFields imageFields = new ImageFields();
	
	/**
	 * 返回指定图片字段主键对应的图片。
	 * <p> 如果图片不存在，则返回默认的图片。
	 * @param key 图片字段主键。
	 * @return 主键对应的图片。
	 */
	public Image getImage(ImageKeys key){
		return imageFields.getImage(key);
	}
	
	/**
	 * 以图标的形式返回指定图片字段对应的图片。
	 * <p> 如果图片不存在，则返回默认的图片。
	 * @param key 图片字段的主键。
	 * @return 主键对应的图片的图标形式。
	 */
	public Icon getImageAsIcon(ImageKeys key){
		return imageFields.getImageAsIcon(key);
	}
	
	
	
	private ModuleMgr module;
	private ViewMgr view;
	private ControlMgr control;
	
	private Scheduler233(ModuleMgr module, ViewMgr view, ControlMgr control){
		this.module = module;
		this.view = view;
		this.control = control;
		
		view.setControlPort(control.getControlPort());
		control.setViewControlPort(view.getViewControlPort());
		control.setSchedulerControlPort(scp);
	}
	
	
}
