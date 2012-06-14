package com.doapps.cakephp.properties;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

import com.doapps.cakephp.preferences.PreferenceConstants;

public class CakePHPPropertyPage extends PropertyPage {
	private static final int TEXT_FIELD_WIDTH = 50;

	private Text cakeAppDir;
	private Combo cakeVersion;

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
		
		Label cakeVersionLabel = new Label(composite, SWT.NONE);
		cakeVersionLabel.setText("CakePHP version:");
		cakeVersion = new Combo (composite, SWT.DROP_DOWN);
		cakeVersion.setItems(PreferenceConstants.cakeVersionsList);
		try {
			String selectedItem = ((IResource) getElement()).getPersistentProperty(new QualifiedName("", PreferenceConstants.P_CAKE_VER));
			cakeVersion.setText((null != selectedItem) ? selectedItem : PreferenceConstants.DEFAULT_CAKE_VER);
		} catch (CoreException e) {
			cakeVersion.setText(PreferenceConstants.DEFAULT_CAKE_VER);
		}		
		
		
		Label appNameLabel = new Label(composite, SWT.NONE);
		appNameLabel.setText("App folder (APP_DIR):");		
		cakeAppDir = new Text(composite, SWT.SINGLE | SWT.BORDER);
		cakeAppDir.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridData gd = new GridData();
		gd.widthHint = convertWidthInCharsToPixels(TEXT_FIELD_WIDTH);
		cakeAppDir.setLayoutData(gd);

		try {
			String appFolderName =	((IResource) getElement()).getPersistentProperty(new QualifiedName("", PreferenceConstants.P_APP_DIR));
			cakeAppDir.setText((appFolderName != null) ? appFolderName : PreferenceConstants.DEFAULT_APP_DIR);
		} catch (CoreException e) {
			cakeAppDir.setText(PreferenceConstants.DEFAULT_APP_DIR);
		}
				
	    Button browseFS = new Button(composite, SWT.PUSH);
	    browseFS.setText("Browse"); //$NON-NLS-1$
	    browseFS.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent evt) {
	        DirectoryDialog dlg = new DirectoryDialog(cakeVersion.getShell());
	        dlg.setFilterPath(cakeAppDir.getText());
	        dlg.setMessage("Select CakePHP app folder (APP_DIR)");
	        String selectedDir = dlg.open();
	        cakeAppDir.setText(selectedDir != null ? selectedDir : PreferenceConstants.DEFAULT_APP_DIR);
	      }
	    });	   
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

	protected void performDefaults() {
		super.performDefaults();
		cakeAppDir.setText(PreferenceConstants.DEFAULT_APP_DIR);
		cakeVersion.setText(PreferenceConstants.DEFAULT_CAKE_VER);
	}
	
	public boolean performOk() {
		try {
			((IResource) getElement()).setPersistentProperty(
				new QualifiedName("", PreferenceConstants.P_APP_DIR),
				cakeAppDir.getText());
			
			((IResource) getElement()).setPersistentProperty(
					new QualifiedName("", PreferenceConstants.P_CAKE_VER),
					cakeVersion.getText());			
		} catch (CoreException e) {
			return false;
		}
		return true;
	}

}