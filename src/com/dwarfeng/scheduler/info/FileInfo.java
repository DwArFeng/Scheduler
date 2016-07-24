package com.dwarfeng.scheduler.info;


/**
 * ��¼�ļ���Ϣ���ࡣ
 * <p> ��¼���ļ���������Ϣ���ࡣ
 * TODO Ϊ��������Ӹ���ģ�����������򿪵��ĵ������Ĺ��ܡ�
 * @author DwArFeng
 * @since 1.8
 */
public class FileInfo {
	
	/**
	 * �ļ���Ϣ�Ĺ�������
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private String lastProjectPath = null;
		
		/**
		 * Ĭ�ϵĹ��췽����
		 */
		public Productor(){
			
		}
		
		/**
		 * �������һ�����򿪵��ļ���
		 * @param val ���һ�����򿪵��ļ���
		 * @return ����������
		 */
		public Productor lastProjectPath(String val){
			this.lastProjectPath = val;
			return this;
		}
		
		/**
		 * ���ݹ������������������ָ�����ļ���Ϣ��
		 * <p> û�����õ����Խ�ʹ��ȱʡ���á�
		 * @return ���ɵ��ļ���Ϣ��
		 */
		public FileInfo product(){
			return new FileInfo(lastProjectPath);
		}
		
	}
	
	/**����򿪵Ĺ���·��*/
	private final String lastProjectPath;
	
	/**
	 * 
	 * @param lastProjectPath
	 */
	public FileInfo(String lastProjectPath){
		this.lastProjectPath = lastProjectPath;
	}
	
	/**
	 * ��ȡ���һ������ȡ�Ĺ��̵�·����
	 * @return ���һ������ȡ�Ĺ��̵�·����
	 */
	public String getLastProjectPath() {
		if(lastProjectPath == null || !lastProjectPath.endsWith(".sch")) return null;
		return lastProjectPath;
	}
}
