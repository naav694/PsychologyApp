package mx.rokegcode.psychologyapp.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ANSWER")
public class AnswerRoom implements Parcelable {
    @PrimaryKey
    @ColumnInfo (name = "PK_ANSWER")
    private int pk_answer;
    @ColumnInfo (name = "FK_PREGUNTA")
    private int fk_pregunta;
    @ColumnInfo (name = "ANSWER")
    private String answer;
    @ColumnInfo (name = "SENT_STATUS")
    private String sentStatus; //E: Enviado, N: No enviado

    public AnswerRoom(){

    }

    protected AnswerRoom(Parcel in) {
        pk_answer = in.readInt();
        fk_pregunta = in.readInt();
        answer = in.readString();
        sentStatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(pk_answer);
        parcel.writeInt(fk_pregunta);
        parcel.writeString(answer);
        parcel.writeString(sentStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnswerRoom> CREATOR = new Creator<AnswerRoom>() {
        @Override
        public AnswerRoom createFromParcel(Parcel in) {
            return new AnswerRoom(in);
        }

        @Override
        public AnswerRoom[] newArray(int size) {
            return new AnswerRoom[size];
        }
    };

    public int getPk_answer() {
        return pk_answer;
    }

    public void setPk_answer(int pk_answer) {
        this.pk_answer = pk_answer;
    }

    public int getFk_pregunta() {
        return fk_pregunta;
    }

    public void setFk_pregunta(int fk_pregunta) {
        this.fk_pregunta = fk_pregunta;
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
