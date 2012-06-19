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

import com.doapps.cakephp.files.ICakePHPProject;
import com.doapps.cakephp.files.impl.CakePHPProject;
import com.doapps.cakephp.util.FileUtils;

/**
 * @author jeremy
 * 
 */
public class OpenCakePHPFileHandler extends AbstractHandler
{ 
  private ICakePHPProject getCakePHPProject()
  {
    return new CakePHPProject(FileUtils.getProjectForSelection());
//	  return new CakePHPProject(FileUtils.getCurrentProjectPath());
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
