package com.sfl.sflassignment.ui.branches.view;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sfl.sflassignment.R;
import com.sfl.sflassignment.ui.branches.BranchesBuilder;
import com.sfl.sflassignment.ui.branches.view.adapter.BranchesRecyclerAdapter;
import com.sfl.sflassignment.ui.branches.view.adapter.CurrenciesRecyclerAdapter;
import com.sfl.sflassignment.ui.branches.view.model.BranchItem;
import com.sfl.sflassignment.ui.branches.view.model.CurrencyItem;
import com.sfl.sflassignment.ui.branches.view.model.Header;
import com.sfl.sflassignment.ui.constant.TransactionType;

import java.util.List;
import java.util.Map;

public class BranchesActivity extends AppCompatActivity implements BranchesViewInput {

    private BranchesViewOutput viewOutput;

    private NestedScrollView scrollView;

    private ImageView branchLogo;
    private TextView branchTitle;
    private TextView branchCity;
    private TextView branchAddress;
    private TextView branchPhoneNumber;
    private TextView branchWorkingDays;

    private CurrenciesRecyclerAdapter currencyAdapter;
    private BranchesRecyclerAdapter branchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI();

        BranchesBuilder builder = new BranchesBuilder();
        builder.build(this);

        if (savedInstanceState != null) {
            viewOutput.loadState(savedInstanceState);
        }

        viewOutput.loadData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        viewOutput.saveState(outState);
    }

    private void setupUI() {
        setContentView(R.layout.activity_branches);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        scrollView = findViewById(R.id.scroll);

        setupHeader();
        setupTransactionSelection();
        setupCurrencyList();
        setupBranchList();
    }

    private void setupHeader() {
        branchLogo = findViewById(R.id.iv_logo);
        branchTitle = findViewById(R.id.tv_branch_title);
        branchCity = findViewById(R.id.tv_branch_city);
        branchAddress = findViewById(R.id.tv_branch_address);
        branchPhoneNumber = findViewById(R.id.tv_branch_phone_number);
        branchPhoneNumber.setPaintFlags(branchPhoneNumber.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        branchWorkingDays = findViewById(R.id.tv_working_days);
    }

    private void setupTransactionSelection() {
        RadioGroup radioGroup = findViewById(R.id.rg_transaction_type);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_cashless) {
                    viewOutput.transactionTypeSelected(TransactionType.CASHLESS);
                } else {
                    viewOutput.transactionTypeSelected(TransactionType.CASH);
                }
            }
        });
    }

    private void setupCurrencyList() {
        RecyclerView currencyRecyclerView = findViewById(R.id.rv_currencies);
        currencyAdapter = new CurrenciesRecyclerAdapter();
        currencyRecyclerView.setAdapter(currencyAdapter);
        currencyRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void setupBranchList() {
        RecyclerView branchRecyclerView = findViewById(R.id.rv_branches);
        branchAdapter = new BranchesRecyclerAdapter();
        branchAdapter.setOnItemClickListener(new BranchesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(BranchItem item) {
                viewOutput.branchSelected(item);
            }
        });
        branchRecyclerView.setAdapter(branchAdapter);
        branchRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void updateHeader(Header header) {
        branchLogo.setImageResource(R.mipmap.ic_launcher_round);
        branchTitle.setText(header.getTitle());
        branchCity.setText(header.getCity());
        branchAddress.setText(header.getAddress());
        branchPhoneNumber.setText(header.getPhoneNumber());
        branchWorkingDays.setText(parseWorkingDays(header.getWorkingDays()));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(header.getTitle());
        }

        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.setExpanded(true);
        scrollView.smoothScrollTo(0, 0);
    }

    @Override
    public void updateBranchItems(List<BranchItem> branchItems) {
        branchAdapter.setBranchItems(branchItems);
    }

    @Override
    public void updateCurrencyItems(List<CurrencyItem> currencyItems) {
        currencyAdapter.setCurrencyItems(currencyItems);
    }

    public void setViewOutput(BranchesViewOutput viewOutput) {
        this.viewOutput = viewOutput;
    }

    private String parseWorkingDays(Map<String, String> workingDays) {
        StringBuilder result = new StringBuilder();
        String[] weekDays = getResources().getStringArray(R.array.week_days);
        for (Map.Entry<String, String> entry : workingDays.entrySet()) {
            String days = entry.getKey();
            String hours = entry.getValue();
            if (days.contains("-")) {
                String firstDay = days.substring(0, days.indexOf('-'));
                String lastDay = days.substring(days.indexOf('-') + 1, days.length());

                result.append(weekDays[Integer.parseInt(firstDay) - 1]);
                result.append('-');
                result.append(weekDays[Integer.parseInt(lastDay) - 1]);
                result.append('\t');
                result.append(hours);
                result.append('\n');
            } else {
                result.append(weekDays[Integer.parseInt(days) - 1]);
                result.append(hours);
                result.append('\n');
            }
        }
        result.deleteCharAt(result.length() - 1);

        return result.toString();
    }
}
