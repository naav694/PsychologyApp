package mx.rokegcode.psychologyapp.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import mx.rokegcode.psychologyapp.R;
import mx.rokegcode.psychologyapp.model.data.Answer;
import mx.rokegcode.psychologyapp.model.data.Question;
import mx.rokegcode.psychologyapp.presenter.callback.MainCallback;
import mx.rokegcode.psychologyapp.presenter.implementation.MainPresenter;

public class MainActivity extends BaseActivity implements MainCallback {
    private MainPresenter mPresenter;

    /*@BindView(R.id.recyclerQuestions)
    RecyclerView recyclerViewQuestion;*/
    @BindView(R.id.layoutQuestions)
    LinearLayout layoutQuestions;

    private ArrayList<Question> mSurvey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Cuestionario");
        }
        mPresenter = new MainPresenter(this, this, this);
        if (getIntent().getExtras() != null) {
            mSurvey = getIntent().getExtras().getParcelableArrayList("survey");
            if (mSurvey != null) {
                addViewQuestion(mSurvey);
            }
            //initRecyclerView(survey);
        }
    }

    /*private void initRecyclerView(ArrayList<Question> survey) {
        recyclerViewQuestion.setLayoutManager(new LinearLayoutManager(this));
        QuestionAdapter questionAdapter = new QuestionAdapter(survey);
        recyclerViewQuestion.setAdapter(questionAdapter);
    }*/


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    private void addViewQuestion(ArrayList<Question> survey) {
        for (int i = 0; i < survey.size(); i++) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.layout_question_field, null);
            TextInputLayout layoutQuestion = view.findViewById(R.id.layoutQuestion);
            layoutQuestion.setHint(survey.get(i).getPsyQuestion());
            TextInputEditText editAnswer = (TextInputEditText) layoutQuestion.getEditText();
            if (editAnswer != null) {
                editAnswer.setId(survey.get(i).getPkQuestion());
            }
            layoutQuestions.addView(view);
        }
    }

    @OnClick(R.id.btnSendSurvey)
    public void onSendSurvey() {
        ArrayList<Answer> answers = new ArrayList<>();
        for (int i = 0; i < mSurvey.size(); i++) {
            Answer answer = new Answer();
            answer.setFkQuestion(mSurvey.get(i).getPkQuestion());
            TextInputEditText tmpEditText = findViewById(mSurvey.get(i).getPkQuestion());
            if (tmpEditText.getText() != null) {
                answer.setmAnswer(tmpEditText.getText().toString());
            }
            answers.add(answer);
        }
        mPresenter.sendSurveyRx(this, answers);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onSuccess(String result) {

    }
}