package com.dwarfeng.scheduler.typedef.funcint;

import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;

/**
 * 系列参数接口。
 * <p>该接口被设计，用于替代老的{@code NTDTree}}（不用在乎这是什么，总之它已经消失了）。
 * <br> 实现该接口意味着能够对其返回或者设置{@linkplain SerialParam}对象。
 * @author DwArFeng
 * @since 1.8
 */
public interface SerialParamable extends ObjectInProjectTree{
	
	/**
	 * 获取该实例的序列参数。
	 * <p> 请一定保证该方法不会返回<code>null</code>。
	 * @return 获取的序列参数。
	 */
	public SerialParam getSerialParam();
	
}
