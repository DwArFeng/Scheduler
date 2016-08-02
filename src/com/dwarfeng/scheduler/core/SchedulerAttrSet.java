package com.dwarfeng.scheduler.core;

import java.awt.Image;

import com.dwarfeng.dwarffunction.program.Version;
import com.dwarfeng.dwarffunction.program.mvc.ProgramAttrSet;
import com.dwarfeng.dwarffunction.program.mvc.ProgramConstField;
import com.dwarfeng.scheduler.info.ImageKeys;
import com.dwarfeng.scheduler.info.StringFieldKey;

/**
 * ����ĳ����ֶΡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface SchedulerAttrSet extends ProgramAttrSet {
	
	/**
	 * ���س���İ汾��
	 * <p> ��ע�ⲻҪ����<code>null</code>��
	 * @return ����İ汾��
	 */
	public Version getVersion();
	
	/**
	 * �����������ơ�
	 * <p> ��ע�ⲻҪ����<code>null</code>��
	 * @return ���ߵ����ơ�
	 */
	public String getAuthor();
	
	/**
	 * �����ı�������Ӧ���ı���
	 * @param key �ı���������
	 * @return ������Ӧ���ı���
	 */
	public String getText(StringFieldKey key);
	
	/**
	 * ����ָ��ͼƬ�ֶ�������Ӧ��ͼƬ��
	 * <p> ���ͼƬ�����ڣ��򷵻�Ĭ�ϵ�ͼƬ��
	 * @param key ͼƬ�ֶ�������
	 * @return ������Ӧ��ͼƬ��
	 */
	public Image getImage(ImageKeys key);

}
