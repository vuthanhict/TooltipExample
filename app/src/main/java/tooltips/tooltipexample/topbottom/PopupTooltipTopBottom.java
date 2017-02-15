package tooltips.tooltipexample.topbottom;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @author ThanhVV
 * @since 2/13/2017.
 */

public class PopupTooltipTopBottom extends RelativeLayout {
    public PopupTooltipTopBottom(Context context) {
        super(context);
    }

    public PopupTooltipTopBottom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PopupTooltipTopBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PopupTooltipTopBottom(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * @param view the View to position the ToolTipView relative to.
     * @return the ToolTipView that was created.
     */
    public TooltipTopBottom showToolTipForView(final View view) {
        final TooltipTopBottom toolTipView = new TooltipTopBottom(getContext());
        toolTipView.setToolTip(view);
        addView(toolTipView);
        return toolTipView;
    }
}
