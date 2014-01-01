package org.dpolivaev.tsgen.scriptwriter.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
		final ArrayList<Goal> goals = new ArrayList<>(goalChecker.goals());
		Collections.sort(goals, new Comparator<Goal>() {

			@Override
			public int compare(Goal o1, Goal o2) {
				return o1.name().compareTo(o2.name());
			}
		});
		if(! goals.isEmpty() && scriptConfiguration.isFileValid()){
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
		ArrayList<Entry<CoverageEntry, CoverageStatus>>  coveredEntries = new ArrayList<>(goal.checkList().coveredEntries());
		Collections.sort(coveredEntries, new Comparator<Map.Entry<CoverageEntry, CoverageStatus>>() {

			@Override
			public int compare(Entry<CoverageEntry, CoverageStatus> o1,
					Entry<CoverageEntry, CoverageStatus> o2) {
				CoverageEntry entry1 = o1.getKey();
				CoverageEntry entry2 = o2.getKey();
				int nameComparison = entry1.getName().compareTo(entry2.getName());
				if(nameComparison != 0)
					return nameComparison;
				return compareReasons(entry1, entry2);
				
			}

			public int compareReasons(CoverageEntry entry1, CoverageEntry entry2) {
				String reason1 = entry1.getReason();
				String reason2 = entry2.getReason();
				if (reason1 == null && reason2 != null)
					return 1;
				else if(reason2 == null)
					return -1;
				else 
					return reason1.compareTo(reason2);
			}
		});
		for (Map.Entry<CoverageEntry, CoverageStatus> entry : coveredEntries)
			writeItemReport(entry.getKey(), entry.getValue());
		xmlWriter.endElement("Goal");
	}

	private void writeItemReport(CoverageEntry coverageEntry, CoverageStatus coverageStatus) {
		xmlWriter.beginElement("Item");
		xmlWriter.setAttribute("name", coverageEntry.getName());
		xmlWriter.setAttribute("required", Integer.toString(coverageStatus.required));
		xmlWriter.setAttribute("reached", Integer.toString(coverageStatus.reached));
		String reason = coverageEntry.getReason();
		if(reason != null)
			xmlWriter.addTextContent(reason.toString());
		else
			xmlWriter.addTextContent("total");
		xmlWriter.endElement("Item");
	}
}
