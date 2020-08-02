package mx.rokegcode.psychologyapp.model.repository;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mx.rokegcode.psychologyapp.model.api.VolleyClient;
import mx.rokegcode.psychologyapp.model.data.QuestionRoom;
import mx.rokegcode.psychologyapp.model.data.UserRoom;
import mx.rokegcode.psychologyapp.model.database.AppDatabase;
import mx.rokegcode.psychologyapp.model.response.LoginResponse;
import mx.rokegcode.psychologyapp.util.AlertReceiver;
import mx.rokegcode.psychologyapp.util.InternetConnection;
import mx.rokegcode.psychologyapp.util.SessionHelper;

public class UserRepository {

    private UserRoom userRoom;
    private QuestionRoom questionRoom;
    private List<QuestionRoom> survey;

    /*
     * Retrieve the user from the session if exist, if doesn't starts the login activity
     * */
    public LoginResponse start(Context context) {
        SessionHelper sessionHelper = new SessionHelper(); //Initialize the session
        if (sessionHelper.getRememberSession(context)) { //If there's a user logged (in session)
            userRoom = sessionHelper.getUserSession(context); //Get the user from the session
            survey = AppDatabase.getInstance(context).questionDao().getUserSurvey(userRoom.getPk_user());
            if (InternetConnection.isConnected(context)) { //If the phone has internet connection
                QuestionRepository questionRepository = new QuestionRepository();
                questionRepository.uploadPendingSurveys();
                //send to main acitivity
                return new LoginResponse("main", userRoom, new ArrayList<>(survey));
            } else { //If the phone doesn't have internet connection
                //send to main activity
                return new LoginResponse("main", userRoom, new ArrayList<>(survey));
            }
        } else { //Is there is not users in session
            //Send to login activity
            return new LoginResponse("login", null, null);
        }
    }

    public LoginResponse getUser(Context context, String user, String password, boolean remember) throws Exception {
        if (AppDatabase.getInstance(context).userDao().getUser(user, password) != 0) { //If the user exist in local database
            return getUserFromRoom(context, user, password, remember);
        } else { //If the user doesn't exist in local database
            if (InternetConnection.isConnected(context)) { //If the phone has internet connection
                //Retrieves the user from webservice
                return getUserFromWebService(context, user, password, remember);
            } else { //If the phone doesn't have internet connection
                //The app can't retrieve the info of the user
                return new LoginResponse("Se requiere conexión a internet para el primer inicio", null, null);
            }
        }
    }

    private LoginResponse getUserFromRoom(Context context, String user, String password, boolean remember) throws Exception {
        userRoom = new UserRoom();
        survey = new ArrayList<>();
        ; //Initialize objects
        SessionHelper sessionHelper = new SessionHelper(); //Initialize session helper
        //Retrieve the user from local database
        userRoom = AppDatabase.getInstance(context).userDao().getUserData(user, password);
        //Retrieve the survey from local database
        survey = AppDatabase.getInstance(context).questionDao().getUserSurvey(userRoom.getPk_user());
        SessionHelper.getInstance().setRememberSession(context, remember); //Set remember in true
        sessionHelper.setUserSession(context, userRoom); //Save the user in session
        return new LoginResponse("main", userRoom, new ArrayList<>(survey));
    }

    /*
     * This method retrieves the user if exist in the cloud
     */
    private LoginResponse getUserFromWebService(Context context, String user, String password, boolean remember) throws Exception {
        //The url of the server
        String url = "https://sistemascoatepec.000webhostapp.com/ws/ws.php?accion=GetUser";
        //Build the request
        RequestFuture<JSONObject> request = VolleyClient.getInstance().createRequest(context, url, Request.Method.POST, createUserJSON(user, password));
        JSONObject response = request.get(); //Retrieves the response
        userRoom = new UserRoom(); //Create a new user room
        //Fill the user with the info retrieved
        JSONObject jsonUser = response.getJSONObject("USER");
        userRoom.setPk_user(jsonUser.getInt("PK_USUARIO"));
        userRoom.setLogin(jsonUser.getString("USUARIO"));
        userRoom.setPassword(jsonUser.getString("CONTRASENIA"));
        userRoom.setFrequency(jsonUser.getInt("FRECUENCIA"));
        userRoom.setSaveUser(remember);
        //Save the user in local database
        AppDatabase.getInstance(context).userDao().insertUser(userRoom);
        if (remember) { //If the user select the check
            SessionHelper.getInstance().setUserSession(context, userRoom); //Save the user in session
            SessionHelper.getInstance().setRememberSession(context, remember); //Set remember in true
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 1);
            c.set(Calendar.MINUTE, 20);
            c.set(Calendar.SECOND, 0);
            startAlarm(context, c);
        }
        //Retrieve the questions
        JSONArray jsonArraySurvey = response.getJSONArray("SURVEY");
        JSONObject jsonObjectSurvey; //Initialize the json object
        survey = new ArrayList<>(); //Initialize the array of questions
        for (int x = 0; x <= jsonArraySurvey.length() - 1; x++) {
            jsonObjectSurvey = jsonArraySurvey.getJSONObject(x); //Get the question into the json object
            questionRoom = new QuestionRoom(); //Create a new question room
            //Fill with the info retrievedc
            questionRoom.setPk_question(jsonObjectSurvey.getInt("PK_PREGUNTA"));
            questionRoom.setFk_user(userRoom.getPk_user());
            questionRoom.setQuestion(jsonObjectSurvey.getString("TEXTO"));
            //Save the question in local database
            AppDatabase.getInstance(context).questionDao().insertQuestion(questionRoom);
            //Add the question in the array
            survey.add(questionRoom);
        }
        return new LoginResponse("main", userRoom, new ArrayList<>(survey));
    }

    private JSONObject createUserJSON(String user, String password) throws Exception {
        JSONObject userJSON = new JSONObject();
        userJSON.put("user", user);
        userJSON.put("password", password);
        return userJSON;
    }

    private void startAlarm(Context context, Calendar c) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm(Context context, Calendar c) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
}
