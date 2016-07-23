package com.dwarfeng.scheduler.typedef.cabstruct;

/**
 * 控制器的控制站点，被视图管理器持有。
 * @author DwArFeng
 * @since 1.8
 */
public interface ControlPort {

	/**
	 * 使程序显示指定的信息包中对应的输出信息。
	 * @param pack 指定的信息包。
	 */
	public void showMessage(MessagePack pack);
}
