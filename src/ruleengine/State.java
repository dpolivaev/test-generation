package ruleengine;

class State implements PropertySet {
	private StringBuilder assignedPropertiesAsString = new StringBuilder(); 
	private int count = 0;	
	
	
	/* (non-Javadoc)
	 * @see ruleengine.PropertySet#setPropertyValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setPropertyValue(String name, Object value) {
		if(assignedPropertiesAsString.length() > 0)
			assignedPropertiesAsString.append('\t');
		this.assignedPropertiesAsString.append(name).append( "=" ).append(value);
	}

	public void nextIteration() {
		this.count++;
		assignedPropertiesAsString.setLength(0); 
		
	}

	public String getAssignedPropertiesAsString() {
			return count + " : " + assignedPropertiesAsString + '\n';
	}
	
}