package com.discover.tunisia.services;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceApiClient {
    String API_ENDPOINT = "https://streamerzapps.com/ontt_web_services/api/";

    @GET("event/read.php")
    Call<ResponseBody> getEvent();

    @GET("monsejour/read.php")
    Call<ResponseBody> getSejour();

    @GET("monsejourelements/read_single.php")
    Call<ResponseBody> getOneSejour(@Query("id") int id);

    @GET("incontournable/read.php")
    Call<ResponseBody> getIncontournable();

    @GET("incontournabledetail/read_single.php")
    Call<ResponseBody> getOneIncontournable(@Query("id") int id);

    @GET("city/read.php")
    Call<ResponseBody> getNavigations();

    @GET("gallery/read_gallery.php")
    Call<ResponseBody> getPhotos();

    @POST("user/create.php")
    Call<ResponseBody> createAccount(@Body JsonObject object);

    @POST("user/connexion.php")
    Call<ResponseBody> login(@Body JsonObject object);

    @POST("user/connexion_facebook.php")
    Call<ResponseBody> loginFacebook(@Body JsonObject object);

    @GET("actualites/read.php")
    Call<ResponseBody> getNews();

    @GET("search/search.php")
    Call<ResponseBody> search(@Query("keyword") String keyword);
}
