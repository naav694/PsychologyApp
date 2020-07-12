package mx.rokegcode.psychologyapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mx.rokegcode.psychologyapp.R;
import mx.rokegcode.psychologyapp.model.UserRoom;
import mx.rokegcode.psychologyapp.presenter.callback.LoginCallback;
import mx.rokegcode.psychologyapp.presenter.implementation.LoginPresenter;
import mx.rokegcode.psychologyapp.view.dialog.SweetDialogs;

public class LoginActivity extends BaseActivity implements LoginCallback {

    @BindView(R.id.txtUser) TextInputEditText txtUser;
    @BindView(R.id.txtPassword) TextInputEditText txtPassword;
    @BindView(R.id.checkUser) CheckBox checkUser;

    private SweetAlertDialog sweetProgress;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick({R.id.btnLogin})
    public void onButtonLoginPressed(){
        if(txtUser.getText() != null && txtPassword.getText() != null){ //If the user put something in the fields
            if(!(txtUser.getText().toString().trim().isEmpty() || txtPassword.getText().toString().trim().isEmpty())){ //if both fields are filled
                int check = checkUser.isChecked() ? 1: 0;

                presenter.Login(txtUser.getText().toString(),txtPassword.getText().toString(),check);

                //Start the survey activity
            }else{ //if the user didn't put an user or password
                Toast.makeText(this, "nelson no", Toast.LENGTH_SHORT).show();
            }
        }

    }



    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    public void onLoading() {

    }

    /*
     * this method is executed if the login process had an error
     */
    @Override
    public void onError(String error) {
        sweetProgress.dismiss();
        //Show the error message to the user
        SweetDialogs.sweetError(this,error).show();
    }

    /*
    * This method is executed if the login process is complete without errors
    */
    @Override
    public void onSuccess(UserRoom user) {
        sweetProgress.dismiss();
        //Declaring the new intent
        Intent main = new Intent(this,MainActivity.class);
        //Sending retrieved user to the new activity
        //intent.putExtra("user", user);
        //Start the new Activity
        startActivity(main);
    }
}