package mx.rokegcode.psychologyapp.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "USER")
public class User implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "PK_USER")
    private int pkUser;
    @ColumnInfo(name = "LOGIN")
    private String userName;
    @ColumnInfo(name = "PASSWORD")
    private String userPassword;
    @ColumnInfo(name = "FREQUENCY")
    private int surveyFrequency;

    public User() {

    }

    protected User(Parcel in) {
        pkUser = in.readInt();
        userName = in.readString();
        userPassword = in.readString();
        surveyFrequency = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getPkUser() {
        return pkUser;
    }

    public void setPkUser(int pkUser) {
        this.pkUser = pkUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getSurveyFrequency() {
        return surveyFrequency;
    }

    public void setSurveyFrequency(int surveyFrequency) {
        this.surveyFrequency = surveyFrequency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(pkUser);
        parcel.writeString(userName);
        parcel.writeString(userPassword);
        parcel.writeInt(surveyFrequency);
    }
}
