package utils;

public class Utils {

    public static RuntimeException runtimeException(Exception e) {
        if(e instanceof RuntimeException)
            return ((RuntimeException)e);
        else
            return new RuntimeException(e);
    }

}
