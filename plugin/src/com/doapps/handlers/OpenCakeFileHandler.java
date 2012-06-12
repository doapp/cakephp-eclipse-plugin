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
package com.doapps.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.xicabin.cakephp.util.Inflector;

import com.doapps.cakephp.util.CakePHPHelper;

/**
 * @author jeremy
 * 
 */
public class OpenCakeFileHandler extends AbstractHandler
{

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
   * ExecutionEvent)
   */
  @Override
  public Object execute(ExecutionEvent executionEvent) throws ExecutionException
  {
    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    String selectedText = CakePHPHelper.getSelectedText(page);
    IFile selectedFile = CakePHPHelper.getSelectedFile(page);
    byte[] initialContents = new byte[0];
    if (selectedFile != null)
    {
      IFile destinationFile = null;

      if (CakePHPHelper.isModel(selectedFile))
      {
        destinationFile = CakePHPHelper.getControllerFromModel(selectedFile);
        initialContents = CakePHPHelper.getInitialControllerContents(selectedFile);
      }
      else if (CakePHPHelper.isController(selectedFile))
      {
        if ((selectedText != null) && (selectedText.length() > 0))
        {
          destinationFile = CakePHPHelper.getViewFromAction(selectedFile, selectedText);
        }
        if (destinationFile == null)
        {
          destinationFile = CakePHPHelper.getModelFromController(selectedFile);
        }
      }
      else if (CakePHPHelper.isView(selectedFile))
      {
        destinationFile = CakePHPHelper.getJSFileFromView(selectedFile);
        initialContents = CakePHPHelper.getInitialJSContents();
      }
      else if (CakePHPHelper.isJSFile(selectedFile))
      {
        destinationFile = CakePHPHelper.getViewFromJSFile(selectedFile);
      }
      CakePHPHelper.openFile(page, destinationFile, initialContents);
    }
    return null;
  }

}
