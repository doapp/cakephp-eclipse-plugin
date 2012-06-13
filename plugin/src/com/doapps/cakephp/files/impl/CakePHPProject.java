package com.doapps.cakephp.files.impl;

import java.io.ByteArrayInputStream;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.doapps.cakephp.files.ICakeAction;
import com.doapps.cakephp.files.ICakePHPFile;
import com.doapps.cakephp.files.ICakePHPProject;
import com.doapps.cakephp.files.IController;
import com.doapps.cakephp.files.IModel;

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
  
  @Override
  public IFile getFile(IPath path)
  {
    return this.project.getFile(path);
  }
  
  public ICakePHPFile getCakePHPFile(IFile file)
  {
    if (file == null)
    {
      return null;
    }
    
    if (isModel(file))
    {
      return new Model(this, file);
    }
    
    return null;
  }
  
  @Override
  public boolean isModel(IFile file)
  {
    // TODO: determine if actually a model file
    if (file == null)
    {
      return false;
    }
    IPath modelFilePath = file.getProjectRelativePath();
    int segmentCount = modelFilePath.segmentCount();
    if (segmentCount < 2)
    {
      return false;
    }
    String folderName = modelFilePath.segment(segmentCount - 2);
    String fileName = file.getName();
    boolean isModelFolder = getModelFolderRegex().matcher(folderName).matches();
    boolean isModelSuffix = getModelFileRegex().matcher(fileName).matches();
    return isModelFolder && isModelSuffix;
  }

  private Pattern getModelFolderRegex()
  {
    // TODO: get Controller folder name from preferences
    String folderName = "Model";
    return Pattern.compile(folderName, Pattern.CASE_INSENSITIVE);
  }

  private Pattern getModelFileRegex()
  {
    // TODO: get Controller folder name from preferences
    String fileRegex = ".*\\.php";
    return Pattern.compile(fileRegex, Pattern.CASE_INSENSITIVE);
  }
  
  private boolean isInFolderAndNameMatches(IFile file, Pattern folderPattern, Pattern filePattern)
  {
    if (file == null)
    {
      return false;
    }
    
    IPath filePath = file.getProjectRelativePath();
    int segmentCount = filePath.segmentCount();
    if (segmentCount < 2)
    {
      return false;
    }
    String folderName = filePath.segment(segmentCount - 2);
    String fileName = file.getName();
    boolean isFolder = getControllerFolderRegex().matcher(folderName).matches();
    boolean isFile = getControllerFileRegex().matcher(fileName).matches();
    return isFolder && isFile;
  }

  @Override
  public boolean isController(IFile file)
  {
    // TODO: determine if actually a model file
    return isInFolderAndNameMatches(file, getControllerFolderRegex(), getControllerFileRegex());
    
  }

  private Pattern getControllerFolderRegex()
  {
    // TODO: get Controller folder name from preferences
    String folderName = "Controller";
    return Pattern.compile(folderName, Pattern.CASE_INSENSITIVE);
  }

  private Pattern getControllerFileRegex()
  {
    // TODO: get Controller folder name from preferences
    String fileRegex = ".*Controller\\.php";
    return Pattern.compile(fileRegex, Pattern.CASE_INSENSITIVE);
  }

  @Override
  public ICakePHPFile getFileToOpen()
  {
    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    if (page == null)
    {
      return null;
    }
    IFile file = null;
    ISelection selection = page.getSelection();

    if (selection instanceof ITextSelection)
    {
      IEditorInput editorInput = page.getActiveEditor().getEditorInput();
      if (editorInput instanceof IFileEditorInput)
      {
        file = ((IFileEditorInput) editorInput).getFile();
      }
    }
    else if (selection instanceof IStructuredSelection && !selection.isEmpty())
    {
      Object element = ((IStructuredSelection) selection).getFirstElement();
      if (element instanceof IFile)
      {
        file = (IFile) element;
      }
      else if (element instanceof IMethod)
      {
        IResource r = ((IMethod) element).getResource();
        if (r != null)
        {
          file = (IFile) r.getAdapter(IFile.class);
        }
      }
    }
    
    if (file != null)
    {
      return getCakePHPFile(file);
    }
    return null;
  }
  
  @Override
  public boolean openNextFile()
  {
    ICakePHPFile cakePHPFile = getFileToOpen();
    IFile destinationFile = cakePHPFile.getFile();
    try
    {
      if (destinationFile != null)
      {
        // TODO: check in preferences to see if automatically create files or prompt or do nothing
        if (!destinationFile.exists())
        {
          // currently there's a bug that the file won't get created the first time after the folder is created.
          // so the user has to perform the action twice
          IPath fullPath = destinationFile.getLocation();
          if (fullPath.toFile().getParentFile().mkdirs())
          {
            // create Eclipse resource so that the create file doesn't blow chunks
            destinationFile.getProject().getFile(destinationFile.getParent().getProjectRelativePath()).refreshLocal(IFile.DEPTH_ZERO, null);
          }
          String initialContent = cakePHPFile.getInitialContents();
          byte[] initialBytes = new byte[0];
          if (initialContent != null)
          {
            initialBytes = initialContent.getBytes();
          }
          destinationFile.create(new ByteArrayInputStream(initialBytes), false, null);
        }
        if (destinationFile.exists())
        {
          IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), destinationFile);
        }
      }
      return true;
    }
    catch (CoreException e)
    {
      // TODO: how to log error messages
      String clazz = destinationFile.getName();
      System.err.println("OpenCakeFile can not open file: " + clazz);
      e.printStackTrace();
      return false;
    }
  }
  
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
  public IPath getJSFolder(IController controller)
  {
    // TODO: read from preferences...allow project specific preferences too
    String jsFolderName = controller.getName();
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
  public IFile getJSFile(IController controller, ICakeAction action)
  {
    // TODO: get js file name
    String jsName = action.getName();
    IPath jsFilePath = getJSFolder(controller).append(jsName);
    return this.project.getFile(jsFilePath);
  }

  @Override
  public IFile getViewFile(IController controller, ICakeAction action)
  {
    // TODO: get js file name
    String viewFolderName = action.getName();
    IPath viewFilePath = getViewFolder(controller).append(viewFolderName);
    return this.project.getFile(viewFilePath);
  }

  @Override
  public IController getController(ICakePHPFile file)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IModel getModel(ICakePHPFile file)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IPath getAppFolder()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IPath getViewFolder(IController controller)
  {
    // TODO Auto-generated method stub
    return null;
  }

}
