package ruleengine;

class State {
	private StringBuilder assignedPropertiesAsString; 
	private int count = 0;	
	
	
	void addProperty(String name, Object value) {
		if(assignedPropertiesAsString.length() > 0)
			assignedPropertiesAsString.append('\t');
		this.assignedPropertiesAsString.append(name).append( "=" ).append(value);
	}

	public void nextIteration() {
		this.count++;
		assignedPropertiesAsString = new StringBuilder(); 
		
	}

	String getAssignedPropertiesAsString() {
		if(count == 0)
			return "";
		else
			return count + " : " + assignedPropertiesAsString + '\n';
	}
	
}