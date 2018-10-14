package com.sfl.sflassignment.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sfl.sflassignment.network.endpoint.PublicEndpoint;
import com.sfl.sflassignment.network.model.BankInfo;
import com.sfl.sflassignment.network.model.BranchInfo;
import com.sfl.sflassignment.network.model.ExchangeRates;

import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager implements IRatesClient {

    private static final String BASE_URL = "http://rate.am/ws/mobile/v2/";

    private PublicEndpoint endpoint;

    public NetworkManager() {
        endpoint = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build()
                .create(PublicEndpoint.class);
    }

    public void getRates(final ResultCallback<Map<String, ExchangeRates>> callback) {
        endpoint.getBankExchangeRates().enqueue(new Callback<Map<String, ExchangeRates>>() {
            @Override
            public void onResponse(Call<Map<String, ExchangeRates>> call, Response<Map<String, ExchangeRates>> response) {
                callback.success(response.body());
            }

            @Override
            public void onFailure(Call<Map<String, ExchangeRates>> call, Throwable t) {
                callback.failure(t);
            }
        });
    }

    public void getBankInfo(String identifier, final ResultCallback<BankInfo> callback) {
        endpoint.getBankInfo(identifier).enqueue(new Callback<BankInfo>() {
            @Override
            public void onResponse(Call<BankInfo> call, Response<BankInfo> response) {
                callback.success(response.body());
            }

            @Override
            public void onFailure(Call<BankInfo> call, Throwable t) {
                callback.failure(t);
            }
        });
    }
}
