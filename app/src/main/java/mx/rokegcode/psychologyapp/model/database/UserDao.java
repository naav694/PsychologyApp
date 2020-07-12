package mx.rokegcode.psychologyapp.model.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import mx.rokegcode.psychologyapp.model.UserRoom;
@Dao
public interface UserDao {
    @Query("SELECT * FROM USER")
    List<UserRoom> getUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserRoom userRoom);

    @Query("SELECT COUNT(*) FROM user WHERE LOGIN = :login AND PASSWORD = :password")
    int getUser(String login, String password);

    @Query("UPDATE user SET SAVE_USER = :credentials WHERE PK_USER = :pkUser")
    void updateUser(int credentials, int pkUser);
}
