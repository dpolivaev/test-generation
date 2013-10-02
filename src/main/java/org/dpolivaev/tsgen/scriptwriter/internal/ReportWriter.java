package org.dpolivaev.tsgen.scriptwriter.internal;

import java.util.Collection;
import java.util.Map;
import org.dpolivaev.tsgen.coverage.CoverageEntry;
import org.dpolivaev.tsgen.coverage.CoverageStatus;
import org.dpolivaev.tsgen.coverage.Goal;
import org.dpolivaev.tsgen.coverage.GoalChecker;
import org.dpolivaev.tsgen.scriptwriter.ScriptConfiguration;

public class ReportWriter {



	final private HandlerFactory handlerFactory;
	private XmlWriter xmlWriter;

	public ReportWriter(ResultFactory resultFactory) {
        handlerFactory = new HandlerFactory(resultFactory);
    }

	public void createReport(GoalChecker goalChecker, ScriptConfiguration scriptConfiguration) {
		final Collection<Goal> goals = goalChecker.goals();
		if(! goals.isEmpty()){
			xmlWriter = new SaxXmlWriter(handlerFactory.newHandler(scriptConfiguration));
			xmlWriter.startDocument();
			xmlWriter.beginElement("Report");
			for(Goal goal : goals)
				createGoalReport(goal);
			xmlWriter.endElement("Report");
			xmlWriter.endDocument();
			xmlWriter = null;
		}
	}

	private void createGoalReport(Goal goal) {
		xmlWriter.beginElement("Goal");
		xmlWriter.setAttribute("name", goal.name());
		xmlWriter.setAttribute("required", Integer.toString(goal.checkList().requiredItemNumber()));
		xmlWriter.setAttribute("achieved", Integer.toString(goal.checkList().achievedItemNumber()));
		for (Map.Entry<CoverageEntry, CoverageStatus> entry : goal.checkList().coveredEntries())
			writeItemReport(entry.getKey(), entry.getValue());
		xmlWriter.endElement("Goal");
	}

	private void writeItemReport(CoverageEntry coverageEntry, CoverageStatus coverageStatus) {
		xmlWriter.beginElement("Item");
		xmlWriter.setAttribute("name", coverageEntry.getName());
		xmlWriter.setAttribute("required", Integer.toString(coverageStatus.required));
		xmlWriter.setAttribute("reached", Integer.toString(coverageStatus.reached));
		xmlWriter.addTextContent(coverageEntry.getReason().toString());
		xmlWriter.endElement("Item");
	}
}
