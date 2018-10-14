package com.sfl.sflassignment.ui.branches.view;

import android.os.Bundle;

import com.sfl.sflassignment.ui.branches.view.model.BranchItem;
import com.sfl.sflassignment.ui.constant.TransactionType;

public interface BranchesViewOutput {

    void loadData();

    void branchSelected(BranchItem branch);

    void transactionTypeSelected(TransactionType transaction);

    void saveState(Bundle bundle);

    void loadState(Bundle bundle);
}
