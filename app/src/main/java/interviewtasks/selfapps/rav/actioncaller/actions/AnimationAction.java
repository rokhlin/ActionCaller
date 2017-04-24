package interviewtasks.selfapps.rav.actioncaller.actions;

import android.content.Context;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import interviewtasks.selfapps.rav.actioncaller.MainActivity;
import interviewtasks.selfapps.rav.actioncaller.R;

/**
 *
 */

public class AnimationAction  extends BaseAction {
    private static final String TAG = "AnimationAction" ;
    private static boolean logging = true;

    @Override
    public void pickAction(Context c) {
        if(logging) Log.d(TAG,"pickAction");

        Button btn = (Button) ((MainActivity)c).findViewById(R.id.bn_pick_action);
        final Animation animationRotateCenter = AnimationUtils.loadAnimation(c, R.anim.rotation);
        btn.startAnimation(animationRotateCenter);
    }
}
