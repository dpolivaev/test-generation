package org.dpolivaev.testgeneration.engine.scriptwriter.internal;

import static org.mockito.Mockito.verify;

import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry;
import org.dpolivaev.testgeneration.engine.coverage.internal.RequirementChecker;
import org.dpolivaev.testgeneration.engine.scriptwriter.WriterFactory;
import org.dpolivaev.testgeneration.engine.utils.internal.Utils;
import org.junit.Test;
import org.mockito.Mockito;

public class RequirementCheckerTest {

	private static final String SOME_NAME = "name1";
	private static final String OTHER_NAME = "name2";
	private static final String SOME_REASON = "reason1";
	private static final String OTHER_REASON = "reason2";

	@Test
	public void registersRequirementsByRunner() {
		final RequirementChecker requirementChecker = new RequirementChecker();
		final CoverageEntry coverageEntry1 = new CoverageEntry(SOME_NAME, SOME_REASON);
		requirementChecker.addItems(coverageEntry1);
		final RequirementChecker otherRequirementChecker = new RequirementChecker();
		final CoverageEntry coverageEntry2 = new CoverageEntry(OTHER_NAME, OTHER_REASON);
		requirementChecker.addItems(coverageEntry2);
		final RequirementChecker compositeChecker = requirementChecker.with(otherRequirementChecker);
		WriterFactory runner = Mockito.mock(WriterFactory.class);
		compositeChecker.registerRequiredItems(runner);
		
		verify(runner).registerRequiredItems(Utils.set(coverageEntry1, coverageEntry2));
		 
	}

}
