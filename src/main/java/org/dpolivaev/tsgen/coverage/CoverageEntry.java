package org.dpolivaev.tsgen.coverage;

public class CoverageEntry{

	final private String name;
	final private String reason;

	public CoverageEntry(String name, String reason) {
		this.name = name;
		this.reason = reason;
	}

	public String getName() {
		return name;
	}

	public String getReason() {
		return reason;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + reason.hashCode();
		result = prime * result + name.hashCode();
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
		if (!reason.equals(other.reason))
			return false;
		if (!name.equals(other.name))
			return false;
		return true;
	}
}
