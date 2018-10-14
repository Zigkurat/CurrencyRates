package com.sfl.sflassignment.ui.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.sfl.sflassignment.ui.constant.CurrencyType;
import com.sfl.sflassignment.ui.constant.TransactionType;

import java.util.Date;
import java.util.Map;

public class BankData implements Parcelable {
    private String identifier;
    private String title;
    private String logo;
    private Date date;
    private String nearestBranchIdentifier;
    private Map<CurrencyType, Map<TransactionType, RateData>> currencyRates;
    private Map<String, BranchData> branches;

    public BankData() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNearestBranchIdentifier() {
        return nearestBranchIdentifier;
    }

    public void setNearestBranchIdentifier(String nearestBranchIdentifier) {
        this.nearestBranchIdentifier = nearestBranchIdentifier;
    }

    public Map<CurrencyType, Map<TransactionType, RateData>> getCurrencyRates() {
        return currencyRates;
    }

    public void setCurrencyRates(Map<CurrencyType, Map<TransactionType, RateData>> currencyRates) {
        this.currencyRates = currencyRates;
    }

    public Map<String, BranchData> getBranches() {
        return branches;
    }

    public void setBranches(Map<String, BranchData> branches) {
        this.branches = branches;
    }

    //region parcelable
    protected BankData(Parcel in) {
        identifier = in.readString();
        title = in.readString();
        logo = in.readString();
        date = new Date(in.readLong());
        currencyRates = in.readHashMap(getClass().getClassLoader());
        branches = in.readHashMap(getClass().getClassLoader());
    }

    public static final Creator<BankData> CREATOR = new Creator<BankData>() {
        @Override
        public BankData createFromParcel(Parcel in) {
            return new BankData(in);
        }

        @Override
        public BankData[] newArray(int size) {
            return new BankData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(identifier);
        dest.writeString(title);
        dest.writeString(logo);
        dest.writeLong(date.getTime());
        dest.writeMap(currencyRates);
        dest.writeMap(branches);
    }

//    private Map<String, Map<String, RateData>> writeCurrenciesToParcel() {
//        Map<String, Map<String, RateData>> resultMap = new HashMap<>();
//        for (Map.Entry<CurrencyType, Map<TransactionType, RateData>> currencyEntry : currencyRates.entrySet()) {
//            Map<String, RateData> resultTransactionMap = new HashMap<>();
//            for (Map.Entry<TransactionType, RateData> transactionEntry : currencyEntry.getValue().entrySet()) {
//                resultTransactionMap.put(transactionEntry.getKey().getValue(), transactionEntry.getValue());
//            }
//            resultMap.put(currencyEntry.getKey().toString(), resultTransactionMap);
//        }
//
//        return resultMap;
//    }
    //endregion
}
