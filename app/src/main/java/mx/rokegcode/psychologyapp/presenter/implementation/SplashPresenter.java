package mx.rokegcode.psychologyapp.presenter.implementation;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mx.rokegcode.psychologyapp.model.data.User;
import mx.rokegcode.psychologyapp.model.repository.AnswerRepository;
import mx.rokegcode.psychologyapp.model.repository.UserRepository;
import mx.rokegcode.psychologyapp.model.response.LoginResponse;
import mx.rokegcode.psychologyapp.presenter.callback.SplashCallback;

public class SplashPresenter extends BasePresenter {
    private SplashCallback callback;

    public SplashPresenter(Context context, SplashCallback callback, Activity activity) {
        super(context, activity);
        this.callback = callback;
    }

    public void onStartApp() {
        UserRepository userRepository = new UserRepository();
        if (sessionHelper.getRememberSession(context)) {
            User user = sessionHelper.getUserSession(context);
            disposable = Observable.fromCallable(() -> userRepository.onLogin(context, user.getUserName(), user.getUserPassword()))
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(result -> callback.onLoading())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        if (result.getResponse().equals("main") || result.getResponse().equals("login")) {
                            callback.onSuccess(result.getResponse(), result.getQuestionList());
                        } else {
                            callback.onError(result.getResponse());
                        }
                    }, throwable -> callback.onError(throwable.getMessage()));
        } else {
            callback.onSuccess("login", new ArrayList<>());
        }

    }

}
