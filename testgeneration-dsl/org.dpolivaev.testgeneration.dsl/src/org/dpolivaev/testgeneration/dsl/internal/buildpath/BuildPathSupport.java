/*******************************************************************************
 * Copyright (c) 2000, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.dpolivaev.testgeneration.dsl.internal.buildpath;


import java.io.File;
import java.io.FilenameFilter;

import org.osgi.framework.Version;
import org.dpolivaev.testgeneration.dsl.internal.StrategyDslPlugin;
import org.eclipse.equinox.frameworkadmin.BundleInfo;
import org.eclipse.osgi.service.resolver.VersionRange;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IAccessRule;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;


public class BuildPathSupport {
	public final static IPath CONTAINER_PATH= new Path("org.dpolivaev.testgeneration.dsl.TSGEN_CONTAINER");
	
	public static class PluginDescription {
		
		private final String bundleId;
		private final VersionRange versionRange;
		private final String bundleRoot;
		private final String binaryImportedRoot;
		private final String sourceBundleId;
		private final String repositorySource;
		private final String javadoc;

		public PluginDescription(String bundleId, VersionRange versionRange, String bundleRoot, String binaryImportedRoot, String sourceBundleId, String repositorySource, String javadoc) {
			this.bundleId= bundleId;
			this.versionRange= versionRange;
			this.bundleRoot= bundleRoot;
			this.binaryImportedRoot= binaryImportedRoot;
			this.sourceBundleId= sourceBundleId;
			this.repositorySource= repositorySource;
			this.javadoc= javadoc;
		}
		
		public IPath getBundleLocation() {
			return P2Utils.getBundleLocationPath(P2Utils.findBundle(bundleId, versionRange, false));
		}
		
		public IPath getSourceBundleLocation() {
			return getSourceLocation(P2Utils.findBundle(bundleId, versionRange, false));
		}
		
		public IClasspathEntry getLibraryEntry() {
			BundleInfo bundleInfo= P2Utils.findBundle(bundleId, versionRange, false);
			IPath bundleLocation= P2Utils.getBundleLocationPath(bundleInfo);
			if (bundleLocation != null) {
				
				IPath bundleRootLocation= getLibraryLocation(bundleInfo, bundleLocation);
				IPath srcLocation= getSourceLocation(bundleInfo);
				
				String javadocLocation=  "";
				if(javadoc != null)
					javadocLocation = Platform.getPreferencesService().getString(StrategyDslPlugin.PLUGIN_ID, javadoc, "", null); //$NON-NLS-1$
				IClasspathAttribute[] attributes;
				if (javadocLocation.length() == 0) {
					attributes= new IClasspathAttribute[0];
				} else {
					attributes= new IClasspathAttribute[] { JavaCore.newClasspathAttribute(IClasspathAttribute.JAVADOC_LOCATION_ATTRIBUTE_NAME, javadocLocation) };
				}
				
				return JavaCore.newLibraryEntry(bundleRootLocation, srcLocation, null, getAccessRules(), attributes, false);
			}
			return null;
		}

		public IAccessRule[] getAccessRules() {
			return new IAccessRule[0];
		}

		private IPath getLibraryLocation(BundleInfo bundleInfo, IPath bundleLocation) {
			IPath bundleRootLocation= null;
			if (bundleRoot != null)
				bundleRootLocation= getLocationIfExists(bundleInfo, bundleRoot);
			
			if (bundleRootLocation == null && binaryImportedRoot != null)
				bundleRootLocation= getLocationIfExists(bundleInfo, binaryImportedRoot);
			
			if (bundleRootLocation == null)
				bundleRootLocation= bundleLocation;
			return bundleRootLocation;
		}

		private IPath getSourceLocation(BundleInfo bundleInfo) {
			IPath srcLocation= null;
			if (repositorySource != null) {
				// Try source in workspace (from repository)
				srcLocation= getLocationIfExists(bundleInfo, repositorySource);
			}
			
			if (srcLocation == null) {
				BundleInfo sourceBundleInfo= null;
				if (bundleInfo != null) {
					// Try exact version
					sourceBundleInfo= P2Utils.findBundle(sourceBundleId, new Version(bundleInfo.getVersion()), true);
				}
				if (sourceBundleInfo == null) {
					// Try version range
					sourceBundleInfo= P2Utils.findBundle(sourceBundleId, versionRange, true);
				}
				srcLocation= P2Utils.getBundleLocationPath(sourceBundleInfo);
			}
			return srcLocation;
		}

		private IPath getLocationIfExists(BundleInfo bundleInfo, final String entryInBundle) {
			IPath srcLocation= null;
			IPath bundleLocationPath= P2Utils.getBundleLocationPath(bundleInfo);
			if (bundleLocationPath != null) {
				File bundleFile= bundleLocationPath.toFile();
				if (bundleFile.isDirectory()) {
					File srcFile= null;
					final int starIdx= entryInBundle.indexOf('*');
					if (starIdx != -1) {
						File[] files= bundleFile.listFiles(new FilenameFilter() {
							private String pre= entryInBundle.substring(0, starIdx);
							private String post= entryInBundle.substring(starIdx + 1);
							public boolean accept(File dir, String name) {
								return name.startsWith(pre) && name.endsWith(post);
							}
						});
						if (files.length > 0) {
							srcFile= files[0];
						}
					}
					if (srcFile == null)
						srcFile= new File(bundleFile, entryInBundle);
					if (srcFile.exists()) {
						srcLocation= new Path(srcFile.getPath());
						if (srcFile.isDirectory()) {
							srcLocation= srcLocation.addTrailingSeparator();
						}
					}
				}
			}
			return srcLocation;
		}
	}

	
	public static final PluginDescription TSGEN_ENGINE_PLUGIN= new PluginDescription(
			"org.dpolivaev.testgeneration.engine", VersionRange.emptyRange, "bin", "org.dpolivaev.testgeneration.engine_*.jar", "org.dpolivaev.testgeneration.engine.source", "src/main/java",  null);
	
	private static final PluginDescription GUAVA_PLUGIN= new PluginDescription(
			"com.google.guava", VersionRange.emptyRange, null, "com.google.guava_*.jar", "com.google.guava.source", "source-bundle/", null);
	
	private static final PluginDescription XBASE_LIB_PLUGIN= new PluginDescription(
			"org.eclipse.xtext.xbase.lib", VersionRange.emptyRange, null, "org.eclipse.xtext.xbase.lib_*.jar", "org.eclipse.xtext.xbase.source", "source-bundle/", null);
	
	/**
	 * @return the org.junit version 4 library, or <code>null</code> if not available
	 */
	public static IClasspathEntry getTsGenEngineEntry() {
		return TSGEN_ENGINE_PLUGIN.getLibraryEntry();
	}

	/**
	 * @return the org.hamcrest.core library, or <code>null</code> if not available
	 */
	public static IClasspathEntry getGuavaLibraryEntry() {
		return GUAVA_PLUGIN.getLibraryEntry();
	}
	/**
	 * @return the org.hamcrest.core library, or <code>null</code> if not available
	 */
	public static IClasspathEntry getXBaseLibraryEntry() {
		return XBASE_LIB_PLUGIN.getLibraryEntry();
	}

}
