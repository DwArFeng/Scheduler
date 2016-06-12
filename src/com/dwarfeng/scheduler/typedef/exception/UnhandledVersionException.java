package com.dwarfeng.scheduler.typedef.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 不能处理的版本异常。
 * <p> 程序在读取自身不能处理的版本时会抛出这个异常。
 * 异常记录了不能处理的版本号，以及所有能处理的版本号，并通过一个内在的版本比较器进行版本新旧的对比。
 *<b> 该异常还可以根据不能处理的版本号，使用内部算法推测哪个版本的解析器有可能能够读取这个未知版本的内容。
 * @author DwArFeng
 * @since 1.8
 */
public class UnhandledVersionException extends Exception {

	/**
	 * 
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum VersionType{
		/**指示该版本太晚*/
		TOO_LATE,
		/**指示该版本太早*/
		TOO_EARLY,
		/**指示该版本处于中间版本且版本号未知，是比较少见的*/
		MID_UNKOWN
	}
	
	private String failedVersion;
	private String[] versions;
	
	/**
	 * 构造一个不能处理的版本异常对象。
	 * @param failedVersion 失败的版本信息。 
	 * @param versions 能够支持的所有版本信息。
	 */
	public UnhandledVersionException(String failedVersion,String[] versions) {
		this.failedVersion = failedVersion;
		this.versions = versions;
	}

	/**
	 * 获取不能处理的版本号。
	 * @return 不能处理的版本号。
	 */
	public String getFailedVersion(){
		return this.failedVersion;
	}
	
	/**
	 * 返回版本的类型。
	 * @return 版本的类型枚举。
	 */
	public VersionType getVersionType(){
		List<String> sl = new ArrayList<String>();
		for(String version:versions) sl.add(version);
		sl.add(failedVersion);
		String[] vs = sl.toArray(new String[0]);
		Arrays.sort(vs,new VersionComparator());
		if(failedVersion.equals(vs[0])){
			return VersionType.TOO_EARLY;
		}else if(failedVersion.equals(vs[vs.length-1])){
			return VersionType.TOO_LATE;
		}else{
			return VersionType.MID_UNKOWN;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage(){
		String str = super.getMessage();
		return str == null || str.equals("") ? "Unhandeled version :" +  failedVersion: str;
	}
}



/**
 * 版本比较器。
 * <p> 通过约定的版本规范比较两个版本的大小，如<code>版本0.2.0 > 版本0.1.0</code>。
 * @author DwArFeng
 * @since 1.8
 */
class VersionComparator implements Comparator<String>{

	private final static String SP = "//.";
	
	@Override
	public int compare(String s1, String s2) {
		if(s1 == null) throw new NullPointerException("S1 can't be null");
		if(s2 == null) throw new NullPointerException("S2 can't be null");
		
		Integer edition1,edition2;
		Integer version1,version2;
		Integer internal1,internal2;
		
		try{
			String[] t1 = s1.split(SP);
			String[] t2 = s2.split(SP);
			
			edition1 = new Integer(t1[0]); edition2 = new Integer(t2[0]);
			version1 = new Integer(t1[1]); version2 = new Integer(t2[1]);
			internal1 = new Integer(t1[2]); internal2 = new Integer(t2[2]);
			
			if(Integer.compare(edition1, edition2) != 0){
				return Integer.compare(edition1, edition2);
			}else if(Integer.compare(version1, version2) != 0){
				return Integer.compare(version1, version2);
			}else{
				return Integer.compare(internal1, internal2);
			}
			
		}catch(Exception e){
			throw new IllegalArgumentException("Bad version format");
		}
	}
	
}
