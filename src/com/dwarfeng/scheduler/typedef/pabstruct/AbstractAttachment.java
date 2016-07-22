package com.dwarfeng.scheduler.typedef.pabstruct;

import java.io.InputStream;
import java.io.OutputStream;

import com.dwarfeng.dwarffunction.io.CT;
import com.dwarfeng.scheduler.io.ProjectIoHelper;
import com.dwarfeng.scheduler.io.Scpath;
import com.dwarfeng.scheduler.typedef.exception.AttachmentException;

/**
 * ���󸽼���
 * <p> ��󻯵�ʵ���˸����ķ�����
 * XXX ���Գ�����󻯵ķ�װload��save������
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractAttachment<T> extends AbstractObjectOutProjectTree implements Attachment<T>{

	/**·��*/
	protected final Scpath scpath;
	
	/**
	 * ����һ��Ĭ�ϵĳ��󸽼���
	 * @param scpath ���󸽼��Ĺ���·��������Ϊnull��
	 * @throws NullPointerException ������·��Ϊnullʱ��
	 */
	public AbstractAttachment(Scpath scpath) {
		if(scpath == null) throw new NullPointerException("Scpath can't be null");
		this.scpath = scpath;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.Scpathable#getScpath()
	 */
	@Override
	public Scpath getScpath() {
		return scpath;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.Attachment#load()
	 */
	@Override
	public T load() throws AttachmentException{
		
		InputStream in = null;
		T obj = null;
		
		try{
			//�����̨��ӡ��Ϣ
			CT.trace("���ڶ�ȡ�ļ� ��" + getScpath().getPathName() + "\t�߳�������" + Thread.currentThread());
			
			//����������
			in = ProjectIoHelper.getInputStream(getRootProject(), getScpath());
			
			//��ȡ�ļ�
			obj = loadAttachment(in);
			
			//����ɹ���Ϣ
			CT.trace("�ļ���ȡ�ɹ���" + scpath.getPathName() + "\t�߳�������" + Thread.currentThread());
			
			//���ض�ȡ�Ķ���
			return obj;
			
		}catch(Exception e){
			
			throw new AttachmentException(obj,getScpath(),"Attachement load failed", e);
			
		}finally{
			//�ر�������
			if(in != null){
				try{
					in.close();
				}catch(Exception e){
					e.printStackTrace();
					CT.trace("����ĳЩԭ��������û�����ر�");
				}
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.Attachment#save()
	 */
	@Override
	public void save(T obj) throws AttachmentException{
		
		if(obj == null) throw new NullPointerException("Obj can't be null");
		
		OutputStream out = null;
		
		try{
			
			//�����̨��ӡ��Ϣ
			CT.trace("���ڴ����ļ���" + scpath.getPathName() + "\t�߳�������" + Thread.currentThread());
			
			//���������
			out = ProjectIoHelper.getOutputStream(getRootProject(), getScpath(), true);
			
			//����������������
			saveAttachment(out,obj);
			
			//����ɹ���Ϣ
			CT.trace("�ļ�����ɹ���" + scpath.getPathName() + "\t�߳�������" + Thread.currentThread());
			
		}catch(Exception e){
			
			throw new AttachmentException(obj,getScpath(),"Attachement save failed", e);
			
		}finally{
			if(out != null) {
				try{
					out.close();
				}catch(Exception e){
					e.printStackTrace();
					CT.trace("����ĳЩԭ�������û�������ر�");
				}
			}
		}
	}
	
	/**
	 * ͨ�����е���������ȡ������ָ���Ķ���ķ�����
	 * @param in ָ������������
	 * @return ��ȡ�����ɵĶ���
	 * @throws Exception ��ȡʱ�������쳣��
	 */
	protected abstract T loadAttachment(InputStream in) throws Exception;
	
	/**
	 * ͨ�����е�������洢ָ������ķ�����
	 * <p> �÷������Ա�֤��ڲ���<code>T obj</code>��Ϊnull�������д�˷���ʱ���Բ�����
	 * ��<code>obj</code>���ж������
	 * @param out ָ�����������
	 * @param obj ָ���Ĵ洢����
	 * @throws Exception ����ʱ�����쳣��
	 */
	protected abstract void saveAttachment(OutputStream out,T obj) throws Exception;
}
