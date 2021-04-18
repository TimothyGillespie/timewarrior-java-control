package eu.gillespie.timewarriorcontrol;

import com.google.gson.Gson;
import eu.gillespie.timewarriorcontrol.gson.TrackingAdapter;
import eu.gillespie.timewarriorcontrol.util.Parser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor
@Setter
public class Tracking {
    String annotation;
    List<Tag> tags;
    LocalDateTime startTime;
    LocalDateTime endTime;

    private static final Gson gson = new Gson().newBuilder()
            .registerTypeAdapter(Tracking.class, new TrackingAdapter())
            .create();

    private static final Pattern tagPatternUserView = Pattern.compile("(?>\\\"([^\\\"\\']+)\\\"|([a-zA-Z0-9]+))");

    /**
     * Parses the output of the DOM information encoded as json.
     *
     * @param json The json returned by the DOM when requested.
     * @return The tracking object with information from the json.
     */
    public static Tracking fromJson(String json) {
        return gson.fromJson(json, Tracking.class);
    }

    /**
     * Parses the output the timew and timew stop command.
     *
     * @param userView The String that the user sees when getting his view of an tracking.
     * @return A tracking object with the information returned in the user view.
     */
    public static Tracking fromUserView(String userView) {
        Tracking tracking = new Tracking();

        String[] lines = userView.split("\\n");
        for(String singleLine : lines) {
            singleLine = singleLine.trim();

            if(singleLine.startsWith("Tracking") || singleLine.startsWith("Recorded")) {
                if(singleLine.startsWith("Tracking"))
                    singleLine = singleLine.replaceFirst("Tracking", "").trim();

                if(singleLine.startsWith("Recorded"))
                    singleLine = singleLine.replaceFirst("Recorded", "").trim();


                Matcher matcher = tagPatternUserView.matcher(singleLine);

                String nextGroup;
                LinkedList<Tag> tags = new LinkedList<>();

                while(matcher.find()) {
                    tags.add(new Tag(matcher.group()));
                }

                tracking.setTags(tags);

                continue;
            }

            if(singleLine.startsWith("Started")) {
                singleLine = singleLine.replaceFirst("Started", "").trim();
                tracking.setStartTime(Parser.parseUserviewDateDate(singleLine));
                continue;
            }

            if(singleLine.startsWith("Ended")) {
                singleLine = singleLine.replaceFirst("Ended", "").trim();
                tracking.setEndTime(Parser.parseUserviewDateDate(singleLine));
            }
        }

        return tracking;
    }

}
