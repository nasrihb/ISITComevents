package com.example.mongodb;

import com.example.mongodb.Retrofit.IMyService;
import com.example.mongodb.Retrofit.RetrofitClientIp;

public class Common {
    private static String BASE_URL="http://ip.jsontest.com/";

    public static IMyService getIpService(){
        return RetrofitClientIp.getClient(BASE_URL).create(IMyService.class);
    }
}
