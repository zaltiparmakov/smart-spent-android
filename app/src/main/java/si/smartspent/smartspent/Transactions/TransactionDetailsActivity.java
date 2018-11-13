package si.smartspent.smartspent.Transactions;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import si.smartspent.smartspent.DrawerActivity;
import si.smartspent.smartspent.R;

import static si.smartspent.smartspent.Transactions.TransactionsAdapter.KEY_AMOUNT;
import static si.smartspent.smartspent.Transactions.TransactionsAdapter.KEY_DESC;
import static si.smartspent.smartspent.Transactions.TransactionsAdapter.KEY_LOCATION;
import static si.smartspent.smartspent.Transactions.TransactionsAdapter.KEY_NAME;
import static si.smartspent.smartspent.Transactions.TransactionsAdapter.KEY_TYPE;

public class TransactionDetailsActivity extends DrawerActivity {
    public TextView name;
    public TextView description;
    public TextView location;
    public TextView amount;
    public TextView type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // insert this view into drawer layout
        getLayoutInflater().inflate(R.layout.activity_transaction_details, frameLayout);

        name = findViewById(R.id.txt_name);
        description = findViewById(R.id.txt_description);
        location = findViewById(R.id.txt_location);
        amount = findViewById(R.id.txt_amount);
        type = findViewById(R.id.txt_type);

        fill_data();
    }

    private void fill_data() {
        // get intent that started this activity
        Intent intent = getIntent();

        name.setText(intent.getExtras().get(KEY_NAME).toString());
        description.setText(intent.getExtras().get(KEY_DESC).toString());
        location.setText(intent.getExtras().get(KEY_LOCATION).toString());
        amount.setText(intent.getExtras().get(KEY_AMOUNT).toString());
        type.setText(intent.getExtras().get(KEY_TYPE).toString());
    }
}
