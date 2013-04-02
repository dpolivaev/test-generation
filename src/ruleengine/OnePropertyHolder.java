package ruleengine;

class OnePropertyHolder implements PropertySet{
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