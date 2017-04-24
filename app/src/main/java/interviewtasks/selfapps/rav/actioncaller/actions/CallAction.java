package interviewtasks.selfapps.rav.actioncaller.actions;

import android.content.Context;
import android.os.Parcel;

import interviewtasks.selfapps.rav.actioncaller.MainActivity;


/**
 *
 */

public class CallAction extends BaseAction {
    private static final String TAG = "CallAction" ;
    private static boolean logging = true;


    @Override
    public void pickAction(Context c) {
        ((MainActivity)c).openCallActivity();
    }
}
