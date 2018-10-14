package com.sfl.sflassignment.ui.rates.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.sfl.sflassignment.R;
import com.sfl.sflassignment.ui.constant.CurrencyType;
import com.sfl.sflassignment.ui.constant.SortDescriptor;
import com.sfl.sflassignment.ui.constant.TransactionType;
import com.sfl.sflassignment.ui.rates.RatesBuilder;
import com.sfl.sflassignment.ui.rates.view.adapter.RatesRecyclerAdapter;
import com.sfl.sflassignment.ui.rates.view.model.RateItem;

import java.util.List;

public class RatesActivity extends AppCompatActivity implements RatesViewInput {

    private static final int LOCATION_PERMISSION_REQUEST = 1001;

    private RatesViewOutput viewOutput;

    private View lastCheckedButton;
    private SwipeRefreshLayout refreshLayout;
    private RatesRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI();

        RatesBuilder builder = new RatesBuilder();
        builder.build(this);

        if (savedInstanceState != null) {
            viewOutput.loadState(savedInstanceState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateLastKnownLocation();
        viewOutput.loadData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        viewOutput.saveState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            updateLastKnownLocation();
        }
    }

    private void setupUI() {
        setContentView(R.layout.activity_rates);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupCurrencySpinner();
        setupTransactionSpinner();
        setupSortButtons();
        setupRateList();
    }

    private void setupCurrencySpinner() {
        Spinner currencySpinner = findViewById(R.id.spinner_currency);
        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(RatesActivity.this, android.R.layout.simple_spinner_item, CurrencyType.stringValues());
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(currencyAdapter);
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewOutput.currencyTypeSelected(CurrencyType.valueOf((String) parent.getAdapter().getItem(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupTransactionSpinner() {
        Spinner transactionSpinner = findViewById(R.id.spinner_transaction);
        ArrayAdapter<String> transactionAdapter = new ArrayAdapter<>(RatesActivity.this, android.R.layout.simple_spinner_item, TransactionType.stringValues());
        transactionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transactionSpinner.setAdapter(transactionAdapter);
        transactionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewOutput.transactionTypeSelected(TransactionType.valueOf((String) parent.getAdapter().getItem(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void updateLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST);
            }
        } else {
            FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(this);
            locationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    viewOutput.locationUpdated(location);
                }
            });
        }
    }

    private void setupRateList() {
        adapter = new RatesRecyclerAdapter();
        adapter.setOnItemClickListener(new RatesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(RateItem item) {
                viewOutput.listItemSelected(item);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter.notifyDataSetChanged();

        refreshLayout = findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewOutput.refreshData();
            }
        });
    }

    private void setupSortButtons() {
        RadioButton distanceButton = findViewById(R.id.rb_by_distance);
        distanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSortButton(v, SortDescriptor.DISTANCE);
            }
        });
        RadioButton buyingButton = findViewById(R.id.rb_by_buying);
        buyingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSortButton(v, SortDescriptor.BUYING);
            }
        });
        RadioButton sellingButton = findViewById(R.id.rb_by_selling);
        sellingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSortButton(v, SortDescriptor.SELLING);
            }
        });
    }

    private void toggleSortButton(View button, SortDescriptor descriptor) {
        if (lastCheckedButton == button) {
            viewOutput.toggleSortOrder();
            lastCheckedButton.setActivated(!lastCheckedButton.isActivated());
        } else {
            viewOutput.sortDescriptorChanged(descriptor);
            if (lastCheckedButton != null) {
                lastCheckedButton.setActivated(false);
            }
            lastCheckedButton = button;
        }
    }

    @Override
    public void updateListItems(List<RateItem> rateItems) {
        adapter.setItems(rateItems);
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void updateListItem(RateItem rateItem) {
        adapter.updateItem(rateItem);
    }

    @Override
    public void navigate(Class clazz, Parcelable parcelable) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("data", parcelable);

        startActivity(intent);
    }

    public void setViewOutput(RatesViewOutput viewOutput) {
        this.viewOutput = viewOutput;
    }
}
