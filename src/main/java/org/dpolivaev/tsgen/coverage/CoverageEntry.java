package org.dpolivaev.tsgen.coverage;

public class CoverageEntry {

	final private String name;
	final private Object reason;

	public CoverageEntry(String name, Object reason) {
		this.name = name;
		this.reason = reason;
	}

	public String getName() {
		return name;
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
				+ ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
