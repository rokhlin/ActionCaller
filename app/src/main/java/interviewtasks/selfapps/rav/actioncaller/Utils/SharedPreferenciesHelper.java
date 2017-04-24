package interviewtasks.selfapps.rav.actioncaller.Utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import interviewtasks.selfapps.rav.actioncaller.actions.Action;
import static interviewtasks.selfapps.rav.actioncaller.interfaces.Constants.*;

public class SharedPreferenciesHelper {

    public static void savePreferencies(Context c, ArrayList<Action> actions){
        SharedPreferences mSettings = c.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);;
        String json = new Gson().toJson(actions);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(KEY_ARRAY, json);
        editor.apply();
    }

    public static ArrayList<Action> restorePreferencies(Context c){
        SharedPreferences mSettings = c.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);;
        String json = mSettings.getString(KEY_ARRAY,"");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Action>>(){}.getType();
        return (ArrayList<Action>) gson.fromJson(json, listType);
    }
}
