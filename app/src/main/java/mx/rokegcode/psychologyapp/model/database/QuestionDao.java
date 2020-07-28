package mx.rokegcode.psychologyapp.model.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import mx.rokegcode.psychologyapp.model.data.QuestionRoom;

@Dao
public interface QuestionDao {
    @Query("SELECT * FROM QUESTION")
    List<QuestionRoom> getUserSurvey();
}
