/**
 * 
 */
package com.doapps.cakephp.files.impl;

import java.util.regex.Pattern;

import com.doapps.cakephp.files.CakeVersion;
import com.doapps.cakephp.files.IElement;

/**
 * @author ryan
 *
 */
public class CakeVersion1 extends CakeVersion
{
	private static CakeVersion1 instance = null;
	private static final Pattern CONTROLLER_NAME_PATTERN = Pattern.compile("(.*)Controller\\..*");
	
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
	
	/* (non-Javadoc)
	 * @see com.doapps.cakephp.files.ICakeVersion#getDefaultAppDir()
	 */
	public String getDefaultAppDirName() {
		return "app";
	}	

	/* (non-Javadoc)
	 * @see com.doapps.cakephp.files.ICakeVersion#getModelDirName()
	 */
	public String getModelDirName() {
		return "models";
	}

	public String constructModelName(String name) {
	    return name.toLowerCase() + ".php";
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
	
	@Override
	public String getJSfolderNameForElement(IElement element)
	{
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
	
	public String constructControllerName(String name) {
		return name + "_controller.php";
	}
	
  public String constructViewName(String controllerName, String action)
  {
    return controllerName + "/" + action + ".ctp";
  }
  
	public Pattern getControllerNamePattern() {
		return CakeVersion1.CONTROLLER_NAME_PATTERN;
	}
}
