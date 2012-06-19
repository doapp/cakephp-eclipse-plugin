package com.doapps.cakephp.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.doapps.cakephp.Activator;
import com.doapps.cakephp.files.CakeVersion;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_CAKE_VER,PreferenceConstants.DEFAULT_CAKE_VER);
		store.setDefault(PreferenceConstants.P_CREATE_FILES_AUTOMATICALLY,PreferenceConstants.DEFAULT_CREATE_FILES_AUTOMATICALLY);
		store.setDefault(PreferenceConstants.P_APP_DIR,CakeVersion.getVersion(PreferenceConstants.DEFAULT_CAKE_VER).getDefaultAppDirName());		
	}

}
