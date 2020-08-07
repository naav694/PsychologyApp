package mx.rokegcode.psychologyapp.presenter.implementation;

import android.app.Activity;
import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mx.rokegcode.psychologyapp.model.repository.UserRepository;
import mx.rokegcode.psychologyapp.presenter.callback.LoginCallback;

public class LoginPresenter extends BasePresenter {
    private LoginCallback callback;

    public LoginPresenter(Context context, LoginCallback callback, Activity activity) {
        super(context, activity);
        this.callback = callback;
    }

    /*
     * This function is used for login to the aplication
     */
    public void onLogin(String user, String password, boolean remember) {
        UserRepository userRepository = new UserRepository();
        disposable = Observable.fromCallable(() -> userRepository.onLogin(context, user, password))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(result -> callback.onLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.getResponse().equals("main")) {
                        result.getUser().setUserPassword(password);
                        sessionHelper.setRememberSession(context, remember);
                        sessionHelper.setUserSession(context, result.getUser());
                        callback.onSuccess(result);
                    } else {
                        callback.onError(result.getResponse());
                    }
                }, throwable -> callback.onError(throwable.getMessage()));
    }
}
