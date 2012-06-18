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

import com.doapps.cakephp.Activator;
import com.doapps.cakephp.files.CakeVersion;
import com.doapps.cakephp.files.ICakeAction;
import com.doapps.cakephp.files.ICakePHPFile;
import com.doapps.cakephp.files.ICakePHPProject;
import com.doapps.cakephp.files.IController;
import com.doapps.cakephp.files.IModel;
import com.doapps.cakephp.preferences.PreferenceConstants;
import com.doapps.cakephp.util.FileUtils;

public class CakePHPProject implements ICakePHPProject
{
  private IProject project;
  private CakeVersion cakeVersion;

  public CakePHPProject(IProject project)
  {
    this.project = project;

    String cakeVersionString = FileUtils.getProjectPropertyOrWorkspacePref(this.project, PreferenceConstants.P_CAKE_VER);
    this.cakeVersion = CakeVersion.getVersion(cakeVersionString);
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

  /**
   * Get the CakePHP version object for this project
   * 
   * @return
   */
  public CakeVersion getCakePHPVersion()
  {
    return this.cakeVersion;
  }

  public ICakePHPFile getCakePHPFile(IFile file)
  {
    if (file == null)
    {
      return null;
    }

    if (getCakePHPVersion().isModel(file))
    {
      return new Model(this, file);
    }
    else if (getCakePHPVersion().isController(file))
    {
      return new Controller(this, file);
    }
    else if (getCakePHPVersion().isElement(file)) // order matters, this needs
                                                  // to be ahead of view,
                                                  // because it's parent folder
                                                  // is View
    {
      return new Element(this, file);
    }
    else if (getCakePHPVersion().isView(file))
    {
      return new View(this, file);
    }
    else if (getCakePHPVersion().isJsFile(file))
    {
      return new JSFile(this, file);
    }

    return null;
  }

  private boolean hasParentFolderAndNameMatches(IFile file, Pattern filePattern, Pattern folderPattern, int maxParentsToCheck)
  {
    if (file == null)
    {
      return false;
    }

    String fileName = file.getName();
    boolean isFile = filePattern.matcher(fileName).matches();
    // file name doesn't match, can't be what we are looking for
    if (!isFile)
    {
      return false;
    }
    // just checking file name, not checking parent folder
    if (maxParentsToCheck < 1)
    {
      return true;
    }

    IPath filePath = file.getProjectRelativePath();
    int segmentCount = filePath.segmentCount();
    // (segmentCount - 2) ....... 3....2....1
    int folderStart = segmentCount - 2;
    for (int i = 0; i < maxParentsToCheck && i < folderStart; ++i)
    {
      String folderName = filePath.segment(folderStart - i);
      boolean folderMatches = folderPattern.matcher(folderName).matches();
      if (folderMatches)
      {
        return true;
      }
    }
    return false;
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
      ICakePHPFile currentFile = getCakePHPFile(file);
      if (currentFile != null)
      {
        switch (currentFile.getCakePHPFileType())
        {
          case MODEL:
          {
            return getController(currentFile);
          }
          case CONTROLLER:
          {
            return getModel(currentFile);
          }
          case VIEW:
          {
            return getModel(currentFile);
          }
          case JSFILE:
          {
            break;
          }
          case ELEMENT:
          {
            break;
          }
        }
      }
    }
    return null;
  }

  @Override
  public boolean openNextFile()
  {
    ICakePHPFile cakePHPFile = getFileToOpen();
    if (cakePHPFile == null)
    {
      return false;
    }
    IFile destinationFile = cakePHPFile.getFile();
    if (destinationFile == null)
    {
      return false;
    }
    try
    {
      // TODO: check in preferences to see if automatically create files or
      // prompt or do nothing
      if (!destinationFile.exists())
      {
        // currently there's a bug that the file won't get created the first
        // time after the folder is created.
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

  private IController getController(IPath rootFolder, String name)
  {
    // IController controller = new Controller(this,
    // getCakePHPVersion().getController(name));
    CakeVersion version = getCakePHPVersion();
    String controllerFileName = version.constructControllerName(name);
    IController controller = new Controller(this, getFile(rootFolder.append(version.getControllerDirName()).append(controllerFileName)));
    return controller;
  }

  private IModel getModel(IPath rootFolder, String name)
  {
    // IModel model = new Model(this, getCakePHPVersion().getModel(name));
    CakeVersion version = getCakePHPVersion();
    String modelFileName = version.constructModelName(name);
    IModel model = new Model(this, getFile(rootFolder.append(version.getModelDirName()).append(modelFileName)));
    return model;
  }

  @Override
  public IController getController(ICakePHPFile file)
  {
    // TODO: determine parent folder, like plugin or something
    IPath rootFolder = getAppFolder();

    switch (file.getCakePHPFileType())
    {
      case MODEL:
      {
        return getController(rootFolder, getCakePHPVersion().getControllerNameForModel((IModel) file));
      }
      case CONTROLLER:
      {
        // just return the same controller
        return (IController) file;
      }
      case VIEW:
      {
        // return getModel(getModelNameForController((IController) file));
      }
      case JSFILE:
      {
        break;
      }
      case ELEMENT:
      {
        break;
      }
    }
    return null;
  }

  @Override
  public IModel getModel(ICakePHPFile file)
  {
    // TODO: determine parent folder, like plugin or something
    IPath rootFolder = getAppFolder();

    switch (file.getCakePHPFileType())
    {
      case MODEL:
      {
        // just return same file
        return (IModel) file;
      }
      case CONTROLLER:
      {
        return getModel(rootFolder, getCakePHPVersion().getModelNameForController((IController) file));
      }
      case VIEW:
      {
        break;
      }
      case JSFILE:
      {
        break;
      }
      case ELEMENT:
      {
        break;
      }
    }
    return null;
  }

  public IFile getAppDir()
  {
    String appDir = getAppDirName();
    if (null != appDir)
    {
      return getProject().getFile(appDir);
    }
    // If here, just use the default
    return getProject().getFile(getAppDirName());
  }

  public String getAppDirName()
  {
    String appDir = null;

    try
    {
      // First check if they have a proj specific app dir
      if (FileUtils.isProjectSpecificSettingsEnabled(getProject()))
      {
        appDir = FileUtils.getProjectProperty(getProject(), PreferenceConstants.P_APP_DIR);
        if (null != appDir)
          return appDir;
      }
    }
    catch (CoreException e)
    {
      // Property not found, so not using project specific settings
    }

    // If we are here, try to get the editor wide preferences app dir
    appDir = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_APP_DIR);
    // Pref app dir is relative to project
    if (null != appDir)
      return appDir;

    return getCakePHPVersion().getDefaultAppDirName();
  }

  @Override
  public IPath getAppFolder()
  {
    return getAppDir().getProjectRelativePath();
  }

  @Override
  public IPath getWebrootFolder()
  {
    // return getCakePHPVersion().getWebrootDir().getFullPath();
    return null;
  }

  @Override
  public IFile getJsFile(IController controller, ICakeAction action)
  {
    // return getCakePHPVersion().getJsFile(controller, action);
    return null;
  }

  @Override
  public IFile getViewFile(IController controller, ICakeAction action)
  {
    // return getCakePHPVersion().getViewFile(controller, action);
    return null;
  }

  @Override
  public IPath getJsFolder()
  {
    // return getCakePHPVersion().getJsDir().getFullPath();
    return null;
  }

  @Override
  public IPath getJsFolder(IController controller)
  {
    // return getCakePHPVersion().getJsFolder(controller).getFullPath();
    return null;
  }

  @Override
  public IPath getViewFolder()
  {
    // return getCakePHPVersion().getViewDir().getFullPath();
    return null;
  }

  @Override
  public IPath getViewFolder(IController controller)
  {
    // return getCakePHPVersion().getViewFolder(controller).getFullPath();
    return null;
  }

  @Override
  public IPath getElementsFolder()
  {
    // return getCakePHPVersion().getElementDir().getFullPath();
    return null;
  }

  @Override
  public IPath getModelFolder()
  {
    // return getCakePHPVersion().getModelDir().getFullPath();
    return null;
  }
}
