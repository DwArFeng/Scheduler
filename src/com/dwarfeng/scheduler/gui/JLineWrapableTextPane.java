package com.dwarfeng.scheduler.gui;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * ʵ�����л��Ƿ��Զ����й��ܵ��ı��༭��塣
 * <p> �������Ҫ������{@linkplain JScrollPane} �У��Դﵽ��õ�Ч�������ң����ø��ı��༭����{@linkplain JScrollPane} ��Ҫ
 * ����Layout����Ϊ{@linkplain LineWarpableViewportLayout}��
 * @author DwArFeng
 * @since 1.8
 */
public class JLineWrapableTextPane extends JTextPane{
	
	private static final long serialVersionUID = -6707981349896752396L;
	
	private boolean lineWarp;
	
	/**
	 * ����һ��Ĭ�ϵģ��Զ����еĿ��Զ������ı���塣
	 */
	public JLineWrapableTextPane(){
		this(true);
	}
	/**
	 * ����һ��ָ���Ƿ��Զ����еĿ��Զ������ı���塣
	 * @param isLineWarp �Ƿ��Զ����С�
	 */
	public JLineWrapableTextPane(boolean isLineWarp){
		super();
		this.setLineWarp(isLineWarp);
	}
	
	/**
	 * ���ظ��ı�����Ƿ��Զ����С�
	 * @return �Ƿ��Զ����С�
	 */
	public boolean isLineWarp() {
		return lineWarp;
	}
	/**
	 * ���ø��ı�����Ƿ��Զ����С�
	 * @param lineWarp �Ƿ��Զ����С�
	 */
	public void setLineWarp(boolean lineWarp) {
		this.lineWarp = lineWarp;
		repaint();
	}
	@Override
	public boolean getScrollableTracksViewportWidth(){
		if(lineWarp){
			return true;
		}else{
			return false;
		}
	}

}
