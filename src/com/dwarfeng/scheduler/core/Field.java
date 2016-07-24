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
 * �ƻ��������ı��ֶΡ�
 * <p> ���ฺ��洢���������г��ֵ��ı��ֶΡ�
 * <br> ���е��ı��ֶο��Է�Ϊ���ࣺ�쳣�ı�����ǩ�ı�����Ϣ�ı���ÿ���ı������Զ������õ�����
 * @author DwArFeng
 * @since 1.8
 */
 final class StringFields {
	
	private static final String PATH = "resource/lang/stringField";
	private static final String MISSING_FIELD = "�ֶ�ȱʧ";
	
	private ResourceBundle stringField;
	
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
		this.stringField = ResourceBundle.getBundle(PATH, locale, this.getClass().getClassLoader());
	}
	
	/**
	 * �����ı�������Ӧ���ı���
	 * @param key �ı���������
	 * @return ������Ӧ���ı���
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
  * �ƻ�������ͼ���ֶΡ�
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