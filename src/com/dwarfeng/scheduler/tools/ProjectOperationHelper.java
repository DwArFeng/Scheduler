package com.dwarfeng.scheduler.tools;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import com.dwarfeng.func.io.CT;
import com.dwarfeng.scheduler.core.Scheduler;
import com.dwarfeng.scheduler.gui.JProjectTree;
import com.dwarfeng.scheduler.io.ProjectIoHelper;
import com.dwarfeng.scheduler.io.Scpath;
import com.dwarfeng.scheduler.project.Project;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.desint.Editable;
import com.dwarfeng.scheduler.typedef.exception.ProjectCloseException;
import com.dwarfeng.scheduler.typedef.exception.ProjectException;
import com.dwarfeng.scheduler.typedef.exception.ProjectPathNotSuccessException;
import com.dwarfeng.scheduler.typedef.exception.UnhandledVersionException;
import com.dwarfeng.scheduler.typedef.exception.UnstructFailedException;
import com.dwarfeng.scheduler.typedef.exception.UnstructFailedException.FailedType;
import com.dwarfeng.scheduler.typedef.funcint.Deleteable;
import com.dwarfeng.scheduler.typedef.funcint.Moveable;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;
import com.dwarfeng.scheduler.typedef.funcint.SerialParam;
import com.dwarfeng.scheduler.typedef.funcint.SerialParamSetable;

/**
 * �����������Լ������������ķ�װ������
 * <p> �ṩ�ķ�װ�������ǹ������ƶ���ǿ��ķ��������κ�����£���Щ������Ҫ������������ͬ�ȷ�����
 * @author DwArFeng
 * @since 1.8
 */
public final class ProjectOperationHelper {

	/**
	 * �ڹ�������ָ��λ����ʾ�˵���
	 * @param tree ָ���Ĺ�������
	 * @param popupInTree ָ���Ŀɵ����˵�����
	 * @param x ��ʾλ�õ�x���ꡣ
	 * @param y ��ʾλ�õ�y���ꡣ
	 */
	public static void showPopupInProjectTree(JProjectTree tree,PopupInTree popupInTree,int x,int y){
		if(tree == null) throw new NullPointerException("Project tree can't be null");
		if(popupInTree == null) throw new NullPointerException("PopupInTree can't be null");
		
		JPopupMenu popup = popupInTree.getJPopupMenu();
		try{
			if(popup == null){
				throw new NullPointerException("popupInTree���ص����˵�ֵΪnull���÷���ֵΥ��������ϵ������Ա");
			}
		}catch(NullPointerException e){
			e.printStackTrace();
			return;
		}
		
		popup.show(tree, x, y);
	}
	
	/**
	 * ����һ�����в��������ö�������в�����
	 * <p> �÷������һ���Ի���Ҫ���û������µĲ��������û�ѡ���ȡ��ʱ���������Զ������κε��޸ġ�
	 * <br> ���û����ȷ��ʱ���������޸Ķ�������в��������Ҹ����йع��������档
	 * @param sps ָ�������в��������ö���
	 */
	public static void setSerialParam(SerialParamSetable sps){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());
		if(sps == null) throw new NullPointerException("Sps can't be null");
		
		try{
			SerialParam sp = sps.getSerialParam();
			if(sp == null) throw new IllegalArgumentException("Sps �������в���ֵΪnull���÷���ֵΥ��������ϵ������Ա");
		}catch(IllegalArgumentException e){
			e.printStackTrace();
			return;
		}
		SerialParam sp = UserInput.getSerialParam("�������ã�" + sps.getSerialParam().getName(), sps.getSerialParam());
		if(sp == null) return;
		sps.setSerialParam(sp);
		
		Scheduler.getInstance().refreshProjectTrees(sps.getRootProject(), sps);
	}
	
	/**
	 * ѯ�ʲ�ɾ���������ļ��ķ�����
	 * <p> ��������������������ָ���õ�ȷ����Ϣ��Ϊ{@linkplain Deleteable#getConfirmWord()}����ѯ���û��Ƿ�ɾ����
	 * <br> ���û����ȡ�����߹رնԻ���ʱʲôҲ���������û����ȷ�ϰ�ť��ָ���������丸�ڵ���ɾ�����йع�����������£���ѡ�и��ڵ㡣
	 * @param deleteable ָ���Ŀ�ɾ������
	 */
	public static void requestDelete(Deleteable deleteable){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());
		
		if(deleteable == null) throw new NullPointerException("Deleteable can't be null");
		
		String confirmWord = deleteable.getConfirmWord() == null ?
				"����ɾ�����̶���" + deleteable.toString() + "\n"
				+ "��ǰ�������ɻָ�" 
				: 
				deleteable.getConfirmWord();
		
		int sel = JOptionPane.showConfirmDialog(
				Scheduler.getInstance().getGui(),
				confirmWord, 
				"ɾ��ȷ��", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.INFORMATION_MESSAGE, 
				null
		);
		if(sel == JOptionPane.YES_OPTION){
			
			ObjectInProjectTree parent = deleteable.getParent();
			
			CT.trace("����ɾ������...");
			//���deleteable�ǿɱ༭�������ȹرտα༭����Ĵ��ڣ�����еĻ���
			if(deleteable instanceof Editable){
				ProjectOperationHelper.forceDisposeEditor((Editable)deleteable);
			}
			deleteable.removeFromeParent();
			CT.trace("�����Ѿ��ɹ��ı�ɾ��");
			
			Scheduler.getInstance().refreshProjectTrees(parent.getRootProject(), parent);
		}
	
	}
	
	/**
	 * ��ָ���Ŀ��ƶ�������ͬ��֮������һλ�ķ�����
	 * <p> ����Ԫ��ǿ�Ʋ����ƶ������������Ԫ���Ѿ�λ����λ��Ҳ�����ƶ���
	 * <br> �÷��������������¹�������
	 * @param moveable ָ���Ŀ��ƶ�����
	 */
	public static void moveUp(Moveable moveable){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());

		if(moveable == null) throw new NullPointerException("Moveable can't be null");
		
		ObjectInProjectTree parent = moveable.getParent();
		//����Ԫ��ǿ�Ʋ����ƶ��������Ƿ�ʵ��Moveable�ӿ�
		if(parent == null) return;
		int index = parent.getIndex(moveable);
		//����Ѿ��ڶ����ˣ��򲻿����ƶ�
		if(index == 0)return;
		parent.remove(moveable);
		parent.insert(moveable, index - 1);
		//���¹�����
		Scheduler.getInstance().refreshProjectTrees(moveable.getRootProject(),moveable);
	}
	
	/**
	 * ��ָ���Ŀ��ƶ�������ͬ��֮������һλ�ķ�����
	 * <p> ����Ԫ��ǿ�Ʋ����ƶ������������Ԫ���Ѿ�λ����λ��Ҳ�����ƶ���
	 * <br> �÷��������������¹�������
	 * @param moveable ָ���Ŀ��ƶ�����
	 */
	public static void moveDown(Moveable moveable){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());
		
		if(moveable == null) throw new NullPointerException("Moveable can't be null");

		ObjectInProjectTree parent = moveable.getParent();
		//����Ԫ��ǿ�Ʋ����ƶ��������Ƿ�ʵ��Moveable�ӿ�
		if(parent == null) return;
		int index = parent.getIndex(moveable);
		//����Ѿ���ĩ���ˣ��򲻿����ƶ�
		if(index == parent.getChildCount()-1)return;
		parent.remove(moveable);
		parent.insert(moveable, index + 1);
		//���¹�����
		Scheduler.getInstance().refreshProjectTrees(moveable.getRootProject(),moveable);
	}
	
	/**
	 * �½�һ����׼�Ĺ����ĵ���
	 */
	public static Project createNewProject(File file){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());

		if(file == null) throw new NullPointerException("File can't be null");
		try{
			CT.trace("���ڴ������ļ�...");
			return ProjectIoHelper.createDefaultProject(file);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * ��ȡָ���ļ�ָʾ�Ĺ����ĵ�������ڶ�ȡ�����з����쳣�����޷���ȡ���򷵻�<code>null</code>��
	 * @param file ָ�����ļ���
	 * @return ��ȡ�����ĵ���
	 */
	public static Project loadProject(File file){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());

		if(file == null) throw new NullPointerException("File can't be null");
		
		CT.trace("���ڶ�ȡ�ļ�");
		Project project = null;
		try {
			project = ProjectIoHelper.loadProject(file, ProjectIoHelper.Operate.FOREGROUND);
			CT.trace("�ļ���ȡ�ɹ�");
			return project;
		}catch(Exception e){
			if(e instanceof IOException){
				
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(
						Scheduler.getInstance().getGui(), 
						"��ȡ�ļ�ʱ����ͨ���쳣���ļ�������ȷ��ȡ\n"
						+ "�������쳣����Ϣ��\n"
						+ e.getMessage(), 
						"�����쳣", 
						JOptionPane.WARNING_MESSAGE, 
						null
				);
				
				CT.trace("�ļ���ȡʧ��");
				return null;
				
			}
			else if(e instanceof UnhandledVersionException){
				
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(
						Scheduler.getInstance().getGui(), 
						"��⵽��֧�ֵİ汾���ļ�������ȷ��ȡ\n"
						+ "�ļ��İ汾�ǣ�" + ((UnhandledVersionException) e).getFailedVersion() + "\n"
						+ "�ð汾���ڱ��������" +  ((UnhandledVersionException) e).getVersionType(),
						"�����쳣", 
						JOptionPane.WARNING_MESSAGE, 
						null
				);
				
				CT.trace("�ļ���ȡʧ��");
				return null;
				
			}
			else if(e instanceof UnstructFailedException){
				
				e.printStackTrace();
				
				StringBuilder sb = new StringBuilder();
				for(FailedType type: ((UnstructFailedException) e).getFailedSet()){
					sb.append(type + " ");
				}
				JOptionPane.showMessageDialog(
						Scheduler.getInstance().getGui(), 
						"�����ļ��ṹʱ�����쳣���ļ�������ȷ��ȡ\n"
						+ "�������쳣����Ϣ��\n"
						+ sb.toString(), 
						"�����쳣", 
						JOptionPane.WARNING_MESSAGE, 
						null
				);
				
				CT.trace("�ļ���ȡʧ��");
				return null;
				
			}
			else if(e instanceof ProjectPathNotSuccessException){
				
				e.printStackTrace();
				
				for(Scpath scpath : ((ProjectPathNotSuccessException) e).getFailedList()){
					CT.trace("��ѹʧ�ܣ�\t" + scpath.getPathName());
				}
				
				int i = JOptionPane.showConfirmDialog(
						Scheduler.getInstance().getGui(), 
						"��ѹ�ļ�����·��ʱ�����쳣��һ�������ļ�û����ȷ�ı���ѹ��\n"
						+ "��ѹʧ�ܵ��ļ���������˿���̨�ϡ�\n"
						+ "�������쳣����Ϣ��\n"
						+ e.getMessage()
						+"\n\n"
						+ "�ļ���Ȼ���Ա���ȡ�����ǽ�ѹʧ�ܵ�·����������ݶ�ʧ��Ҫ������",
						"�����쳣", 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.WARNING_MESSAGE,
						null
				);
				
				if(i == JOptionPane.YES_OPTION){
					project = ((ProjectException) e).getProject();
					CT.trace("�ļ��ܹ���ȡ�ɹ�����Ӧ·����һ��������");
					return project;
				}else{
					closeProject(((ProjectException) e).getProject());
					CT.trace("�ļ���ȡʧ��");
					return null;
				}
			}
			else{
				
				e.printStackTrace();
				JOptionPane.showMessageDialog(
						Scheduler.getInstance().getGui(), 
						"��ȡ�ļ�ʱ�����޷�������쳣\n"
						+ "�쳣��Ϣ�Ѿ�����ӡ�ڿ���̨��\n"
						+ "�������쳣����Ϣ��\n"
						+ e.getMessage(),
						"�����쳣", 
						JOptionPane.WARNING_MESSAGE, 
						null
				);
				CT.trace("�ļ���ȡʧ��");
				return null;
				
			}
			
		}
	}
	
	/**
	 * ����ָ���Ĺ��̡�
	 * <p>������λ�ñ��������µ�·���ϡ�
	 * @param project ָ���Ĺ��̡�
	 */
	public static void saveProject(Project project){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());

		if(project == null) throw new NullPointerException("File can't be null");
		CT.trace("���ڽ�����ѹ���洢");
		try {
			ProjectIoHelper.saveProject(project,ProjectIoHelper.Operate.FOREGROUND);
			CT.trace("�ļ�����ɹ�");
		}catch(Exception e){
			if(e instanceof IOException){
				
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(
						Scheduler.getInstance().getGui(), 
						"�����ļ�ʱ����ͨ���쳣���ļ�������ȷ����\n"
						+ "�������쳣����Ϣ��\n"
						+ e.getMessage(), 
						"�����쳣", 
						JOptionPane.WARNING_MESSAGE, 
						null
				);
				
				CT.trace("�ļ�����ʧ��");
				
			}
			else if(e instanceof ProjectPathNotSuccessException){
				
				e.printStackTrace();
				
				for(Scpath scpath : ((ProjectPathNotSuccessException) e).getFailedList()){
					CT.trace("ѹ��ʧ�ܣ�\t" + scpath.getPathName());
				}
				
				JOptionPane.showMessageDialog(
						Scheduler.getInstance().getGui(), 
						"ѹ���ļ�����·��ʱ�����쳣��һ�������ļ�û����ȷ�ı�ѹ����\n"
						+ "ѹ��ʧ�ܵ��ļ���������˿���̨�ϡ�\n"
						+ "�������쳣����Ϣ��\n"
						+ e.getMessage(),
						"�����쳣", 
						JOptionPane.WARNING_MESSAGE,
						null
				);
				
				CT.trace("�ļ��ܹ�����ɹ�����Ӧ·����һ��������");
				
			}
			else{
				
				e.printStackTrace();
				JOptionPane.showMessageDialog(
						Scheduler.getInstance().getGui(), 
						"�����ļ�ʱ�����޷�������쳣\n"
						+ "�쳣��Ϣ�Ѿ�����ӡ�ڿ���̨��\n"
						+ "�������쳣����Ϣ��\n"
						+ e.getMessage(),
						"�����쳣", 
						JOptionPane.WARNING_MESSAGE, 
						null
				);
				CT.trace("�ļ���ȡʧ��");
				
			}
		}
	}
	
	/**
	 * �رգ��ͷţ�ָ���Ĺ��̡�
	 * <p> �ù��̲���ر���������ϵı༭����Ҳ���ᱣ�湤�̣�ֻ�ǵ����Ĺرա�
	 * �������������ʹ�á�
	 * @param project ָ���Ĺ��̡�
	 */
	public static void closeProject(Project project){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());

		if(project == null) throw new NullPointerException("Project can't be null");
		
		CT.trace("�����ͷŹ����ļ�");
		try{
			ProjectIoHelper.closeProject(project);
			CT.trace("�����ļ��ͷ����");
		}catch(ProjectCloseException e){
			
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(
					Scheduler.getInstance().getGui(), 
					"�ر��ļ�ʱ�����쳣\n"
					+ "�������쳣����Ϣ��\n"
					+ e.getMessage(), 
					"�����쳣", 
					JOptionPane.WARNING_MESSAGE, 
					null
			);
		}
	}

	/**
	 * ����ָ���ɱ༭����ı༭���沢��ʼ�༭��
	 * @param editable ָ���Ŀɱ༭����
	 */
	public static void edit(Editable editable){
		
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());
		
		if(editable == null) throw new NullPointerException("Editable can't be null");
		
		try{
			Scheduler.getInstance().getGui().getDesktopPane().edit(editable);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * �ͷ�ָ����������������ϵ����б༭����
	 * @param project ָ���Ĺ��̡�
	 * @return �Ƿ�ȫ���رճɹ������������һ������û�йرճɹ����򷵻�<code>false</code>��
	 * @throws NullPointerException <code>project</code>Ϊ<code>null</code>��
	 */
	public static boolean disposeEditor(Project project,int i){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());
		
		if(project == null) throw new NullPointerException("Project can't be null");
		
		return Scheduler.getInstance().getGui().getDesktopPane().disposeEditor(project);
	}
	
	/**
	 * �ͷ�ָ����������������ϵ����б༭����
	 * @param project ָ���Ĺ��̡�
	 * @throws NullPointerException <code>project</code>Ϊ<code>null</code>��
	 */
	public static void forceDisposeEditor(Project project){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());
		
		if(project == null) throw new NullPointerException("Project can't be null");
		
		Scheduler.getInstance().getGui().getDesktopPane().forceDisposeEditor(project);
	}
	
	/**
	 * �ر�ָ���ɱ༭��������������ϵı༭����
	 * @param editable ָ���Ŀɱ༭����
	 * @return �Ƿ�ȫ���رճɹ������������һ������û�йرճɹ����򷵻�<code>false</code>��
	 */
	public static boolean disposeEditor(Editable editable,int i){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());

		if(editable == null) throw new NullPointerException("Editable can't be null");
		
		return Scheduler.getInstance().getGui().getDesktopPane().disposeEditor(editable);
	}
	
	/**
	 * �ر�ָ���ɱ༭��������������ϵı༭����
	 * @param editable ָ���Ŀɱ༭����
	 * @return �Ƿ�ȫ���رճɹ������������һ������û�йرճɹ����򷵻�<code>false</code>��
	 */
	public static void forceDisposeEditor(Editable editable){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());

		if(editable == null) throw new NullPointerException("Editable can't be null");
		
		Scheduler.getInstance().getGui().getDesktopPane().forceDisposeEditor(editable);
	}
	
	private ProjectOperationHelper(){
		//��ֹʵ�������ࡣ
	}
}
