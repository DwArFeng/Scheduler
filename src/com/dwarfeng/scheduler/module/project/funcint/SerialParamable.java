package com.dwarfeng.scheduler.module.project.funcint;

import com.dwarfeng.scheduler.module.project.abstruct.ProjectTreeNode;

/**
 * ϵ�в����ӿڡ�
 * <p>�ýӿڱ���ƣ���������ϵ�{@code NTDTree}}�������ں�����ʲô����֮���Ѿ���ʧ�ˣ���
 * <br> ʵ�ָýӿ���ζ���ܹ����䷵�ػ�������{@linkplain SerialParam}����
 * @author DwArFeng
 * @since 1.8
 */
public interface SerialParamable extends ProjectTreeNode{
	
	/**
	 * ��ȡ��ʵ�������в�����
	 * <p> ��һ����֤�÷������᷵��<code>null</code>��
	 * @return ��ȡ�����в�����
	 */
	public SerialParam getSerialParam();
	
}
