package com.dwarfeng.scheduler.typedef.conmod;

import java.awt.Color;
import java.awt.Font;

/**
 * 程序的输出信息包。
 * <p> 程序输出的信息分为两部分（不包括直接用提示框或者对话框这样的输出），第一部分是输出在控制台上，第二部分是输出的状态栏中，
 * 且状态栏的输出根据状态不同而不同，比如警告性的文本应该用红色等。
 * <br> 将这些信息封装在一个对象中，就是所谓的输出信息包
 * @author DwArFeng
 * @since 1.8
 */
public class OutputMessagePack {
	
	/**
	 * 状态栏的输出类型枚举。
	 * <p> 该枚举记录了在状态栏中输出的所有类型，以及不同类型对应的文本颜色以及文本字体。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum StatusLabelType{
		
		/**正常性文本*/
		NORMAL(new Font(Font.SANS_SERIF,Font.PLAIN,12),Color.BLACK),
		/**警告性文本*/
		ALARM(new Font(Font.SANS_SERIF,Font.BOLD,12),Color.RED)
		
		;
		private final Font font;
		private final Color color;
		
		StatusLabelType(Font font,Color color){
			this.font = font;
			this.color = color;
		}
		
		/**
		 * 获取相应输出类型对应的字体。
		 * @return 对应的字体。
		 */
		public Font getFont(){
			return this.font;
		}
		
		/**
		 * 获取相应输出类型对应的文字颜色。
		 * @return 文字颜色。
		 */
		public Color getColor(){
			return this.color;
		}
	}
	
	private final String consoleMessage;
	private final String statusLabelMessage;
	private final StatusLabelType type;
	
	/**
	 * 创建指定控制台输出文本，指定状态标签输出文本，指定状态标签输出类型的输出信息包。
	 * <p> 请保证入口参数不为<code>null</code>。
	 * @param consoleMessage 指定的控制台输出文本。
	 * @param statusLabelMessage 指定的状态标签输出文本。
	 * @param type 指定的状态标签输出类型。
	 */
	public OutputMessagePack(String consoleMessage, String statusLabelMessage, StatusLabelType type){
		this.consoleMessage = consoleMessage;
		this.statusLabelMessage = statusLabelMessage;
		this.type = type;
	}

	/**
	 * 获取控制台输出文本。
	 * @return 控制台输出文本。
	 */
	public String getConsoleMessage() {
		return consoleMessage;
	}

	/**
	 * 获取状态标签输出文本。
	 * @return 状态标签输出文本。
	 */
	public String getStatusLabelMessage() {
		return statusLabelMessage;
	}

	/**
	 * 获取状态标签输出文本的类型。
	 * @return 文本类型。
	 */
	public StatusLabelType getStatusLabelType() {
		return type;
	}
	
}
