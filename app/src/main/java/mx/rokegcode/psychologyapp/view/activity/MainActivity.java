package mx.rokegcode.psychologyapp.view.activity;

import android.os.Bundle;
import android.widget.Toast;

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
    private ArrayList<QuestionRoom> survey = null;
    private UserRoom user = null;

    //@BindView(R.id.lstQuestions) RecyclerView recyclerViewQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainPresenter(this,this,this);
        user = getIntent().getExtras().getParcelable("user");
        survey = getIntent().getExtras().getParcelableArrayList("survey");
        Toast.makeText(this, survey.size(), Toast.LENGTH_SHORT).show();

        //initRecyclerView(survey);
    }

    private void initRecyclerView(ArrayList<QuestionRoom> survey){
        recyclerViewQuestion = findViewById(R.id.lstQuestions);
        recyclerViewQuestion.setLayoutManager(new LinearLayoutManager(this));
        questionAdapter = new QuestionAdapter(survey);
        recyclerViewQuestion.setAdapter(questionAdapter);
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

                break;
        }
    }
}