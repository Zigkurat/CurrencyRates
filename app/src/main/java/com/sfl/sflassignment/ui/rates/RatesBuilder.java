package com.sfl.sflassignment.ui.rates;

import com.sfl.sflassignment.ui.rates.interactor.RatesInteractor;
import com.sfl.sflassignment.ui.rates.presenter.RatesPresenter;
import com.sfl.sflassignment.ui.rates.view.RatesActivity;

public class RatesBuilder {
//    wireframe = BatchFilingWireframe()
//
//    let presenter = BatchFilingPresenter()
//    presenter.wireframe = wireframe
//    wireframe.presenter = presenter
//
//    let interactor = BatchFilingInteractor(account: account)
//    presenter.interactor = interactor
//    interactor.output = presenter
//    interactor.dmsAccountManager = BatchFilingDMSAccountManager()
//    interactor.aiEngineWrapper = BatchFilingAIEWrapper()
//    interactor.pendingOpsWrapper = BatchFilingPendingOpsWrapper()
//
//    let loadingViewController = BatchFilingViewController()
//    loadingViewController.wireframe = wireframe
//    presenter.userInterface = loadingViewController
//    loadingViewController.eventHandler = presenter
//    wireframe.initialViewController = loadingViewController

    public void build(RatesActivity view) {
        RatesPresenter presenter = new RatesPresenter();
        presenter.setView(view);

        RatesInteractor interactor = new RatesInteractor();
        presenter.setInteractor(interactor);
        interactor.setOutput(presenter);

        view.setViewOutput(presenter);
    }
}
