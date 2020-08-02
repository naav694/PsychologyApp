package mx.rokegcode.psychologyapp.presenter.callback;

import mx.rokegcode.psychologyapp.model.data.UserRoom;
import mx.rokegcode.psychologyapp.model.response.LoginResponse;

public interface SplashCallback {
    void onSuccess(LoginResponse result);
    void onLoading();
    void onError(String error);
}
