package com.dwarfeng.scheduler;

import com.dwarfeng.scheduler.core.Scheduler;

/**
 * �������������
 * <p> ���Զ�����Ϊ����ģʽ����������һ����Ĭ�ϵģ�û�����������ģ��ڶ����Ǵ������������ġ�
 * <br> ͬʱ�����໹�ṩ<code>main</code>������ָ���޲�������������
 * @author DwArFeng
 * @since 1.8
 */
public class Luncher {

	public static void main(String[] args){
		lunch();
	}
	
	/**
	 * �����в�������������
	 */
	public static void lunch(){
		new Scheduler.Luncher().lunch();
	}
}
