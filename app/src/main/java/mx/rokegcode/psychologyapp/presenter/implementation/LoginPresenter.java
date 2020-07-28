package mx.rokegcode.psychologyapp.presenter.implementation;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mx.rokegcode.psychologyapp.model.QuestionRoom;
import mx.rokegcode.psychologyapp.model.UserRoom;
import mx.rokegcode.psychologyapp.model.api.VolleyClient;
import mx.rokegcode.psychologyapp.model.database.AppDatabase;
import mx.rokegcode.psychologyapp.presenter.callback.LoginCallback;
import mx.rokegcode.psychologyapp.support.InternetConnection;

public class LoginPresenter extends BasePresenter{
    private LoginCallback callback;
    private UserRoom userRoom;
    private QuestionRoom questionRoom;

    public LoginPresenter(Context context,LoginCallback callback, Activity activity) {
        super(context,activity);
        this.callback = callback;
    }

    /*
    * This function is used for login to the aplication
    */
    public void Login(String user, String password, int remember){
        //Searching the user in the cloud and if couldn't find search in the local database
        disposable = Observable.fromCallable(()-> checkExistingUser(user,password,remember) )
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(result -> callback.onLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                    if(result.equals("main")){
                        callback.onSuccess(result,userRoom);
                    }else{
                        callback.onError(result);
                    }
                }, trhowable -> {
                    callback.onError(trhowable.getMessage());
                });
    }


    //TODO Crea una clase UserRepository y ahi metes la logica para hacer llamado a Web y para Room
    public String checkExistingUser(String user, String password, int remember){
        String respuesta = "";
        List<UserRoom> users;
        //TODO Para validar que haya credenciales recordadas usaremos SharedPreferences
        if(AppDatabase.getInstance(context).userDao().getUser(user.toUpperCase(),password.toUpperCase()) == 1){ //If the user exists in local database
            users = AppDatabase.getInstance(context).userDao().getUserData(user,password);
            userRoom = users.get(0); //TODO Utiliza una clase como response por ejemplo: LoginResponse("respuesta", Usuario)
            respuesta = "main";
        }else{ //If the user don't exist in local database
            if(InternetConnection.isConnected(context)){ //If the phone has internet connection
                try { //TODO No es necesario utilizar un try/catch ya que en el codigo del RxJava cacha la Excepciones en subscribe() en la variable throwable
                    respuesta = getUserFromWebService(user,password,remember);
                } catch (Exception e) {
                    respuesta = e.getMessage();
                }
            }else{ //If the phone doesn't have internet connection
                respuesta =  "No se encuentra conectado a internet.";
            }
        }
        return respuesta;
    }

    /*
    * This method retrieves the user if exist in the cloud
    */
    //TODO Crea una clase UserRepository y ahi metes la logica para hacer llamado a Web y para Room
    private String getUserFromWebService(String user, String password, int remember) throws Exception{
        //The url of the server
        String respuesta = "";
        try{ //TODO Igual aqui no es necesario el try/catch
            respuesta = "Error al obtener el usuario: ";
            //String url ="https://sistemascoatepec.000webhostapp.com/ws/ws.php?accion=GetUser&user="+user+"&pass="+password;
            String url = "http://192.168.100.37/psychosystem-1/ws/ws.php?accion=GetUser&user='"+user+"'&pass='"+password+"'";
            RequestFuture<JSONObject> request = VolleyClient.getInstance().createRequest(context,url, Request.Method.GET, new JSONObject());
            //TODO Manda el usuario y contraseña por post, creas el json y lo mandas :d
            JSONObject response = request.get();
            userRoom = new UserRoom();
            JSONArray jsonArrayUser = response.getJSONArray("USER"); //TODO Esto no, ya que lo que esperas es un JSONObject
            //TODO JSONObject jsonUser = response.getJSONObject("USER");
            JSONObject jsonObjectUser = jsonArrayUser.getJSONObject(0); //TODO Esto no
            userRoom.setPk_user(jsonObjectUser.getInt("PK_USUARIO")); //TODO jsonUser
            userRoom.setLogin(jsonObjectUser.getString("USUARIO")); //TODO jsonUser
            userRoom.setPassword(jsonObjectUser.getString("CONTRASENIA")); //TODO jsonUser
            userRoom.setFrequency(jsonObjectUser.getInt("FRECUENCIA")); //TODO jsonUser
            userRoom.setSaveUser(remember);
            //Save the user in local database
            respuesta = "Error al guardar el usuario en local: ";
            AppDatabase.getInstance(context).userDao().insertUser(userRoom);

            //Retrieve the questions
            JSONArray jsonArraySurvey = response.getJSONArray("SURVEY");
            JSONObject jsonObjectSurvey;
            for(int x = 0; x <= jsonArraySurvey.length()-1; x++){
                jsonObjectSurvey = jsonArrayUser.getJSONObject(x);
                questionRoom = new QuestionRoom();
                questionRoom.setPk_question(jsonObjectSurvey.getInt("PK_PREGUNTA"));
                questionRoom.setQuestion(jsonObjectSurvey.getString("TEXTO"));
                //AppDatabase.getInstance(context).questionDao().

            }

        }catch (Exception e){
            respuesta += e.getMessage();
        }
        return respuesta;
    }

}
