package org.uipcredits.mobilebanking.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.uipcredits.mobilebanking.R;
import org.uipcredits.mobilebanking.injection.ActivityContext;
import org.uipcredits.mobilebanking.models.accounts.loan.LoanAccount;
import org.uipcredits.mobilebanking.utils.CurrencyUtil;
import org.uipcredits.mobilebanking.utils.DateHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Vishwajeet
 * @since 22/6/16.
 */
public class LoanAccountsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private List<LoanAccount> loanAccountsList = new ArrayList<>();

    @Inject
    public LoanAccountsListAdapter(@ActivityContext Context context) {
        this.context = context;
    }

    public void setLoanAccountsList(List<LoanAccount> loanAccountsList) {
        this.loanAccountsList = loanAccountsList;
        notifyDataSetChanged();
    }

    public List<LoanAccount> getLoanAccountsList() {
        return loanAccountsList;
    }

    public LoanAccount getItem(int position) {
        return loanAccountsList.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_loan_account, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {

            LoanAccount loanAccount = getItem(position);
            ((ViewHolder) holder).tvClientLoanAccountNumber.setText(loanAccount.getAccountNo());
            ((ViewHolder) holder).tvLoanAccountProductName.setText(loanAccount.getProductName());
            ((ViewHolder) holder).tvAccountBalance.setVisibility(View.GONE);

            if (loanAccount.getStatus().getActive() && loanAccount.getInArrears()) {

                setLoanAccountsGeneralDetails(holder,R.drawable.redimg, R.color.red, context.getString(R.string.
                        string_and_string, context.getString(R.string.disbursement), DateHelper
                        .getDateAsString(loanAccount.getTimeline().getActualDisbursementDate())));
                setLoanAccountsDetails(((ViewHolder) holder), loanAccount, R.color.red,R.drawable.redimg);

            } else if (loanAccount.getStatus().getActive()) {

                setLoanAccountsGeneralDetails(holder,R.drawable.greenimg, R.color.deposit_green, context.getString(R.
                        string.string_and_string, context.getString(R.string.disbursement),
                        DateHelper.getDateAsString(loanAccount.getTimeline().
                                getActualDisbursementDate())));
                setLoanAccountsDetails(((ViewHolder) holder), loanAccount, R.color.deposit_green,R.drawable.greenimg);

            } else if (loanAccount.getStatus().getWaitingForDisbursal()) {

                setLoanAccountsGeneralDetails(holder, R.drawable.blueimg,R.color.blue, context.getString(R.string.
                        string_and_string, context.getString(R.string.approved), DateHelper
                        .getDateAsString(loanAccount.getTimeline().getApprovedOnDate())));

            } else if (loanAccount.getStatus().getPendingApproval()) {

                setLoanAccountsGeneralDetails(holder,R.drawable.redimg, R.color.light_yellow, context.getString(R.
                                string.string_and_string, context.getString(R.string.
                                submitted), DateHelper.getDateAsString(loanAccount.
                                getTimeline().getSubmittedOnDate())));

            }  else if (loanAccount.getStatus().getOverpaid()) {

                setLoanAccountsDetails(((ViewHolder) holder), loanAccount, R.color.purple,R.drawable.redimg);
                setLoanAccountsGeneralDetails(holder,R.drawable.redimg, R.color.purple, context.getString(R.string.
                        string_and_string, context.getString(R.string.disbursement), DateHelper
                        .getDateAsString(loanAccount.getTimeline().getActualDisbursementDate())));

            } else if (loanAccount.getStatus().getClosed()) {

                setLoanAccountsGeneralDetails(holder, R.drawable.redimg,R.color.black, context.getString(R.string.
                        string_and_string, context.getString(R.string.closed), DateHelper
                        .getDateAsString(loanAccount.getTimeline().getClosedOnDate())));

            } else {
                setLoanAccountsGeneralDetails(holder,R.drawable.redimg, R.color.gray_dark, context.getString(R.string.
                            string_and_string, context.getString(R.string.withdrawn), DateHelper
                        .getDateAsString(loanAccount.getTimeline().getWithdrawnOnDate())));

            }
        }

    }

    private void setLoanAccountsDetails(ViewHolder viewHolder, LoanAccount loanAccount, int color, int img) {

        double amountBalance = loanAccount.getLoanBalance() != 0 ? loanAccount.getLoanBalance() : 0;
        viewHolder.tvAccountBalance.setVisibility(View.VISIBLE);
        viewHolder.tvAccountBalance.setText(CurrencyUtil.formatCurrency(context, amountBalance));
        viewHolder.tvAccountBalance.setTextColor(ContextCompat.getColor(context, color));

            viewHolder.imagess.setBackgroundResource(img);

    }

    private void setLoanAccountsGeneralDetails(RecyclerView.ViewHolder holder,int img, int colorId,
                                                 String dateStr) {
        ((ViewHolder) holder).ivStatusIndicator.setColorFilter(ContextCompat.
                getColor(context, colorId));
        ((ViewHolder) holder).tvDate.setText(dateStr);

        ((ViewHolder) holder).imagess.setBackgroundResource(img);
    }
    @Override
    public int getItemCount() {
        return loanAccountsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_clientLoanAccountNumber)
        TextView tvClientLoanAccountNumber;

        @BindView(R.id.tv_loanAccountProductName)
        TextView tvLoanAccountProductName;

        @BindView(R.id.iv_status_indicator)
        AppCompatImageView ivStatusIndicator;

        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.images)
        ImageView imagess;

        @BindView(R.id.tv_account_balance)
        TextView tvAccountBalance;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
