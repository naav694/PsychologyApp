package mx.rokegcode.psychologyapp.model.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import mx.rokegcode.psychologyapp.model.data.QuestionRoom;

@Dao
public interface QuestionDao {
    @Query("SELECT * FROM QUESTION WHERE FK_USER = :pkuser")
    List<QuestionRoom> getUserSurvey(int pkuser);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuestion(QuestionRoom questionRoom);
}
