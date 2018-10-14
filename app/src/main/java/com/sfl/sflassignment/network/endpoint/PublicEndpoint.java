package com.sfl.sflassignment.network.endpoint;

import com.sfl.sflassignment.network.model.BankInfo;
import com.sfl.sflassignment.network.model.ExchangeRates;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PublicEndpoint {
    @GET("rates.ashx?lang=en")
    Call<Map<String, ExchangeRates>> getBankExchangeRates();

    @GET("branches.ashx")
    Call<BankInfo> getBankInfo(@Query("id") String identifier);
}
