package com.doapps.cakephp.files.impl;

import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;

import com.doapps.cakephp.files.CakePHPFileType;
import com.doapps.cakephp.files.ICakePHPProject;
import com.doapps.cakephp.files.IController;

public class Controller extends CakePHPFile implements IController
{
  private static final Pattern NAME_PATTERN = Pattern.compile("(.*)Controller\\..*");
  
  public Controller(ICakePHPProject project, IFile file)
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
    return CakePHPFileType.CONTROLLER;
  }
  
  @Override
  public Pattern getNamePattern()
  {
    // TODO: get from version class
    return NAME_PATTERN;
    //return Pattern.compile("\\(.*\\)_controller\\..*");
  }
}
