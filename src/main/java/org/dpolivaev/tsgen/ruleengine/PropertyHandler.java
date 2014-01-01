package org.dpolivaev.tsgen.ruleengine;


/**
 * @author Dimitry Polivaev 18.02.2013
 */
public interface PropertyHandler {
	PropertyHandler DO_NOTHING = new PropertyHandler() {
		@Override
		public void handlePropertyCombination(PropertyContainer propertyContainer) {
		}
	};

	void handlePropertyCombination(PropertyContainer propertyContainer);
}
