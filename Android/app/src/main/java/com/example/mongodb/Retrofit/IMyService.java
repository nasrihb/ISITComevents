package com.example.mongodb.Retrofit;

import com.example.mongodb.Models.Event;
import com.example.mongodb.Models.IP;
import com.example.mongodb.Models.Session;
import com.example.mongodb.Models.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IMyService {

    @GET("/")
    Call<IP> getIP();

    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("email") String email,
                                    @Field("name") String name,
                                    @Field("password") String password);
    @POST("login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email,
                                 @Field("password") String password);

    @POST("addevent")
    @FormUrlEncoded
    Observable<String> addEvent(@Field("email") String email,
                                @Field("titre") String titre,
                                @Field("date") String date,
                                @Field("heuredeb") String heuredeb,
                                @Field("heurefin") String heurefin,
                                @Field("lieu") String lieu,
                                @Field("responsable") String responsable,
                                @Field("description") String description);

    @GET("getuser/{email}")
    Call<List<User>> getuser(@Path("email") String email);

    @PUT("updateemail/{email}")
    @FormUrlEncoded
    Observable<String> updateEmail(@Path("email") String email,
                                    @Field("email2") String email2);



    @PUT("updateuser/{email}")
    @FormUrlEncoded
    Observable<String> updateUser(@Path("email") String email,
                                 @Field("name") String name,
                                 @Field("info") String info,
                                 @Field("adress") String adress,
                                 @Field("tel") String tel,
                                 @Field("linkdin") String linkdin);


    @GET("getevents")
    Call<List<Event>> getEvents();


    @GET("getdetailsevent/{id}")
    Call<List<Event>> getdetailsevent(@Path("id") String id);


    @GET("geteventemail/{email}")
    Call<List<Event>> geteventemail(@Path("email") String email);


    @GET("geteventid/{id}")
    Call<List<Event>> geteventid(@Path("id") String id);



    @GET("getsession/{email}")
    Call<List<Session>> getsession(@Path("email") String email);


    @PUT("updateevent/{id}")
    @FormUrlEncoded
    Observable<String> editEvent(@Path("id") String id,
                                 @Field("email") String email,
                                @Field("titre") String titre,
                                @Field("date") String date,
                                @Field("heuredeb") String heuredeb,
                                @Field("heurefin") String heurefin,
                                @Field("lieu") String lieu,
                                @Field("responsable") String responsable,
                                @Field("description") String description);


    @PUT("changepassword/{email}")
    @FormUrlEncoded
    Observable<String> changePassword(@Path("email") String email,
                                      @Field("currentpassword") String currentpassword,
                                      @Field("newpassword") String newpassword);


    @PUT("changephone/{email}")
    @FormUrlEncoded
    Observable<String> changePhone(@Path("email") String email,
                                      @Field("password") String password,
                                      @Field("phone") String phone);


    @DELETE("deleteevent/{id}")
    Call<Void>  deleteEvent(@Path("id") String id);


    @DELETE("deleteuser/{email}")
    Call<Void>  closeAccount(@Path("email") String email);

    @POST("addsession/{email}")
    @FormUrlEncoded
    Observable<String> addSession(@Path("email") String email,
                                 @Field("ip") String ip,
                                 @Field("localisation") String localisation,
                                 @Field("iemi") String iemi);


}
