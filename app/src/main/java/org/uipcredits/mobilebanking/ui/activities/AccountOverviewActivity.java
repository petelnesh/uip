package org.uipcredits.mobilebanking.ui.activities;

import android.os.Bundle;

import org.uipcredits.mobilebanking.R;
import org.uipcredits.mobilebanking.ui.activities.base.BaseActivity;
import org.uipcredits.mobilebanking.ui.fragments.AccountOverviewFragment;
import org.uipcredits.mobilebanking.ui.fragments.AccountOverviewFragment;


/**
 * @author Rajan Maurya
 *         On 16/10/17.
 */
public class AccountOverviewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        replaceFragment(AccountOverviewFragment.newInstance(), false, R.id.container);
        showBackButton();
        hideToolbarElevation();
    }
}
