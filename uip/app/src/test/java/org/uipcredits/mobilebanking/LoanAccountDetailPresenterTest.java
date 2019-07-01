package org.uipcredits.mobilebanking;

import android.content.Context;

import com.uipcredits.mobilebanking.R;

import org.uipcredits.mobilebanking.api.DataManager;
import com.uipcredits.mobilebanking.models.accounts.loan.LoanWithAssociations;
import org.uipcredits.mobilebanking.presenters.LoanAccountsDetailPresenter;
import org.uipcredits.mobilebanking.ui.views.LoanAccountsDetailView;
import org.uipcredits.mobilebanking.util.RxSchedulersOverrideRule;
import org.uipcredits.mobilebanking.utils.Constants;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.uipcredits.mobilebanking.api.DataManager;
import org.uipcredits.mobilebanking.ui.views.LoanAccountsDetailView;
import org.uipcredits.mobilebanking.util.RxSchedulersOverrideRule;
import org.uipcredits.mobilebanking.utils.Constants;

import io.reactivex.Observable;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by dilpreet on 27/6/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoanAccountDetailPresenterTest {

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Mock
    Context context;

    @Mock
    DataManager dataManager;

    @Mock
    LoanAccountsDetailView view;

    private LoanAccountsDetailPresenter presenter;
    private LoanWithAssociations loanWithAssociations;

    @Before
    public void setUp() throws Exception {
        presenter = new LoanAccountsDetailPresenter(dataManager, context);
        presenter.attachView(view);

        loanWithAssociations = FakeRemoteDataSource.getLoanAccountWithTransaction();
    }

    @Test
    public void testLoadLoanAccountDetails() throws Exception {
        when(dataManager.getLoanWithAssociations(Constants.REPAYMENT_SCHEDULE, 4)).
                thenReturn(Observable.just(loanWithAssociations));

        presenter.loadLoanAccountDetails(4);

        verify(view).showProgress();
        verify(view).hideProgress();
        verify(view).showLoanAccountsDetail(loanWithAssociations);
        verify(view, never()).showErrorFetchingLoanAccountsDetail(context.
                getString(R.string.error_loan_account_details_loading));
    }

    @Test
    public void testLoadLoanAccountDetailsFails() throws Exception {
        when(dataManager.getLoanWithAssociations(Constants.REPAYMENT_SCHEDULE, 4)).
                thenReturn(Observable.<LoanWithAssociations>error(new RuntimeException()));

        presenter.loadLoanAccountDetails(4);

        verify(view).showProgress();
        verify(view).hideProgress();
        verify(view).showErrorFetchingLoanAccountsDetail(context.
                getString(R.string.error_loan_account_details_loading));
        verify(view, never()).showLoanAccountsDetail(null);
    }
}