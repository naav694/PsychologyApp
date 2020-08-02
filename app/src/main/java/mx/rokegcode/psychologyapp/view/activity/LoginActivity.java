package mx.rokegcode.psychologyapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.CheckBox;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mx.rokegcode.psychologyapp.R;
import mx.rokegcode.psychologyapp.model.data.UserRoom;
import mx.rokegcode.psychologyapp.model.response.LoginResponse;
import mx.rokegcode.psychologyapp.presenter.callback.LoginCallback;
import mx.rokegcode.psychologyapp.presenter.implementation.LoginPresenter;
import mx.rokegcode.psychologyapp.view.dialog.SweetDialogs;

public class LoginActivity extends BaseActivity implements LoginCallback {

    @BindView(R.id.txtUser)
    TextInputEditText txtUser;
    @BindView(R.id.txtPassword)
    TextInputEditText txtPassword;
    @BindView(R.id.checkUser)
    CheckBox checkUser;

    private SweetAlertDialog sweetProgress;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenter(this, this, this);
    }

    @OnClick({R.id.btnLogin})
    public void onBtnLoginPressed() {
        if (txtUser.getText() != null && txtPassword.getText() != null) { //If the fields arent empty
            if (!(txtUser.getText().toString().trim().isEmpty())) { //if both fields are filled
                boolean check = checkUser.isChecked(); //Send the value of the checked (checked 1 | unchecked 0)
                //Execute the login
                presenter.Login(txtUser.getText().toString(), txtPassword.getText().toString(), check);
            } else { //if the user didn't put an user or password
                SweetDialogs.sweetWarning(this, "Por favor llene los campos de texto").show();
            }
        }
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    public void onLoading() {
        sweetProgress = SweetDialogs.sweetLoading(this, "Iniciando sesion");
        sweetProgress.show();
    }

    /*
     * this method is executed if the login process had an error
     */
    @Override
    public void onError(String error) {
        sweetProgress.dismiss();
        //Show the error message to the user
        SweetDialogs.sweetError(this, error).show();
    }

    /*
     * This method is executed if the login process is complete without errors
     */
    @Override
    public void onSuccess(LoginResponse result) {
        //Declaring the new intent
        Intent main = new Intent(this, MainActivity.class);
        main.putExtra("user", result.getUserRoom());
        main.putExtra("user", (Parcelable) result.getQuestionList());
        startActivity(main); //Start the new Activity
    }
}