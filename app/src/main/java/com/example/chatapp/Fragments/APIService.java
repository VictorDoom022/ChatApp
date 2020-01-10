package com.example.chatapp.Fragments;

import com.example.chatapp.Notifications.MyResponse;
import com.example.chatapp.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAYxU4lE8:APA91bGUzsKhV8y_UV0znA6n6Sk8KDNeqeldRQstUuUGWAdcKpIRgj_IXtI70qLWN8JiX_zVJqmaShVb1NJsKJvOd6tI-cxsEMJYTeEKF9W--GWIXRSfDPWi22z-w2F1SJQVfB1stxHL"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
