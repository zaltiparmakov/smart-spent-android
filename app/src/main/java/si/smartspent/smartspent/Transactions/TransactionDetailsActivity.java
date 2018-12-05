package si.smartspent.smartspent.Transactions;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import si.smartspent.smartspent.DrawerActivity;
import si.smartspent.smartspent.R;

import static si.smartspent.smartspent.Transactions.TransactionsAdapter.KEY_AMOUNT;
import static si.smartspent.smartspent.Transactions.TransactionsAdapter.KEY_LOCATION;
import static si.smartspent.smartspent.Transactions.TransactionsAdapter.KEY_DESCRIPTION;
import static si.smartspent.smartspent.Transactions.TransactionsAdapter.KEY_CATEGORY;

public class TransactionDetailsActivity extends DrawerActivity {
    public TextView description;
    public TextView location;
    public TextView amount;
    public TextView category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // insert this view into drawer layout
        getLayoutInflater().inflate(R.layout.activity_transaction_details, frameLayout);

        description = findViewById(R.id.txt_description);
        location = findViewById(R.id.txt_location);
        amount = findViewById(R.id.txt_amount);
        category = findViewById(R.id.txt_category);

        fill_data();
    }

    private void fill_data() {
        // get intent that started this activity
        Intent intent = getIntent();

        description.setText(intent.getExtras().get(KEY_DESCRIPTION).toString());
        location.setText(intent.getExtras().get(KEY_LOCATION).toString());
        amount.setText(intent.getExtras().get(KEY_AMOUNT).toString());
        category.setText(intent.getExtras().get(KEY_CATEGORY).toString());
    }
}
