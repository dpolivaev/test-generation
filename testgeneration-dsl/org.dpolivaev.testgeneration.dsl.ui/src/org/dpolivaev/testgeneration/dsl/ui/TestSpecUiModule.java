/*
 * generated by Xtext
 */
package org.dpolivaev.testgeneration.dsl.ui;

import org.dpolivaev.testgeneration.dsl.ui.autoedit.TestSpecAutoEditStrategyProvider;
import org.dpolivaev.testgeneration.dsl.ui.outline.TestSpecQuickOutlineFilterAndSorter;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.editor.autoedit.AbstractEditStrategyProvider;
import org.eclipse.xtext.ui.editor.outline.quickoutline.QuickOutlineFilterAndSorter;

/**
 * Use this class to register components to be used within the IDE.
 */
public class TestSpecUiModule extends org.dpolivaev.testgeneration.dsl.ui.AbstractTestSpecUiModule {
	public TestSpecUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}

	public Class<? extends AbstractEditStrategyProvider>
    bindAbstractEditStrategyProvider() {
		return TestSpecAutoEditStrategyProvider.class;
	}
	
	public Class<? extends QuickOutlineFilterAndSorter>
	bindQuickOutlineFilterAndSorter(){
		return TestSpecQuickOutlineFilterAndSorter.class;
	}

}
