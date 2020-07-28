package mx.rokegcode.psychologyapp.model.repository;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import mx.rokegcode.psychologyapp.model.api.VolleyClient;
import mx.rokegcode.psychologyapp.model.data.QuestionRoom;
import mx.rokegcode.psychologyapp.model.data.UserRoom;
import mx.rokegcode.psychologyapp.model.database.AppDatabase;
import mx.rokegcode.psychologyapp.support.InternetConnection;

public class UserRepository {
    private UserRoom userRoom;
    private QuestionRoom questionRoom;

    public String checkExistingUser(Context context,String user, String password, int remember) throws Exception{
        String respuesta = "";
        List<UserRoom> users;
        //TODO Para validar que haya credenciales recordadas usaremos SharedPreferences
        if(AppDatabase.getInstance(context).userDao().getUser(user.toUpperCase(),password.toUpperCase()) == 1){ //If the user exists in local database
            users = AppDatabase.getInstance(context).userDao().getUserData(user,password);
            userRoom = users.get(0); //TODO Utiliza una clase como response por ejemplo: LoginResponse("respuesta", Usuario)
            respuesta = "main";
        }else{ //If the user don't exist in local database
            if(InternetConnection.isConnected(context)){ //If the phone has internet connection
                respuesta = getUserFromWebService(context,user,password,remember);
            }else{ //If the phone doesn't have internet connection
                respuesta =  "No se encuentra conectado a internet.";
            }
        }
        return respuesta;
    }

    /*
     * This method retrieves the user if exist in the cloud
     */
    private String getUserFromWebService(Context context,String user, String password, int remember) throws Exception{
        //The url of the server
        String respuesta = "";
        respuesta = "Error al obtener el usuario: ";
        //String url ="https://sistemascoatepec.000webhostapp.com/ws/ws.php?accion=GetUser&user="+user+"&pass="+password;
        String url = "http://192.168.100.37/psychosystem-1/ws/ws.php?accion=GetUser&user='"+user+"'&pass='"+password+"'";
        RequestFuture<JSONObject> request = VolleyClient.getInstance().createRequest(context,url, Request.Method.GET, new JSONObject());
        //TODO Manda el usuario y contraseña por post, creas el json y lo mandas :d
        JSONObject response = request.get();
        userRoom = new UserRoom();
        JSONObject jsonUser = response.getJSONObject("USER");
        userRoom.setPk_user(jsonUser.getInt("PK_USUARIO"));
        userRoom.setLogin(jsonUser.getString("USUARIO"));
        userRoom.setPassword(jsonUser.getString("CONTRASENIA"));
        userRoom.setFrequency(jsonUser.getInt("FRECUENCIA"));
        userRoom.setSaveUser(remember);

        //Save the user in local database
        respuesta = "Error al guardar el usuario en local: ";
        AppDatabase.getInstance(context).userDao().insertUser(userRoom);

        //Retrieve the questions
        JSONArray jsonArraySurvey = response.getJSONArray("SURVEY");
        JSONObject jsonObjectSurvey;
        for(int x = 0; x <= jsonArraySurvey.length()-1; x++){
            jsonObjectSurvey = jsonArraySurvey.getJSONObject(x);
            questionRoom = new QuestionRoom();
            questionRoom.setPk_question(jsonObjectSurvey.getInt("PK_PREGUNTA"));
            questionRoom.setQuestion(jsonObjectSurvey.getString("TEXTO"));
            //AppDatabase.getInstance(context).questionDao().
        }
        return respuesta;
    }
}
