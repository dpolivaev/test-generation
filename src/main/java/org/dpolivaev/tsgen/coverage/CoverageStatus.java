package org.dpolivaev.tsgen.coverage;

public class CoverageStatus {
	final public int required;
	final public int reached;
	public CoverageStatus(int required, int reached) {
		super();
		this.required = required;
		this.reached = reached;
	}
}
