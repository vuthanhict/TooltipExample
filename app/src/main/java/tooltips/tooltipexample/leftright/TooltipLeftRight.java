package tooltips.tooltipexample.leftright;

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

public class TooltipLeftRight extends RelativeLayout {
    @BindView(R.id.tooltip_pointer_left)
    ImageView mLeftPointerView;
    @BindView(R.id.tooltip_pointer_right)
    ImageView mRightPointerView;

    private int mHeight;
    private int mWidth;
    private View mView;

    private int mRelativeMasterViewY;
    private int mRelativeMasterViewX;

    private float mMargin = getResources().getDimension(R.dimen.dimen_4_x);
    private float mArrowMargin = getResources().getDimension(R.dimen.arrow_margin);

    public TooltipLeftRight(Context context) {
        super(context);
    }

    public TooltipLeftRight(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TooltipLeftRight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TooltipLeftRight(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.popup_tooltip_left_right, this, true);
        ButterKnife.bind(this);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mWidth = TooltipLeftRight.this.getWidth();
                mHeight = TooltipLeftRight.this.getHeight();

                Log.d("PopupTooltip", mHeight + "");
                applyToolTipPositionLeftRight();
            }
        });
    }

    public void setToolTip(View view) {
        mView = view;

        init();
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

    private void applyToolTipPositionLeftRight() {
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

        boolean showLeft = true;
        int toolTipViewAboveX = mRelativeMasterViewX + masterViewWidth;
        if (masterViewScreenPosition[0] + masterViewWidth >= viewDisplayFrame.right - mWidth) {
            showLeft = false;
            toolTipViewAboveX = mRelativeMasterViewX - getWidth();
        }

        boolean showBelow = true;
        int toolTipViewY = Math.max(0, mRelativeMasterViewY);
        // Calculate show below if popup display outside screen
        if (toolTipViewY + mHeight + mMargin > viewDisplayFrame.bottom) {
            if ((toolTipViewAboveY - mMargin < 0) || ((toolTipViewBelowY + mHeight) + mMargin >
                    viewDisplayFrame.bottom)) {
                showBelow = relativeMasterViewCenterY < viewDisplayFrame.bottom / 2;
            }
        }

        if (!showBelow) {
            toolTipViewY = toolTipViewY + masterViewHeight - mHeight;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            ViewHelper.setAlpha(mLeftPointerView, showLeft ? 1 : 0);
            ViewHelper.setAlpha(mRightPointerView, showLeft ? 0 : 1);
        } else {
            mLeftPointerView.setVisibility(showLeft ? VISIBLE : GONE);
            mRightPointerView.setVisibility(showLeft ? GONE : VISIBLE);
        }

        int toolTipViewX = toolTipViewAboveX;

        // resize list view height if popup outside screen
        if (!showBelow) {
            // outside above
            if (toolTipViewY - mMargin < 0) {
//                mHeightListView = mHeightListView + (toolTipViewY - (int) mMargin);
//                updateHeightForListView(mHeightListView);
//                mHeight = mHeight + (toolTipViewY - (int) mMargin);
//                toolTipViewY = (int) mMargin;
            }
        } else {
            // outside below
            if ((toolTipViewY + mHeight) + mMargin > viewDisplayFrame.bottom) {
//                mHeightListView = mHeightListView - ((toolTipViewY + mHeight) + (int) mMargin -
//                        viewDisplayFrame.bottom);
//                mHeight = mHeight - ((toolTipViewY + mHeight) + (int) mMargin - viewDisplayFrame
//                        .bottom);
//                updateHeightForListView(mHeightListView);
            }
        }

        setPointerY(masterViewHeight / 2, !showBelow);

        ViewHelper.setY(this, toolTipViewY);
        ViewHelper.setX(this, toolTipViewX);
    }

    public void setPointerY(int pointerY, boolean isBottom) {
        int pointerHeight = Math.max(mLeftPointerView.getMeasuredHeight(), mRightPointerView
                .getMeasuredHeight());
        if (isBottom) {
            pointerY = pointerY + pointerHeight / 2;
            ViewHelper.setY(mLeftPointerView, mHeight - pointerY);
            ViewHelper.setY(mRightPointerView, mHeight - pointerY);
        } else {
            pointerY = pointerY - pointerHeight / 2;
            ViewHelper.setY(mLeftPointerView, pointerY);
            ViewHelper.setY(mRightPointerView, pointerY);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
