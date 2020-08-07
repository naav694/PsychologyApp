package mx.rokegcode.psychologyapp.presenter.implementation;

import android.app.Activity;
import android.content.Context;

import io.reactivex.disposables.Disposable;
import mx.rokegcode.psychologyapp.util.SessionHelper;

public abstract class BasePresenter {
    protected Context context;
    protected Activity activity;
    protected Disposable disposable;
    protected SessionHelper sessionHelper = new SessionHelper();

    BasePresenter(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public void onDispose(){
        if(disposable != null){
            disposable.dispose();
        }
    }
}
