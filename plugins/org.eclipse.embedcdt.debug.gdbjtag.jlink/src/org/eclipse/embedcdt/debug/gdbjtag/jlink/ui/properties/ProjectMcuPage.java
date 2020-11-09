/*******************************************************************************
 * Copyright (c) 2015 Liviu Ionescu.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Liviu Ionescu - initial version
 *******************************************************************************/

package org.eclipse.embedcdt.debug.gdbjtag.jlink.ui.properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.embedcdt.core.preferences.ScopedPreferenceStoreWithoutDefaults;
import org.eclipse.embedcdt.core.ui.FieldEditorPropertyPage;
import org.eclipse.embedcdt.core.ui.XpackDirectoryNotStrictFieldEditor;
import org.eclipse.embedcdt.debug.gdbjtag.jlink.Activator;
import org.eclipse.embedcdt.debug.gdbjtag.jlink.preferences.DefaultPreferences;
import org.eclipse.embedcdt.debug.gdbjtag.jlink.preferences.PersistentPreferences;
import org.eclipse.embedcdt.debug.gdbjtag.jlink.ui.Messages;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;

public class ProjectMcuPage extends FieldEditorPropertyPage {

	// ------------------------------------------------------------------------

	public static final String ID = "org.eclipse.embedcdt.debug.gdbjtag.jlink.projectPropertiesPage";

	// ------------------------------------------------------------------------

	private PersistentPreferences fPersistentPreferences;
	private DefaultPreferences fDefaultPreferences;

	// ------------------------------------------------------------------------

	public ProjectMcuPage() {
		super(GRID);

		if (Activator.getInstance().isDebugging()) {
			System.out.println("jlink.ProjectMcuPage()");
		}

		fPersistentPreferences = Activator.getInstance().getPersistentPreferences();
		fDefaultPreferences = Activator.getInstance().getDefaultPreferences();

		setDescription(Messages.ProjectMcuPagePropertyPage_description);
	}

	// ------------------------------------------------------------------------

	protected IPreferenceStore doGetPreferenceStore() {

		Object element = getElement();
		if (element instanceof IProject) {
			if (Activator.getInstance().isDebugging()) {
				System.out.println("jlink.ProjectMcuPage.doGetPreferenceStore() project store");
			}
			return new ScopedPreferenceStoreWithoutDefaults(new ProjectScope((IProject) element), Activator.PLUGIN_ID);
		}
		return null;
	}

	@Override
	protected void createFieldEditors() {

		FieldEditor executable = new StringFieldEditor(PersistentPreferences.EXECUTABLE_NAME,
				Messages.McuPage_executable_label, getFieldEditorParent());
		addField(executable);

		boolean isStrict = fPersistentPreferences.getFolderStrict();

		String[] xpackNames = fDefaultPreferences.getXpackNames();

		FieldEditor folder = new XpackDirectoryNotStrictFieldEditor(xpackNames, PersistentPreferences.INSTALL_FOLDER,
				Messages.McuPage_executable_folder, getFieldEditorParent(), isStrict);
		addField(folder);
	}

	// ------------------------------------------------------------------------
}
