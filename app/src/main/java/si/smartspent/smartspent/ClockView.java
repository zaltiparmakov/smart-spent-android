package si.smartspent.smartspent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ClockView extends View {
    private int units_of_60 = 15;

    private int indicator;
    private double speed;
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private Paint circlePaint;
    private Paint arcPaint;
    private RectF rec=new RectF();;
    private RectF rec2=new RectF();;

    public ClockView(Context context) {
        super(context);
        initClockView();
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initClockView();
    }

    public ClockView(Context context, AttributeSet ats, int defaultStyle) {
        super(context, ats, defaultStyle);
        initClockView();
    }

    public int getUnitsOf60() {
        return units_of_60;
    }

    public boolean setUnitsOf60(long updatedTime) {
        int unit = (int) ((updatedTime / speed) % 60);
        if (unit > 60)
            unit = 60;
        if (unit == this.units_of_60)
            return false;
        this.units_of_60 = unit;
        invalidate();
        return true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        System.out.println(eventX+" "+eventY);
        int mMeasuredWidth = getMeasuredWidth();
        int mMeasuredHeight = getMeasuredHeight();
        int d = Math.min(mMeasuredWidth, mMeasuredHeight);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //speed+=100;
                indicator = (int) ((eventY/d)*100);
                return true;
            case MotionEvent.ACTION_MOVE:
                // path.lineTo(eventX, eventY);
                indicator = (int) ((eventY/d)*100);
                break;
            case MotionEvent.ACTION_UP:
                indicator = (int) ((eventY/d)*100);
                break;
            default:
                return false;
        }
        if (indicator>100) indicator=100;
        if (indicator<1) indicator=1;

        // Schedules a repaint.
        invalidate();
        return true;
    }
    protected void initClockView() {
        speed = 1000;
        setFocusable(false);
        indicator = 50;
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.GREEN);
        circlePaint.setStrokeWidth(10);
        circlePaint.setStyle(Paint.Style.STROKE);
        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setColor(Color.BLACK);
        arcPaint.setStrokeWidth(1);
        arcPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        arcPaint.setTextSize(48f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int mMeasuredWidth = getMeasuredWidth();
        int mMeasuredHeight = getMeasuredHeight();
        int d = Math.min(mMeasuredWidth, mMeasuredHeight);
        int px = d / 2;
        int py = d / 2;
        int radius = px - 4;
        rec.set(px - radius, py - radius, px + radius, py
                + radius);
        if (units_of_60 < 60) // if not empty circle
            canvas.drawArc(rec, -90 + (units_of_60 * 6),
                    360 - (units_of_60 * 6), true, arcPaint);
        canvas.drawCircle(px, py, radius, circlePaint); //empty circle!
        canvas.drawCircle(px+radius+20, d*indicator/100, 5, arcPaint); //indicator circle!
        rec2.set(px + radius+10, py - radius, px + radius+30, py
                + radius);
        canvas.drawRect(rec2, circlePaint);

        canvas.drawText(indicator+"", px + radius+12+12+8,py, arcPaint);
    }
}
