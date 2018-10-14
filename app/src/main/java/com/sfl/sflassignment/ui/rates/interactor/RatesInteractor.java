package com.sfl.sflassignment.ui.rates.interactor;

import android.location.Location;
import android.util.Log;

import com.sfl.sflassignment.network.NetworkManager;
import com.sfl.sflassignment.network.ResultCallback;
import com.sfl.sflassignment.network.model.BankInfo;
import com.sfl.sflassignment.network.model.BranchInfo;
import com.sfl.sflassignment.network.model.ExchangeRate;
import com.sfl.sflassignment.network.model.ExchangeRates;
import com.sfl.sflassignment.network.model.WorkHours;
import com.sfl.sflassignment.ui.constant.CurrencyType;
import com.sfl.sflassignment.ui.constant.TransactionType;
import com.sfl.sflassignment.ui.data.BankData;
import com.sfl.sflassignment.ui.data.BranchData;
import com.sfl.sflassignment.ui.data.RateData;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatesInteractor implements RatesInteractorInput {

    private NetworkManager networkManager = new NetworkManager();
    private RatesInteractorOutput output;

    @Override
    public void getRates() {
        networkManager.getRates(new ResultCallback<Map<String, ExchangeRates>>() {
            @Override
            public void success(Map<String, ExchangeRates> result) {
                Map<String, BankData> bankDataMap = createBankDataMap(result);
                output.presentOutput(bankDataMap);
            }

            @Override
            public void failure(Throwable error) {
                output.handleError(error);
            }
        });
    }

    private Map<String, BankData> createBankDataMap(Map<String, ExchangeRates> exchangeRatesMap) {
        Map<String, BankData> result = new HashMap<>();

        for (Map.Entry<String, ExchangeRates> entry : exchangeRatesMap.entrySet()) {
            ExchangeRates rates = entry.getValue();
            BankData data = new BankData();

            data.setIdentifier(entry.getKey());
            data.setTitle(rates.getTitle());
            data.setLogo(rates.getLogo());
            data.setDate(new Date(rates.getDate()));
            data.setCurrencyRates(createCurrencyRates(rates.getList()));

            loadBankBranches(data);

            result.put(entry.getKey(), data);
        }

        return result;
    }

    private Map<CurrencyType, Map<TransactionType, RateData>> createCurrencyRates(Map<String, Map<String, ExchangeRate>> currencyRatesMap) {
        Map<CurrencyType, Map<TransactionType, RateData>> result = new HashMap<>();

        for (Map.Entry<String, Map<String, ExchangeRate>> currencyEntry : currencyRatesMap.entrySet()) {
            Map<TransactionType, RateData> transactionTypeMap = new HashMap<>();

            for (Map.Entry<String, ExchangeRate> transactionEntry : currencyEntry.getValue().entrySet()) {
                ExchangeRate rate = transactionEntry.getValue();
                RateData data = new RateData();
                data.setBuy(rate.getBuy());
                data.setSell(rate.getSell());

                TransactionType transactionType = TransactionType.valueFrom(transactionEntry.getKey());
                if (transactionType != null) {
                    transactionTypeMap.put(transactionType, data);
                }
            }
            try {
                CurrencyType.valueOf(currencyEntry.getKey());
            } catch (Exception e) {
                Log.e("HELLO", currencyEntry.getKey());
            }
            result.put(CurrencyType.valueOf(currencyEntry.getKey()), transactionTypeMap);
        }

        return result;
    }

    private void loadBankBranches(final BankData bankData) {
        networkManager.getBankInfo(bankData.getIdentifier(), new ResultCallback<BankInfo>() {
            @Override
            public void success(BankInfo result) {
                bankData.setBranches(extractBranchData(result));
                output.updateBankData(bankData);
            }

            @Override
            public void failure(Throwable error) {
                output.handleError(error);
            }
        });
    }

    private Map<String, BranchData> extractBranchData(BankInfo bankInfo) {
        Map<String, BranchData> result = new HashMap<>();

        for (Map.Entry<String, BranchInfo> entry : bankInfo.getList().entrySet()) {
            BranchInfo branchInfo = entry.getValue();
            BranchData branchData = new BranchData();

            branchData.setTitle(branchInfo.getTitle().get("en"));
            branchData.setAddress(branchInfo.getAddress().get("en"));
            branchData.setLocation(extractLocation(branchInfo.getLocation()));
            branchData.setPhoneNumber(branchInfo.getContacts());
            branchData.setWorkHours(extractWorkHours(branchInfo));
            result.put(entry.getKey(), branchData);
        }

        return result;
    }

    private Location extractLocation(Map<String, Double> locationMap) {
        Location location = null;
        Double lat = locationMap.get("lat");
        Double lng = locationMap.get("lng");
        if (lat != null && lng != null) {
            location = new Location("rate.am");
            location.setLatitude(lat);
            location.setLongitude(lng);
        }

        return location;
    }

    private Map<String, String> extractWorkHours(BranchInfo branchInfo) {
        Map<String, String> result = new HashMap<>();
        List<WorkHours> workHours = branchInfo.getWorkhours();
        for (WorkHours item : workHours) {
            result.put(item.getDays(), item.getHours());
        }

        return result;
    }

    public RatesInteractorOutput getOutput() {
        return output;
    }

    public void setOutput(RatesInteractorOutput output) {
        this.output = output;
    }
}
