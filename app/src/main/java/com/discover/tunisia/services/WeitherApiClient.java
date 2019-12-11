package com.discover.tunisia.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeitherApiClient {
    String API_ENDPOINT = "http://api.weatherstack.com/";

    @GET("current")
    Call<ResponseBody> getWeither(@Query("access_key") String key,@Query("query") String country);

}
