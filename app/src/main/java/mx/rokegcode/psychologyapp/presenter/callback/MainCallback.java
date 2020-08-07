package mx.rokegcode.psychologyapp.presenter.callback;

public interface MainCallback {
    void onLoading();
    void onError(String error);
    void onSuccess(String result);
}
