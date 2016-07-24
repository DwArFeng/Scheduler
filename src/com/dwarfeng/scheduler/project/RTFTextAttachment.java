package com.dwarfeng.scheduler.project;

import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import com.dwarfeng.scheduler.core.module.Scpath;

/**
 * XXX ���֮����������HTML�ı��Ļ��������Ϊһ�������ࡣ
 * ����ʽ�ı�������
 * @author DwArFeng
 * @since 1.8
 */
public class RTFTextAttachment extends TextAttachment<StyledDocument,RTFEditorKit>{

	/**
	 * ����һ�����ı�������
	 * @param scpath ָ���Ĺ���·����
	 */
	public RTFTextAttachment(Scpath scpath) {
		super(scpath,new RTFEditorKit());
	}
	
	@Override
	public StyledDocument createDefaultObject() {
		return (StyledDocument) getEditorKit().createDefaultDocument();
	}

}
