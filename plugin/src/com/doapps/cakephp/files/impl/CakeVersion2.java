/**
 * 
 */
package com.doapps.cakephp.files.impl;

import java.io.File;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.EditorsUI;

import com.doapps.cakephp.Activator;
import com.doapps.cakephp.files.CakePHPContsants;
import com.doapps.cakephp.files.CakeVersion;
import com.doapps.cakephp.preferences.PreferenceConstants;
import com.doapps.cakephp.util.FileUtils;

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
		super("2.X");
	}

	public static CakeVersion2 getInstance()
	{
		if (instance == null)
		{
			instance = new CakeVersion2();
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
		return "app";
	}	

	/* (non-Javadoc)
	 * @see com.doapps.cakephp.files.ICakeVersion#getModelDirName()
	 */
	public String getModelDirName() {
		return "Model";
	}

	/* (non-Javadoc)
	 * @see com.doapps.cakephp.files.ICakeVersion#getViewDirName()
	 */
	public String getViewDirName() {
		return "View";
	}
	
	public String getElementDirName() {
		return "Elements";
	}

	/* (non-Javadoc)
	 * @see com.doapps.cakephp.files.ICakeVersion#getControllerDirName()
	 */
	public String getControllerDirName() {
		return "Controller";
	}
	
	public String getControllerFileNameSuffix() {
		return "Controller\\.php";
	}
}
