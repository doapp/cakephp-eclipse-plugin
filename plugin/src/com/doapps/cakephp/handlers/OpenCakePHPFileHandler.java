/**
 * OpenCakeFileHandler.java
 * cakephp-plugin
 * 
 * Created by jeremy on Jun 4, 2012
 * DoApp, Inc. owns and reserves all rights to the intellectual
 * property and design of the following application.
 *
 * Copyright 2012 - All rights reserved.  Created by DoApp, Inc.
 */
package com.doapps.cakephp.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.doapps.cakephp.files.ICakePHPProject;
import com.doapps.cakephp.files.impl.CakePHPProject;

/**
 * @author jeremy
 * 
 */
public class OpenCakePHPFileHandler extends AbstractHandler
{
  private IProject getProjectForSelection()
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
    return null;
  }
  
  private ICakePHPProject getCakePHPProject()
  {
    return new CakePHPProject(getProjectForSelection());
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
   * ExecutionEvent)
   */
  @Override
  public Object execute(ExecutionEvent executionEvent) throws ExecutionException
  {
    ICakePHPProject project = getCakePHPProject();
    if (project == null)
    {
      return null;
    }
    project.openNextFile();
    
    // TODO: what should I return here?
    return null;
  }

}
