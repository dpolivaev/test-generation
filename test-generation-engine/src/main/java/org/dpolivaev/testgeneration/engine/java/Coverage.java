package org.dpolivaev.testgeneration.engine.java;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Coverage {
	GoalCoverage[] first() default {};
	GoalCoverage[] again() default {};
}
