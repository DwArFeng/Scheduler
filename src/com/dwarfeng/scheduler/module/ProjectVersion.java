package com.dwarfeng.scheduler.module;

import java.io.IOException;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import com.dwarfeng.scheduler.module.project.Project;
import com.dwarfeng.scheduler.typedef.exception.UnstructFailedException;

/**
 * 代表着存档版本的枚举。
 * <p> 该接口是Compatible Framework中的一员，主要负责不同版本保存于读取时的兼容性问题。
 * <p> 存档版本号是区别不同种类存档的方式。一般来说，随着程序版本的增加，新的功能被陆续的
 * 添加到程序中，也需要存档的版本进行不断的更新。为了程序更好的向前兼容，需要用版本号来区分新旧存档。
 * @author DwArFeng
 * @since 1.8
 */
enum ProjectVersion {
	
	/**版本号0.0.0*/
	W0_0_0("0.0.0",new W2SF(){
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.io.W2SF#loadStruct(java.util.zip.ZipFile)
		 */
		@Override
		public Project loadStruct(ZipFile file) throws IOException,UnstructFailedException{
			return W2SFCommonFunc.loadStruct0_0_0(file);
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.io.W2SF#saveStruct(com.dwarfeng.scheduler.project.Project, java.util.zip.ZipOutputStream)
		 */
		@Override
		public void saveStruct(Project project, ZipOutputStream zout) throws IOException {
			W2SFCommonFunc.saveStruct0_0_0(project, zout);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.io.W2SF#unzipFile(com.dwarfeng.scheduler.project.Project, java.util.zip.ZipFile, com.dwarfeng.scheduler.io.Scpath)
		 */
		@Override
		public void unzipFile(Project project, ZipFile file, Scpath scpath) throws ZipException, IOException {
			W2SFCommonFunc.unzipFile0_0_0(project, file, scpath);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.io.W2SF#zipFile(com.dwarfeng.scheduler.project.Project, com.dwarfeng.scheduler.io.Scpath, java.util.zip.ZipOutputStream)
		 */
		@Override
		public void zipFile(Project project, Scpath scpath, ZipOutputStream zout) throws IOException {
			W2SFCommonFunc.zipFile0_0_0(project, scpath, zout);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.io.W2SF#getScpathLastVersion(com.dwarfeng.scheduler.io.Scpath)
		 */
		@Override
		public Scpath getScpathLastVersion(Scpath scpath) {
			return W2SFCommonFunc.getScpathLastVersion0_0_0(scpath);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.io.W2SF#getScpathThisVersion(com.dwarfeng.scheduler.io.Scpath)
		 */
		@Override
		public Scpath getScpathThisVersion(Scpath scpath) {
			return W2SFCommonFunc.getScpathThisVersion0_0_0(scpath);
		}
	}),
	/**版本号0.1.0*/
	W0_1_0("0.1.0",new W2SF(){
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.io.W2SF#loadStruct(java.util.zip.ZipFile)
		 */
		@Override
		public Project loadStruct(ZipFile file) throws IOException,UnstructFailedException{
			return W2SFCommonFunc.loadStruct0_1_0(file);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.io.W2SF#saveStruct(com.dwarfeng.scheduler.project.Project, java.util.zip.ZipOutputStream)
		 */
		@Override
		public void saveStruct(Project project, ZipOutputStream zout)throws IOException {
			W2SFCommonFunc.saveStruct0_1_0(project, zout);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.io.W2SF#unzipFile(com.dwarfeng.scheduler.project.Project, java.util.zip.ZipFile, com.dwarfeng.scheduler.io.Scpath)
		 */
		@Override
		public void unzipFile(Project project, ZipFile file, Scpath scpath)throws ZipException, IOException {
			W2SFCommonFunc.unzipFile0_0_0(project, file, scpath);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.io.W2SF#zipFile(com.dwarfeng.scheduler.project.Project, com.dwarfeng.scheduler.io.Scpath, java.util.zip.ZipOutputStream)
		 */
		@Override
		public void zipFile(Project project, Scpath scpath, ZipOutputStream zout)throws IOException {
			W2SFCommonFunc.zipFile0_0_0(project, scpath, zout);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.io.W2SF#getScpathLastVersion(com.dwarfeng.scheduler.io.Scpath)
		 */
		@Override
		public Scpath getScpathLastVersion(Scpath scpath) {
			return W2SFCommonFunc.getScpathLastVersion0_0_0(scpath);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.io.W2SF#getScpathThisVersion(com.dwarfeng.scheduler.io.Scpath)
		 */
		@Override
		public Scpath getScpathThisVersion(Scpath scpath) {
			return W2SFCommonFunc.getScpathThisVersion0_0_0(scpath);
		}
	}),
	/**版本号0.2.0*/
	W0_2_0("0.2.0",new W2SF(){
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.io.W2SF#loadStruct(java.util.zip.ZipFile)
		 */
		@Override
		public Project loadStruct(ZipFile file) throws IOException,UnstructFailedException{
			return W2SFCommonFunc.loadStruct0_2_0(file);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.io.W2SF#saveStruct(com.dwarfeng.scheduler.project.Project, java.util.zip.ZipOutputStream)
		 */
		@Override
		public void saveStruct(Project project, ZipOutputStream zout)throws IOException {
			W2SFCommonFunc.saveStruct0_2_0(project, zout);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.io.W2SF#unzipFile(com.dwarfeng.scheduler.project.Project, java.util.zip.ZipFile, com.dwarfeng.scheduler.io.Scpath)
		 */
		@Override
		public void unzipFile(Project project, ZipFile file, Scpath scpath)throws ZipException, IOException {
			W2SFCommonFunc.unzipFile0_0_0(project, file, scpath);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.io.W2SF#zipFile(com.dwarfeng.scheduler.project.Project, com.dwarfeng.scheduler.io.Scpath, java.util.zip.ZipOutputStream)
		 */
		@Override
		public void zipFile(Project project, Scpath scpath, ZipOutputStream zout)throws IOException {
			W2SFCommonFunc.zipFile0_0_0(project, scpath, zout);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.io.W2SF#getScpathLastVersion(com.dwarfeng.scheduler.io.Scpath)
		 */
		@Override
		public Scpath getScpathLastVersion(Scpath scpath) {
			return W2SFCommonFunc.getScpathLastVersion0_0_0(scpath);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.io.W2SF#getScpathThisVersion(com.dwarfeng.scheduler.io.Scpath)
		 */
		@Override
		public Scpath getScpathThisVersion(Scpath scpath) {
			return W2SFCommonFunc.getScpathThisVersion0_0_0(scpath);
		}
	});
	
	private final String version;
	private final W2SF w2sf;
	
	private ProjectVersion(String version,W2SF w2sf){
		this.version = version;
		this.w2sf = w2sf;
	}
	/**
	 * 获取版本枚举所代表的版本号的文本形式。
	 * @return 版本的文本形式。
	 */
	public String getVersionString(){
		return this.version;
	}
	
	/**
	 * 返回对应版本的解析器。
	 * @return 对应版本的解析器。
	 */
	public W2SF getW2sf(){
		return this.w2sf;
	}
	
}














