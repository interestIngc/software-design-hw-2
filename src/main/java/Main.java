import client.VKSearchClient;
import search.VKSearchManager;

import java.io.IOException;
import java.time.Instant;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String accessToken = "5bfd5c195bfd5c195bfd5c19595b868cfc55bfd5bfd5c193896bc4b8d247cdad5a75473";
        String tag = "#lol";
        VKSearchManager manager = new VKSearchManager(accessToken, new VKSearchClient());

        int hours = 10;
        int[] frequency = manager.calculateTagFrequency(tag, hours, Instant.now().getEpochSecond());

        for (int i = 0; i < hours; i++) {
            System.out.println("hour: " + (i + 1) + " frequency: " + frequency[i]);
        }
    }
}
