package com.yami.shop.common.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;

public class AssumeRole {

    public static void main(String[] args) {
        //构建一个阿里云客户端，用于发起请求。
        //构建阿里云客户端时需要设置AccessKey ID和AccessKey Secret。
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G去掉F中文YZVDU去掉keZaaq中文xSpwj1", "acjl4tbo5VUlKuogn5SVzyEYLk2C8A");
        IAcsClient client = new DefaultAcsClient(profile);

        //构造请求，设置参数。关于参数含义和设置方法，请参见API参考。
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setRegionId("cn-hangzhou");
        request.setRoleArn("1655293085965410:role/aliyunosstokengeneratorrole");
        request.setRoleSessionName("external-username");

        //发起请求，并得到响应。
        try {
            AssumeRoleResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }

    }
}
