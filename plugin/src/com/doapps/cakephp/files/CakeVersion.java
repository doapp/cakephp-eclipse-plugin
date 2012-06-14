package com.doapps.cakephp.files;

import com.doapps.cakephp.util.FileUtils;

public abstract class CakeVersion
{
	public String getCurrentProjectDir() {
		//TODO: fiture out how to cache, and still work when they switch projects
		return FileUtils.getCurrentProjectPath();
	}
	public abstract String getVersion();

	public abstract String getAppDir();
	public abstract String getAppDirName();
	
	public abstract String getModelDir();
	public abstract String getModelDirName();

	public abstract String getViewDir();
	public abstract String getViewDirName();

	public abstract String getControllerDir();
	public abstract String getControllerDirName();
}
