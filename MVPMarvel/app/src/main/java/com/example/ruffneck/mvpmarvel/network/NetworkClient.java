package com.example.ruffneck.mvpmarvel.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.Version;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.example.ruffneck.mvpmarvel.models.CharacterDataWrapper;
import rx.Observable;

public class NetworkClient {

    public static Retrofit retrofit;
    public static final String HOST = "https://gateway.marvel.com:443/v1/public/";
    public static final String CHARACTERS = "characters?";

    public void NetworkClient(){
    }

    public static Retrofit getRetrofit(){

        if(retrofit==null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(chain -> {
                        Request originalRequest = chain.request();
                        Request requestWithUserAgent = originalRequest.newBuilder()
                                .removeHeader("User-Agent")
                                .header("User-Agent", Version.userAgent() + "/" + "android")
                                .build();
                        return chain.proceed(requestWithUserAgent);
                    })
                    .addInterceptor(interceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(HOST)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }

        // return retrofit.create(MarvelApiEndPoint.class);
        return retrofit;
    }

    public interface MarvelApiEndPoint{
        @GET(CHARACTERS)
        Observable<CharacterDataWrapper> getCharacters(@Query("ts") String ts, @Query("limit") int limit, @Query("offset") int offset, @Query("apikey") String apikey, @Query("hash") String hash );
    }

    public interface MarvelApiEndPoint2{
        @GET(CHARACTERS)
        Observable<CharacterDataWrapper> getCharacter(@Query("ts") String ts, @Query("id") int id, @Query("apikey") String apikey, @Query("hash") String hash );
    }
}
