package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyChecker {
    private static final String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static boolean isEmail(String email){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
