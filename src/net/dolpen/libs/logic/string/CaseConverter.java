package net.dolpen.libs.logic.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文字列のケース変換周り
 */
public class CaseConverter {

    /**
     * @param str camelToLowerSnake
     * @return camel_to_lower_snake
     */
    public static String camelToLowerSnake(String str) {
        return str.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                .replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    /**
     * @param str camelToUpperSnake
     * @return CAMEL_TO_UPPER_SNAKE
     */
    public static String camelToUpperSnake(String str) {
        return str.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                .replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
    }

    /**
     * @param str snake_to_lower_camel
     * @return snakeToLowerCamel
     */
    public static String snakeToLowerCamel(String str) {
        Pattern p = Pattern.compile("_([a-z])");
        Matcher m = p.matcher(str.toLowerCase());
        StringBuffer sb = new StringBuffer(str.length());
        while (m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * @param str snake_to_upper_camel
     * @return SnakeToUpperCamel
     */
    public static String snakeToUpperCamel(String str) {
        Pattern p = Pattern.compile("_([a-z])");
        Matcher m = p.matcher(str.toLowerCase());
        StringBuffer sb = new StringBuffer(str.length());
        while (m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        sb.replace(0, 1, sb.substring(0, 1).toUpperCase());
        return sb.toString();
    }

}
