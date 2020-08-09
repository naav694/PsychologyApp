package mx.rokegcode.psychologyapp.presenter.implementation;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mx.rokegcode.psychologyapp.model.data.Answer;
import mx.rokegcode.psychologyapp.model.repository.AnswerRepository;
import mx.rokegcode.psychologyapp.presenter.callback.MainCallback;

public class MainPresenter extends BasePresenter{
    private MainCallback callback;

    public MainPresenter(Context context,MainCallback callback, Activity activity) {
        super(context, activity);
        this.callback = callback;
    }

    /**
    * This method search the survey(group of questions) for the respective user
    */
    public void sendSurveyRx(Context context, ArrayList<Answer> answerArrayList){
        AnswerRepository answerRepository = new AnswerRepository();
        //Searching the questions in the local database
        disposable = Observable.fromCallable(()-> answerRepository.onSendSurvey(context,answerArrayList))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(result -> callback.onLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                    callback.onSuccess(result);
                },throwable -> {
                    callback.onError(throwable.getMessage());
                });
    }

}
