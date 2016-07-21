package com.dwarfeng.scheduler.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.dwarfeng.dwarffunction.io.CT;
import com.dwarfeng.dwarffunction.io.FileFunction;
import com.dwarfeng.scheduler.core.Scheduler;
import com.dwarfeng.scheduler.info.AppearanceInfo;
import com.dwarfeng.scheduler.info.FileInfo;

/**
 * 有关程序外观和配置的工厂类。
 * @author DwArFeng
 * @since 1.8
 */
public final class ConfigHelper {

	/**
	 * 创建默认的程序外观。
	 * @return 默认的程序外观。
	 */
	public static AppearanceInfo createDefaultAppearanceInfo(){
		return new AppearanceInfo.Productor().product();
	}
	
	/**
	 * 读取程序外观。
	 * <p> 该方法会在Scheduler中获取配置文件夹的路径，并在其中寻找
	 * "appearance.xml"后进行读取，如果读取成功，返回读取后的外观，如果读取失败则返回默认的外观。
	 * @return 读取后的或者是默认的程序外观。
	 */
	public static AppearanceInfo loadAppearanceInfo(){
		try{
			int frameExtension;
			int frameWidth;
			int frameHeight;
			boolean consoleVisible;
			boolean projectTreeVisible;
			boolean paramVisible;
			boolean functionPanelVisible;
			int consoleHeight;
			int projectTreeWidth;
			int paramWidth;
			
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(Scheduler.getInstance().getConfigPath() + "appearance.xml"));
			
			Element frame = (Element) document.selectNodes("/appearance/frame").get(0);
			frameExtension = new Integer(frame.attribute("extension").getValue()).intValue();
			frameWidth = new Integer(frame.attribute("width").getValue()).intValue();
			frameHeight = new Integer(frame.attribute("height").getValue()).intValue();
			
			Element console = (Element) document.selectNodes("/appearance/console").get(0);
			consoleVisible = new Boolean(console.attribute("visible").getValue()).booleanValue();
			consoleHeight = new Integer(console.attribute("height").getValue()).intValue();
			
			Element pTree = (Element) document.selectNodes("/appearance/pTree").get(0);
			projectTreeVisible = new Boolean(pTree.attribute("visible").getValue()).booleanValue();
			projectTreeWidth = new Integer(pTree.attribute("width").getValue()).intValue();
			
			Element param = (Element) document.selectNodes("/appearance/param").get(0);
			paramVisible = new Boolean(param.attribute("visible").getValue()).booleanValue();
			paramWidth = new Integer(param.attribute("width").getValue()).intValue();
			
			Element fPanel = (Element) document.selectNodes("/appearance/fPanel").get(0);
			functionPanelVisible = new Boolean(fPanel.attribute("visible").getValue()).booleanValue();
			
			return new AppearanceInfo
					.Productor()
					.frameExtension(frameExtension)
					.frameWidth(frameWidth)
					.frameHeight(frameHeight)
					.consoleVisible(consoleVisible)
					.projectTreeVisible(projectTreeVisible)
					.paramVisible(paramVisible)
					.functionPanelVisible(functionPanelVisible)
					.consoleHeight(consoleHeight)
					.projectTreeWidth(projectTreeWidth)
					.paramWidth(paramWidth)
					.product();
			
		}catch(Exception e){
			e.printStackTrace();
			return createDefaultAppearanceInfo();
		}
	}

	/**
	 * 保存程序外观。
	 * <p> 该方法在Scheduler中获取配置文件夹的路径，并把外观信息保存在
	 * appearance.xml中。
	 * @param appearanceInfo 外观信息。
	 */
	public static void saveApperanceInfo(AppearanceInfo appearanceInfo){
		Document document = DocumentHelper.createDocument();
		
		Element appearance = DocumentHelper.createElement("appearance");
		document.add(appearance);
		
		Element frame = DocumentHelper.createElement("frame");
		frame.addAttribute("extension", appearanceInfo.getFrameExtension() + "");
		frame.addAttribute("width", appearanceInfo.getFrameWidth() + "");
		frame.addAttribute("height", appearanceInfo.getFrameHeight() + "");
		appearance.add(frame);
		
		Element console = DocumentHelper.createElement("console");
		console.addAttribute("visible", appearanceInfo.isConsoleVisible() + "");
		console.addAttribute("height", appearanceInfo.getConsoleHeight() + "");
		appearance.add(console);
		
		Element projectTree = DocumentHelper.createElement("pTree");
		projectTree.addAttribute("visible", appearanceInfo.isProjectTreeVisible() + "");
		projectTree.addAttribute("width", appearanceInfo.getProjectTreeWidth() + "");
		appearance.add(projectTree);
		
		Element param = DocumentHelper.createElement("param");
		param.addAttribute("visible", appearanceInfo.isParamVisible() + "");
		param.addAttribute("width", appearanceInfo.getParamWidth() + "");
		appearance.add(param);
		
		Element functionPanel = DocumentHelper.createElement("fPanel");
		functionPanel.addAttribute("visible", appearanceInfo.isFunctionPanelVisible() + "");
		appearance.add(functionPanel);
		
		document.setXMLEncoding("UTF-8");
		
		FileOutputStream fout = null;
		try{
			File file = new File(Scheduler.getInstance().getConfigPath() + "appearance.xml");
			FileFunction.createFileIfNotExists(file);
			fout = new FileOutputStream(file);
			XMLWriter writer = new XMLWriter(fout,OutputFormat.createPrettyPrint());
			writer.write(document);
		}catch(Exception e){
			e.printStackTrace();
			CT.trace("由于某些原因，外观方案没有被保存");
		}finally{
			if(fout != null){
				try{
					fout.close();
				}catch(IOException e){
					e.printStackTrace();
					CT.trace("由于某些原因，文件输出流无法正常关闭");
				}
			}
		}
	}

	/**
	 * 创建默认的文件信息。
	 * @return 默认的文件信息。
	 */
	public static FileInfo createDefaultFileInfo(){
		return new FileInfo.Productor().product();
	}
	
	public static FileInfo loadFileInfo(){
		SAXReader reader = new SAXReader();
		try {
			//Infomation的各种参数
			String lastProjectPath;
			
			//读取文件
			Document document = reader.read(new File(Scheduler.getInstance().getConfigPath() + "files.xml"));
			
			//读取最后被打开的文档的信息
			Element lastProj = (Element) document.selectNodes("/info/lastProj").get(0);
			lastProjectPath = lastProj.attribute("value").getValue();
			
			//构造，返回Information
			return new FileInfo(lastProjectPath);
		} catch (DocumentException e) {
			e.printStackTrace();
			CT.trace("由于某些原因，信息文件没有被成功的读取");
			return createDefaultFileInfo();
		}
	}
	
	public static void saveFileInfo(FileInfo fileInfo){
		//创建文档
		Document document = DocumentHelper.createDocument();
		//添加根元素
		Element info = DocumentHelper.createElement("info");
		document.add(info);
		
		//添加lastProject子元素，以及其属性字段
		Element lastProject = DocumentHelper.createElement("lastProj");
		lastProject.addAttribute("value", fileInfo.getLastProjectPath() == null ? "" : fileInfo.getLastProjectPath());
		info.add(lastProject);
		
		//写入工作路径
		File infoFile = new File(Scheduler.getInstance().getConfigPath() + "files.xml");
		FileOutputStream fout = null;
		try{
			FileFunction.createFileIfNotExists(infoFile);
			fout = new FileOutputStream(infoFile);
			XMLWriter writer = new XMLWriter(fout, OutputFormat.createPrettyPrint());
			writer.write(document);
		}catch(Exception e){
			e.printStackTrace();
			CT.trace("由于某些原因，程序信息文件无法正确写入");
		}finally{
			if(fout != null){
				try{
					fout.close();
				}catch(IOException e){
					e.printStackTrace();
					CT.trace("由于某些原因，文件输出流没有关闭");
				}
			}
		}
	}
	
	//不可实例化
	private ConfigHelper() {
		throw new IllegalStateException("CfgHelper can't be initialized");
	}

}
