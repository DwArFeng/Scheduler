package com.dwarfeng.scheduler.module;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 工程与文件的映射。
 * @author DwArFeng
 * @since 1.8
 */
final class SProjectFileReflect {
	
	/**文件的存储路径*/
	private final File saveFile;
	/**工作路径*/
	private final File workSpaceFile;
	/**工程路径的锁池*/
	private final Map<Scpath, ReadWriteLock> scpathLockPool;
	
	/**
	 * 生成标准的工作文件映射。
	 * @throws IOException 当生成工作文件映射时与文件交互发生异常时抛出的异常。
	 */
	public SProjectFileReflect(File saveFile, File workSpaceFile) throws IOException{
		//指定存储路径
		this.saveFile = saveFile;
		//生成工作路径。
		this.workSpaceFile = workSpaceFile;
		//生成锁池
		this.scpathLockPool = new HashMap<Scpath, ReadWriteLock>();
		
	}
	
	/**
	 * 获取工程文件映射中的存储路径。
	 * @return 存储路径。
	 */
	public File getSaveFile(){
		return this.saveFile;
	}
	
	/**
	 * 获取该工程文件映射中的工作目录所指示的文件。
	 * @return 工作目录所指示的文件。
	 */
	public File getWorkspaceFile(){
		return this.workSpaceFile;
	}
	
	/**
	 * 获取指定路径在该工程文件映射中的同步锁。
	 * <b> 如果在之前，该文件映射中的锁池中没有对应的路径的锁，则会创建新锁，并且添加对该锁的映射。
	 * <b> 只要路径的名称相同，就会获得同一把锁。
	 * @param scpath 指定的路径。
	 * @return 与指定的路径向对应的同步锁。
	 */
	public ReadWriteLock getScpathLock(Scpath scpath){
		scpathLockPool.putIfAbsent(scpath, new ReentrantReadWriteLock());
		return scpathLockPool.get(scpath);
	}
	
//	/**
//	 * 在工作目录下申请一个文件夹作为这个工程的工作目录文件夹。
//	 * @return 指向工作目录的工作目录文件。
//	 * @throws IOException 
//	 */
//	private static File genWorkspaceFile() throws IOException {
//		int tryTimes = 0;
//		long l = Calendar.getInstance().getTimeInMillis();
//		File file;
//		String path;
//		do{
//			path = "" + l + tryTimes + File.separator;
//			file = new File(Scheduler133.getInstance().getWorkspacePath()  + path + File.separator);
//		}while(file.exists());
//		//如果不存在这个目录，则创建它。
//		file.mkdirs();
//		return file;
//	}
}
