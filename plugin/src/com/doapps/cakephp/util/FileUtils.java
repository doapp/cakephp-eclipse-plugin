/**
 *
 * CakePHP Eclipse Plugin
 * Copyright 2012, DoApp, Inc. (http://www.doapps.com)
 *
 * Licensed under The MIT License
 * Redistributions of files must retain the above copyright notice.
 *
 * @copyright     Copyright 2012, DoApp, Inc. (http://www.doapps.com)
 * @link          https://github.com/doapp/cakephp-eclipse-plugin
 * @license       MIT License (http://www.opensource.org/licenses/mit-license.php)
 */
package com.doapps.cakephp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.doapps.cakephp.Activator;
import com.doapps.cakephp.preferences.PreferenceConstants;

/**
 * @author ryan 
 *
 */
public class FileUtils {

	public static String getSelectedText()
	{
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if (null == page)
		{
			return null;
		}
		String text = null;
		ISelection selection = page.getSelection();
		if (selection instanceof ITextSelection)
		{
			text = ((ITextSelection) selection).getText();
		}
		else if (selection instanceof IStructuredSelection)
		{
			Object element = ((IStructuredSelection) selection).getFirstElement();
			if (element instanceof IMethod)
			{
				String fullyQualifiedName = ((IMethod) element).getFullyQualifiedName();
				String[] split = fullyQualifiedName.split("\\$");
				if (split.length > 1)
				{
					return split[1];
				}
			}
			return null;
		}
		return text;
	}

	static public IProject getProjectForSelection()
	{
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		ISelection selection = page.getSelection();
		if (selection != null)
		{
			if (selection instanceof ITextSelection)
			{
				IEditorInput editorInput = page.getActiveEditor().getEditorInput();
				if (editorInput instanceof IFileEditorInput)
				{
					return ((IFileEditorInput) editorInput).getFile().getProject();
				}
			}
			else if (selection instanceof IStructuredSelection && !selection.isEmpty())
			{
				Object element = ((IStructuredSelection) selection).getFirstElement();
				if (element instanceof IResource)
				{
					return ((IResource) element).getProject();
				}
			}
			// TODO: are there other types of selections
		}

		IFile file = getSelectedFile();
		if (file != null)
		{
			return file.getProject();
		}
		return null;
	}

	/**
	 * Get the full path of the current project
	 * 
	 * @return full path string or "" if not found
	 */
	static public IPath getCurrentProjectPath() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		//First check: editor is 'active'  
		IWorkbenchPart workbenchPart = window.getActivePage().getActivePart(); 
		IFile file = (IFile) workbenchPart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);		
		IPath projectPath = file.getProject().getLocation();

		//Second check: editor not active, ctrl+alt (right click prop) on project
		if(null == projectPath) {
			if (window != null)
			{
				IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
				Object firstElement = selection.getFirstElement();
				if (firstElement instanceof IAdaptable)
				{
					IProject project = (IProject)((IAdaptable)firstElement).getAdapter(IProject.class);
					projectPath = project.getLocation();	            
				}
			}
		}

		return projectPath;
	}

	static public Boolean isProjectSpecificSettingsEnabled(IProject project) {
		Boolean projectSpecificSettingsEnabled = false;
		try {
			//First check if they have a proj specific app dir
			String enableProjSpecific = FileUtils.getProjectProperty(project, PreferenceConstants.P_ENABLE_PROJECT_SPECIFIC_SETTINGS);
			if(null != enableProjSpecific) {
				projectSpecificSettingsEnabled = Boolean.parseBoolean(enableProjSpecific);			
			}		
		} catch (CoreException e) {
			//Property not found, so not using project specific settings
		}
		return projectSpecificSettingsEnabled;
	}

	/**
	 * Get project specific property (if enabled) fall back to workspace pref
	 * 
	 * @param key they key must be the same in the properties store and the preference store
	 * @return null|String
	 */
	static public String getProjectPropertyOrWorkspacePref(IProject project, String key) {
		if(!isProjectSpecificSettingsEnabled(project)) return Activator.getDefault().getPreferenceStore().getString(key);
		else {
			try {
				return getProjectProperty(project, key);
			} catch (CoreException e) {
				return null;
			}
		}
	}

	/**
	 * Get a property from the persistent property store
	 * 
	 * @param key
	 * @return null|String value
	 * @throws CoreException
	 */
	static public String getProjectProperty(IProject project, String key) throws CoreException {
		// shouldn't this property have a name?
		return project.getPersistentProperty(new QualifiedName("", key));
		//		String value = null;
		//		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		//	    if (window != null)
		//	    {
		//	        IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
		//	        Object firstElement = selection.getFirstElement();
		//	        if (firstElement instanceof IResource)
		//	        {
		//	        	value = ((IResource) firstElement).getPersistentProperty(new QualifiedName("", key));
		//	        }
		//	    }
		//	    return value;
	}

	public static IFile getSelectedFile()
	{
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if (page == null)
		{
			return null;
		}
		IFile file = null;
		ISelection selection = page.getSelection();

		if (selection instanceof ITextSelection)
		{
			IEditorInput editorInput = page.getActiveEditor().getEditorInput();
			if (editorInput instanceof IFileEditorInput)
			{
				file = ((IFileEditorInput) editorInput).getFile();
			}
		}
		else if (selection instanceof IStructuredSelection && !selection.isEmpty())
		{
			Object element = ((IStructuredSelection) selection).getFirstElement();
			if (element instanceof IFile)
			{
				file = (IFile) element;
			}
			else if (element instanceof IMethod)
			{
				IResource r = ((IMethod) element).getResource();
				if (r != null)
				{
					file = (IFile) r.getAdapter(IFile.class);
				}
			}
		}
		return file;
	}
	
	//String[] command
	public static int runCommandInConsole() {
		String[] command = {"/bin/bash", "-c", "/home/ryan/git/mln-webapp/webapp/app/Console/cake bake"};
		String name = "testConsole";
		MessageConsole myConsole = null;

		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++){
			if (name.equals(existing[i].getName())){
				myConsole = (MessageConsole) existing[i];
			}
		}
		if(null == myConsole){
			//no console found, so create a new one
			myConsole = new MessageConsole(name, null);
			conMan.addConsoles(new IConsole[]{myConsole});
		}
		MessageConsoleStream out = myConsole.newMessageStream();

		Process process = null;
		try {
			ProcessBuilder pb = new ProcessBuilder(command);
			pb.redirectErrorStream(true);
			pb.directory(new File("/home/ryan/git/mln-webapp/webapp/app/"));
			process = pb.start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;

			while ((line = br.readLine()) != null) {
				out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String executeCommand(String command, boolean waitForResponse) {

		String response = "";

		ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", command);
		pb.redirectErrorStream(true);

		try {
			Process shell = pb.start();

			if (waitForResponse) {

				// To capture output from the shell
				InputStream shellIn = shell.getInputStream();

				// Wait for the shell to finish and get the return code
				int shellExitStatus = shell.waitFor();
				System.out.println("Exit status" + shellExitStatus);

				response = convertStreamToStr(shellIn);

				shellIn.close();
			}

		}

		catch (IOException e) {
			System.out.println("Error occured while executing Linux command. Error Description: "
					+ e.getMessage());
		}

		catch (InterruptedException e) {
			System.out.println("Error occured while executing Linux command. Error Description: "
					+ e.getMessage());
		}

		return response;
	}

	/*
	 * To convert the InputStream to String we use the Reader.read(char[]
	 * buffer) method. We iterate until the Reader return -1 which means
	 * there's no more data to read. We use the StringWriter class to
	 * produce the string.
	 */

	public static String convertStreamToStr(InputStream is) throws IOException {

		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		}
		else {
			return "";
		}
	}
	
}
