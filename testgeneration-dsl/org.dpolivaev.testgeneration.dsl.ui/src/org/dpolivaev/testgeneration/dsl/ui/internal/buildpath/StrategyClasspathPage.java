/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.dpolivaev.testgeneration.dsl.ui.internal.buildpath;

import org.dpolivaev.testgeneration.dsl.internal.buildpath.BuildPathSupport;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPage;
import org.eclipse.jdt.ui.wizards.NewElementWizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
  */
public class StrategyClasspathPage extends NewElementWizardPage implements IClasspathContainerPage{

	public StrategyClasspathPage() {
		super("StrategyClasspathPage"); //$NON-NLS-1$
	}

	public void createControl(Composite parent) {
		final Label label = new Label(parent, 0);
		label.setText("Test generation library");
		setControl(label);
	}

	public boolean finish() {
		return true;
	}

	public IClasspathEntry getSelection() {
		return JavaCore.newContainerEntry(BuildPathSupport.CONTAINER_PATH);
	}

	public void setSelection(IClasspathEntry containerEntry) {
	}

}
