package com.doapps.cakephp.files.impl;

import org.eclipse.core.resources.IFile;

import com.doapps.cakephp.files.ICakePHPFile;
import com.doapps.cakephp.files.ICakePHPProject;

public abstract class CakePHPFile implements ICakePHPFile
{
  private ICakePHPProject project;
  private IFile file;
  
  public CakePHPFile(ICakePHPProject project, IFile file)
  {
    this.project = project;
    this.file = file;
  }
  
  @Override
  public ICakePHPProject getProject()
  {
    return this.project;
  }
  
  @Override
  public IFile getFile()
  {
    return this.file;
  }

  @Override
  public String getName()
  {
    return this.file.getName();
  }
}
