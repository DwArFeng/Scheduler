package com.dwarfeng.scheduler.core;

import java.util.MissingResourceException;

import com.dwarfeng.dwarffunction.threads.NadeRunner;
import com.dwarfeng.dwarffunction.threads.RunnerQueue;
import com.dwarfeng.scheduler.info.ExceptionStringFieldKey;
import com.dwarfeng.scheduler.info.LabelStringFieldKey;
import com.dwarfeng.scheduler.info.MessageStringFieldKey;
import com.dwarfeng.scheduler.typedef.cabstruct.SControlMgr;
import com.dwarfeng.scheduler.typedef.cabstruct.SModuleMgr;
import com.dwarfeng.scheduler.typedef.cabstruct.SViewMgr;

/**
 * �ƻ��������
 * <p> �ó������Ҫ�����ǽ���ƻ��Ĺ滮�����Լ��ʼ���������⡣�ó�����ҪΧ��������������б�д��
 * <br>&nbsp;&nbsp; 1.�ʼǼ�¼�Լ����������˲������������е��е��Ʊʼǵȱʼǹ��������������������ĳЩ���ܡ�
 * <br>&nbsp;&nbsp; 2.�������ͨ�������������ָ���ļƻ����񣬳������ϵͳʱ��������Щ������й���
 * <p> ����ṹ����MVC�ṹ�����Ҿ����ܵ������໥���롣
 * <br> <code>Scheduler</code>����ӵ��һ����̨ά�������κ���Ҫ���ú�̨�ķ����������йܵ����ά�����ϡ�
 * @author DwArFeng
 * @since 1.8
 */
public final class Scheduler {

	/**�йصĺ�̨*/
	private final static RunnerQueue<NadeRunner> background = new RunnerQueue<NadeRunner>();
	
//	/**
//	 * ��ó���ĺ�̨�����һ����������
//	 * @param runner ָ������������
//	 */
//	public static void backgroundInvoke(NadeRunner runner){
//		background.invoke(runner);
//	}
//	
//	/**
//	 * ��ȡ�����̨�ĵȴ����е�Ԫ��������
//	 * @return Ԫ��������
//	 */
//	public static int getBackgroundQueueSize(){
//		return background.getQueueSize();
//	}
	
	/**�йص��ı��ֶ�*/
	private static StringFields stringFields = new StringFields();
	
	/**
	 * �����쳣�ı���
	 * @param key �쳣�ı���������
	 * @return ������Ӧ���ı���
	 */
	public static String getException(ExceptionStringFieldKey key){
		return stringFields.getException(key);
	}
	
	/**
	 * ���ر�ǩ�ı���
	 * @param key ��ǩ�ı���������
	 * @return  ������Ӧ���ı���
	 */
	public static String getLabel(LabelStringFieldKey key){
		return stringFields.getLabel(key);
	}
	
	/**
	 * ������Ϣ�ı���
	 * @param key ��Ϣ�ı���������
	 * @return ������Ӧ���ı���
	 */
	public static String getMessage(MessageStringFieldKey key){
		return stringFields.getMessage(key);
	}
	
	
	
	private SModuleMgr module;
	private SViewMgr view;
	private SControlMgr control;
	
	private Scheduler(SModuleMgr module, SViewMgr view, SControlMgr control){
		this.module = module;
		this.view = view;
		this.control = control;
	}
	
	
}
