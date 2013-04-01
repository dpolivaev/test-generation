package ruleengine;

class State {
	private StringBuilder assignedPropertiesAsString = new StringBuilder(); 
	private int count = 0;	
	
	
	void addProperty(String name, Object value) {
		if(assignedPropertiesAsString.length() > 0)
			assignedPropertiesAsString.append('\t');
		this.assignedPropertiesAsString.append(name).append( "=" ).append(value);
	}

	public void nextIteration() {
		this.count++;
		assignedPropertiesAsString.setLength(0); 
		
	}

	String getAssignedPropertiesAsString() {
			return count + " : " + assignedPropertiesAsString + '\n';
	}
	
}