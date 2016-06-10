package com.dwarfeng.scheduler.typedef.abstruct;

/**
 * ���������ӿڡ�
 * <p>�ýӿڱ���ƣ���������ϵ�{@code NTDTree}}�������ں�����ʲô����֮���Ѿ���ʧ�ˣ���
 * <br>�ڹ������У�����һЩ�ڵ��ӵ���Լ���һ�����Ĳ������������֡���������ǩ�ȵȡ�����ʵ�ָýӿڵĶ�����˵��
 * ͬһ�����͵Ĳ���Ӧ������ͬ����š���ЩID�Ѿ��ڸýӿ��б�ָ���ˡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface SerialParamable {
	
	/**����������ռ�����*/
	public final static int NAME = 0;
	/**����������ռ�����*/
	public final static int DESCRIBE = 1;
	/**��ǩ������ռ�����*/
	public final static int TAGS = 2;
	
	/**
	 * �������������ӿ����ж��ٸ�������������ǰΪֹ��������
	 * @return ���������ӿ��еĲ���������
	 */
	public default int  getTotalParams(){
		return 3;
	}
	/**
	 * 
	 * @param index
	 * @return
	 */
	public default Object getParam(int index){
		return getParams()[index];
	}
	/**
	 * 
	 * @param index
	 * @throws IllegalArgumentException
	 */
	public void setParam(int index,Object object) throws IllegalArgumentException;
	/**
	 * 
	 * @return
	 */
	public Object[] getParams();
	/**
	 * 
	 * @param objects
	 */
	public default void setParams(Object[] objects){
		if(objects == null) throw new NullPointerException("Objects can't be null");
		if(objects.length < getTotalParams()) throw new IndexOutOfBoundsException("Object's length must larger than " + getTotalParams());
		for(int i = 0; i< getTotalParams() ; i ++){
			try{
				setParam(i,objects[i]);
			}catch(IllegalArgumentException e){
				e.printStackTrace();
			}
		}
	}
}
