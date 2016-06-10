package com.dwarfeng.scheduler.typedef.abstruct;

import com.dwarfeng.scheduler.io.Scpath;
import com.dwarfeng.scheduler.project.Project;

/**
 * 抽象附件。
 * <p> 最大化的实现了附件的方法。
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractAttachment implements Attachment{

	/**对象的工程上文*/
	protected ObjectInProject context;
	/**路径*/
	protected final Scpath scpath;
	/**附件指向的对象*/
	protected Object target;
	
	/**
	 * 生成一个默认的抽象附件。
	 * @param scpath 抽象附件的工作路径，不能为null。
	 * @throws NullPointerException 当工作路径为null时。
	 */
	public AbstractAttachment(Scpath scpath) {
		if(scpath == null) throw new NullPointerException("Scpath can't be null");
		this.scpath = scpath;
	}

	@Override
	public Project getRootProject() {
		return context.getRootProject();
	}
	/**
	 * 设置工程上下文，由其包含类指定。
	 * <p> 为了保证{@linkplain ObjectInProject#getRootProject()} 方法的迭代速度，上下文应该设置为可以确定{@linkplain Project}
	 * 对象的最上端的{@linkplain ObjectInProject}。
	 * @param context 指定的上下文。
	 */
	public void setRootProject(ObjectInProject context){
		this.context = context;
	}

	@Override
	public Scpath getScpath() {
		return scpath;
	}

	@Override
	public Object getAttachObject() {
		return target == null ? createDefaultObject() : target;
	}

	@Override
	public void setAttachObject(Object target) {
		if(checkTarget(target))this.target = target;
	}
	
	@Override
	public void release(){
		this.target = null;
	}
	
	/**
	 * 判断传入的参数是否符合要求。
	 * @param target 指定的传入参数。
	 * @return 是否符合要求。
	 */
	protected abstract boolean checkTarget(Object target);

}
