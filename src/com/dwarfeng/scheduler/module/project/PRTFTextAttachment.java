package com.dwarfeng.scheduler.module.project;

import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import com.dwarfeng.scheduler.module.Scpath;

/**
 * XXX ���֮����������HTML�ı��Ļ��������Ϊһ�������ࡣ
 * ����ʽ�ı�������
 * @author DwArFeng
 * @since 1.8
 */
class PRTFTextAttachment extends PTextAttachment<StyledDocument,RTFEditorKit>{

	/**
	 * ����һ�����ı�������
	 * @param scpath ָ���Ĺ���·����
	 */
	public PRTFTextAttachment(Scpath scpath) {
		super(scpath,new RTFEditorKit());
	}
	
	@Override
	public StyledDocument createDefaultObject() {
		return (StyledDocument) getEditorKit().createDefaultDocument();
	}

}
