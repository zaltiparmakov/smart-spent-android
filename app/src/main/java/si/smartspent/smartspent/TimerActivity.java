package si.smartspent.smartspent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.zip.Inflater;

public class TimerActivity extends DrawerActivity {
    private ClockView clock;

    private long updatedTime;
    private LongOperation t;

    public long getUpdatedTime() {
        return updatedTime;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_timer, frameLayout);
        clock = (ClockView) findViewById(R.id.clock);
        clock.setSpeed(500);
        setUpdatedTime(System.currentTimeMillis());

    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
        clock.setUnitsOf60(updatedTime);
    }

    public void updateKlik(View v) {//on klik na gumbu
        setUpdatedTime(System.currentTimeMillis());
    }

    public void startStopKlik(View v) {//on klik na gumbu
        if (t == null) {
            t = new LongOperation();
            t.execute();
        } else {
            t.quit();
            t = null;
        }
    }
    private class LongOperation extends AsyncTask<Void, Void, Void> {
        private boolean stopClock = false;
        public void quit() {
            stopClock = true;
        }
        @Override
        protected Void doInBackground(Void... params) {
            while (!stopClock) {
                publishProgress(); //update Clock
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            setUpdatedTime(System.currentTimeMillis());
        }
    }
}
