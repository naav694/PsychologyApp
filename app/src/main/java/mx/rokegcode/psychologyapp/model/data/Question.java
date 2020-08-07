package mx.rokegcode.psychologyapp.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "QUESTION")
public class Question implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "PK_QUESTION")
    private int pkQuestion;
    @ColumnInfo(name = "FK_USER")
    private int fkUser;
    @ColumnInfo(name = "QUESTION")
    private String psyQuestion;

    public Question() {

    }

    protected Question(Parcel in) {
        pkQuestion = in.readInt();
        fkUser = in.readInt();
        psyQuestion = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pkQuestion);
        dest.writeInt(fkUser);
        dest.writeString(psyQuestion);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public int getPkQuestion() {
        return pkQuestion;
    }

    public void setPkQuestion(int pkQuestion) {
        this.pkQuestion = pkQuestion;
    }

    public int getFkUser() {
        return fkUser;
    }

    public void setFkUser(int fkUser) {
        this.fkUser = fkUser;
    }

    public String getPsyQuestion() {
        return psyQuestion;
    }

    public void setPsyQuestion(String psyQuestion) {
        this.psyQuestion = psyQuestion;
    }
}
