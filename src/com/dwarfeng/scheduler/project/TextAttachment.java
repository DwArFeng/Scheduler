package com.dwarfeng.scheduler.project;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.text.Document;
import javax.swing.text.EditorKit;

import com.dwarfeng.func.io.CT;
import com.dwarfeng.scheduler.io.ProjectHelper;
import com.dwarfeng.scheduler.io.Scpath;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractAttachment;

/**
 * 文本附件。
 * <p> 该类为文本附件提供了最大化的方法实现。
 * @author DwArFeng
 * @since 1.8
 */
public abstract class TextAttachment extends AbstractAttachment {

	/**使用的编辑包*/
	protected EditorKit kit;
	
	public TextAttachment(Scpath scpath,EditorKit kit) {
		super(scpath);
		if(kit == null) throw new NullPointerException("EditorKit can't be null");
		this.kit = kit;
	}
	
	/**
	 * 返回该对象的编辑包。
	 * @return 该对象的编辑包。
	 */
	public EditorKit getEditorKit(){
		return kit;
	}

	@Override
	public void load() throws Exception {
		Document document = (Document) getEditorKit().createDefaultDocument();
		InputStream win = null;
		
		try{
			//读取语句
			CT.trace("正在读取文件 ：" + getScpath().getPathName() + "\t线程描述：" + Thread.currentThread());
			
			//初始化输入流。
			try {
				win = ProjectHelper.getInputStream(getRootProject(), getScpath());
			} catch (Exception e) {
				e.printStackTrace();
				CT.trace("由于某些原因，无法成功的构建工作路径输入流");
				throw e;
			}
			
			
			//读取文件。
			try {
				getEditorKit().read(win, document, 0);
				setAttachObject(document);
			} catch (Exception e) {
				e.printStackTrace();
				CT.trace("由于某些原因，无法读取文件");
				throw e;
			}
			
			CT.trace("文件读取完成");
			
		}finally{
			if(win != null){
				try{
					win.close();
				}catch(IOException e){
					e.printStackTrace();
					CT.trace("由于某些原因，文件输出流无法关闭");
				}
			}
		}		
	}

	@Override
	public void save() throws Exception {
		OutputStream wout = null;
		
		try{
			//输出方法
			CT.trace("正在存入文件：" + scpath.getPathName() + "\t线程描述：" + Thread.currentThread());
			
			//初始化输出流
			try {
				wout = ProjectHelper.getOutputStream(getRootProject(), scpath);
			} catch (Exception e) {
				e.printStackTrace();
				CT.trace("由于某些原因，无法成功构建工作文件输出流");
				throw e;
			}
			
			//写出文件
			try {
				getEditorKit().write(wout, getAttachObject(), 0, getAttachObject().getLength());
			} catch (Exception e) {
				e.printStackTrace();
				CT.trace("由于某些原因，文件写入失败");
				throw e;
			}
			CT.trace("文件存入完毕");
			
		}finally{
			if(wout != null){
				try{
					wout.close();
				}catch(IOException e){
					e.printStackTrace();
					CT.trace("由于某些原因，文件输出流无法关闭");
				}
			}
		}
	}
	
	@Override
	public Document getAttachObject() {
		return (Document) super.getAttachObject();
	}

}
