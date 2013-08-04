package utils;

import java.util.Collection;

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

}
