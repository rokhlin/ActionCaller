package interviewtasks.selfapps.rav.actioncaller;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import interviewtasks.selfapps.rav.actioncaller.Utils.SharedPreferenciesHelper;
import interviewtasks.selfapps.rav.actioncaller.actions.Action;
import interviewtasks.selfapps.rav.actioncaller.interfaces.ActionService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static interviewtasks.selfapps.rav.actioncaller.interfaces.Constants.*;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mp;
    private Button btnPickAction, listOfActions;
    private ArrayList<Action> actions;
    private Intent phoneCall;
    private int  lastPicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneCall = new Intent(Intent.ACTION_DIAL);

        mp = MediaPlayer.create(this, R.raw.beep);

        initButtons();

        if (savedInstanceState != null) {
            actions = savedInstanceState.getParcelableArrayList(KEY_ARRAY);
            lastPicked = savedInstanceState.getInt(KEY_LAST_PICKED);
        }

        initLoadingJSON();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_ARRAY, actions);
        outState.putInt(KEY_LAST_PICKED,lastPicked);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferenciesHelper.savePreferencies(this,actions);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferenciesHelper.restorePreferencies(this);
    }


    private void initLoadingJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://androidexam.s3.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ActionService service = retrofit.create(ActionService.class);
        service.getActions().enqueue(new Callback<List<Action>>() {
            @Override
            public void onResponse(Call<List<Action>> call, Response<List<Action>> response) {
                if(response != null)
                    actions = (ArrayList<Action>) response.body();
                sort(actions); //sorting by priority

                //show buttons after loading
                btnPickAction.setEnabled(true);
                listOfActions.setEnabled(true);
            }

            @Override
            public void onFailure(Call<List<Action>> call, Throwable t) {
                if(logging)  Log.d(TAG, "onFailure");
            }
        });
    }

    private void initButtons() {
        btnPickAction = (Button) findViewById(R.id.bn_pick_action);
        btnPickAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickActionByPriority(lastPicked++);
            }
        });
        btnPickAction.setEnabled(false);

        listOfActions = (Button)findViewById(R.id.btn_list);
        listOfActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenciesHelper.savePreferencies(getApplicationContext(),actions);
                Intent intent = new Intent(MainActivity.this, List_activity.class);
                startActivity(intent);
            }
        });
        listOfActions.setEnabled(false);
    }


    /**
     * Main method to call action by priority
     * @param i - counter of the action to show
     */
    private void pickActionByPriority(int i) {
        if(i < actions.size()){
            if(checkAccessibilityToRun(actions.get(i)))
                actions.get(i).doAction(this);
            else pickActionByPriority(lastPicked++);
        }
        else createInformationToastWithSound(getResources().getText(R.string.notification_action_last_action));
    }

    /**
     *
     * @param action - to check
     * @return true if we can use tis action, and false if not
     */
    private boolean checkAccessibilityToRun(Action action) {
        boolean res = true;
        if(!action.isEnabled()){
            createInformationToastWithSound(action.getType()+" - "+getResources().getText(R.string.notification_action_unvaliable));
            res = false;
        }
        if(!action.availableToStartOnThisDay()){
            createInformationToastWithSound(action.getType()+" - "+getResources().getText(R.string.notification_action_runs_in_incorrect_day));
            res = false;
        }
        if(action.isCooledDown()){
            createInformationToastWithSound(action.getType()+" - "+getResources().getText(R.string.notification_action_cooledDown));
            res = false;
        }
        return res;
    }


    private void createInformationToastWithSound(CharSequence text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
        mp.start();
    }

    private void sort(ArrayList<Action> actions) {
        Collections.sort(actions);
    }


    public void sendNotification(){
        if(logging) Log.d(TAG,"sendNotification");
        NotificationCompat.Builder myNotification = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_bubble_chart)
                .setContentText(getResources().getText(R.string.notification_message))
                .setAutoCancel(true)
                .setContentTitle(getResources().getText(R.string.notification_title));

        //Call phone Dial intent on tap notification
        PendingIntent phoneCallIntent = PendingIntent.getActivity(this, 0, phoneCall, PendingIntent.FLAG_UPDATE_CURRENT);
        myNotification.setContentIntent(phoneCallIntent);

        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, myNotification.build());
    }

    public void openCallActivity(){
        if(logging) Log.d(TAG,"openCallActivity");
        startActivity(phoneCall);
    }




}
