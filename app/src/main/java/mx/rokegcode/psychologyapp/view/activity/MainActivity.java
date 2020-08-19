package mx.rokegcode.psychologyapp.view.activity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mx.rokegcode.psychologyapp.R;
import mx.rokegcode.psychologyapp.model.data.Answer;
import mx.rokegcode.psychologyapp.model.data.Question;
import mx.rokegcode.psychologyapp.presenter.callback.MainCallback;
import mx.rokegcode.psychologyapp.presenter.implementation.MainPresenter;
import mx.rokegcode.psychologyapp.view.dialog.SweetDialogs;

public class MainActivity extends BaseActivity implements MainCallback {
    @BindView(R.id.layoutQuestions)
    LinearLayout layoutQuestions;

    private SweetAlertDialog sweetProgress;
    private ArrayList<Question> mSurvey;
    private MainPresenter mPresenter;

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
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    private void addViewQuestion(ArrayList<Question> survey) {
        for (int i = 0; i < survey.size(); i++) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.layout_question_field, null);
            TextInputLayout layoutQuestion = view.findViewById(R.id.layoutQuestion);
            layoutQuestion.setHint(survey.get(i).getPsyQuestion());
            layoutQuestion.setHintTextColor(ColorStateList.valueOf(getColor(R.color.colorAccent)));
            TextInputEditText editAnswer = (TextInputEditText) layoutQuestion.getEditText();
            if (editAnswer != null) {
                editAnswer.setId(survey.get(i).getPkQuestion());
            }
            layoutQuestions.addView(view);
        }
    }

    @OnClick(R.id.btnSendSurvey)
    public void onSendSurvey() {
        boolean send = true;
        ArrayList<Answer> answers = new ArrayList<>();
        for (int i = 0; i < mSurvey.size(); i++) {
            TextInputEditText tmpEditText = findViewById(mSurvey.get(i).getPkQuestion());
            Answer answer = new Answer();
            answer.setFkQuestion(mSurvey.get(i).getPkQuestion());
            answer.setSentStatus("N");
            if (tmpEditText.getText().toString().equals("")) {
                send = false;
                break;
            } else {
                answer.setAnswer(tmpEditText.getText().toString());
            }
            answers.add(answer);
        }
        if (send) {
            mPresenter.sendSurveyRx(this, answers);
        } else {
            SweetDialogs.sweetWarning(this, "Debe contestar todas las preguntas").show();
        }
    }

    @Override
    public void onLoading() {
        sweetProgress = SweetDialogs.sweetLoading(this, "Guardando respuestas...");
        sweetProgress.show();
    }

    @Override
    public void onError(String error) {
        sweetProgress.dismiss();
        SweetDialogs.sweetError(this, error).show();
    }

    @Override
    public void onSuccess(String result) {
        sweetProgress.dismiss();
        SweetDialogs.sweetSuccessCloseActivity(this, result, this).show();
    }
}