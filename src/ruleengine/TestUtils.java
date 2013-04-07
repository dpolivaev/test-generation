package ruleengine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestUtils {
	public static Set<String> set(String... names) {
		HashSet<String> set = new HashSet<String>();
		set.addAll(Arrays.asList(names));
		return set;
	}

	public static Rule ruleStub(final String targetedPropertyName) {
		return new RuleStub(targetedPropertyName);
	}

	public static void performCombination(Rule rule, State state) {
		rule.nextCombination(state);
		rule.finishCombination(state);
	}

}
