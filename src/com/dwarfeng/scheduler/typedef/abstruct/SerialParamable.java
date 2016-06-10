package com.dwarfeng.scheduler.typedef.abstruct;

/**
 * 连续参数接口。
 * <p>该接口被设计，用于替代老的{@code NTDTree}}（不用在乎这是什么，总之它已经消失了）。
 * <br>在工程树中，总有一些节点会拥有自己的一连串的参数，比如名字、描述、标签等等。对于实现该接口的对象来说，
 * 同一种类型的参数应该有相同的序号。这些ID已经在该接口中被指定了。
 * @author DwArFeng
 * @since 1.8
 */
public interface SerialParamable {
	
	/**名称属性所占的序号*/
	public final static int NAME = 0;
	/**描述属性所占的序号*/
	public final static int DESCRIBE = 1;
	/**标签属性所占的序号*/
	public final static int TAGS = 2;
	
	/**
	 * 返回连续参数接口中有多少个连续参数，当前为止是三个。
	 * @return 连续参数接口中的参数个数。
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
