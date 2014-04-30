package org.dpolivaev.testgeneration.engine.java;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GoalCoverage {
	String goal();
	String item(); 
	String[] coverage();
}
