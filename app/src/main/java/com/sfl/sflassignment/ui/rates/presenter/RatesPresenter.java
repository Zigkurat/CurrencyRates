package com.sfl.sflassignment.ui.rates.presenter;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;

import com.sfl.sflassignment.ui.branches.view.BranchesActivity;
import com.sfl.sflassignment.ui.constant.CurrencyType;
import com.sfl.sflassignment.ui.constant.SortDescriptor;
import com.sfl.sflassignment.ui.constant.SortOrder;
import com.sfl.sflassignment.ui.constant.TransactionType;
import com.sfl.sflassignment.ui.data.BankData;
import com.sfl.sflassignment.ui.data.BranchData;
import com.sfl.sflassignment.ui.data.RateData;
import com.sfl.sflassignment.ui.rates.interactor.RatesInteractorInput;
import com.sfl.sflassignment.ui.rates.interactor.RatesInteractorOutput;
import com.sfl.sflassignment.ui.rates.view.RatesViewInput;
import com.sfl.sflassignment.ui.rates.view.RatesViewOutput;
import com.sfl.sflassignment.ui.rates.view.model.RateItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatesPresenter implements RatesInteractorOutput, RatesViewOutput {

    private static final String LAST_LOCATION_KEY = "last_location";
    private static final String TRANSACTION_TYPE_KEY = "transaction_type";
    private static final String CURRENCY_TYPE_KEY = "currency_type";
    private static final String SORT_DESCRIPTOR_KEY = "sort_descriptor";
    private static final String SORT_ORDER_KEY = "sort_order";
    private static final String BANK_DATA_MAP_KEY = "bank_data_map";

    private RatesViewInput view;
    private RatesInteractorInput interactor;

    private Location lastKnownLocation;

    private TransactionType transactionType = TransactionType.CASH;
    private CurrencyType currencyType = CurrencyType.USD;
    private SortDescriptor sortDescriptor;
    private SortOrder sortOrder;
    private Map<String, BankData> bankDataMap;

    //region interactor
    @Override
    public void presentOutput(Map<String, BankData> rateItems) {
        this.bankDataMap = rateItems;
        present();
    }

    @Override
    public void updateBankData(BankData bankData) {
        bankDataMap.put(bankData.getIdentifier(), bankData);
        RateItem rateItem = rateItemFromBankData(bankData);
        if (rateItem != null) {
            view.updateListItem(rateItem);
        }
    }

    @Override
    public void handleError(Throwable error) {

    }
    //endregion

    //region view
    @Override
    public void loadData() {
        if (bankDataMap != null) {
            present();
        } else {
            interactor.getRates();
        }
    }

    @Override
    public void refreshData() {
        interactor.getRates();
    }

    @Override
    public void locationUpdated(Location location) {
        lastKnownLocation = location;
        present();
    }

    @Override
    public void currencyTypeSelected(CurrencyType currency) {
        currencyType = currency;
        present();
    }

    @Override
    public void transactionTypeSelected(TransactionType transaction) {
        transactionType = transaction;
        present();
    }

    @Override
    public void listItemSelected(RateItem item) {
        BankData data = bankDataMap.get(item.getIdentifier());

        view.navigate(BranchesActivity.class, data);
    }

    @Override
    public void sortDescriptorChanged(SortDescriptor descriptor) {
        sortDescriptor = descriptor;
        sortOrder = SortOrder.ASCENDING;
        present();
    }

    @Override
    public void toggleSortOrder() {
        if (sortOrder == SortOrder.ASCENDING) {
            sortOrder = SortOrder.DESCENDING;
        } else {
            sortOrder = SortOrder.ASCENDING;
        }
        present();
    }

    @Override
    public void saveState(Bundle bundle) {
        bundle.putParcelable(LAST_LOCATION_KEY, lastKnownLocation);
        bundle.putString(TRANSACTION_TYPE_KEY, transactionType.getValue());
        bundle.putString(CURRENCY_TYPE_KEY, currencyType.toString());
        if (sortDescriptor != null) {
            bundle.putString(SORT_DESCRIPTOR_KEY, sortDescriptor.toString());
        }
        if (sortOrder != null) {
            bundle.putString(SORT_ORDER_KEY, sortOrder.toString());
        }
        if (bankDataMap != null) {
            bundle.putParcelableArrayList(BANK_DATA_MAP_KEY, new ArrayList<Parcelable>(bankDataMap.values()));
        }
    }

    @Override
    public void loadState(Bundle bundle) {
        lastKnownLocation = bundle.getParcelable(LAST_LOCATION_KEY);
        String transactionTypeString = bundle.getString(TRANSACTION_TYPE_KEY);
        if (transactionTypeString != null) {
            transactionType = TransactionType.valueFrom(transactionTypeString);
        }
        String currencyTypeString = bundle.getString(CURRENCY_TYPE_KEY);
        if (currencyTypeString != null) {
            currencyType = CurrencyType.valueOf(currencyTypeString);
        }
        String sortDescriptorString = bundle.getString(SORT_DESCRIPTOR_KEY);
        if (sortDescriptorString != null) {
            sortDescriptor = SortDescriptor.valueOf(sortDescriptorString);
        }
        String sortOrderString = bundle.getString(SORT_ORDER_KEY);
        if (sortDescriptorString != null) {
            sortOrder = SortOrder.valueOf(sortOrderString);
        }

        List<BankData> bankDataList = bundle.getParcelableArrayList(BANK_DATA_MAP_KEY);
        if (bankDataList != null) {
            bankDataMap = new HashMap<>();
            for (BankData bankData : bankDataList) {
                bankDataMap.put(bankData.getIdentifier(), bankData);
            }
        }
    }
    //endregion

    private void present() {
        if (bankDataMap != null) {
            List<RateItem> rateItems = extractRateItems();
            sortItems(rateItems);
            view.updateListItems(rateItems);
        }
    }

    private List<RateItem> extractRateItems() {
        List<RateItem> items = new ArrayList<>();

        for (BankData data : bankDataMap.values()) {
            RateItem item = rateItemFromBankData(data);
            if (item != null) {
                items.add(item);
            }
        }

        return items;
    }

    private RateItem rateItemFromBankData(BankData data) {
        RateItem rateItem = null;
        RateData rateData;
        Map<TransactionType, RateData> currencyRate = data.getCurrencyRates().get(currencyType);

        if (currencyRate != null) {
            rateData = currencyRate.get(transactionType);

            if (rateData != null) {
                rateItem = new RateItem();
                rateItem.setIdentifier(data.getIdentifier());
                rateItem.setTitle(data.getTitle());
                rateItem.setBuyPrice(rateData.getBuy());
                rateItem.setSellPrice(rateData.getSell());
                if (lastKnownLocation != null) {
                    rateItem.setDistance(calculateNearestBranchDistance(data));
                }
            }
        }

        return rateItem;
    }

    private Float calculateNearestBranchDistance(BankData data) {
        float nearestDistance = Float.MAX_VALUE;
        if (data.getBranches() != null) {

            for (Map.Entry<String, BranchData> entry : data.getBranches().entrySet()) {
                BranchData branch = entry.getValue();
                if (branch.getLocation() != null) {
                    float branchDistance = lastKnownLocation.distanceTo(branch.getLocation());
                    if (branchDistance < nearestDistance) {
                        nearestDistance = branchDistance;
                        data.setNearestBranchIdentifier(entry.getKey());
                    }
                }
            }
        }

        return nearestDistance == Float.MAX_VALUE ? null : nearestDistance;
    }

    private void sortItems(List<RateItem> items) {
        if (sortDescriptor == null) {
            return;
        }
        Collections.sort(items, new Comparator<RateItem>() {
            @Override
            public int compare(RateItem o1, RateItem o2) {
                float o1Value;
                float o2Value;
                switch (sortDescriptor) {
                    case BUYING:
                        o1Value = o1.getBuyPrice();
                        o2Value = o2.getBuyPrice();
                        break;
                    case SELLING:
                        o1Value = o1.getSellPrice();
                        o2Value = o2.getSellPrice();
                        break;
                    case DISTANCE:
                    default:
                        if (o1.getDistance() == null) {
                            o1Value = Float.MAX_VALUE;
                        } else {
                            o1Value = o1.getDistance();
                        }
                        if (o2.getDistance() == null) {
                            o2Value = Float.MAX_VALUE;
                        } else {
                            o2Value = o2.getDistance();
                        }
                }

                if (sortOrder == SortOrder.ASCENDING) {
                    return Float.compare(o1Value, o2Value);
                } else {
                    return -Float.compare(o1Value, o2Value);
                }
            }
        });
    }

    public void setView(RatesViewInput view) {
        this.view = view;
    }

    public void setInteractor(RatesInteractorInput interactor) {
        this.interactor = interactor;
    }
}
