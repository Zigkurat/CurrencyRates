package com.sfl.sflassignment.ui.rates.interactor;

import com.sfl.sflassignment.ui.data.BankData;

import java.util.Map;

public interface RatesInteractorOutput {

    void presentOutput(Map<String, BankData> bankDataMap);

    void updateBankData(BankData bankData);

    void handleError(Throwable error);
}
