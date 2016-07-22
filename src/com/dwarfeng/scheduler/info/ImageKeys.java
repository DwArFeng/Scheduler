package com.dwarfeng.scheduler.info;

import com.dwarfeng.dwarffunction.interfaces.Nameable;

/**
 * ͼƬ�ֶΡ�
 * @author DwArFeng
 * @since 1.8
 */
public enum ImageKeys implements Nameable{
	;
	
	private final String name;
	
	private ImageKeys(String name) {
		this.name = name;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dwarffunction.interfaces.Nameable#getName()
	 */
	@Override
	public String getName() {
		return this.name();
	}

}
