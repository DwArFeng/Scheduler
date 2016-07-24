package com.dwarfeng.scheduler.info;


/**
 * 记录文件信息的类。
 * <p> 记录着文件的运行信息的类。
 * TODO 为这个类增加更多的，类似于最近打开的文档这样的功能。
 * @author DwArFeng
 * @since 1.8
 */
public class FileInfo {
	
	/**
	 * 文件信息的构造器。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		private String lastProjectPath = null;
		
		/**
		 * 默认的构造方法。
		 */
		public Productor(){
			
		}
		
		/**
		 * 设置最后一个被打开的文件。
		 * @param val 最后一个被打开的文件。
		 * @return 构造器自身。
		 */
		public Productor lastProjectPath(String val){
			this.lastProjectPath = val;
			return this;
		}
		
		/**
		 * 根据构造器的配置情况生成指定的文件信息。
		 * <p> 没有设置的属性将使用缺省配置。
		 * @return 生成的文件信息。
		 */
		public FileInfo product(){
			return new FileInfo(lastProjectPath);
		}
		
	}
	
	/**最近打开的工程路径*/
	private final String lastProjectPath;
	
	/**
	 * 
	 * @param lastProjectPath
	 */
	public FileInfo(String lastProjectPath){
		this.lastProjectPath = lastProjectPath;
	}
	
	/**
	 * 获取最后一个被读取的工程的路径。
	 * @return 最后一个被读取的工程的路径。
	 */
	public String getLastProjectPath() {
		if(lastProjectPath == null || !lastProjectPath.endsWith(".sch")) return null;
		return lastProjectPath;
	}
}
