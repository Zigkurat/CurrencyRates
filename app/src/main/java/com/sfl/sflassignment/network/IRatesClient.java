package com.sfl.sflassignment.network;

import com.sfl.sflassignment.network.model.BankInfo;
import com.sfl.sflassignment.network.model.ExchangeRates;

import java.util.Map;

public interface IRatesClient {

    void getRates(ResultCallback<Map<String, ExchangeRates>> callback);

    void getBankInfo(String identifier, ResultCallback<BankInfo> callback);
}
