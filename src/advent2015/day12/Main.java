package advent2015.day12;

import org.json.JSONArray;
import org.json.JSONObject;
import util.InputLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.StreamSupport;

public class Main {

    public static void main(String[] args) throws Exception {
        String input = InputLoader.loadInput().get(0);

        AtomicLong count = new AtomicLong(0L);
        JSONArray array = new JSONArray(input);

        countArray(array, count);

        System.out.println(count.get());
    }

    private static void countArray(JSONArray array, AtomicLong count) {
        count.addAndGet(StreamSupport.stream(Spliterators.spliteratorUnknownSize(array.iterator(), 0), false)
                .filter(Integer.class::isInstance)
                .mapToInt(Integer.class::cast)
                .sum());

        count.addAndGet(StreamSupport.stream(Spliterators.spliteratorUnknownSize(array.iterator(), 0), false)
                .filter(Long.class::isInstance)
                .mapToLong(Long.class::cast)
                .sum());

        StreamSupport.stream(Spliterators.spliteratorUnknownSize(array.iterator(), 0), false)
                .forEach(json -> {
                    if (json instanceof JSONArray)
                        countArray((JSONArray) json, count);
                    if (json instanceof JSONObject)
                        countObject((JSONObject) json, count);
                });

    }

    private static void countObject(JSONObject json, AtomicLong count) {
        boolean ignore = json.toMap().values().stream()
                .anyMatch(object -> "red".equals(object));
        if (!ignore) {
            count.addAndGet(json.toMap().values().stream()
                    .filter(Integer.class::isInstance)
                    .mapToInt(Integer.class::cast)
                    .sum());
            count.addAndGet(json.toMap().values().stream()
                    .filter(Long.class::isInstance)
                    .mapToLong(Long.class::cast)
                    .sum());

            json.toMap().values().forEach(object -> {
                if (object instanceof ArrayList)
                    countArray(new JSONArray((ArrayList) object), count);
                if (object instanceof HashMap)
                    countObject(new JSONObject((HashMap) object), count);
            });
        }
    }

}
