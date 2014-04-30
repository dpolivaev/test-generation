package org.dpolivaev.testgeneration.engine.ruleengine;


/**
 * @author Dimitry Polivaev 18.02.2013
 */
public interface PropertyHandler {
	PropertyHandler DO_NOTHING = new PropertyCombinationHandler() {
		@Override
		public void handlePropertyCombination(PropertyContainer propertyContainer) {
		}
	};

	void generationStarted(PropertyContainer propertyContainer);
	void handlePropertyCombination(PropertyContainer propertyContainer);
	void generationFinished();
}
