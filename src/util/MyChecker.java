package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyChecker {
    private static final String regexEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String regexPassword = "^(?=.*\\d)(?=.*[A-Z])[.@/!;:,$%^&*+=-_|()#`~'\"A-Za-z\\d]{8,}$";

    public static boolean notEmail(String email){
        Pattern pattern = Pattern.compile(regexEmail);
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }

    public static boolean isGoodPassword(String pass){
        Pattern pattern = Pattern.compile(regexPassword);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }

}
