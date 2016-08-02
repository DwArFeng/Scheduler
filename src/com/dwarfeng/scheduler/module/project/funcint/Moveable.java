package com.dwarfeng.scheduler.module.project.funcint;

import com.dwarfeng.scheduler.module.project.abstruct.ProjectTreeNode;

/**
 * 可移动接口。
 * <p> 实现该接口意味着可以在主界面工程树中快捷地在同级之中移动位置。
 * <br> 实现移动位置功能并不一定要实现这个接口，除此之外，还可以用剪切粘贴的形式进行移动。
 * 然而该实现该接口可以快捷的使用快捷键进行。
 * <br> 该接口是标记接口，其中不实现任何方法。
 * @author DwArFeng
 * @since 1.8
 */
public interface Moveable extends ProjectTreeNode {}
