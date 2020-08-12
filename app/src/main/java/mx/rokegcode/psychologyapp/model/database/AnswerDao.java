package mx.rokegcode.psychologyapp.model.database;

import androidx.room.Dao;
import androidx.room.Insert;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Query;
import androidx.room.Update;
import mx.rokegcode.psychologyapp.model.data.Answer;

@Dao
public interface AnswerDao {

    @Insert
    void insertAnswers(List<Answer> answers);

    @Query("SELECT * FROM ANSWER WHERE SENT_STATUS = 'N' ")
    List<Answer> getPendingAnswers();

    @Update
    void updateAnswerStatus(Answer answer);
}
