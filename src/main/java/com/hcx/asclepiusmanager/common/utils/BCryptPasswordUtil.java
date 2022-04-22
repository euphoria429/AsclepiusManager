package com.hcx.asclepiusmanager.common.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author huangcaixia
 * @Description
 * @date 2022/4/20 12:05
 */

public class BCryptPasswordUtil {

    public static String encryptPassword(Object plaintextPassword) throws IllegalArgumentException {
        final String str;
        if (plaintextPassword instanceof char[]) {
            str = new String((char[]) plaintextPassword);
        } else if (plaintextPassword instanceof String) {
            str = (String) plaintextPassword;
        } else {
            throw new IllegalArgumentException("Unsupported password type: " + plaintextPassword.getClass().getName());
        }
        return BCrypt.hashpw(str, BCrypt.gensalt());
    }

    public static boolean passwordsMatch(Object submittedPlaintext, String encrypted) {
        return BCrypt.checkpw(new String((char[]) submittedPlaintext), encrypted);
    }

    public static boolean passwordsMatchString(String submittedPlaintext, String encrypted) {
        return BCrypt.checkpw( submittedPlaintext, encrypted);
    }
}
