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
package com.doapps.cakephp.files.impl;

import org.eclipse.core.resources.IFile;

import com.doapps.cakephp.files.CakePHPFileType;
import com.doapps.cakephp.files.ICakePHPProject;
import com.doapps.cakephp.files.IModel;

public class Model extends CakePHPFile implements IModel
{

  public Model(ICakePHPProject project, IFile file)
  {
    super(project, file);
  }
  @Override
  public String getInitialContents()
  {
	//TODO: run command line Cake console bake (prompt first)
    return null;
  }

  @Override
  public CakePHPFileType getCakePHPFileType()
  {
    return CakePHPFileType.MODEL;
  }
}
