package org.dpolivaev.testgeneration.dsl.ui.autoedit;

import org.eclipse.xtext.ui.editor.autoedit.DefaultAutoEditStrategyProvider;

public class TestSpecAutoEditStrategyProvider extends DefaultAutoEditStrategyProvider{
	@Override
	protected void configureCurlyBracesBlock(IEditStrategyAcceptor acceptor){}
	@Override
	protected void configureSquareBrackets(IEditStrategyAcceptor acceptor) {}

	@Override
	protected void configureParenthesis(IEditStrategyAcceptor acceptor) {}

	protected void configureCompoundBracesBlocks(IEditStrategyAcceptor acceptor) {}

	@Override
	protected void configureStringLiteral(IEditStrategyAcceptor acceptor) {}

}
