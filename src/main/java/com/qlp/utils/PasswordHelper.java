package com.qlp.utils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.qlp.sys.entity.User;

/**
 * Created by admin on 2014/9/4.
 */
public final class PasswordHelper {
    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private static final int hashIterations = 2;

    /**
     * 
     * @param user
     * @param password
     * @param flag:true-->生成新的加盐值;flag:false-->用数据库中存储的加盐值
     * @return
     */
    public static String[] encryptPassword(User user,String password,boolean flag){
    	String salt = user.getSalt();
    	if(flag){
    		salt = randomNumberGenerator.nextBytes().toHex();
    	}
        String newWord = new SimpleHash("MD5",password, ByteSource.Util.bytes(user.getLoginName() + salt),hashIterations).toHex();
        return new String[]{newWord,salt};
    }
    
}
