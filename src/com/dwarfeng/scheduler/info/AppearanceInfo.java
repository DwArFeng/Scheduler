package com.dwarfeng.scheduler.info;


/**
 * 控制外观的类。
 * <p> 该类负责记录程序的“外观”，包括窗口的大小，各个工具栏是否被隐藏等等。
 * @author DwArFeng
 * @since 1.8
 */
public class AppearanceInfo {
	
	/**
	 * 外观信息的构造器。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		private int frameExtension = 0;
		private int frameWidth = 800;
		private int frameHeight = 600;

		private boolean consoleVisible = true;
		private boolean projectTreeVisible = true;
		private boolean paramVisible = true;
		private boolean functionPanelVisible = true;
		
		private int consoleHeight = 100;
		private int projectTreeWidth = 150;
		private int paramWidth = 150;
		
		/**
		 * 提供默认的构造。
		 */
		public Productor(){}
		
		/**
		 * 设置程序界面的扩展模式。
		 * @param val 扩展模式的按位值。
		 * @return 构造器自身。
		 * @see JFrame#getExtendedState();
		 */
		public Productor frameExtension(int val){
			this.frameExtension = val;
			return this;
		}
		/**
		 * 设置程序界面的宽度。
		 * @param val 界面的宽度。
		 * @return 构造器自身。
		 */
		public Productor frameWidth(int val){
			this.frameWidth = val;
			return this;
		}
		/**
		 * 设置程序界面的高度。
		 * @param val 界面的高度。
		 * @return 构造器自身。
		 */
		public Productor frameHeight(int val){
			this.frameHeight = val;
			return this;
		}
		/**
		 * 设置控制台是否可见。
		 * @param val 控制台是否可见。
		 * @return 构造器自身。
		 */
		public Productor consoleVisible(boolean val){
			this.consoleVisible = val;
			return this;
		}
		/**
		 * 设置工程树是否可见。
		 * @param val 工程树是否可见。
		 * @return 构造器自身。
		 */
		public Productor projectTreeVisible(boolean val){
			this.projectTreeVisible = val;
			return this;
		}
		/**
		 * 设置参数栏是否可见。
		 * @param val 参数栏是否可见。
		 * @return 构造器自身。
		 */
		public Productor paramVisible(boolean val){
			this.paramVisible = val;
			return this;
		}
		/**
		 * 设置功能面板是否可见。
		 * @param val 功能面板是否可见。
		 * @return 构造器自身。
		 */
		public Productor functionPanelVisible(boolean val){
			this.functionPanelVisible = val;
			return this;
		}
		/**
		 * 设置控制台的高度。
		 * @param val 控制台的高度。
		 * @return 构造器自身。
		 */
		public Productor consoleHeight(int val){
			this.consoleHeight = val;
			return this;
		}
		/**
		 * 设置工程树的宽度。
		 * @param val 工程树的宽度。
		 * @return 构造器自身。
		 */
		public Productor projectTreeWidth(int val){
			this.projectTreeWidth = val;
			return this;
		}
		/**
		 * 设置参数栏的宽度。 
		 * @param val 参数栏的宽度。
		 * @return 构造器自身。
		 */
		public Productor paramWidth(int val){
			this.paramWidth = val;
			return this;
		}
		
		/**
		 * 根据构造器的设置情况来生成外观信息。
		 * <p> 没有设置的属性将使用缺省配置。
		 * @return 生成的外观信息。
		 */
		public AppearanceInfo product(){
			return new AppearanceInfo(
					frameExtension,
					frameWidth,
					frameHeight,
					consoleVisible,
					projectTreeVisible,
					paramVisible,
					functionPanelVisible,
					consoleHeight,
					projectTreeWidth,
					paramWidth
			);
		}
	}
	
	private final int frameExtension;
	private final int frameWidth;
	private final int frameHeight;

	private final boolean consoleVisible;
	private final boolean projectTreeVisible;
	private final boolean paramVisible;
	private final boolean functionPanelVisible;
	
	private final int consoleHeight;
	private final int projectTreeWidth;
	private final int paramWidth;
	
	private AppearanceInfo(
			int frameExtension,
			int frameWidth,
			int frameHeight,
			boolean consoleVisible,
			boolean projectTreeVisible,
			boolean paramVisible,
			boolean functionPanelVisible,
			int consoleHeight,
			int projectTreeWidth,
			int paramWidth
	){
		this.frameExtension = frameExtension;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		
		this.consoleVisible = consoleVisible;
		this.projectTreeVisible = projectTreeVisible;
		this.paramVisible = paramVisible;
		
		this.functionPanelVisible = functionPanelVisible;
		this.consoleHeight = consoleHeight;
		this.projectTreeWidth = projectTreeWidth;
		this.paramWidth = paramWidth;
	}

	/**
	 * 
	 * @return
	 */
	public int getFrameExtension(){
		return this.frameExtension;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getFrameWidth(){
		return frameWidth;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getFrameHeight(){
		return frameHeight;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isConsoleVisible() {
		return consoleVisible;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isProjectTreeVisible(){
		return projectTreeVisible;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isParamVisible(){
		return paramVisible;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isFunctionPanelVisible(){
		return functionPanelVisible;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getConsoleHeight(){
		return consoleHeight;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getProjectTreeWidth(){
		return projectTreeWidth;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getParamWidth(){
		return paramWidth;
	}
}
