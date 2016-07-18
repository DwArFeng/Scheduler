package com.dwarfeng.scheduler.typedef.exception;

import java.util.ArrayList;
import java.util.List;

import com.dwarfeng.scheduler.io.Scpath;
import com.dwarfeng.scheduler.project.Project;

/**
 * ����·�����ɹ��쳣��
 * <p> ���쳣ָʾ�Ź���·�����������ı���ȡ���߱��洢��
 * @author DwArFeng
 * @since 1.8
 */
public class ProjectPathNotSuccessException extends Exception{
	
	/**
	 * ָʾ�Ź����ڽ��к��ֵ���ʱ�����˲��ɹ�������
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum FailedType{
		/**�ڶ�ȡ��ʱ�����ʧ�ܵı��*/
		LOAD,
		/**�ڴ洢��ʱ�����ʧ�ܵı��*/
		SAVE;
	}
	
	private final FailedType failedType;
	private final Project project;
	private final List<Scpath> successList;
	private final List<Scpath> failedList;

	/**
	 * ����һ������·�����ɹ��쳣��
	 * @param project ʧ�ܵĹ��̡�
	 * @param successList �����еĳɹ�·���б���
	 * @param failedList ������ʧ�ܵ�·���б���
	 */
	public ProjectPathNotSuccessException(Project project,List<Scpath> successList,List<Scpath> failedList,FailedType failedType) {
		this.project = project;
		this.successList = successList;
		this.failedList = failedList;
		this.failedType = failedType;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage(){
		String str = super.getMessage();
		if(str == null || str.equals("")){
			str = "Some path " + (failedType == FailedType.LOAD ? "loaded" : "saved") + " unsuccessfully"
					+ " : " + failedList.size() + "fails";
		}
		return str;
	}

	/**
	 * ����ʧ�ܵĹ��̡�
	 * @return ʧ�ܵĹ��̡�
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * ���سɹ��Ķ�ȡ�б���
	 * @return �ɹ��Ķ�ȡ�б���
	 */
	public List<Scpath> getSuccessList() {
		return new ArrayList<Scpath>(successList);
	}

	/**
	 * ����ʧ���б���
	 * @return ʧ���б���
	 */
	public List<Scpath> getFailedList() {
		return new ArrayList<Scpath>(failedList);
	}

	/**
	 * ����ʧ�����͡�
	 * @return ʧ�����͡�
	 */
	public FailedType getFailedType() {
		return failedType;
	}
}