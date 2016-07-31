package com.dwarfeng.scheduler.project.funcint;

import com.dwarfeng.scheduler.module.project.abstruct.ObjectInProject;

/**
 * ���в��������ýӿڡ�
 * <p> ʵ�ָýӿ���ζ�ſ�����������Ĺ�������ʹ�ÿ�ݼ�<code>"F2"</code>�����ٸ������в��������ԡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface SerialParamSetable extends SerialParamable{

	/**
	 * ���ø�ʵ�������в�����
	 * <p> Լ��������<code>serialParam</code>��ʱ��һ��Ҫʹ��{@linkplain SerialParam#setContext(ObjectInProject)}���������������ġ�
	 * @param serialParam ָ�������в�����
	 */
	public void setSerialParam(SerialParam serialParam);
}
