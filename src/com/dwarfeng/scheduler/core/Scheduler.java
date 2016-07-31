package com.dwarfeng.scheduler.core;

import java.awt.Image;
import java.util.Locale;
import java.util.Queue;

import com.dwarfeng.dwarffunction.program.MvcProgram;
import com.dwarfeng.dwarffunction.program.Version;
import com.dwarfeng.dwarffunction.threads.NadeRunner;
import com.dwarfeng.dwarffunction.threads.RunnerQueue;
import com.dwarfeng.scheduler.info.ImageKeys;
import com.dwarfeng.scheduler.info.StringFieldKey;
import com.dwarfeng.scheduler.module.SModuleManager;
import com.dwarfeng.scheduler.module.SModuleControlPort;
import com.dwarfeng.scheduler.project.abstruct.SControlPort;
import com.dwarfeng.scheduler.project.abstruct.SViewControlPort;
import com.dwarfeng.scheduler.view.SViewManager;

/**
 * �ƻ��������
 * <p> �ó������Ҫ�����ǽ���ƻ��Ĺ滮�����Լ��ʼ���������⡣�ó�����ҪΧ��������������б�д��
 * <br>&nbsp;&nbsp; 1.�ʼǼ�¼�Լ����������˲������������е��е��Ʊʼǵȱʼǹ��������������������ĳЩ���ܡ�
 * <br>&nbsp;&nbsp; 2.�������ͨ�������������ָ���ļƻ����񣬳������ϵͳʱ��������Щ������й���
 * <p> ����ṹ����MVC�ṹ�����Ҿ����ܵ������໥���롣
 * <br> <code>Scheduler</code>����ӵ��һ����̨ά�������κ���Ҫ���ú�̨�ķ����������йܵ����ά�����ϡ�
 * @author DwArFeng
 * @since 1.8
 */
public final class Scheduler extends MvcProgram<SProgramControlPort, SModuleControlPort, SViewControlPort, SControlPort, SProgramConstField>{
	
	private final SProgramControlPort programControlPort = new SProgramControlPort() {
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.cabstruct.SchedulerControlPort#backgroundInvoke(com.dwarfeng.dwarffunction.threads.NadeRunner)
		 */
		@Override
		public void backgroundInvoke(NadeRunner runner) {
			background.invoke(runner);
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.cabstruct.SchedulerControlPort#getBackgroundQueueSize()
		 */
		@Override
		public int getBackgroundQueueSize() {
			return background.getQueueSize();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.typedef.cabstruct.SchedulerControlPort#getBackgroundQueue()
		 */
		@Override
		public Queue<NadeRunner> getBackgroundQueue() {
			return background.getWaitingQueue();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.core.SProgramControlPort#setLocale(java.util.Locale)
		 */
		@Override
		public void setLocale(Locale locale) {
			stringFields.setLocale(locale);
		}
		
	};
	private final SProgramConstField programConstField = new SProgramConstField() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.core.SProgramConstField#getVersion()
		 */
		@Override
		public Version getVersion() {
			// TODO Auto-generated method stub
			return null;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.core.SProgramConstField#getAuthor()
		 */
		@Override
		public String getAuthor() {
			// TODO Auto-generated method stub
			return null;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.core.SProgramConstField#getText(com.dwarfeng.scheduler.info.StringFieldKey)
		 */
		@Override
		public String getText(StringFieldKey key) {
			return stringFields.getText(key);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.scheduler.core.SProgramConstField#getImage(com.dwarfeng.scheduler.info.ImageKeys)
		 */
		@Override
		public Image getImage(ImageKeys key) {
			return imageFields.getImage(key);
		}
		
	};
	
	
	/**�йصĺ�̨*/
	private final static RunnerQueue<NadeRunner> background = new RunnerQueue<NadeRunner>();
	


	
	
	
	
	
	

	/**�ı��ֶ�*/
	private StringFields stringFields = new StringFields();
	

	
	/**ͼƬ�ֶ�*/
	private ImageFields imageFields = new ImageFields();
	



	
	
	private Scheduler(SModuleManager moduleManager, SViewManager viewManager, SControlManager controlManager){
		super(moduleManager, viewManager, controlManager);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.MvcProgram#getProgramControlPort()
	 */
	@Override
	public SProgramControlPort getProgramControlPort() {
		return programControlPort;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.program.mvc.ProgramManager#getProgramConstField()
	 */
	@Override
	public SProgramConstField getProgramConstField() {
		return programConstField;
	}
}
