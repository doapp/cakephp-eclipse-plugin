package com.doapps.cakephp.files;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;

public interface ICakePHPFile
{
  public ICakePHPProject getProject();
  
  public IFile getFile(IPath path);
  
  public IController getController();
  
  public IModel getModel();
  
  public IPath getAppFolder();
  
  public IPath getWebrootFolder();
  
  public IPath getJSRootFolder();
  
  public IPath getJSFolder();
  
  public IPath getViewRootFolder();
  
  public IPath getViewFolder();
  
  public IPath getElementsFolder();
  
  public IFile getJSFile(ICakeAction action);
  
  public IFile getViewFile(ICakeAction action);
  
}
