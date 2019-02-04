package si.smartspent.smartspent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class StockMarketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_market);
        WebView mWebView = (WebView) findViewById(R.id.stock_web);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                try {
                    String js = "javascript:var element1 = document.getElementById('sfcnt'); " +
                            "element1.parentNode.removeChild(element1); " +
                            "var element3 = document.getElementById('qslc');" +
                            "element3.parentNode.removeChild(element3);";
                    view.loadUrl(js);
                    view.setVisibility(View.VISIBLE);
                    //view.loadUrl(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mWebView.setVisibility(View.GONE);
        mWebView.loadUrl("https://www.google.com/finance#wptab=s:H4sIAAAAAAAAAOPQeMSozC3w8sc9YSmpSWtOXmMU4RJyy8xLzEtO9UnMS8nMSw9ITE_l2cXEG-4f5OMS7-sY5O0aEgwAS36xMjgAAAA");

        /*
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        */
    }
}
