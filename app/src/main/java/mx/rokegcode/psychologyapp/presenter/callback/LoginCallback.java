package mx.rokegcode.psychologyapp.presenter.callback;

import mx.rokegcode.psychologyapp.model.UserRoom;

public interface LoginCallback {
    void onLoading();
    void onError(String error);
    void onSuccess(UserRoom user);
}
