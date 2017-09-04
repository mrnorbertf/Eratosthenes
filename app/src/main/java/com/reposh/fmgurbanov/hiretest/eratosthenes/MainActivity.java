package com.reposh.fmgurbanov.hiretest.eratosthenes;

import com.reposh.fmgurbanov.hiretest.eratosthenes.baseComponents.ViewModelActivity;
import com.reposh.fmgurbanov.hiretest.eratosthenes.databinding.ActivityMainBinding;

public class MainActivity extends ViewModelActivity<ActivityMainBinding, MainViewModel> {

    @Override
    public MainViewModel onCreate() {
        return new MainViewModel(this);
    }


    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    @Override
    public int getVariable() {
        return com.reposh.fmgurbanov.hiretest.eratosthenes.BR.viewModel;
    }

    /**
     * Override for set layout resource
     *
     * @return layout resource id
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}
