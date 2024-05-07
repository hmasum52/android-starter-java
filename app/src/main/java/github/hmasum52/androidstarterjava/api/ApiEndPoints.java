package github.hmasum52.androidstarterjava.api;

import com.google.gson.JsonElement;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiEndPoints {

    @POST
    Call<JsonElement> POST(@Url String relativePath, @HeaderMap Map<String,String> headerMap, @Body Object object);
    @POST
    Call<JsonElement> POST(@Url String relativePath, @Body Object object);

    @GET
    Call<JsonElement> GET(@Url String relativePath, @HeaderMap Map<String,String> headerMap);

    @GET
    Call<JsonElement> GET(@Url String relativePath);

    @DELETE
    Call<JsonElement> DELETE(@Url String relativePath, @HeaderMap Map<String,String> headerMap);

    @DELETE
    Call<JsonElement> DELETE(@Url String relativePath);
}
