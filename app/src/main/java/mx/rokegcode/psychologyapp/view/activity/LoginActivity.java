package mx.rokegcode.psychologyapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import java.security.Permissions;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mx.rokegcode.psychologyapp.R;
import mx.rokegcode.psychologyapp.model.UserRoom;
import mx.rokegcode.psychologyapp.presenter.callback.LoginCallback;
import mx.rokegcode.psychologyapp.presenter.implementation.BasePresenter;
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
        presenter = new LoginPresenter(this,this,this);
    }

    @OnClick({R.id.btnLogin})
    public void onBtnLoginPressed(){
        if(txtUser.getText() != null && txtPassword.getText() != null){ //If the fields arent empty
            if(!(txtUser.getText().toString().trim().isEmpty())){ //if both fields are filled
                int check = checkUser.isChecked() ? 1: 0; //Send the value of the checked (checked 1 | unchecked 0)
                //Execute the login
                presenter.Login(txtUser.getText().toString(),txtPassword.getText().toString(),check);
            }else{ //if the user didn't put an user or password
                SweetDialogs.sweetWarning(this,"Por favor llene los campos de texto"); //TODO Aqui falta el show()
            }
        }
    }



    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    public void onLoading() {
        sweetProgress = SweetDialogs.sweetLoading(this,"Iniciando sesion");
        sweetProgress.show();
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
    public void onSuccess(String result, UserRoom user) {
        switch(result){
            case "main":
                //Declaring the new intent
                Intent main = new Intent(this,MainActivity.class);
                //TODO Para lo del usuario, vamos a hacer una clase como para sesion con ayuda de SharedPreferences y GSON (libreria de terceros)
                main.putExtra("user", user); //Sending retrieved user to the new activity
                startActivity(main); //Start the new Activity
                break;
        }
    }
}