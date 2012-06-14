package com.doapps.cakephp.files.impl;

import org.eclipse.core.resources.IFile;

import com.doapps.cakephp.files.CakePHPFileType;
import com.doapps.cakephp.files.ICakePHPProject;
import com.doapps.cakephp.files.IController;

public class View extends CakePHPFile implements IController
{
  public View(ICakePHPProject project, IFile file)
  {
    super(project, file);
  }

  @Override
  public String getInitialContents()
  {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public CakePHPFileType getCakePHPFileType()
  {
    return CakePHPFileType.VIEW;
  }

}
