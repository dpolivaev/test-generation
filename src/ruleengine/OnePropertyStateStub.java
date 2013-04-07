package ruleengine;

import static ruleengine.TestUtils.set;

import java.util.Set;

class OnePropertyStateStub implements State {
	private String name;

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	private Object value;

	@Override
	public void setPropertyValue(Rule rule, Object value) {
		this.name = rule.getTargetedPropertyName();
		this.value = value;
	}

	@Override
	public boolean containsPropertyValue(String name) {
		return name.equals(this.name);
	}

	@Override
	public boolean containsPropertyValues(Set<String> names) {
		return names.isEmpty()
				|| containsPropertyValue(names.iterator().next());
	}

	public Rule ruleStub() {
		return TestUtils.ruleStub(name);
	}

	public PropertyAssignedEvent event() {
		return new PropertyAssignedEvent(this, ruleStub(), set());
	}

}