package com.sfl.sflassignment.ui.branches;

import com.sfl.sflassignment.ui.branches.interactor.BranchesInteractor;
import com.sfl.sflassignment.ui.branches.presenter.BranchesPresenter;
import com.sfl.sflassignment.ui.branches.view.BranchesActivity;
import com.sfl.sflassignment.ui.data.BankData;

public class BranchesBuilder {
    public void build(BranchesActivity view) {
        BranchesPresenter presenter = new BranchesPresenter();
        presenter.setView(view);
        presenter.setBankData((BankData)view.getIntent().getExtras().get("data"));

        BranchesInteractor interactor = new BranchesInteractor();
        presenter.setInteractor(interactor);
        interactor.setOutput(presenter);

        view.setViewOutput(presenter);
    }
}
