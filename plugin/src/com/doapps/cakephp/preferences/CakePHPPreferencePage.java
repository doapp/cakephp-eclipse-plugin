package com.doapps.cakephp.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import com.doapps.cakephp.Activator;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class CakePHPPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public CakePHPPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("CakePHP preferences:");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		String[][] labelsAndValues = new String[2][2];
		labelsAndValues[0][0] = "2.1";
		labelsAndValues[0][1] = "2.1";
		labelsAndValues[1][0] = "1.3";
		labelsAndValues[1][1] = "1.3";

		addField(new ComboFieldEditor(PreferenceConstants.P_CAKE_VER,
				"&CakePHP Version", labelsAndValues, getFieldEditorParent()));

		addField(new StringFieldEditor(PreferenceConstants.P_APP_FOLDER_NAME,
				"&App folder name (APP_DIR name):", getFieldEditorParent()));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}