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

import com.dwarfeng.scheduler.info.ImageKeys;
import com.dwarfeng.scheduler.info.StringFieldKey;

/**
 * 计划管理器文本字段。
 * <p> 该类负责存储程序中所有出现的文本字段。
 * <br> 所有的文本字段可以分为三类：异常文本、标签文本、信息文本。每个文本都可以独立设置地区。
 * @author DwArFeng
 * @since 1.8
 */
 final class StringFields {
	
	private static final String PATH = "resource/lang/stringField";
	private static final String MISSING_FIELD = "字段缺失";
	
	private ResourceBundle stringField;
	
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
		this.stringField = ResourceBundle.getBundle(PATH, locale, this.getClass().getClassLoader());
	}
	
	/**
	 * 返回文本主键对应的文本。
	 * @param key 文本的主键。
	 * @return 主键对应的文本。
	 */
	public String getText(StringFieldKey key){
		try{
			return stringField.getString(key.toString());
		}catch(MissingResourceException e){
			return MISSING_FIELD;
		}
	}

}
 
 /**
  * 计划管理器图像字段。
  * @author DwArFeng
  * @since 1.8
  */
 final class ImageFields {
		
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