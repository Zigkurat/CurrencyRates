package com.sfl.sflassignment.ui.rates.view;

import android.location.Location;
import android.os.Bundle;

import com.sfl.sflassignment.ui.constant.CurrencyType;
import com.sfl.sflassignment.ui.constant.SortDescriptor;
import com.sfl.sflassignment.ui.constant.TransactionType;
import com.sfl.sflassignment.ui.rates.view.model.RateItem;

public interface RatesViewOutput {
    void loadData();

    void refreshData();

    void locationUpdated(Location location);

    void currencyTypeSelected(CurrencyType currencyType);

    void transactionTypeSelected(TransactionType transactionType);

    void sortDescriptorChanged(SortDescriptor descriptor);

    void toggleSortOrder();

    void listItemSelected(RateItem item);

    void saveState(Bundle bundle);

    void loadState(Bundle bundle);
}
