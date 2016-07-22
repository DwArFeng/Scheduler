package com.dwarfeng.scheduler.core;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.dwarfeng.scheduler.info.ExceptionStringFieldKey;
import com.dwarfeng.scheduler.info.LabelStringFieldKey;
import com.dwarfeng.scheduler.info.MessageStringFieldKey;

/**
 * �ƻ��������ı��ֶΡ�
 * <p> ���ฺ��洢���������г��ֵ��ı��ֶΡ�
 * <br> ���е��ı��ֶο��Է�Ϊ���ࣺ�쳣�ı�����ǩ�ı�����Ϣ�ı���ÿ���ı������Զ������õ�����
 * @author DwArFeng
 * @since 1.8
 */
public final class StringFields {
	
	/**
	 * 
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum StringFieldType{
		EXCEPTION,
		LABEL,
		MESSAGE
	}
	
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
