package interviewtasks.selfapps.rav.actioncaller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

import interviewtasks.selfapps.rav.actioncaller.Utils.SharedPreferenciesHelper;
import interviewtasks.selfapps.rav.actioncaller.actions.Action;
import interviewtasks.selfapps.rav.actioncaller.adapters.ListAdapter;

public class List_activity extends AppCompatActivity {
    private static final String KEY_ARRAY = "actions";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ArrayList<Action> actions = SharedPreferenciesHelper.restorePreferencies(this);
        ListAdapter adapter = new ListAdapter(this, actions);

        ListView listView = (ListView) findViewById(R.id.listView_actionList);
        listView.setAdapter(adapter);

    }

}
