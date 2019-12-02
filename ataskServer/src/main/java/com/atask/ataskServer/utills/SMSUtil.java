package com.atask.ataskServer.utills;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * Created by zhong on 2019-7-2.
 */
public class SMSUtil {
	// 阿里云密钥
    private final static String accessKeyId = "";
    private final static  String accessKeyPwd = "";

    public static String sendRegCAPTCHA(String phoneNumber)
    {
        // 生成验证码
        String randomCode = getRandomCode();

        // 发送短信
        DefaultProfile profile = DefaultProfile.getProfile("cn-hongkong", accessKeyId, accessKeyPwd);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "ATask");
        request.putQueryParameter("TemplateCode", "SMS_174992895");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+ randomCode+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            //System.out.println(response.getData());
            return randomCode;
        } catch (Exception e) {
          e.printStackTrace();
          return "";
        }
    }

    public static String sendRstPwdCAPTCHA(String phoneNumber)
    {
        // 生成验证码
        String randomCode = getRandomCode();

        // 发送短信
        DefaultProfile profile = DefaultProfile.getProfile("cn-hongkong", "LTAI4FtkppDCNC2CEm1bQvBk", "iA96HX30ZFC22n1L7pPGAiu9PAOq6B");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "ATask");
        request.putQueryParameter("TemplateCode","SMS_174992897");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+ randomCode+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            //System.out.println(response.getData());
            return randomCode;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getRandomCode()
    {
        // 生成验证码
        String number1 = String.valueOf((int)(Math.random()*(10)));
        String number2 = String.valueOf((int)(Math.random()*(10)));
        String number3 = String.valueOf((int)(Math.random()*(10)));
        String number4 = String.valueOf((int)(Math.random()*(10)));

        String randomCode = number1 + number2 + number3 + number4;

        return randomCode;
    }

}
