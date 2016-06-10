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
public class JLineWarpableTextPane extends JTextPane{
	
	private static final long serialVersionUID = 2849498237282832705L;
	
	private boolean lineWarp;
	
	/**
	 * ����һ��Ĭ�ϵģ��Զ����еĿ��Զ������ı���塣
	 */
	public JLineWarpableTextPane(){
		this(true);
	}
	/**
	 * ����һ��ָ���Ƿ��Զ����еĿ��Զ������ı���塣
	 * @param isLineWarp �Ƿ��Զ����С�
	 */
	public JLineWarpableTextPane(boolean isLineWarp){
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
