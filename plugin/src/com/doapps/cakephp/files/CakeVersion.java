package com.doapps.cakephp.files;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.xicabin.cakephp.util.Inflector;

import com.doapps.cakephp.Activator;
import com.doapps.cakephp.preferences.PreferenceConstants;
import com.doapps.cakephp.util.FileUtils;

public abstract class CakeVersion
{
	private String name;
	private IProject project;
	
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
	
	//TODO: figure out how to pass in IProject handle in Activator. This is only needed cuz window pref and proj prop cant pass in project handle
//	public static CakeVersion getVersion(String name)
//	{
//		CakeVersion version = versions.get(name);
//		
//		if (version == null)
//		{
//			throw new RuntimeException("Invalid cake version: " + name);
//		}
//
//		return version;
//	}
	
//	public void setProject(IProject project) {
//		this.project = project;
//	}
//	public IProject getProject() {
//		return this.project;
//	}
	
//	public IPath getCurrentProjectDir()
//	{
//		// TODO: figure out how to cache, and still work when they switch projects
//		return FileUtils.getCurrentProjectPath();
//	}

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

//	public IFile getModelDir() {
//		return getProject().getFile(getAppDirName() + File.separator + getModelDirName());
//	}

	public abstract String getModelDirName();

//	public IFile getModel(String name){	
//		return getProject().getFile(getModelDir().getProjectRelativePath().append(constructModelName(name)));
//	}
	
	/**
	 *  
	 * @param name 
	 * @return name of model (including extension). v2 ex: User.php v1: user.php
	 */
	public abstract String constructModelName(String name);
	
//	public IFile getViewDir() {
//		return getProject().getFile(getAppDirName() + File.separator + getViewDirName());
//	}

	public abstract String getViewDirName();
		
//	public IFile getElementDir() {
//		return getProject().getFile(getElementDirName());
//	}

	public abstract String getElementDirName();
	
//	public IFile getWebrootDir() {
//		return getProject().getFile(getWebrootDirName());
//	}
	
	public String getWebrootDirName() {
		return "webroot";
	}
	
//	public IFile getJsDir() {
//		return getProject().getFile(getJsDirName());
//	}
	
	public String getJsDirName() {
		return "js";
	}
	
//	public IFile getCssDir() {
//		return getProject().getFile(getCssDirName());
//	}
	
	public String getCssDirName() {
		return "css";
	}
	
//	public IFile getControllerDir() {
//		return getProject().getFile(getAppDirName() + File.separator + getControllerDirName());
//	}
	
	public abstract String getControllerDirName();
	
	public abstract String getControllerFileNameSuffix();
	
//	public IFile getController(String name){		
//		return getProject().getFile(getControllerDir().getProjectRelativePath().append(constructControllerName(name)));
//	}
	
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
	    // TODO: is this different for versions?  I don't think so
	    // 1.x: tests -> test
	    // 2.x: Tests -> Test
	    return Inflector.singularize(controller.getName());
	}
		
	public String getControllerNameForModel(IModel model)
	{
		// TODO: is this different for versions?  I don't think so
		// 1.x: test -> tests
		// 2.x: Test -> Tests
		return Inflector.pluralize(model.getName());
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
		
		//-2 cuz its 0 indexed
		return (thePath.segment(numSegments - 2).equals(versionDirName));
	}
	
	public boolean isElement(IFile file) {
		//For a model, 2nd to last segment should be <elementDirName>/
		IPath thePath = file.getProjectRelativePath();
		int numSegments = thePath.segmentCount();
		String versionDirName = getElementDirName();
		
		//-2 cuz its 0 indexed
		return (thePath.segment(numSegments - 2).equals(versionDirName));
	}
	
	public boolean isJsFile(IFile file) {
		//For a model, 2nd to last segment should be <jsDirName>/
		IPath thePath = file.getProjectRelativePath();
		int numSegments = thePath.segmentCount();
		String versionDirName = getJsDirName();
		
		//-2 cuz its 0 indexed
		return (thePath.segment(numSegments - 2).equals(versionDirName));
	}
	
	public boolean isController(IFile file) {
		//For a model, 2nd to last segment should be <controllerDirName>/
		IPath thePath = file.getProjectRelativePath();
		int numSegments = thePath.segmentCount();
		String versionDirName = getControllerDirName();
		
		//-2 cuz its 0 indexed
		return (thePath.segment(numSegments - 2).equals(versionDirName));
	}
}
