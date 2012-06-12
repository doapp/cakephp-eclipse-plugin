package com.doapps.cakephp.files.impl;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;

import com.doapps.cakephp.files.ICakeAction;
import com.doapps.cakephp.files.ICakePHPFile;
import com.doapps.cakephp.files.ICakePHPProject;

public abstract class CakePHPFile implements ICakePHPFile
{
  private ICakePHPProject project;
  
  @Override
  public IPath getWebrootFolder()
  {
    // TODO: read webroot from preferences...allow project specific preferences too
    String webroot = "webroot";
    return getAppFolder().append(webroot);
  }

  @Override
  public IPath getJSRootFolder()
  {
    // TODO: read from preferences...allow project specific preferences too
    String jsRootName = "js";
    return getWebrootFolder().append(jsRootName);
  }
  
  @Override
  public IPath getJSFolder()
  {
    // TODO: read from preferences...allow project specific preferences too
    String jsFolderName = "View";
    return getJSRootFolder().append(jsFolderName);
  }

  @Override
  public IPath getViewRootFolder()
  {
    // TODO: read from preferences...allow project specific preferences too
    String viewRoot = "View";
    return getAppFolder().append(viewRoot);
  }

  @Override
  public IPath getElementsFolder()
  {
    // TODO: read from preferences...allow project specific preferences too
    String elementsFolderName = "Elements";
    return getViewRootFolder().append(elementsFolderName);
  }

  @Override
  public IFile getJSFile(ICakeAction action)
  {
    // TODO: get js file name
    String jsFolderName = action.getName();
    IPath jsFilePath = getJSFolder().append(jsFolderName);
    return getFile(jsFilePath);
  }

  @Override
  public IFile getViewFile(ICakeAction action)
  {
    // TODO: get js file name
    String viewFolderName = action.getName();
    IPath viewFilePath = getViewFolder().append(viewFolderName);
    return getFile(viewFilePath);
  }
  
  @Override
  public ICakePHPProject getProject()
  {
    return this.project;
  }
  
  @Override
  public IFile getFile(IPath path)
  {
    return getProject().getProject().getFile(path);
  }

}
