package com.doapps.cakephp.files.impl;

import org.eclipse.core.resources.IFile;

import com.doapps.cakephp.files.CakePHPFileType;
import com.doapps.cakephp.files.ICakePHPProject;
import com.doapps.cakephp.files.IModel;

public class Model extends CakePHPFile implements IModel
{

  public Model(ICakePHPProject project, IFile file)
  {
    super(project, file);
  }
  @Override
  public String getInitialContents()
  {
	//TODO: run command line Cake console bake (prompt first)
    return null;
  }

  @Override
  public CakePHPFileType getCakePHPFileType()
  {
    return CakePHPFileType.MODEL;
  }
}
