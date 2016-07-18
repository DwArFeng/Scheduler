package com.dwarfeng.scheduler.typedef.desint;

import javax.swing.JPanel;

import com.dwarfeng.scheduler.core.RunnerQueue;

/**
 * 最大程度的实现了基于界面的边界器方法。
 * <p> 
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractEditor<T extends Editable> extends JPanel implements Editor<T>{

	private static final long serialVersionUID = 7409953830467982439L;
	
	/**可编辑对象*/
	protected final T editable;
	
	/**程序的状态*/
	protected enum State{
		/**良好状态，如程序正常的初始化时，会呈现这种状态*/
		SUCC,
		/**失败状态，程序初始化发生异常时，会呈现这种状态*/
		FAIL;
	}
	
	protected State state;
	
	/**
	 * 生成一个属于指定主界面，编辑指定对象的抽象编辑器。
	 * @param editable 指定的可编辑对象。
	 */
	public AbstractEditor(T editable) {
		
		if(editable == null) throw new NullPointerException("Editable must not be null");
		
		this.editable = editable;
		//初始化参数
		paramInit();
		//显示欢迎界面
		welcomeInit();
		
		//异步加载文档
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				boolean flag = false;
				try {
					prepareInit();
				} catch (Exception e) {
					flag = doWhenExceptionInPrepare(e);
				}
				if(flag){
					state = State.FAIL;
					return;
				}
				try {
					editorInit();
					state = State.SUCC;
				} catch (Exception e) {
					doWhenExceptionInInit(e);
					state = State.FAIL;
				}		
			}
		};
		RunnerQueue.invoke(runnable);
	}
	
	/**
	 * 该构造器是提供给子类调试时用的。
	 * <p> 该构造器不会像{@linkplain #AbstractEditor(Editable)}这样按照严格的顺序初始胡说
	 * ，这样可以使子类构造器以不同的顺序调用不同的方法进行验证，或者进行界面开发。
	 * <p> 切勿在任何正式程序中使用这个构造器。
	 * 
	 * @param editable 指定的可编辑对象。
	 * @param i 无任何意义，只是为了区分{@linkplain #AbstractEditor(Editable)}
	 */
	protected AbstractEditor(T editable,int i){
		this.editable = editable;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editor#getEditable()
	 */
	@Override
	public T getEditable() {
		return this.editable;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.desint.Editor#getEditPanel()
	 */
	@Override
	public JPanel getEditPanel(){
		return this;
	}

	/**
	 * 欢迎界面初始化方法。
	 * <p> 该方法用来显示欢迎界面，比如在界面上显示"正在加载，请稍后..."这样的标签，也可以定义一些图案。
	 * 用于在加载方法完成之前显示欢迎界面。
	 */
	protected abstract void welcomeInit();
	
	/**
	 * 在编辑之前的准备方法，由子类实现，通常在这个方法中进行读取操作。
	 * 
	 * @throws Exception 准备方法失败时抛出异常。
	 */
	protected abstract void prepareInit() throws Exception;
	
	/**
	 * 当界面初始化异常时的处理方法。
	 * @param e 需要处理的异常。
	 */
	protected abstract void doWhenExceptionInInit(Exception e);
	
	/**
	 * 当准备文件出现异常时调用的方法。
	 * <p> 程序会在边界器进入准备阶段的初始化工作遇到异常时调用此方法，抛出的异常被作为入口参数进行传递。
	 * <br> 请尽可能的为异常进行分类判断，并且根据不同的异常类型设置不同的处理方法。
	 * <p> 在程序的最后，需要返回一个布尔值结果，如果返回结果为<code>true</code>，则表示初始化不做向下进行，并且将编辑器的状态设置为{@linkplain State#FAIL}。
	 * 反之则代表程序继续向下进行，并且仍然将状态设置为{@linkplain State#SUCC}。
	 * @param e 准备文件时抛出的异常。  
	 * @return 如果为<code>true</code>，则程序继续向下初始化，反之不向下初始化。
	 */
	protected abstract boolean doWhenExceptionInPrepare(Exception e);
	
	/**
	 * 参数初始化方法。
	 * <p> 该方法在构造器方法中被第一个调用，一般来说，用于初始化参数。
	 */
	protected abstract void paramInit();
	
	/**
	 * 当{@linkplain #prepareInit()}方法正确地被执行，或者{@linkplain #doWhenExceptionInPrepare(Exception)}方法返回<code>true</code>
	 * 的时候，该方法紧接着被调用，以对编辑界面进行初始化。
	 * @throws Exception 编辑初始化出现异常。
	 */
	protected abstract void editorInit() throws Exception;
	
	/**
	 * 返回该编辑器的状态。
	 * @return 编辑器的状态。
	 */
	protected State getState() {
		return state;
	}
	
}
