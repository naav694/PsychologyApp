package mx.rokegcode.psychologyapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import mx.rokegcode.psychologyapp.model.data.User;

public class SessionHelper {

    private static SessionHelper mInstance;
    private SharedPreferences sharedPref;

    public static synchronized SessionHelper getInstance() {
        if (mInstance == null) {
            mInstance = new SessionHelper();
        }
        return mInstance;
    }

    public User getUserSession(Context context) {
        Gson gson = new Gson();
        sharedPref = context.getSharedPreferences("PsychologyApp", Context.MODE_PRIVATE);
        String jsonUser = sharedPref.getString("user", "");
        return gson.fromJson(jsonUser, User.class);
    }

    public void setUserSession(Context context, User user) {
        Gson gson = new Gson();
        sharedPref = context.getSharedPreferences("PsychologyApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String jsonUser = gson.toJson(user);
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
