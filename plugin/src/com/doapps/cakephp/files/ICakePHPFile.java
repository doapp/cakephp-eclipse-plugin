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
package com.doapps.cakephp.files;

import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;

public interface ICakePHPFile
{
  public ICakePHPProject getProject();
  
  public IFile getFile();
  
  public String getName();

  public String getInitialContents();
  
  public Pattern getNamePattern();
  
  public CakePHPFileType getCakePHPFileType();

}
