package com.dwarfeng.scheduler.typedef.desint;

import javax.swing.JComponent;
import javax.swing.JMenuBar;


/**
 * �༭�ӿڡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface Editor<T extends Editable> {
	
	/**
	 * ��ȡ���еĿɱ༭����,�뱣֤�÷������᷵��null��
	 * @return �ɱ༭����
	 */
	public T getEditable();
	
	/**
	 * ���ر༭���棬�뱣֤�÷������᷵��null��
	 * @return �༭���Ľ��档
	 */
	public JComponent getEditPanel();
	
	/**
	 * ���ر༭���Ĳ˵������÷�������null����༭����û�в˵���
	 * @return �༭���Ĳ˵�����
	 */
	public JMenuBar getMenuBar();
	
	/**
	 * ֹͣ�༭������<p>
	 * �÷����ڱ༭���������ر�ʱ�����ã�����ֹͣ�༭�����棨�����Ҫ��ʱ���ܻ�����쳣����ʱ�п�����Ҫ��ֹֹͣ�༭�Ĺ��̡�
	 * <br> �׳��쳣Ҳ��ζ����ֹֹͣ���̡�
	 * <br> �÷����������׳��κ��쳣����������ʱ�쳣��
	 * @return �Ƿ���ֹֹͣ���̣�<code>false</code>��ʾ��ֹֹͣ���̡�
	 */
	public boolean stopEdit();
	
	/**
	 * ǿ��ֹͣ�༭��<p>
	 * �÷����ڱ༭��ǿ��ֹͣ�����Ӧ�Ŀɱ༭����ɾ��ʱ����༭����ǿ��ֹͣ��ʱ�����á�
	 * <br>�÷�����Ӧ���׳��κ��쳣�����Ҳ���������������н��б���ȶ���������÷�����ò�ʵ���κζ�����
	 */
	public void forceStopEdit();
	
}
