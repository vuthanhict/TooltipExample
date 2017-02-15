package tooltips.tooltipexample.topbottom;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nineoldandroids.view.ViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import tooltips.tooltipexample.R;

/**
 * @author ThanhVV
 * @since 2/13/2017.
 */

public class TooltipTopBottom extends RelativeLayout {
    @BindView(R.id.tooltip_pointer_top)
    ImageView mTopPointerView;
    @BindView(R.id.tooltip_pointer_bottom)
    ImageView mBottomPointerView;

    private int mHeight;
    private int mWidth;
    private View mView;

    private int mRelativeMasterViewY;
    private int mRelativeMasterViewX;

    private float mMargin = getResources().getDimension(R.dimen.dimen_4_x);
    private float mArrowMargin = getResources().getDimension(R.dimen.arrow_margin);

    public TooltipTopBottom(Context context) {
        super(context);
    }

    public TooltipTopBottom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TooltipTopBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TooltipTopBottom(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.popup_tooltip_top_bottom, this, true);
        ButterKnife.bind(this);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mWidth = TooltipTopBottom.this.getWidth();
                mHeight = TooltipTopBottom.this.getHeight();

                Log.d("PopupTooltip", mHeight + "");
                applyToolTipPositionTopBottom();
            }
        });
    }

    public void setToolTip(View view) {
        mView = view;

        init();
    }

    private void applyToolTipPositionTopBottom() {
        final int[] masterViewScreenPosition = new int[2];
        mView.getLocationOnScreen(masterViewScreenPosition);

        final Rect viewDisplayFrame = new Rect();
        mView.getWindowVisibleDisplayFrame(viewDisplayFrame);

        final int[] parentViewScreenPosition = new int[2];
        ((View) getParent()).getLocationOnScreen(parentViewScreenPosition);

        final int masterViewWidth = mView.getWidth();
        final int masterViewHeight = mView.getHeight();

        mRelativeMasterViewX = masterViewScreenPosition[0] - parentViewScreenPosition[0];
        mRelativeMasterViewY = masterViewScreenPosition[1] - parentViewScreenPosition[1];
        final int relativeMasterViewCenterX = mRelativeMasterViewX + masterViewWidth / 2;
        final int relativeMasterViewCenterY = mRelativeMasterViewY + masterViewHeight / 2;

        int toolTipViewAboveY = mRelativeMasterViewY + masterViewHeight / 2 - mHeight;
        int toolTipViewBelowY = Math.max(0, mRelativeMasterViewY + masterViewHeight);

        int toolTipViewX = Math.max(0, relativeMasterViewCenterX - mWidth / 2);
        if (toolTipViewX + mWidth > viewDisplayFrame.right - mMargin) {
            toolTipViewX = viewDisplayFrame.right - mWidth - (int) mMargin;
        }

        setX(toolTipViewX);
        setPointerCenterX(relativeMasterViewCenterX);

        boolean showBelow = (toolTipViewBelowY + mHeight) + mMargin < viewDisplayFrame.bottom;
        // Calculate show below if popup display outside screen
        if ((toolTipViewAboveY - mMargin < 0) || ((toolTipViewBelowY + mHeight) +
                mMargin > viewDisplayFrame.bottom)) {
//            showBelow = Math.abs(toolTipViewAboveY - mMarginRight) > Math.abs((toolTipViewBelowY
//                    + mHeight) + mMarginRight - viewDisplayFrame.bottom);
            showBelow = relativeMasterViewCenterY < viewDisplayFrame.bottom / 2;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            ViewHelper.setAlpha(mTopPointerView, showBelow ? 1 : 0);
            ViewHelper.setAlpha(mBottomPointerView, showBelow ? 0 : 1);
        } else {
            mTopPointerView.setVisibility(showBelow ? VISIBLE : GONE);
            mBottomPointerView.setVisibility(showBelow ? GONE : VISIBLE);
        }

        // resize list view height if popup outside screen
        int toolTipViewY;
        if (!showBelow) {
            toolTipViewY = toolTipViewAboveY;
            // outside above
            if (toolTipViewAboveY - mMargin < 0) {
                toolTipViewY = toolTipViewAboveY - (toolTipViewAboveY - (int) mMargin);
//                mHeightListView = mHeightListView + (toolTipViewAboveY - (int) mMarginRight);
//                updateHeightForListView(mHeightListView);
            }
            toolTipViewY = toolTipViewAboveY - mBottomPointerView.getMeasuredHeight();
        } else {
            toolTipViewY = toolTipViewBelowY;
            // outside below
            if ((toolTipViewBelowY + mHeight - mBottomPointerView.getMeasuredHeight()) +
                    mMargin > viewDisplayFrame.bottom) {
//                mHeightListView = mHeightListView - ((toolTipViewBelowY + mHeight -
//                        mBottomPointerView.getMeasuredHeight()) + (int) mMarginRight -
//                        viewDisplayFrame.bottom);
//                updateHeightForListView(mHeightListView);
            }
        }

        ViewHelper.setY(this, toolTipViewY);
        ViewHelper.setX(this, toolTipViewX);
    }

    public void setPointerCenterX(final int pointerCenterX) {
        int pointerWidth = Math.max(mTopPointerView.getMeasuredWidth(), mBottomPointerView
                .getMeasuredWidth());

        ViewHelper.setX(mTopPointerView, pointerCenterX - pointerWidth / 2 - (int) getX());
        ViewHelper.setX(mBottomPointerView, pointerCenterX - pointerWidth / 2 - (int) getX());
    }

    /**
     * Convenience method for getting X.
     */
    @SuppressLint("NewApi")
    @Override
    public float getX() {
        float result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            result = super.getX();
        } else {
            result = ViewHelper.getX(this);
        }
        return result;
    }

    /**
     * Convenience method for setting X.
     */
    @SuppressLint("NewApi")
    @Override
    public void setX(final float x) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            super.setX(x);
        } else {
            ViewHelper.setX(this, x);
        }
    }

    /**
     * Convenience method for getting Y.
     */
    @SuppressLint("NewApi")
    @Override
    public float getY() {
        float result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            result = super.getY();
        } else {
            result = ViewHelper.getY(this);
        }
        return result;
    }

    /**
     * Convenience method for setting Y.
     */
    @SuppressLint("NewApi")
    @Override
    public void setY(final float y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            super.setY(y);
        } else {
            ViewHelper.setY(this, y);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
