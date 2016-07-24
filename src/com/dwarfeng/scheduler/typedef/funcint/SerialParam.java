package com.dwarfeng.scheduler.typedef.funcint;

import java.util.ArrayList;
import java.util.List;

import com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectOutProjectTree;

/**
 * ���в�����
 * <p> ���в�����ָһ������Ŀ������л���ʾ�Ĳ����������е����ơ���������ǩ�ȶ������в�����һԱ��
 * ���в����е����в���������ʹ��{@linkplain #getValue(Serial)} �����أ��������᷽���������ܡ����⣬���в����е���������
 * ���������Լ��ķ��ط�ʽ��
 * @author DwArFeng
 * @since 1.8
 */
public final class SerialParam extends AbstractObjectOutProjectTree{

	/**
	 * ���в��������б�ǡ�
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum Serial{
		/**��������*/
		NAME,
		/**��������*/
		DESCRIBE,
		/**��ǩID�б�*/
		TAGIDS;
	}
	
	/**�����ֶ�*/
	private final String name;
	/**�����ֶ�*/
	private final String describe;
	/**��ǩID�б�*/
	private final List<Integer> tagIds;
	
	/**
	 * ���в����Ĺ�������
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		/**�����ֶ�*/
		private String name = "δ����";
		/**�����ֶ�*/
		private String describe = "";
		/**��ǩID����*/
		private List<Integer> tagIds = new ArrayList<Integer>();
		
		/**
		 * ����һ����������
		 */
		public Productor(){}
		
		/**
		 * �趨����ֵ��
		 * <p> ��ڲ���Ϊ<code>null</code>����Ϊ<code>""</code>���Ὣ������Ϊ��ʼֵ��
		 * @param val ���Ƶ�ֵ��
		 * @return ����������
		 */
		public Productor name(String val){
			this.name = val;
			if(val == null || val.equals("")) name = "δ����";
			name = val;
			return this;
		}
		
		/**
		 * �趨����ֵ��
		 * <p> ��ڲ���Ϊ<code>null</code>���Ὣ������Ϊ<code>""</code>��
		 * @param val ������ֵ��
		 * @return ����������
		 */
		public Productor describe(String val){
			if(val == null) describe = "";
			this.describe = val;
			return this;
		}
		
		/**
		 * �趨��ǩID�б�
		 * <p> ��ڲ���Ϊ<code>null</code>���Ὣ��ǩID�б��趨Ϊ�յ��б�
		 * @param val ��ǩ�б��ֵ��
		 * @return ����������
		 */
		public Productor tagIds(List<Integer> val){
			if(val == null) tagIds = new ArrayList<Integer>();
			this.tagIds = val;
			return this;
		}
		
		/**
		 * �������в���ʵ����
		 * @return �ɹ���������������в���ʵ����
		 */
		public SerialParam product(){
			return new SerialParam(name, describe,tagIds);
		}
	}
	
	/**
	 * �ڲ���ʼ��������
	 * @param name �����ֶε�ֵ��
	 * @param describe �����ֶε�ֵ��
	 * @param tagIds ������ǩID�б��ֶε�ֵ��
	 */
	private SerialParam(String name,String describe,List<Integer> tagIds){
		this.name = name;
		this.describe = describe;
		this.tagIds = tagIds;
	}
	
	/**
	 * ��ȡ���в��������ơ�
	 * @return ���в��������ơ�
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * ��ȡ���в�����������
	 * @return ���в�����������
	 */
	public String getDescribe(){
		return this.describe;
	}
	
	/**
	 * ��ȡ���в����ı�ǩID�б�
	 * @return ���в����ı�ǩID�б�
	 */
	public List<Integer> getTagIds(){
		return this.tagIds;
	}
	
	/**
	 * ��ȡ���в���ָ�����б�־�����ı�ֵ��
	 * @param serial ���б�־��
	 * @return ���ص��ı�ֵ��
	 */
	public String getValue(Serial serial){
		if(serial == null) throw new NullPointerException("Serial can't be null");
		switch (serial) {
			case NAME:
				return this.name;
			case DESCRIBE:
				return this.describe;
			case TAGIDS:
				StringBuilder sb = new StringBuilder();
				for(int i : tagIds){
					sb.append("-" + i + "-");
				}
				return sb.toString();
		}
		return null;
	}

}
