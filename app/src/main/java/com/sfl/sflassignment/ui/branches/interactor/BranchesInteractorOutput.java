package com.sfl.sflassignment.ui.branches.interactor;

import com.sfl.sflassignment.ui.data.BankData;

public interface BranchesInteractorOutput {
    void presentOutput(BankData bankData);

    void handleError(Throwable error);
}
