package interviewtasks.selfapps.rav.actioncaller.actions;

import android.content.Context;
import android.os.Parcel;
import android.widget.Toast;

import interviewtasks.selfapps.rav.actioncaller.R;


/**
 *
 */

public class ToastAction extends BaseAction {

    @Override
    public void pickAction(Context c) {
            //TODO: Add The “toast action” can be chosen only if internet connection is available.
            Toast toast = Toast.makeText(c,
                    c.getResources().getText(R.string.toast_message), Toast.LENGTH_SHORT);
            toast.show();
    }
}
