package si.smartspent.smartspent.Transactions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;

import si.smartspent.smartspent.DrawerActivity;
import si.smartspent.smartspent.Location;
import si.smartspent.smartspent.R;

public class TransactionsActivity extends DrawerActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Transactions> transactionsList;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // insert this view into drawer layout
        getLayoutInflater().inflate(R.layout.activity_transactions, frameLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        transactionsList = new ArrayList<>();
        transactionsList.add(
                new Transactions(0,"Test","Test",new Location("12.123","15.1231"),
                        12, LocalDateTime.now(), "test", "test"));
        transactionsList.add(
                new Transactions(0,"Test","Test",new Location("12.123","15.1231"),
                        12, LocalDateTime.now(), "test", "test")
        );

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new TransactionsAdapter(transactionsList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
