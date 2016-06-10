package com.dwarfeng.scheduler.typedef.desint;

import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.dwarfeng.scheduler.core.RunnerQueue;
import com.dwarfeng.scheduler.gui.SchedulerGui;

/**
 * ���̶ȵ�ʵ���˻��ڽ���ı߽���������
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractEditor extends JPanel implements Editor{

	private static final long serialVersionUID = -20125203242713969L;
	/**�ɱ༭����*/
	protected Editable editable;
	/**������ָ��*/
	protected SchedulerGui mainFrame;
	
	protected AbstractEditor(){
		this(null);
	}
	/**
	 * ����һ������ָ�������棬�༭ָ������ĳ���༭����
	 * @param mainFrame ָ���������档
	 * @param editable ָ���Ŀɱ༭����
	 */
	public AbstractEditor(Editable editable) {
		if(editable == null) throw new NullPointerException("Editable must not be null");
		this.editable = editable;
		init();
	}
	
	@Override
	public Editable getEditable() {
		return this.editable;
	}
	
	/**
	 * ���ָ��������档
	 * @return ָ��������档
	 */
	public SchedulerGui getMainFrame(){
		return this.mainFrame;
	}
	
	/**
	 * ����������Ϊָ���������档
	 * @param mainFrame ָ���������档
	 */
	public void setMainFrame(SchedulerGui mainFrame){
		this.mainFrame = mainFrame;
	}
	
	/**
	 * ����༭������һ���˵��������ڲ��༭������ء�
	 * <p> �����ϣ�����ɲ˵����Ļ����򷵻�null��
	 * @return �˵�����
	 */
	public abstract JMenuBar getMenuBar();
	/**
	 * ��������ķ�������Ҫ�Ľ����������籣�棩��Ҫ���������С�
	 */
	public void dispose(){
		if(getEditable() != null){
			doWhenDispose();
			//�����ĵ�
			saveEditable();
			//�ͷ���Դ
			getEditable().release();
			getEditable().firedEditorClose();
		}
	}
	
	private void init(){
		paramInit();
		//�ж��ĵ��Ƿ�Ϊnull����Ϊnull�����첽��ȡ�ĵ�
		if(getEditable() != null){
			//��ʾ��ӭ����
			welcomeInit();
		}
		//�첽��ȡ�ĵ�����
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					getEditable().load();
				} catch (Exception e) {
					doWhenExceptionInLoad(e);
				}
				try {
					editorInit();
				} catch (Exception e) {
					doWhenExceptionInInit(e);
				}
			}
		};
		RunnerQueue.invoke(runnable);
	}
	
	@Override
	public void loadEditable(){
		try {
			getEditable().load();
		} catch (Exception e) {
			doWhenExceptionInLoad(e);
		}
	}
	@Override
	public void saveEditable(){
		try {
			getEditable().save();
		} catch (Exception e) {
			doWhenExceptionInSave(e);
		}
	}
	/**
	 * ��ӭ�����ʼ��������
	 * <p> �÷����ڴ����{@linkplain Editable} ��Ϊ<code>null</code> ʱ���á�
	 * �����ڼ��ط������֮ǰ��ʾ��ӭ���档
	 */
	protected abstract void welcomeInit();
	/**
	 * �������ʼ���쳣ʱ�Ĵ�������
	 * @param e 
	 */
	protected abstract void doWhenExceptionInInit(Exception e);
	/**
	 * �������ļ������쳣ʱ���õķ�����
	 * @param e 
	 */
	protected abstract void doWhenExceptionInLoad(Exception e);
	/**
	 * ������ʼ��������
	 * <p> �÷����ڹ����������б���һ�����ã�һ����˵�����ڳ�ʼ��������
	 */
	protected abstract void paramInit();
	/**
	 * 
	 * @throws Exception
	 */
	protected abstract void editorInit() throws Exception;
	/**
	 * 
	 */
	protected abstract void doWhenDispose();
	/**
	 * 
	 * @param e
	 */
	protected abstract void doWhenExceptionInSave(Exception e);
	
}
