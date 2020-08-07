package mx.rokegcode.psychologyapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;

import androidx.appcompat.app.ActionBar;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mx.rokegcode.psychologyapp.R;
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
    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Inicio de sesión");
        }
        mPresenter = new LoginPresenter(this, this, this);
    }

    @OnClick({R.id.btnLogin})
    public void onBtnLoginPressed() {
        if (txtUser.getText() != null && txtPassword.getText() != null) {
            if (!(txtUser.getText().toString().trim().isEmpty())) {
                boolean check = checkUser.isChecked();
                mPresenter.onLogin(txtUser.getText().toString(), txtPassword.getText().toString(), check);
            } else {
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

    /**
     * this method is executed if the login process had an error
     */
    @Override
    public void onError(String error) {
        sweetProgress.dismiss();
        SweetDialogs.sweetError(this, error).show();
    }

    /**
     * This method is executed if the login process is complete without errors
     */
    @Override
    public void onSuccess(LoginResponse result) {
        sweetProgress.dismiss();
        Intent i = new Intent(this, MainActivity.class);
        i.putParcelableArrayListExtra("survey", result.getQuestionList());
        startActivity(i);
    }
}