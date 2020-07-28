package mx.rokegcode.psychologyapp.presenter.callback;

import mx.rokegcode.psychologyapp.model.data.UserRoom;

public interface MainCallback {
    void onLoading();
    void onError(String error);
    void onSuccess(String result);
}
