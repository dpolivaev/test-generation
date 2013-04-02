package ruleengine;

class OnePropertyHolder implements PropertyHolder{
	private String name;
	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	private Object value;

	@Override
	public void setPropertyValue(String name, Object value) {
		this.name = name;
		this.value = value;
	}
	
}