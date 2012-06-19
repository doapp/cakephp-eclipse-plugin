/**
 * Switch.java
 * cakephp-plugin
 * 
 * Created by jeremy on Jun 3, 2012
 * DoApp, Inc. owns and reserves all rights to the intellectual
 * property and design of the following application.
 *
 * Copyright 2012 - All rights reserved.  Created by DoApp, Inc.
 */
package com.doapps.cakephp.commands;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;

/**
 * @author jeremy
 *
 */
public class Switch extends org.eclipse.core.commands.AbstractHandler
{

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent executionEvent) throws ExecutionException
	{
		IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editorPart != null)
		{
			String title = editorPart.getTitle();
			IWorkbenchPartSite partSite = editorPart.getSite();
			IWorkbenchPage page = partSite.getPage();
			IFile file = (IFile) editorPart.getEditorInput().getAdapter(IFile.class);
			IPath path = file.getProjectRelativePath();
			System.out.println(path);
		}
		
		return null;
	}

}
