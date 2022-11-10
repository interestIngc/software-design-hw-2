package client;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class VKSearchClientTest {
    private static final String ACCESS_TOKEN =
            "5bfd5c195bfd5c195bfd5c19595b868cfc55bfd5bfd5c193896bc4b8d247cdad5a75473";

    @Test
    public void fetchesData() throws IOException, InterruptedException {
        VKSearchClient client = new VKSearchClient();
        long time = 1668083559;
        String url = "https://api.vk.com/method/newsfeed.search?q=%23cats"
                + "&start_time=" + (time - 3600)
                + "&end_time=" + time
                + "&access_token=" + ACCESS_TOKEN
                + "&v=5.131";

        String data = client.fetchData(url);

        assertTrue(data.length() > 0);
    }
}
