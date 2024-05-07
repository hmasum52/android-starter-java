package github.hmasum52.androidstarterjava.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;

import github.hmasum52.androidstarterjava.hilt.AppModule;
import github.hmasum52.androidstarterjava.model.fakestore.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FakeStoreApi {
    private final ApiEndPoints apiEndPoints;
    private final Gson gson = new Gson();

    @Inject
    public FakeStoreApi(FakeStoreEndpoints endpoints) {
        this.apiEndPoints = endpoints.getEndPoints();
    }

    public void getProducts(){
        Call<JsonElement> call = apiEndPoints.GET("products");
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                List<Product> products = gson.fromJson(response.body(), new TypeToken<List<Product>>(){}.getType());
                assert products != null;
                products.forEach(product -> Log.d("FakeStoreApi", "onResponse: "+product.getTitle()));
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable throwable) {
                Log.e("FakeStoreApi", "onFailure: "+throwable.getMessage());
            }
        });
    }

}
