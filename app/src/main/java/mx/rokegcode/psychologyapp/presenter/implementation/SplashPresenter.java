package mx.rokegcode.psychologyapp.presenter.implementation;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mx.rokegcode.psychologyapp.model.data.UserRoom;
import mx.rokegcode.psychologyapp.model.database.AppDatabase;
import mx.rokegcode.psychologyapp.model.repository.UserRepository;
import mx.rokegcode.psychologyapp.model.response.LoginResponse;
import mx.rokegcode.psychologyapp.presenter.callback.SplashCallback;
import mx.rokegcode.psychologyapp.util.InternetConnection;
import mx.rokegcode.psychologyapp.util.SessionHelper;

public class SplashPresenter extends BasePresenter {
    private SplashCallback callback;

    public SplashPresenter(Context context, SplashCallback callback, Activity activity) {
        super(context, activity);
        this.callback = callback;
    }

    public void onStartApp(){
        UserRepository userRepository = new UserRepository();
        disposable = Observable.fromCallable( () -> userRepository.start(context))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(result-> callback.onLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                    if(result.getResponse().equals("main") || result.getResponse().equals("login")){
                        callback.onSuccess(result);
                    }else{
                        callback.onError(result.getResponse());
                    }
                    }, throwable-> callback.onError(throwable.getMessage()));
    }

}
