package mx.rokegcode.psychologyapp.view.activity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import mx.rokegcode.psychologyapp.R;
import mx.rokegcode.psychologyapp.model.data.QuestionRoom;
import mx.rokegcode.psychologyapp.model.data.UserRoom;
import mx.rokegcode.psychologyapp.presenter.callback.MainCallback;
import mx.rokegcode.psychologyapp.presenter.implementation.MainPresenter;
import mx.rokegcode.psychologyapp.view.adapter.QuestionAdapter;

public class MainActivity extends BaseActivity implements MainCallback {
    private MainPresenter presenter;
    private QuestionAdapter questionAdapter;
    private RecyclerView recyclerViewQuestion;
    private List<QuestionRoom> questionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainPresenter(this,this,this);
        presenter.GetSurvey();
        recyclerViewQuestion = findViewById(R.id.lstQuestions);
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onSuccess(String result) {

        switch(result){
            case "survey":
                recyclerViewQuestion.setLayoutManager(new LinearLayoutManager(this));
                questionAdapter = new QuestionAdapter(questionList);
                recyclerViewQuestion.setAdapter(questionAdapter);
                break;
        }
    }
}