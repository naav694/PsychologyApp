package mx.rokegcode.psychologyapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import mx.rokegcode.psychologyapp.R;
import mx.rokegcode.psychologyapp.model.data.UserRoom;
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
        presenter = new SplashPresenter(this,this,this);
        presenter.onStartApp();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    public void onSuccess(LoginResponse result ) {
        switch(result.getResponse()){
            case "login": //if we dont have user saved then open the login activity
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case "main": //If we jave an user saved then open the main activity
                Intent main = new Intent(this, MainActivity.class);
                main.putExtra("user",result.getUserRoom());
                main.putParcelableArrayListExtra("questions", (ArrayList<? extends Parcelable>) result.getQuestionList());
                startActivity(main);
                break;
        }
    }

    @Override
    public void onLoading() {
        Glide.with(this).load(R.drawable.logo).into(imageSplash);
        imageSplash.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String error) {
        SweetDialogs.sweetError(this,error);
    }
}