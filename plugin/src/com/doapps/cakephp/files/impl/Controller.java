package com.doapps.cakephp.files.impl;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;

import com.doapps.cakephp.files.IController;
import com.doapps.cakephp.files.IModel;

public class Controller extends CakePHPFile implements IController
{
  private IFile file;
  
  @Override
  public IController getController()
  {
    return this;
  }

  @Override
  public IModel getModel()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IPath getAppFolder()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IPath getViewFolder()
  {
    // TODO determine view name
    String viewFolderName = this.file.getName();
    return getViewRootFolder().append(viewFolderName);
  }

  @Override
  public IFile getFile()
  {
    return this.file;
  }

}
