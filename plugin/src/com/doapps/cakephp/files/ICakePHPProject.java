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
  
  public IController getController(ICakePHPFile file);
  
  public IModel getModel(ICakePHPFile file);
  
  public IPath getAppFolder();
  
  public IPath getWebrootFolder();
  
  public IPath getJSRootFolder();
  
  public IPath getJSFolder(IController controller);
  
  public IPath getViewRootFolder();
  
  public IPath getViewFolder(IController controller);
  
  public IPath getElementsFolder();
  
  public IFile getJSFile(IController controller, ICakeAction action);
  
  public IFile getViewFile(IController controller, ICakeAction action);

  boolean isModel(IFile file);

  boolean isController(IFile file);

  boolean isView(IFile file);

  boolean isJSFile(IFile file);

  boolean isElement(IFile file);
  
}
