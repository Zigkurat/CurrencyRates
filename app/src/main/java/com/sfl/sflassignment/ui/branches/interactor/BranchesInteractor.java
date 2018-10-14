package com.sfl.sflassignment.ui.branches.interactor;

import com.sfl.sflassignment.network.NetworkManager;
import com.sfl.sflassignment.network.ResultCallback;
import com.sfl.sflassignment.network.model.BankInfo;
import com.sfl.sflassignment.ui.data.BankData;

public class BranchesInteractor implements BranchesInteractorInput {

    private NetworkManager networkManager = new NetworkManager();
    private BranchesInteractorOutput output;

    @Override
    public void getBankInfo(String identifier) {
        networkManager.getBankInfo(identifier, new ResultCallback<BankInfo>() {
            @Override
            public void success(BankInfo result) {
                output.presentOutput(createBranchData(result));
            }

            @Override
            public void failure(Throwable error) {
                output.handleError(error);
            }
        });
    }

    private BankData createBranchData(BankInfo bankInfo) {
        return new BankData();
    }

    public BranchesInteractorOutput getOutput() {
        return output;
    }

    public void setOutput(BranchesInteractorOutput output) {
        this.output = output;
    }
}
