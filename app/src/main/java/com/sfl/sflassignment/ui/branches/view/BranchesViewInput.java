package com.sfl.sflassignment.ui.branches.view;

import com.sfl.sflassignment.ui.branches.view.model.BranchItem;
import com.sfl.sflassignment.ui.branches.view.model.CurrencyItem;
import com.sfl.sflassignment.ui.branches.view.model.Header;

import java.util.List;

public interface BranchesViewInput {

    void updateHeader(Header header);

    void updateBranchItems(List<BranchItem> branchItems);

    void updateCurrencyItems(List<CurrencyItem> currencyItems);
}
