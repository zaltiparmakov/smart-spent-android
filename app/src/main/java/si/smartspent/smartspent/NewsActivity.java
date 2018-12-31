package si.smartspent.smartspent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class NewsActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_news, frameLayout);

        // TODO: User of EventRegistry.org to get news related to finances, stocks markets, etc.
    }
}
