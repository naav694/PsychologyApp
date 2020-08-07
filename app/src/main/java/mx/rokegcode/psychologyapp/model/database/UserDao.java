package mx.rokegcode.psychologyapp.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import mx.rokegcode.psychologyapp.model.data.User;

@Dao
public interface UserDao {
    @Query("SELECT COUNT(*) FROM User WHERE LOGIN = :login AND PASSWORD = :password")
    int getUser(String login, String password);

    @Query("SELECT * FROM User WHERE LOGIN = :login AND PASSWORD = :password")
    User getUserData(String login, String password);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);
}
