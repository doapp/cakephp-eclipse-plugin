package com.doapps.cakephp.files.impl;

import org.eclipse.core.resources.IFile;

import com.doapps.cakephp.files.IModel;

public class Model extends CakePHPFile implements IModel
{

  public Model(CakePHPProject project, IFile file)
  {
    super(project, file);
  }
  @Override
  public String getInitialContents()
  {
    // TODO Auto-generated method stub
    return null;
  }

}
