package org.dpolivaev.tsgen.utils.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static RuntimeException runtimeException(Exception e) {
        if(e instanceof RuntimeException)
            return ((RuntimeException)e);
        else
            return new RuntimeException(e);
    }

    public static void addMatchingStrings(Collection<String> availableProperties, String startWith, Collection<String> strings) {
        for(String string : strings)
            if(string.startsWith(startWith))
                availableProperties.add(string);
    }

	@SafeVarargs
	public static <T> Set<T> set(T... elements) {
	    HashSet<T> set = new HashSet<T>();
	    set.addAll(Arrays.asList(elements));
		return set;
	}

}
