package mx.rokegcode.psychologyapp.model.repository;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mx.rokegcode.psychologyapp.model.api.VolleyClient;
import mx.rokegcode.psychologyapp.model.data.Question;
import mx.rokegcode.psychologyapp.model.data.User;
import mx.rokegcode.psychologyapp.model.database.AppDatabase;
import mx.rokegcode.psychologyapp.model.response.LoginResponse;
import mx.rokegcode.psychologyapp.util.AlertReceiver;
import mx.rokegcode.psychologyapp.util.InternetConnection;

public class UserRepository {

    public LoginResponse onLogin(Context context, String userName, String password) throws Exception {
        if (InternetConnection.isConnected(context)) {
            AnswerRepository answerRepository = new AnswerRepository();
            answerRepository.sendPendingAnswers(context);
            return getUserFromWebService(context, userName, password);
        } else {
            return getUserFromRoom(context, userName, password);
        }
    }

    private LoginResponse getUserFromRoom(Context context, String userName, String password) {
        User user = AppDatabase.getInstance(context).userDao().getUserData(userName, password);
        if (user != null) {
            List<Question> survey = AppDatabase.getInstance(context).questionDao().getUserSurvey(user.getPkUser());
            return new LoginResponse("main", user, new ArrayList<>(survey));
        } else {
            return new LoginResponse("Se requiere conexión a internet para el primer inicio", null, null);
        }
    }

    private LoginResponse getUserFromWebService(Context context, String userName, String password) throws Exception {
        //String url = "https://sistemascoatepec.000webhostapp.com/ws/ws.php?accion=GetUser";
        String url = "https://psyproject.devmaw.com/ws/ws.php?accion=GetUser";
        RequestFuture<JSONObject> request = VolleyClient.getInstance().createRequest(context, url, Request.Method.POST, createUserJSON(userName, password));
        JSONObject response = request.get();
        if (response.getString("RESPUESTA").equals("OK")) {
            User user = new User();
            JSONObject jsonUser = response.getJSONObject("USER");
            user.setPkUser(jsonUser.getInt("PK_USUARIO"));
            user.setUserName(jsonUser.getString("USUARIO"));
            user.setUserPassword(jsonUser.getString("CONTRASENIA"));
            user.setSurveyFrequency(jsonUser.getInt("FRECUENCIA"));
            AppDatabase.getInstance(context).userDao().insertUser(user);
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, (c.get(Calendar.HOUR_OF_DAY)+user.getSurveyFrequency()));
            c.set(Calendar.MINUTE, c.get(Calendar.HOUR));
            c.set(Calendar.SECOND, c.get(Calendar.SECOND));
            startAlarm(context, c);
            JSONArray jsonArraySurvey = response.getJSONArray("SURVEY");
            ArrayList<Question> survey = new ArrayList<>();
            for (int i = 0; i < jsonArraySurvey.length(); i++) {
                JSONObject jsonObjectSurvey = jsonArraySurvey.getJSONObject(i);
                Question question = new Question();
                question.setPkQuestion(jsonObjectSurvey.getInt("PK_PREGUNTA"));
                question.setFkUser(user.getPkUser());
                question.setPsyQuestion(jsonObjectSurvey.getString("TEXTO"));
                AppDatabase.getInstance(context).questionDao().insertQuestion(question);
                survey.add(question);
            }
            return new LoginResponse("main", user, survey);
        } else {
            return new LoginResponse(response.getString("MENSAJE"), null, new ArrayList<>());
        }
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
