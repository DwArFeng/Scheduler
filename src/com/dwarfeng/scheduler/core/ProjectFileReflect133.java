package com.dwarfeng.scheduler.core;


/**
 * XXX ������������˼�������ı�Ҫ�ԣ��Ҹо���������ûʲô��Ҫ��
 * �����ļ�ӳ���ࡣ
 * <p> ��������������ĺ�����֮һ��������Ӳ���ļ����ڴ棨�����������󣩵����á�ͬʱ ���˹�ͨ����·���빤�̰������á�
 * <br> �����¼�����������ڴ����еļ�¼λ�ã�ѹ����λ�ã���ͬʱ��¼�˸ù����ڹ���Ŀ¼�е���ʱ��ѹλ�á�
 * @author DwArFeng
 * @since 1.8
 */
public class ProjectFileReflect133{
//	
//	public final static Scpath VERSION_PATH = new Scpath("version.xml");
//	public final static String DOC_PREFIX = "docs/";
//	public final static String DOC_SUFFIX = ".rtf";
//	
//	/**Zip�汾*/
//	private String zipVersion;
//	/**Zip�汾�Ƿ��ʼ��*/
//	private boolean zipVersionInitial;
//	/**zip��ѹ��*/
//	private Unzipper unzipper;
//	
////	/** XXX ����ת�����еķ���
////	 * 
////	 * @param pathname
////	 * @param isNew
////	 * @throws ProjectCantConstructException 
////	 */
////	public ProjectFileReflect133(String pathname,boolean isNew) throws ProjectCantConstructException{
////		//�ж���ڲ����Ƿ�ǿջ��߷Ƿ�
////		if(pathname == null) throw new NullPointerException("File can't be null");
////		if(!pathname.endsWith(".sch")) throw new IllegalArgumentException("File name must end with \"sch\"");
////		//��ʼ�����ֳ�Ա����
////		this.zipVersion = null;
////		this.zipVersionInitial = false;
////		this.zipper = new Zipper();
////		
////		//���ɹ���ʵ��
////		final W2sf projectIO = ProjectFactory.getProjectIO(getZipVersion());
////		if(projectIO == null) throw new ProjectCantConstructException("Unexcepted sch version");
////		//���ļ��н���Project�ܹ�
////		if(!isNew){
////			try{
////				
////				//���ɽ�ѹʵ��
////				this.unzipper = new Unzipper();
////				
////				Thread backgroundUnzip = new Thread(new Runnable() {
////					@Override
////					public void run() {
////						unzipper.unzip();						
////					}
////				},"��̨��ѹ�߳�");
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
//	 * ȷ��ָ����Urlable�����Ѿ�����ѹ��
//	 * <p> ����ڸ��㷨����ʱ����Urlable��Ȼû�н�ѹ���������÷������ڵ��̣߳����ҽ���Urlable�Ľ�ѹ�������ڶ��ˣ�ֱ����
//	 * Urlable��ѹ��ɡ�
//	 * @param scpath ָ����Urlable��
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
//	 * �����ڹ���Ŀ¼���ĵ�Ŀ¼�·���һ�������ļ��С�
//	 * @return �ļ��е����ơ�
//	 */
//	
//	/**
//	 * ��ѹ�ߡ�
//	 * <p>����Ϊpfr���ڲ��࣬ר�����ڿ�����̨�߳̽��н�ѹ��
//	 * @author DwArFeng
//	 * @since 1.8
//	 */
//	class Unzipper{
//		
//		/**�����ļ��е�ʣ����ض���*/
//		private final List<Scpath> scpathsToUnzip;
//		/**��ͣ���صı��*/
//		private boolean backgroundUnzipHoldFlag;
//		/**�����*/
//		private final ReadWriteLock holdFlagLock;
//		/**�ļ����ص�ͬ����*/
//		private final ReadWriteLock urlableLock;
//		/**�ļ��Ľ�ѹ��*/
//		private final  Lock unzipLock;
//		/**��̨��ѹ���龰*/
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
//						if(scpath != null) CT.trace(scpath.getPathName() + "û�н�ѹ�ɹ�");
//					}finally{
//						removeScpathToUnzip(scpath);
//					}
//					backgroundUnzipCondition.signalAll();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//					CT.trace("��̨��ѹ�̱߳��ж�");
//				}finally{
//					unzipLock.unlock();
//				}
//			}
//			CT.trace("��̨��ѹ�Ѿ�ȫ�����");
//		}
//
//		/**
//		 * 
//		 * @param scpath
//		 */
//		private void checkUnzip(Scpath scpath){
//			//�ж�urlable��null
//			if(scpath == null) throw new NullPointerException("Urlable can't be null");
//			//����ļ��Ѿ���ѹ�ˣ���ֱ�ӷ��أ��������Ƚ�ѹָ���ļ���
//			if(! isUrlableUnzip(scpath)) return;
//			setBackgroundUnzipHoldFlag(true);
//			unzipLock.lock();
//			try{
//				//�ٴμ����ļ��Ƿ񱻽�ѹ�ˣ���һ���ǳ���Ҫ����ΪҲ���ڸ÷���������ʱ����ѹ�߳����ڽ�ѹ���ļ���
//				if(! isUrlableUnzip(scpath)) return;
//				try{
//					projectIO.unzipFile(ProjectFileReflect.this, scpath);	
//				}catch (IOException e) {
//					e.printStackTrace();
//					CT.trace("�ļ�û�б��ɹ��Ľ�ѹ");
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


