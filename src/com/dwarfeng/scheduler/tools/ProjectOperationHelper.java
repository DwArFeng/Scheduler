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
 * 对整个工程以及工程树操作的封装方法。
 * <p> 提供的封装方法均是功能完善而且强大的方法，在任何情况下，这些方法都要优先于其他的同等方法。
 * @author DwArFeng
 * @since 1.8
 */
public final class ProjectOperationHelper {

	/**
	 * 在工程树的指定位置显示菜单。
	 * @param tree 指定的工程树。
	 * @param popupInTree 指定的可弹出菜单对象。
	 * @param x 显示位置的x坐标。
	 * @param y 显示位置的y坐标。
	 */
	public static void showPopupInProjectTree(JProjectTree tree,PopupInTree popupInTree,int x,int y){
		if(tree == null) throw new NullPointerException("Project tree can't be null");
		if(popupInTree == null) throw new NullPointerException("PopupInTree can't be null");
		
		JPopupMenu popup = popupInTree.getJPopupMenu();
		try{
			if(popup == null){
				throw new NullPointerException("popupInTree返回弹出菜单值为null，该返回值违例，请联系开发人员");
			}
		}catch(NullPointerException e){
			e.printStackTrace();
			return;
		}
		
		popup.show(tree, x, y);
	}
	
	/**
	 * 设置一个序列参数可设置对象的序列参数。
	 * <p> 该方法会打开一个对话框，要求用户输入新的参数，当用户选择或取消时，方法不对对象做任何的修改。
	 * <br> 当用户点击确定时，方法将修改对象的序列参数，并且更新有关工程树界面。
	 * @param sps 指定的序列参数可设置对象。
	 */
	public static void setSerialParam(SerialParamSetable sps){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());
		if(sps == null) throw new NullPointerException("Sps can't be null");
		
		try{
			SerialParam sp = sps.getSerialParam();
			if(sp == null) throw new IllegalArgumentException("Sps 返回序列参数值为null，该返回值违例，请联系开发人员");
		}catch(IllegalArgumentException e){
			e.printStackTrace();
			return;
		}
		SerialParam sp = UserInput.getSerialParam("参数设置：" + sps.getSerialParam().getName(), sps.getSerialParam());
		if(sp == null) return;
		sps.setSerialParam(sp);
		
		Scheduler.getInstance().refreshProjectTrees(sps.getRootProject(), sps);
	}
	
	/**
	 * 询问并删除工程中文件的方法。
	 * <p> 方法会在主界面中生成指定好的确认信息，为{@linkplain Deleteable#getConfirmWord()}，并询问用户是否删除。
	 * <br> 当用户点击取消或者关闭对话框时什么也不做，当用户点击确认按钮后，指定对象将在其父节点中删除，有关工程树界面更新，并选中父节点。
	 * @param deleteable 指定的可删除对象。
	 */
	public static void requestDelete(Deleteable deleteable){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());
		
		if(deleteable == null) throw new NullPointerException("Deleteable can't be null");
		
		String confirmWord = deleteable.getConfirmWord() == null ?
				"即将删除工程对象：" + deleteable.toString() + "\n"
				+ "当前操作不可恢复" 
				: 
				deleteable.getConfirmWord();
		
		int sel = JOptionPane.showConfirmDialog(
				Scheduler.getInstance().getGui(),
				confirmWord, 
				"删除确认", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.INFORMATION_MESSAGE, 
				null
		);
		if(sel == JOptionPane.YES_OPTION){
			
			ObjectInProjectTree parent = deleteable.getParent();
			
			CT.trace("正在删除对象...");
			//如果deleteable是可编辑对象，则先关闭课编辑对象的窗口（如果有的话）
			if(deleteable instanceof Editable){
				ProjectOperationHelper.forceDisposeEditor((Editable)deleteable);
			}
			deleteable.removeFromeParent();
			CT.trace("对象已经成功的被删除");
			
			Scheduler.getInstance().refreshProjectTrees(parent.getRootProject(), parent);
		}
	
	}
	
	/**
	 * 将指定的可移动对象在同级之间上移一位的方法。
	 * <p> 顶级元素强制不可移动，并且如果该元素已经位于首位，也不可移动。
	 * <br> 该方法将会在最后更新工程树。
	 * @param moveable 指定的可移动对象。
	 */
	public static void moveUp(Moveable moveable){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());

		if(moveable == null) throw new NullPointerException("Moveable can't be null");
		
		ObjectInProjectTree parent = moveable.getParent();
		//顶级元素强制不可移动，不管是否实现Moveable接口
		if(parent == null) return;
		int index = parent.getIndex(moveable);
		//如果已经在顶端了，则不可以移动
		if(index == 0)return;
		parent.remove(moveable);
		parent.insert(moveable, index - 1);
		//更新工程树
		Scheduler.getInstance().refreshProjectTrees(moveable.getRootProject(),moveable);
	}
	
	/**
	 * 将指定的可移动对象在同级之间下移一位的方法。
	 * <p> 顶级元素强制不可移动，并且如果该元素已经位于首位，也不可移动。
	 * <br> 该方法将会在最后更新工程树。
	 * @param moveable 指定的可移动对象。
	 */
	public static void moveDown(Moveable moveable){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());
		
		if(moveable == null) throw new NullPointerException("Moveable can't be null");

		ObjectInProjectTree parent = moveable.getParent();
		//顶级元素强制不可移动，不管是否实现Moveable接口
		if(parent == null) return;
		int index = parent.getIndex(moveable);
		//如果已经在末端了，则不可以移动
		if(index == parent.getChildCount()-1)return;
		parent.remove(moveable);
		parent.insert(moveable, index + 1);
		//更新工程树
		Scheduler.getInstance().refreshProjectTrees(moveable.getRootProject(),moveable);
	}
	
	/**
	 * 新建一个标准的工程文档。
	 */
	public static Project createNewProject(File file){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());

		if(file == null) throw new NullPointerException("File can't be null");
		try{
			CT.trace("正在创建新文件...");
			return ProjectIoHelper.createDefaultProject(file);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 读取指定文件指示的工程文档，如果在读取过程中发生异常导致无法读取，则返回<code>null</code>。
	 * @param file 指定的文件。
	 * @return 读取出的文档。
	 */
	public static Project loadProject(File file){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());

		if(file == null) throw new NullPointerException("File can't be null");
		
		CT.trace("正在读取文件");
		Project project = null;
		try {
			project = ProjectIoHelper.loadProject(file, ProjectIoHelper.Operate.FOREGROUND);
			CT.trace("文件读取成功");
			return project;
		}catch(Exception e){
			if(e instanceof IOException){
				
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(
						Scheduler.getInstance().getGui(), 
						"读取文件时发生通信异常，文件不能正确读取\n"
						+ "以下是异常的信息：\n"
						+ e.getMessage(), 
						"发生异常", 
						JOptionPane.WARNING_MESSAGE, 
						null
				);
				
				CT.trace("文件读取失败");
				return null;
				
			}
			else if(e instanceof UnhandledVersionException){
				
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(
						Scheduler.getInstance().getGui(), 
						"检测到不支持的版本，文件不能正确读取\n"
						+ "文件的版本是：" + ((UnhandledVersionException) e).getFailedVersion() + "\n"
						+ "该版本对于本程序而言" +  ((UnhandledVersionException) e).getVersionType(),
						"发生异常", 
						JOptionPane.WARNING_MESSAGE, 
						null
				);
				
				CT.trace("文件读取失败");
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
						"构架文件结构时出现异常，文件不能正确读取\n"
						+ "以下是异常的信息：\n"
						+ sb.toString(), 
						"发生异常", 
						JOptionPane.WARNING_MESSAGE, 
						null
				);
				
				CT.trace("文件读取失败");
				return null;
				
			}
			else if(e instanceof ProjectPathNotSuccessException){
				
				e.printStackTrace();
				
				for(Scpath scpath : ((ProjectPathNotSuccessException) e).getFailedList()){
					CT.trace("解压失败：\t" + scpath.getPathName());
				}
				
				int i = JOptionPane.showConfirmDialog(
						Scheduler.getInstance().getGui(), 
						"解压文件具体路径时发生异常，一个或多个文件没有正确的被解压。\n"
						+ "解压失败的文件被输出在了控制台上。\n"
						+ "以下是异常的信息：\n"
						+ e.getMessage()
						+"\n\n"
						+ "文件仍然可以被读取，但是解压失败的路径会造成数据丢失，要继续吗？",
						"发生异常", 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.WARNING_MESSAGE,
						null
				);
				
				if(i == JOptionPane.YES_OPTION){
					project = ((ProjectException) e).getProject();
					CT.trace("文件架构读取成功，对应路径中一个或多个损坏");
					return project;
				}else{
					closeProject(((ProjectException) e).getProject());
					CT.trace("文件读取失败");
					return null;
				}
			}
			else{
				
				e.printStackTrace();
				JOptionPane.showMessageDialog(
						Scheduler.getInstance().getGui(), 
						"读取文件时遇到无法处理的异常\n"
						+ "异常信息已经被打印在控制台上\n"
						+ "以下是异常的信息：\n"
						+ e.getMessage(),
						"发生异常", 
						JOptionPane.WARNING_MESSAGE, 
						null
				);
				CT.trace("文件读取失败");
				return null;
				
			}
			
		}
	}
	
	/**
	 * 保存指定的工程。
	 * <p>将工程位置保存在最新的路径上。
	 * @param project 指定的工程。
	 */
	public static void saveProject(Project project){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());

		if(project == null) throw new NullPointerException("File can't be null");
		CT.trace("正在将工程压缩存储");
		try {
			ProjectIoHelper.saveProject(project,ProjectIoHelper.Operate.FOREGROUND);
			CT.trace("文件保存成功");
		}catch(Exception e){
			if(e instanceof IOException){
				
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(
						Scheduler.getInstance().getGui(), 
						"保存文件时发生通信异常，文件不能正确保存\n"
						+ "以下是异常的信息：\n"
						+ e.getMessage(), 
						"发生异常", 
						JOptionPane.WARNING_MESSAGE, 
						null
				);
				
				CT.trace("文件保存失败");
				
			}
			else if(e instanceof ProjectPathNotSuccessException){
				
				e.printStackTrace();
				
				for(Scpath scpath : ((ProjectPathNotSuccessException) e).getFailedList()){
					CT.trace("压缩失败：\t" + scpath.getPathName());
				}
				
				JOptionPane.showMessageDialog(
						Scheduler.getInstance().getGui(), 
						"压缩文件具体路径时发生异常，一个或多个文件没有正确的被压缩。\n"
						+ "压缩失败的文件被输出在了控制台上。\n"
						+ "以下是异常的信息：\n"
						+ e.getMessage(),
						"发生异常", 
						JOptionPane.WARNING_MESSAGE,
						null
				);
				
				CT.trace("文件架构保存成功，对应路径中一个或多个损坏");
				
			}
			else{
				
				e.printStackTrace();
				JOptionPane.showMessageDialog(
						Scheduler.getInstance().getGui(), 
						"保存文件时遇到无法处理的异常\n"
						+ "异常信息已经被打印在控制台上\n"
						+ "以下是异常的信息：\n"
						+ e.getMessage(),
						"发生异常", 
						JOptionPane.WARNING_MESSAGE, 
						null
				);
				CT.trace("文件读取失败");
				
			}
		}
	}
	
	/**
	 * 关闭（释放）指定的工程。
	 * <p> 该过程不会关闭桌面面板上的编辑器，也不会保存工程，只是单纯的关闭。
	 * 请配合其它方法使用。
	 * @param project 指定的工程。
	 */
	public static void closeProject(Project project){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());

		if(project == null) throw new NullPointerException("Project can't be null");
		
		CT.trace("正在释放工程文件");
		try{
			ProjectIoHelper.closeProject(project);
			CT.trace("工程文件释放完毕");
		}catch(ProjectCloseException e){
			
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(
					Scheduler.getInstance().getGui(), 
					"关闭文件时发生异常\n"
					+ "以下是异常的信息：\n"
					+ e.getMessage(), 
					"发生异常", 
					JOptionPane.WARNING_MESSAGE, 
					null
			);
		}
	}

	/**
	 * 调出指定可编辑对象的编辑界面并开始编辑。
	 * @param editable 指定的可编辑对象。
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
	 * 释放指定工程在桌面面板上的所有编辑器。
	 * @param project 指定的工程。
	 * @return 是否全部关闭成功，如果至少有一个界面没有关闭成功，则返回<code>false</code>。
	 * @throws NullPointerException <code>project</code>为<code>null</code>。
	 */
	public static boolean disposeEditor(Project project,int i){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());
		
		if(project == null) throw new NullPointerException("Project can't be null");
		
		return Scheduler.getInstance().getGui().getDesktopPane().disposeEditor(project);
	}
	
	/**
	 * 释放指定工程在桌面面板上的所有编辑器。
	 * @param project 指定的工程。
	 * @throws NullPointerException <code>project</code>为<code>null</code>。
	 */
	public static void forceDisposeEditor(Project project){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());
		
		if(project == null) throw new NullPointerException("Project can't be null");
		
		Scheduler.getInstance().getGui().getDesktopPane().forceDisposeEditor(project);
	}
	
	/**
	 * 关闭指定可编辑对象在桌面面板上的编辑器。
	 * @param editable 指定的可编辑对象。
	 * @return 是否全部关闭成功，如果至少有一个界面没有关闭成功，则返回<code>false</code>。
	 */
	public static boolean disposeEditor(Editable editable,int i){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());

		if(editable == null) throw new NullPointerException("Editable can't be null");
		
		return Scheduler.getInstance().getGui().getDesktopPane().disposeEditor(editable);
	}
	
	/**
	 * 关闭指定可编辑对象在桌面面板上的编辑器。
	 * @param editable 指定的可编辑对象。
	 * @return 是否全部关闭成功，如果至少有一个界面没有关闭成功，则返回<code>false</code>。
	 */
	public static void forceDisposeEditor(Editable editable){
		if(Scheduler.getInstance().getGui() == null ) throw new IllegalStateException("Bad state : " + Scheduler.getInstance().getState());

		if(editable == null) throw new NullPointerException("Editable can't be null");
		
		Scheduler.getInstance().getGui().getDesktopPane().forceDisposeEditor(editable);
	}
	
	private ProjectOperationHelper(){
		//禁止实例化该类。
	}
}
