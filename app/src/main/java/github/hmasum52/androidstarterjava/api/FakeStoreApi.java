package github.hmasum52.androidstarterjava.api;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    public static final List<String> categories = List.of("electronics", "jewelery", "men's clothing", "women's clothing");

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

    public void getProduct(int id){
        Call<JsonElement> call = apiEndPoints.GET("products/"+id);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Product product = gson.fromJson(response.body(), Product.class);
                assert product != null;
                Log.d("FakeStoreApi", "onResponse: "+product.getTitle());
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable throwable) {
                Log.e("FakeStoreApi", "onFailure: "+throwable.getMessage());
            }
        });
    }

    public LiveData<List<Product>> getProducts(String category){
        MutableLiveData<List<Product>> products = new MutableLiveData<>();

        Call<JsonElement> call = apiEndPoints.GET("products/category/"+category);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                List<Product> productList = gson.fromJson(response.body(), new TypeToken<List<Product>>(){}.getType());
                products.setValue(productList);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable throwable) {
                Log.e("FakeStoreApi", "onFailure: "+throwable.getMessage());
            }
        });

        return products;
    }
}
