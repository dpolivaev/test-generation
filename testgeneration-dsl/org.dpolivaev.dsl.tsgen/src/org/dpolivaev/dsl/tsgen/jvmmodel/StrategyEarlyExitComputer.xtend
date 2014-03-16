package org.dpolivaev.dsl.tsgen.jvmmodel

import org.eclipse.xtext.xbase.controlflow.DefaultEarlyExitComputer
import org.dpolivaev.dsl.tsgen.strategydsl.LabeledExpression
import java.util.Collection
import org.eclipse.xtext.xbase.controlflow.IEarlyExitComputer.ExitPoint

class StrategyEarlyExitComputer extends DefaultEarlyExitComputer{
	protected def Collection<ExitPoint> _exitPoints(LabeledExpression expression) {
		return getExitPoints(expression.expr);
	}
}