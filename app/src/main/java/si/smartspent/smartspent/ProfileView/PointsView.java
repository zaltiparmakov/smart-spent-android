package si.smartspent.smartspent.ProfileView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import si.smartspent.smartspent.R;

import static android.content.Context.WINDOW_SERVICE;

public class PointsView extends View {
    private int points;
    Bitmap bitmap, oldBitmap;

    public PointsView(Context context) {
        super(context);
        // points = Utils.getPoints(this);
        points = 9000;
    }

    public PointsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // points = Utils.getPoints(this);
        points = 9000;
    }

    public PointsView(Context context, AttributeSet attrs, int defaultStyle) {
        super(context, attrs, defaultStyle);
        // points = Utils.getPoints(this);
        points = 9000;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { // TODO Maybe add some leaderboard type ordeal?
        float eventX = event.getX();
        float eventY = event.getY();
        Toast.makeText(getContext(), "X: " + eventX + " Y: " + eventY + points, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int l = 0;
        int p = 500;
        int x = 0;

        // TODO Animate pulsating effect over the icons
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
                p += 500;
                canvas.drawBitmap(bitmap, x, 0, null);
                x += size + (size * 0.25f);
            }
            l++;
        }
    }
}
