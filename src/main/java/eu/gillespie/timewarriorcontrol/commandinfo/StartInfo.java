package eu.gillespie.timewarriorcontrol.commandinfo;

import eu.gillespie.timewarriorcontrol.Tag;
import eu.gillespie.timewarriorcontrol.Tracking;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@AllArgsConstructor
public class StartInfo {
     private final Tracking previousTracking;
     private final Tracking currentTracking;
     private final List<Tag> newTags;

     private static final Pattern newTagPattern = Pattern.compile("Note: '\\\"?(?<newTag>[^\\'\\\"]*)\\\"?' is a new tag.$");

    /**
     * Parses the output the timew and timew stop command.
     *
     * @param userView The String that the user sees when getting his view of an tracking.
     * @return A tracking object with the information returned in the user view.
     */
    public static StartInfo fromUserView(String userView) {

        LinkedList<String> recordedLines = new LinkedList<>();
        LinkedList<String> trackingLines = new LinkedList<>();

        LinkedList<Tag> newTags = new LinkedList<>();

        boolean undecided = true;
        boolean recorded = true;

        String[] lines = userView.split("\n");


        for(String singleLine : lines) {
            singleLine = singleLine.trim();
            if(singleLine.startsWith("Note:")) {
                Matcher matcher = newTagPattern.matcher(singleLine);
                if(matcher.find())
                    newTags.add(new Tag(matcher.group("newTag")));

                continue;
            }

            if(singleLine.startsWith("Recorded")) {
                undecided = false;
                recorded = true;
            }

            if(singleLine.startsWith("Tracking")) {
                undecided = false;
                recorded = false;
            }

            if(!undecided && recorded)
                recordedLines.add(singleLine);

            if(!undecided && !recorded)
                trackingLines.add(singleLine);
        }

        Tracking recordedTracking = null;
        Tracking activeTracking = null;

        if(!recordedLines.isEmpty())
            recordedTracking = Tracking.fromUserView(String.join("\n", recordedLines));

        if(!trackingLines.isEmpty())
            activeTracking = Tracking.fromUserView(String.join("\n", trackingLines));

        return new StartInfo(recordedTracking, activeTracking, newTags);
    }
}
