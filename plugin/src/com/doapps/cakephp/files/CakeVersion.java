package com.doapps.cakephp.files;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.xicabin.cakephp.util.Inflector;

public abstract class CakeVersion
{
	private String name;

	private static final Map<String, CakeVersion> versions = new HashMap<String, CakeVersion>();

	protected CakeVersion(String name)
	{
		this.name = name;
		versions.put(name, this);
	}

	// use this method to get list of project settings
	public static Set<String> getVersions()
	{
		return versions.keySet();
	}

	// use this to get the version from the selected project setting
	public static CakeVersion getVersion(String name)
	{
		CakeVersion version = versions.get(name);

		if (version == null)
		{
			throw new RuntimeException("Invalid cake version: " + name);
		}

		return version;
	}

	public String getVersion()
	{
		return this.name;
	}

	//	public IFile getAppDir() {
	//		String appDir = getAppDirName();
	//		if( null != appDir ) {
	//			return getProject().getFile(appDir);
	//		}
	//		//If here, just use the default
	//		return getDefaultAppDir();
	//	}

	//	public String getAppDirName() {
	//		String appDir = null;
	//
	//		try {
	//			//First check if they have a proj specific app dir
	//			if(FileUtils.isProjectSpecificSettingsEnabled()) {
	//				appDir = FileUtils.getProjectProperty(PreferenceConstants.P_APP_DIR);
	//				if(null != appDir) return appDir;
	//			}
	//		} catch (CoreException e) {
	//			//Property not found, so not using project specific settings
	//		}
	//
	//		//If we are here, try to get the editor wide preferences app dir
	//		appDir = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_APP_DIR);
	//		//Pref app dir is relative to project	
	//		if(null != appDir) return appDir;
	//		
	//		return getDefaultAppDirName();
	//	}

	//	public IFile getDefaultAppDir() {
	//		return getProject().getFile(getDefaultAppDirName());
	//	}

	public abstract String getDefaultAppDirName();

	public abstract String getModelDirName();

	/**
	 *  
	 * @param name 
	 * @return name of model (including extension). v2 ex: User.php v1: user.php
	 */
	public abstract String constructModelName(String name);

	public abstract String constructViewName(String controllerName, String action);

	public abstract String getViewDirName();

	public abstract String getElementDirName();

	public String getWebrootDirName() {
		return "webroot";
	}

	public String getJsDirName() {
		return "js";
	}

	public String getCssDirName() {
		return "css";
	}

	public abstract String getControllerDirName();

	public abstract Pattern getControllerNamePattern();
	
	public abstract String getControllerFileNameSuffix();

	/**
	 * Append version specific suffix to a controller name 
	 * 
	 * @param name 
	 * @return name of controller (including extension). v2 ex: BlahController.php v1: blah_controller.php
	 */
	public abstract String constructControllerName(String name);


	/******************** Finder methods ********************/		

	/*********** 
	 * Start of getting things by controller
	 *******/

	//	public IFile getViewFolder(IController controller) {
	//		//TODO: find out how to search/manipulate paths
	//		return getViewDir();
	//	}

	//	public IFile getJsFolder(IController controller) {
	//	    String jsFolderName = controller.getName();
	//	    return getProject().getFile(jsFolderName);
	//	}

	public String getModelNameForController(IController controller) {
		// 1.x: tests -> test
		// 2.x: Tests -> Test
		return Inflector.singularize(controller.getName());
	}

	public String getControllerNameForModel(IModel model)
	{
		// 1.x: test -> tests
		// 2.x: Test -> Tests
		return Inflector.pluralize(model.getName());
	}

	public String getControllerNameForView(IView view)
	{
		// 1.x: views/tests/index.ctp -> tests
		// 2.x: View/Tests/index.ctp -> Tests
		IPath path = view.getFile().getProjectRelativePath();
		int numSegments = path.segmentCount();
		if (numSegments > 2)
		{
			return path.segment(numSegments - 2);
		}
		return null;
	}

	public String getControllerNameForJSFile(IJSFile file)
	{
		// 1.x: views/tests/index.ctp -> tests
		// 2.x: View/Tests/index.ctp -> Tests
		IPath path = file.getFile().getProjectRelativePath();
		int numSegments = path.segmentCount();
		if (numSegments > 2)
		{
			return path.segment(numSegments - 2);
		}
		return null;
	}

	public String getViewNameForAction(String action)
	{
		// 1.x: tests_controller.php:index() -> views/tests/index.ctp
		// 2.x: TestsController.php:index() -> View/Tests/index.ctp
		return action;
	}

	public String getViewFolderNameForController(IController controller)
	{
		// 1.x: tests_controller.php:index() -> views/tests/index.ctp
		// 2.x: TestsController.php:index() -> View/Tests/index.ctp
		return controller.getName();
	}

	public String getJSfolderNameForController(IController controller)
	{
		return controller.getName();
	}

  public abstract String getJSfolderNameForElement(IElement element);

	public String getJSFileNameForView(IView view)
	{
		return view.getName();
	}

  public String getJSFileNameForElement(IElement element)
  {
    return element.getName();
  }

	public String getViewFileNameForJSFile(IJSFile jsFile)
	{
		return jsFile.getName();
	}

	/**
	 * Start of getting things by controller and action
	 */

	//	public IFile getViewFile(IController controller, ICakeAction action) {
	//	    String actionName = action.getName();
	//	    IFile viewFile = getProject().getFile(getViewFolder(controller).toString() + File.separator + actionName);
	//	    return viewFile;
	//	}

	//	public IFile getJsFile(IController controller, ICakeAction action) {
	//	    String jsName = action.getName();
	//	    IFile jsFile = getProject().getFile(getJsFolder(controller).toString() + File.separator + jsName);
	//	    return jsFile;
	//	}

	public boolean isModel(IFile file) {
		//For a model, 2nd to last segment should be <modelDirName>/
		IPath thePath = file.getProjectRelativePath();
		int numSegments = thePath.segmentCount();
		String versionDirName = getModelDirName();

		//-2 cuz its 0 indexed
		return (thePath.segment(numSegments - 2).equals(versionDirName));
	}

	public boolean isView(IFile file) {
		//For a model, 2nd to last segment should be <viewDirName>/
		IPath thePath = file.getProjectRelativePath();
		int numSegments = thePath.segmentCount();
		String versionDirName = getViewDirName();

		//-3 cuz its 0 indexed
		return (thePath.segment(numSegments - 3).equals(versionDirName));
	}

	public boolean isElement(IFile file) {
		//For a model, 2nd to last segment should be <elementDirName>/
		IPath thePath = file.getProjectRelativePath();
		int numSegments = thePath.segmentCount();
		if (numSegments < 3)
		{
		  return false;
		}
		//-2 cuz its 0 indexed
		return getElementDirName().equals(thePath.segment(numSegments - 2)) && getViewDirName().equals(thePath.segment(numSegments - 3));
	}

	public boolean isJsFile(IFile file) {
		//For a model, 2nd to last segment should be <jsDirName>/
		IPath thePath = file.getProjectRelativePath();
		int numSegments = thePath.segmentCount();
		String versionDirName = getJsDirName();

		//-3 cuz its 0 indexed
		return (thePath.segment(numSegments - 3).equals(versionDirName));
	}

	public boolean isController(IFile file) {
		//For a model, 2nd to last segment should be <controllerDirName>/
		IPath thePath = file.getProjectRelativePath();
		int numSegments = thePath.segmentCount();
		String versionDirName = getControllerDirName();

		//-2 cuz its 0 indexed
		return (thePath.segment(numSegments - 2).equals(versionDirName));
	}

	public IPath getRootFolder(ICakePHPFile file)
	{
		int segmentCountToRemove = -1;
		switch (file.getCakePHPFileType())
		{
		case MODEL:
		case CONTROLLER:
		{
			// Current format
			// 2.X: <root >/Model/Test.php
			// 1.X: <root >/models/Test.php
			segmentCountToRemove = 2;
			break;
		}
		case ELEMENT:
		case VIEW:
		{
			// Current format
			// 2.X: <root >/View/Models/index.ctp
			// 2.X: <root >/View/Elements/nav.ctp
			// 1.X: <root >/View/models/index.ctp
			// 1.X: <root >/View/elements/nav.ctp
			segmentCountToRemove = 3;
			break;
		}
		case JSFILE:
		{
			// Current format
			// 2.X: <root >/webroot/js/Model/index.js
			// 1.X: <root >/webroot/js/models/index.js
			segmentCountToRemove = 4;
			break;
		}
		}
		IPath path = file.getFile().getProjectRelativePath();
		int segmentCount = path.segmentCount();
		if ((segmentCountToRemove >= 0) && (segmentCount > segmentCountToRemove))
		{
			IPath root = path.removeLastSegments(segmentCountToRemove);
			return root;
		}
		// default to app folder?
		return null;
	}

	public IPath getControllerPath(IModel model)
	{
		String controllerFileName = constructControllerName(getControllerNameForModel(model));
		return getRootFolder(model).append(getControllerDirName()).append(controllerFileName);
	}

	public IPath getControllerPath(IView view)
	{
		String controllerFileName = constructControllerName(getControllerNameForView(view));
		return getRootFolder(view).append(getControllerDirName()).append(controllerFileName);
	}

	public IPath getControllerPath(IJSFile file)
	{
		String controllerFileName = constructControllerName(getControllerNameForJSFile(file));
		return getRootFolder(file).append(getControllerDirName()).append(controllerFileName);
	}

	public IPath getModelPath(IController controller)
	{
		String modelFileName = constructModelName(getModelNameForController(controller));
		return getRootFolder(controller).append(getModelDirName()).append(modelFileName);
	}

	public IPath getViewPath(IController controller, String action)
	{
		String viewPath = constructViewName(getViewFolderNameForController(controller), action);
		return getRootFolder(controller).append(getViewDirName()).append(viewPath);
	}

	public IPath getViewPath(IJSFile file)
	{
		IController controller = file.getProject().getControllerForJSFile(file);
		if (controller == null)
		{
			return null;
		}
		String folderName = getViewFolderNameForController(controller);
		String fileName = getViewFileNameForJSFile(file);
		return getRootFolder(file).append(getViewDirName()).append(folderName).append(fileName).addFileExtension(getViewExtension());
	}

	public IPath getJSFilePath(IView view)
	{
		IController controller = view.getProject().getControllerForView(view);
		if (controller == null)
		{
			return null;
		}
		String jsFolderName = getJSfolderNameForController(controller);
		String jsFileName = getJSFileNameForView(view);
		return getRootFolder(view).append(getWebrootDirName()).append(getJsDirName()).append(jsFolderName).append(jsFileName).addFileExtension(getJSFileExtension());
	}

  public IPath getJSFilePath(IElement element)
  {
    String jsFolderName = getJSfolderNameForElement(element);
    String jsFileName = getJSFileNameForElement(element);
    return getRootFolder(element).append(getWebrootDirName()).append(getJsDirName()).append(jsFolderName).append(jsFileName).addFileExtension(getJSFileExtension());
  }

	public String getViewExtension()
	{
		return "ctp";
	}

	public String getJSFileExtension()
	{
		return "js";
	}

}
