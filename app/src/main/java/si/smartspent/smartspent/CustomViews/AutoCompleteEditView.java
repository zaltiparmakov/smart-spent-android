package si.smartspent.smartspent.CustomViews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;

import si.smartspent.smartspent.R;

public class AutoCompleteEditView extends AppCompatAutoCompleteTextView {
//    private Drawable mButtonImage;

    public AutoCompleteEditView(Context context) {
        super(context);
        setupButton();
    }

    public AutoCompleteEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupButton();
    }

    public AutoCompleteEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupButton();
    }

    private void setupButton() {
//        mButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back, null);

        setBackgroundResource(R.drawable.bg_edittext);
        setMaxLines(1);
    }
}
