package mx.rokegcode.psychologyapp.presenter.implementation;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mx.rokegcode.psychologyapp.model.UserRoom;
import mx.rokegcode.psychologyapp.model.api.VolleyClient;
import mx.rokegcode.psychologyapp.model.database.AppDatabase;
import mx.rokegcode.psychologyapp.presenter.callback.LoginCallback;
import mx.rokegcode.psychologyapp.support.InternetConnection;

public class LoginPresenter extends BasePresenter{
    private LoginCallback callback;
    private UserRoom userRoom;

    public LoginPresenter(Context context,LoginCallback callback, Activity activity) {
        super(context,activity);
        this.callback = callback;
    }

    /*
    * This function is used for login to the aplication
    */
    public void Login(String user, String password, int remember){
        //Searching the user in the cloud and if couldn't find search in the local database
        disposable = Observable.fromCallable(()-> InternetConnection.isConnected(context) ? getUserFromWebService(user,password,remember) : getUserFromRoom(user,password,remember) )
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(result -> callback.onLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                    if(result.isEmpty()){
                        callback.onSuccess(userRoom);
                    }else{
                        callback.onError(result);
                    }
                },trhowable ->{
                    Toast.makeText(context, "hola", Toast.LENGTH_SHORT).show();
                });
    }

    /*
    * This method retrieves the user if exist in the cloud
    */
    private String getUserFromWebService(String user, String password, int remember) throws Exception{
        //The url of the server
        String url ="https://sistemascoatepec.000webhostapp.com/";
        RequestFuture<JSONObject> request = VolleyClient.getInstance() .createRequest(context,url, Request.Method.GET, new JSONObject());
        JSONObject response = request.get();
        userRoom = new UserRoom();
        JSONArray jsonarrayUser = response.getJSONArray("USER");
        JSONObject jsonObjectUser = jsonarrayUser.getJSONObject(0);
        userRoom.setPk_user(jsonObjectUser.getInt("PK_USUARIO"));
        userRoom.setLogin(jsonObjectUser.getString("USUARIO"));
        userRoom.setPassword(jsonObjectUser.getString("CONTRASENIA"));
        userRoom.setSaveUser(remember);

        AppDatabase.getInstance(context).userDao().insertUser(userRoom);
        String userLogged = userRoom.getLogin();
        return "";
    }

    /*
    * This method retrieves the user if exist in local data
    */
    private String getUserFromRoom(String user, String password, int remember){
        List<UserRoom> users = AppDatabase.getInstance(context).userDao().getUsers();
        if(!users.isEmpty()){
            if(AppDatabase.getInstance(context).userDao().getUser(user.toUpperCase(),password.toUpperCase()) == 1){
                AppDatabase.getInstance(context).userDao().updateUser(remember,userRoom.getPk_user());
                return "";
            }else{
                return "Usuario o contraseña incorrrectos";
            }
        }else{
            return "La primera vez debe iniciar sesión con conexión a internet";
        }
    }
}
