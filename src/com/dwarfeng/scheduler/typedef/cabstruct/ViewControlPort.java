package com.dwarfeng.scheduler.typedef.cabstruct;

/**
 * 视图控制站。
 * <p> 该控制站点被{@linkplain ControlMgr}引用，必要时直接调用其中的方法，做到对界面的控制。
 * @author DwArFeng
 * @since 1.8
 */
public interface ViewControlPort {

	/**
	 * 在界面上显示指定信息包中的输出文本。
	 * @param pack 指定的信息包。
	 */
	public void showMessage(MessagePack pack);
	
}
