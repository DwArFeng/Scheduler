package com.dwarfeng.scheduler.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
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
import com.dwarfeng.scheduler.typedef.cabstruct.ControlMgr;
import com.dwarfeng.scheduler.typedef.cabstruct.ModuleMgr;
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
public final class Scheduler {

	/**有关的后台*/
	private final static RunnerQueue<NadeRunner> background = new RunnerQueue<NadeRunner>();
	
//	/**
//	 * 向该程序的后台中添加一个运行器。
//	 * @param runner 指定的运行器。
//	 */
//	public static void backgroundInvoke(NadeRunner runner){
//		background.invoke(runner);
//	}
//	
//	/**
//	 * 获取程序后台的等待队列的元素数量。
//	 * @return 元素数量。
//	 */
//	public static int getBackgroundQueueSize(){
//		return background.getQueueSize();
//	}
	
	/**
	 * 文本字段的类型枚举。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum StringFieldType{
		/**异常文本*/
		EXCEPTION,
		/**标签文本*/
		LABEL,
		/**信息文本*/
		MESSAGE
	}
	
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
		 * 设定指定文本类型的地区。
		 * @param type 指定的文本类型。
		 * @param locale 指定的地区。
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
		 * 返回异常文本。
		 * @param key 异常文本的主键。
		 * @return 主键对应的文本。
		 */
		public String getException(ExceptionStringFieldKey key){
			try{
				return exceptionStringField.getString(key.toString());
			}catch(MissingResourceException e){
				return MISSING_FIELD;
			}
		}
		
		/**
		 * 返回标签文本。
		 * @param key 标签文本的主键。
		 * @return  主键对应的文本。
		 */
		public String getLabel(LabelStringFieldKey key){
			try{
				return labelStringField.getString(key.toString());
			}catch(MissingResourceException e){
				return MISSING_FIELD;
			}
		}
		
		/**
		 * 返回信息文本。
		 * @param key 信息文本的主键。
		 * @return 主键对应的文本。
		 */
		public String getMessage(MessageStringFieldKey key){
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
	 * 返回异常文本。
	 * @param key 异常文本的主键。
	 * @return 主键对应的文本。
	 */
	public static String getException(ExceptionStringFieldKey key){
		return stringFields.getException(key);
	}
	
	/**
	 * 返回标签文本。
	 * @param key 标签文本的主键。
	 * @return  主键对应的文本。
	 */
	public static String getLabel(LabelStringFieldKey key){
		return stringFields.getLabel(key);
	}
	
	/**
	 * 设置地区。
	 * <p> 文本字段会自动变更为指定地区对应的字段。
	 * @param locale 指定的地区。
	 */
	public void setLocale(Locale locale){
		stringFields.setLocale(locale);;
	}
	
	/**
	 * 设定指定文本类型的地区。
	 * @param type 指定的文本类型。
	 * @param locale 指定的地区。
	 */
	public void setLocale(StringFieldType type,Locale locale){
		stringFields.setLocale(type, locale);
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
				//TODO 用控制器来做。
				e.printStackTrace();
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
	
	private Scheduler(ModuleMgr module, ViewMgr view, ControlMgr control){
		this.module = module;
		this.view = view;
		this.control = control;
		
		view.setControlMgr(control);
		control.setViewAskPort(view.getViewAskPort());
		control.setViewControlPort(view.getViewControlPort());
	}
	
	
}
