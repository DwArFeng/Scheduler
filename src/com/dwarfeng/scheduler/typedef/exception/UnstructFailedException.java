package com.dwarfeng.scheduler.typedef.exception;

import java.util.HashSet;
import java.util.Set;

/**
 * 工程解构失败异常。
 * <p> 从外部文档中的构造信息中读取工程文件时发生的异常。
 * <br> 这个异常不算做{@linkplain ProjectException} 的子类，因为该异常发生时，工程还没有被构造出来。
 * <br> 此异常相比{@linkplain ProjectPathNotSuccessException} 来说，是个严重的多的异常，前者只能造成数量。
 * <br> 该异常不存在孪生异常<code>StructFailedException</code>，因为构架一个工程几乎不可能出现异常。
 * 有限的数据丢失，而此异常代表着文档可以出现了严重的结构性的损坏。
 * @author DwArFeng
 * @since 1.8
 */
public final class UnstructFailedException extends Exception {
	
	private static final long serialVersionUID = -8564937676851082667L;

	/**
	 * 结构损坏类型的枚举，每一个字段代表着不同部位的结构损坏。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum FailedType{
		/**代表着标签损坏的的字段*/
		TAGS("tags.xml"),
		/**代表着所有笔记损坏的字段*/
		NOTEBOOKS("notebooks.xml"),
		/**代表着即兴计划损坏的字段*/
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
	 * 生成一个默认的解构失败异常。
	 */
	public UnstructFailedException() {
		this(null,null);
	}
	
	/**
	 * 生成一个具有指定失败标签的解构失败异常。
	 * @param failedSet 指定的失败标签。
	 */
	public UnstructFailedException(Set<FailedType> failedSet){
		this(failedSet,null);
	}

	/**
	 * 生成一个具有指定失败标签，指定信息的解构失败异常。
	 * @param failedSet 指定的失败标签。
	 * @param message 指定的信息。
	 */
	public UnstructFailedException(Set<FailedType> failedSet,String message) {
		super(message);
		this.failedSet = failedSet == null ?
				new HashSet<FailedType>():
				new HashSet<FailedType>(failedSet);
	}

	/**
	 * 返回解构失败的标签。
	 * @return 标签集合。
	 */
	public Set<FailedType> getFailedSet() {
		return new HashSet<FailedType>(this.failedSet);
	}

}
