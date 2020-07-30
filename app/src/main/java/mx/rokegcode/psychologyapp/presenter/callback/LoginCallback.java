package mx.rokegcode.psychologyapp.presenter.callback;

import mx.rokegcode.psychologyapp.model.data.UserRoom;
import mx.rokegcode.psychologyapp.model.response.LoginResponse;

public interface LoginCallback {
    void onLoading();
    void onError(LoginResponse error);
    void onSuccess(LoginResponse result);
}
