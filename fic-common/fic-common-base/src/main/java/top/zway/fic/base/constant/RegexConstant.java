package top.zway.fic.base.constant;

import java.util.regex.Pattern;

/**
 * @author ZZJ
 */
public class RegexConstant {
    public static final Pattern EMAIL_REGEX = Pattern.compile("^([a-zA-Z0-9]+[_|_|\\-|.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|_|.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,6}$");
}
