package tooltips.tooltipexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tooltips.tooltipexample.leftright.DialogPopupLeftRight;
import tooltips.tooltipexample.topbottom.DialogPopupTopBottom;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_hello)
    View mHelloBt;

    @BindView(R.id.btn_hello_2)
    View mHelloBt2;

    @BindView(R.id.btn_welcome)
    View mWelcome;

    @BindView(R.id.btn_welcome_2)
    View mWelcome2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_hello)
    public void helloWorld() {
        final DialogPopupTopBottom dialogPopup = new DialogPopupTopBottom();
        dialogPopup.init(this, new DialogPopupTopBottom.Callbacks() {
            @Override
            public void onViewCreated() {
                dialogPopup.setContent(mHelloBt);
            }
        });
        dialogPopup.show(getSupportFragmentManager(), null);
    }

    @OnClick(R.id.btn_hello_2)
    public void helloWorld2() {
        final DialogPopupTopBottom dialogPopup = new DialogPopupTopBottom();
        dialogPopup.init(this, new DialogPopupTopBottom.Callbacks() {
            @Override
            public void onViewCreated() {
                dialogPopup.setContent(mHelloBt2);
            }
        });
        dialogPopup.show(getSupportFragmentManager(), null);
    }

    @OnClick(R.id.btn_welcome_2)
    public void welcome2() {
        final DialogPopupLeftRight dialogPopup = new DialogPopupLeftRight();
        dialogPopup.init(this, new DialogPopupLeftRight.Callbacks() {
            @Override
            public void onViewCreated() {
                dialogPopup.setContent(mWelcome2);
            }
        });
        dialogPopup.show(getSupportFragmentManager(), null);
    }

    @OnClick(R.id.btn_welcome)
    public void welcome() {
        final DialogPopupLeftRight dialogPopup = new DialogPopupLeftRight();
        dialogPopup.init(this, new DialogPopupLeftRight.Callbacks() {
            @Override
            public void onViewCreated() {
                dialogPopup.setContent(mWelcome);
            }
        });
        dialogPopup.show(getSupportFragmentManager(), null);
    }
}
