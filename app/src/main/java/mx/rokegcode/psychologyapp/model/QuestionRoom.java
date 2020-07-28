package mx.rokegcode.psychologyapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "QUESTION")
public class QuestionRoom implements Parcelable{ //TODO Los objetos van en el package de "data"
    @PrimaryKey
    @ColumnInfo (name = "PK_QUESTION")
    private int pk_question;
    @ColumnInfo (name = "QUESTION")
    private String question;
    @ColumnInfo (name = "ANSWER")
    private String answer;
    private boolean expanded;

    public QuestionRoom(){

    }

    protected QuestionRoom(Parcel in){
        pk_question = in.readInt();
        question = in.readString();
        answer = in.readString();
    }

    public static final Parcelable.Creator<UserRoom> CREATOR = new Parcelable.Creator<UserRoom>() {
        @Override
        public UserRoom createFromParcel(Parcel in) {
            return new UserRoom(in);
        }

        @Override
        public UserRoom[] newArray(int size) {
            return new UserRoom[size];
        }
    };

    public boolean isExpanded(){
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getPk_question() {
        return pk_question;
    }

    public void setPk_question(int pk_question) {
        this.pk_question = pk_question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public static Creator<UserRoom> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(pk_question);
        parcel.writeString(question);
        parcel.writeString(answer);
    }
}
