package mx.rokegcode.psychologyapp.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;
import mx.rokegcode.psychologyapp.model.data.AnswerRoom;

@Dao
public interface AnswerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAnswer(AnswerRoom answerRoom);

    @Update()
    void updateAnswer(AnswerRoom answerRoom);
}
