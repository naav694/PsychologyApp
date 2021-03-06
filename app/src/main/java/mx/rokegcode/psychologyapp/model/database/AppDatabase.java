package mx.rokegcode.psychologyapp.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import mx.rokegcode.psychologyapp.model.data.Answer;
import mx.rokegcode.psychologyapp.model.data.Question;
import mx.rokegcode.psychologyapp.model.data.User;

@Database(entities = {User.class, Question.class, Answer.class}, exportSchema = false, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "PSYCHOLOGY";
    private static AppDatabase mInstance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }

    public abstract UserDao userDao();

    public abstract QuestionDao questionDao();

    public abstract  AnswerDao answerDao();

}
