package com.example.makroid.tellit.fragments;

import android.support.v4.app.Fragment;

import rx.subscriptions.CompositeSubscription;

public abstract class SignInBaseFragment extends Fragment {

    // region Member Variables
    protected CompositeSubscription compositeSubscription = new CompositeSubscription();
    // endregion

    // region Lifecycle Methods
    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
    // region
}
