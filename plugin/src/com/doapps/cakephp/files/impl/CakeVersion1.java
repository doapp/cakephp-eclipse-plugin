/**
 * 
 */
package com.doapps.cakephp.files.impl;

import java.io.File;

import com.doapps.cakephp.files.CakeVersion;

/**
 * @author ryan
 *
 */
public class CakeVersion1 extends CakeVersion
{
	private static CakeVersion1 instance = null;

	// so only this package com implement it
	private CakeVersion1()
	{
		super("1.X");
	}

	public static CakeVersion1 getInstance()
	{
		if (instance == null)
		{
			instance = new CakeVersion1();
		}
		return instance;
	}

	public String getDefaultAppDir() {
		return getCurrentProjectDir() + File.separator + getAppDirName();
	}

	/* (non-Javadoc)
	 * @see com.doapps.cakephp.files.ICakeVersion#getDefaultAppDir()
	 */
	public String getAppDirName() {
		//TODO: look up if they have a proj specific property, otherwise fall back to window pref, otherwise hardcode 'app'
		return "app";
	}	

	/* (non-Javadoc)
	 * @see com.doapps.cakephp.files.ICakeVersion#getModelDirName()
	 */
	public String getModelDirName() {
		return "models";
	}

	/* (non-Javadoc)
	 * @see com.doapps.cakephp.files.ICakeVersion#getViewDirName()
	 */
	public String getViewDirName() {
		return "views";
	}
	
	public String getElementDirName() {
		return "elements";
	}

	/* (non-Javadoc)
	 * @see com.doapps.cakephp.files.ICakeVersion#getControllerDirName()
	 */
	public String getControllerDirName() {
		return "controllers";
	}
	
	public String getControllerFileNameSuffix() {
		return "_controller\\.php";
	}
}
