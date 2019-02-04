package si.smartspent.smartspent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsActivity extends DrawerActivity {

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
                    String js = "javascript:var element1 = document.getElementsByClassName('pGxpHc')[0]; " +
                            "element1.parentNode.removeChild(element1);";
                    view.loadUrl(js);
                    view.setVisibility(View.VISIBLE);
                    //view.loadUrl(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mWebView.setVisibility(View.GONE);
        mWebView.loadUrl("https://news.google.com/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRGx6TVdZU0FtVnVHZ0pWVXlnQVAB?hl=en-US&gl=US&ceid=US%3Aen");
    }
}
