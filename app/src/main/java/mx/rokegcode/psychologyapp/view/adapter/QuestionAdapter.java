package mx.rokegcode.psychologyapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import mx.rokegcode.psychologyapp.R;
import mx.rokegcode.psychologyapp.model.data.QuestionRoom;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    List<QuestionRoom> questionList;

    public QuestionAdapter(List<QuestionRoom> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_question,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //TODO AQUI MERENGUES Y ESQUE YO RECUERDO QUE SE LE MANDABA EL MODELO EN EL CONSTRUCTOR :C
        holder.txtAnswer.setHint("holi"+position);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public void refreshInfo(List<QuestionRoom> model){
        this.questionList = model;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtAnswer) TextInputEditText txtAnswer;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }
}
