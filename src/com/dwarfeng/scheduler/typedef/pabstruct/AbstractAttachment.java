package com.dwarfeng.scheduler.typedef.pabstruct;

import java.io.InputStream;
import java.io.OutputStream;

import com.dwarfeng.dwarffunction.io.CT;
import com.dwarfeng.scheduler.io.ProjectIoHelper;
import com.dwarfeng.scheduler.io.Scpath;
import com.dwarfeng.scheduler.typedef.exception.AttachmentException;

/**
 * 抽象附件。
 * <p> 最大化的实现了附件的方法。
 * XXX 可以尝试最大化的封装load和save方法。
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractAttachment<T> extends AbstractObjectOutProjectTree implements Attachment<T>{

	/**路径*/
	protected final Scpath scpath;
	
	/**
	 * 生成一个默认的抽象附件。
	 * @param scpath 抽象附件的工作路径，不能为null。
	 * @throws NullPointerException 当工作路径为null时。
	 */
	public AbstractAttachment(Scpath scpath) {
		if(scpath == null) throw new NullPointerException("Scpath can't be null");
		this.scpath = scpath;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.Scpathable#getScpath()
	 */
	@Override
	public Scpath getScpath() {
		return scpath;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.Attachment#load()
	 */
	@Override
	public T load() throws AttachmentException{
		
		InputStream in = null;
		T obj = null;
		
		try{
			//向控制台打印信息
			CT.trace("正在读取文件 ：" + getScpath().getPathName() + "\t线程描述：" + Thread.currentThread());
			
			//构建输入流
			in = ProjectIoHelper.getInputStream(getRootProject(), getScpath());
			
			//读取文件
			obj = loadAttachment(in);
			
			//输出成功信息
			CT.trace("文件读取成功：" + scpath.getPathName() + "\t线程描述：" + Thread.currentThread());
			
			//返回读取的对象
			return obj;
			
		}catch(Exception e){
			
			throw new AttachmentException(obj,getScpath(),"Attachement load failed", e);
			
		}finally{
			//关闭输入流
			if(in != null){
				try{
					in.close();
				}catch(Exception e){
					e.printStackTrace();
					CT.trace("由于某些原因，输入流没正常关闭");
				}
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.scheduler.typedef.abstruct.Attachment#save()
	 */
	@Override
	public void save(T obj) throws AttachmentException{
		
		if(obj == null) throw new NullPointerException("Obj can't be null");
		
		OutputStream out = null;
		
		try{
			
			//向控制台打印信息
			CT.trace("正在存入文件：" + scpath.getPathName() + "\t线程描述：" + Thread.currentThread());
			
			//构建输出流
			out = ProjectIoHelper.getOutputStream(getRootProject(), getScpath(), true);
			
			//利用输出流进行输出
			saveAttachment(out,obj);
			
			//输出成功信息
			CT.trace("文件存入成功：" + scpath.getPathName() + "\t线程描述：" + Thread.currentThread());
			
		}catch(Exception e){
			
			throw new AttachmentException(obj,getScpath(),"Attachement save failed", e);
			
		}finally{
			if(out != null) {
				try{
					out.close();
				}catch(Exception e){
					e.printStackTrace();
					CT.trace("由于某些原因，输出流没有正常关闭");
				}
			}
		}
	}
	
	/**
	 * 通过现有的输入流读取并生成指定的对象的方法。
	 * @param in 指定的输入流。
	 * @return 读取并生成的对象。
	 * @throws Exception 读取时发生的异常。
	 */
	protected abstract T loadAttachment(InputStream in) throws Exception;
	
	/**
	 * 通过现有的输出流存储指定对象的方法。
	 * <p> 该方法可以保证入口参数<code>T obj</code>不为null，因此重写此方法时可以不考虑
	 * 对<code>obj</code>的判定情况。
	 * @param out 指定的输出流。
	 * @param obj 指定的存储对象。
	 * @throws Exception 保存时发生异常。
	 */
	protected abstract void saveAttachment(OutputStream out,T obj) throws Exception;
}
