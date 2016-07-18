package com.dwarfeng.scheduler.typedef.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * ���ܴ���İ汾�쳣��
 * <p> �����ڶ�ȡ�����ܴ���İ汾ʱ���׳�����쳣��
 * �쳣��¼�˲��ܴ���İ汾�ţ��Լ������ܴ���İ汾�ţ���ͨ��һ�����ڵİ汾�Ƚ������а汾�¾ɵĶԱȡ�
 *<b> ���쳣�����Ը��ݲ��ܴ���İ汾�ţ�ʹ���ڲ��㷨�Ʋ��ĸ��汾�Ľ������п����ܹ���ȡ���δ֪�汾�����ݡ�
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
		/**ָʾ�ð汾̫��*/
		TOO_LATE,
		/**ָʾ�ð汾̫��*/
		TOO_EARLY,
		/**ָʾ�ð汾�����м�汾�Ұ汾��δ֪���ǱȽ��ټ���*/
		MID_UNKOWN
	}
	
	private String failedVersion;
	private String[] versions;
	
	/**
	 * ����һ�����ܴ���İ汾�쳣����
	 * @param failedVersion ʧ�ܵİ汾��Ϣ�� 
	 * @param versions �ܹ�֧�ֵ����а汾��Ϣ��
	 */
	public UnhandledVersionException(String failedVersion,String[] versions) {
		this.failedVersion = failedVersion;
		this.versions = versions;
	}

	/**
	 * ��ȡ���ܴ���İ汾�š�
	 * @return ���ܴ���İ汾�š�
	 */
	public String getFailedVersion(){
		return this.failedVersion;
	}
	
	/**
	 * ���ذ汾�����͡�
	 * @return �汾������ö�١�
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
 * �汾�Ƚ�����
 * <p> ͨ��Լ���İ汾�淶�Ƚ������汾�Ĵ�С����<code>�汾0.2.0 > �汾0.1.0</code>��
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
