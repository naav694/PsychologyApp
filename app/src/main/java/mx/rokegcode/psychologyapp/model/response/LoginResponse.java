package mx.rokegcode.psychologyapp.model.response;

import java.util.ArrayList;

import mx.rokegcode.psychologyapp.model.data.Question;
import mx.rokegcode.psychologyapp.model.data.User;

public class LoginResponse {

    private String response;
    private User user;
    private ArrayList<Question> questionList;

    public LoginResponse(String response, User user, ArrayList<Question> questionList) {
        this.response = response;
        this.user = user;
        this.questionList = questionList;
    }


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }
}
