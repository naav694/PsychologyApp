package mx.rokegcode.psychologyapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import mx.rokegcode.psychologyapp.model.data.UserRoom;

public class SessionHelper {

    private static SessionHelper mInstance;
    private SharedPreferences sharedPref;

    public static synchronized SessionHelper getInstance() {
        if (mInstance == null) {
            mInstance = new SessionHelper();
        }
        return mInstance;
    }

    public UserRoom getUserSession(Context context) {
        Gson gson = new Gson();
        sharedPref = context.getSharedPreferences("PsychologyApp", Context.MODE_PRIVATE);
        String jsonUser = sharedPref.getString("user", "");
        return gson.fromJson(jsonUser, UserRoom.class);
    }

    public void setUserSession(Context context, UserRoom userRoom) {
        Gson gson = new Gson();
        sharedPref = context.getSharedPreferences("PsychologyApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String jsonUser = gson.toJson(userRoom);
        editor.putString("user", jsonUser);
        editor.apply();
    }

    public void deleteUserSession(Context context) {
        sharedPref = context.getSharedPreferences("PsychologyApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user", "");
        editor.apply();
    }

    public void setRememberSession(Context context, boolean remember) {
        sharedPref = context.getSharedPreferences("PsychologyApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("remember", remember);
        editor.apply();
    }

    public boolean getRememberSession(Context context) {
        sharedPref = context.getSharedPreferences("PsychologyApp", Context.MODE_PRIVATE);
        return sharedPref.getBoolean("remember", false);
    }

}
