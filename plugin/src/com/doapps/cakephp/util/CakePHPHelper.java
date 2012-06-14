//NOT USED - just in here for ref

/**
 * CakePHPHelper.java
 * cakephp-plugin
 * 
 * Created by jeremy on Jun 4, 2012
 * DoApp, Inc. owns and reserves all rights to the intellectual
 * property and design of the following application.
 *
 * Copyright 2012 - All rights reserved.  Created by DoApp, Inc.
 */
package com.doapps.cakephp.util;

import java.io.ByteArrayInputStream;

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
import org.eclipse.ui.ide.IDE;
import org.xicabin.cakephp.util.Inflector;

/**
 * @author jeremy
 * 
 */
public abstract class CakePHPHelper
{
  private static final String PHP = ".php";
  private static final String MODELS = "Model";
  private static final String VIEWS = "View";
  //private static final String VIEW_EXT_1_1 = ".thtml";
  private static final String VIEW_EXT_CTP = ".ctp";
  private static final String CONTROLLERS = "Controller";
  private static final String CONTROLLER_SUFFIX = "Controller";
  private static final String CONTROLLER_FILE_SUFFIX = CONTROLLER_SUFFIX + PHP;
  private static final String WEBROOT = "webroot";
  private static final String JS = "js";
  private static final String JS_EXT = "js";
  

  private static String[] splitAction(String action)
  {
    String separator = "/";
    String admin = "admin";
    if (action == null || !action.contains(separator))
    {
      return new String[] { null, action };
    }
    // pattern 1: controller/action => controller, action
    // pattern 2: /controller/action => controller, action
    // pattern 3: admin/controller/action => controller, admin_action
    // pattern 4: /admin/controller/action => controller, admin_action
    // pattern 5: action => null, action
    action = action.trim().replaceFirst(separator, "").trim();
    // now there's 3 patterns
    String[] splits = action.split(separator);
    if (splits.length < 2)
    {
      return new String[] { null, action };
    }
    // now there's 2 patterns
    if (splits.length > 2 && splits[0].equals(admin))
    {
      return new String[] { splits[1].trim(), admin + "_" + splits[2].trim() };
    }
    // now there's 1 pattern
    return new String[] { splits[0].trim(), splits[1].trim() };
  }

  public static IFile getViewFromAction(IFile controllerFile, String action)
  {
    if (!isController(controllerFile))
      return null;
    IPath controllerFilePath = controllerFile.getProjectRelativePath();
    String[] keys = splitAction(action);
    String view = keys[0];
    if (view == null || view.length() == 0)
    {
      view = getControllerBaseName(controllerFilePath.lastSegment());
      //view = view.substring(0, 1).toUpperCase() + view.substring(1);
      view = Inflector.humanize(view);
    }
    action = keys[1];
    IPath viewPath = getAppFolderFromController(controllerFilePath).append(VIEWS).append(view);
    return getPreferenceViewFile(controllerFile.getProject(), viewPath, action);
  }

  private static IFile getPreferenceViewFile(IProject project, IPath viewPath, String action)
  {
    String[] viewExts = getAvailableViewExts();
    String[] viewFiles = new String[] { Inflector.underscorize(action), Inflector.camelize(action) };
    for (int iExt = 0; iExt < viewExts.length; ++iExt)
    {
      for (int iFile = 0; iFile < viewFiles.length; ++iFile)
      {
        IPath viewFilePath = viewPath.append(viewFiles[iFile] + viewExts[iExt]);
        IFile file = project.getFile(viewFilePath);
        if (file.exists())
        {
          return file;
        }
        break;
      }
    }
    // never return 'null', use file.exists() to check exists
    IPath viewFilePath = viewPath.append(Inflector.underscorize(action) + getDefaultViewExt());
    return project.getFile(viewFilePath);
  }

  public static IFile getSelectedFile(IWorkbenchPage page)
  {
    if (null == page)
      return null;
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
          return (IFile) r.getAdapter(IFile.class);
        }
      }
    }
    return file;
  }
  
  public static String getSelectedText(IWorkbenchPage page)
  {
    if (null == page)
      return null;
    String text = null;
    ISelection selection = page.getSelection();
    if (selection instanceof ITextSelection)
    {
      text = ((ITextSelection) selection).getText();
    }
    else if (selection instanceof IStructuredSelection)
    {
      Object element = ((IStructuredSelection) selection).getFirstElement();
      if (element instanceof IMethod)
      {
        String fullyQualifiedName = ((IMethod) element).getFullyQualifiedName();
        String[] split = fullyQualifiedName.split("\\$");
        if (split.length > 1)
        {
          return split[1];
        }
      }
      return null;
    }
    return text;
  }

  public static boolean isModel(IFile currentFile)
  {
    if (currentFile == null)
      return false;
    IPath modelFilePath = currentFile.getProjectRelativePath();
    if (modelFilePath.segmentCount() < 2)
      return false;
    boolean isModel = modelFilePath.segment(modelFilePath.segmentCount() - 2).equalsIgnoreCase(MODELS)
        && modelFilePath.lastSegment().endsWith(PHP);
    return isModel;
  }

  public static boolean isController(IFile currentFile)
  {
    if (currentFile == null)
      return false;
    IPath controllerFilePath = currentFile.getProjectRelativePath();
    if (controllerFilePath.segmentCount() < 2)
      return false;
    return controllerFilePath.segment(controllerFilePath.segmentCount() - 2).equals(CONTROLLERS)
        && controllerFilePath.lastSegment().endsWith(CONTROLLER_FILE_SUFFIX);
  }

  private static IPath getAppFolderFromJSFile(IPath jsFilePath)
  {
    return jsFilePath.removeLastSegments(4);
  }
  
  private static IPath getAppFolderFromView(IPath viewFilePath)
  {
    return viewFilePath.removeLastSegments(3);
  }

  public static IFile getModelFromView(IFile viewFile)
  {
    if (!isView(viewFile))
      return null;
    IPath viewFilePath = viewFile.getProjectRelativePath();
    IPath model = getModelFromView(viewFilePath);
    return viewFile.getProject().getFile(model);
  }

  private static IPath getModelFromView(IPath viewFilePath)
  {
    String singular = getModelNameFromViewPath(viewFilePath);
    return getAppFolderFromView(viewFilePath).append(MODELS).append(singular);
  }

  private static String getPluralResourceNameFromViewPath(IPath viewFilePath)
  {
    return viewFilePath.segment(viewFilePath.segmentCount() - 2);
  }

  private static String getModelNameFromViewPath(IPath viewFilePath)
  {
    String singular = Inflector.singularize(getPluralResourceNameFromViewPath(viewFilePath));
    singular += PHP;
    return singular;
  }

  public static IFile getControllerFromView(IFile viewFile)
  {
    if (!isView(viewFile))
      return null;
    IPath viewFilePath = viewFile.getProjectRelativePath();
    String plural = getPluralResourceNameFromViewPath(viewFilePath);

    String controllerName = plural + CONTROLLER_FILE_SUFFIX;
    IPath controllerPath = getAppFolderFromView(viewFilePath).append(CONTROLLERS).append(controllerName);

    IFile file = viewFile.getProject().getFile(controllerPath);
    return file;
  }

  private static String[] getAvailableViewExts()
  {
    /*
     * IPreferenceStore store = Activator.getDefault().getPreferenceStore();
     * boolean useCustomViewExt = store
     * .getBoolean(PreferenceConstants.USE_CUSTOM_VIEW_EXT); String[] viewExts =
     * null; if (useCustomViewExt) { String viewExt =
     * store.getString(PreferenceConstants.VIEW_EXT); viewExts = new String[] {
     * viewExt }; } else { viewExts = new String[] { VIEW_EXT_1_1, VIEW_EXT_1_2
     * }; } return viewExts;
     */
    return new String[] { VIEW_EXT_CTP };
  }
  
  private static String[] getAvailableJSExts()
  {
    return new String[] { JS_EXT };
  }

  private static String getDefaultViewExt()
  {
    return VIEW_EXT_CTP;
//    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
//    boolean useCustomViewExt = store.getBoolean(PreferenceConstants.USE_CUSTOM_VIEW_EXT);
//    String viewExt = null;
//    if (useCustomViewExt)
//    {
//      viewExt = store.getString(PreferenceConstants.VIEW_EXT);
//    }
//    else
//    {
//      viewExt = VIEW_EXT_1_1;
//    }
//    return viewExt;
  }

  public static boolean isView(IFile currentFile)
  {
    if (currentFile == null)
      return false;
    IPath viewFilePath = currentFile.getProjectRelativePath();
    if (viewFilePath.segmentCount() < 3)
      return false;
    String view = viewFilePath.segment(viewFilePath.segmentCount() - 3);
    if (!view.equals(VIEWS))
    {
      return false;
    }
    String[] viewExts = getAvailableViewExts();
    String name = viewFilePath.lastSegment();
    for (int I = 0; I < viewExts.length; ++I)
    {
      if (name.endsWith(viewExts[I]))
      {
        return true;
      }
    }
    return false;
  }

  private static IPath getAppFolderFromModel(IPath modelFilePath)
  {
    return modelFilePath.removeLastSegments(2);
  }
  
  public static String getModelNamePlural(IFile modelFile)
  {
    IPath modelFilePath = modelFile.getProjectRelativePath();
    String modelFilename = modelFilePath.lastSegment();
    if (!CakePHPHelper.isModel(modelFile))
      return null;
    String singular = modelFilename.substring(0, modelFilename.indexOf('.'));
    String plural = Inflector.pluralize(singular);
    return plural;
  }

  public static IFile getControllerFromModel(IFile modelFile)
  {
    IPath modelFilePath = modelFile.getProjectRelativePath();
//    String modelFilename = modelFilePath.lastSegment();
//    if (!CakePHPHelper.isModel(modelFile))
//      return null;
//    String singular = modelFilename.substring(0, modelFilename.indexOf('.'));
//    String plural = Inflector.pluralize(singular);
    
    String plural = getModelNamePlural(modelFile);

    String controllerName = plural + CONTROLLER_FILE_SUFFIX;
    IPath controllerPath = getAppFolderFromModel(modelFilePath).append(CONTROLLERS).append(controllerName);

    IFile file = modelFile.getProject().getFile(controllerPath);
    return file;
  }

  private static String getModelName(String controllerFilename)
  {
    String singular = Inflector.singularize(getControllerBaseName(controllerFilename));
    singular += PHP;
    return singular;
  }

  private static String getControllerBaseName(String controllerFilename)
  {
    return controllerFilename.substring(0, controllerFilename.length() - CONTROLLER_FILE_SUFFIX.length());
  }

  public static IFile getModelFromController(IFile controllerFile)
  {
    if (!isController(controllerFile))
      return null;
    IPath controllerFilePath = controllerFile.getProjectRelativePath();
    String controllerFilename = controllerFilePath.lastSegment();

    IPath model = getModelFromController(controllerFilePath, getModelName(controllerFilename));
    IFile file = controllerFile.getProject().getFile(model);
    return file;
  }

  private static IPath getAppFolderFromController(IPath controllerFilePath)
  {
    return controllerFilePath.removeLastSegments(2);
  }

  private static IPath getModelFromController(IPath controllerFilePath, String singular)
  {
    return getAppFolderFromController(controllerFilePath).append(MODELS).append(singular);
  }

  private static IPath getJSFileFromApp(IPath appFolder, String modelName, String viewName)
  {
    return appFolder.append(WEBROOT).append(JS).append(modelName).append(viewName).addFileExtension(JS_EXT);
  }
  
  public static IFile getJSFileFromView(IFile selectedFile)
  {
    if (!isView(selectedFile))
    {
      return null;
    }
    IPath viewPath = selectedFile.getProjectRelativePath();
    int segmentCount = viewPath.segmentCount();
    if (segmentCount < 3)
    {
      return null;
    }
    String modelName = viewPath.segment(segmentCount - 2);
    String[] split = viewPath.lastSegment().split("\\.");
    String viewName = split[0];
    
    IPath jsPath = getJSFileFromApp(getAppFolderFromView(viewPath), modelName, viewName);
    IFile file = selectedFile.getProject().getFile(jsPath);
    return file;
  }

  public static boolean isJSFile(IFile selectedFile)
  {
    if (selectedFile == null)
      return false;
    IPath viewFilePath = selectedFile.getProjectRelativePath();
    if (viewFilePath.segmentCount() < 3)
      return false;
    String view = viewFilePath.segment(viewFilePath.segmentCount() - 3);
    if (!view.equals(JS))
    {
      return false;
    }
    String[] jsExts = getAvailableJSExts();
    String name = viewFilePath.lastSegment();
    for (int I = 0; I < jsExts.length; ++I)
    {
      if (name.endsWith(jsExts[I]))
      {
        return true;
      }
    }
    return false;
  }

  public static IFile getViewFromJSFile(IFile selectedFile)
  {
    if (!isJSFile(selectedFile))
      return null;
    IPath selectedFilePath = selectedFile.getProjectRelativePath();
    //String[] keys = splitAction(selectedFilePath.lastSegment());
    String[] keys = selectedFilePath.lastSegment().split("\\.");
    String view = keys[0];
    int segmentCount = selectedFilePath.segmentCount();
    if (segmentCount < 3)
    {
      return null;
    }
    String modelName = selectedFilePath.segment(segmentCount - 2);
    // TODO: there's got to be a better way to do this
    IPath viewPath = getAppFolderFromJSFile(selectedFilePath).append(VIEWS).append(Inflector.pluralize(modelName));
    return getPreferenceViewFile(selectedFile.getProject(), viewPath, view);
  }

  public static void openFile(IWorkbenchPage page, IFile destinationFile, byte[] initialContent)
  {
    try
    {
      if (destinationFile != null)
      {
        // TODO: prompt to create the file ?  I say no, why would you try the shortcut if you didn't want it created
        if (!destinationFile.exists())
        {
          IPath fullPath = destinationFile.getLocation();
          if (fullPath.toFile().getParentFile().mkdirs())
          {
            // create Eclipse resource so that the create file doesn't blow chunks
            destinationFile.getProject().getFile(destinationFile.getParent().getProjectRelativePath()).refreshLocal(IFile.DEPTH_ZERO, null);
          }
          destinationFile.create(new ByteArrayInputStream(initialContent), false, null);
        }
        if (destinationFile.exists())
        {
          IDE.openEditor(page, destinationFile);
        }
      }
    }
    catch (CoreException e)
    {
      String clazz = destinationFile.getName();
      System.err.println("OpenCakeFile can not open file: " + clazz);
      e.printStackTrace();
    }
  }

  public static byte[] getInitialJSContents()
  {
    return new String("$(function() {\n\n\n});\n").getBytes();
  }
  
  public static byte[] getInitialControllerContents(IFile modelFile)
  {
    String modelPlural = getModelNamePlural(modelFile);
    return new String("<?php\nclass " + modelPlural + CONTROLLERS + " extends AppController\n{\n  public $name = '" + modelPlural + "';\n  public $uses = array();\n\n\n}\n?>").getBytes();
  }
}
