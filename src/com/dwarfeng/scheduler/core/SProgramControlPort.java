package com.dwarfeng.scheduler.core;

import java.util.Locale;
import java.util.Queue;

import com.dwarfeng.dwarffunction.program.mvc.ProgramControlPort;
import com.dwarfeng.dwarffunction.threads.NadeRunner;

public interface SProgramControlPort extends ProgramControlPort{

	/**
	 * �����ĺ�̨����������һ���µ����ж���
	 * @param runner ���ж���
	 */
	public void backgroundInvoke(NadeRunner runner);
	
	/**
	 * ���س���ĺ�̨���е����ڵȴ���Ԫ��������
	 * @return ��̨�������ڵȴ���Ԫ��������
	 */
	public int getBackgroundQueueSize();
	
	/**
	 * ���س����̨���е����ڵȴ���Ԫ����ɵĶ��С�
	 * <p> �ö����Ǻ�̨���еĸ�������ʹ�Ըö��н����޸ģ�Ҳ����Գ����̨��ԭʼ�������Ӱ�졣
	 * @return �����̨�����е�����Ԫ����ɵ��Ķ��С�
	 */
	public Queue<NadeRunner> getBackgroundQueue();
	
	
	
	/**
	 * ���ó�������Ե�����
	 * <p> �ı��ֶλ��Զ����Ϊָ��������Ӧ���ֶΡ�
	 * @param locale ָ���ĵ�����
	 */
	public void setLocale(Locale locale);

	
}
