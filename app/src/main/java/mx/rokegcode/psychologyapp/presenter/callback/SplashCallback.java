package mx.rokegcode.psychologyapp.presenter.callback;

import mx.rokegcode.psychologyapp.model.data.UserRoom;

public interface SplashCallback {
    void onSuccess(String result, UserRoom user);
    void onLoading();
    void onError(String result);
}
