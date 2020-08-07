package mx.rokegcode.psychologyapp.model.database;

import androidx.room.Dao;
import androidx.room.Insert;

import java.util.List;

import mx.rokegcode.psychologyapp.model.data.Answer;

@Dao
public interface AnswerDao {

    @Insert
    void insertAnswers(List<Answer> answers);
}
