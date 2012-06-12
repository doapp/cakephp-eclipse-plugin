cakephp-plugin
==============

An Eclipse plugin for switching between CakePHP components.

This plugin makes creating and editing CakePHP projects a breeze.  It binds keys to perform frequently used actions.

## Eclipse Update Site
Open up **Help -> Install New Software** and add
http://doapp-jeremy.github.com/cakephp-plugin-update-site/ to your list of update sites
* **Note: That there are currently issues if you have the Zend PDT Eclipse download.  We are working on a fork for that**

## Key Bindings
<table>
  <tr>
    <td>Ctrl+Shift+;</td><td>Cycle between files: Model <-> Controller and View <--> JS  Note: if a function is highlighted in the Controller, it will open up the view.</td>
  <tr>
    <td>Ctrl+Shift+M</td><td>Opens up the model from any file (except JS..in progress)</td>
  </tr>
  <tr>
    <td>Ctrl+Shift+M</td><td>Opens up the controller from any file (except JS..in progress)</td>
  </tr>
</table>
* **Note: These bindings can be changed in Window -> Preferences.  The open model and open controller bindings are already binded, so to get them to work, you will need to unbind the current ones.**

## Source code
The source code is located at https://github.com/doapp-jeremy/cakephp-plugin and was modeled after http://opencakefile.cvs.sourceforge.net/viewvc/opencakefile/org.xicabin.cakephp.opencakefile/
