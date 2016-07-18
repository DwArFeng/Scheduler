package com.dwarfeng.scheduler.typedef.desint;

import javax.swing.JPanel;

import com.dwarfeng.scheduler.core.RunnerQueue;

/**
 * ���̶ȵ�ʵ���˻��ڽ���ı߽���������
 * <p> 
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractEditor<T extends Editable> extends JPanel implements Editor<T>{

	private static final long serialVersionUID = 7409953830467982439L;
	
	/**�ɱ༭����*/
	protected final T editable;
	
	/**�����״̬*/
	protected enum State{
		/**����״̬������������ĳ�ʼ��ʱ�����������״̬*/
		SUCC,
		/**ʧ��״̬�������ʼ�������쳣ʱ�����������״̬*/
		FAIL;
	}
	
	protected State state;
	
	/**
	 * ����һ������ָ�������棬�༭ָ������ĳ���༭����
	 * @param editable ָ���Ŀɱ༭����
	 */
	public AbstractEditor(T editable) {
		
		if(editable == null) throw new NullPointerException("Editable must not be null");
		
		this.editable = editable;
		//��ʼ������
		paramInit();
		//��ʾ��ӭ����
		welcomeInit();
		
		//�첽�����ĵ�
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
	 * �ù��������ṩ���������ʱ�õġ�
	 * <p> �ù�����������{@linkplain #AbstractEditor(Editable)}���������ϸ��˳���ʼ��˵
	 * ����������ʹ���๹�����Բ�ͬ��˳����ò�ͬ�ķ���������֤�����߽��н��濪����
	 * <p> �������κ���ʽ������ʹ�������������
	 * 
	 * @param editable ָ���Ŀɱ༭����
	 * @param i ���κ����壬ֻ��Ϊ������{@linkplain #AbstractEditor(Editable)}
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
	 * ��ӭ�����ʼ��������
	 * <p> �÷���������ʾ��ӭ���棬�����ڽ�������ʾ"���ڼ��أ����Ժ�..."�����ı�ǩ��Ҳ���Զ���һЩͼ����
	 * �����ڼ��ط������֮ǰ��ʾ��ӭ���档
	 */
	protected abstract void welcomeInit();
	
	/**
	 * �ڱ༭֮ǰ��׼��������������ʵ�֣�ͨ������������н��ж�ȡ������
	 * 
	 * @throws Exception ׼������ʧ��ʱ�׳��쳣��
	 */
	protected abstract void prepareInit() throws Exception;
	
	/**
	 * �������ʼ���쳣ʱ�Ĵ�������
	 * @param e ��Ҫ������쳣��
	 */
	protected abstract void doWhenExceptionInInit(Exception e);
	
	/**
	 * ��׼���ļ������쳣ʱ���õķ�����
	 * <p> ������ڱ߽�������׼���׶εĳ�ʼ�����������쳣ʱ���ô˷������׳����쳣����Ϊ��ڲ������д��ݡ�
	 * <br> �뾡���ܵ�Ϊ�쳣���з����жϣ����Ҹ��ݲ�ͬ���쳣�������ò�ͬ�Ĵ�������
	 * <p> �ڳ���������Ҫ����һ������ֵ�����������ؽ��Ϊ<code>true</code>�����ʾ��ʼ���������½��У����ҽ��༭����״̬����Ϊ{@linkplain State#FAIL}��
	 * ��֮��������������½��У�������Ȼ��״̬����Ϊ{@linkplain State#SUCC}��
	 * @param e ׼���ļ�ʱ�׳����쳣��  
	 * @return ���Ϊ<code>true</code>�������������³�ʼ������֮�����³�ʼ����
	 */
	protected abstract boolean doWhenExceptionInPrepare(Exception e);
	
	/**
	 * ������ʼ��������
	 * <p> �÷����ڹ����������б���һ�����ã�һ����˵�����ڳ�ʼ��������
	 */
	protected abstract void paramInit();
	
	/**
	 * ��{@linkplain #prepareInit()}������ȷ�ر�ִ�У�����{@linkplain #doWhenExceptionInPrepare(Exception)}��������<code>true</code>
	 * ��ʱ�򣬸÷��������ű����ã��ԶԱ༭������г�ʼ����
	 * @throws Exception �༭��ʼ�������쳣��
	 */
	protected abstract void editorInit() throws Exception;
	
	/**
	 * ���ظñ༭����״̬��
	 * @return �༭����״̬��
	 */
	protected State getState() {
		return state;
	}
	
}
