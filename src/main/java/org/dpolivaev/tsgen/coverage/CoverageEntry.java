package org.dpolivaev.tsgen.coverage;

public class CoverageEntry {

	final private String goal;
	final private Object reason;

	public CoverageEntry(String goal, Object reason) {
		this.goal = goal;
		this.reason = reason;
	}

	public String getGoal() {
		return goal;
	}

	public Object getReason() {
		return reason;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((reason == null) ? 0 : reason.hashCode());
		result = prime * result
				+ ((goal == null) ? 0 : goal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoverageEntry other = (CoverageEntry) obj;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (goal == null) {
			if (other.goal != null)
				return false;
		} else if (!goal.equals(other.goal))
			return false;
		return true;
	}

}
