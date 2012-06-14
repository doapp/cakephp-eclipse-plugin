package com.doapps.cakephp.files;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;

import com.doapps.cakephp.Activator;
import com.doapps.cakephp.preferences.PreferenceConstants;
import com.doapps.cakephp.util.FileUtils;

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

	public String getCurrentProjectDir()
	{
		// TODO: fiture out how to cache, and still work when they switch projects
		return FileUtils.getCurrentProjectPath();
	}

	public String getVersion()
	{
		return this.name;
	}

	public String getAppDir() {
		String appDir = null;

		try {
			//First check if they have a proj specific app dir
			if(FileUtils.isProjectSpecificSettingsEnabled()) {
				appDir = FileUtils.getProjectProperty(PreferenceConstants.P_APP_DIR);
				if(null != appDir) return appDir;
			}
		} catch (CoreException e) {
			//Property not found, so not using project specific settings
		}

		//If we are here, try to get the editor wide preferences app dir
		appDir = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_APP_DIR);
		//Pref app dir is relative to workspace	
		if( null != appDir ) {
			appDir = getCurrentProjectDir() + File.separator + appDir;
			return appDir;
		}

		//If here, just use the default
		return getDefaultAppDir();
	}

	public abstract String getDefaultAppDir();

	public abstract String getAppDirName();

	public String getModelDir() {
		return getAppDir() + File.separator + getModelDirName();
	}

	public abstract String getModelDirName();

	public String getViewDir() {
		return getAppDir() + File.separator + getViewDirName();
	}

	public abstract String getViewDirName();
		
	public String getElementDir() {
		return getAppDir() + File.separator + getElementDirName();
	}

	public abstract String getElementDirName();
	
	public String getControllerDir() {
		return getAppDir() + File.separator + getControllerDirName();
	}

	public abstract String getControllerDirName();
	
	public abstract String getControllerFileNameSuffix();
}
