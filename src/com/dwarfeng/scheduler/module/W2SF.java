package com.dwarfeng.scheduler.module;

import java.io.IOException;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.dom4j.DocumentException;

import com.dwarfeng.scheduler.module.project.Project;
import com.dwarfeng.scheduler.typedef.exception.UnstructFailedException;

/**
 * 定义工程工作目录层与工作压缩包文档层之间的所有通信方法。
 * <p> 该接口是Compatible Framework中的一员，主要负责不同版本保存于读取时的兼容性问题。
 * <p> 该方法名称是 Workspace to sch file function，取首字母，两个F避免重复，只取一个F。
 * <br> 该结构的方法由不同版本的读取器分别实现。 
 * <br> 不同版本的读取器的命名规则 "W" + versionName.replace('.','_');
 * @author DwArFeng。
 * @since 1.8。
 */
 interface W2SF{
	
	/**
	 * 压缩/解压模式锁代表的枚举。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum Operate{
		/**代表前台操作（如压缩、解压）的标记*/
		FOREGROUND,
		/**代表后台操作（如压缩、解压）的标记*/
		BACKGROUND
	}
	
	/**
	 * 在压缩文件中读取工程的骨架信息。
	 * @param file 指定的压缩文件。
	 * @return 根据文件的骨架信息构造出的工程。
	 * @throws DocumentException XML结构异常。
	 * @throws IOException 通信异常。
	 * @throws ZipException 压缩文件异常。
	 */
	public Project loadStruct(ZipFile file) throws IOException,UnstructFailedException;

	/**
	 * 向压缩文件输出流中写出工程的骨架文件。
	 * @param project 指定的工程文件。
	 * @param zout 压缩包输出流。
	 * @throws IOException 通信异常。
	 */
	public void saveStruct(Project project,ZipOutputStream zout) throws IOException;
	
	/**
	 * 从指定的路径中解压指定文件。
	 * @param project 解压相关的工程对象。
	 * @param file 与解压有关的压缩文件。
	 * @param scpath 解压相关的工作目录路径。
	 * @throws IOException 通信异常。
	 * @throws ZipException 压缩文件异常。
	 */
	public void unzipFile(Project project,ZipFile file,Scpath scpath) throws IOException;
	
	/**
	 * 想指定的Zip输出流添加指定的文件。
	 * @param pfr 与压缩有关的工程文件映射。
	 * @param zout 与压缩有关的。
	 * @param scpath 指定的工作目录。
	 * @throws IOException 通信异常。
	 */
	public void zipFile(Project project, Scpath scpath, ZipOutputStream zout) throws IOException;
	
	/**
	 * 将该版本的Scpath按照规则转换为这个版本的Scpath。
	 * <p> 注意，必须要保证 {@code scpath.equals(getScpathThisVersion(getScpathLastVersion(scpath)));}
	 * ，也就是说，Scpath转换为最新的版本之后再转换为该版本，所得到的Scpath必须与转换前的一致。
	 * @param scpath 指定的工作目录路径。
	 * @return 转换后的最新版本的工作目录路径。
	 */
	public Scpath getScpathLastVersion(Scpath scpath);
	/**
	 * 将最新版本的Scpath转换为这个版本的Scpath。
	 * <p> 注意，必须要保证 {@code scpath.equals(getScpathThisVersion(getScpathLastVersion(scpath)));}
	 * ，也就是说，Scpath转换为最新的版本之后再转换为该版本，所得到的Scpath必须与转换前的一致。
	 * @param scpath 指定的最新版本的工作目录路径。
	 * @return 这个版本的工作目录路径。
	 */
	public Scpath getScpathThisVersion(Scpath scpath);
}