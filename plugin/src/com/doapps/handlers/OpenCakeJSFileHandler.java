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
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.doapps.cakephp.util.CakePHPHelper;

/**
 * @author jeremy
 * 
 */
public class OpenCakeJSFileHandler extends AbstractHandler
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
    IFile selectedFile = CakePHPHelper.getSelectedFile(page);
    if (selectedFile != null)
    {
      IFile destinationFile = null;

      if (CakePHPHelper.isModel(selectedFile))
      {
        // TODO: implement pop-up
      }
      if (CakePHPHelper.isController(selectedFile))
      {
        destinationFile = CakePHPHelper.getModelFromController(selectedFile);
      }
      if (CakePHPHelper.isView(selectedFile))
      {
        destinationFile = CakePHPHelper.getJSFileFromView(selectedFile);
      }
      CakePHPHelper.openFile(page, destinationFile, new byte[0]);
    }
    return null;
  }

}
