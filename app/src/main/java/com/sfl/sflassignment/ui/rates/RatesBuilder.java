package com.sfl.sflassignment.ui.rates;

import com.sfl.sflassignment.ui.rates.interactor.RatesInteractor;
import com.sfl.sflassignment.ui.rates.presenter.RatesPresenter;
import com.sfl.sflassignment.ui.rates.view.RatesActivity;

public class RatesBuilder {

    public void build(RatesActivity view) {
        RatesPresenter presenter = new RatesPresenter();
        presenter.setView(view);

        RatesInteractor interactor = new RatesInteractor();
        presenter.setInteractor(interactor);
        interactor.setOutput(presenter);

        view.setViewOutput(presenter);
    }
}
