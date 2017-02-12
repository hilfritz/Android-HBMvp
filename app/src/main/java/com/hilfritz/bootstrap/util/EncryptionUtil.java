package com.hilfritz.bootstrap.util;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

/**
 * Created by hilfritz on 8/1/16.
 */
public class EncryptionUtil {

    private static String KEY_TAG = "sample";

    public static String encrypt(String message){
        String retVal = "";
        if(!StringUtil.isStringEmpty(message)) {
            try {
                retVal = AESCrypt.encrypt(KEY_TAG, message);
            } catch (GeneralSecurityException e) {
                //handle error
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return retVal;
    }

    public static String decrypt(String message){
        String retVal = "";
        if(!StringUtil.isStringEmpty(message) && message.matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$")) {
            try {
                retVal = AESCrypt.decrypt(KEY_TAG, message);
            } catch (GeneralSecurityException e) {
                //handle error - could be due to incorrect password or tampered encryptedMsg

                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return retVal;
    }
}
