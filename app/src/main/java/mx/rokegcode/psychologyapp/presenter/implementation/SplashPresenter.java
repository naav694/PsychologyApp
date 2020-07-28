package mx.rokegcode.psychologyapp.presenter.implementation;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mx.rokegcode.psychologyapp.model.UserRoom;
import mx.rokegcode.psychologyapp.model.database.AppDatabase;
import mx.rokegcode.psychologyapp.presenter.callback.SplashCallback;
import mx.rokegcode.psychologyapp.support.InternetConnection;

public class SplashPresenter extends BasePresenter {
    private SplashCallback callback;
    private UserRoom user;

    public SplashPresenter(Context context, SplashCallback callback, Activity activity) {
        super(context, activity);
        this.callback = callback;
    }

    public void onStartApp(){
        disposable = Observable.fromCallable(this::login)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(result-> callback.onLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                    if (result.equals("main")) {
                        callback.onSuccess(result,user);
                    }else if(result.equals("login")){
                        callback.onSuccess(result,null);
                    }else{
                        callback.onError(result);
                    }
                    }, throwable->{
                        callback.onError(throwable.getMessage());
                    });
    }

    private String login(){
        List<UserRoom> users = AppDatabase.getInstance(context).userDao().getSavedUser();
        if(users.isEmpty()){
            return "login";
        }else{
            uploadPendingSurveys();
            user = users.get(0);
            return "main";
        }
    }
    private void uploadPendingSurveys(){
        if(InternetConnection.isConnected(context)){ //If the phone has connection


        }
    }
}
