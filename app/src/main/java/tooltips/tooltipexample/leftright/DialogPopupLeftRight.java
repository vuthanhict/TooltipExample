package tooltips.tooltipexample.leftright;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import tooltips.tooltipexample.BaseDialogFragment;
import tooltips.tooltipexample.R;
import tooltips.tooltipexample.topbottom.PopupTooltipTopBottom;

/**
 * @author ThanhVV
 * @since 2/13/2017.
 */

public class DialogPopupLeftRight extends BaseDialogFragment {
    @BindView(R.id.popup)
    PopupTooltipLeftRight mPopup;
    private Context mContext;

    @Override
    public void init(View view) {

    }

    @Override
    public boolean isCancel() {
        return true;
    }

    @Override
    public int getLayoutID() {
        return R.layout.dialog_popup_left_right;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.PopupDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                        .LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mCallbacks != null) {
            mCallbacks.onViewCreated();
        }
    }

    public void setContent(View view) {
        mPopup.showToolTipForView(view);
    }

    private Callbacks mCallbacks;

    public interface Callbacks {
        void onViewCreated();
    }

    public void init(Context context, Callbacks callbacks) {
        this.mContext = context;
        this.mCallbacks = callbacks;
    }
}
