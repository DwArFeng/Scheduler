package com.dwarfeng.scheduler.gui;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * 实现了切换是否自动换行功能的文本编辑面板。
 * <p> 该面板需要放置在{@linkplain JScrollPane} 中，以达到最好的效果，并且，放置该文本编辑面板的{@linkplain JScrollPane} 需要
 * 设置Layout属性为{@linkplain LineWarpableViewportLayout}。
 * @author DwArFeng
 * @since 1.8
 */
public class JLineWarpableTextPane extends JTextPane{
	
	private static final long serialVersionUID = 2849498237282832705L;
	
	private boolean lineWarp;
	
	/**
	 * 生成一个默认的，自动换行的可自动换行文本面板。
	 */
	public JLineWarpableTextPane(){
		this(true);
	}
	/**
	 * 生成一个指定是否自动换行的可自动换行文本面板。
	 * @param isLineWarp 是否自动换行。
	 */
	public JLineWarpableTextPane(boolean isLineWarp){
		super();
		this.setLineWarp(isLineWarp);
	}
	
	/**
	 * 返回该文本面板是否自动换行。
	 * @return 是否自动换行。
	 */
	public boolean isLineWarp() {
		return lineWarp;
	}
	/**
	 * 设置该文本面板是否自动换行。
	 * @param lineWarp 是否自动换行。
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
