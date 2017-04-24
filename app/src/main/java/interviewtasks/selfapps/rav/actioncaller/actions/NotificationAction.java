package interviewtasks.selfapps.rav.actioncaller.actions;

import android.content.Context;

import interviewtasks.selfapps.rav.actioncaller.MainActivity;

/**
 *
 */

public class NotificationAction extends BaseAction {


    @Override
    public void pickAction(Context c) {

            ((MainActivity)c).sendNotification();
    }
}
