package com.dwarfeng.scheduler.typedef.abstruct;

import com.dwarfeng.scheduler.project.Project;

/**
 * �ڹ����ļ��ӿڡ�
 * <p> �ýӿ�ָʾ��һ���������ڹ��̵�һ�����࣬ʵ�ָýӿڼ���ζ�Ÿö����ܹ���ֱ����ӽ�Project�С�
 * @author DwArFeng
 * @since 1.8
 */
public interface ObjectInProject{
	
	/**
	 * ���ظö����Project����
	 * @return ����������Project��
	 */
	public Project getRootProject();
	
}
