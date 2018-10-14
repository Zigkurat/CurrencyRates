package com.sfl.sflassignment.ui.rates.view;

import android.os.Parcelable;

import com.sfl.sflassignment.ui.rates.view.model.RateItem;

import java.util.List;

public interface RatesViewInput {

    void updateListItems(List<RateItem> rateItems);

    void updateListItem(RateItem rateItem);

    void navigate(Class clazz, Parcelable parcelable);
}
