package mx.rokegcode.psychologyapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import mx.rokegcode.psychologyapp.R;
import mx.rokegcode.psychologyapp.model.data.Question;
import mx.rokegcode.psychologyapp.model.response.LoginResponse;
import mx.rokegcode.psychologyapp.presenter.callback.SplashCallback;
import mx.rokegcode.psychologyapp.presenter.implementation.SplashPresenter;
import mx.rokegcode.psychologyapp.view.dialog.SweetDialogs;

public class SplashActivity extends BaseActivity implements SplashCallback {

    @BindView(R.id.imageSplash)
    ImageView imageSplash;

    private SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        presenter = new SplashPresenter(this, this, this);
        presenter.onStartApp();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    public void onSuccess(LoginResponse response) {
        switch (response.getResponse()) {
            case "login": //if we dont have user saved then open the login activity
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case "main": //If we jave an user saved then open the main activity
                Intent main = new Intent(this, MainActivity.class);
                main.putParcelableArrayListExtra("survey", response.getQuestionList());
                startActivity(main);
                finish();
                break;
        }
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onError(String error) {
        SweetDialogs.sweetError(this, error);
    }
}