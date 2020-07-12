package mx.rokegcode.psychologyapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "USER")
public class UserRoom implements Parcelable{
    @PrimaryKey
    @ColumnInfo(name = "PK_USER")
    private int pk_user;
    @ColumnInfo(name = "LOGIN")
    private String login;
    @ColumnInfo(name = "PASSWORD")
    private String password;
    @ColumnInfo(name = "SAVE_USER")
    private int saveUser;

    public UserRoom(){

    }

    protected UserRoom(Parcel in){
        pk_user = in.readInt();
        login = in.readString();
        password = in.readString();
        saveUser = in.readInt();
    }

    public static final Creator<UserRoom> CREATOR = new Creator<UserRoom>() {
        @Override
        public UserRoom createFromParcel(Parcel in) {
            return new UserRoom(in);
        }

        @Override
        public UserRoom[] newArray(int size) {
            return new UserRoom[size];
        }
    };

    public int getPk_user() {
        return pk_user;
    }

    public void setPk_user(int pk_user) {
        this.pk_user = pk_user;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSaveUser() {
        return saveUser;
    }

    public void setSaveUser(int saveUser) {
        this.saveUser = saveUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(pk_user);
        parcel.writeString(login);
        parcel.writeString(password);
        parcel.writeInt(saveUser);
    }
}
