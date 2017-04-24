package interviewtasks.selfapps.rav.actioncaller.actions;

import android.content.Context;
import android.util.Log;


public class BaseAction {
    private static final String TAG = "BaseAction";
    private boolean logging = true;

    private Context c;



    public Context getContext() {
        return c;
    }

    public static BaseAction create(String type){
        switch (type){
            case "toast":
                return new ToastAction();

            case "animation":
                return new AnimationAction();

            case "call":
                return new CallAction();

            case "notification":
                return new NotificationAction();
        }
        return null;
    }

    public void pickAction(Context c) {
        if(logging) Log.d(TAG,"pickAction BaseAction");
    }
}
