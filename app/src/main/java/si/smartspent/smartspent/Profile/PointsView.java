package si.smartspent.smartspent.Profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import si.smartspent.smartspent.R;

import static android.content.Context.WINDOW_SERVICE;
import static org.greenrobot.eventbus.EventBus.TAG;
import static si.smartspent.smartspent.Utils.API_URL;

public class PointsView extends View {
    int points;
    Bitmap bitmap, oldBitmap;
    org.json.simple.JSONObject jsonObjectsimple;

    public PointsView(Context context) {
        super(context);
        try {
            JSONObject jsonObject = new JSONObject(jsonObjectsimple.toString());
            points = jsonObject.getInt("points");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PointsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
        JSONObject jsonObject = new JSONObject(jsonObjectsimple.toString());
        points = jsonObject.getInt("points");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PointsView(Context context, AttributeSet attrs, int defaultStyle) {
        super(context, attrs, defaultStyle);
        try {
        JSONObject jsonObject = new JSONObject(jsonObjectsimple.toString());
        points = jsonObject.getInt("points");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class UserPointsDataTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            final OkHttpClient client;

            try {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                client = builder.build();

                Request request = new Request.Builder()
                        .header("Content-Type", "application/json")
                        .url(API_URL + "/user")
                        .get()
                        .build();

                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String jsonString = response.body().string();
                    // parse JSON array string with transactions to a JSONArray
                    jsonObjectsimple = (org.json.simple.JSONObject) (new JSONParser()).parse(jsonString);
                    return true;
                }
            } catch (IOException e) {
                Log.e(TAG, "Could not execute HTTP request.");
            } catch (org.json.simple.parser.ParseException e) {
                Log.e(TAG, "Error while parsing JSON string.");
            }
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        Toast.makeText(getContext(), "Points: " + points, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int l = 0;
        int p = 10;
        int x = 0;

        new PointsView.UserPointsDataTask().execute((Void) null);
        try {
            JSONObject jsonObject = new JSONObject(jsonObjectsimple.toString());
            points = jsonObject.getInt("points");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Display display = ((WindowManager) getContext().getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();
        float ratio;
        if(screenHeight > screenWidth) {
            ratio = screenHeight / (float) screenWidth;
        } else {
            ratio = screenWidth / (float) screenHeight;
        }
        //Log.wtf("VARIABLE", "Height: " + screenHeight + " Width: " + screenWidth);
        int size = Math.round(ratio * 96);

        oldBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_profile_points);
        //Log.wtf("VARIABLE", "size: " + size + " ratio: " + ratio);
        bitmap = Bitmap.createScaledBitmap(oldBitmap, size, size, true);
        oldBitmap.recycle();
        while(l < 5) {
            if(points >= p) {
                p += 10;
                canvas.drawBitmap(bitmap, x, 0, null);
                x += size + (size * 0.25f);
            }
            l++;
        }
    }
}
