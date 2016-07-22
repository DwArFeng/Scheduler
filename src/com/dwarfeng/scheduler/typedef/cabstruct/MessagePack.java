package com.dwarfeng.scheduler.typedef.cabstruct;

import java.awt.Color;
import java.awt.Font;

/**
 * ��Ϣ����
 * <p> ��װ�����Ϣ�İ����˰��а�����Ҫ�ٿ���̨���������Ϣ�Լ���Ҫ�ٿ��������������Ϣ��
 * @author DwArFeng
 * @since 1.8
 */
public final class MessagePack {
	
	/**
	 * ��ʾ�����״̬���ϵ���Ϣ�ĸ�ʽ��
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum StatusMessageType{
		/**��ͨ��ʽ*/
		NORMAL(new Font(Font.SANS_SERIF, Font.PLAIN, 12), Color.BLACK),
		/**��ʾ��ʽ�����ı��Ӵ֡�ʹ�ú�ɫ*/
		ALARM(new Font(Font.SANS_SERIF, Font.BOLD, 12), Color.RED)
		
		;
		private final Font font;
		private final Color color;
		
		private StatusMessageType(Font font, Color color) {
			this.font = font;
			this.color = color;
		}
		
		/**
		 * ���ص�ǰö���ֶζ�Ӧ�����塣
		 * @return ��Ӧ�����塣
		 */
		public Font getFont(){
			return this.font;
		}
		
		/**
		 * ���ص�ǰö���ֶζ�Ӧ����ɫ��
		 * @return ��Ӧ����ɫ��
		 */
		public Color getColor(){
			return this.color;
		}
	}

	private final String consoleMessage;
	private final String statusMessage;
	private final StatusMessageType type;
	
	/**
	 * ����һ������ָ������̨��Ϣ��ָ��״̬����Ϣ��ָ��״̬����ʾ���͵���Ϣ����
	 * @param consoleMessage ָ���Ŀ���̨�����Ϣ��
	 * @param statusString ָ����״̬�������Ϣ��
	 * @param type ָ����״̬����ʾ���͡�
	 */
	public MessagePack(String consoleMessage, String statusString, StatusMessageType type){
		this.consoleMessage = consoleMessage;
		this.statusMessage = statusString;
		this.type = type;
	}
	
	/**
	 * ��ȡ����̨�������Ϣ��
	 * @return ����̨�������Ϣ��
	 */
	public String getConsoleMessage(){
		return this.consoleMessage;
	}
	
	/**
	 * ��ȡ״̬���������Ϣ��
	 * @return ״̬���������Ϣ��
	 */
	public String getStatusMessage(){
		return this.statusMessage;
	}
	
	/**
	 * ��ȡ״̬���ĵ���ʾ���͡�
	 * @return ״̬������ʾ���͡�
	 */
	public StatusMessageType getStatusMessageType(){
		return this.type;
	}
}
