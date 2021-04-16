package eu.gillespie.timewarriorcontrol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.gillespie.timewarriorcontrol.gson.TrackingAdapter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class Tracking {
    String annotation;
    List<Tag> tags;
    LocalDateTime startTime;
    LocalDateTime endTime;

    private static final Gson gson = new Gson().newBuilder().registerTypeAdapter(Tracking.class, new TrackingAdapter()).create();

    public static Tracking fromJson(String json) {
        return gson.fromJson(json, Tracking.class);
    }

}
