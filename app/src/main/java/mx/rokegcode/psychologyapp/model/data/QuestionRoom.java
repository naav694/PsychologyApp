package mx.rokegcode.psychologyapp.model.data;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "QUESTION")
public class QuestionRoom implements Parcelable{
    @PrimaryKey
    @ColumnInfo (name = "PK_QUESTION")
    private int pk_question;
    @ColumnInfo (name = "FK_USER")
    private int fk_user;
    @ColumnInfo (name = "QUESTION")
    private String question;
    @ColumnInfo (name = "ANSWER")
    private String answer;
    @ColumnInfo (name="SENT_STATUS")
    private String sentStatus; // E: Enviado, N: No enviado

    public QuestionRoom(){

    }

    protected QuestionRoom(Parcel in) {
        pk_question = in.readInt();
        fk_user = in.readInt();
        question = in.readString();
        answer = in.readString();
        sentStatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pk_question);
        dest.writeInt(fk_user);
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeString(sentStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuestionRoom> CREATOR = new Creator<QuestionRoom>() {
        @Override
        public QuestionRoom createFromParcel(Parcel in) {
            return new QuestionRoom(in);
        }

        @Override
        public QuestionRoom[] newArray(int size) {
            return new QuestionRoom[size];
        }
    };

    public int getPk_question() {
        return pk_question;
    }

    public void setPk_question(int pk_question) {
        this.pk_question = pk_question;
    }

    public int getFk_user() {
        return fk_user;
    }

    public void setFk_user(int fk_user) {
        this.fk_user = fk_user;
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

    public String getSentStatus() {
        return sentStatus;
    }

    public void setSentStatus(String sentStatus) {
        this.sentStatus = sentStatus;
    }
}
