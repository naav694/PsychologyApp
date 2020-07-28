package mx.rokegcode.psychologyapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import mx.rokegcode.psychologyapp.R;
import mx.rokegcode.psychologyapp.model.UserRoom;
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
    public void onSuccess(String result, UserRoom user) {
        switch(result){
            case "login": //if we dont have user saved then open the login activity
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case "main": //If we jave an user saved then open the main activity
                Intent main = new Intent(this, MainActivity.class);
                main.putExtra("user",user);
                startActivity(main);
                break;
        }
    }

    @Override
    public void onLoading() {
        Glide.with(this).load(R.drawable.warning_sigh).into(imageSplash);
        imageSplash.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String result) {
        SweetDialogs.sweetError(this,result);
    }
}