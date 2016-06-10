package com.dwarfeng.scheduler.typedef.desint;

import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.dwarfeng.scheduler.core.RunnerQueue;
import com.dwarfeng.scheduler.gui.SchedulerGui;

/**
 * 最大程度的实现了基于界面的边界器方法。
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractEditor extends JPanel implements Editor{

	private static final long serialVersionUID = -20125203242713969L;
	/**可编辑对象*/
	protected Editable editable;
	/**主界面指针*/
	protected SchedulerGui mainFrame;
	
	protected AbstractEditor(){
		this(null);
	}
	/**
	 * 生成一个属于指定主界面，编辑指定对象的抽象编辑器。
	 * @param mainFrame 指定的主界面。
	 * @param editable 指定的可编辑对象。
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
	 * 获得指向的主界面。
	 * @return 指向的主界面。
	 */
	public SchedulerGui getMainFrame(){
		return this.mainFrame;
	}
	
	/**
	 * 设置主界面为指定的主界面。
	 * @param mainFrame 指定的主界面。
	 */
	public void setMainFrame(SchedulerGui mainFrame){
		this.mainFrame = mainFrame;
	}
	
	/**
	 * 允许编辑器返回一个菜单栏，供内部编辑窗体加载。
	 * <p> 如果不希望生成菜单栏的话，则返回null。
	 * @return 菜单栏。
	 */
	public abstract JMenuBar getMenuBar();
	/**
	 * 结束自身的方法，必要的结束方法（如保存）需要包含在其中。
	 */
	public void dispose(){
		if(getEditable() != null){
			doWhenDispose();
			//保存文档
			saveEditable();
			//释放资源
			getEditable().release();
			getEditable().firedEditorClose();
		}
	}
	
	private void init(){
		paramInit();
		//判断文档是否为null，不为null，则异步读取文档
		if(getEditable() != null){
			//显示欢迎界面
			welcomeInit();
		}
		//异步读取文档方法
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
	 * 欢迎界面初始化方法。
	 * <p> 该方法在传入的{@linkplain Editable} 不为<code>null</code> 时调用。
	 * 用于在加载方法完成之前显示欢迎界面。
	 */
	protected abstract void welcomeInit();
	/**
	 * 当界面初始化异常时的处理方法。
	 * @param e 
	 */
	protected abstract void doWhenExceptionInInit(Exception e);
	/**
	 * 当加载文件出现异常时调用的方法。
	 * @param e 
	 */
	protected abstract void doWhenExceptionInLoad(Exception e);
	/**
	 * 参数初始化方法。
	 * <p> 该方法在构造器方法中被第一个调用，一般来说，用于初始化参数。
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
