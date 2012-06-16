package com.doapps.cakephp.properties;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

import com.doapps.cakephp.files.CakeVersion;
import com.doapps.cakephp.preferences.PreferenceConstants;

public class CakePHPPropertyPage extends PropertyPage {
	private static final int TEXT_FIELD_WIDTH = 50;

	private Text cakeAppDir;
	private Combo cakeVersion;
	private Button useProjectSpecificSettings;
	
	/**
	 * Constructor for SamplePropertyPage.
	 */
	public CakePHPPropertyPage() {
		super();
	}
	
	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		createView(composite);
		return composite;
	}

	private void createView(Composite parent) {
		Composite composite = createDefaultComposite(parent);
		
		//Enable proj specific settings (defaults to false)
		Boolean initialEnableProjSpecificVal = false;
		useProjectSpecificSettings = new Button(composite, SWT.CHECK);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		useProjectSpecificSettings.setLayoutData(gridData);
//		useProjectSpecificSettings.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		try {
			String enableProjSpecific = ((IResource) getElement()).getPersistentProperty(new QualifiedName("", PreferenceConstants.P_ENABLE_PROJECT_SPECIFIC_SETTINGS));
			if(null != enableProjSpecific) {
				initialEnableProjSpecificVal = Boolean.parseBoolean(enableProjSpecific);
				useProjectSpecificSettings.setSelection(initialEnableProjSpecificVal);				
			}
			else {
				useProjectSpecificSettings.setSelection(false);
			}
		} catch (CoreException e) {
			useProjectSpecificSettings.setSelection(false);
		}	
				
		useProjectSpecificSettings.setText("Enable project specific settings"); //$NON-NLS-1$
		useProjectSpecificSettings.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent evt) {
	    	  toggleProjectSpecificFields(useProjectSpecificSettings.getSelection());
	      }
	    });	
	    
		addSeparator(composite);		
		
		//Cake version selector
		Label cakeVersionLabel = new Label(composite, SWT.NONE);
		cakeVersionLabel.setText("CakePHP version:");
		cakeVersion = new Combo (composite, SWT.DROP_DOWN);		
		cakeVersion.setItems(CakeVersion.getVersions().toArray(new String[0]));
		
		try {
			String selectedItem = ((IResource) getElement()).getPersistentProperty(new QualifiedName("", PreferenceConstants.P_CAKE_VER));
			cakeVersion.setText((null != selectedItem) ? selectedItem : PreferenceConstants.DEFAULT_CAKE_VER);
		} catch (CoreException e) {
			cakeVersion.setText(PreferenceConstants.DEFAULT_CAKE_VER);
		}		
		
		
		//APP_DIR directory selector
		Label appNameLabel = new Label(composite, SWT.NONE);
		appNameLabel.setText("App folder (rel to project):");		
		cakeAppDir = new Text(composite, SWT.SINGLE | SWT.BORDER);
		cakeAppDir.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridData gd = new GridData();
		gd.widthHint = convertWidthInCharsToPixels(TEXT_FIELD_WIDTH);
		cakeAppDir.setLayoutData(gd);

		try {
			String appFolder =	((IResource) getElement()).getPersistentProperty(new QualifiedName("", PreferenceConstants.P_APP_DIR));
			cakeAppDir.setText((appFolder != null) ? appFolder : CakeVersion.getVersion(cakeVersion.getText()).getAppDirName());
		} catch (CoreException e) {
			cakeAppDir.setText(CakeVersion.getVersion(cakeVersion.getText()).getAppDirName());
		}
				
//	    Button browseFS = new Button(composite, SWT.PUSH);
//	    browseFS.setText("Browse"); //$NON-NLS-1$
//	    browseFS.addSelectionListener(new SelectionAdapter() {
//	      public void widgetSelected(SelectionEvent evt) {
//	        DirectoryDialog dlg = new DirectoryDialog(cakeVersion.getShell());
//	        dlg.setFilterPath(cakeAppDir.getText());
//	        dlg.setMessage("Select CakePHP app folder (APP_DIR)");
//	        String selectedDir = dlg.open();
//	        cakeAppDir.setText(selectedDir != null ? selectedDir : CakeVersion.getVersion(cakeVersion.getText()).getAppDir().toOSString());
//	      }
//	    });	   
	    
	    
	    toggleProjectSpecificFields(initialEnableProjSpecificVal);
	}
	
//	private void addFirstSection(Composite parent) {
//		Composite composite = createDefaultComposite(parent);
//
//		//Label for path field
//		Label pathLabel = new Label(composite, SWT.NONE);
//		pathLabel.setText(PATH_TITLE);
//
//		// Path text field
//		Text pathValueText = new Text(composite, SWT.WRAP | SWT.READ_ONLY);
//		pathValueText.setText(((IResource) getElement()).getFullPath().toString());
//		
//		Combo combo = new Combo (composite, SWT.DROP_DOWN);
//		combo.setItems(PreferenceConstants.cakeVersionsList);		
//	}

	private void addSeparator(Composite parent) {
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(gridData);
	}


	private Composite createDefaultComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);

		return composite;
	}
	
	private void toggleProjectSpecificFields(Boolean projectSpecificEnabled) {
		cakeAppDir.setEnabled(projectSpecificEnabled);
		cakeVersion.setEnabled(projectSpecificEnabled);
	}

	/**
	 * Restore defaults button pressed
	 */
	protected void performDefaults() {
		super.performDefaults();
		cakeAppDir.setText(CakeVersion.getVersion(PreferenceConstants.DEFAULT_CAKE_VER).getAppDirName());
		cakeVersion.setText(PreferenceConstants.DEFAULT_CAKE_VER);
		useProjectSpecificSettings.setSelection(false);
		toggleProjectSpecificFields(false);
	}
	
	public boolean performOk() {
		try {
			((IResource) getElement()).setPersistentProperty(
				new QualifiedName("", PreferenceConstants.P_APP_DIR),
				cakeAppDir.getText());
			
			((IResource) getElement()).setPersistentProperty(
					new QualifiedName("", PreferenceConstants.P_CAKE_VER),
					cakeVersion.getText());	
			
			((IResource) getElement()).setPersistentProperty(
					new QualifiedName("", PreferenceConstants.P_ENABLE_PROJECT_SPECIFIC_SETTINGS),
					new Boolean(useProjectSpecificSettings.getSelection()).toString());	
						
		} catch (CoreException e) {
			return false;
		}
		return true;
	}

}