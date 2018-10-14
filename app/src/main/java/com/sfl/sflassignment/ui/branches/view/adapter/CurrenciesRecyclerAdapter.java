package com.sfl.sflassignment.ui.branches.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sfl.sflassignment.R;
import com.sfl.sflassignment.ui.branches.view.model.CurrencyItem;

import java.util.ArrayList;
import java.util.List;

public class CurrenciesRecyclerAdapter extends RecyclerView.Adapter<CurrenciesRecyclerAdapter.CurrencyViewHolder> {
    private List<CurrencyItem> currencyItems;

    public CurrenciesRecyclerAdapter() {
        currencyItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_currency, viewGroup, false);
        return new CurrencyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder currencyViewHolder, int i) {
        CurrencyItem item = currencyItems.get(currencyViewHolder.getAdapterPosition());

        currencyViewHolder.currency.setImageResource(R.mipmap.ic_launcher);
        currencyViewHolder.title.setText(item.getTitle());
        currencyViewHolder.buy.setText(item.getBuy());
        currencyViewHolder.sell.setText(item.getSell());
    }

    @Override
    public int getItemCount() {
        return currencyItems == null ? 0 : currencyItems.size();
    }

    public void setCurrencyItems(List<CurrencyItem> currencyItems) {
        this.currencyItems.clear();
        this.currencyItems.addAll(currencyItems);
        notifyDataSetChanged();
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder {
        ImageView currency;
        TextView title;
        TextView buy;
        TextView sell;

        CurrencyViewHolder(View itemView) {
            super(itemView);
            currency = itemView.findViewById(R.id.iv_currency);
            title = itemView.findViewById(R.id.tv_currency_title);
            buy = itemView.findViewById(R.id.tv_currency_buy_price);
            sell = itemView.findViewById(R.id.tv_currency_sell_price);
        }
    }
}
