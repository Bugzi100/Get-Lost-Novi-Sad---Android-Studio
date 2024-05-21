package com.example.maps;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("images")
    Call<ImageResponse> getGeotaggedImages(@Query("lat") double lat, @Query("lng") double lng);
}

