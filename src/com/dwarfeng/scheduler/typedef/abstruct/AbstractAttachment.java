package com.dwarfeng.scheduler.typedef.abstruct;

import com.dwarfeng.scheduler.io.Scpath;
import com.dwarfeng.scheduler.project.Project;

/**
 * ���󸽼���
 * <p> ��󻯵�ʵ���˸����ķ�����
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractAttachment implements Attachment{

	/**����Ĺ�������*/
	protected ObjectInProject context;
	/**·��*/
	protected final Scpath scpath;
	/**����ָ��Ķ���*/
	protected Object target;
	
	/**
	 * ����һ��Ĭ�ϵĳ��󸽼���
	 * @param scpath ���󸽼��Ĺ���·��������Ϊnull��
	 * @throws NullPointerException ������·��Ϊnullʱ��
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
	 * ���ù��������ģ����������ָ����
	 * <p> Ϊ�˱�֤{@linkplain ObjectInProject#getRootProject()} �����ĵ����ٶȣ�������Ӧ������Ϊ����ȷ��{@linkplain Project}
	 * ��������϶˵�{@linkplain ObjectInProject}��
	 * @param context ָ���������ġ�
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
	 * �жϴ���Ĳ����Ƿ����Ҫ��
	 * @param target ָ���Ĵ��������
	 * @return �Ƿ����Ҫ��
	 */
	protected abstract boolean checkTarget(Object target);

}
