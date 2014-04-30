package org.dpolivaev.testgeneration.dsl.ui.launch

import org.dpolivaev.testgeneration.dsl.strategydsl.Generation
import org.eclipse.core.runtime.CoreException
import org.eclipse.debug.core.DebugPlugin
import org.eclipse.debug.core.ILaunchConfiguration
import org.eclipse.debug.ui.DebugUITools
import org.eclipse.debug.ui.ILaunchShortcut
import org.eclipse.debug.ui.RefreshTab
import org.eclipse.jface.dialogs.MessageDialog
import org.eclipse.jface.viewers.ISelection
import org.eclipse.ui.IEditorPart
import org.eclipse.ui.IFileEditorInput
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.util.Strings
import org.eclipse.xtext.xbase.ui.editor.XbaseEditor

import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.*
import static org.eclipse.jface.dialogs.MessageDialog.*
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.core.resources.IFile
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.ui.resource.IResourceSetProvider
import com.google.inject.Inject

class StrategyDSLLaunchShortcut implements ILaunchShortcut {
	@Inject
    IResourceSetProvider resourceSetProvider;
    
	public static val String BUNDLE_ID = "org.dpolivaev.testgeneration.dsl.ui"

	override launch(ISelection selection, String mode) {
        if (selection instanceof IStructuredSelection) {
            val structuredSelection = selection as IStructuredSelection
            val firstElement = structuredSelection.firstElement
            if (firstElement instanceof IFile) {
                val file =  firstElement as IFile
                val project = file.project;
                 
                val uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true)
                val rs = resourceSetProvider.get(project)
                val r = rs.getResource(uri, true) as XtextResource
				val info = new LaunchConfigurationInfo(project.name, qualifiedClassName(r))
				launch(mode, info)
				return
                 
            }
        }
		MessageDialog::openError(null, "Unsupported Launch Selection.", 
			""
		)
	}
	
	override launch(IEditorPart editor, String mode) {
		if (editor instanceof XbaseEditor) {
			val xbaseEditor = editor as XbaseEditor
			if (xbaseEditor.editorInput instanceof IFileEditorInput) {
				val project = (xbaseEditor.editorInput as IFileEditorInput).file.project
				val info = xbaseEditor.document.readOnly [
					new LaunchConfigurationInfo(project.name, qualifiedClassName(it))
				]
				launch(mode, info)
				return
			}
		} 
		openError(null, "Wrong editor kind.", "")
	}

	def qualifiedClassName(XtextResource it) {
		val generation = contents.filter(Generation).head
		if(generation == null)
			return null
		val className = generation.eResource.URI.trimFileExtension.lastSegment
		val qualifiedClassName = if(generation.package != null) generation.package + '.' + className else className
		qualifiedClassName
	}

	def launch(String mode, LaunchConfigurationInfo info) {
		if (info.getClazz.isNullOrEmpty)    
			openError(null, "Launch Error", "Could not determine the class that should be executed.")
		else if (info.getProject.isNullOrEmpty)  
			openError(null, "Launch Error", "Could not determine the project that should be executed.")
		else try {
			val configs = DebugPlugin::getDefault.launchManager.launchConfigurations
			val config = configs.findFirst[info.configEquals(it)] ?: info.createConfiguration 
			DebugUITools::launch(config, mode)
		} catch (CoreException e) {
			openError(null, "Problem running workflow.", e.message)
		}
	}
}

@Data class LaunchConfigurationInfo {
	val String project
	val String clazz
	
	def getName() {
		Strings::lastToken(getClazz, ".")
	}
	
	def createConfiguration()  {
		val launchManager = DebugPlugin::getDefault.launchManager
		val configType = launchManager.getLaunchConfigurationType("org.dpolivaev.testgeneration.dsl.ui.StrategyLaunchConfigurationType")
		val wc = configType.newInstance(null, getName)
		wc.setAttribute(ATTR_PROJECT_NAME, getProject)
		wc.setAttribute(ATTR_MAIN_TYPE_NAME, getClazz)
		wc.setAttribute(ATTR_STOP_IN_MAIN, false)
		wc.setAttribute(RefreshTab::ATTR_REFRESH_SCOPE, "${workspace}")
		wc.setAttribute(RefreshTab::ATTR_REFRESH_RECURSIVE, true)
		wc.doSave
	}

	def configEquals(ILaunchConfiguration a) {
		a.getAttribute(ATTR_MAIN_TYPE_NAME, "X") == getClazz && 
		a.getAttribute(ATTR_MAIN_TYPE_NAME, "X") == getProject &&
		a.type.identifier == "org.dpolivaev.testgeneration.dsl.ui.StrategyLaunchConfigurationType"
	}
	
	def isValid() {
		!getClazz.isNullOrEmpty && !getProject.isNullOrEmpty
	}
}