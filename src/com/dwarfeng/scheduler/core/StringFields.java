package com.dwarfeng.scheduler.core;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.dwarfeng.scheduler.info.ExceptionStringFieldKey;
import com.dwarfeng.scheduler.info.LabelStringFieldKey;
import com.dwarfeng.scheduler.info.MessageStringFieldKey;

/**
 * 计划管理器文本字段。
 * <p> 该类负责存储程序中所有出现的文本字段。
 * <br> 所有的文本字段可以分为三类：异常文本、标签文本、信息文本。每个文本都可以独立设置地区。
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
