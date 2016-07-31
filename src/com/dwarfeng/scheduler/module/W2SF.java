package com.dwarfeng.scheduler.module;

import java.io.IOException;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.dom4j.DocumentException;

import com.dwarfeng.scheduler.module.project.Project;
import com.dwarfeng.scheduler.typedef.exception.UnstructFailedException;

/**
 * ���幤�̹���Ŀ¼���빤��ѹ�����ĵ���֮�������ͨ�ŷ�����
 * <p> �ýӿ���Compatible Framework�е�һԱ����Ҫ����ͬ�汾�����ڶ�ȡʱ�ļ��������⡣
 * <p> �÷��������� Workspace to sch file function��ȡ����ĸ������F�����ظ���ֻȡһ��F��
 * <br> �ýṹ�ķ����ɲ�ͬ�汾�Ķ�ȡ���ֱ�ʵ�֡� 
 * <br> ��ͬ�汾�Ķ�ȡ������������ "W" + versionName.replace('.','_');
 * @author DwArFeng��
 * @since 1.8��
 */
 interface W2SF{
	
	/**
	 * ѹ��/��ѹģʽ�������ö�١�
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum Operate{
		/**����ǰ̨��������ѹ������ѹ���ı��*/
		FOREGROUND,
		/**�����̨��������ѹ������ѹ���ı��*/
		BACKGROUND
	}
	
	/**
	 * ��ѹ���ļ��ж�ȡ���̵ĹǼ���Ϣ��
	 * @param file ָ����ѹ���ļ���
	 * @return �����ļ��ĹǼ���Ϣ������Ĺ��̡�
	 * @throws DocumentException XML�ṹ�쳣��
	 * @throws IOException ͨ���쳣��
	 * @throws ZipException ѹ���ļ��쳣��
	 */
	public Project loadStruct(ZipFile file) throws IOException,UnstructFailedException;

	/**
	 * ��ѹ���ļ��������д�����̵ĹǼ��ļ���
	 * @param project ָ���Ĺ����ļ���
	 * @param zout ѹ�����������
	 * @throws IOException ͨ���쳣��
	 */
	public void saveStruct(Project project,ZipOutputStream zout) throws IOException;
	
	/**
	 * ��ָ����·���н�ѹָ���ļ���
	 * @param project ��ѹ��صĹ��̶���
	 * @param file ���ѹ�йص�ѹ���ļ���
	 * @param scpath ��ѹ��صĹ���Ŀ¼·����
	 * @throws IOException ͨ���쳣��
	 * @throws ZipException ѹ���ļ��쳣��
	 */
	public void unzipFile(Project project,ZipFile file,Scpath scpath) throws IOException;
	
	/**
	 * ��ָ����Zip��������ָ�����ļ���
	 * @param pfr ��ѹ���йصĹ����ļ�ӳ�䡣
	 * @param zout ��ѹ���йصġ�
	 * @param scpath ָ���Ĺ���Ŀ¼��
	 * @throws IOException ͨ���쳣��
	 */
	public void zipFile(Project project, Scpath scpath, ZipOutputStream zout) throws IOException;
	
	/**
	 * ���ð汾��Scpath���չ���ת��Ϊ����汾��Scpath��
	 * <p> ע�⣬����Ҫ��֤ {@code scpath.equals(getScpathThisVersion(getScpathLastVersion(scpath)));}
	 * ��Ҳ����˵��Scpathת��Ϊ���µİ汾֮����ת��Ϊ�ð汾�����õ���Scpath������ת��ǰ��һ�¡�
	 * @param scpath ָ���Ĺ���Ŀ¼·����
	 * @return ת��������°汾�Ĺ���Ŀ¼·����
	 */
	public Scpath getScpathLastVersion(Scpath scpath);
	/**
	 * �����°汾��Scpathת��Ϊ����汾��Scpath��
	 * <p> ע�⣬����Ҫ��֤ {@code scpath.equals(getScpathThisVersion(getScpathLastVersion(scpath)));}
	 * ��Ҳ����˵��Scpathת��Ϊ���µİ汾֮����ת��Ϊ�ð汾�����õ���Scpath������ת��ǰ��һ�¡�
	 * @param scpath ָ�������°汾�Ĺ���Ŀ¼·����
	 * @return ����汾�Ĺ���Ŀ¼·����
	 */
	public Scpath getScpathThisVersion(Scpath scpath);
}