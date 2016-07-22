package com.dwarfeng.scheduler.project;

import com.dwarfeng.dwarffunction.cna.IDMap;
import com.dwarfeng.dwarffunction.cna.IDMap.CodingType;
import com.dwarfeng.scheduler.typedef.pabstruct.AbstractObjectOutProjectTree;
import com.dwarfeng.scheduler.typedef.pabstruct.ObjectInProject;

/**
 * ID-标签映射类。
 * @author DwArFeng。
 * @since 1.8。
 */
public class TagMap extends AbstractObjectOutProjectTree implements ObjectInProject{
	
	/**记录ID-Tag映射的ID映射*/
	private IDMap<Tag> map;
	
	/**
	 * 生成一个没有任何现有ID-Tag映射的ID-标签映射类。
	 */
	public TagMap(){
		this(null);
	}
	/**
	 * 生成一个具有指定关系的ID-标签映射类。
	 * @param map 指定的ID-标签映射对象。
	 */
	public TagMap(IDMap<Tag> map){
		this.map = map == null ? new IDMap<Tag>(CodingType.COMPACT) : map;
	}
	
	
	/**
	 * 获取指定ID对应的tag
	 * @param id 指定的id值。
	 * @return 指定id值对应的标签。
	 */
	public Tag getTag(int id){
		return map.get(id);
	}
	
	/**
	 * 返回这个工程中具有的所有ID。
	 * @return 工程中所有ID组成的数组。
	 */
	public int[] getTagIds(){
		return map.getAllIDs();
	}

//	/**
//	 * 映射标签与标签ID的类。
//	 * <p> 该类存放着整个工程的所有Tag，并且每个Tag都定义了唯一的编号。
//	 * <br> 该类提供了针对
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
//			//TODO 移除标签功能待完善
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
//	//TODO 移除标签功能待完善
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