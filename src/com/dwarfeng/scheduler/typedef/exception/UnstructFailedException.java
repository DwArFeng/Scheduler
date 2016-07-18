package com.dwarfeng.scheduler.typedef.exception;

import java.util.HashSet;
import java.util.Set;

/**
 * ���̽⹹ʧ���쳣��
 * <p> ���ⲿ�ĵ��еĹ�����Ϣ�ж�ȡ�����ļ�ʱ�������쳣��
 * <br> ����쳣������{@linkplain ProjectException} �����࣬��Ϊ���쳣����ʱ�����̻�û�б����������
 * <br> ���쳣���{@linkplain ProjectPathNotSuccessException} ��˵���Ǹ����صĶ���쳣��ǰ��ֻ�����������
 * <br> ���쳣�����������쳣<code>StructFailedException</code>����Ϊ����һ�����̼��������ܳ����쳣��
 * ���޵����ݶ�ʧ�������쳣�������ĵ����Գ��������صĽṹ�Ե��𻵡�
 * @author DwArFeng
 * @since 1.8
 */
public final class UnstructFailedException extends Exception {
	
	private static final long serialVersionUID = -8564937676851082667L;

	/**
	 * �ṹ�����͵�ö�٣�ÿһ���ֶδ����Ų�ͬ��λ�Ľṹ�𻵡�
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum FailedType{
		/**�����ű�ǩ�𻵵ĵ��ֶ�*/
		TAGS("tags.xml"),
		/**���������бʼ��𻵵��ֶ�*/
		NOTEBOOKS("notebooks.xml"),
		/**�����ż��˼ƻ��𻵵��ֶ�*/
		IMPLANTS("implants.xml");
		
		private final String label;
		
		private FailedType(String label){
			this.label = label;
		}
		
		@Override
		public String toString(){
			return this.label;
		}
	}

	private final Set<FailedType> failedSet;
	
	/**
	 * ����һ��Ĭ�ϵĽ⹹ʧ���쳣��
	 */
	public UnstructFailedException() {
		this(null,null);
	}
	
	/**
	 * ����һ������ָ��ʧ�ܱ�ǩ�Ľ⹹ʧ���쳣��
	 * @param failedSet ָ����ʧ�ܱ�ǩ��
	 */
	public UnstructFailedException(Set<FailedType> failedSet){
		this(failedSet,null);
	}

	/**
	 * ����һ������ָ��ʧ�ܱ�ǩ��ָ����Ϣ�Ľ⹹ʧ���쳣��
	 * @param failedSet ָ����ʧ�ܱ�ǩ��
	 * @param message ָ������Ϣ��
	 */
	public UnstructFailedException(Set<FailedType> failedSet,String message) {
		super(message);
		this.failedSet = failedSet == null ?
				new HashSet<FailedType>():
				new HashSet<FailedType>(failedSet);
	}

	/**
	 * ���ؽ⹹ʧ�ܵı�ǩ��
	 * @return ��ǩ���ϡ�
	 */
	public Set<FailedType> getFailedSet() {
		return new HashSet<FailedType>(this.failedSet);
	}

}
