package eu.gillespie.timewarriorcontrol.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import eu.gillespie.timewarriorcontrol.Tag;
import eu.gillespie.timewarriorcontrol.Tracking;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TrackingAdapter extends TypeAdapter<Tracking> {
    @Override
    public Tracking read(JsonReader jsonReader) throws IOException {
        Tracking tracking = new Tracking();
        jsonReader.beginObject();
        String fieldname = null;

        ArrayList<Tag> tags = new ArrayList<>();

        while (jsonReader.hasNext()) {
            JsonToken token = jsonReader.peek();

            if (token.equals(JsonToken.NAME)) {
                //get the current token
                fieldname = jsonReader.nextName();
            }

            switch(fieldname) {
                case "start":
                    tracking.setStartTime(LocalDateTime.parse(jsonReader.nextString()));
                    break;
                case "end":
                    tracking.setEndTime(LocalDateTime.parse(jsonReader.nextString()));
                    break;
                case "tags":
                    tags.add(new Tag(jsonReader.nextString()));
                    break;
                case "annotation":
                    tracking.setAnnotation(jsonReader.nextString());
                    break;
                default:
                    break;
            }
        }
        jsonReader.endObject();
        tracking.setTags(tags);

        return tracking;
    }

    @Override
    public void write(JsonWriter jsonWriter, Tracking tracking) throws IOException {

    }

}