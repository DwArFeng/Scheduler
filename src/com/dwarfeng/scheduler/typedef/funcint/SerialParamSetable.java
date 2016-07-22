package com.dwarfeng.scheduler.typedef.funcint;

import com.dwarfeng.scheduler.typedef.pabstruct.ObjectInProject;

/**
 * 序列参数可设置接口。
 * <p> 实现该接口意味着可以在主界面的工程树种使用快捷键<code>"F2"</code>来快速更改序列参数的属性。
 * @author DwArFeng
 * @since 1.8
 */
public interface SerialParamSetable extends SerialParamable{

	/**
	 * 设置该实例的序列参数。
	 * <p> 约定：设置<code>serialParam</code>的时候一定要使用{@linkplain SerialParam#setContext(ObjectInProject)}方法设置其上下文。
	 * @param serialParam 指定的序列参数。
	 */
	public void setSerialParam(SerialParam serialParam);
}
