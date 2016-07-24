package com.dwarfeng.scheduler.module;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * �������ļ���ӳ�䡣
 * @author DwArFeng
 * @since 1.8
 */
final class SProjectFileReflect {
	
	/**�ļ��Ĵ洢·��*/
	private final File saveFile;
	/**����·��*/
	private final File workSpaceFile;
	/**����·��������*/
	private final Map<Scpath, ReadWriteLock> scpathLockPool;
	
	/**
	 * ���ɱ�׼�Ĺ����ļ�ӳ�䡣
	 * @throws IOException �����ɹ����ļ�ӳ��ʱ���ļ����������쳣ʱ�׳����쳣��
	 */
	public SProjectFileReflect(File saveFile, File workSpaceFile) throws IOException{
		//ָ���洢·��
		this.saveFile = saveFile;
		//���ɹ���·����
		this.workSpaceFile = workSpaceFile;
		//��������
		this.scpathLockPool = new HashMap<Scpath, ReadWriteLock>();
		
	}
	
	/**
	 * ��ȡ�����ļ�ӳ���еĴ洢·����
	 * @return �洢·����
	 */
	public File getSaveFile(){
		return this.saveFile;
	}
	
	/**
	 * ��ȡ�ù����ļ�ӳ���еĹ���Ŀ¼��ָʾ���ļ���
	 * @return ����Ŀ¼��ָʾ���ļ���
	 */
	public File getWorkspaceFile(){
		return this.workSpaceFile;
	}
	
	/**
	 * ��ȡָ��·���ڸù����ļ�ӳ���е�ͬ������
	 * <b> �����֮ǰ�����ļ�ӳ���е�������û�ж�Ӧ��·����������ᴴ��������������ӶԸ�����ӳ�䡣
	 * <b> ֻҪ·����������ͬ���ͻ���ͬһ������
	 * @param scpath ָ����·����
	 * @return ��ָ����·�����Ӧ��ͬ������
	 */
	public ReadWriteLock getScpathLock(Scpath scpath){
		scpathLockPool.putIfAbsent(scpath, new ReentrantReadWriteLock());
		return scpathLockPool.get(scpath);
	}
	
//	/**
//	 * �ڹ���Ŀ¼������һ���ļ�����Ϊ������̵Ĺ���Ŀ¼�ļ��С�
//	 * @return ָ����Ŀ¼�Ĺ���Ŀ¼�ļ���
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
//		//������������Ŀ¼���򴴽�����
//		file.mkdirs();
//		return file;
//	}
}
