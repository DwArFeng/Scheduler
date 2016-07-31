package com.dwarfeng.scheduler.module.project.abstruct;

import com.dwarfeng.scheduler.module.PScpathable;
import com.dwarfeng.scheduler.typedef.exception.AttachmentException;

/**
 * �����ӿڡ�
 * <p>�˽ӿ�ʵ�ָýӿڵĶ����ǹ����ļ���һ���֣�������ռ�й��̽ṹ��ͬʱ�����빤���е�ĳ���ļ������ӡ�
 * <br> �ýӿ��Ǹ����ͽӿڣ����еķ��ʹ����Ÿ���ָʾ���ļ���������ʲô�౻��ȡ�Լ���ʲô�౻���档
 * @author DwArFeng
 * @since 1.8
 */
interface Attachment<T> extends PScpathable{
	
	/**
	 * �Ӹ����ж�ȡ�ļ��������Է���ָʾ���෵�ء�
	 * @return ȥ�����ļ��Ķ�����ʽ��
	 * @throws AttachmentException ������ȡʧ���쳣��
	 */
	public T load() throws AttachmentException;
	
	/**
	 * ��ָ���Ķ��󱣴��ڸ����У�һ����˵����Ḳ��ԭ�еĸ�����
	 * @param obj ָ���Ķ���
	 * @throws AttachmentException ��������ʧ���쳣�� 
	 */
	public void save(T obj) throws AttachmentException;
	
	/**
	 * ����Ĭ�ϵĸ�������
	 * @return Ĭ�ϵĸ�������
	 */
	public T createDefaultObject();
}
