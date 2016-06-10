package com.dwarfeng.scheduler.typedef.abstruct;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractSerialParamableTreeNode extends AbstractObjectInProjectTree implements SerialParamable{

	private static final long serialVersionUID = -4800774978005509299L;
	
	private String name;
	private String describe;
	private List<Integer> tagIdList;
	
	/**
	 * 
	 */
	public AbstractSerialParamableTreeNode() {
		this(true,null,null,null);
	}
	/**
	 * 
	 * @param name
	 * @param describe
	 * @param tagIdList
	 */
	public AbstractSerialParamableTreeNode(boolean allowsChildren,String name,String describe,List<Integer> tagIdList){
		//调用父类方法
		super(allowsChildren);
		//设置自身参数
		this.name = name == null ? "" : name;
		this.describe = describe == null ? "" : describe;
		this.tagIdList = tagIdList == null ? new ArrayList<Integer>() : tagIdList;
	}
	@Override
	public void setParam(int index, Object object)throws IllegalArgumentException {
		switch (index) {
			case NAME:
				setName(object);
				break;
			case DESCRIBE:
				setDescribe(object);
				break;
			case TAGS:
				setTags(object);
				break;
		default:
			throw new IllegalArgumentException("Index illegal");
		}
	}
	@Override
	public Object[] getParams() {
		return new Object[]{name,describe,tagIdList};
	}
	
	private void setName(Object object) {
		if(!(object instanceof String)){
			throw new IllegalArgumentException("Object must instance of String");
		}else {
			this.name = (String) object;
		}
	}
	private void setDescribe(Object object) {
		if(!(object instanceof String)){
			throw new IllegalArgumentException("Object must instance of String");
		}else {
			this.describe = (String) object;
		}		
	}
	private void setTags(Object object) {
		//TODO
	}
}
