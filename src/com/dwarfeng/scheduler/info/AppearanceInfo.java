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
	 * ��ȡ������������İ�λ��ʾ��ʽ��
	 * <p> ��ֵ��¼�˴����Ƿ�����󻯣����ǵ�����󻯣�����û����󻯡�
	 * @return �������������
	 */
	public int getFrameExtension(){
		return this.frameExtension;
	}
	
	/**
	 * ��ȡ���ڵ���Ļ��ȡ�
	 * @return ���ڵ���Ļ��ȡ�
	 */
	public int getFrameWidth(){
		return frameWidth;
	}
	
	/**
	 * ��ȡ���ڵ���Ļ�߶ȡ�
	 * @return ���ڵ���Ļ�߶ȡ�
	 */
	public int getFrameHeight(){
		return frameHeight;
	}
	
	/**
	 * ��ȡ����̨�Ƿ�ɼ���
	 * @return ����̨�Ƿ�ɼ���
	 */
	public boolean isConsoleVisible() {
		return consoleVisible;
	}
	
	/**
	 * ��ȡ�������Ƿ�ɼ���
	 * @return �������Ƿ�ɼ���
	 */
	public boolean isProjectTreeVisible(){
		return projectTreeVisible;
	}
	
	/**
	 * ��ȡ�������Ƿ�ɼ���
	 * @return �������Ƿ�ɼ���
	 */
	public boolean isParamVisible(){
		return paramVisible;
	}
	
	/**
	 * ��ȡ��������Ƿ�ɼ���
	 * @return ��������Ƿ�ɼ���
	 */
	public boolean isFunctionPanelVisible(){
		return functionPanelVisible;
	}
	
	/**
	 * ��ȡ����̨�ĸ߶ȡ�
	 * @return ����̨�ĸ߶ȡ�
	 */
	public int getConsoleHeight(){
		return consoleHeight;
	}
	
	/**
	 * ��ȡ�������Ŀ�ȡ�
	 * @return �������Ŀ�ȡ�
	 */
	public int getProjectTreeWidth(){
		return projectTreeWidth;
	}
	
	/**
	 * ��ȡ�������Ŀ�ȡ�
	 * @return �������Ŀ�ȡ�
	 */
	public int getParamWidth(){
		return paramWidth;
	}
}
