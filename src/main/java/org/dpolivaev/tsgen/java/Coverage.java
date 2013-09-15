package org.dpolivaev.tsgen.java;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Coverage {
	RequirementCoverage[] first() default {};
	RequirementCoverage[] again() default {};
}
