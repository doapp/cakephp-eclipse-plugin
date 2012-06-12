package com.doapps.cakephp.files.impl;

import org.eclipse.core.resources.IProject;

import com.doapps.cakephp.files.ICakePHPProject;

public class CakePHPProject implements ICakePHPProject
{
  private IProject project;
  
  

  public CakePHPProject(IProject project)
  {
    this.project = project;
  }

  @Override
  public IProject getProject()
  {
    return this.project;
  }

}
