package com.doapps.cakephp.files;

import org.eclipse.core.resources.IFile;

public interface ICakePHPFile
{
  public ICakePHPProject getProject();
  
  public IFile getFile();
  
  public String getName();

  public String getInitialContents();
  
  public CakePHPFileType getCakePHPFileType();
}
