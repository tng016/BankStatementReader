package Regex;

import java.util.regex.*;

/**
 * Created by tzeyangng on 9/1/17.
 */
public class RegexChecker {
    public static boolean regexContains(String regex, String str){

        Pattern checkRegex = Pattern.compile(regex);

        Matcher regexMatcher = checkRegex.matcher(str);

        return regexMatcher.find();
    }

    //returns starting index
    public static int regexStart(String regex, String str){

        Pattern checkRegex = Pattern.compile(regex);

        Matcher regexMatcher = checkRegex.matcher(str);

        return regexMatcher.start();
    }

    public static String regexExtractor(String regex, String str){

        Pattern checkRegex = Pattern.compile(regex);

        Matcher regexMatcher = checkRegex.matcher(str);

        while (regexMatcher.find()){
            if (regexMatcher.group().length()!=0) {
                return (regexMatcher.group().trim());
            }
        }
        return null;
    }

    public static String regexExtractorLast(String regex, String str){

        Pattern checkRegex = Pattern.compile(regex);

        Matcher regexMatcher = checkRegex.matcher(str);

        String s = "";

        while (regexMatcher.find()){
            if (regexMatcher.group().length()!=0) {
                s = (regexMatcher.group().trim());
            }
        }
        return s;
    }

}
