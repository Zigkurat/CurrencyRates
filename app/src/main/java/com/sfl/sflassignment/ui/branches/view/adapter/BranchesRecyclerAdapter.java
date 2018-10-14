package com.sfl.sflassignment.ui.branches.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sfl.sflassignment.R;
import com.sfl.sflassignment.ui.branches.view.model.BranchItem;

import java.util.ArrayList;
import java.util.List;

public class BranchesRecyclerAdapter extends RecyclerView.Adapter<BranchesRecyclerAdapter.BranchViewHolder> {
    private List<BranchItem> branchItems;
    private OnItemClickListener onItemClickListener;

    public BranchesRecyclerAdapter() {
        branchItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public BranchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_branch, viewGroup, false);
        return new BranchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchViewHolder branchViewHolder, int i) {
        final BranchItem branchItem = branchItems.get(branchViewHolder.getAdapterPosition());

        branchViewHolder.title.setText(branchItem.getTitle());
        branchViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClicked(branchItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return branchItems == null ? 0 : branchItems.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setBranchItems(List<BranchItem> branchItems) {
        this.branchItems.clear();
        this.branchItems.addAll(branchItems);
        notifyDataSetChanged();
    }

    class BranchViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        BranchViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_branch_title);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(BranchItem item);
    }
}
