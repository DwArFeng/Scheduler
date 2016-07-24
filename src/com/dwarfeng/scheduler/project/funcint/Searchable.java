package com.dwarfeng.scheduler.project.funcint;

import java.util.Map;

import com.dwarfeng.scheduler.project.funcint.SerialParam.Serial;

/**
 * 标识着一个工程树对象能够被搜索的接口。
 * @author DwArFeng
 * @since 1.8
 */
public interface Searchable extends SerialParamable{
	
	/**
	 * 返回该对象是否匹配指定序列的指定文本。
	 * @param serial 指定的序列标志。
	 * @param value 指定的文本值。
	 * @return 是否匹配。
	 */
	public default boolean isMatch(Serial serial,String value){
		String str = getSerialParam().getValue(serial);
		return str.contains(value);
	}
	
	/**
	 * 返回该对象是否匹配整个序列——文本映射中的所有序列和与其相对应的文本。
	 * @param map 序列——文本映射。
	 * @return 是否全部匹配。
	 */
	public default boolean isMatch(Map<Serial, String> map){
		for(Serial serial : map.keySet()){
			if(! isMatch(serial,map.get(serial))) return false;
		}
		return true;
	}
}
