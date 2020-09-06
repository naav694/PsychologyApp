package mx.rokegcode.psychologyapp.model.repository;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.RequestFuture;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mx.rokegcode.psychologyapp.model.api.VolleyClient;
import mx.rokegcode.psychologyapp.model.data.Answer;
import mx.rokegcode.psychologyapp.model.data.Question;
import mx.rokegcode.psychologyapp.model.data.User;
import mx.rokegcode.psychologyapp.model.database.AppDatabase;
import mx.rokegcode.psychologyapp.model.response.LoginResponse;
import mx.rokegcode.psychologyapp.util.InternetConnection;

public class AnswerRepository {

    public String onSendSurvey(Context context, ArrayList<Answer> answerArrayList) throws Exception {
        if (InternetConnection.isConnected(context)) {
            return surveyToWB(context, answerArrayList);
        } else {
            return surveyToRoom(context, answerArrayList);
        }
    }

    private String surveyToWB(Context context, ArrayList<Answer> answerArrayList) throws Exception{
        //String url = "https://sistemascoatepec.000webhostapp.com/ws/ws.php?accion=SaveAnswers";
        String url = "https://psyproject.devmaw.com/ws/ws.php?accion=SaveAnswers";
        RequestFuture<JSONObject> request = VolleyClient.getInstance().createRequest(context,url, Request.Method.POST, createJSONSurvey(answerArrayList));
        JSONObject response = request.get();
        return response.getString("MENSAJE");
    }

    private JSONObject createJSONSurvey(ArrayList<Answer> answerArrayList) throws Exception {
        JSONObject jsonSurvey = new JSONObject();
        JSONArray jsonAnswers = new JSONArray();
        for(int i=0; i < answerArrayList.size(); i++) {
            JSONObject jsonAnswer = new JSONObject();
            jsonAnswer.put("FK_PREGUNTA", answerArrayList.get(i).getFkQuestion());
            jsonAnswer.put("TEXTO", answerArrayList.get(i).getAnswer());
            jsonAnswers.put(jsonAnswer);
        }
        jsonSurvey.put("surveypost", jsonAnswers);
        return jsonSurvey;
    }

    private String surveyToRoom(Context context, ArrayList<Answer> answerArrayList) {
        AppDatabase.getInstance(context).answerDao().insertAnswers(answerArrayList);
        return "Las respuestas se guardaron en el telefono";
    }

    public void sendPendingAnswers(Context context) throws Exception {
        ArrayList<Answer> answers = new ArrayList<>();
        answers = (ArrayList<Answer>) AppDatabase.getInstance(context).answerDao().getPendingAnswers();
        //String url = "https://sistemascoatepec.000webhostapp.com/ws/ws.php?accion=SaveAnswers";
        String url = "https://psyproject.devmaw.com/ws/ws.php?accion=SaveAnswers";
        RequestFuture<JSONObject> request = VolleyClient.getInstance().createRequest(context,url, Request.Method.POST, createJSONSurvey(answers));
        JSONObject response = request.get();
        if(response.getString("RESPUESTA").equals("OK")){
            for(int i = 0; i < answers.size(); i++){
                answers.get(i).setSentStatus("E");
                AppDatabase.getInstance(context).answerDao().updateAnswerStatus(answers.get(i));
            }
        }
    }

}
