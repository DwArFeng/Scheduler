package com.dwarfeng.scheduler.tools;

import com.dwarfeng.scheduler.core.Scheduler133;
import com.dwarfeng.scheduler.typedef.funcint.SerialParam;

/**
 * 需要用户输入的方法整合型工具包。
 * @author DwArFeng
 * @since 1.8
 */
public final class UserInput {

	/**
	 * 获取用户输入的序列参数。
	 * @param frameTitle 对话框的标题，为null则显示默认值。
	 * @param serialParam 参照的序列参数，为null则参照默认值。
	 * @return 用户输入的序列参数，如果按下取消或者退出按钮，则返回null。
	 */
	public static SerialParam getSerialParam(String frameTitle,SerialParam serialParam){
		SerialParamDialog dialog = new SerialParamDialog(Scheduler133.getInstance().getGui(),frameTitle,serialParam);
		dialog.setVisible(true);
		return dialog.getSerialParam();
	}
	
	private UserInput() {
		//禁止外部实例化
	}

}
