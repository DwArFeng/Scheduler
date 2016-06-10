package com.dwarfeng.scheduler.info;


/**
 * ������۵��ࡣ
 * <p> ���ฺ���¼����ġ���ۡ����������ڵĴ�С�������������Ƿ����صȵȡ�
 * @author DwArFeng
 * @since 1.8
 */
public class AppearanceInfo {
	
	/**
	 * �����Ϣ�Ĺ�������
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
		 * �ṩĬ�ϵĹ��졣
		 */
		public Productor(){}
		
		/**
		 * ���ó���������չģʽ��
		 * @param val ��չģʽ�İ�λֵ��
		 * @return ����������
		 * @see JFrame#getExtendedState();
		 */
		public Productor frameExtension(int val){
			this.frameExtension = val;
			return this;
		}
		/**
		 * ���ó������Ŀ�ȡ�
		 * @param val ����Ŀ�ȡ�
		 * @return ����������
		 */
		public Productor frameWidth(int val){
			this.frameWidth = val;
			return this;
		}
		/**
		 * ���ó������ĸ߶ȡ�
		 * @param val ����ĸ߶ȡ�
		 * @return ����������
		 */
		public Productor frameHeight(int val){
			this.frameHeight = val;
			return this;
		}
		/**
		 * ���ÿ���̨�Ƿ�ɼ���
		 * @param val ����̨�Ƿ�ɼ���
		 * @return ����������
		 */
		public Productor consoleVisible(boolean val){
			this.consoleVisible = val;
			return this;
		}
		/**
		 * ���ù������Ƿ�ɼ���
		 * @param val �������Ƿ�ɼ���
		 * @return ����������
		 */
		public Productor projectTreeVisible(boolean val){
			this.projectTreeVisible = val;
			return this;
		}
		/**
		 * ���ò������Ƿ�ɼ���
		 * @param val �������Ƿ�ɼ���
		 * @return ����������
		 */
		public Productor paramVisible(boolean val){
			this.paramVisible = val;
			return this;
		}
		/**
		 * ���ù�������Ƿ�ɼ���
		 * @param val ��������Ƿ�ɼ���
		 * @return ����������
		 */
		public Productor functionPanelVisible(boolean val){
			this.functionPanelVisible = val;
			return this;
		}
		/**
		 * ���ÿ���̨�ĸ߶ȡ�
		 * @param val ����̨�ĸ߶ȡ�
		 * @return ����������
		 */
		public Productor consoleHeight(int val){
			this.consoleHeight = val;
			return this;
		}
		/**
		 * ���ù������Ŀ�ȡ�
		 * @param val �������Ŀ�ȡ�
		 * @return ����������
		 */
		public Productor projectTreeWidth(int val){
			this.projectTreeWidth = val;
			return this;
		}
		/**
		 * ���ò������Ŀ�ȡ� 
		 * @param val �������Ŀ�ȡ�
		 * @return ����������
		 */
		public Productor paramWidth(int val){
			this.paramWidth = val;
			return this;
		}
		
		/**
		 * ���ݹ�������������������������Ϣ��
		 * <p> û�����õ����Խ�ʹ��ȱʡ���á�
		 * @return ���ɵ������Ϣ��
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
