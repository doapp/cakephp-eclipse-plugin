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
  
  public IModel getModel(ICakePHPFile file);
    
  public IPath getViewFolder();
  
  public IPath getViewFolder(IController controller);
  
  public IPath getElementsFolder();
  
  public IController getController(ICakePHPFile file);  
  
  public IPath getWebrootFolder();
  
  public IPath getJSFolder();
  
  public IPath getJSFolder(IController controller);
  
  public IView getView(ICakePHPFile file, String selectedText);

  public IJSFile getJSFile(IView view);  
  
}
