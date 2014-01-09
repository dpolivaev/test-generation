/*******************************************************************************
 * Copyright (c) 2006, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.dpolivaev.dsl.tsgen.internal.buildpath;

import org.dpolivaev.dsl.tsgen.internal.StrategyDslPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public class DslStrategyContainerInitializer extends ClasspathContainerInitializer {

	private static final IStatus NOT_SUPPORTED= new Status(IStatus.ERROR, StrategyDslPlugin.PLUGIN_ID, ClasspathContainerInitializer.ATTRIBUTE_NOT_SUPPORTED, new String(), null);
	private static final IStatus READ_ONLY= new Status(IStatus.ERROR, StrategyDslPlugin.PLUGIN_ID, ClasspathContainerInitializer.ATTRIBUTE_READ_ONLY, new String(), null);

	private static class TestGenerationContainer implements IClasspathContainer {

		private final IClasspathEntry[] fEntries;
		private final IPath fPath;

		public TestGenerationContainer(IPath path, IClasspathEntry... entries) {
			fPath= path;
			fEntries= entries;
		}

		public IClasspathEntry[] getClasspathEntries() {
			return fEntries;
		}

		public String getDescription() {
			return "Test generation library";
		}

		public int getKind() {
			return IClasspathContainer.K_APPLICATION;
		}

		public IPath getPath() {
			return fPath;
		}

	}


	public DslStrategyContainerInitializer() {
	}

	public void initialize(IPath containerPath, IJavaProject project) throws CoreException {
		TestGenerationContainer container= new TestGenerationContainer(containerPath,
				BuildPathSupport.getTsGenEngineEntry(), 
				BuildPathSupport.getGuavaLibraryEntry(),
				BuildPathSupport.getXBaseLibraryEntry());
		JavaCore.setClasspathContainer(containerPath, new IJavaProject[] { project }, 	new IClasspathContainer[] { container }, null);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.ClasspathContainerInitializer#canUpdateClasspathContainer(org.eclipse.core.runtime.IPath, org.eclipse.jdt.core.IJavaProject)
	 */
	public boolean canUpdateClasspathContainer(IPath containerPath, IJavaProject project) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.ClasspathContainerInitializer#getAccessRulesStatus(org.eclipse.core.runtime.IPath, org.eclipse.jdt.core.IJavaProject)
	 */
	public IStatus getAccessRulesStatus(IPath containerPath, IJavaProject project) {
		return READ_ONLY;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.ClasspathContainerInitializer#getSourceAttachmentStatus(org.eclipse.core.runtime.IPath, org.eclipse.jdt.core.IJavaProject)
	 */
	public IStatus getSourceAttachmentStatus(IPath containerPath, IJavaProject project) {
		return READ_ONLY;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.ClasspathContainerInitializer#getAttributeStatus(org.eclipse.core.runtime.IPath, org.eclipse.jdt.core.IJavaProject, java.lang.String)
	 */
	public IStatus getAttributeStatus(IPath containerPath, IJavaProject project, String attributeKey) {
		if (attributeKey.equals(IClasspathAttribute.JAVADOC_LOCATION_ATTRIBUTE_NAME)) {
			return Status.OK_STATUS;
		}
		return NOT_SUPPORTED;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.ClasspathContainerInitializer#getComparisonID(org.eclipse.core.runtime.IPath, org.eclipse.jdt.core.IJavaProject)
	 */
	public Object getComparisonID(IPath containerPath, IJavaProject project) {
		return containerPath;
	}

}
