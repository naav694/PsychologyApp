package mx.rokegcode.psychologyapp.view.activity;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import mx.rokegcode.psychologyapp.R;
import mx.rokegcode.psychologyapp.model.QuestionRoom;
import mx.rokegcode.psychologyapp.model.UserRoom;
import mx.rokegcode.psychologyapp.presenter.callback.MainCallback;
import mx.rokegcode.psychologyapp.presenter.implementation.BasePresenter;
import mx.rokegcode.psychologyapp.view.adapter.QuestionAdapter;

public class MainActivity extends BaseActivity implements MainCallback {
    private QuestionAdapter questionAdapter;
    private RecyclerView recyclerViewQuestion;
    private List<QuestionRoom> questionList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
        initRecycler(); //TODO Aqui deberia ir la consulta a las preguntas, usando el Presenter
    }

    /*
     * This method is executed when the activity resumes
     */
    protected void onResume() { //TODO Este para qué?
        super.onResume();
        //
    }

    private void initComponents() {
        recyclerViewQuestion = findViewById(R.id.lstQuestions);
    }

    //TODO Se manda a llamar en el onSuccess()
    private void initRecycler() {
        recyclerViewQuestion.setLayoutManager(new LinearLayoutManager(this));
        questionAdapter = new QuestionAdapter(this, questionList); //TODO No es necesario pasarle el context, usa parent.context
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
    public void onSuccess(UserRoom user) {

    }
}