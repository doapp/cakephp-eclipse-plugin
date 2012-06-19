package com.doapps.cakephp.files.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import com.doapps.cakephp.util.FileUtils;

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
		return getProject().getCakePHPVersion().getControllerNamePattern();
	}
}
