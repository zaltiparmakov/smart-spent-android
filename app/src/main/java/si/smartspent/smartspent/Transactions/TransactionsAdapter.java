package si.smartspent.smartspent.Transactions;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import si.smartspent.smartspent.R;

class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder> {
    private ArrayList<Transactions> transactionsList;
    public static final String KEY_NAME = "name";
    public static final String KEY_DESC = "description";
    public static final String KEY_LOCATION = "";
    public static final String KEY_CURRENCY = "";
    public static final String KEY_AMOUNT = "";
    public static final String KEY_DATETIME = "";
    public static final String KEY_TYPE = "type";
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
        public TextView description;
        public TextView location;
        public TextView amount;
        public TextView type;

        public TransactionsViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txt_name);
            description = itemView.findViewById(R.id.txt_description);
            location = itemView.findViewById(R.id.txt_location);
            amount = itemView.findViewById(R.id.txt_amount);
            type = itemView.findViewById(R.id.txt_type);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final TransactionsViewHolder holder, final int position) {
        final Transactions transactions = transactionsList.get(position);
        holder.name.setText(transactions.getName());
        holder.description.setText(transactions.getDescription());
        holder.amount.setText(Double.toString(transactions.getAmount()));
        holder.location.setText(transactions.getLocation().toString());
        holder.type.setText(transactions.getType());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TransactionDetails = new Intent(v.getContext(), TransactionDetailsActivity.class)
                        .putExtra(KEY_NAME, transactions.getName())
                        .putExtra(KEY_DESC, transactions.getDescription())
                        .putExtra(KEY_AMOUNT, Double.toString(transactions.getAmount()))
                        .putExtra(KEY_PAYMETHOD, transactions.getPayMethod())
                        .putExtra(KEY_DATETIME, transactions.getDateTime().toString())
                        .putExtra(KEY_LOCATION, transactions.getLocation().toString())
                        .putExtra(KEY_TYPE, transactions.getType());
//                        .putExtra(KEY_CURRENCY, transactions.getCurrency().toString());
                v.getContext().startActivity(TransactionDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }
}
