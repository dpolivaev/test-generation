package org.dpolivaev.tsgen.coverage;

public class CoverageEntry{

	public static final String ANY = null;
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
	
	public CoverageEntry forAnyReason(){
		return reason == ANY ? this : new CoverageEntry(name, ANY);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CoverageEntry [name=" + name + ", reason=" + reason + "]";
	}
	
	
}
