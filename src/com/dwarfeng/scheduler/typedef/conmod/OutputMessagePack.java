package com.dwarfeng.scheduler.typedef.conmod;

import java.awt.Color;
import java.awt.Font;

/**
 * ����������Ϣ����
 * <p> �����������Ϣ��Ϊ�����֣�������ֱ������ʾ����߶Ի������������������һ����������ڿ���̨�ϣ��ڶ������������״̬���У�
 * ��״̬�����������״̬��ͬ����ͬ�����羯���Ե��ı�Ӧ���ú�ɫ�ȡ�
 * <br> ����Щ��Ϣ��װ��һ�������У�������ν�������Ϣ��
 * @author DwArFeng
 * @since 1.8
 */
public class OutputMessagePack {
	
	/**
	 * ״̬�����������ö�١�
	 * <p> ��ö�ټ�¼����״̬����������������ͣ��Լ���ͬ���Ͷ�Ӧ���ı���ɫ�Լ��ı����塣
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum StatusLabelType{
		
		/**�������ı�*/
		NORMAL(new Font(Font.SANS_SERIF,Font.PLAIN,12),Color.BLACK),
		/**�������ı�*/
		ALARM(new Font(Font.SANS_SERIF,Font.BOLD,12),Color.RED)
		
		;
		private final Font font;
		private final Color color;
		
		StatusLabelType(Font font,Color color){
			this.font = font;
			this.color = color;
		}
		
		/**
		 * ��ȡ��Ӧ������Ͷ�Ӧ�����塣
		 * @return ��Ӧ�����塣
		 */
		public Font getFont(){
			return this.font;
		}
		
		/**
		 * ��ȡ��Ӧ������Ͷ�Ӧ��������ɫ��
		 * @return ������ɫ��
		 */
		public Color getColor(){
			return this.color;
		}
	}
	
	private final String consoleMessage;
	private final String statusLabelMessage;
	private final StatusLabelType type;
	
	/**
	 * ����ָ������̨����ı���ָ��״̬��ǩ����ı���ָ��״̬��ǩ������͵������Ϣ����
	 * <p> �뱣֤��ڲ�����Ϊ<code>null</code>��
	 * @param consoleMessage ָ���Ŀ���̨����ı���
	 * @param statusLabelMessage ָ����״̬��ǩ����ı���
	 * @param type ָ����״̬��ǩ������͡�
	 */
	public OutputMessagePack(String consoleMessage, String statusLabelMessage, StatusLabelType type){
		this.consoleMessage = consoleMessage;
		this.statusLabelMessage = statusLabelMessage;
		this.type = type;
	}

	/**
	 * ��ȡ����̨����ı���
	 * @return ����̨����ı���
	 */
	public String getConsoleMessage() {
		return consoleMessage;
	}

	/**
	 * ��ȡ״̬��ǩ����ı���
	 * @return ״̬��ǩ����ı���
	 */
	public String getStatusLabelMessage() {
		return statusLabelMessage;
	}

	/**
	 * ��ȡ״̬��ǩ����ı������͡�
	 * @return �ı����͡�
	 */
	public StatusLabelType getStatusLabelType() {
		return type;
	}
	
}
