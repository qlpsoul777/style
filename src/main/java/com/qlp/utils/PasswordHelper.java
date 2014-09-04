package com.qlp.utils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Created by admin on 2014/9/4.
 */
public final class PasswordHelper {
    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private static final int hashIterations = 2;

    public static String[] encryptPassword(String loginName,String password){
        String salt = randomNumberGenerator.nextBytes().toHex();
        String newWord = new SimpleHash("MD5",password, ByteSource.Util.bytes(loginName + salt),hashIterations).toHex();
        return new String[]{newWord,salt};
    }
}
