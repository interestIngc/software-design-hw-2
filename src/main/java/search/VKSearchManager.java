package search;

import client.VKSearchClient;
import post.Post;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VKSearchManager {
    private static final String HOST = "https://api.vk.com";
    private static final String VERSION = "5.131";
    private static final int HOUR_IN_SECONDS = 60 * 60;

    private final String accessToken;
    private final VKSearchClient client;

    public VKSearchManager(String accessToken, VKSearchClient client) {
        this.accessToken = accessToken;
        this.client = client;
    }

    public int[] calculateTagFrequency(String tag, int hours, long endTime)
            throws IOException, InterruptedException {
        if (hours < 1 || hours > 24) {
            throw new IllegalArgumentException("hours must be positive");
        }
        long startTime = endTime - hours * HOUR_IN_SECONDS;
        String url = makeUrl(tag, startTime, endTime);

        List<Post> posts = VKSearchResponseParser.parse(client.fetchData(url));

        int[] frequency = new int[hours];
        for (Post post : posts) {
            int hour = (int) ((post.getDate() - startTime) / HOUR_IN_SECONDS);
            frequency[hour]++;
        }

        return frequency;
    }

    private String makeUrl(String tag, long startTime, long endTime) {
        StringBuilder stringBuilder =
                new StringBuilder()
                        .append(HOST)
                        .append("/method")
                        .append("/newsfeed.search")
                        .append("?q=")
                        .append(URLEncoder.encode(tag, StandardCharsets.UTF_8));

        Map<String, String> params = new LinkedHashMap<>();
        params.put("start_time", String.valueOf(startTime));
        params.put("end_time", String.valueOf(endTime));
        params.put("access_token", accessToken);
        params.put("v", VERSION);

        params.forEach((key, value) -> stringBuilder.append("&").append(key).append("=").append(value));
        return stringBuilder.toString();
    }
}
