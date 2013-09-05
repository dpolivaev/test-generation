package org.dpolivaev.tsgen.coverage;

public class CoverageEntry {

	final private String requirementId;
	final private String description;

	public CoverageEntry(String requirementId, String description) {
		this.requirementId = requirementId;
		this.description = description;
	}

	public String getRequirementId() {
		return requirementId;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((requirementId == null) ? 0 : requirementId.hashCode());
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
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (requirementId == null) {
			if (other.requirementId != null)
				return false;
		} else if (!requirementId.equals(other.requirementId))
			return false;
		return true;
	}

}
