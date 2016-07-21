package com.dwarfeng.scheduler.gui;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * ʵ�����л��Ƿ��Զ����й��ܵ��ı��༭��塣
 * <p> �������Ҫ������{@linkplain JScrollPane} �У��Դﵽ��õ�Ч�������ң����ø��ı��༭����{@linkplain JScrollPane} ��Ҫ
 * ����Layout����Ϊ{@linkplain LineWrapableViewportLayout}��
 * @author DwArFeng
 * @since 1.8
 */
public class JLineWrapableTextPane extends JTextPane{
	
	private static final long serialVersionUID = -6707981349896752396L;
	
	private boolean lineWrap;
	
	/**
	 * ����һ��Ĭ�ϵģ��Զ����еĿ��Զ������ı���塣
	 */
	public JLineWrapableTextPane(){
		this(true);
	}
	/**
	 * ����һ��ָ���Ƿ��Զ����еĿ��Զ������ı���塣
	 * @param isLineWrap �Ƿ��Զ����С�
	 */
	public JLineWrapableTextPane(boolean isLineWrap){
		super();
		this.setLineWrap(isLineWrap);
	}
	
	/**
	 * ���ظ��ı�����Ƿ��Զ����С�
	 * @return �Ƿ��Զ����С�
	 */
	public boolean isLineWrap() {
		return lineWrap;
	}
	/**
	 * ���ø��ı�����Ƿ��Զ����С�
	 * @param lineWrap �Ƿ��Զ����С�
	 */
	public void setLineWrap(boolean lineWrap) {
		this.lineWrap = lineWrap;
		repaint();
	}
	@Override
	public boolean getScrollableTracksViewportWidth(){
		if(lineWrap){
			return true;
		}else{
			return false;
		}
	}

}
