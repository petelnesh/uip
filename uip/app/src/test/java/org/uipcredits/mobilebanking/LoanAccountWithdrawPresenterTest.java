package org.uipcredits.mobilebanking;

import android.content.Context;

import com.uipcredits.mobilebanking.R;

import org.uipcredits.mobilebanking.api.DataManager;
import com.uipcredits.mobilebanking.models.accounts.loan.LoanWithdraw;
import org.uipcredits.mobilebanking.presenters.LoanAccountWithdrawPresenter;
import org.uipcredits.mobilebanking.ui.views.LoanAccountWithdrawView;
import org.uipcredits.mobilebanking.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.uipcredits.mobilebanking.api.DataManager;
import org.uipcredits.mobilebanking.ui.views.LoanAccountWithdrawView;
import org.uipcredits.mobilebanking.util.RxSchedulersOverrideRule;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by dilpreet on 24/7/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoanAccountWithdrawPresenterTest {

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Mock
    Context context;

    @Mock
    DataManager dataManager;

    @Mock
    LoanAccountWithdrawView view;

    @Mock
    LoanWithdraw loanWithdraw;

    @Mock
    ResponseBody mockedResponseBody;

    private LoanAccountWithdrawPresenter presenter;
    private long loanId = 1;

    @Before
    public void setUp() {
        presenter = new LoanAccountWithdrawPresenter(dataManager, context);
        presenter.attachView(view);
    }

    @After
    public void tearDown() {
        presenter.detachView();
    }

    @Test
    public void testWithdrawLoanAccount() {
        when(dataManager.withdrawLoanAccount(loanId, loanWithdraw)).thenReturn(Observable.
                just(mockedResponseBody));

        presenter.withdrawLoanAccount(loanId, loanWithdraw);

        verify(view).showProgress();
        verify(view).hideProgress();
        verify(view).showLoanAccountWithdrawSuccess();
        verify(view, never()).showLoanAccountWithdrawError(context.getString(R.string.
                error_loan_account_withdraw));
    }

    @Test
    public void testWithdrawLoanAccountFail() {
        when(dataManager.withdrawLoanAccount(loanId, loanWithdraw)).thenReturn(Observable.
                <ResponseBody>error(new RuntimeException()));

        presenter.withdrawLoanAccount(loanId, loanWithdraw);

        verify(view).showProgress();
        verify(view).hideProgress();
        verify(view).showLoanAccountWithdrawError(context.getString(R.string.
                error_loan_account_withdraw));
        verify(view, never()).showLoanAccountWithdrawSuccess();
    }

}
