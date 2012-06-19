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
package com.doapps.cakephp.files;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

public interface ICakePHPProject
{
  public IProject getProject();
  
  public IFile getFile(IPath path);
  
  public ICakePHPFile getCakePHPFile(IFile file);

  public boolean openNextFile();
  
  public ICakePHPFile getFileToOpen();
      
  public IPath getAppFolder();
  
  public IPath getModelFolder();
  
  public IPath getViewFolder();
  
  public IPath getViewFolder(IController controller);
  
  public IPath getElementsFolder();
  
  public IPath getWebrootFolder();
  
  public IPath getJSFolder();
  
  public IPath getJSFolder(IController controller);
  
  public CakeVersion getCakePHPVersion();

  public IController getControllerForView(IView file);
  
  public IController getControllerForJSFile(IJSFile file);

  public boolean openModelFile();

  public boolean openControllerFile();
  
  public ICakePHPFile getCurrentlySelectedFile();

}
