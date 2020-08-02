package mx.rokegcode.psychologyapp.model.response;

import java.util.ArrayList;

import mx.rokegcode.psychologyapp.model.data.QuestionRoom;
import mx.rokegcode.psychologyapp.model.data.UserRoom;

public class LoginResponse {

    private String response;
    private UserRoom userRoom;
    private ArrayList<QuestionRoom> questionList;

    public LoginResponse(String response, UserRoom userRoom, ArrayList<QuestionRoom> questionList) {
        this.response = response;
        this.userRoom = userRoom;
        this.questionList = questionList;
    }


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public UserRoom getUserRoom() {
        return userRoom;
    }

    public void setUserRoom(UserRoom userRoom) {
        this.userRoom = userRoom;
    }

    public ArrayList<QuestionRoom> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<QuestionRoom> questionList) {
        this.questionList = questionList;
    }
}
