package com.dwarfeng.scheduler.module;

import java.io.IOException;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import com.dwarfeng.scheduler.module.project.Project;
import com.dwarfeng.scheduler.typedef.exception.UnstructFailedException;

/**
 * �����Ŵ浵�汾��ö�١�
 * <p> �ýӿ���Compatible Framework�е�һԱ����Ҫ����ͬ�汾�����ڶ�ȡʱ�ļ��������⡣
 * <p> �浵�汾��������ͬ����浵�ķ�ʽ��һ����˵�����ų���汾�����ӣ��µĹ��ܱ�½����
 * ��ӵ������У�Ҳ��Ҫ�浵�İ汾���в��ϵĸ��¡�Ϊ�˳�����õ���ǰ���ݣ���Ҫ�ð汾���������¾ɴ浵��
 * @author DwArFeng
 * @since 1.8
 */
enum ProjectVersion {
	
	/**�汾��0.0.0*/
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
	/**�汾��0.1.0*/
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
	/**�汾��0.2.0*/
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
	 * ��ȡ�汾ö��������İ汾�ŵ��ı���ʽ��
	 * @return �汾���ı���ʽ��
	 */
	public String getVersionString(){
		return this.version;
	}
	
	/**
	 * ���ض�Ӧ�汾�Ľ�������
	 * @return ��Ӧ�汾�Ľ�������
	 */
	public W2SF getW2sf(){
		return this.w2sf;
	}
	
}














