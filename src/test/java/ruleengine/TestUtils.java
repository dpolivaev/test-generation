package ruleengine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestUtils {
    @SafeVarargs
    public static <T> Set<T> set(T... elements) {
        HashSet<T> set = new HashSet<T>();
        set.addAll(Arrays.asList(elements));
		return set;
	}

}
