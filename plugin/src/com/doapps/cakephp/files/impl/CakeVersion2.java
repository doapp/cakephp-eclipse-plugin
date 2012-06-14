/**
 * 
 */
package com.doapps.cakephp.files.impl;

import com.doapps.cakephp.files.CakeVersion;

/**
 * @author ryan
 *
 */
public class CakeVersion2 extends CakeVersion
{
  private static CakeVersion2 instance = null;
  
  // so only this package com implement it
  private CakeVersion2()
  {
    super("2");
  }

  public static CakeVersion2 getInstance()
  {
    if (instance == null)
    {
      instance = new CakeVersion2();
    }
    return instance;
  }

  public String getAppDir() { 
		return getCurrentProjectDir() + getAppDirName();
	}
	
	/* (non-Javadoc)
	 * @see com.doapps.cakephp.files.ICakeVersion#getDefaultAppDir()
	 */
	public String getAppDirName() {
		//TODO: look up if they have a proj specific property, otherwise fall back to window pref, otherwise hardcode 'app'
		return "app";
	}	

	/* (non-Javadoc)
	 * @see com.doapps.cakephp.files.ICakeVersion#getModelDir()
	 */
	public String getModelDir() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.doapps.cakephp.files.ICakeVersion#getModelDirName()
	 */
	public String getModelDirName() {
		return "Model";
	}

	/* (non-Javadoc)
	 * @see com.doapps.cakephp.files.ICakeVersion#getViewDir()
	 */
	public String getViewDir() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.doapps.cakephp.files.ICakeVersion#getViewDirName()
	 */
	public String getViewDirName() {
		return "View";
	}

	/* (non-Javadoc)
	 * @see com.doapps.cakephp.files.ICakeVersion#getControllerDir()
	 */
	public String getControllerDir() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.doapps.cakephp.files.ICakeVersion#getControllerDirName()
	 */
	public String getControllerDirName() {
		return "Controller";
	}
}
