package interviewtasks.selfapps.rav.actioncaller.adapters;

import android.content.Context;
import android.view.DragAndDropPermissions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import interviewtasks.selfapps.rav.actioncaller.R;
import interviewtasks.selfapps.rav.actioncaller.actions.Action;


public class ListAdapter extends BaseAdapter {
    private LayoutInflater lInflater;
    private ArrayList<Action> actionList;

    public ListAdapter(Context c, ArrayList<Action> actions) {
        actionList = actions;
        lInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return actionList.size();
    }

    @Override
    public Object getItem(int position) {
        return actionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy hh:mm:ss:SSS");
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_item, parent, false);
        }
        Action action = actionList.get(position);
        String validDays = "";
        for (Integer day : action.getValidDays()){
            validDays += "-"+day;
        }

        String fullInfo = action.getType() + " "
                            + (action.isEnabled()?"Enabled":"Disabled") + " "
                            + action.getPriority() + " "
                            + format.format(new Date(action.getCoolDown())) + " "
                            + validDays + " ";

        ((TextView) view.findViewById(R.id.tw_info)).setText(fullInfo);
        ((TextView) view.findViewById(R.id.tw_lastLoaded))
                .setText(action.getLast_picked()==0
                        ? "Never called":"Last called: " + format.format(new Date(action.getLast_picked())));
        return view;
    }
}
