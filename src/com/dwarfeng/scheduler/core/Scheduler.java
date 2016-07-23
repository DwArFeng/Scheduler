package com.dwarfeng.scheduler.core;

import com.dwarfeng.dwarffunction.program.MvcProgram;

/**
 * 计划管理程序。
 * <p> 该程序的主要作用是解决计划的规划问题以及笔记整理的问题。该程序主要围绕着两个方向进行编写。
 * <br>&nbsp;&nbsp; 1.笔记记录以及整理工作，此部分类似于现有的有道云笔记等笔记管理程序，在其基础上再添加某些功能。
 * <br>&nbsp;&nbsp; 2.任务管理。通过想程序中输入指定的计划任务，程序根据系统时间来对这些任务进行管理。
 * <p> 程序结构采用MVC结构，并且尽可能的做到相互分离。
 * <br> <code>Scheduler</code>本身拥有一个后台维护器，任何需要调用后台的方法都可以托管到这个维护器上。
 * @author DwArFeng
 * @since 1.8
 */
public final class Scheduler extends MvcProgram<ProgramControlPort, ModuleControlPort, ViewControlPort, ControlPort>{

}
