package client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class VKSearchClient {
    private static final int CONNECTION_TIMEOUT_MILLIS = 10000;

    HttpClient httpClient;

    public VKSearchClient() {
        httpClient = HttpClient.newHttpClient();
    }

    public String fetchData(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .timeout(Duration.ofMillis(CONNECTION_TIMEOUT_MILLIS))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
