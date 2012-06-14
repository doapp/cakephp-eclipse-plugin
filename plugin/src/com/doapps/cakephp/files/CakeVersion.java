package com.doapps.cakephp.files;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.doapps.cakephp.util.FileUtils;

public abstract class CakeVersion
{
  private String name;
  
  private static final Map<String, CakeVersion> versions = new HashMap<String, CakeVersion>();
  
  protected CakeVersion(String name)
  {
    this.name = name;
    versions.put(name, this);
  }
  
  // use this method to get list of project settings
  public static Set<String> getVersions()
  {
    return versions.keySet();
  }
  
  // use this to get the version from the selected project setting
  public static CakeVersion getVersion(String name)
  {
    CakeVersion version = versions.get(name);
    if (version == null)
    {
      throw new RuntimeException("Invalid cake version: " + name);
    }
    return version;
  }
  
  public String getCurrentProjectDir()
  {
    // TODO: fiture out how to cache, and still work when they switch projects
    return FileUtils.getCurrentProjectPath();
  }

  public String getVersion()
  {
    return this.name;
  }

  public abstract String getAppDir();

  public abstract String getAppDirName();

  public abstract String getModelDir();

  public abstract String getModelDirName();

  public abstract String getViewDir();

  public abstract String getViewDirName();

  public abstract String getControllerDir();

  public abstract String getControllerDirName();
}
