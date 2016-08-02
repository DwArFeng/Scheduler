package com.dwarfeng.scheduler.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import com.dwarfeng.dwarffunction.gui.JMenuItemAction;
import com.dwarfeng.scheduler.core.Scheduler133;
import com.dwarfeng.scheduler.module.SProjectOperationHelper;
import com.dwarfeng.scheduler.module.project.funcint.Deleteable;
import com.dwarfeng.scheduler.module.project.funcint.SerialParamSetable;
import com.dwarfeng.scheduler.typedef.desint.Editable;

/**
 * �ṩ�ڶ�ͨ�õ��Ҽ��˵���ť�Ĺ����ࡣ
 * @author DwArFeng
 * @since 1.8
 */
public final class PopupMenuActions {
	
	/**
	 * �½�һ���༭��ť������
	 * @param describe ��곤��ͣ��ʱ��ʾ��������
	 * @param context �йر༭�������ġ�
	 * @return �༭��ť������
	 */
	public static Action newEditItem(String describe,Editable context){
		if(context == null) throw new NullPointerException("Context can't be null");
		
		return new JMenuItemAction.Productor()
				.icon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/edit.png")))
				.name("�༭")
				.description(describe)
				.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0))
				.listener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						SProjectOperationHelper.edit(context);
					}
				})
				.product();
	}
	
	/**
	 * ����һ���������԰�ť������
	 * @param describe ��곤��ͣ��ʱ��ʾ��������
	 * @param context �й��������õ������ġ�
	 * @return �������԰�ť������
	 */
	public static Action newParamSetItem(String describe,SerialParamSetable context){
		if(context == null) throw new NullPointerException("Context can't be null");
		
		return new JMenuItemAction.Productor()
				.icon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/paramPanel.png")))
				.name("��������")
				.description(describe)
				.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_F2,0))
				.listener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						SProjectOperationHelper.setSerialParam(context);
					}
				})
				.product();
	}
	
	/**
	 * ����һ��ɾ������İ�ť������
	 * @param describe ��곤��ͣ��ʱ��ʾ��������
	 * @param context �й�ɾ������������ġ�
	 * @return ɾ������ť������
	 */
	public static Action newDeleteItem(String describe,Deleteable context){
		if(context == null) throw new NullPointerException("Context can't be null");
		
		return new JMenuItemAction.Productor()
				.icon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/deleteFile.png")))
				.name("ɾ��")
				.description("���ɻָ���ɾ����ǰ�ıʼ�")
				.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0))
				.listener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						SProjectOperationHelper.requestDelete(context);
					}
				})
				.product();
	}
	
	/**
	 *  ����һ���½�����İ�ť������
	 * @param describe ��곤��ͣ��ʱ��ʾ��������
	 * @param context �й��½������������ġ�
	 * @return �½�����ť������
	 */
	public static Action newNewItem(String describe,ActionListener context){
		if(context == null) throw new NullPointerException("Context can't be null");

		return new JMenuItemAction.Productor()
				.icon(new ImageIcon(Scheduler133.class.getResource("/resource/menu/new.png")))
				.name("�½�")
				.description(describe)
				.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_N,0))
				.listener(context)
				.product();
	}
	
	private PopupMenuActions(){
		//Do nothing
	}
}
