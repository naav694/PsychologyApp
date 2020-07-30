package mx.rokegcode.psychologyapp.model.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import mx.rokegcode.psychologyapp.model.data.UserRoom;

@Dao
public interface UserDao {
    @Query("SELECT COUNT(*) FROM user WHERE LOGIN = :login AND PASSWORD = :password")
    int getUser(String login, String password);

    @Query("SELECT * FROM USER WHERE LOGIN = :login AND PASSWORD = :password")
    UserRoom getUserData(String login, String password);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserRoom userRoom);
}
