package mx.rokegcode.psychologyapp.presenter.callback;

import java.util.ArrayList;

import mx.rokegcode.psychologyapp.model.data.Question;
import mx.rokegcode.psychologyapp.model.response.LoginResponse;

public interface SplashCallback {
    void onSuccess(LoginResponse response);
    void onLoading();
    void onError(String error);
}
