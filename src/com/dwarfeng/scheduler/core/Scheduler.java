package com.dwarfeng.scheduler.core;

import com.dwarfeng.scheduler.typedef.cabstruct.SControlMgr;
import com.dwarfeng.scheduler.typedef.cabstruct.SModuleMgr;
import com.dwarfeng.scheduler.typedef.cabstruct.SViewMgr;

/**
 * �ƻ��������
 * <p> �ó������Ҫ�����ǽ���ƻ��Ĺ滮�����Լ��ʼ���������⡣�ó�����ҪΧ��������������б�д��
 * <br>&nbsp;&nbsp; 1.�ʼǼ�¼�Լ����������˲������������е��е��Ʊʼǵȱʼǹ��������������������ĳЩ���ܡ�
 * <br>&nbsp;&nbsp; 2.�������ͨ�������������ָ���ļƻ����񣬳������ϵͳʱ��������Щ������й���
 * <p> �����е����з����������̰߳�ȫ�ģ�����Ҫ���̵߳��ã���ʹ���ⲿͬ����
 * @author DwArFeng
 * @since 1.8
 */
public final class Scheduler {
	
	
	private SModuleMgr module;
	private SViewMgr view;
	private SControlMgr control;
	
	private Scheduler(SModuleMgr module, SViewMgr view, SControlMgr control){
		this.module = module;
		this.view = view;
		this.control = control;
	}
	
	
}
