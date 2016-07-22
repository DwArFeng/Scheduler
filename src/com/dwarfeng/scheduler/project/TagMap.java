package com.dwarfeng.scheduler.project;

import com.dwarfeng.dwarffunction.cna.IDMap;
import com.dwarfeng.dwarffunction.cna.IDMap.CodingType;
import com.dwarfeng.scheduler.typedef.pabstruct.AbstractObjectOutProjectTree;
import com.dwarfeng.scheduler.typedef.pabstruct.ObjectInProject;

/**
 * ID-��ǩӳ���ࡣ
 * @author DwArFeng��
 * @since 1.8��
 */
public class TagMap extends AbstractObjectOutProjectTree implements ObjectInProject{
	
	/**��¼ID-Tagӳ���IDӳ��*/
	private IDMap<Tag> map;
	
	/**
	 * ����һ��û���κ�����ID-Tagӳ���ID-��ǩӳ���ࡣ
	 */
	public TagMap(){
		this(null);
	}
	/**
	 * ����һ������ָ����ϵ��ID-��ǩӳ���ࡣ
	 * @param map ָ����ID-��ǩӳ�����
	 */
	public TagMap(IDMap<Tag> map){
		this.map = map == null ? new IDMap<Tag>(CodingType.COMPACT) : map;
	}
	
	
	/**
	 * ��ȡָ��ID��Ӧ��tag
	 * @param id ָ����idֵ��
	 * @return ָ��idֵ��Ӧ�ı�ǩ��
	 */
	public Tag getTag(int id){
		return map.get(id);
	}
	
	/**
	 * ������������о��е�����ID��
	 * @return ����������ID��ɵ����顣
	 */
	public int[] getTagIds(){
		return map.getAllIDs();
	}

//	/**
//	 * ӳ���ǩ���ǩID���ࡣ
//	 * <p> ���������������̵�����Tag������ÿ��Tag��������Ψһ�ı�š�
//	 * <br> �����ṩ�����
//	 * @author DwArFeng
//	 * @since 1.8
//	 */
//	private class TagMap{
//		
//		
//		
//		
//		/**
//		 * 
//		 */
//		public TagMap() {
//			this(null);
//		}
//		/**
//		 * 
//		 * @param contextProject
//		 * @param tagMap
//		 */
//		public TagMap(IDMap< Tag> tagMap){
//			this.tagMap = tagMap == null ? new IDMap<Tag>() : tagMap;
//		}
//		

//		/**
//		 * 
//		 * @param tag
//		 * @return
//		 */
//		public int serachTag(Tag tag){
//			return tagMap.serach(tag);
//		}
//		/**
//		 * 
//		 * @return
//		 */
//		public Tag[] getTags(){
//			return tagMap.getValues().toArray(new Tag[0]);
//		}
//		/**
//		 * 
//		 * @param tag
//		 */
//		public void addTag(Tag tag){
//			this.tagMap.regist(tag);
//		}
//		/**
//		 * 
//		 * @param t
//		 */
//		public void removeTag(Tag t){
//			//TODO �Ƴ���ǩ���ܴ�����
////			try{
////				Iterator<Tagable> iterator = getContextProject().getTagables().iterator();
////				while(iterator.hasNext()){
////					iterator.next().removeTag(t);
////				}
////			}catch(Exception e){
////				throw new RuntimeException("Context not assigned");
////			}finally{
////				this.tagMap.remove(t);
////			}
//		}
//		/**
//		 * 
//		 * @param id
//		 */
//		public void removeTag(int id){
//	//TODO �Ƴ���ǩ���ܴ�����
////			try{
////				Iterator<Tagable> iterator = getContextProject().getTagables().iterator();
////				while(iterator.hasNext()){
////					iterator.next().removeTag(id);
////				}
////			}catch(Exception e){
////				throw new RuntimeException("Context not assigned");
////			}finally{
////				this.tagMap.remove(id);
////			}
//		}
//		
//	}
}