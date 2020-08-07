package mx.rokegcode.psychologyapp.model.repository;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import mx.rokegcode.psychologyapp.model.data.Answer;
import mx.rokegcode.psychologyapp.util.InternetConnection;

public class AnswerRepository {

    public String onSendSurvey(Context context, ArrayList<Answer> answerArrayList) {
        if (InternetConnection.isConnected(context)) {
            return surveyToWB(context, answerArrayList);
        } else {
            return surveyToRoom(context, answerArrayList);
        }
    }

    private String surveyToWB(Context context, ArrayList<Answer> answerArrayList) {

        return "";
    }

    private JSONObject createJSONSurvey(ArrayList<Answer> answerArrayList) throws Exception {
        JSONObject jsonSurvey = new JSONObject();
        JSONArray jsonAnswers = new JSONArray();
        for(int i=0; i < answerArrayList.size(); i++) {
            JSONObject jsonAnswer = new JSONObject();
            jsonAnswer.put("fkQuestion", answerArrayList.get(i).getFkQuestion());
            jsonAnswer.put("answer", answerArrayList.get(i).getmAnswer());
            jsonAnswers.put(jsonAnswer);
        }
        jsonSurvey.put("survey", jsonAnswers);
        return jsonSurvey;
    }

    private String surveyToRoom(Context context, ArrayList<Answer> answerArrayList) {

        return "";
    }
}
