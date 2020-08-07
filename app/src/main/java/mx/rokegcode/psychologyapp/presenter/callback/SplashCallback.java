package mx.rokegcode.psychologyapp.presenter.callback;

import java.util.ArrayList;

import mx.rokegcode.psychologyapp.model.data.Question;

public interface SplashCallback {
    void onSuccess(String response, ArrayList<Question> questionArrayList);
    void onLoading();
    void onError(String error);
}
