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
public class OpenCakePHPModelHandler extends AbstractHandler
{ 
  private ICakePHPProject getCakePHPProject()
  {
    return new CakePHPProject(FileUtils.getProjectForSelection());
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
    project.openModelFile();
    
    // TODO: what should I return here?
    return null;
  }

}
