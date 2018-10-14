package com.sfl.sflassignment.ui.rates.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sfl.sflassignment.R;
import com.sfl.sflassignment.ui.rates.view.model.RateItem;

import java.util.ArrayList;
import java.util.List;

public class RatesRecyclerAdapter extends RecyclerView.Adapter<RatesRecyclerAdapter.RateViewHolder> {
    private List<RateItem> rateItems;
    private OnItemClickListener onItemClickListener;

    private float bestBuyPrice;
    private float bestSellPrice;

    public RatesRecyclerAdapter() {
        rateItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public RateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_rate, viewGroup, false);
        return new RateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RateViewHolder rateViewHolder, int i) {
        Context context = rateViewHolder.itemView.getContext();
        final RateItem item = rateItems.get(rateViewHolder.getAdapterPosition());

        rateViewHolder.logo.setImageResource(R.mipmap.ic_launcher);
        rateViewHolder.title.setText(item.getTitle());
        if (item.getDistance() != null) {
            rateViewHolder.distance.setText(String.valueOf(item.getDistance()));
        }
        if (item.getBuyPrice() == bestBuyPrice) {
            rateViewHolder.buyPrice.setText(getHighlightedString(context, String.valueOf(item.getBuyPrice())));
        } else {
            rateViewHolder.buyPrice.setText(String.valueOf(item.getBuyPrice()));
        }
        if (item.getSellPrice() == bestSellPrice) {
            rateViewHolder.sellPrice.setText(getHighlightedString(context, String.valueOf(item.getSellPrice())));
        } else {
            rateViewHolder.sellPrice.setText(String.valueOf(item.getSellPrice()));
        }

        rateViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClicked(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rateItems == null ? 0 : rateItems.size();
    }

    public void setItems(List<RateItem> rateItems) {
        this.rateItems.clear();
        this.rateItems.addAll(rateItems);

        bestBuyPrice = 0;
        bestSellPrice = Float.MAX_VALUE;
        for (RateItem item : rateItems) {
            if (item.getBuyPrice() > bestBuyPrice) {
                bestBuyPrice = item.getBuyPrice();
            }
            if (item.getSellPrice() < bestSellPrice) {
                bestSellPrice = item.getSellPrice();
            }
        }
    }

    public void updateItem(RateItem rateItem) {
        int index = rateItems.indexOf(rateItem);
        if (index != -1) {
            rateItems.set(index, rateItem);
            notifyItemChanged(index);
        }
    }

    private SpannableString getHighlightedString(Context context, String target) {
        int colorAccent = ContextCompat.getColor(context, R.color.colorPrimary);
        SpannableString spannableString = new SpannableString(target);
        spannableString.setSpan(new ForegroundColorSpan(colorAccent), 0, target.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, target.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public void setOnItemClickListener(RatesRecyclerAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class RateViewHolder extends RecyclerView.ViewHolder {
        ImageView logo;
        TextView title;
        TextView distance;
        TextView buyPrice;
        TextView sellPrice;

        RateViewHolder(View itemView) {
            super(itemView);

            logo = itemView.findViewById(R.id.iv_logo);
            title = itemView.findViewById(R.id.tv_title);
            distance = itemView.findViewById(R.id.tv_distance);
            buyPrice = itemView.findViewById(R.id.tv_buy_price);
            sellPrice = itemView.findViewById(R.id.tv_sell_price);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(RateItem item);
    }
}
