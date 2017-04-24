package interviewtasks.selfapps.rav.actioncaller.actions;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Action implements Parcelable, Comparable<Action>
{

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("enabled")
    @Expose
    private boolean enabled;
    @SerializedName("priority")
    @Expose
    private int priority;
    @SerializedName("valid_days")
    @Expose
    private List<Integer> validDays = null;
    @SerializedName("cool_down")
    @Expose
    private long coolDown;

    private long last_picked = 0;

    public void setCoolDown(long coolDown) {
        this.coolDown = coolDown;
    }

    public long getLast_picked() {
        return last_picked;
    }

    public void setLast_picked(long last_picked) {
        this.last_picked = last_picked;
    }



    public final static Parcelable.Creator<Action> CREATOR = new Creator<Action>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Action createFromParcel(Parcel in) {
            Action instance = new Action();
            instance.type = ((String) in.readValue((String.class.getClassLoader())));
            instance.enabled = ((boolean) in.readValue((boolean.class.getClassLoader())));
            instance.priority = ((int) in.readValue((int.class.getClassLoader())));
            in.readList(instance.validDays, (java.lang.Integer.class.getClassLoader()));
            instance.coolDown = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public Action[] newArray(int size) {
            return (new Action[size]);
        }

    }
            ;

    /**
     * No args constructor for use in serialization
     *
     */
    public Action() {
    }

    /**
     *
     * @param enabled
     * @param validDays
     * @param priority
     * @param coolDown
     * @param type
     */
    public Action(String type, boolean enabled, int priority, List<Integer> validDays, int coolDown) {
        super();
        this.type = type;
        this.enabled = enabled;
        this.priority = priority;
        this.validDays = validDays;
        this.coolDown = coolDown;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Action withType(String type) {
        this.type = type;
        return this;
    }



    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Action withEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Action withPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public List<Integer> getValidDays() {
        return validDays;
    }

    public void setValidDays(List<Integer> validDays) {
        this.validDays = validDays;
    }

    public Action withValidDays(List<Integer> validDays) {
        this.validDays = validDays;
        return this;
    }

    public long getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public Action withCoolDown(int coolDown) {
        this.coolDown = coolDown;
        return this;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(enabled);
        dest.writeValue(priority);
        dest.writeList(validDays);
        dest.writeValue(coolDown);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Action{" +
                "type='" + type + '\'' +
                ", enabled=" + enabled +
                ", priority=" + priority +
                ", validDays=" + validDays +
                ", coolDown=" + coolDown +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Action)) return false;

        Action action = (Action) o;

        if (enabled != action.enabled) return false;
        if (priority != action.priority) return false;
        if (coolDown != action.coolDown) return false;
        if (!type.equals(action.type)) return false;
        return validDays != null ? validDays.equals(action.validDays) : action.validDays == null;

    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + priority;
        result = 31 * result + (validDays != null ? validDays.hashCode() : 0);
        result = (int) (31 * result + coolDown);
        return result;
    }

    @Override
    public int compareTo(@NonNull Action o) {
        return (this.priority < o.getPriority() ? 1 : ((this.priority == o.getPriority() ? 0 : -1)));
    }


    public void doAction(Context c){
        Calendar cc = Calendar.getInstance();
        last_picked = cc.getTimeInMillis();
        BaseAction.create(type).pickAction(c);

    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isCooledDown(){
        Calendar cc = Calendar.getInstance();
        return cc.getTimeInMillis() < last_picked+coolDown;
    }

    public boolean availableToStartOnThisDay(){
        Calendar cc = Calendar.getInstance();

        int currentDayOfWeek = cc.get(Calendar.DAY_OF_WEEK)-1;
        for (Integer day :validDays) {
            if(day == currentDayOfWeek) return true;
        }
        return false;
    }




}