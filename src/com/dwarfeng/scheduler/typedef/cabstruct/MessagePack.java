package com.dwarfeng.scheduler.typedef.cabstruct;

import java.awt.Color;
import java.awt.Font;

/**
 * 信息包。
 * <p> 封装输出信息的包，此包中包含需要再控制台中输出的信息以及需要再控制栏中输出的信息。
 * @author DwArFeng
 * @since 1.8
 */
public final class MessagePack {
	
	/**
	 * 表示输出在状态栏上的信息的格式。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum StatusMessageType{
		/**普通格式*/
		NORMAL(new Font(Font.SANS_SERIF, Font.PLAIN, 12), Color.BLACK),
		/**警示格式——文本加粗、使用红色*/
		ALARM(new Font(Font.SANS_SERIF, Font.BOLD, 12), Color.RED)
		
		;
		private final Font font;
		private final Color color;
		
		private StatusMessageType(Font font, Color color) {
			this.font = font;
			this.color = color;
		}
		
		/**
		 * 返回当前枚举字段对应的字体。
		 * @return 对应的字体。
		 */
		public Font getFont(){
			return this.font;
		}
		
		/**
		 * 返回当前枚举字段对应的颜色。
		 * @return 对应的颜色。
		 */
		public Color getColor(){
			return this.color;
		}
	}

	private final String consoleMessage;
	private final String statusMessage;
	private final StatusMessageType type;
	
	/**
	 * 生成一个具有指定控制台信息，指定状态栏信息，指定状态栏显示类型的信息包。
	 * @param consoleMessage 指定的控制台输出信息。
	 * @param statusString 指定的状态栏输出信息。
	 * @param type 指定的状态栏显示类型。
	 */
	public MessagePack(String consoleMessage, String statusString, StatusMessageType type){
		this.consoleMessage = consoleMessage;
		this.statusMessage = statusString;
		this.type = type;
	}
	
	/**
	 * 获取控制台的输出信息。
	 * @return 控制台的输出信息。
	 */
	public String getConsoleMessage(){
		return this.consoleMessage;
	}
	
	/**
	 * 获取状态栏的输出信息。
	 * @return 状态栏的输出信息。
	 */
	public String getStatusMessage(){
		return this.statusMessage;
	}
	
	/**
	 * 获取状态栏的的显示类型。
	 * @return 状态栏的显示类型。
	 */
	public StatusMessageType getStatusMessageType(){
		return this.type;
	}
}
