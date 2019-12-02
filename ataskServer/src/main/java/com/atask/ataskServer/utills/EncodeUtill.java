package com.atask.ataskServer.utills;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Created by zhong on 2019-7-10.
 */
public class EncodeUtill {
    public static String encodeBase64(String str)
    {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public static String decodeBase64(String str)
    {
        return new String(Base64.getDecoder().decode(str));
    }

    public static String encodeHmac256(String str,String secret)
    {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            return Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(str.getBytes()));
        }catch (Exception ex)
        {
            return null;
        }
    }

}
