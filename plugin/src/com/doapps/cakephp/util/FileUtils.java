/**
 * 
 */
package com.doapps.cakephp.util;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.doapps.cakephp.Activator;
import com.doapps.cakephp.preferences.PreferenceConstants;

/**
 * @author ryan
 *
 */
public class FileUtils {
	
	/**
	 * Get the full path of the current project
	 * 
	 * @return full path string or "" if not found
	 */
	static public String getCurrentProjectPath() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	    if (window != null)
	    {
	        IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
	        Object firstElement = selection.getFirstElement();
	        if (firstElement instanceof IAdaptable)
	        {
	            IProject project = (IProject)((IAdaptable)firstElement).getAdapter(IProject.class);
	            IPath path = project.getLocation();
	            return path.toString();
	        }
	    }
	    return "";
	}
	
	static public Boolean isProjectSpecificSettingsEnabled() {
		Boolean projectSpecificSettingsEnabled = false;
		try {
			//First check if they have a proj specific app dir
			String enableProjSpecific = FileUtils.getProjectProperty(PreferenceConstants.P_ENABLE_PROJECT_SPECIFIC_SETTINGS);
			if(null != enableProjSpecific) {
				projectSpecificSettingsEnabled = Boolean.parseBoolean(enableProjSpecific);			
			}		
		} catch (CoreException e) {
			//Property not found, so not using project specific settings
		}
		return projectSpecificSettingsEnabled;
	}
	
	/**
	 * Get project specific property (if enabled) fall back to workspace pref
	 * 
	 * @param key they key must be the same in the properties store and the preference store
	 * @return null|String
	 */
	static public String getProjectPropertyOrWorkspacePref(String key) {
		if(!isProjectSpecificSettingsEnabled()) return Activator.getDefault().getPreferenceStore().getString(key);
		else {
			try {
				return getProjectProperty(key);
			} catch (CoreException e) {
				return null;
			}
		}
	}
	
	/**
	 * Get a property from the persistent property store
	 * 
	 * @param key
	 * @return null|String value
	 * @throws CoreException
	 */
	static public String getProjectProperty(String key) throws CoreException {
		String value = null;
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	    if (window != null)
	    {
	        IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
	        Object firstElement = selection.getFirstElement();
	        if (firstElement instanceof IResource)
	        {
	        	value = ((IResource) firstElement).getPersistentProperty(new QualifiedName("", key));
	        }
	    }
	    return value;
	}
}
