package com.sfl.sflassignment.ui.branches.presenter;

import android.os.Bundle;

import com.sfl.sflassignment.ui.data.BankData;
import com.sfl.sflassignment.ui.data.BranchData;
import com.sfl.sflassignment.ui.branches.interactor.BranchesInteractor;
import com.sfl.sflassignment.ui.branches.interactor.BranchesInteractorInput;
import com.sfl.sflassignment.ui.branches.interactor.BranchesInteractorOutput;
import com.sfl.sflassignment.ui.branches.view.BranchesViewOutput;
import com.sfl.sflassignment.ui.branches.view.BranchesViewInput;
import com.sfl.sflassignment.ui.branches.view.model.BranchItem;
import com.sfl.sflassignment.ui.branches.view.model.CurrencyItem;
import com.sfl.sflassignment.ui.branches.view.model.Header;
import com.sfl.sflassignment.ui.constant.CurrencyType;
import com.sfl.sflassignment.ui.constant.TransactionType;
import com.sfl.sflassignment.ui.data.RateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BranchesPresenter implements BranchesViewOutput, BranchesInteractorOutput {

    private static final String TRANSACTION_TYPE_KEY = "transaction_type";
    private static final String BANK_DATA_KEY = "bank_data";
    private static final String CURRENT_BRANCH_KEY = "current_branch";

    private BranchesViewInput view;
    private BranchesInteractorInput interactor = new BranchesInteractor();

    private TransactionType transactionType = TransactionType.CASH;
    private BankData bankData;
    private String currentBranchIdentifier;

    //region interactor
    @Override
    public void presentOutput(BankData bankData) {
        this.bankData = bankData;
        present();
    }

    @Override
    public void handleError(Throwable error) {

    }
    //endregion

    //region view
    @Override
    public void loadData() {
        if (bankData.getBranches() != null) {
            present();
        } else {
            interactor.getBankInfo(bankData.getIdentifier());
        }
    }

    @Override
    public void branchSelected(BranchItem branch) {
        currentBranchIdentifier = branch.getIdentifier();
        present();
    }

    @Override
    public void transactionTypeSelected(TransactionType transaction) {
        transactionType = transaction;
        present();
    }

    @Override
    public void saveState(Bundle bundle) {
        bundle.putString(TRANSACTION_TYPE_KEY, transactionType.getValue());
        bundle.putParcelable(BANK_DATA_KEY, bankData);
        bundle.putString(CURRENT_BRANCH_KEY, currentBranchIdentifier);
    }

    @Override
    public void loadState(Bundle bundle) {
        String transactionTypeString = bundle.getString(TRANSACTION_TYPE_KEY);
        if (transactionTypeString != null) {
            transactionType = TransactionType.valueFrom(transactionTypeString);
        }
        bankData = bundle.getParcelable(BANK_DATA_KEY);
        currentBranchIdentifier = bundle.getString(CURRENT_BRANCH_KEY);
    }
    //endregion

    private void present() {
        if (currentBranchIdentifier == null) {
            currentBranchIdentifier = getBranchIdentifier();
        }
        view.updateHeader(extractHeader());
        view.updateCurrencyItems(extractCurrencyItems());
        view.updateBranchItems(extractBranchItems());
    }

    private Header extractHeader() {
        Header header = new Header();
        BranchData branchData = bankData.getBranches().get(currentBranchIdentifier);
        header.setTitle(bankData.getTitle());
        header.setCity(branchData.getTitle());
        header.setAddress(branchData.getAddress());
        header.setPhoneNumber(branchData.getPhoneNumber());
        header.setWorkingDays(branchData.getWorkHours());

        return header;
    }

    private List<CurrencyItem> extractCurrencyItems() {
        List<CurrencyItem> currencyItems = new ArrayList<>();
        for (Map.Entry<CurrencyType, Map<TransactionType, RateData>> entry : bankData.getCurrencyRates().entrySet()) {
            CurrencyItem item = new CurrencyItem();
            item.setTitle(entry.getKey().toString());
            RateData rateData = entry.getValue().get(transactionType);
            if (rateData != null) {
                item.setBuy(String.valueOf(rateData.getBuy()));
                item.setSell(String.valueOf(rateData.getSell()));

                currencyItems.add(item);
            }
        }

        return currencyItems;
    }

    private List<BranchItem> extractBranchItems() {
        List<BranchItem> branchItems = new ArrayList<>();
        for (Map.Entry<String, BranchData> branch : bankData.getBranches().entrySet()) {
            BranchItem branchItem = new BranchItem();
            branchItem.setIdentifier(branch.getKey());
            branchItem.setTitle(branch.getValue().getTitle());

            branchItems.add(branchItem);
        }

        return branchItems;
    }

    private String getBranchIdentifier() {
        String branchIdentifier = bankData.getNearestBranchIdentifier();
        if (branchIdentifier != null) {
            return branchIdentifier;
        } else {
            return new ArrayList<>(bankData.getBranches().keySet()).get(0);
        }
    }

    public BranchesViewInput getView() {
        return view;
    }

    public void setView(BranchesViewInput view) {
        this.view = view;
    }

    public void setInteractor(BranchesInteractorInput interactor) {
        this.interactor = interactor;
    }

    public void setBankData(BankData bankData) {
        this.bankData = bankData;
    }
}
