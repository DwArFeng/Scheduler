package com.dwarfeng.scheduler.gui;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * 实现了切换是否自动换行功能的文本编辑面板。
 * <p> 该面板需要放置在{@linkplain JScrollPane} 中，以达到最好的效果，并且，放置该文本编辑面板的{@linkplain JScrollPane} 需要
 * 设置Layout属性为{@linkplain LineWrapableViewportLayout}。
 * @author DwArFeng
 * @since 1.8
 */
public class JLineWrapableTextPane extends JTextPane{
	
	private static final long serialVersionUID = -6707981349896752396L;
	
	private boolean lineWrap;
	
	/**
	 * 生成一个默认的，自动换行的可自动换行文本面板。
	 */
	public JLineWrapableTextPane(){
		this(true);
	}
	/**
	 * 生成一个指定是否自动换行的可自动换行文本面板。
	 * @param isLineWrap 是否自动换行。
	 */
	public JLineWrapableTextPane(boolean isLineWrap){
		super();
		this.setLineWrap(isLineWrap);
	}
	
	/**
	 * 返回该文本面板是否自动换行。
	 * @return 是否自动换行。
	 */
	public boolean isLineWrap() {
		return lineWrap;
	}
	/**
	 * 设置该文本面板是否自动换行。
	 * @param lineWrap 是否自动换行。
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
