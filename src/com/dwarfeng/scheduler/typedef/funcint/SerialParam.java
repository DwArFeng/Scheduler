package com.dwarfeng.scheduler.typedef.funcint;

import java.util.ArrayList;
import java.util.List;

import com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectOutProjectTree;

/**
 * 序列参数。
 * <p> 序列参数是指一个对象的可以序列化表示的参数，对象中的名称、描述、标签等都是序列参数的一员。
 * 序列参数中的所有参数均可以使用{@linkplain #getValue(Serial)} 来返回，这样做会方便搜索功能。另外，序列参数中的其它所有
 * 参数都有自己的返回方式。
 * @author DwArFeng
 * @since 1.8
 */
public final class SerialParam extends AbstractObjectOutProjectTree{

	/**
	 * 序列参数的序列标记。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum Serial{
		/**名称序列*/
		NAME,
		/**描述序列*/
		DESCRIBE,
		/**标签ID列表*/
		TAGIDS;
	}
	
	/**名称字段*/
	private final String name;
	/**描述字段*/
	private final String describe;
	/**标签ID列表*/
	private final List<Integer> tagIds;
	
	/**
	 * 序列参数的构造器。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Productor{
		
		/**名称字段*/
		private String name = "未命名";
		/**描述字段*/
		private String describe = "";
		/**标签ID描述*/
		private List<Integer> tagIds = new ArrayList<Integer>();
		
		/**
		 * 创建一个构造器。
		 */
		public Productor(){}
		
		/**
		 * 设定名称值。
		 * <p> 入口参数为<code>null</code>或者为<code>""</code>都会将名称设为初始值。
		 * @param val 名称的值。
		 * @return 构造器自身。
		 */
		public Productor name(String val){
			this.name = val;
			if(val == null || val.equals("")) name = "未命名";
			name = val;
			return this;
		}
		
		/**
		 * 设定描述值。
		 * <p> 入口参数为<code>null</code>将会将描述设为<code>""</code>。
		 * @param val 描述的值。
		 * @return 构造器自身。
		 */
		public Productor describe(String val){
			if(val == null) describe = "";
			this.describe = val;
			return this;
		}
		
		/**
		 * 设定标签ID列表。
		 * <p> 入口参数为<code>null</code>将会将标签ID列表设定为空的列表。
		 * @param val 标签列表的值。
		 * @return 构造器自身。
		 */
		public Productor tagIds(List<Integer> val){
			if(val == null) tagIds = new ArrayList<Integer>();
			this.tagIds = val;
			return this;
		}
		
		/**
		 * 构造序列参数实例。
		 * @return 由构造器构造出的序列参数实例。
		 */
		public SerialParam product(){
			return new SerialParam(name, describe,tagIds);
		}
	}
	
	/**
	 * 内部初始化方法。
	 * @param name 名称字段的值。
	 * @param describe 描述字段的值。
	 * @param tagIds 描述标签ID列表字段的值。
	 */
	private SerialParam(String name,String describe,List<Integer> tagIds){
		this.name = name;
		this.describe = describe;
		this.tagIds = tagIds;
	}
	
	/**
	 * 获取序列参数的名称。
	 * @return 序列参数的名称。
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * 获取序列参数的描述。
	 * @return 序列参数的描述。
	 */
	public String getDescribe(){
		return this.describe;
	}
	
	/**
	 * 获取序列参数的标签ID列表。
	 * @return 序列参数的标签ID列表。
	 */
	public List<Integer> getTagIds(){
		return this.tagIds;
	}
	
	/**
	 * 获取序列参数指定序列标志出的文本值。
	 * @param serial 序列标志。
	 * @return 返回的文本值。
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
