package com.dwarfeng.scheduler.project.funcint;

import java.util.Map;

import com.dwarfeng.scheduler.project.funcint.SerialParam.Serial;

/**
 * ��ʶ��һ�������������ܹ��������Ľӿڡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface Searchable extends SerialParamable{
	
	/**
	 * ���ظö����Ƿ�ƥ��ָ�����е�ָ���ı���
	 * @param serial ָ�������б�־��
	 * @param value ָ�����ı�ֵ��
	 * @return �Ƿ�ƥ�䡣
	 */
	public default boolean isMatch(Serial serial,String value){
		String str = getSerialParam().getValue(serial);
		return str.contains(value);
	}
	
	/**
	 * ���ظö����Ƿ�ƥ���������С����ı�ӳ���е��������к��������Ӧ���ı���
	 * @param map ���С����ı�ӳ�䡣
	 * @return �Ƿ�ȫ��ƥ�䡣
	 */
	public default boolean isMatch(Map<Serial, String> map){
		for(Serial serial : map.keySet()){
			if(! isMatch(serial,map.get(serial))) return false;
		}
		return true;
	}
}
