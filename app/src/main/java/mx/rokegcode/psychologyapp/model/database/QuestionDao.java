package mx.rokegcode.psychologyapp.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import mx.rokegcode.psychologyapp.model.data.Question;

@Dao
public interface QuestionDao {
    @Query("SELECT * FROM QUESTION WHERE FK_USER = :pkUser")
    List<Question> getUserSurvey(int pkUser);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuestion(Question question);
}
