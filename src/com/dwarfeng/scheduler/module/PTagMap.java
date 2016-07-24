package com.dwarfeng.scheduler.module;

import com.dwarfeng.dwarffunction.cna.IDMap;
import com.dwarfeng.dwarffunction.cna.IDMap.CodingType;

/**
 * ID-��ǩӳ���ࡣ
 * @author DwArFeng��
 * @since 1.8��
 */
class PTagMap extends PAbstractObjectOutProjectTree implements PObjectInProject{
	
	/**��¼ID-Tagӳ���IDӳ��*/
	private IDMap<PTag> map;
	
	/**
	 * ����һ��û���κ�����ID-Tagӳ���ID-��ǩӳ���ࡣ
	 */
	public PTagMap(){
		this(null);
	}
	/**
	 * ����һ������ָ����ϵ��ID-��ǩӳ���ࡣ
	 * @param map ָ����ID-��ǩӳ�����
	 */
	public PTagMap(IDMap<PTag> map){
		this.map = map == null ? new IDMap<PTag>(CodingType.COMPACT) : map;
	}
	
	
	/**
	 * ��ȡָ��ID��Ӧ��tag
	 * @param id ָ����idֵ��
	 * @return ָ��idֵ��Ӧ�ı�ǩ��
	 */
	public PTag getTag(int id){
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