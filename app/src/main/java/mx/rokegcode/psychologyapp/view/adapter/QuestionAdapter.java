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
import androidx.recyclerview.widget.RecyclerView;
import mx.rokegcode.psychologyapp.R;
import mx.rokegcode.psychologyapp.model.QuestionRoom;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    List<QuestionRoom> model;

    //Listener
    private View.OnClickListener listener;

    public QuestionAdapter(Context context, List<QuestionRoom> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_question,parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtQuestion.setText(model.get(position).getQuestion());

        boolean isExpanded = model.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE: View.GONE);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!= null){
            listener.onClick(view);
        }
    }

    public void refreshInfo(List<QuestionRoom> model){
        this.model = model;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtQuestion;
        EditText txtAnswer;
        ImageView btnExpandir;
        LinearLayout expandableLayout;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            btnExpandir = itemView.findViewById(R.id.btnExpandir);

            btnExpandir.setOnClickListener(view -> {
                QuestionRoom question = model.get(getAdapterPosition());
                question.setExpanded(!question.isExpanded());
                notifyItemChanged(getAdapterPosition());
            });
        }
    }
}
