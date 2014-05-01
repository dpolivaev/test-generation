package org.dpolivaev.testgeneration.dsl.jvmmodel

import org.eclipse.xtext.xbase.controlflow.DefaultEarlyExitComputer
import org.dpolivaev.testgeneration.dsl.testspec.LabeledExpression
import java.util.Collection
import org.eclipse.xtext.xbase.controlflow.IEarlyExitComputer.ExitPoint

class StrategyEarlyExitComputer extends DefaultEarlyExitComputer{
	protected def Collection<ExitPoint> _exitPoints(LabeledExpression expression) {
		return getExitPoints(expression.expr);
	}
}