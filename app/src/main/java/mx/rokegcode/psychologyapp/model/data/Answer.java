package mx.rokegcode.psychologyapp.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ANSWER")
public class Answer implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "PK_ANSWER")
    private int pkAnswer;
    @ColumnInfo(name = "FK_PREGUNTA")
    private int fkQuestion;
    @ColumnInfo(name = "ANSWER")
    private String Answer;
    @ColumnInfo(name = "SENT_STATUS")
    private String sentStatus; //E: Enviado, N: No enviado

    public Answer() {

    }

    protected Answer(Parcel in) {
        pkAnswer = in.readInt();
        fkQuestion = in.readInt();
        Answer = in.readString();
        sentStatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(pkAnswer);
        parcel.writeInt(fkQuestion);
        parcel.writeString(Answer);
        parcel.writeString(sentStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

    public int getPkAnswer() {
        return pkAnswer;
    }

    public void setPkAnswer(int pkAnswer) {
        this.pkAnswer = pkAnswer;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public int getFkQuestion() {
        return fkQuestion;
    }

    public void setFkQuestion(int fkQuestion) {
        this.fkQuestion = fkQuestion;
    }

    public String getSentStatus() {
        return sentStatus;
    }

    public void setSentStatus(String sentStatus) {
        this.sentStatus = sentStatus;
    }


}
