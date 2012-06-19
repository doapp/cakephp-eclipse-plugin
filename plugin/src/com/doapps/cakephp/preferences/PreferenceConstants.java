/**
 *
 * CakePHP Eclipse Plugin
 * Copyright 2012, DoApp, Inc. (http://www.doapps.com)
 *
 * Licensed under The MIT License
 * Redistributions of files must retain the above copyright notice.
 *
 * @copyright     Copyright 2012, DoApp, Inc. (http://www.doapps.com)
 * @link          https://github.com/doapp/cakephp-eclipse-plugin
 * @license       MIT License (http://www.opensource.org/licenses/mit-license.php)
 */
package com.doapps.cakephp.preferences;

import com.doapps.cakephp.Activator;

/**
 * Constant definitions for plug-in preferences
 */
public class PreferenceConstants {

	public static final String P_CAKE_VER = Activator.PLUGIN_ID + ".cakeVer";
	public static final String P_APP_DIR = Activator.PLUGIN_ID + ".appFolderName";
	public static final String P_CREATE_FILES_AUTOMATICALLY = Activator.PLUGIN_ID + ".createFiles"; 
	public static final String P_ENABLE_PROJECT_SPECIFIC_SETTINGS = Activator.PLUGIN_ID + ".enableProjectSpecific";	
	
	public static final String DEFAULT_CAKE_VER = "2.X";
	public static final Boolean DEFAULT_CREATE_FILES_AUTOMATICALLY = true;
}
