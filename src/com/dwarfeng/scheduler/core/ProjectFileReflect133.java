package com.dwarfeng.scheduler.core;


/**
 * XXX 我现在在认真思考这个类的必要性，我感觉这个类真的没什么必要。
 * 工程文件映射类。
 * <p> 该类是整个程序的核心类之一，起到连接硬盘文件与内存（即工程树对象）的作用。同时 起到了沟通工作路径与工程包的作用。
 * <br> 该类记录了整个工程在磁盘中的记录位置（压缩包位置），同时记录了该工程在工作目录中的临时解压位置。
 * @author DwArFeng
 * @since 1.8
 */
public class ProjectFileReflect133{
//	
//	public final static Scpath VERSION_PATH = new Scpath("version.xml");
//	public final static String DOC_PREFIX = "docs/";
//	public final static String DOC_SUFFIX = ".rtf";
//	
//	/**Zip版本*/
//	private String zipVersion;
//	/**Zip版本是否初始化*/
//	private boolean zipVersionInitial;
//	/**zip解压类*/
//	private Unzipper unzipper;
//	
////	/** XXX 正在转移其中的方法
////	 * 
////	 * @param pathname
////	 * @param isNew
////	 * @throws ProjectCantConstructException 
////	 */
////	public ProjectFileReflect133(String pathname,boolean isNew) throws ProjectCantConstructException{
////		//判断入口参数是否非空或者非法
////		if(pathname == null) throw new NullPointerException("File can't be null");
////		if(!pathname.endsWith(".sch")) throw new IllegalArgumentException("File name must end with \"sch\"");
////		//初始化各种成员变量
////		this.zipVersion = null;
////		this.zipVersionInitial = false;
////		this.zipper = new Zipper();
////		
////		//生成工程实例
////		final W2sf projectIO = ProjectFactory.getProjectIO(getZipVersion());
////		if(projectIO == null) throw new ProjectCantConstructException("Unexcepted sch version");
////		//从文件中建立Project架构
////		if(!isNew){
////			try{
////				
////				//生成解压实例
////				this.unzipper = new Unzipper();
////				
////				Thread backgroundUnzip = new Thread(new Runnable() {
////					@Override
////					public void run() {
////						unzipper.unzip();						
////					}
////				},"后台解压线程");
////				backgroundUnzip.setPriority(Thread.MIN_PRIORITY);
////				backgroundUnzip.start();
////			}catch(Exception e){
////				throw new ProjectCantConstructException();
////			}
////		}else{
////		}
////	}
//	
//	/**
//	 * 确保指定的Urlable对象已经被解压。
//	 * <p> 如果在该算法调用时，该Urlable仍然没有解压，则阻塞该方法所在的线程，并且将该Urlable的解压任务置于顶端，直到该
//	 * Urlable解压完成。
//	 * @param scpath 指定的Urlable。
//	 */
//	public void checkUnzip(Scpath scpath){
//		unzipper.checkUnzip(scpath);
//	}
//	
////	private Collection<Urlable> getUrlablesExcept(Urlable target) {
////		if(context == null) throw new ContextNotAssignedException();
////		Collection<Urlable> urlables = context.getUrlables();
////		urlables.remove(target);
////		return urlables;
////	}
//	/**
//	 * 申请在工作目录的文档目录下分配一个独立文件夹。
//	 * @return 文件夹的名称。
//	 */
//	
//	/**
//	 * 解压者。
//	 * <p>该类为pfr的内部类，专门用于开启后台线程进行解压。
//	 * @author DwArFeng
//	 * @since 1.8
//	 */
//	class Unzipper{
//		
//		/**工程文件中的剩余加载对象*/
//		private final List<Scpath> scpathsToUnzip;
//		/**暂停加载的标记*/
//		private boolean backgroundUnzipHoldFlag;
//		/**标记锁*/
//		private final ReadWriteLock holdFlagLock;
//		/**文件加载的同步锁*/
//		private final ReadWriteLock urlableLock;
//		/**文件的解压缩*/
//		private final  Lock unzipLock;
//		/**后台解压的情景*/
//		private final Condition backgroundUnzipCondition;
//		
//		private void unzip(){
//			while(!isAllUnzipWorkDone()){
//				unzipLock.lock();
//				try{
//					while(getBackgroundUnzipHoldFlag()){
//						backgroundUnzipCondition.await();
//					}
//					if(isAllUnzipWorkDone()) return;
//					Scpath scpath = null;
//					try{
//						scpath = getNextScpathToUnzip();
//						projectIO.unzipFile(ProjectFileReflect.this, scpath);	
//					}catch(Exception e){
//						e.printStackTrace();
//						if(scpath != null) CT.trace(scpath.getPathName() + "没有解压成功");
//					}finally{
//						removeScpathToUnzip(scpath);
//					}
//					backgroundUnzipCondition.signalAll();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//					CT.trace("后台解压线程被中断");
//				}finally{
//					unzipLock.unlock();
//				}
//			}
//			CT.trace("后台解压已经全部完成");
//		}
//
//		/**
//		 * 
//		 * @param scpath
//		 */
//		private void checkUnzip(Scpath scpath){
//			//判断urlable非null
//			if(scpath == null) throw new NullPointerException("Urlable can't be null");
//			//如果文件已经解压了，则直接返回，否则优先解压指定文件；
//			if(! isUrlableUnzip(scpath)) return;
//			setBackgroundUnzipHoldFlag(true);
//			unzipLock.lock();
//			try{
//				//再次检测该文件是否被解压了，这一步非常重要，因为也许在该方法被调用时，解压线程正在解压该文件。
//				if(! isUrlableUnzip(scpath)) return;
//				try{
//					projectIO.unzipFile(ProjectFileReflect.this, scpath);	
//				}catch (IOException e) {
//					e.printStackTrace();
//					CT.trace("文件没有被成功的解压");
//				}finally{
//					removeScpathToUnzip(scpath);
//					setBackgroundUnzipHoldFlag(false);
//					backgroundUnzipCondition.signalAll();
//				}
//			}finally{
//				unzipLock.unlock();
//			}
//			
//		}
//		
//		/**
//		 * 
//		 * @param urlables
//		 */
//		private void addScpathsToUnzip(Collection<Scpath> scpaths){
//			urlableLock.writeLock().lock();
//			try{
//				scpathsToUnzip.addAll(scpaths);
//			}finally{
//				urlableLock.writeLock().unlock();
//			}
//		}
//		/**
//		 * 
//		 * @param scpath
//		 */
//		private void removeScpathToUnzip(Scpath scpath){
//			urlableLock.writeLock().lock();
//			try{
//				scpathsToUnzip.remove(scpath);
//			}finally{
//				urlableLock.writeLock().unlock();
//			}
//		}
//		/**
//		 * 
//		 * @return
//		 */
//		private Scpath getNextScpathToUnzip(){
//			urlableLock.readLock().lock();
//			try{
//				return scpathsToUnzip.get(0);
//			}finally{
//				urlableLock.readLock().unlock();
//			}
//		}
//		/**
//		 * 
//		 * @return
//		 */
//		private boolean isAllUnzipWorkDone(){
//			urlableLock.readLock().lock();
//			try{
//				return scpathsToUnzip.isEmpty();
//			}finally{
//				urlableLock.readLock().unlock();
//			}
//		}
//		/**
//		 * 
//		 * @return
//		 */
//		private boolean getBackgroundUnzipHoldFlag(){
//			holdFlagLock.readLock().lock();
//			try{
//				return backgroundUnzipHoldFlag;
//			}finally{
//				holdFlagLock.readLock().unlock();
//			}
//		}
//		/**
//		 * 
//		 * @param aFlag
//		 */
//		private void setBackgroundUnzipHoldFlag(boolean aFlag){
//			holdFlagLock.writeLock().lock();
//			try{
//				backgroundUnzipHoldFlag = aFlag;
//			}finally{
//				holdFlagLock.writeLock().unlock();
//			}
//		}
//		/**
//		 * 
//		 * @param urlable
//		 * @return
//		 */
//		private boolean isUrlableUnzip(Scpath scpath){
//			urlableLock.readLock().lock();
//			try{
//				return scpathsToUnzip.contains(scpath);
//			}finally{
//				urlableLock.readLock().unlock();
//			}
//		}
//	}
}


