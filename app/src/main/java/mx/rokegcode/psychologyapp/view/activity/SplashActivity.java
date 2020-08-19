package mx.rokegcode.psychologyapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import butterknife.BindView;
import mx.rokegcode.psychologyapp.R;
import mx.rokegcode.psychologyapp.model.data.Question;
import mx.rokegcode.psychologyapp.presenter.callback.SplashCallback;
import mx.rokegcode.psychologyapp.presenter.implementation.SplashPresenter;
import mx.rokegcode.psychologyapp.view.dialog.SweetDialogs;

public class SplashActivity extends AppCompatActivity implements SplashCallback {

    @BindView(R.id.imageSplash)
    ImageView imageSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SplashPresenter presenter = new SplashPresenter(this, this, this);
        presenter.onStartApp();
    }

    @Override
    public void onSuccess(String response, ArrayList<Question> questionArrayList) {
        switch (response) {
            case "login": //if we dont have user saved then open the login activity
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case "main": //If we jave an user saved then open the main activity
                Intent main = new Intent(this, MainActivity.class);
                main.putParcelableArrayListExtra("survey", questionArrayList);
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