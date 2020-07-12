package mx.rokegcode.psychologyapp.presenter.implementation;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mx.rokegcode.psychologyapp.model.UserRoom;
import mx.rokegcode.psychologyapp.presenter.callback.MainCallback;
import mx.rokegcode.psychologyapp.support.InternetConnection;

public class MainPresenter extends BasePresenter{
    private MainCallback callback;

    public MainPresenter(Context context, Activity activity) {
        super(context, activity);
    }

    /*
    * This method search the survey(group of questions) for the respective user
    */
    public void Survey(UserRoom userRoom){
        //Searching the user in the cloud and if couldn't find search in the local database
        disposable = Observable.fromCallable(()-> InternetConnection.isConnected(context) ? getSurveyFromWebService(userRoom)  : getSurveyFromRoom(userRoom) )
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(result -> callback.onLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                    if(result.isEmpty()){
                        callback.onSuccess(userRoom);
                    }else{
                        callback.onError(result);
                    }
                },trhowable ->{
                    Toast.makeText(context, "hola", Toast.LENGTH_SHORT).show();
                });

    }
}
