package github.hmasum52.androidstarterjava.api;

import lombok.Getter;
import retrofit2.Retrofit;

@Getter
public class FakeStoreEndpoints{
    private final ApiEndPoints endPoints;
    public FakeStoreEndpoints(Retrofit retrofit) {
        this.endPoints = retrofit.create(ApiEndPoints.class);
    }
}