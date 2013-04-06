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
}
