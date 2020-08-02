package mx.rokegcode.psychologyapp.presenter.implementation;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;

import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mx.rokegcode.psychologyapp.model.repository.UserRepository;
import mx.rokegcode.psychologyapp.model.response.LoginResponse;
import mx.rokegcode.psychologyapp.presenter.callback.LoginCallback;

public class LoginPresenter extends BasePresenter{
    private LoginCallback callback;

    public LoginPresenter(Context context,LoginCallback callback, Activity activity) {
        super(context,activity);
        this.callback = callback;
    }

    /*
    * This function is used for login to the aplication
    */
    public void Login(String user, String password, boolean remember){
        UserRepository userRepository =  new UserRepository();
        //Searching the user in the cloud and if couldn't find search in the local database
        disposable = Observable.fromCallable(()-> userRepository.getUser(context,user,password,remember) )
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(result -> callback.onLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                    if(result.getResponse().equals("main")){
                        callback.onSuccess(result);
                    }else{
                        callback.onError(result.getResponse());
                    }
                }, throwable -> callback.onError(throwable.getMessage()));
    }
}
