package interviewtasks.selfapps.rav.actioncaller.interfaces;


import java.util.List;

import interviewtasks.selfapps.rav.actioncaller.actions.Action;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ActionService {

    @GET("/butto_to_action_config.json")
    Call<List<Action>> getActions();
}
