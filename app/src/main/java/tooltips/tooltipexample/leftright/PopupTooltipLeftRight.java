package tooltips.tooltipexample.leftright;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import tooltips.tooltipexample.topbottom.TooltipTopBottom;

/**
 * @author ThanhVV
 * @since 2/13/2017.
 */

public class PopupTooltipLeftRight extends RelativeLayout {
    public PopupTooltipLeftRight(Context context) {
        super(context);
    }

    public PopupTooltipLeftRight(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PopupTooltipLeftRight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PopupTooltipLeftRight(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * @param view the View to position the ToolTipView relative to.
     * @return the ToolTipView that was created.
     */
    public TooltipLeftRight showToolTipForView(final View view) {
        final TooltipLeftRight toolTipView = new TooltipLeftRight(getContext());
        toolTipView.setToolTip(view);
        addView(toolTipView);
        return toolTipView;
    }
}
