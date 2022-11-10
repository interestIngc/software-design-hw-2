package search;

import client.VKSearchClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VKSearchManagerTest {
    private static final String ACCESS_TOKEN = "abacaba";
    private static final String TAG = "#lol";
    private static final long CURRENT_TIME = 1668015190;

    @Mock private VKSearchClient client;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateTagFrequency_invalidHours() throws IOException, InterruptedException {
        VKSearchManager manager = new VKSearchManager(ACCESS_TOKEN, client);

        manager.calculateTagFrequency(TAG, 25, CURRENT_TIME);
    }

    @Test
    public void calculateTagFrequency_oneHour() throws IOException, InterruptedException {
        String response = "{\n" +
                "\"response\":{\n" +
                "\"count\":2\n" +
                ",\"items\":[\n" +
                "{\n" +
                "\"date\":1668015189\n" +
                ",\"text\":\"#анекдоты #шутка #lol\"\n" +
                "},\n" +
                "{\n" +
                "\"date\":1668014743\n" +
                ",\"text\":\"#lol что за жиза.\"\n" +
                "}]}}\n";
        when(client.fetchData(any())).thenReturn(response);

        VKSearchManager manager = new VKSearchManager(ACCESS_TOKEN, client);
        int[] frequency = manager.calculateTagFrequency(TAG, 1, CURRENT_TIME);

        String expectedUrl = "https://api.vk.com/method/newsfeed.search?q=%23lol"
                + "&start_time=" + (CURRENT_TIME - 3600)
                + "&end_time=" + CURRENT_TIME
                + "&access_token=" + ACCESS_TOKEN
                + "&v=5.131";
        verify(client).fetchData(expectedUrl);

        assertThat(frequency).hasSize(1);
        assertThat(frequency[0]).isEqualTo(2);
    }

    @Test
    public void calculateTagFrequency_twoHours() throws IOException, InterruptedException {
        String response = "{\n" +
                "\"response\":{\n" +
                "\"count\":3\n" +
                ",\"items\":[\n" +
                "{\n" +
                "\"date\":1668015189\n" +
                ",\"text\":\"#анекдоты #шутка #lol\"\n" +
                "},\n" +
                "{\n" +
                "\"date\":1668014743\n" +
                ",\"text\":\"#lol что за жиза.\"\n" +
                "},\n" +
                "{\n" +
                "\"date\":1668009000\n" +
                ",\"text\":\"#lol kek\"\n" +
                "}\n" +
                "]}}\n";
        when(client.fetchData(any())).thenReturn(response);

        VKSearchManager manager = new VKSearchManager(ACCESS_TOKEN, client);
        int[] frequency = manager.calculateTagFrequency(TAG, 2, CURRENT_TIME);

        String expectedUrl = "https://api.vk.com/method/newsfeed.search?q=%23lol"
                + "&start_time=" + (CURRENT_TIME - 7200)
                + "&end_time=" + CURRENT_TIME
                + "&access_token=" + ACCESS_TOKEN
                + "&v=5.131";
        verify(client).fetchData(expectedUrl);

        assertThat(frequency).hasSize(2);
        assertThat(frequency[0]).isEqualTo(1);
        assertThat(frequency[1]).isEqualTo(2);
    }
}
