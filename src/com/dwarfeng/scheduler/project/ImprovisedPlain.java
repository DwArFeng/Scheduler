package com.dwarfeng.scheduler.project;

import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import com.dwarfeng.scheduler.gui.JProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.AbstractObjectInProjectTree;
import com.dwarfeng.scheduler.typedef.abstruct.ObjectInProject;
import com.dwarfeng.scheduler.typedef.desint.AbstractEditor;
import com.dwarfeng.scheduler.typedef.desint.Editable;
import com.dwarfeng.scheduler.typedef.funcint.Deleteable;
import com.dwarfeng.scheduler.typedef.funcint.Moveable;
import com.dwarfeng.scheduler.typedef.funcint.PopupInTree;

public class ImprovisedPlain extends AbstractObjectInProjectTree 
implements Editable, Deleteable, Moveable,PopupInTree {

	public ImprovisedPlain(boolean allowsChildren) {
		super(allowsChildren);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<ObjectInProject> getOtherObjectInProjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void renderLabel(JLabel label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getConfirmWord() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEditorTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractEditor getEditor() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void firedEditorClose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JPopupMenu getJPopupMenu(JProjectTree jProjectTree) {
		// TODO Auto-generated method stub
		return null;
	}

}
