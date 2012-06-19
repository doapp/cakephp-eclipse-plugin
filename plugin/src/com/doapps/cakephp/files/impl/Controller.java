package com.doapps.cakephp.files.impl;

import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.doapps.cakephp.files.CakePHPFileType;
import com.doapps.cakephp.files.ICakePHPProject;
import com.doapps.cakephp.files.IController;

public class Controller extends CakePHPFile implements IController
{    
	public Controller(ICakePHPProject project, IFile file)
	{
		super(project, file);
	}

	@Override
	public String getInitialContents()
	{
		//TODO: run command line Cake console bake (prompt first)
		return null;
	}

	@Override
	public CakePHPFileType getCakePHPFileType()
	{
		return CakePHPFileType.CONTROLLER;
	}

	@Override
	public Pattern getNamePattern()
	{
//		String name = "testConsole";
//		MessageConsole myConsole = null;
//
//		ConsolePlugin plugin = ConsolePlugin.getDefault();
//		IConsoleManager conMan = plugin.getConsoleManager();
//		IConsole[] existing = conMan.getConsoles();
//		for (int i = 0; i < existing.length; i++){
//			if (name.equals(existing[i].getName())){
//				myConsole = (MessageConsole) existing[i];
//			}
//		}
//		if(null == myConsole){
//			//no console found, so create a new one
//			myConsole = new MessageConsole(name, null);
//			conMan.addConsoles(new IConsole[]{myConsole});
//		}
//		MessageConsoleStream out = myConsole.newMessageStream();
//		out.println("Hello from Generic console sample action");
		   
		return getProject().getCakePHPVersion().getControllerNamePattern();
	}
}
