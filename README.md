cakephp-eclipse-plugin
==============

An Eclipse plugin for interacting with CakePHP projects

This plugin makes creating and editing CakePHP projects a breeze.  Among other things, it binds keys to perform frequently used actions.

## Features

*  cycle between model, view, controller, JS file.
*  open up (M|V|C) from file outline view
*  open up (M|C) directly from any file
*  open up view by highlighting action method in controller, and doing ctrl+shift+;
*  create files that do not exist
*  CakePHP v1.3+ support (have only tested on 2.1 so far)

## Installing

### Requirements

You must have eclipse 3.7 and PDT.  You have 2 options to get PDT:

*  The most up-to-date version of PDT is now maintained by Zend. To get this version you must download the [Zend all-in-one PDT](http://www.zend.com/en/community/pdt/). They don't have an update site for their version of PDT. [You should complain](http://www.zend.com/en/company/contact-us/).
*  From your existing Eclise 3.7 install: **help > install new software > (search for PDT)**

### Install plugin and configure enviornment

1.  **Help > Install New Software** and add <code>http://doapp.github.com/cakephp-eclipse-plugin/update-site/</code> to your list of update sites
1.  If you installed the Zend all-in-one PDT choose TODO. Otherwise choose TODO
1.  After restart of eclipse, you have to unbind (or change to whatever you want) default Eclipse keybindings.  To do this open **window > Preferences > General > keys**. Find the following and click 'Unbind Command':
    *  Opens a method in a PHP editor (when: Editing PHP source)
    *  Toggle Comment (when: Editing PHP source AND Editing in Structured Text Editor AND Editing JavaScript Source AND Editing Script source)
1.  Now setup your prefrences: **window > Preferences > CakePHP**.  You can override these on a project by project basis by right clicking on your project.

This plugin uses the following convention for JS files:
<code>webroot/js/{Controller}/{view}.js</code>

For example, the view <code>Examples/index.ctp</code>'s JS file would be <code>webroot/js/Examples/index.js</code>

We recommend using the [asset_compress plugin](https://github.com/markstory/asset_compress) to manage all your css/js files.

## Usage

### Key Bindings

<table>
  <tr>
    <td>Ctrl+Shift+;</td><td>Cycle between files: Model <-> Controller and View <--> JS  Note: if a function is highlighted in the Controller (or in outline view), it will open up the view.</td>
  <tr>
    <td>Ctrl+Shift+M</td><td>Opens up the model from any file (including JS)</td>
  </tr>
  <tr>
    <td>Ctrl+Shift+C</td><td>Opens up the controller from any file (including JS)</td>
  </tr>
</table>

## TODO/Need help

* right click>bake on M|V|C directory will kick off [eclipse external tool](http://rynop.com/setting-up-eclipse-36-for-cakephp-13-developm) to bake file(s). This might help: http://stackoverflow.com/questions/4591141/how-to-launch-external-tool-from-custom-menu
* ctrl+shift+e to display a typeahead popup of elements to open
* tie in saves to [webrf](https://github.com/rynop/webrf)
* figure out supported CakePHP version objects dynamically - package introspection? Class loading?
 
## Thanks

This plugin was inspired by the [open cake file plugin](http://opencakefile.sourceforge.net/)

## Things that we find useful along side of this plugin

*  [asset_compress plugin](https://github.com/markstory/asset_compress) to manage all your css/js files
*  [webrf](https://github.com/rynop/webrf) to easily refresh that browser on your other window while you edit

## Build instructions

1.  <code>git checkout gh-pages && git pull && git checkout master && git pull</code>
1.  Syncronize update site, and build
1.  <code>git add update-site/ && git commit -am 'new updatesite build' && git push</code>
1.  <code>cp -Rf update-site /tmp/</code>

```
git checkout gh-pages && git pull && cp -R /tmp/update-site/* update-site/ && git add update-site/ && git add feature/ && git commit -am 'new updatesite build' && git push
```



