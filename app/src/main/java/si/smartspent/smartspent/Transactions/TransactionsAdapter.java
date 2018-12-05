package si.smartspent.smartspent.Transactions;

import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import si.smartspent.smartspent.R;

class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder> {
    private ArrayList<Transaction> transactionsList;
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DESC = "description";
    public static final String KEY_LOCATION = "";
    public static final String KEY_CURRENCY = "";
    public static final String KEY_AMOUNT = "";
    public static final String KEY_DATE = "";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_PAYMETHOD = "paymentMethod";

    public TransactionsAdapter(ArrayList data) {
        transactionsList = data;
    }

    @NonNull
    @Override
    public TransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.transactions_list, viewGroup, false);

        return new TransactionsViewHolder(view);
    }

    // Provide reference to the views for each data item
    public class TransactionsViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
//        public TextView description;
//        public TextView location;
        public TextView amount;
        public TextView category;

        public TransactionsViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txt_description);
            category = itemView.findViewById(R.id.txt_category);
//            description = itemView.findViewById(R.id.txt_description);
//            location = itemView.findViewById(R.id.txt_location);
            amount = itemView.findViewById(R.id.txt_amount);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final TransactionsViewHolder holder, final int position) {
        final Transaction transactions = transactionsList.get(position);
        holder.name.setText(transactions.getDescription());
//        holder.location.setText(transactions.getLocation().toString());
        String amountString = "€ " + Double.toString(transactions.getAmount());
        holder.amount.setText(amountString);
        holder.category.setText(transactions.getCategory());

        holder.itemView.setOnClickListener(v -> {
            Intent TransactionDetails = new Intent(v.getContext(), TransactionDetailsActivity.class)
                    .putExtra(KEY_DESCRIPTION, transactions.getDescription())
                    .putExtra(KEY_AMOUNT, Double.toString(transactions.getAmount()))
                    .putExtra(KEY_PAYMETHOD, transactions.getPaymentMethod())
                    .putExtra(KEY_DATE, transactions.getDate().toString())
                    .putExtra(KEY_LOCATION, transactions.getLocation().toString())
                    .putExtra(KEY_CATEGORY, transactions.getCategory());
            v.getContext().startActivity(TransactionDetails);
        });
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }
}
