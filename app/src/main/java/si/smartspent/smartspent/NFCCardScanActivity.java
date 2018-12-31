package si.smartspent.smartspent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class NFCCardScanActivity extends DrawerActivity {
    private ImageView nfc_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_nfccard_scan, frameLayout);
        getSupportActionBar().hide();

        nfc_image = findViewById(R.id.nfc_image);

        Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(1600);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        nfc_image.setAnimation(animation);
    }
}
