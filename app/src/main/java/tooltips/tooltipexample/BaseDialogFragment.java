package tooltips.tooltipexample;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ThinhNH on 11/14/2015.
 */
public abstract class BaseDialogFragment extends DialogFragment {

    @Nullable
    @BindView(R.id.rootView)
    View mRootView;

    private FragmentManager mFragmentManager;

    public abstract void init(View view);

    public abstract int getLayoutID();

    public interface DatePikerListener {
        void onDateSet(Calendar calendar);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        mFragmentManager = manager;
        FragmentTransaction ft = manager.beginTransaction();
//		ft.add(this, tag);
        String tagName = this.getClass().getSimpleName();
        Fragment fragment = manager.findFragmentByTag(tagName);
        if (fragment != null && fragment.getClass().getSimpleName().equals(this.getClass()
                .getSimpleName()) && fragment.isVisible()) {
            return;
        }
        ft.add(this, tagName);
//		ft.addToBackStack(tagName);
        ft.commitAllowingStateLoss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutID(), container, false);
        ButterKnife.bind(this, rootView);
        init(rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_HIDDEN);

        setCancelable(isCancel());

        if (isFullScreen()) {
            mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isCancel()) {
                        dismiss();
                    }
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isFullScreen()) {
            Dialog dialog = getDialog();
            if (dialog != null) {
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                            .LayoutParams.MATCH_PARENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
        }
    }

    protected boolean isFullScreen() {
        return false;
    }

    @Override
    public void dismiss() {
        hideKeyboard();
        dismissAllowingStateLoss();
        if (mFragmentManager != null) {
            String tagName = this.getClass().getSimpleName();
            mFragmentManager.popBackStack(tagName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public boolean isCancel() {
        return true;
    }

    protected void showDatePickerDialog(final View view, final DatePikerListener listener) {
        final DatePickerDialog.OnDateSetListener dateSet = new DatePickerDialog.OnDateSetListener
                () {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                if (listener != null) {
                    listener.onDateSet(calendar);
                }
            }

        };

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), dateSet, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                pickerDialog.show();
            }
        });
    }


    /**
     * Hidden Keyboard
     */
    public void showKeyboard() {
        if (getActivity() == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context
                .INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Hidden Keyboard
     */
    public void hideKeyboard() {
        if (getActivity() == null) {
            return;
        }
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService
                (Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
