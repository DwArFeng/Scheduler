package com.dwarfeng.scheduler.tools;

import com.dwarfeng.scheduler.core.Scheduler133;
import com.dwarfeng.scheduler.typedef.funcint.SerialParam;

/**
 * ��Ҫ�û�����ķ��������͹��߰���
 * @author DwArFeng
 * @since 1.8
 */
public final class UserInput {

	/**
	 * ��ȡ�û���������в�����
	 * @param frameTitle �Ի���ı��⣬Ϊnull����ʾĬ��ֵ��
	 * @param serialParam ���յ����в�����Ϊnull�����Ĭ��ֵ��
	 * @return �û���������в������������ȡ�������˳���ť���򷵻�null��
	 */
	public static SerialParam getSerialParam(String frameTitle,SerialParam serialParam){
		SerialParamDialog dialog = new SerialParamDialog(Scheduler133.getInstance().getGui(),frameTitle,serialParam);
		dialog.setVisible(true);
		return dialog.getSerialParam();
	}
	
	private UserInput() {
		//��ֹ�ⲿʵ����
	}

}
