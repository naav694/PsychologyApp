package mx.rokegcode.psychologyapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import mx.rokegcode.psychologyapp.R;
import mx.rokegcode.psychologyapp.model.data.QuestionRoom;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> implements View.OnClickListener {
    List<QuestionRoom> questionList;

    //Listener
    private View.OnClickListener listener;

    public QuestionAdapter(List<QuestionRoom> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_question,parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtQuestion.setText(questionList.get(position).getQuestion());
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!= null){
            listener.onClick(view);
        }
    }

    public void refreshInfo(List<QuestionRoom> model){
        this.questionList = model;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtQuestion) TextView txtQuestion;
        @BindView(R.id.txtAnswer) EditText getTxtAnswer;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }
}
