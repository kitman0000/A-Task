package com.atask.ataskServer.utills;

import com.atask.ataskServer.userModule.entry.UsrInfoPayLoad;

/**
 * Created by zhong on 2019-7-10.
 */
public class JwtUtil {
    private String token;

    private String header;
    private String payload;
    private String signature;

	// 密钥
    private static final String SECRET = ;

    /**
     * 设置Header，默认使用HS256算法
     */
    public void setHeader()
    {
        header = EncodeUtill.encodeBase64("{" +
                "'typ':'JWT'," +
                "'alg':'HS256'" +
                "'iat':'" + System.currentTimeMillis() + "'" +
                "}");
    }

    /**
     * 设置payload
     * @param sub 用户id
     */
    public void setPayload(String sub,int accountId)
    {
        String payloadClearTxt = "{"+
                                "\"sub\":\"" + sub + "\","+
                                "\"account\":\"" + accountId + "\"" +
                                "}";
        payload = EncodeUtill.encodeBase64(payloadClearTxt);
    }

    /**
     * 签名
     */
    public void createSignature()
    {
        signature = EncodeUtill.encodeHmac256(EncodeUtill.encodeBase64(header) + "." + EncodeUtill.encodeBase64(payload), SECRET);
    }

    /**
     * 生成token
     */
    public void createToken()
    {
        token = header + "." + payload + "." + signature;
    }

    public String getToken()
    {
        return token;
    }

    /**
     * 检查token有效性
     * @param token token
     * @return 是否有效
     */
    public static boolean checkToken(String token)
    {
        if(token.equals("null"))    // 空token
            return false;

        String[] tokenParts = token.split("\\.");
        String header = tokenParts[0];
        String payload = tokenParts[1];
        String signature = tokenParts[2];

        // 检查签名是否正确
        String tempSignature = EncodeUtill.encodeHmac256(EncodeUtill.encodeBase64(header) + "." + EncodeUtill.encodeBase64(payload), SECRET);
        return tempSignature.equals(signature);
    }

    public static UsrInfoPayLoad getUsrInfoPayLoad(String token)
    {
        String[] tokenParts = token.split("\\.");
        String payload = tokenParts[1];
        return (UsrInfoPayLoad) JsonUtil.jsonToObject(EncodeUtill.decodeBase64(payload),UsrInfoPayLoad.class);

    }
}
