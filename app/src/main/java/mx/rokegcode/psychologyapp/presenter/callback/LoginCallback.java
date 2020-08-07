package mx.rokegcode.psychologyapp.presenter.callback;

import mx.rokegcode.psychologyapp.model.response.LoginResponse;

public interface LoginCallback {
    void onLoading();
    void onError(String error);
    void onSuccess(LoginResponse result);
}
