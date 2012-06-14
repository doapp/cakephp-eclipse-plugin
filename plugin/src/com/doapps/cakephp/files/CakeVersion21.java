/**
 * 
 */
package com.doapps.cakephp.files;

/**
 * @author ryan
 *
 */
public class CakeVersion21 extends CakeVersion {
	
	/* (non-Javadoc)
	 * @see com.doapps.cakephp.files.ICakeVersion#getVersion()
	 */
	public String getVersion() {
		return "2.1";
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
