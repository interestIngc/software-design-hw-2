package search;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import post.Post;

import java.util.ArrayList;
import java.util.List;

public final class VKSearchResponseParser {
    public static List<Post> parse(String data) {
        JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
        JsonArray entries =
                jsonObject.get("response")
                        .getAsJsonObject()
                        .getAsJsonArray("items");

        List<Post> posts = new ArrayList<>(entries.size());
        for (JsonElement element : entries) {
            posts.add(new Post(element.getAsJsonObject().get("date").getAsLong()));
        }

        return posts;
    }
}
