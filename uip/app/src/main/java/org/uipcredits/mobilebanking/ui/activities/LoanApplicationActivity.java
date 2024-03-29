package org.uipcredits.mobilebanking.ui.activities;

import android.os.Bundle;

import org.uipcredits.mobilebanking.R;
import org.uipcredits.mobilebanking.ui.activities.base.BaseActivity;
import org.uipcredits.mobilebanking.ui.enums.LoanState;
import org.uipcredits.mobilebanking.ui.fragments.LoanApplicationFragment;


public class LoanApplicationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_application);
        if (savedInstanceState == null) {
            replaceFragment(LoanApplicationFragment.newInstance(LoanState.CREATE), false,
                    R.id.container);
        }
        showBackButton();
    }
}
