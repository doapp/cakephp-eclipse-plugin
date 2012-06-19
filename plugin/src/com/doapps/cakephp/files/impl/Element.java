package com.doapps.cakephp.files.impl;

import org.eclipse.core.resources.IFile;

import com.doapps.cakephp.files.CakePHPFileType;
import com.doapps.cakephp.files.ICakePHPProject;
import com.doapps.cakephp.files.IElement;

public class Element extends CakePHPFile implements IElement
{
  public Element(ICakePHPProject project, IFile file)
  {
    super(project, file);
  }

  @Override
  public String getInitialContents()
  {
    return null;
  }
  
  @Override
  public CakePHPFileType getCakePHPFileType()
  {
    return CakePHPFileType.ELEMENT;
  }

}
