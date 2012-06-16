package com.doapps.cakephp.files;

import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;

public interface ICakePHPFile
{
  public ICakePHPProject getProject();
  
  public IFile getFile();
  
  public String getName();

  public String getInitialContents();
  
//  public Pattern getNamePattern();
  
  public CakePHPFileType getCakePHPFileType();
}
