package com.discover.tunisia.services;

import android.support.annotation.NonNull;
import com.discover.tunisia.config.Utils;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceFacotry {

    private static OnConnectionTimeoutListener onConnectionTimeoutListener;
    private static ServiceApiClient serviceApiClient;
    private static WeitherApiClient weitherApiClient;
    public static ServiceApiClient getServiceApiClient() {

        if (serviceApiClient == null) {
            OkHttpClient client = new OkHttpClient();
            client.newBuilder().connectTimeout(Utils.TIMEOUT, TimeUnit.SECONDS);
            client.newBuilder().readTimeout(Utils.TIMEOUT, TimeUnit.SECONDS);
            client.newBuilder().addInterceptor(RetrofitServiceFacotry::onOnIntercept).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ServiceApiClient.API_ENDPOINT)
//                    .client(new OkHttpClient().newBuilder().connectTimeout(Utils.TIMEOUT, TimeUnit.SECONDS).readTimeout(Utils.TIMEOUT, TimeUnit.SECONDS).build())
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            serviceApiClient = retrofit.create(ServiceApiClient.class);
        }
        return serviceApiClient;
    }

    public static WeitherApiClient getWeitherApiClient() {

        if (weitherApiClient == null) {
            OkHttpClient client = new OkHttpClient();
            client.newBuilder().connectTimeout(Utils.TIMEOUT, TimeUnit.SECONDS);
            client.newBuilder().readTimeout(Utils.TIMEOUT, TimeUnit.SECONDS);
            client.newBuilder().addInterceptor(RetrofitServiceFacotry::onOnIntercept).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(WeitherApiClient.API_ENDPOINT)
//                    .client(new OkHttpClient().newBuilder().connectTimeout(Utils.TIMEOUT, TimeUnit.SECONDS).readTimeout(Utils.TIMEOUT, TimeUnit.SECONDS).build())
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            weitherApiClient = retrofit.create(WeitherApiClient.class);
        }
        return weitherApiClient;
    }

    @NonNull
    private static Response onOnIntercept(Interceptor.Chain chain) throws IOException {
        try {
            Response response = chain.proceed(chain.request());
            String content = response.body().toString();
//            Log.d(TAG, lastCalledMethodName + " - " + content);
            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), content)).build();
        } catch (SocketTimeoutException exception) {
            exception.printStackTrace();
            if (onConnectionTimeoutListener != null)
                onConnectionTimeoutListener.onConnectionTimeout();
        }

        return chain.proceed(chain.request());
    }

    public interface OnConnectionTimeoutListener {
        void onConnectionTimeout();
    }


}
